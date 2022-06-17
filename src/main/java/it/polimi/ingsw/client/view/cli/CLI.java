package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.utilities.InputParser;
import it.polimi.ingsw.client.view.cli.graphics.*;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.*;
import java.util.function.Predicate;

import static it.polimi.ingsw.shared.enums.MovementDestination.HALL;

public class CLI implements ViewInterface {

    private boolean isSet = false;
    private BoardData board;
    private boolean gameMode;
    private String nickname;
    private Integer numPlayer;
    private Printer printer;
    private Client client;

    public CLI(Client client) {
        this.client = client;
    }


    public String getNickname() {
        return nickname;
    }

    public void initCLI() {
        Logo.printLogo();
    }


    // *********************************************************************  //
    //                               LOGIN                                    //
    // *********************************************************************  //

    public String nicknameRequest() {
        System.out.println("Insert your nickname (be sure to insert only valid characters (A-Z, a-z, 0-9):");

        nickname = InputParser.getLine();

        while(nickname.equalsIgnoreCase("")) {
            System.out.println("Be sure to type something");
            nickname = InputParser.getLine();
        }
        System.out.printf("Nickname selected: %s%n", nickname);
        return nickname;
    }

    //asks game mode

    public boolean gameModeRequest() {

        System.out.println("Insert a game mode, simple or expert mode: s | e");
        String s = InputParser.getLine();
        while((!s.equals("s")&&!s.equals("e"))){
            System.out.println("Be sure to type s or e");
            s = InputParser.getLine();
        }
        if (s.equals("s")){
            System.out.println("You've chosen simple mode");
            return false;
        }
        else if (s.equals("e")){
            System.out.println("You've chosen expert mode");
            return true;
        }
        return false;
    }
    //ask number of players
    public int numberOfPlayersRequest(){
        System.out.println("Insert desired number of players: 2, 3 or 4");
        numPlayer = InputParser.getInt();

//        while(numPlayer == null || (numPlayer < 2 || numPlayer > 4 ) ) {
        while(numPlayer == null ) {
                System.out.println("You must choose the correct number of players");
                numPlayer = InputParser.getInt();
        }


        System.out.printf("Number of players: %d\n", numPlayer);
        isSet = true;
        return numPlayer;
    }
    //shows that you're waiting for the others
    @Override
    public void waiting() {
        Waiting.printWaiting();
        /*if every player joined
        if(InputParser.getLine().equals("")){
            System.out.println("Initializing Game Board");
            activeGame();
        }
        */

    }



    // *********************************************************************  //
    //                               SET UP                                   //
    // *********************************************************************  //


    public void setBoard(BoardData board) {
        this.printer.setBoard(board);
        this.board = board;
    }

    @Override
    public void endGame(String winnerPlayer) {
        if(winnerPlayer.equals(nickname)){
            printer.printYouWon();
        }else{
            printer.printWinner(winnerPlayer);
        }
    }

    @Override
    public void askChooseIsland(boolean setBan, boolean calculateInfluence) {
        System.out.print("Choose Island for "+(setBan ? "placing a ban card" : "")+ (calculateInfluence ? "influence calculation" : ""));
        int selectedIsle = selectIsle();
        client.sendCommandMessage(new ChooseIslandResponse(nickname,selectedIsle,setBan,calculateInfluence));
    }

    @Override
    public void askChooseColour(int toDiscard, boolean toExclude) {
        System.out.print("Choose Colour to "+(toDiscard > 0 ? "discard" : "")+ (toExclude ? "exclude from influence calculation" : "")+" (r = red, b = blue, g = green, p = pink, y = yellow)\n ");
        System.out.print("Choose student to move: ");
        PawnColour selectedColour = null;
        while (selectedColour == null){
            String input = InputParser.getLine();
            switch (input) {
                case "r":
                    selectedColour = PawnColour.RED;
                    break;
                case "g":
                    selectedColour = PawnColour.GREEN;
                    break;
                case "b":
                    selectedColour = PawnColour.BLUE;
                    break;
                case "y":
                    selectedColour = PawnColour.YELLOW;
                    break;
                case "p":
                    selectedColour = PawnColour.PINK;
                    break;
                default:
                    System.out.print("choose an available option:\n");
                    selectedColour = null;
                    break;
            }
        }
        client.sendCommandMessage(new ChooseColourResponse(nickname,selectedColour,toExclude,toDiscard));


    }

