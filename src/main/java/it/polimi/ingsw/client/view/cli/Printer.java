package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.cli.graphics.Colour;
import it.polimi.ingsw.client.view.cli.graphics.GraphicalCloud;
import it.polimi.ingsw.client.view.cli.graphics.GraphicalIsland;
import it.polimi.ingsw.client.view.cli.graphics.GraphicalStudents;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CloudData;
import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.network.data.PlayerBoardData;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.util.*;

public class Printer {

    private BoardData board;
    boolean gameMode;

    public Printer(boolean gameMode) {
        this.gameMode = gameMode;
    }


    public void printBoard(){
        System.out.print("\nBOARD DATA: \n");

        ArrayList<CloudData> clouds = board.getGameBoard().getClouds();
        Map<String, PlayerBoardData> playerData = board.getPlayerBoards();
        Integer motherNaturePosition = board.getGameBoard().getMotherNaturePosition();
        System.out.print("\nIsles: \n");
        printIsleCircle();

        System.out.print("\nMotherNaturePosition: "+motherNaturePosition+"\n");


        System.out.print("\nClouds: \n");
        printClouds();

        System.out.print("\nPlayers: \n");
        playerData.keySet().forEach(this::printPlayer);

        if(gameMode){
            System.out.print("\nCharacters: \n");
            printCharacters();
        }
    }

    public void printExpertOption(boolean expertMode) {
        if(gameMode){
            System.out.print("\nOr press 'c' to use a character effect\n");
        }
    }

    public void printPlayer(String playerName){
        System.out.print("\nPlayer "+playerName+": \n");
        showStudentsInEntrance(playerName);
        drawHall(playerName);
        drawTowers(playerName);
        printDeckHorizontal(playerName);
    }

    public void printDeck(PlayerBoardData playerData){
        System.out.print("\nDeck: \n");
        playerData.getDeck().entrySet().forEach(card -> System.out.print("card "+card.getKey()+": "+
                "\nvalue: "+card.getValue().getValue()
                +"\nmovement: "+card.getValue().getMovement()+"\n"));
    }


    public void printDeckHorizontal(String playerName) {
        HashMap<Integer, AssistantCard> deck = board.getPlayerBoards().get(playerName).getDeck();
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

    public void printClouds(){
        Set<Integer> cloudIndexes = new HashSet<>();

        for(int index = 0;index< board.getGameBoard().getClouds().size();index++){
            cloudIndexes.add(index);
        }
        printClouds(cloudIndexes);
    }


    public void printClouds(Set<Integer> availableCloudIndexes) {
        GraphicalCloud graphicClouds = new GraphicalCloud();
        graphicClouds.drawSetOfClouds(0,0,availableCloudIndexes,board);
    }

    public void printCharacters() {
        board.getCharacters().keySet().forEach(this::printCharacter);
    }

    public void printCharacter(int id){
        System.out.print("Character "+id+":\nprice"+board.getCharacters().get(id).getPrice());
        showStudents(board.getCharacters().get(id).getStudents());
    }

    public void printIsles(ArrayList<Integer> isleIndexes) {
        List<IsleData> toPrintIsles = new ArrayList<>();
        isleIndexes.forEach(index -> toPrintIsles.add(board.getGameBoard().getIsleCircle().getIsles().get(index)));
        GraphicalIsland graphicIsles = new GraphicalIsland();
        graphicIsles.drawIsles(toPrintIsles, board.getGameBoard().getMotherNaturePosition());
    }


    public void printIsleCircle() {
        GraphicalIsland graphicIsles = new GraphicalIsland();
        graphicIsles.drawIsleCircle(0,0,board);
    }


    public void printWinner(String winnerPlayer) {
    }

    public void printYouWon() {
    }

    public void showStudents(Map<PawnColour,Integer> studentMap) {
        //test per provare la stampa
        GraphicalStudents draw = new GraphicalStudents();
        draw.drawStudents(studentMap);
    }

    public void showStudentsInEntrance(String playerName) {
        System.out.print("Entrance:\n");
        showStudents(board.getPlayerBoards().get(playerName).getEntrance());
    }




    public void drawHall(String playerName) {
        Map<PawnColour,Integer> studentMap=board.getPlayerBoards().get(playerName).getHall();
        Set<PawnColour> professor = board.getPlayerBoards().get(playerName).getProfessors();
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

    public void drawTowers(String playerName) {
        System.out.print("Towers: \n");
        TowerColour Color = board.getPlayerBoards().get(playerName).getTowerColour();
        if(Color== it.polimi.ingsw.shared.enums.TowerColour.WHITE)
            System.out.print(Colour.ANSI_BRIGHT_WHITE.getCode());
        if(Color== it.polimi.ingsw.shared.enums.TowerColour.BLACK)
            System.out.print(Colour.ANSI_BLACK.getCode());
        if(Color== it.polimi.ingsw.shared.enums.TowerColour.GREY)
            System.out.print(Colour.ANSI_BRIGHT_BLACK.getCode());
        int towercounter = board.getPlayerBoards().get(playerName).getTowerCounter();
        for (int i = 0; i < towercounter; i++) {
            System.out.print("♖ ");
        }
    }

    public void setBoard(BoardData board) {
        this.board = board;
    }

}
