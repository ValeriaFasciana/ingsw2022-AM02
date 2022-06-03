package it.polimi.ingsw.server.model.game;
import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.ActionVisitor;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.Phase;

import java.io.IOException;
import java.util.*;


public class Game implements GameInterface,ActionVisitor {

    private Map<String,Player> players;
    private GameBoard gameBoard;
    private Boolean expertVariant;
    private GameSettings settings;
    private Round currentRound;
    private EnumMap<PawnColour, Professor> professorMap;
    private static Deserializer deserializer = new Deserializer();
    private static final HashMap<Integer, AssistantCard> assistantDeck;
    private Optional<PawnColour> influenceExcludedColour = Optional.empty();

    private List<BoardUpdateListener> boardUpdateListeners = new ArrayList<>();

    static {
        Map<Integer,AssistantCard> tempDeck = new HashMap<>();
        try {
            tempDeck = deserializer.getAssistantDeck();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assistantDeck = (HashMap<Integer, AssistantCard>) tempDeck;
    }

    private static final HashMap<Integer,CharacterCard> characterMap;

    static {
        Map<Integer,CharacterCard> tempCharacters = new HashMap<>();
        try {
            tempCharacters = deserializer.getCharacters();
        } catch (IOException e) {
            e.printStackTrace();
        }
        characterMap = (HashMap<Integer, CharacterCard>) tempCharacters;
    }



    public Game(List<String> playerNames, Integer numberOfPlayers,Boolean expertVariant){
        this.settings = deserializer.getSettings(numberOfPlayers);
        this.gameBoard = new GameBoard(settings.getNumberOfClouds(), settings.getNumberOfIslands(),settings.getStudentsInClouds());
        this.players = initPlayers(playerNames);
        this.professorMap = initProfessorMap();
        this.expertVariant = expertVariant;
        Player firstPlayer = this.players.get(playerNames.get(0));
        List<String> playerList = this.players.keySet().stream().toList();
        this.currentRound = new Round(firstPlayer, playerList);
        if(Boolean.TRUE.equals(expertVariant)) initCharacterCards();
    }

    private EnumMap<PawnColour, Professor> initProfessorMap() {
        EnumMap<PawnColour, Professor> professorMap = new EnumMap<>(PawnColour.class);
        for(PawnColour colour : PawnColour.values()){
            professorMap.put(colour,new Professor());
        }
        return professorMap;
    }

    public void create(){
        notifyGameInit();
    }

    @Override
    public int getNumberOfPlayers() {
        return players.size();
    }

    @Override
    public Set<Integer> getAvailableClouds() {
        return gameBoard.getAvailableClouds();
    }

    @Override
    public GameSettings getSettings() {
        return this.settings;
    }

    private Map<String, Player> initPlayers(List<String> playerNames) {
        Map<String, Player> playerMap = new HashMap<>();
        for(String nickName : playerNames){
            Player newPlayer = new Player(nickName,settings.getStudentsInEntrance(),settings.getNumberOfTowersForPlayer(),assistantDeck);
            newPlayer.addStudentsToEntrance(gameBoard.getBag().pick(settings.getStudentsInEntrance()));
            playerMap.put(nickName,newPlayer);
        }

        assignTowerColours(playerMap);
        return playerMap;
    }

    private void assignTowerColours(Map<String, Player> playerMap) {
        playerMap.values().stream().toList().get(0).setTowerColour(TowerColour.BLACK);
        playerMap.values().stream().toList().get(1).setTowerColour(TowerColour.WHITE);
        if(playerMap.size() == 3){
            playerMap.values().stream().toList().get(2).setTowerColour(TowerColour.GREY);
        }
    }

//    public void addPlayer(String nickname){
//        if(this.players.containsKey(nickname))return;
//        Player newPlayer = new Player(nickname,this.settings.getStudentsInEntrance(),this.settings.getNumberOfTowersForPlayer(),assistantDeck);
//        this.players.put(nickname,newPlayer);
//    }

    private void initCharacterCards() {
        //Deserializer.getCharacters();
    }

    @Override
    public Set<Integer> getPlayableAssistants(){
        return currentRound.getPlayableAssistants();
    }


    @Override
    public void playAssistantCard(int assistantId){
        AssistantCard playedAssistant = assistantDeck.get(assistantId);
        this.currentRound.updateWithPlayedAssistant(playedAssistant);
        notifyBoardListeners();
    }

    @Override
    public void moveStudentToCurrentPlayerHall(PawnColour studentColour){
        Map<PawnColour,Integer> studentMap = new EnumMap<>(PawnColour.class);
        studentMap.put(studentColour,1);
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.addStudentToHall(studentColour);
        currentPlayer.removeStudentsFromEntrance(studentMap);
        this.professorMap = (EnumMap<PawnColour, Professor>) assignProfessorsToPlayer(currentPlayer);
        notifyBoardListeners();
    }



    public Map<PawnColour,Professor> assignProfessorsToPlayer(Player player){
        Map<PawnColour,Integer> studentsInHall = player.getBoard().getStudentsInHall();
        for(Map.Entry<PawnColour, Integer> studentEntry : studentsInHall.entrySet()){
            Integer studentCount = studentEntry.getValue();
            PawnColour colour = studentEntry.getKey();
            if(this.currentRound.checkProfessorAssignment(studentCount,professorMap.get(colour).getCounter())){
                professorMap.get(colour).setPlayer(player.getNickName());
                professorMap.get(colour).setCounter(studentCount);
            }
        }
        return professorMap;
    }

    private void notifyBoardListeners() {
        boardUpdateListeners.forEach(boardListener -> boardListener.onBoardUpdate(getBoardData()));
    }
    private void notifyGameInit() {
        boardUpdateListeners.forEach(boardListener -> boardListener.onGameInit(getBoardData()));
    }

    public void excludeColourFromInfluence(PawnColour colour){
        this.influenceExcludedColour = Optional.ofNullable(colour);
    }

    public void moveMotherNature(int isleIndex){
        boolean isBannedIsle = this.gameBoard.isIsleBanned(isleIndex);
        this.gameBoard.moveMotherNatureTo(isleIndex);
        if(!isBannedIsle){
            calculateInfluence(isleIndex,this.influenceExcludedColour);
            this.influenceExcludedColour = Optional.empty();
        }
    }

    public void calculateInfluence(int isleIndex,Optional<PawnColour> excludedColour){
        //get influential colours: only the colours that are present on the island and also have an associated professor
        Set<PawnColour> professorColours = getAvailableProfessors();
        if(professorColours.isEmpty())return;
        EnumMap<PawnColour,Integer> influentialStudentOnIsleMap = (EnumMap<PawnColour, Integer>) this.gameBoard.getStudentsOnIsle(isleIndex).entrySet().stream().filter(studentEntry ->studentEntry.getValue() >0);


        Set<PawnColour> influentialColours = influentialStudentOnIsleMap.keySet();
        influentialColours.retainAll(professorColours);
        if(influentialColours.isEmpty())return;
        //remove excluded colour from influential colours (used for character effect)
        excludedColour.ifPresent(influentialColours::remove);

        EnumMap<TowerColour,Integer> playerInfluenceMap = new EnumMap<>(TowerColour.class);
        //for each influential colour, add influence to the player owning the respective professor
        for(PawnColour colour : influentialColours){
            TowerColour playerTowerColour = players.get(professorMap.get(colour).getPlayer()).getTowerColour();
            int playerInfluence = playerInfluenceMap.getOrDefault(playerTowerColour, 0);
            playerInfluenceMap.put(playerTowerColour,playerInfluence + influentialStudentOnIsleMap.get(colour));
        }
        playerInfluenceMap = (EnumMap<TowerColour, Integer>) currentRound.sumAdditionalInfluence(playerInfluenceMap);
        TowerColour isleTowerColour = gameBoard.getIsleTowerColour(isleIndex);
        if(isleTowerColour!= null && !currentRound.excludeTowersFromInfluence()){
            int isleSize = gameBoard.getIsleSize(isleIndex);
            playerInfluenceMap.put(isleTowerColour,playerInfluenceMap.getOrDefault(isleTowerColour,0) + isleSize );
        }

        Optional<TowerColour> towerToPlace = getMostInfluentialPlayer(playerInfluenceMap);
        if(towerToPlace.isPresent() && towerToPlace.get() != isleTowerColour){
                this.gameBoard.placeTowerOnIsle(isleIndex, towerToPlace.get());
                this.gameBoard.manageIsleMerge(isleIndex);
                removeTowerFromPlayer(towerToPlace.get());
                addTowerToPlayer(isleTowerColour);
        }
    }

//    private Set<PawnColour> getInfluentialColoursOnIsle(int isleIndex) {
//        EnumMap<PawnColour,Integer> studentMapOnIsle = (EnumMap<PawnColour, Integer>) this.gameBoard.getStudentsOnIsle(isleIndex);
//        Set<PawnColour> influentialColours = new HashSet<>();
//        for(PawnColour colour : PawnColour.values()){
//            if(studentMapOnIsle.get(colour)>0){
//                influentialColours.add(colour);
//            }
//        }
//        return influentialColours;
//    }

    private Set<PawnColour> getAvailableProfessors() {
        Set<PawnColour> availableProfessors = new HashSet<>();
        for(PawnColour colour : PawnColour.values()){
            if( professorMap.get(colour).getPlayer()!= null){
                availableProfessors.add(colour);
            }
        }
        return availableProfessors;
    }

    private void addTowerToPlayer(TowerColour towerColour) {
        players.values()
                .stream().filter(player->towerColour.equals(player.getTowerColour()))
                .forEach(Player::addTower);
    }

    private void removeTowerFromPlayer(TowerColour towerColour) {
       players.values()
               .stream().filter(player->towerColour.equals(player.getTowerColour()))
               .forEach(Player::removeTower);
    }

    private Optional<TowerColour> getMostInfluentialPlayer(Map<TowerColour, Integer> playerInfluenceMap) {
        if(playerInfluenceMap.isEmpty())return Optional.empty();
        EnumMap<TowerColour, Integer> sortedMap = new EnumMap<>(TowerColour.class);
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

    @Override
    public void endCurrentPlayerTurn(){
        if(this.currentRound.isEnded()) {
            this.currentRound = this.currentRound.initNextRound(this.players);
            this.gameBoard.addStudentsToCloud(this.settings.getStudentsInClouds());
        }else{
            this.currentRound.setNextPlayer(this.players);
        }
    }

    @Override
    public Phase getRoundPhase() {
        return currentRound.getCurrentPhase();
    }

    @Override
    public List<Integer> getMotherNatureAvailableIslands() {
        Optional<AssistantCard> playedAssistant = getCurrentPlayer().getChosenAssistant();
        return playedAssistant.map(assistantCard -> gameBoard.getMotherNatureNextIslands(assistantCard.getValue())).orElse(null);
    }

    @Override
    public Map<PawnColour, Boolean> getPlayerHallAvailability(String playerName) {
        return players.get(playerName).getHallAvailability();
    }


    public Map<String, Player> getPlayers() {
        return players;
    }


    public Player getCurrentPlayer() {
        return this.currentRound.getCurrentPlayer();
    }

    @Override
    public String getCurrentPlayerName() {
        return this.currentRound.getCurrentPlayer().getNickName();
    }

    public Round getCurrentRound() {
        return this.currentRound;
    }

    @Override
    public void useAction(Action action){
        action.accept(this);
    }

    @Override
    public void setBanOnIsland(int isleIndex) {
        this.gameBoard.setBanOnIsle(isleIndex);
    }

    @Override
    public void emptyCloud(int cloudIndex) {
        Map<PawnColour,Integer> studentsOnCloud = this.gameBoard.getStudentsOnCloud(cloudIndex);
        getCurrentPlayer().addStudentsToEntrance(studentsOnCloud);
        gameBoard.emptyCloud(cloudIndex);
        notifyBoardListeners();
    }

    @Override
    public void playCharacterCard(int characterId) {
        CharacterCard card = characterMap.get(characterId);
        this.currentRound.setCurrentRuleSet(card.getRuleSet());
        card.increasePrice();
    }

    @Override
    public void moveStudentToIsle(PawnColour studentColour, int isleIndex) {
        getCurrentPlayer().removeStudentsFromEntrance(new EnumMap<>(Map.of(studentColour, 1)));
        gameBoard.addStudentToIsle(isleIndex,new EnumMap<>(Map.of(studentColour, 1)));
        notifyBoardListeners();
    }

    public BoardData getBoardData(){
        Map<String,PlayerBoardData> playerBoards = new HashMap<>();
        for( Map.Entry<String, Player> playerEntry : players.entrySet()){
            playerBoards.put(playerEntry.getKey(),playerEntry.getValue().getBoardData());
        }
        return new BoardData(playerBoards,gameBoard.getData());
    }

    public void addBoardUpdateListener(BoardUpdateListener listener){
        boardUpdateListeners.add(listener);
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