    @Override
    public void askMoveStudentsFromCard(int characterId, MovementDestination destination, int studentsToMove, boolean canMoveLess) {
        System.out.print("You can move up to "+studentsToMove +" students from this card to "+destination.toString()+"\n");
        printer.printCharacters();
        int selectedIsle = -1;

        if(destination.equals(HALL)){
            printer.printPlayer(nickname);
        }else if(destination.equals(MovementDestination.ISLE)){
            selectedIsle = selectIsle();
        }

        Map<PawnColour,Integer> selectedStudents = new EnumMap<>(PawnColour.class);
        int movedStudents = 0;
        while(movedStudents<studentsToMove){
            PawnColour selectedStudent = selectStudentFromCharacter(characterId);
            if(selectedStudent!= null){
                selectedStudents.put(selectedStudent,selectedStudents.getOrDefault(selectedStudent,0)+1);
                movedStudents++;
            }else break;
        }
        client.sendCommandMessage(new MoveStudentFromCardResponse(nickname,characterId,selectedIsle,destination,selectedStudents));
    }

    @Override
    public void askExchangeStudents(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to) {
        System.out.print("\n You can exchange up to "+numberOfStudents+ " from "+ from.toString() +" to "+to.toString()+"\n");
        Map<PawnColour,Integer> fromMap = new EnumMap<>(PawnColour.class);
        Map<PawnColour,Integer> toMap = new EnumMap<>(PawnColour.class);
        PawnColour fromColour = null;
        PawnColour toColour = null;
        int studentsMoved = 0;
        while(studentsMoved < numberOfStudents){
            switch(from){
                case CARD -> {
                    System.out.print("\nChoose a student from Card: \n");
                    fromColour = selectStudentFromCharacter(characterId);
                }
                case HALL ->{
                    System.out.print("\nChoose a student from hall: \n");
                    fromColour = selectStudentFromHall();
                }
            }
            if(fromColour == null)break;

            fromMap.put(fromColour,fromMap.getOrDefault(fromColour,0)+1);

            switch(to){
                case ENTRANCE ->{
                    System.out.print("\nChoose a student from entrance: ");
                    toColour = selectStudentFromEntrance();
                    toMap.put(toColour,toMap.getOrDefault(toColour,0)+1);
                }

            }
        }
        client.sendCommandMessage(new ExchangeStudentsResponse(nickname,characterId,from ,to, fromMap, toMap));

    }

