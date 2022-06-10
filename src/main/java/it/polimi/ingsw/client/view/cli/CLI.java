package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.utilities.InputParser;
import it.polimi.ingsw.client.view.cli.graphics.*;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.PlayerBoardData;
import it.polimi.ingsw.network.data.CloudData;
import it.polimi.ingsw.network.data.IsleCircleData;
import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.server.model.board.Cloud;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

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
        while((!s.equals("s")&&!s.equals("e"))){
            System.out.println("Be sure to type s or e");
            s = InputParser.getLine();
        }
        if (s.equals("s")){
            gameMode = false;
            System.out.println("You've chosen simple mode");
        }
        if (s.equals("e")){
            gameMode = true;
            System.out.println("You've chosen expert mode");
        }

        return gameMode;
    }
    //ask number of players
    public int numberOfPlayersRequest(){
        System.out.println("Insert desired number of players: 2, 3 or 4");
        numPlayer = InputParser.getInt();

        while(numPlayer == null || (numPlayer < 2 || numPlayer > 4 ) ) {
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

    @Override
    public void endGame(String winnerPlayer) {
        if(winnerPlayer.equals(nickname)){
            printYouWon();
        }else{
            printWinner(winnerPlayer);
        }
    }

    private void printWinner(String winnerPlayer) {
    }

    private void printYouWon() {
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
        System.out.print("Entrance:\n");
        showStudents(board.getPlayerBoards().get(nickname).getEntrance());
    }

    
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    //info solo per chi crea il gioco
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
    public void askUserInfo(boolean invalidName) {
        System.out.print("\nThe chosen nickname is already used by another player, try again...\n");
        askUserInfo();
    }


    //info per i player che si connettono alla lobby
    @Override
    public void askUserInfo() {
        String nickname = nicknameRequest();
        NicknameResponse message = new NicknameResponse((nickname));
        serverHandler.sendCommandMessage(message);
        this.waiting();
    }


    private int selectIsle() {
        System.out.print("Choose Isle Between: \n");
        printIsleCircle();
        int dest = Integer.parseInt(InputParser.getLine());
        while(dest<0 || dest > 11){
            System.out.print("choose valid index \n");
            dest = Integer.parseInt(InputParser.getLine());
        }
        return dest;
    }

    private void printIsleCircle() {
        GraphicalIsland graphicIsles = new GraphicalIsland();
        graphicIsles.drawIsleCircle(0,0,board);
    }

    @Override
    public void moveMotherNature(ArrayList<Integer> availableIsleIndexes) {
        printBoard();
        System.out.println("Chose mother nature destination:");
        for (Integer i : availableIsleIndexes) {
            System.out.println(i);
        }
        Integer mothernaturedestination = Integer.valueOf(InputParser.getLine());
        while(!availableIsleIndexes.contains(mothernaturedestination)) {
            System.out.println("\nChoose an available mother nature destination\n");
            mothernaturedestination = Integer.valueOf(InputParser.getLine());
        }
        MoveMotherNatureResponse message = new MoveMotherNatureResponse(nickname,mothernaturedestination);
        serverHandler.sendCommandMessage(message);
        this.waiting();
    }

    private void printIsles(ArrayList<Integer> isleIndexes) {
        List<IsleData> toPrintIsles = new ArrayList<>();
        isleIndexes.forEach(index -> toPrintIsles.add(board.getGameBoard().getIsleCircle().getIsles().get(index)));
        GraphicalIsland graphicIsles = new GraphicalIsland();
        graphicIsles.drawIsles(toPrintIsles, board.getGameBoard().getMotherNaturePosition());
    }

    @Override
    public void askCloud(Set<Integer> availableCloudIndexes) {

        System.out.println("\nChoose cloud between: \n");

        printClouds(availableCloudIndexes);
        int chosenCloud = Integer.parseInt(InputParser.getLine());

        while(!availableCloudIndexes.contains(chosenCloud)) {
            System.out.println("not valid cloud. Choose again:\n");
            chosenCloud = Integer.parseInt(InputParser.getLine());
        }
        System.out.printf("chosen Cloud: %d\n", chosenCloud);
        ChooseCloudResponse message = new ChooseCloudResponse(nickname,chosenCloud);
        serverHandler.sendCommandMessage(message);
        this.waiting();

    }


    private void printClouds(){
        Set<Integer> cloudIndexes = new HashSet<>();

        for(int index = 0;index< board.getGameBoard().getClouds().size();index++){
            cloudIndexes.add(index);
        }
        printClouds(cloudIndexes);
    }
    private void printClouds(Set<Integer> availableCloudIndexes) {
        GraphicalCloud graphicClouds = new GraphicalCloud();
        graphicClouds.drawSetOfClouds(0,0,availableCloudIndexes,board);
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

        ArrayList<CloudData> clouds = board.getGameBoard().getClouds();
        Map<String,PlayerBoardData> playerData = board.getPlayerBoards();
        Integer motherNaturePosition = board.getGameBoard().getMotherNaturePosition();
        System.out.print("\nIsles: \n");
        printIsleCircle();

        System.out.print("\nMotherNaturePosition: "+motherNaturePosition+"\n");


        System.out.print("\nClouds: \n");
        printClouds();

        System.out.print("\nPlayers: \n");
        playerData.entrySet().stream().forEach(this::printPlayer);
    }



    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {
        printBoard();
        Map<String,PlayerBoardData> playerData = board.getPlayerBoards();
        printDeckHorizontal();
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
        this.waiting();

    }

    @Override
    public void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability) {

        printBoard();
        PawnColour selectedColour = selectStudentFromEntrance();
        MessageFromClientToServer toReturnMessage = null;

        if(hallColourAvailability.get(selectedColour)){
            while(toReturnMessage == null){
                System.out.println("Choose a destination for student movement between Hall(h) and Isles(i) : \n");
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
            System.out.print("Choose student to move: (r = red, b = blue, g = green, p = pink, y = yellow) \n");
            showStudentsInEntrance();
            String colour = InputParser.getLine();
            switch (colour) {
                case "r":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.RED)>0){
                        selectedStudent = PawnColour.RED;
                    } else{System.out.print("There are no student of that color in the entrance. Chose again\n");}
                    break;
                case "g":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.GREEN)>0){
                        selectedStudent = PawnColour.GREEN;
                    } else{System.out.print("There are no student of that color in the entrance. Chose again\n");}
                    break;
                case "b":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.BLUE)>0){
                        selectedStudent = PawnColour.BLUE;
                    }else{System.out.print("There are no student of that color in the entrance. Chose again\n");}
                    break;
                case "y":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.YELLOW)>0){
                        selectedStudent = PawnColour.YELLOW;
                    } else{System.out.print("There are no student of that color in the entrance. Chose again\n");}
                    break;
                case "p":
                    if(board.getPlayerBoards().get(nickname).getEntrance().get(PawnColour.PINK)>0){
                        selectedStudent = PawnColour.PINK;
                    } else{System.out.print("There are no student of that color in the entrance. Chose again\n");}
                    break;
                default:
                    selectedStudent = null;
                    break;
            }
        }
        return selectedStudent;
    }



    private void printPlayer(Map.Entry<String, PlayerBoardData> player){
        System.out.print("\nPlayer "+player.getKey()+": \n");
        showStudentsInEntrance();
        drawHall();
        drawTowers();
        printDeckHorizontal();
    }

    private void printDeck(PlayerBoardData playerData){
        System.out.print("\nDeck: \n");
        playerData.getDeck().entrySet().forEach(card -> System.out.print("card "+card.getKey()+": "+
                "\nvalue: "+card.getValue().getValue()
                +"\nmovement: "+card.getValue().getMovement()+"\n"));
    }


    public void printDeckHorizontal() {
        HashMap<Integer, AssistantCard> deck = board.getPlayerBoards().get(nickname).getDeck();
        System.out.print(Colour.ANSI_GREEN.getCode()+"\nDeck: \n");
        String primariga = "";
        String secondariga= "";
        String terzariga = "";
        String quartariga= "";
        String quintariga= "";
        String sestariga= "";
        for(Map.Entry<Integer, AssistantCard> card : deck.entrySet()){
            primariga = primariga + "Card: "+card.getKey()+"   ";
            secondariga = secondariga + "█████████ ";
            if(card.getValue().getValue()>=10) {
                terzariga = terzariga +"█ "+card.getValue().getValue()+"  "+card.getValue().getMovement()+" █ ";
            }
            else{
                terzariga = terzariga +"█ "+card.getValue().getValue()+"   "+card.getValue().getMovement()+" █ ";
            }
            quartariga = quartariga +"█       █ ";
            quintariga = quintariga +"█       █ ";
            sestariga = sestariga + "█████████ ";

        }
        System.out.print(primariga + "\n"+secondariga + "\n"+terzariga + "\n"+quartariga + "\n"+quintariga + "\n"+sestariga + "\n");

    }

    public void drawHall() {
        Map<PawnColour,Integer> studentMap=board.getPlayerBoards().get(nickname).getHall();
        Set<PawnColour> professor = board.getPlayerBoards().get(nickname).getProfessors();
        System.out.print("Hall:\n");
        System.out.println("Students:                                       Professor:");
        //green students
        int greenStudents = studentMap.get(PawnColour.GREEN);
        for (int i = 0; i<greenStudents; i++) {
            System.out.print(Colour.ANSI_GREEN.getCode() + "[♟]");
        }
        for (int i = greenStudents; i<10; i++) {
            System.out.print(Colour.ANSI_GREEN.getCode() + "[  ]");
        }
        if(professor.contains(PawnColour.GREEN)){
            System.out.println("                                [X]");
        }
        else{
            System.out.println("                                [ ]");
        }
        System.out.println("");
        //red students
        int redStudents = studentMap.get(PawnColour.RED);
        for (int i = 0; i<redStudents; i++) {
            System.out.print(Colour.ANSI_RED.getCode() + "[♟]");
        }
        for (int i = redStudents; i<10; i++) {
            System.out.print(Colour.ANSI_RED.getCode() + "[  ]");
        }
        if(professor.contains(PawnColour.RED)){
            System.out.println("                                [X]");
        }
        else{
            System.out.println("                                [ ]");
        }
        System.out.println("");
        //yellow students
        int yellowStudents = studentMap.get(PawnColour.YELLOW);
        for (int i = 0; i<yellowStudents; i++) {
            System.out.print(Colour.ANSI_YELLOW.getCode() + "[♟]");
        }
        for (int i = yellowStudents; i<10; i++) {
            System.out.print(Colour.ANSI_YELLOW.getCode() + "[  ]");
        }
        if(professor.contains(PawnColour.YELLOW)){
            System.out.println("                                [X]");
        }
        else{
            System.out.println("                                [ ]");
        }
        System.out.println("");
        //pink students
        int pinkStudents = studentMap.get(PawnColour.PINK);
        for (int i = 0; i<pinkStudents; i++) {
            System.out.print(Colour.ANSI_PURPLE.getCode() + "[♟]");
        }
        for (int i = pinkStudents; i<10; i++) {
            System.out.print(Colour.ANSI_PURPLE.getCode() + "[  ]");
        }
        if(professor.contains(PawnColour.PINK)){
            System.out.println("                                [X]");
        }
        else{
            System.out.println("                                [ ]");
        }
        System.out.println("");
        //blue students
        int blueStudents = studentMap.get(PawnColour.BLUE);
        for (int i = 0; i<blueStudents; i++) {
            System.out.print(Colour.ANSI_BLUE.getCode() + "[♟]");
        }
        for (int i = blueStudents; i<10; i++) {
            System.out.print(Colour.ANSI_BLUE.getCode() + "[  ]");
        }
        if(professor.contains(PawnColour.BLUE)){
            System.out.println("                                [X]");
        }
        else{
            System.out.println("                                [ ]");
        }
        System.out.println("");
    }

    public void drawTowers() {
        System.out.print("Towers: \n");
        TowerColour Color = board.getPlayerBoards().get(nickname).getTowerColour();
        if(Color== it.polimi.ingsw.shared.enums.TowerColour.WHITE)
            System.out.print(Colour.ANSI_BRIGHT_WHITE.getCode());
        if(Color== it.polimi.ingsw.shared.enums.TowerColour.BLACK)
            System.out.print(Colour.ANSI_BLACK.getCode());
        if(Color== it.polimi.ingsw.shared.enums.TowerColour.GREY)
            System.out.print(Colour.ANSI_BRIGHT_BLACK.getCode());
        int towercounter = board.getPlayerBoards().get(nickname).getTowerCounter();
        for (int i = 0; i < towercounter; i++) {
            System.out.print("♖ ");
        }
    }
}
