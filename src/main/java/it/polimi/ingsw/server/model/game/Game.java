package it.polimi.ingsw.server.model.game;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.network.data.PlayerBoardData;
import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.controller.listeners.EndGameListener;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.action.ActionVisitor;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterEffect;
import it.polimi.ingsw.server.model.cards.characters.DefaultRuleSet;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.Phase;
import it.polimi.ingsw.shared.enums.TowerColour;

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
    private HashMap<Integer, AssistantCard> assistantDeck;
    private Optional<PawnColour> influenceExcludedColour = Optional.empty();
    private Random rand = new Random();
    private String winner;

    private List<BoardUpdateListener> boardUpdateListeners = new ArrayList<>();
    private List<EndGameListener> endGameListeners = new ArrayList<>();

    private Map<Integer,CharacterCard> characterMap = new HashMap<>();


    public Game(List<String> playerNames, Integer numberOfPlayers,Boolean expertVariant){
        try{
            this.settings = deserializer.getSettings(numberOfPlayers);
            this.expertVariant = expertVariant;
            this.assistantDeck = (HashMap<Integer, AssistantCard>) deserializer.getAssistantDeck();
            this.gameBoard = new GameBoard(settings.getNumberOfClouds(), settings.getNumberOfIslands(),settings.getStudentsInClouds());
            this.players = initPlayers(playerNames);
            this.professorMap = initProfessorMap();
            Player firstPlayer = this.players.get(playerNames.get(0));
            List<String> playerList = this.players.keySet().stream().toList();
            this.currentRound = new Round(firstPlayer, playerList);
            if(Boolean.TRUE.equals(expertVariant)) {
                initCharacterCards();
            }
        }catch (IOException e){
            System.out.print("Error in reading config files: "+e.getMessage());
        }

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
    public CharacterEffect getCharacterEffect(int characterId) {
        return characterMap.get(characterId).getEffect();
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
            HashMap<Integer, AssistantCard> playerDeck = new HashMap<>(assistantDeck);
            Player newPlayer = new Player(nickName,settings.getStudentsInEntrance(),settings.getNumberOfTowersForPlayer(),playerDeck,expertVariant ? 1 : 0 );
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

    private void initCharacterCards() throws IOException {
        Map<Integer,CharacterCard> characterDeck = deserializer.getCharacters();
        for(int i = 0; i<3; i++){
            int pickedCharacterIndex = rand.nextInt(characterDeck.size());
            while(characterMap.containsKey(pickedCharacterIndex)){
                pickedCharacterIndex = rand.nextInt(characterDeck.size());
            }
            characterMap.putIfAbsent(pickedCharacterIndex,characterDeck.get(pickedCharacterIndex));
        }
        characterMap.values().forEach(characterCard -> characterCard.addStudents(gameBoard.getBag().pick(characterCard.getStudentsCapacity())));
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

    public void addStudentToCurrentPlayerHall(PawnColour studentColour){
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.addStudentToHall(studentColour,expertVariant);
        this.professorMap = (EnumMap<PawnColour, Professor>) assignProfessorsToPlayer(currentPlayer);
    }

    @Override
    public void addStudentsToCurrentPlayerHall(Map<PawnColour,Integer> studentMap) {
        for(PawnColour colour : studentMap.keySet()){
            for(int i = 0 ; i < studentMap.get(colour) ; i++){
                addStudentToCurrentPlayerHall(colour);
            }
        }
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

    public void notifyBoardListeners() {
        boardUpdateListeners.forEach(boardListener -> boardListener.onBoardUpdate(getBoardData()));
    }

    @Override
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    @Override
    public CharacterCard getCharacter(int characterId) {
        return characterMap.get(characterId);
    }

    private void notifyGameInit() {
        boardUpdateListeners.forEach(boardListener -> boardListener.onGameInit(getBoardData(),expertVariant));
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
        notifyBoardListeners();
    }

    public void calculateInfluence(int isleIndex,Optional<PawnColour> excludedColour){
        //get influential colours: only the colours that are present on the island and also have an associated professor
        Set<PawnColour> professorColours = getAvailableProfessors();
        if(professorColours.isEmpty())return;
        EnumMap<PawnColour,Integer> influentialStudentOnIsleMap = this.gameBoard.getIsleCircle().get(isleIndex).getAvailableColours();

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
                if(isleTowerColour!= null)addTowerToPlayer(isleTowerColour);
                checkEndGameConditions();
        }
        notifyBoardListeners();
    }


    private Set<PawnColour> getAvailableProfessors() {
        Set<PawnColour> availableProfessors = new HashSet<>();
        for(PawnColour colour : PawnColour.values()){
            if(!Objects.equals(professorMap.get(colour).getPlayer(), "")){
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
        getCurrentPlayer().setHasPlayedCharacter(false);

        if(this.currentRound.isEnded()) {
            if(currentRound.getIsLastRound()){
                endGame();
            }else{
                this.currentRound = this.currentRound.initNextRound(this.players);
                this.gameBoard.addStudentsToClouds(this.settings.getStudentsInClouds());
                checkLastRoundConditions();
            }
        }else{
            this.currentRound.setNextPlayer(this.players);
        }
        this.currentRound.setCurrentRuleSet(DefaultRuleSet.getInstance());
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
        notifyBoardListeners();
    }

    @Override
    public void emptyCloud(int cloudIndex) {
        Map<PawnColour,Integer> studentsOnCloud = this.gameBoard.getStudentsOnCloud(cloudIndex);
        getCurrentPlayer().addStudentsToEntrance(studentsOnCloud);
        gameBoard.emptyCloud(cloudIndex);
        notifyBoardListeners();
    }

    @Override
    public void activateCharacter(int characterId) {
        CharacterCard card = characterMap.get(characterId);
        this.currentRound.setCurrentRuleSet(card.getRuleSet());
        getCurrentPlayer().payCoins(card.getPrice());
        card.increasePrice();
        notifyBoardListeners();
    }

    @Override
    public void moveStudentToIsle(PawnColour studentColour, int isleIndex){
        moveStudentToIsle(studentColour,isleIndex,false);
    }

    @Override
    public void moveStudentToIsle(PawnColour studentColour, int isleIndex, boolean fromCharacter) {
        getCurrentPlayer().removeStudentsFromEntrance(new EnumMap<>(Map.of(studentColour, 1)));
        gameBoard.addStudentToIsle(isleIndex,new EnumMap<>(Map.of(studentColour, 1)));
        notifyBoardListeners();
    }

    public BoardData getBoardData(){
        Map<String, PlayerBoardData> playerBoards = new HashMap<>();
        HashMap<Integer, CharacterCardData> charactersData = new HashMap<>();
        for( Map.Entry<String, Player> playerEntry : players.entrySet()){
            playerBoards.put(playerEntry.getKey(),playerEntry.getValue().getBoardData(professorMap));
        }
        if(Boolean.TRUE.equals(expertVariant)){
            for( Map.Entry<Integer, CharacterCard> characterCardEntry : characterMap.entrySet()){
                charactersData.put(characterCardEntry.getKey(),characterCardEntry.getValue().getData());
            }
        }
        return new BoardData(playerBoards,gameBoard.getData(),charactersData);
    }

    public void addBoardUpdateListener(BoardUpdateListener listener){
        boardUpdateListeners.add(listener);
    }

    public void addEndGameListener(EndGameListener listener){
        endGameListeners.add(listener);
    }

    private void checkLastRoundConditions(){
        boolean isLastRound = gameBoard.getBag().isEmpty();

        for(Map.Entry<String, Player> player : players.entrySet()){
            isLastRound = isLastRound || player.getValue().getDeck().isEmpty();
        }
        currentRound.setIsLastRound(isLastRound);
    }

    private void checkEndGameConditions(){
        boolean endGame = gameBoard.getIsleCircle().getSize() <= 3;
        for(Map.Entry<String, Player> player : players.entrySet()){
            endGame = endGame || player.getValue().getTowerCounter() == 0;
        }
        if(endGame)endGame();
    }

    private void endGame() {
        winner = getWinner();
        notifyEndGameListeners();
    }

    private String getWinner() {
       List<Player> chart = players.values().stream().sorted(Comparator.comparingInt(Player::getTowerCounter)).toList();

        String winner = chart.get(0).getNickName();

       if(winner == chart.get(1).getNickName()){
           Map<String,Integer> professorChart = new HashMap<>();
           professorMap.values().forEach(professor -> professorChart.put(professor.getPlayer(),professorChart.getOrDefault(professor.getPlayer(),0)));
           winner = professorChart.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).toList().get(0);

       }
       return winner;

    }

    private void notifyEndGameListeners() {
        endGameListeners.forEach(endGameListener -> endGameListener.onEndGame(winner));
    }

}
