package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;

import static it.polimi.ingsw.shared.enums.MovementDestination.HALL;

/**
 * Class to manage the command line interface
 */
public class CLI implements ViewInterface {

    private BoardData board;
    private String nickname;
    private Integer numPlayer;
    private Printer printer;
    private Client client;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public CLI(Client client) {
        this.client = client;
        this.printer = new Printer();
    }


    public String getNickname() {
        return nickname;
    }

    /**
     * Private method to initialize the logo and start the game
     */
    public void initCLI() {
        printer.printLogo();
    }


    // *********************************************************************  //
    //                               LOGIN                                    //
    // *********************************************************************  //

    /**
     * Method to handle the nickname request
     * @return the client nickname
     */
    public String nicknameRequest() {
        printer.writeln("Insert your nickname (be sure to insert only valid characters (A-Z, a-z, 0-9):");
        nickname = readString();

        while(nickname.equalsIgnoreCase("")) {
            printer.writeln("Be sure to type something");
            nickname = readString();
        }
        printer.writeln("Nickname selected: "+ nickname);
        return nickname;
    }

    /**
     * Method to handle the game mode request
     * @return true if expert, false if simple
     */
    public boolean gameModeRequest() {

        printer.writeln("Insert a game mode, simple or expert mode: s | e");
        String s = readString();
        while((!s.equals("s")&&!s.equals("e"))){
            printer.writeln("Be sure to type s or e");
            s = readString();
        }
        if (s.equals("s")){
            printer.writeln("You've chosen simple mode");
            return false;
        }
        else if (s.equals("e")){
            printer.writeln("You've chosen expert mode");
            return true;
        }
        return false;
    }

    /**
     * Method to handle the request for the number of players
     * @return the number of players
     */

    public int numberOfPlayersRequest(){
        numPlayer = null;
        while(numPlayer == null ) {
            printer.writeln("Insert desired number of players: 2, 3: ");
            String input = readString();
            try {
                numPlayer = Integer.parseInt(input);
                if (numPlayer < 2 || numPlayer > 3) {
                    printer.writeln("You must choose the correct number of players");
                    numPlayer = null;
                }
            }catch (NumberFormatException e){
                printer.writeln("You must choose the correct number of players");
                numPlayer = null;
            }
        }


        printer.writeln("Number of players: "+numPlayer);
        return numPlayer;
    }

    /**
     * Put the client in waiting
     */
    @Override
    public void waiting() {
        printer.printWaiting();
        /*if every player joined
        if(readString().equals("")){
            printer.write("Initializing Game Board");
            activeGame();
        }
        */

    }



    // *********************************************************************  //
    //                               SET UP                                   //
    // *********************************************************************  //

    /**
     * Method to set the board data
     * @param board the board data
     */
    public void setBoard(BoardData board) {
        this.printer.setBoard(board);
        this.board = board;
    }

    @Override
    public void endGame(String causingPlayer,String cause) {

    }

    /**
     * Method to handle the client's island choice. If setBan is true, it
     * put the ban on. If calculateInfluence is true, it calculates the influence
     * @param setBan the island ban
     * @param calculateInfluence the island influence
     */
    @Override
    public void askChooseIsland(boolean setBan, boolean calculateInfluence) {
        printer.writeln("Choose Island for "+(setBan ? "placing a ban card" : "")+ (calculateInfluence ? "influence calculation" : ""));
        int selectedIsle = selectIsle();
        client.sendCommandMessage(new ChooseIslandResponse(nickname,selectedIsle,setBan,calculateInfluence));
    }