    private PawnColour selectStudentFromHall() {
        PawnColour selectedStudent = null;
        while(selectedStudent == null) {
            System.out.print("\nChoose student to move: (r = red, b = blue, g = green, p = pink, y = yellow) \n");
            printer.drawHall(nickname);
            String input  = InputParser.getLine();

            switch (input) {
                case "r":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.RED)>0){
                        selectedStudent = PawnColour.RED;
                    } else{System.out.print("There are no student of that color in the Hall. Choose again\n");}
                    break;
                case "g":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.GREEN)>0){
                        selectedStudent = PawnColour.GREEN;
                    } else{System.out.print("There are no student of that color in the Hall. Choose again\n");}
                    break;
                case "b":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.BLUE)>0){
                        selectedStudent = PawnColour.BLUE;
                    }else{System.out.print("There are no student of that color in the Hall. Choose again\n");}
                    break;
                case "y":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.YELLOW)>0){
                        selectedStudent = PawnColour.YELLOW;
                    } else{System.out.print("There are no student of that color in the Hall. Choose again\n");}
                    break;
                case "p":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.PINK)>0){
                        selectedStudent = PawnColour.PINK;
                    } else{System.out.print("There are no student of that color in the Hall. Choose again\n");}
                    break;
                default:
                    selectedStudent = null;
                    break;
            }
        }
        return selectedStudent;
    }

    @Override
    public void initBoard(BoardData boardData, boolean expertMode) {
        this.board = boardData;
        this.gameMode =expertMode;
        this.printer = new Printer(gameMode);
        printer.setBoard(boardData);
        printer.printBoard();
    }

    // *********************************************************************  //
    //                               ACTIONS                                  //
    // *********************************************************************  //
    
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    //info solo per chi crea il gioco
    @Override
    public void askLobbyInfo() {
        String nickname = nicknameRequest();
        int numberOfPlayers = numberOfPlayersRequest();
        this.gameMode = gameModeRequest();
        //costruisco il messaggio
        LobbyInfoResponse message = new LobbyInfoResponse(nickname, numberOfPlayers, gameMode);
        //mando messaggio al server
        client.sendCommandMessage(message);
        this.waiting();

    }

    @Override
    public void askUserInfo(boolean invalidName) {
        System.out.print("\nThe chosen nickname is already used by another player, try again...\n");
        askUserInfo();
    }

    @Override
    public void askUserInfo() {
        String nickname = nicknameRequest();
        NicknameResponse message = new NicknameResponse((nickname));
        client.sendCommandMessage(message);
        this.waiting();
    }

    private int selectIsle() {
        System.out.print("Choose Isle Between: \n");
        printer.printIsleCircle();
        int dest = Integer.parseInt(InputParser.getLine());
        while(dest<0 || dest > 11){
            System.out.print("choose valid index \n");
            dest = Integer.parseInt(InputParser.getLine());
        }
        return dest;
    }

    @Override
    public void moveMotherNature(ArrayList<Integer> availableIsleIndexes) {
        printer.printBoard();
        printer.printIsleCircle();
        System.out.println("Choose mother nature destination:\n" );
        System.out.println(availableIsleIndexes);
        printer.printExpertOption(nickname);

        String input = InputParser.getLine();
        if(handleCharacterChoice(input))return;
        Integer mothernaturedestination = Integer.valueOf(input);
        while(!availableIsleIndexes.contains(mothernaturedestination)) {
            printer.printIsleCircle();
            System.out.println("\nChoose an available mother nature destination\n");
            System.out.println(availableIsleIndexes);
            mothernaturedestination = Integer.valueOf(InputParser.getLine());
        }
        MoveMotherNatureResponse message = new MoveMotherNatureResponse(nickname,mothernaturedestination);
        client.sendCommandMessage(message);
        this.waiting();
    }

    @Override
    public void askCloud(Set<Integer> availableCloudIndexes) {

        System.out.println("\nChoose cloud between: \n");
        printer.printClouds(availableCloudIndexes);
        printer.printExpertOption(nickname);
        String input = InputParser.getLine();
        if(handleCharacterChoice(input))return;
        int chosenCloud = Integer.parseInt(input);
        while(!availableCloudIndexes.contains(chosenCloud)) {
            System.out.println("not valid cloud. Choose again:\n");
            chosenCloud = Integer.parseInt(InputParser.getLine());
        }
        System.out.printf("chosen Cloud: %d\n", chosenCloud);
        ChooseCloudResponse message = new ChooseCloudResponse(nickname,chosenCloud);
        client.sendCommandMessage(message);
        this.waiting();

    }


    private boolean handleCharacterChoice(String input){

        if(!Objects.equals(input, "c") || !gameMode || board.getPlayerBoards().get(nickname).hasPlayedCharacter())return false;


        printer.printCharacters();
        System.out.print("Choose a character to play or press c to cancel\n");
        String line = InputParser.getLine();
        if(line.equals("c")){
            return false;
        }

        do{
            if(board.getCharacters().containsKey(Integer.valueOf(line))){
                int selectedCharacter = Integer.parseInt(line);
                if(board.getCharacters().get(selectedCharacter).getPrice() > board.getPlayerBoards().get(nickname).getCoins()){
                    System.out.print("\nYou don't have enough coins to play this character\n");
                }else{
                    client.sendCommandMessage(new UseCharacterEffectRequest(nickname,selectedCharacter));
                    return true;
                }
            }
            System.out.print("\nChoose again or type 'c'\n");
            line = InputParser.getLine();
            if(line.equals("c")){
                return false;
            }

        }while(true);

    }


    private PawnColour selectStudentFromCharacter(int characterId){
        PawnColour selectedStudent = null;
        printer.printCharacter(characterId);
        while(selectedStudent == null) {
            System.out.print("Choose student to move: (r = red, b = blue, g = green, p = pink, y = yellow) \n");
            System.out.print("Press 's' to stop moving \n");
            String input  = InputParser.getLine();
            if(input.equals("s"))break;
            switch (input) {
                case "r":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.RED)>0){
                        selectedStudent = PawnColour.RED;
                    } else{System.out.print("There are no student of that color on the card. Choose again\n");}
                    break;
                case "g":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.GREEN)>0){
                        selectedStudent = PawnColour.GREEN;
                    } else{System.out.print("There are no student of that color on the card Choose again\n");}
                    break;
                case "b":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.BLUE)>0){
                        selectedStudent = PawnColour.BLUE;
                    }else{System.out.print("There are no student of that color on the card. Choose again\n");}
                    break;
                case "y":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.YELLOW)>0){
                        selectedStudent = PawnColour.YELLOW;
                    } else{System.out.print("There are no student of that color on the card. Choose again\n");}
                    break;
                case "p":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.PINK)>0){
                        selectedStudent = PawnColour.PINK;
                    } else{System.out.print("There are no student of that color on the card. Choose again\n");}
                    break;
                default:
                    selectedStudent = null;
                    break;
            }
        }
        return selectedStudent;
    }


    // *********************************************************************  //
    //                               PREDICATES                               //
    // *********************************************************************  //

    public static Predicate<Integer> conditionOnIntegerRange(int min, int max){
        return p -> p >= min && p <= max;
    }

    public static Predicate<Integer> conditionOnInteger(List<Integer> list){
        return list::contains;
    }

    public static Predicate<String> conditionOnString(List<String> list){
        return list::contains;
    }




    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {
        printer.printBoard();
        printer.printDeckHorizontal(nickname);
        System.out.println("Choose Assistant Card between: "+availableAssistantIds);
        String input =InputParser.getLine();

        int chosenAssistant = Integer.parseInt(input);

        while(!availableAssistantIds.contains(chosenAssistant)) {
            System.out.println("not valid index. Choose again:");
            chosenAssistant = Integer.parseInt(InputParser.getLine());
        }
        System.out.printf("chosen Assistant: %d\n", chosenAssistant);
        ChooseAssistantResponse message = new ChooseAssistantResponse(nickname,chosenAssistant);
        //mando messaggio al server
        client.sendCommandMessage(message);
        this.waiting();

    }

    @Override
    public void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability) {

        printer.printBoard();
        PawnColour selectedColour = selectStudentFromEntrance();
        if(selectedColour == null)return;
        MessageFromClientToServer toReturnMessage = null;

        if(hallColourAvailability.get(selectedColour)){
            while(toReturnMessage == null){
                System.out.println("Choose a destination for student movement between Hall(h) and Isles(i) : \n");
                printer.printExpertOption(nickname);

                String input = InputParser.getLine();
                if(handleCharacterChoice(input))return;

                switch (input) {
                    case "h":
                        toReturnMessage = new MoveStudentToHallResponse(nickname, selectedColour);
                        break;
                    case "i":
                        int selectedIsland = selectIsle();
                        toReturnMessage = new MoveStudentToIsleResponse(nickname, selectedIsland, selectedColour);
                        break;
                    default:
                        toReturnMessage = null;
                }
            }
        }
        else{
            int selectedIsland = selectIsle();
            toReturnMessage = new MoveStudentToIsleResponse(nickname, selectedIsland, selectedColour);
        }
        client.sendCommandMessage(toReturnMessage);
    }

    private PawnColour selectStudentFromEntrance() {
        PawnColour selectedStudent = null;
        while(selectedStudent == null) {
            System.out.print("\nChoose student to move: (r = red, b = blue, g = green, p = pink, y = yellow) \n");
            printer.showStudentsInEntrance(nickname);
            printer.printExpertOption(nickname);
            String input  = InputParser.getLine();
            if(handleCharacterChoice(input))return null;

            switch (input) {
                case "r":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.RED)>0){
                        selectedStudent = PawnColour.RED;
                    } else{System.out.print("There are no student of that color in the entrance. Choose again\n");}
                    break;
                case "g":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.GREEN)>0){
                        selectedStudent = PawnColour.GREEN;
                    } else{System.out.print("There are no student of that color in the entrance. Choose again\n");}
                    break;
                case "b":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.BLUE)>0){
                        selectedStudent = PawnColour.BLUE;
                    }else{System.out.print("There are no student of that color in the entrance. Choose again\n");}
                    break;
                case "y":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.YELLOW)>0){
                        selectedStudent = PawnColour.YELLOW;
                    } else{System.out.print("There are no student of that color in the entrance. Choose again\n");}
                    break;
                case "p":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.PINK)>0){
                        selectedStudent = PawnColour.PINK;
                    } else{System.out.print("There are no student of that color in the entrance. Choose again\n");}
                    break;
                default:
                    selectedStudent = null;
                    break;
            }
        }
        return selectedStudent;
    }


}
