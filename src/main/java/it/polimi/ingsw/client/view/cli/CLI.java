package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.utilities.InputParser;
import it.polimi.ingsw.client.view.cli.graphics.GraphicalCards;
import it.polimi.ingsw.client.view.cli.graphics.GraphicalStudents;
import it.polimi.ingsw.client.view.cli.graphics.Logo;
import it.polimi.ingsw.client.view.cli.graphics.Waiting;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.server.model.PlayerBoardData;
import it.polimi.ingsw.server.model.board.CloudData;
import it.polimi.ingsw.server.model.board.IsleCircleData;
import it.polimi.ingsw.server.model.board.IsleData;
import it.polimi.ingsw.shared.enums.PawnColour;
import java.util.*;
import java.util.function.Predicate;

public class CLI implements ViewInterface {

    private boolean isSet = false;
    private BoardData board;
    private ServerHandler serverHandler;
    private boolean gameMode;
    private String nickname;
    private Integer numPlayer;

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
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
        System.out.printf("Nickname selected: %s\n", nickname);
        return nickname;
    }

    //asks game mode

    public boolean gameModeRequest() {

        System.out.println("Insert a game mode, simple or expert mode: s | e");
        String s = InputParser.getLine();
        if (s.equals("s")){
            gameMode = false;
            System.out.println("You've chosen simple mode");
        }
        if (s.equals("e")){
            gameMode = true;
            System.out.println("You've chosen expert mode");
        }
        else while(s.equals("")){
            System.out.println("Be sure to type something");
            s = InputParser.getLine();
        }
        return gameMode;
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

    public void activeGame(){
        //input for commands

        System.out.println("Choose next move: \nc to Select Card\nm to Select Student to Move\n" +
                           "sd to Select Student Destination\nmd to Select Mother Nature Destination");
        String comm = InputParser.getLine();
        while(!comm.equalsIgnoreCase("")) {
            switch (comm) {
                case "c":
                    //selectCard();
                    break;
                case "m":
                    //selectStudentToMove();
                    break;
                case "sd":
                    //selectStudentDestination();
                    break;
                case "md":
                    //selectMotherNatureDestination();
                    break;
            }
            System.out.println("\nChoose next move: \nc to Select Card\nm to Select Student to Move\n" +
                    "sd to Select Student Destination\nmd to Select Mother Nature Destination");
            comm = InputParser.getLine();
        }
        System.out.println("\nYou're turn is finished");
    }

    public void setBoard(BoardData board) {
        this.board = board;
    }

    // *********************************************************************  //
    //                               ACTIONS                                  //
    // *********************************************************************  //

    public void showStudents(Map<PawnColour,Integer> studentMap) {
        //test per provare la stampa
        GraphicalStudents draw = new GraphicalStudents();
        draw.drawStudents(studentMap);
    }

    public void showStudentsInEntrance() {
        System.out.print("\nENTRANCE:\n");
        System.out.print("\n"+board.getPlayerBoards().get(nickname).getEntrance()+"\n");
        showStudents(board.getPlayerBoards().get(nickname).getEntrance());
    }

    
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void askLobbyInfo() {
        String nickname = nicknameRequest();
        int numberOfPlayers = numberOfPlayersRequest();
        boolean gameMode = gameModeRequest();
        //costruisco il messaggio
        LobbyInfoResponse message = new LobbyInfoResponse(nickname, numberOfPlayers, gameMode);
        //mando messaggio al server
        serverHandler.sendCommandMessage(message);
        this.waiting();

    }
    @Override
    public void askUserInfo() {
        String nickname = nicknameRequest();
        NicknameResponse message = new NicknameResponse((nickname));
        serverHandler.sendCommandMessage(message);
        this.waiting();

    }


    private int selectIsle() {
        System.out.print("Choose Isle Between: ");
        int i = 0;
        for(IsleData isle : board.getGameBoard().getIsleCircle().getIsles()){
            System.out.print("isle "+i+": \n");
            showStudents(isle.getStudentMap());
            i++;
        }
        int dest = Integer.parseInt(InputParser.getLine());
        while(dest<0 || dest > 11){
            System.out.print("choose valid index \n");
            dest = Integer.parseInt(InputParser.getLine());
        }
        return dest;
    }

    @Override
    public void moveMotherNature(ArrayList<Integer> availableIsleIndexes) {
        System.out.println("Chose mother nature destination:");
        for (Integer i : availableIsleIndexes) {
            System.out.println(i);
        }
        Integer mothernaturedestination = Integer.valueOf(InputParser.getLine());
        while(!availableIsleIndexes.contains(mothernaturedestination)) {
            System.out.println("Chose an available assistant mother nature destination");
            mothernaturedestination = Integer.valueOf(InputParser.getLine());
        }
        MoveMotherNatureResponse message = new MoveMotherNatureResponse(nickname,mothernaturedestination);
        serverHandler.sendCommandMessage(message);


    }

    @Override
    public void askCloud(ArrayList<Integer> availableCloudIndexes) {
        System.out.println("Choose cloud between: "+availableCloudIndexes);

        int chosenCloud = Integer.parseInt(InputParser.getLine());

        while(!availableCloudIndexes.contains(chosenCloud)) {
            System.out.println("not valid cloud. Choose again:");
            chosenCloud = Integer.parseInt(InputParser.getLine());
        }
        System.out.printf("chosen Cloud: %d\n", chosenCloud);
        ChooseCloudResponse message = new ChooseCloudResponse(nickname,chosenCloud);
        serverHandler.sendCommandMessage(message);

    }

//    @Override
//    public void askAssistantCard(Set<Integer> availableAssistantIds) {
//        System.out.println("Assistant card:");
//        for (Integer i : availableAssistantIds) {
//            System.out.println(i);
//        }
//        Integer AssistantCard = Integer.valueOf(InputParser.getLine());
//        while(!availableAssistantIds.contains(AssistantCard)) {
//            System.out.println("Chose an available assistant card");
//            AssistantCard = Integer.valueOf(InputParser.getLine());
//        }
//        ChooseAssistantResponse message = new ChooseAssistantResponse(nickname,AssistantCard);
//        serverHandler.sendCommandMessage(message);
//        this.waiting();
//
//
//
//
//
//    }
//

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
    public void printBoard(BoardData boardData) {
        setBoard(boardData);
        printBoard();
    }

    private void printBoard(){
        System.out.print("\nBOARD DATA: \n");
        IsleCircleData circleData = board.getGameBoard().getIsleCircle();
        List<IsleData> isles = circleData.getIsles();
        ArrayList<CloudData> clouds = board.getGameBoard().getClouds();
        Map<String, PlayerBoardData> playerData = board.getPlayerBoards();
        Integer motherNaturePosition = board.getGameBoard().getMotherNaturePosition();
        System.out.print("\nIsles: \n");
        isles.forEach(isleData -> System.out.print("students: "+isleData.getStudentMap()+"\n"+ "ban: "+isleData.getBanCounter()+"\n"));

        System.out.print("\nMotherNaturePosition: "+motherNaturePosition+"\n");


        System.out.print("\nClouds: \n");
        clouds.forEach(cloud -> System.out.print("students: "+cloud.getStudentMap()+"\n"+ "side: "+cloud.getSide()+"\n"));

        System.out.print("\nPlayers: \n");
        playerData.entrySet().stream().forEach(this::printPlayer);
    }



    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {
        Map<String,PlayerBoardData> playerData = board.getPlayerBoards();
        printDeck2(playerData.get(nickname));
        System.out.println("Choose Assistant Card between: "+availableAssistantIds);

        int chosenAssistant = Integer.parseInt(InputParser.getLine());

        while(!availableAssistantIds.contains(chosenAssistant)) {
            System.out.println("not valid index. Choose again:");
            chosenAssistant = Integer.parseInt(InputParser.getLine());
        }
        System.out.printf("chosen Assistant: %d\n", chosenAssistant);
        ChooseAssistantResponse message = new ChooseAssistantResponse(nickname,chosenAssistant);
        //mando messaggio al server
        serverHandler.sendCommandMessage(message);

    }

    @Override
    public void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability) {

        printBoard();
        PawnColour selectedColour = selectStudentFromEntrance();
        MessageFromClientToServer toReturnMessage = null;

        if(hallColourAvailability.get(selectedColour)){
            while(toReturnMessage == null){
                System.out.println("Choose a destination for student movement between Hall(h) and Isles(i) : ");
                String dest = InputParser.getLine();
                switch (dest) {
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
        serverHandler.sendCommandMessage(toReturnMessage);
    }

    private PawnColour selectStudentFromEntrance() {
        PawnColour selectedStudent = null;
        while(selectedStudent == null) {
            System.out.print("Choose student to move: (r = red, b = blue, w = white, g = green, p = pink, y = yellow ");
            showStudentsInEntrance();
            String colour = InputParser.getLine();
            switch (colour) {
                case "r":
                    selectedStudent = PawnColour.RED;
                    break;
                case "g":
                    selectedStudent = PawnColour.GREEN;
                    break;
                case "b":
                    selectedStudent = PawnColour.BLUE;
                    break;
                case "y":
                    selectedStudent = PawnColour.YELLOW;
                    break;
                case "p":
                    selectedStudent = PawnColour.PINK;
                    break;
                default:
                    selectedStudent = null;
            }
        }
        return selectedStudent;
    }



    private void printPlayer(Map.Entry<String, PlayerBoardData> player){
        System.out.print("Player "+player.getKey()+": "
                +   "\nEntrance: "+player.getValue().getEntrance()
                +   "\nHall: "+player.getValue().getHall()
                +   "\nTowers: "+player.getValue().getTowerCounter()+"\n");
        printDeck(player.getValue());
    }

    private void printDeck(PlayerBoardData playerData){
        System.out.print("\nDeck: \n");
        playerData.getDeck().entrySet().forEach(card -> System.out.print("card "+card.getKey()+": "+
                "\nvalue: "+card.getValue().getValue()
                +"\nmovement: "+card.getValue().getMovement()+"\n"));
    }
    private void printDeck2(PlayerBoardData player) {
        GraphicalCards cards = new GraphicalCards();
        System.out.print("\nDeck: \n");
        player.getDeck().entrySet().forEach(card -> cards.printCardVertical(card.getKey(), card.getValue().getValue(), card.getValue().getMovement()));
        //cards.printCardHorizontal(player.getDeck());
    }
}