    /**
     * Method to handle the colour choice. If discard > 0 it discard the colour
     * choice. If toExclude is true it exclude the colour choice when calculating influence
     * @param toDiscard value of the colour to discard
     * @param toExclude if it's true it discard the colour
     */
    @Override
    public void askChooseColour(int toDiscard, boolean toExclude) {
        printer.writeln("Choose Colour to "+(toDiscard > 0 ? "discard" : "")+ (toExclude ? "exclude from influence calculation" : "")+" (r = red, b = blue, g = green, p = pink, y = yellow)\n ");
        PawnColour selectedColour = null;
        while (selectedColour == null){
            String input = readString();
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
                    printer.writeln("choose an available option:");
                    selectedColour = null;
                    break;
            }
        }
        client.sendCommandMessage(new ChooseColourResponse(nickname,selectedColour,toExclude,toDiscard));


    }

    /**
     * Method to handle the choice of the student on the Character Card
     * @param characterId character's cards id
     * @param destination destination where to move the student to
     * @param studentsToMove value of the colour of the student to move
     * @param canMoveLess if it's true the player can decide to move less students, instead than all of them
     */
    @Override
    public void askMoveStudentsFromCard(int characterId, MovementDestination destination, int studentsToMove, boolean canMoveLess) {
        printer.writeln("You can move up to "+studentsToMove +" students from this card to "+destination.toString()+"\n");
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

    /**
     * Method to handle the exchange of students
     * @param characterId character's cards id
     * @param numberOfStudents max number that the player can exchange
     * @param from starting location of the students to exchange
     * @param to destination of the students
     */
    @Override
    public void askExchangeStudents(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to) {
        printer.write("\n You can exchange up to "+numberOfStudents+ " from "+ from.toString() +" to "+to.toString()+"\n");
        Map<PawnColour,Integer> fromMap = new EnumMap<>(PawnColour.class);
        Map<PawnColour,Integer> toMap = new EnumMap<>(PawnColour.class);
        PawnColour fromColour = null;
        PawnColour toColour = null;
        int studentsMoved = 0;
        while(studentsMoved < numberOfStudents){
            switch(from){
                case CARD -> {
                    printer.writeln("\nChoose a student from Card: ");
                    fromColour = selectStudentFromCharacter(characterId);
                }
                case HALL ->{
                    printer.writeln("\nChoose a student from hall: ");
                    fromColour = selectStudentFromHall();
                }
            }
            if(fromColour == null)break;

            fromMap.put(fromColour,fromMap.getOrDefault(fromColour,0)+1);

            switch(to){
                case ENTRANCE ->{
                    printer.writeln("\nChoose a student from entrance: ");
                    toColour = selectStudentFromEntrance();
                    toMap.put(toColour,toMap.getOrDefault(toColour,0)+1);
                    studentsMoved++;
                }

            }
        }
        client.sendCommandMessage(new ExchangeStudentsResponse(nickname,characterId,from ,to, fromMap, toMap));

    }

    /**
     * Method to select student from hall
     * @return the value Pawncolour of the student chosen
     */
    private PawnColour selectStudentFromHall() {
        PawnColour selectedStudent = null;
        while(selectedStudent == null) {
            printer.writeln("\nChoose student to move: (r = red, b = blue, g = green, p = pink, y = yellow)");
            printer.drawHall(nickname);
            String input  = readString();

            switch (input) {
                case "r":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.RED)>0){
                        selectedStudent = PawnColour.RED;
                    } else{printer.write("There are no student of that color in the Hall. Choose again\n");}
                    break;
                case "g":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.GREEN)>0){
                        selectedStudent = PawnColour.GREEN;
                    } else{printer.write("There are no student of that color in the Hall. Choose again\n");}
                    break;
                case "b":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.BLUE)>0){
                        selectedStudent = PawnColour.BLUE;
                    }else{printer.write("There are no student of that color in the Hall. Choose again\n");}
                    break;
                case "y":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.YELLOW)>0){
                        selectedStudent = PawnColour.YELLOW;
                    } else{printer.write("There are no student of that color in the Hall. Choose again\n");}
                    break;
                case "p":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.PINK)>0){
                        selectedStudent = PawnColour.PINK;
                    } else{printer.write("There are no student of that color in the Hall. Choose again\n");}
                    break;
                default:
                    selectedStudent = null;
                    break;
            }
        }
        return selectedStudent;
    }

    /**
     * Method to initialize board
     * @param boardData board data
     * @param expertMode if it's true, the game mode is expert
     */
    @Override
    public void initBoard(BoardData boardData, boolean expertMode) {
        this.board = boardData;
        printer.setBoard(boardData);
        printer.printBoard(nickname);
    }

    /**
     * Method to handle the request for lobby info
     * @param username the player's chosen username
     * @param canJoinLobby if it's true, the player can join a lobby
     * @param canRejoinLobby if it's true, the player can rejoin a lobby after disconnecting
     */
    @Override
    public void askLoginInfo(String username, boolean canJoinLobby, boolean canRejoinLobby) {

        while(true) {
            printer.writeln("Press 'n' to create a new lobby" + (canJoinLobby ? " or press 'j' to join an existent lobby: " : "") + (canRejoinLobby ? " or press 'r' to rejoin an existent lobby: " : ""));
            String loginChoice = readString();
            if (canJoinLobby && loginChoice.equals("j") || (canRejoinLobby && loginChoice.equals("r"))) {
                String nickname = nicknameRequest();
                JoinLobbyResponse message = new JoinLobbyResponse(username, nickname, canRejoinLobby && loginChoice.equals("r"));
                client.sendCommandMessage(message);
                this.waiting();
                return;
            }
            else if (loginChoice.equals("n")) {
                String nickname = nicknameRequest();
                int numberOfPlayers = numberOfPlayersRequest();
                boolean gameMode = gameModeRequest();
                //costruisco il messaggio
                CreateLobbyResponse message = new CreateLobbyResponse(username,nickname, numberOfPlayers, gameMode);
                //mando messaggio al server
                client.sendCommandMessage(message);
                this.waiting();
                return;
            }
        }

    }

    /**
     * Method to notify the disconnection of a player
     * @param disconnectedPlayerName username of the disconnected player
     */
    @Override
    public void notifyDisconnection(String disconnectedPlayerName) {
        printer.write(disconnectedPlayerName + " disconnected...\n");
    }

    /**
     * Method to notify that a player has joined
     * @param joiningPlayer username of the player that has joined the lobby
     */
    @Override
    public void notifyPlayerHasJoined(String joiningPlayer) {
        printer.write(joiningPlayer + " joined the game...\n");
    }

    @Override
    public void askLobbyInfo() {

    }

    // *********************************************************************  //
    //                               ACTIONS                                  //
    // *********************************************************************  //

    /**
     * Method to display a message
     * @param message the message to display
     */
    @Override
    public void displayMessage(String message) {
        printer.write(message);
    }

    /**
     * Method to request the user info
     * @param isRejoin if it's true, the player can rejoin after disconnecting
     */
    @Override
    public void askUserInfo(boolean isRejoin) {
        printer.write(isRejoin ? "There are no players associated to the selected nickname\n" : "Your nickname is already used\n");
        printer.write("Try again: \n");

        String nickname = nicknameRequest();
        JoinLobbyResponse message = new JoinLobbyResponse(nickname,nickname,isRejoin);
        client.sendCommandMessage(message);
        this.waiting();
    }

    /**
     * Method to handle the selection of an island
     * @return the index of the chosen isle
     */
    private int selectIsle() {
        printer.writeln("Choose Isle Between: \n");
        printer.printIsleCircle();
        Integer dest = null;
        while(dest == null){
            String input = readString();
            try{
                dest = Integer.parseInt(input);
                if(dest<0 || dest >= board.getGameBoard().getIsleCircle().getIsles().size()) {
                    dest = null;
                    printer.writeln("Choose a valid index");
                }
            }catch (NumberFormatException e){
                printer.writeln("Choose a valid index");
                dest = null;
            }

        }

        return dest;
    }

    /**
     * Method to handle the selection of the island where to put mother nature on
     * @param availableIsleIndexes available indexes the player can choose from
     */
    @Override
    public void moveMotherNature(Set<Integer> availableIsleIndexes) {
        printer.printBoard(nickname);
        printer.printIsleCircle();
        printer.writeln("Choose mother nature destination:" );
        printer.writeln(availableIsleIndexes.toString());
        printer.printExpertOption(nickname);
        Integer motherNatureDestination = null;
        while(motherNatureDestination == null ){
            String input = readString();
            if(handleCharacterChoice(input))return;
            try {
                motherNatureDestination = Integer.valueOf(input);
                if(!availableIsleIndexes.contains(motherNatureDestination)) {
                    motherNatureDestination = null;
                    printer.writeln("Choose a valid index");
                }
            } catch (NumberFormatException e) {
                printer.writeln("Choose a valid index");
                motherNatureDestination = null;
            }
        }

        MoveMotherNatureResponse message = new MoveMotherNatureResponse(nickname,motherNatureDestination);
        client.sendCommandMessage(message);
    }

    /**
     * Method to handle the selection of the cloud
     * @param availableCloudIndexes available indexes the player can choose from
     */
    @Override
    public void askCloud(Set<Integer> availableCloudIndexes) {

        printer.write("\nChoose cloud between: \n");
        printer.printClouds(availableCloudIndexes);
        printer.printExpertOption(nickname);

        Integer chosenCloud = null;
        while(chosenCloud == null ){
            String input = readString();
            if(handleCharacterChoice(input))return;
            try {
                chosenCloud = Integer.valueOf(input);
                if(!availableCloudIndexes.contains(chosenCloud)) {
                    chosenCloud = null;
                    printer.writeln("not valid cloud. Choose again:");
                }
            } catch (NumberFormatException e) {
                printer.writeln("not even close.. Try again:");
                chosenCloud = null;
            }
        }
        printer.write("chosen Cloud: " + chosenCloud +"\n");
        ChooseCloudResponse message = new ChooseCloudResponse(nickname,chosenCloud);
        client.sendCommandMessage(message);
        this.waiting();
    }


    /**
     * Method to handle the character's card choice
     * @param input the string from the player's input
     * @return if it returns true, the player has chosen a character
     */
    private boolean handleCharacterChoice(String input) {

        if (!Objects.equals(input, "c") || !board.isExpertMode() || board.getPlayerBoards().get(nickname).hasPlayedCharacter())
            return false;

        printer.printCharacters();
        Integer selectedCharacter = null;
        while (selectedCharacter == null) {
            printer.write("Choose a character to play or press c to cancel\n");
            String line = readString();
            if (line.equals("c")) {
                return false;
            }
            try {
                selectedCharacter = Integer.parseInt(line);
                if (board.getCharacters().containsKey(selectedCharacter)) {
                    if (board.getCharacters().get(selectedCharacter).getPrice() > board.getPlayerBoards().get(nickname).getCoins()) {
                        printer.writeln("\nYou don't have enough coins to play this character\n");
                        selectedCharacter = null;
                    } else {
                        client.sendCommandMessage(new UseCharacterEffectRequest(nickname, selectedCharacter));
                        return true;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                selectedCharacter = null;
            }
        }
        return false;
    }

    /**
     * Method to handle the choice of the colour the students from a card
     * @param characterId the character card's id
     * @return the value of the pawncolour of the student chosen
     */
    private PawnColour selectStudentFromCharacter(int characterId){
        PawnColour selectedStudent = null;
        printer.printCharacter(characterId);
        while(selectedStudent == null) {
            printer.write("Choose student to move: (r = red, b = blue, g = green, p = pink, y = yellow) \n");
            String input  = readString();
            if(input.equals("s"))break;
            switch (input) {
                case "r":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.RED)>0){
                        selectedStudent = PawnColour.RED;
                    } else{printer.write("There are no student of that color on the card. Choose again\n");}
                    break;
                case "g":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.GREEN)>0){
                        selectedStudent = PawnColour.GREEN;
                    } else{printer.write("There are no student of that color on the card Choose again\n");}
                    break;
                case "b":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.BLUE)>0){
                        selectedStudent = PawnColour.BLUE;
                    }else{printer.write("There are no student of that color on the card. Choose again\n");}
                    break;
                case "y":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.YELLOW)>0){
                        selectedStudent = PawnColour.YELLOW;
                    } else{printer.write("There are no student of that color on the card. Choose again\n");}
                    break;
                case "p":
                    if(board.getCharacters().get(characterId).getStudents().get(PawnColour.PINK)>0){
                        selectedStudent = PawnColour.PINK;
                    } else{printer.write("There are no student of that color on the card. Choose again\n");}
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


    /**
     * Method to handle the choice of the assistant card
     * @param availableAssistantIds available indexes of the assistant cards
     */
    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {
        printer.printBoard(nickname);
        printer.printDeckHorizontal(nickname);
        Integer chosenAssistant =  null;
        while (!availableAssistantIds.contains(chosenAssistant)) {
            try{
                printer.writeln("Choose Assistant Card between: "+availableAssistantIds);
                String input =readString();
                chosenAssistant = Integer.parseInt(input);
            }catch (NumberFormatException e){
                printer.writeln("Are you drunk?..Try again, you can do it!");
                chosenAssistant = null;
            }
        }

        printer.write("chosen Assistant: "+ chosenAssistant+"\n");
        ChooseAssistantResponse message = new ChooseAssistantResponse(nickname,chosenAssistant);
        //mando messaggio al server
        client.sendCommandMessage(message);
        this.waiting();

    }

    /**
     * Method to handle the choice of the student to move from the entrance
     * @param hallColourAvailability map of the colour of the students and their amount
     */
    @Override
    public void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability) {

        printer.printBoard(nickname);
        PawnColour selectedColour = selectStudentFromEntrance();
        if(selectedColour == null)return;
        MessageFromClientToServer toReturnMessage = null;

        if(hallColourAvailability.get(selectedColour)){
            while(toReturnMessage == null){

                printer.write("Choose a destination for student movement between Hall(h) and Isles(i) : \n");
                printer.printExpertOption(nickname);

                String input = readString();
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

    /**
     * Method to handle the colour of the student to move from entrance
     * @return the colour chosen
     */
    private PawnColour selectStudentFromEntrance() {
        PawnColour selectedStudent = null;
        while(selectedStudent == null) {
            printer.write("\nChoose student to move: (r = red, b = blue, g = green, p = pink, y = yellow) \n");
            printer.showStudentsInEntrance(nickname);
            printer.printExpertOption(nickname);

            String input  = readString();
            if(handleCharacterChoice(input))return null;

            switch (input) {
                case "r":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.RED)>0){
                        selectedStudent = PawnColour.RED;
                    } else{printer.write("There are no student of that color in the entrance. Choose again\n");}
                    break;
                case "g":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.GREEN)>0){
                        selectedStudent = PawnColour.GREEN;
                    } else{printer.write("There are no student of that color in the entrance. Choose again\n");}
                    break;
                case "b":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.BLUE)>0){
                        selectedStudent = PawnColour.BLUE;
                    }else{printer.write("There are no student of that color in the entrance. Choose again\n");}
                    break;
                case "y":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.YELLOW)>0){
                        selectedStudent = PawnColour.YELLOW;
                    } else{printer.write("There are no student of that color in the entrance. Choose again\n");}
                    break;
                case "p":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.PINK)>0){
                        selectedStudent = PawnColour.PINK;
                    } else{printer.write("There are no student of that color in the entrance. Choose again\n");}
                    break;
                default:
                    selectedStudent = null;
                    break;
            }
        }
        return selectedStudent;
    }

    /**
     * Method to read string from input
     * @return the string read
     */
    private String readString(){
        try{
            return reader.readLine();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
