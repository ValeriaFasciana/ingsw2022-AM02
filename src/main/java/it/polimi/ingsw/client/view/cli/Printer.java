package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.view.Constants;
import it.polimi.ingsw.client.view.cli.graphics.*;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.network.data.PlayerBoardData;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.io.*;
import java.util.*;

public class Printer {

    private BoardData board;
    private BufferedWriter printWriter;


    public Printer() {
        printWriter = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public void printBoard(String nickname){
        writeln("\nBOARD :");
        Integer motherNaturePosition = board.getGameBoard().getMotherNaturePosition();
        writeln("\nROUND NUMBER: "+board.getRoundData().getRoundNumber());
        writeln("\nISLES:");
        printIsleCircle();

        writeln("\nMOTHER NATURE POSITION: "+motherNaturePosition);


        writeln("\nCLOUDS :");
        printClouds();

        writeln("STUDENTS IN BAG: "+board.getGameBoard().getStudentsInBag());

        write("\nPLAYERS: \n");
        printPlayers(nickname);

        if(board.isExpertMode()){
            write("\nCHARACTER CARDS: \n");
            printCharacters();
        }
        if(board.getRoundData().isLastRound()){
            write("\nTHIS IS LAST ROUND!!!!!!!!!!!!!!!!!!!!!!!!!!!!! \n");
        }


    }

    public void printExpertOption(String playerName) {
        if(board.isExpertMode() && !board.getPlayerBoards().get(playerName).hasPlayedCharacter()){
            write("\nOr press 'c' to use a character effect\n");
        }
    }

    public void printPlayers(String nickname){
        Map<String, PlayerBoardData> playerData = board.getPlayerBoards();
        playerData.entrySet().stream().filter(player -> !player.getKey().equals(nickname)).forEach(player ->printPlayer(player.getKey()));
        printPlayer(nickname);

    }

    public void printPlayer(String playerName){
        write("\nPlayer "+playerName+": \n");
        write("\nCoins: "+board.getPlayerBoards().get(playerName).getCoins()+"\n");
        showStudentsInEntrance(playerName);
        drawHall(playerName);
        drawTowers(playerName);
        printDeckHorizontal(playerName);
    }

    public void printDeck(PlayerBoardData playerData){
        write("\nDeck: \n");
        playerData.getDeck().entrySet().forEach(card -> write("card "+card.getKey()+": "+
                "\nvalue: "+card.getValue().getValue()
                +"\nmovement: "+card.getValue().getMovement()+"\n"));
    }


    public void printDeckHorizontal(String playerName) {
        HashMap<Integer, AssistantCard> deck = board.getPlayerBoards().get(playerName).getDeck();
        write(Colour.ANSI_GREEN.getCode()+"\nDeck: \n");
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
        write(primariga + "\n"+secondariga + "\n"+terzariga + "\n"+quartariga + "\n"+quintariga + "\n"+sestariga + "\n");
        write(Colour.ANSI_DEFAULT.getCode());
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

    public void printCharacter(Integer characterId){
       CharacterCardData cardData = board.getCharacters().get(characterId);
       GraphicalCard graphicalCards = new GraphicalCard();
       graphicalCards.drawCard(0,0,characterId,cardData.getStudents(),cardData.getPrice());
       writeln("\nDescription : " +  cardData.getDescription()+"\n\n");
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
        write(Colour.ANSI_DEFAULT.getCode());
    }

    public void showStudentsInEntrance(String playerName) {
        write("Entrance:\n");
        showStudents(board.getPlayerBoards().get(playerName).getEntrance());
        write(Colour.ANSI_DEFAULT.getCode());
    }




    public void drawHall(String playerName) {
        Map<PawnColour,Integer> studentMap=board.getPlayerBoards().get(playerName).getHall();
        Set<PawnColour> professor = board.getPlayerBoards().get(playerName).getProfessors();
        write("Hall:\n");
        write("Students:                                       Professor:\n");
        //green students
        int greenStudents = studentMap.get(PawnColour.GREEN);
        for (int i = 0; i<greenStudents; i++) {
            write(Colour.ANSI_GREEN.getCode() +  "["+Constants.STUDENT+"]");
        }
        for (int i = greenStudents; i<10; i++) {
            write(Colour.ANSI_GREEN.getCode() +  "[  ]");
        }
        if(professor.contains(PawnColour.GREEN)){
            write("                                [X]");
        }
        else{
            write("                                [ ]");
        }
        write("\n");
        //red students
        int redStudents = studentMap.get(PawnColour.RED);
        for (int i = 0; i<redStudents; i++) {
            write(Colour.ANSI_RED.getCode() +  "["+Constants.STUDENT+"]");
        }
        for (int i = redStudents; i<10; i++) {
            write(Colour.ANSI_RED.getCode() + "[  ]");
        }
        if(professor.contains(PawnColour.RED)){
            write("                                [X]");
        }
        else{
            write("                                [ ]");
        }
        write("\n");
        //yellow students
        int yellowStudents = studentMap.get(PawnColour.YELLOW);
        for (int i = 0; i<yellowStudents; i++) {
            write(Colour.ANSI_YELLOW.getCode() +  "["+Constants.STUDENT+"]");
        }
        for (int i = yellowStudents; i<10; i++) {
            write(Colour.ANSI_YELLOW.getCode() + "[  ]");
        }
        if(professor.contains(PawnColour.YELLOW)){
            write("                                [X]");
        }
        else{
            write("                                [ ]");
        }
        write("\n");
        //pink students
        int pinkStudents = studentMap.get(PawnColour.PINK);
        for (int i = 0; i<pinkStudents; i++) {
            write(Colour.ANSI_PURPLE.getCode() + "["+Constants.STUDENT+"]");
        }
        for (int i = pinkStudents; i<10; i++) {
            write(Colour.ANSI_PURPLE.getCode() + "[  ]");
        }
        if(professor.contains(PawnColour.PINK)){
            write("                                [X]");
        }
        else{
            write("                                [ ]");
        }
        write("\n");
        //blue students
        int blueStudents = studentMap.get(PawnColour.BLUE);
        for (int i = 0; i<blueStudents; i++) {
            write(Colour.ANSI_BLUE.getCode() +  "["+Constants.STUDENT+"]");
        }
        for (int i = blueStudents; i<10; i++) {
            write(Colour.ANSI_BLUE.getCode() + "[  ]");
        }
        if(professor.contains(PawnColour.BLUE)){
            write("                                [X]");
        }
        else{
            write("                                [ ]");
        }
        write("\n");
        write(Colour.ANSI_DEFAULT.getCode());
    }

    public void drawTowers(String playerName) {
        write("Towers: \n");
        TowerColour Color = board.getPlayerBoards().get(playerName).getTowerColour();
        if(Color== TowerColour.WHITE)
            write(Colour.ANSI_BRIGHT_WHITE.getCode());
        if(Color== TowerColour.BLACK)
            write(Colour.ANSI_BRIGHT_BLUE.getCode());
        if(Color== TowerColour.GREY)
            write(Colour.ANSI_BRIGHT_PURPLE.getCode());
        int towercounter = board.getPlayerBoards().get(playerName).getTowerCounter();
        for (int i = 0; i < towercounter; i++) {
            write("♖ ");
        }
        write(Colour.ANSI_DEFAULT.getCode());

    }

    public void setBoard(BoardData board) {
        this.board = board;
    }

    public void write(String string){
        try{
            printWriter.write(string);
            printWriter.flush();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }
    public void writeln(String string){
        try{
            printWriter.write(string);
            printWriter.newLine();
            printWriter.flush();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void printWaiting() {
        writeln(Colour.ANSI_BLUE.getCode());
        writeln(
                "██            ██    ████      ██   ██████████   ██   ██      ██   ██████████   \n" +
                " ██          ██    ██  ██     ██       ██       ██   ██ ██   ██   ██           \n" +
                "  ██   ██   ██    ████████    ██       ██       ██   ██  ██  ██   ██   █████   \n" +
                "   ██ ████ ██    ██      ██   ██       ██       ██   ██   ██ ██   ██      ██   \n" +
                "    ██    ██    ██        ██  ██       ██       ██   ██    ████   ██████████   \n");
        writeln("Waiting for other players...");
        write(Colour.ANSI_DEFAULT.getCode());
    }
    public void printLogo() {
        write("\033[H\033[2J");
        write("\033[H\033[3J");
        writeln(Colour.ANSI_BLUE.getCode());
        writeln(
                "███████    ███████    ██      ██████      ██        ██   ██████████   ██      ██   ███████     \n" +
                "██         ██   ██    ██    ██      ██    ██ ██     ██       ██        ██    ██    ██          \n" +
                "████       ████       ██   ██ ██████ ██   ██   ██   ██       ██         ██  ██     ███████     \n" +
                "██         ██  ███    ██   ██        ██   ██     ██ ██       ██           ██            ██     \n" +
                "███████    ██    ███  ██   ██        ██   ██        ██       ██           ██       ███████     \n");
        writeln("Welcome to Eriantys boardgame");
        write(Colour.ANSI_DEFAULT.getCode());

    }

}
