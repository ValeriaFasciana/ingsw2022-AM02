package it.polimi.ingsw.server.model.game;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.network.data.PlayerBoardData;
import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.controller.listeners.EndGameListener;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.controller.action.Action;
import it.polimi.ingsw.server.controller.action.ActionVisitor;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class Game implements GameInterface,ActionVisitor {

    private Map<String,Player> players;
    private GameBoard gameBoard;
    private Boolean expertVariant;
    private GameSettings settings;
    private Round currentRound;
    private EnumMap<PawnColour, Professor> professorMap;
    private static Deserializer deserializer = new Deserializer();
    private HashMap<Integer, AssistantCard> assistantDeck;

    public Optional<PawnColour> getInfluenceExcludedColour() {
        return influenceExcludedColour;
    }

    private Optional<PawnColour> influenceExcludedColour = Optional.empty();
    private Random rand = new Random();
    private String winner;

    private List<BoardUpdateListener> boardUpdateListeners = new ArrayList<>();
    private List<EndGameListener> endGameListeners = new ArrayList<>();

    private Map<Integer,CharacterCard> characterMap = new HashMap<>();

    /**
     * Default constructor
     * @param playerNames players usernames
     * @param numberOfPlayers number of players
     * @param expertVariant if true, game mode is expert
     */
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
            this.currentRound = new Round(firstPlayer, playerList,1);
            if(Boolean.TRUE.equals(expertVariant)) {
                initCharacterCards();
            }
        }catch (IOException e){
            System.out.print("Error in reading config files: "+e.getMessage());
        }

    }

    /**
     * Method to initialize professors
     * @return map of professors
     */
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
    public Map<String, Player> getPlayers() {
        return players;
    }

    @Override
    public Set<Integer> getAvailableClouds() {
        return gameBoard.getAvailableClouds();
    }

    @Override
    public GameSettings getSettings() {
        return this.settings;
    }

    /**
     * Method to initialize players
     * @param playerNames list of players
     * @return map of players and their nickname
     */
    private Map<String, Player> initPlayers(List<String> playerNames) {
        Map<String, Player> playerMap = new HashMap<>();
        for(String nickName : playerNames){
            HashMap<Integer, AssistantCard> playerDeck = new HashMap<>(assistantDeck);
            Player newPlayer = new Player(nickName,settings.getStudentsInEntrance(),settings.getNumberOfTowersForPlayer(),playerDeck,expertVariant ? 1 : 0);
            newPlayer.addStudentsToEntrance(gameBoard.getBag().pick(settings.getStudentsInEntrance()));
            playerMap.put(nickName,newPlayer);
        }

        assignTowerColours(playerMap);
        return playerMap;
    }

    /**
     * Method to assign tower colours
     * @param playerMap map of player and their nickname
     */
    private void assignTowerColours(Map<String, Player> playerMap) {
        playerMap.values().stream().toList().get(0).setTowerColour(TowerColour.BLACK);
        playerMap.values().stream().toList().get(1).setTowerColour(TowerColour.WHITE);
        if(playerMap.size() == 3){
            playerMap.values().stream().toList().get(2).setTowerColour(TowerColour.GREY);
        }
    }

    /**
     * Method to initialize character cards
     * @throws IOException IO Exception
     */
    private void initCharacterCards() throws IOException {
        Map<Integer,CharacterCard> characterDeck = deserializer.getCharacters();
        for(int i = 0; i<3; i++){
            int pickedCharacterIndex = rand.nextInt(characterDeck.size());
            Integer characterId = (Integer)characterDeck.keySet().toArray()[pickedCharacterIndex];
            while(characterMap.containsKey(characterId)){
                pickedCharacterIndex = rand.nextInt(characterDeck.size());
                characterId = (Integer)characterDeck.keySet().toArray()[pickedCharacterIndex];
            }

            characterMap.putIfAbsent(characterId,characterDeck.get(characterId));
        }
        characterMap.values().forEach(characterCard -> characterCard.addStudents(gameBoard.getBag().pick(characterCard.getStudentsCapacity())));
    }

    @Override
    public Set<Integer> getPlayableAssistants(){
        return currentRound.getPlayableAssistants();
    }

    /**
     * Method to play chosen assistant card
     * @param assistantId chosen assistant card id
     */
    @Override
    public void playAssistantCard(int assistantId){
        AssistantCard playedAssistant = assistantDeck.get(assistantId);
        this.currentRound.updateWithPlayedAssistant(playedAssistant);
    }

    /**
     * Method to add student to current player hall
     * @param studentColour colour of student to add
     */
    public void addStudentToCurrentPlayerHall(PawnColour studentColour){
        Player currentPlayer = getCurrentPlayer();
        currentPlayer.addStudentToHall(studentColour,expertVariant);
        this.professorMap = (EnumMap<PawnColour, Professor>) assignProfessorsToPlayer(currentPlayer);
    }

    /**
     * Method to add students to current player hall
     * @param studentMap map of students to add
     */
    @Override
    public void addStudentsToCurrentPlayerHall(Map<PawnColour,Integer> studentMap) {
        for(PawnColour colour : studentMap.keySet()){
            for(int i = 0 ; i < studentMap.get(colour) ; i++){
                addStudentToCurrentPlayerHall(colour);
            }
        }
    }

    /**
     * Method to assign professors to player
     * @param player selected player
     * @return map of assigned professors
     */
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

    /**
     * Method to notify other clients via broadcast
     */
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

    /**
     * Method to notify initialization of game to other clients via broadcast
     */
    private void notifyGameInit() {
        boardUpdateListeners.forEach(boardListener -> boardListener.onGameInit(getBoardData(),expertVariant));
    }

    /**
     * Method to exclude colour when calculating influence
     * @param colour colour to exclude
     */
    public void excludeColourFromInfluence(PawnColour colour){
        this.influenceExcludedColour = Optional.ofNullable(colour);
    }

    /**
     * Method to handle deactivation of player
     * @param nickname selected player
     */
    @Override
    public void deactivatePlayer(String nickname) {
        this.players.get(nickname).setActive(false);
        //notifyBoardListeners();
    }

    /**
     * Method to handle activation of player
     * @param nickname selected player
     */
    @Override
    public void activatePlayer(String nickname) {
        this.players.get(nickname).setActive(true);
        notifyBoardListeners();
    }

    /**
     * Method to handle move of mother nature
     * @param isleIndex id of isle to move mother nature to
     */
    public void moveMotherNature(int isleIndex){
        boolean isBannedIsle = this.gameBoard.isIsleBanned(isleIndex);
        this.gameBoard.moveMotherNatureTo(isleIndex);
        if(!isBannedIsle){
            calculateInfluence(isleIndex,this.influenceExcludedColour);
            this.influenceExcludedColour = Optional.empty();
        }
        notifyBoardListeners();
    }

    /**
     * Method to handle calculation of influence
     * @param isleIndex id of isle to calculate its influence
     * @param excludedColour colour to exclude from calculation
     */
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
            playerInfluenceMap.put(isleTowerColour,playerInfluenceMap.getOrDefault(isleTowerColour,0) + isleSize);
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

    /**
     * Method to get available professors
     * @return set of available professors
     */
    private Set<PawnColour> getAvailableProfessors() {
        Set<PawnColour> availableProfessors = new HashSet<>();
        for(PawnColour colour : PawnColour.values()){
            if(!Objects.equals(professorMap.get(colour).getPlayer(), "") && players.get(professorMap.get(colour).getPlayer()).isActive()){
                availableProfessors.add(colour);
            }
        }
        return availableProfessors;
    }

    /**
     * Method to add tower to player
     * @param towerColour selected tower colour
     */
    private void addTowerToPlayer(TowerColour towerColour) {
        players.values()
                .stream().filter(player->towerColour.equals(player.getTowerColour()))
                .forEach(Player::addTower);
    }

    /**
     * Method to remove tower from player
     * @param towerColour selected tower colour
     */
    private void removeTowerFromPlayer(TowerColour towerColour) {
       players.values()
               .stream().filter(player->towerColour.equals(player.getTowerColour()))
               .forEach(Player::removeTower);
    }

    /**
     * Method to get most influential player
     * @param playerInfluenceMap map of all players' influences
     * @return tower colour of the most influential player
     */
    private Optional<TowerColour> getMostInfluentialPlayer(Map<TowerColour, Integer> playerInfluenceMap) {
        if(playerInfluenceMap.isEmpty())return Optional.empty();
        AtomicInteger max = new AtomicInteger();
        List<TowerColour> mostInfluentialPlayers = new ArrayList<>();
        playerInfluenceMap.entrySet().forEach(influenceEntry ->{
            if(influenceEntry.getValue() > max.get()){
            max.set(influenceEntry.getValue());
            mostInfluentialPlayers.clear();
            mostInfluentialPlayers.add(influenceEntry.getKey());
        }else if(influenceEntry.getValue() == max.get()){
            mostInfluentialPlayers.add(influenceEntry.getKey());
        }});

        if(max.get()== 0 || mostInfluentialPlayers.size()>1)return Optional.empty();

        return Optional.ofNullable(mostInfluentialPlayers.get(0));
    }

    /**
     * Method to end current player turn
     */
    @Override
    public void endCurrentPlayerTurn(){

        if(expertVariant) {
            characterMap.values().forEach(characterCard -> characterCard.refill(gameBoard.getBag()));
            getCurrentPlayer().setHasPlayedCharacter(false);
        }
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
        if(getCurrentPlayer().isActive()) {
            notifyBoardListeners();
        }else{
            endCurrentPlayerTurn();
        }
    }


    @Override
    public Phase getRoundPhase() {
        return currentRound.getCurrentPhase();
    }

    @Override
    public Set<Integer> getMotherNatureAvailableIslands() {
        Optional<AssistantCard> playedAssistant = getCurrentPlayer().getChosenAssistant();
        return playedAssistant.map(assistantCard -> gameBoard.getMotherNatureNextIslands(assistantCard.getMovement())).orElse(null);
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

    /**
     * Method to handle action
     * @param action selected action
     */
    @Override
    public void useAction(Action action){
        action.accept(this);
    }

    @Override
    public void setBanOnIsland(int isleIndex) {
        this.gameBoard.setBanOnIsle(isleIndex);
        notifyBoardListeners();
    }

    /**
     * Method to empty cloud
     * @param cloudIndex id of cloud to empty
     */
    @Override
    public void emptyCloud(int cloudIndex) {
        Map<PawnColour,Integer> studentsOnCloud = this.gameBoard.getStudentsOnCloud(cloudIndex);
        getCurrentPlayer().addStudentsToEntrance(studentsOnCloud);
        gameBoard.emptyCloud(cloudIndex);
    }

    /**
     * Method to activate character effect
     * @param characterId id of selected character
     */
    @Override
    public void activateCharacter(int characterId) {
        CharacterCard card = characterMap.get(characterId);
        this.currentRound.setCurrentRuleSet(card.getRuleSet());
        getCurrentPlayer().payCoins(card.getPrice());
        getCurrentPlayer().setHasPlayedCharacter(true);
        card.increasePrice();
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
        return new BoardData(expertVariant, currentRound.getData(), playerBoards,gameBoard.getData(),charactersData);
    }

    /**
     * Method to add listener to board update event
     * @param listener listener to add
     */
    public void addBoardUpdateListener(BoardUpdateListener listener){
        boardUpdateListeners.add(listener);
    }

    /**
     * Method to add listener to end game event
     * @param listener listener to add
     */
    public void addEndGameListener(EndGameListener listener){
        endGameListeners.add(listener);
    }

    /**
     * Method to check last round conditions
     */
    private void checkLastRoundConditions(){
        boolean isLastRound = gameBoard.getBag().isEmpty();

        for(Map.Entry<String, Player> player : players.entrySet()){
            isLastRound = isLastRound || player.getValue().getDeck().size() <= 1;
        }
        currentRound.setIsLastRound(isLastRound);
    }

    /**
     * Method to check end game conditions
     */
    private void checkEndGameConditions(){
        boolean endGame = gameBoard.getIsleCircle().getSize() <= 3;
        for(Map.Entry<String, Player> player : players.entrySet()){
            endGame = endGame || player.getValue().getTowerCounter() == 0;
        }
        if(endGame)endGame();
    }

    /**
     * Method to end game
     */
    private void endGame() {
        winner = getWinner();
        notifyEndGame();
    }

    /**
     * Method to get winner
     * @return winner
     */
    private String getWinner() {
       List<Player> chart = players.values().stream().sorted(Comparator.comparingInt(Player::getTowerCounter)).toList();

        String winner = chart.get(0).getNickName();

       if(Objects.equals(winner, chart.get(1).getNickName())){
           Map<String,Integer> professorChart = new HashMap<>();
           professorMap.values().forEach(professor -> professorChart.put(professor.getPlayer(),professorChart.getOrDefault(professor.getPlayer(),0)));
           winner = professorChart.entrySet().stream().sorted(Comparator.comparingInt(Map.Entry::getValue)).map(Map.Entry::getKey).toList().get(0);

       }
       return winner;

    }

    /**
     * Method to notify listenera on the winner
     */
    private void notifyEndGame() {
        endGameListeners.forEach(endGameListener -> endGameListener.onEndGame(winner));
    }

    public EnumMap<PawnColour, Professor> getProfessorMap() {
        return professorMap;
    }
}
