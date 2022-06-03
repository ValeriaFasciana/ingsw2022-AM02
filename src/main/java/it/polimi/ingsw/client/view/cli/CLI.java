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

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }




    private ServerHandler serverHandler;


    public String getPort() {
        return port;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public boolean getGameMode() {
        return gameMode;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getNumPlayer() {
        return numPlayer;
    }

    private String port;
    private String IPAddress;
    private boolean gameMode;
    private String nickname;
    private Integer numPlayer;

    public static void main(String[] args) {
        CLI cli= new CLI();
        cli.initCLI();
        cli.waiting();
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
                    selectCard();
                    break;
                case "m":
                    selectStudentToMove();
                    break;
                case "sd":
                    selectStudentDestination();
                    break;
                case "md":
                    selectMotherNatureDestination();
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
    @Override
    public void selectMotherNatureDestination() {
    }

    @Override
    public void selectCard() {
        int value = 0;
        int movement = 0;
        System.out.println("Choose Assistant card to play: ");
        int id = InputParser.getInt();

        //test per provare la stampa
        if(id == 2) {
            value = 2;
            movement = 2;
        }
        GraphicalCards card = new GraphicalCards();
        card.printCard(value, movement);

    }

    @Override
    public void selectStudentToMove() {

    }

    public void showStudents(Map<PawnColour,Integer> studentMap) {
        //test per provare la stampa
        GraphicalStudents draw = new GraphicalStudents();
        draw.drawStudents(studentMap);
    }

    public void showStudentsInEntrance() {
        System.out.print("ENTRANCE: ");
        showStudents(board.getPlayerBoards().get(nickname).getEntrance());
    }
    @Override
    public void selectStudentDestination() {
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

    @Override
    public void moveStudent(Map<PawnColour, Boolean> hallColourAvailability) {
        System.out.println("Chose student color:");
        PawnColour color;
        while(true) {
            try {
                color = PawnColour.valueOf(InputParser.getLine().toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Chose a correct color:");
            }
        }

        if(!hallColourAvailability.get(color)){
            System.out.println("Chose student destination: Isle or Hall");
            String destination = InputParser.getLine();
            while((!(destination.equals("Isle")))&&(!(destination.equals("Hall")))) {
                System.out.println("Chose a correct destination:");
                destination = InputParser.getLine();
            }
            if(destination.equals("Hall")){
                MoveStudentToHallResponse message = new MoveStudentToHallResponse(nickname,color);
                serverHandler.sendCommandMessage(message);
                return;
            }
        }
        System.out.println("Chose isle:");
        int isledestination = Integer.parseInt(InputParser.getLine());
        MoveStudentToIsleResponse message = new MoveStudentToIsleResponse(nickname,isledestination,color);
        serverHandler.sendCommandMessage(message);



    }

    @Override
    public void askAssistantCard(Set<Integer> availableAssistantIds) {
        System.out.println("Assistant card:");
        for (Integer i : availableAssistantIds) {
            System.out.println(i);
        }
        Integer AssistantCard = Integer.valueOf(InputParser.getLine());
        while(!availableAssistantIds.contains(AssistantCard)) {
            System.out.println("Chose an available assistant card");
            AssistantCard = Integer.valueOf(InputParser.getLine());
        }
        ChooseAssistantResponse message = new ChooseAssistantResponse(nickname,AssistantCard);
        serverHandler.sendCommandMessage(message);
        this.waiting();





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
        System.out.println("Choose a destination for student movement between Hall(h) and Isles(i) : ");
        String dest = InputParser.getLine();
        while(!dest.equalsIgnoreCase("")) {
            MessageFromClientToServer toReturnMessage = null;
            PawnColour selectedPawn = selectStudentFromEntrance();
            switch (dest) {
                case "h":
                    toReturnMessage = new MoveStudentToHallResponse(nickname,selectedPawn);
                    break;
                case "i":
                    int selectedIsland = 5; //selectStudentFromIsle();
                    toReturnMessage = new MoveStudentToIsleResponse(nickname,selectedIsland,selectedPawn);
                    break;
                default:
            }
            serverHandler.sendCommandMessage(toReturnMessage);
        }
    }

    private PawnColour selectStudentFromEntrance() {

        System.out.print("Choose student to move: (r = red, b = blue, w = white, g = green, p = pink, y = yellow ");
        showStudentsInEntrance();
        String colour = InputParser.getLine();
        PawnColour selectedStudent = null;
        while(!colour.equalsIgnoreCase("")) {

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
                default: selectedStudent = null;
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
}
