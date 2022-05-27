package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.utilities.InputParser;
import it.polimi.ingsw.client.view.cli.graphics.GraphicalCards;
import it.polimi.ingsw.client.view.cli.graphics.GraphicalStudents;
import it.polimi.ingsw.client.view.cli.graphics.Logo;
import it.polimi.ingsw.client.view.cli.graphics.Waiting;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.server.model.player.playerBoard.Entrance;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Predicate;

public class CLI implements ViewInterface {

    private static final String DEFAULT_ADDRESS = "127.0.0.1";
    private static final String DEFAULT_PORT = "1234";
    private boolean isSet = false;


    public String getPort() {
        return port;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public String getGameMode() {
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
    private String gameMode;
    private String nickname;
    private Integer numPlayer;

    public static void main(String[] args) {
        CLI cli= new CLI();
        cli.initCLI();
        cli.waiting();
    }

    private void initCLI() {
        Logo.printLogo();
        askConnectionParameters();
        nicknameRequest();
        gameModeRequest();
        numberOfPlayersRequest();
    }


    // *********************************************************************  //
    //                               LOGIN                                    //
    // *********************************************************************  //

    //ask for ip and port
    @Override
    public void askConnectionParameters() {

        System.out.println("Enter the server's IP address or d (default configuration): ");
        IPAddress = InputParser.getLine();

        while(IPAddress.equals("")) {
            System.out.println("Be sure to type something");
            IPAddress = InputParser.getLine();
        }

        if (IPAddress.equals("d")){
            IPAddress = DEFAULT_ADDRESS;
            port = DEFAULT_PORT;
            System.out.printf("IPAddress: %s \nPort: %s\n", IPAddress, port);
            return;
        }

        System.out.println("Enter the port you want to connect to: (enter an integer between 1024 and 65535)" );
        port = InputParser.getLine();
        while(port.equals("")) {
            System.out.println("Be sure to type something");
            port = InputParser.getLine();
        }
        System.out.printf("IPAddress: %s \nPort: %s\n", IPAddress, port);
    }
    //asks to chose nickname
    @Override
    public void nicknameRequest() {
        System.out.println("Insert your nickname (be sure to insert only valid characters (A-Z, a-z, 0-9):");

        nickname = InputParser.getLine();

        while(nickname.equalsIgnoreCase("")) {
            System.out.println("Be sure to type something");
            nickname = InputParser.getLine();
        }
        System.out.printf("Nickname selected: %s\n", nickname);
    }

    //asks game mode
    @Override
    public void gameModeRequest() {

        System.out.println("Insert a game mode, simple or expert mode: s | e");
        gameMode = InputParser.getLine();
        if (gameMode.equals("s")){
            System.out.println("You've chosen simple mode");
        }
        if (gameMode.equals("e")){
            System.out.println("You've chosen expert mode");
        }
        else while(gameMode.equals("")){
            System.out.println("Be sure to type something");
            gameMode = InputParser.getLine();
        }
    }
    //ask number of players
    @Override
    public void numberOfPlayersRequest(){
        System.out.println("Insert desired number of players: 2, 3 or 4");
        numPlayer = InputParser.getInt();

        while(numPlayer == null || (numPlayer < 2 || numPlayer > 4 ) ) {
            System.out.println("You must choose the correct number of players");
            numPlayer = InputParser.getInt();
        }

        System.out.printf("Number of players: %d\n", numPlayer);
        isSet = true;
    }

    //shows that you're waiting for the others
    @Override
    public void waiting() {
        Waiting.printWaiting();
        
        //if every player joined
        if(InputParser.getLine().equals("")){
            System.out.println("Initializing Game Board");
            activeGame();
        }

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
    public void showStudents() {
        //test per provare la stampa
        EnumMap<PawnColour,Integer> toAddMap = new EnumMap<PawnColour, Integer>(PawnColour.class);
        toAddMap.put(PawnColour.RED, 1);
        toAddMap.put(PawnColour.GREEN, 0);
        toAddMap.put(PawnColour.YELLOW, 3);
        toAddMap.put(PawnColour.PINK, 1);
        toAddMap.put(PawnColour.BLUE, 2);

        Entrance students = new Entrance(7);
        students.addStudents(toAddMap);
        GraphicalStudents draw = new GraphicalStudents();
        draw.drawStudents(students);
    }
    @Override
    public void selectStudentToMove() {
        showStudents();


    }
    @Override
    public void selectStudentDestination() {
    }
    
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
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
}
