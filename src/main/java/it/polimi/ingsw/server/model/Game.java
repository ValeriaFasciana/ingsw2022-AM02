package it.polimi.ingsw.server.model;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.characters.CharacterCard;
import it.polimi.ingsw.server.model.player.Player;

import java.io.IOException;
import java.util.*;


public class Game {

    private Map<String, Player> players;
    private GameBoard gameBoard;
    private Integer numberOfPlayers;
    private State state;
    private Player currentPlayer;
    private Boolean expertVariant;
    private GameSettings settings;
    private Turn currentTurn;
    private Map<UUID, CharacterCard> characterMap;
    private Map<PawnColour,Professor> professorMap;
    private Deserializer deserializer = new Deserializer();
    private ArrayList<Player> playingOrder;
    private Player[] playerOrder;

    public Game(Map<String, Player> players, Integer numberOfPlayers,Boolean expertVariant) throws IOException {
        this.settings = deserializer.getSettings(numberOfPlayers);
        this.gameBoard = new GameBoard(settings.getNumberOfClouds(), settings.getNumberOfIslands(),settings.getStudentsInClouds());
        this.players = players;
        this.professorMap = new EnumMap<PawnColour, Professor>(PawnColour.class);
        if(expertVariant) initCharacterCards();
    }




    public void addPlayer(String nickname, TowerColour towerColour,Integer deckNumber) throws IOException {
        if(this.players.containsKey(nickname))return;
        Player newPlayer = new Player(nickname,this.settings.getStudentsInEntrance(),this.settings.getNumberOfTowersForPlayer(),towerColour,deckNumber);
        this.players.put(nickname,newPlayer);
    }


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void moveMotherNature(int isleIndex){
        this.gameBoard.moveMotherNatureTo(isleIndex);
    }

    public void getPlayableAssistants(){

    }

    public IsleGroup getMotherNaturePosition(){
        return this.gameBoard.getMotherNaturePosition();
    }


    private void initCharacterCards() {
        //Deserializator.getCharacters();
    }


    public void updateTurnWithPlayedAssistant(Player player) {
        this.currentTurn.updateWithPlayedAssistant(player);
    }

    public void startPlanningPhase(){
        this.currentTurn.setCurrentPhase(Phase.PLANNING);
        this.gameBoard.addStudentsToCloud(this.settings.getStudentsInClouds());
    }

    public void assignProfessors(){
        //for()
    }

    public Map<String, Player> getPlayers() {
        return players;
    }


    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public GameSettings getSettings(){
        return settings;
    }

    public Map<PawnColour, Professor> getProfessorMap() {
        return this.professorMap;
    }

    public void initPlayingOrder(){
        Random generator = new Random();
        this.playerOrder = (Player[]) players.values().toArray();
        int index = generator.nextInt(playerOrder.length);
        for(int i = index; i< playerOrder.length; i++){
            this.playingOrder.add(this.playerOrder[i]);
        }
        for(int j = 0; j< index; j++){
            this.playingOrder.add(this.playerOrder[j]);
        }
    }

    public void setPlanningOrder(){
        int index = Arrays.binarySearch(playerOrder,playingOrder.get(0));
        for(int i = index+1; i< playerOrder.length; i++){
            this.playingOrder.set(i,(this.playerOrder[i]));
        }
        for(int j = 0; j< index; j++){
            this.playingOrder.set(j,(this.playerOrder[j]));
        }
    }

    public void setActionOrder(){
        Collections.sort(this.playingOrder,
                Comparator.comparingInt(Player::getChosenAssistantValue));
        /*Missing comparator case when assistant card has same values*/

    }

    public ArrayList<Player> getPlayingOrder() {
        return this.playingOrder;
    }

    public int winningConditions(){
        int fine = 0;
        for (Player player : this.playingOrder) {
            if(player.getTowerCounter()==0)
                fine=1;
            if(player.getDeck().size()==0)
                fine=1;
        }
        if(this.gameBoard.getIsleCircle().getSize()<=3)
            fine=1;
        return fine;
    }

    public void actionPhase(){
        this.currentTurn.setCurrentPhase(Phase.ACTION);
        this.setActionOrder();
        for (Player player : this.playingOrder) {
            this.currentTurn.setCurrentPlayer(player);
            player.setState(State.MOVING_STUDENT);
            player.getAvailableDestination();


        }
    }
}
