package it.polimi.ingsw.server.model;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.characters.CharacterCard;
import it.polimi.ingsw.server.model.player.Player;

import java.io.IOException;
import java.util.*;


public class Game {

    private Map<String,Player> players;
    private GameBoard gameBoard;
    private State state;
    private Boolean expertVariant;
    private GameSettings settings;
    private Round currentRound;
    private Map<PawnColour,Professor> professorMap;
    private static Deserializer deserializer = new Deserializer();
    private static final Map<Integer,AssistantCard> assistantDeck;
    static {
        Map<Integer,AssistantCard> tempDeck = null;
        try {
            tempDeck = deserializer.getAssistantDeck();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assistantDeck = tempDeck;
    }

    ;

    public Game(Map<String, Player> players, Integer numberOfPlayers,Boolean expertVariant) throws IOException {
        this.settings = deserializer.getSettings(numberOfPlayers);
        this.gameBoard = new GameBoard(settings.getNumberOfClouds(), settings.getNumberOfIslands(),settings.getStudentsInClouds());
        this.players = players;
        this.professorMap = new EnumMap<>(PawnColour.class);

        Player firstPlayer = (Player) players.values().toArray()[0];
        LinkedList<String> playerList = new LinkedList<>(players.keySet().stream().toList());
        this.currentRound = new Round(firstPlayer, playerList);
        if(expertVariant) initCharacterCards();
    }

    public void addPlayer(String nickname, TowerColour towerColour){
        if(this.players.containsKey(nickname))return;
        Player newPlayer = new Player(nickname,this.settings.getStudentsInEntrance(),this.settings.getNumberOfTowersForPlayer(),towerColour);
        this.players.put(nickname,newPlayer);
    }

    private void initCharacterCards() {
        //Deserializer.getCharacters();
    }

    public Set<Integer> getPlayableAssistants(){
        return currentRound.getPlayableAssistants();
    }

    public void playAssistantCard(Integer assistantId){
        AssistantCard playedAssistant = assistantDeck.get(assistantId);
        this.currentRound.updateWithPlayedAssistant(playedAssistant);
    }

    public void moveMotherNature(int isleIndex){
        boolean isBannedIsle = this.gameBoard.isIsleBanned(isleIndex);
        this.gameBoard.moveMotherNatureTo(isleIndex);
        if(!isBannedIsle){
            calculateInfluence(isleIndex);
        }
    }

    public void calculateInfluence(int isleIndex) {
        calculateInfluence(isleIndex, Optional.empty());
    }
    public void calculateInfluence(int isleIndex,Optional<PawnColour> excludedColour){
        //get influential colours: only the colours that are present on the island and also have an associated professor
        Set<PawnColour> professorColours = this.professorMap.keySet();
        EnumMap<PawnColour,Integer> studentMapOnIsle = this.gameBoard.getStudentsOnIsle(isleIndex);
        Set<PawnColour> influentialColours = new HashSet<>(studentMapOnIsle.keySet());
        influentialColours.retainAll(professorColours);
        //remove excluded colour from influential colours (used for character effect)
        excludedColour.ifPresent(influentialColours::remove);

        HashMap<TowerColour,Integer> playerInfluenceMap = new HashMap<>();
        //for each influential colour, add influence to the player owning the respective professor
        for(PawnColour colour : influentialColours){
            TowerColour playerTowerColour = players.get(professorMap.get(colour).getPlayer()).getTowerColour();
            int playerInfluence = playerInfluenceMap.getOrDefault(playerTowerColour, 0);
            playerInfluenceMap.put(playerTowerColour,playerInfluence + studentMapOnIsle.get(colour));
        }
        if(!currentRound.excludeTowersFromInfluence()){
            TowerColour isleTowerColour = gameBoard.getIsleTowerColour(isleIndex);
            int isleSize = gameBoard.getIsleSize(isleIndex);
            playerInfluenceMap.put(isleTowerColour,playerInfluenceMap.getOrDefault(isleTowerColour,0) + isleSize );
        }
        playerInfluenceMap = currentRound.sumAdditionalInfluence(playerInfluenceMap);
        Optional<TowerColour> towerToPlace = getMostInfluentialPlayer(playerInfluenceMap);
        towerToPlace.ifPresent(towerColour -> this.gameBoard.placeTowerOnIsle(isleIndex, towerColour));
    }
    private Optional<TowerColour> getMostInfluentialPlayer(HashMap<TowerColour, Integer> playerInfluenceMap) {
        if(playerInfluenceMap.isEmpty())return Optional.empty();
        HashMap<TowerColour, Integer> sortedMap = new HashMap<>();
        playerInfluenceMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        Object[] influencePoints = sortedMap.values().toArray();
        if(influencePoints.length > 1 && Objects.equals(influencePoints[0], influencePoints[1]))return Optional.empty();  // if two players have the same influence return null
        Optional<TowerColour> firstPlayer;
        try{
            firstPlayer =  sortedMap.keySet().stream().findFirst();
        }catch (NullPointerException e){
            return Optional.empty();
        }
        return firstPlayer;
    }

    public void endCurrentPlayerTurn(){
        if(this.currentRound.isEnded()) {
            this.currentRound = this.currentRound.initNextRound(this.players);
            this.gameBoard.addStudentsToCloud(this.settings.getStudentsInClouds());
        }else{
            this.currentRound.setNextPlayer(this.players);
        }
    }

    public IsleGroup getMotherNaturePosition(){
        return this.gameBoard.getMotherNaturePosition();
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }


//    public int winningConditions(){
//        int fine = 0;
//        for (Player player : this.playingOrder) {
//            if(player.getTowerCounter()==0)
//                fine = 1;
//            if(player.getDeck().size()==0)
//                fine = 1;
//        }
//        if(this.gameBoard.getIsleCircle().getSize()<=3)
//            fine = 1;
//        return fine;
//    }
}
