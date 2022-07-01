package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.data.RoundData;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.cards.characters.DefaultRuleSet;
import it.polimi.ingsw.server.model.cards.characters.RuleSet;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.shared.enums.Phase;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.util.*;
import java.util.stream.Collectors;

public class Round {
    private Phase currentPhase;
    private Player currentPlayer;
    private List<String>  planningOrder;
    private List<OrderElement> actionOrder;
    private RuleSet currentRuleSet;
    private String nextRoundFirstPlayer;
    private boolean isLastRound;
    private int roundNumber;

    /**
     * Default constructor
     * @param firstPlayer first player
     * @param playerList player list
     * @param roundNumber round number
     */
    public Round(Player firstPlayer, List<String> playerList,int roundNumber) {
        this.currentPhase = Phase.PLANNING;
        this.currentPlayer = firstPlayer;
        this.planningOrder = initPlanningOrder(firstPlayer.getNickName(),playerList);
        this.actionOrder = new ArrayList<>();
        this.currentRuleSet = DefaultRuleSet.getInstance();
        this.roundNumber = roundNumber;
    }

    public List<OrderElement> getActionOrder() {
        return actionOrder;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Phase getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentRuleSet(RuleSet ruleSet) {
        this.currentRuleSet = ruleSet;
    }

    public void setIsLastRound(boolean isLastRound) {
        this.isLastRound = isLastRound;
    }

    public boolean getIsLastRound() {
        return  isLastRound;
    }

    public RoundData getData() {
        return new RoundData(currentPlayer.getNickName(),roundNumber,isLastRound,currentPhase);
    }


    /**
     * subclass for actionPhase turn handling
     */
     class OrderElement {
        int cardId;
        int cardValue;
        String playerId;
        boolean playsSecond;
        OrderElement(int cardId,int cardValue,String playerId){
           this(cardId,cardValue,playerId,false);
        }
        OrderElement(int cardId,int cardValue,String playerId,boolean playsSecond){
            this.cardId = cardId;
            this.cardValue = cardValue;
            this.playerId = playerId;
            this.playsSecond = playsSecond;
        }
    }

    /**
     * custom order comparator for actionPhase turn order
     */
    public class OrderComparator implements Comparator<OrderElement> {
        public int compare(OrderElement e1,OrderElement e2) {
            if (e1.cardId == e2.cardId) {
                if (e1.playsSecond) return 1;
                if (e2.playsSecond) return -1;
            }
            return Integer.compare(e1.cardValue, e2.cardValue);
        }
    }


    /**
     * method that returns the initial planning order of one round given a list of player names and the name of the first player
     * @param firstPlayer name of the first player
     * @param playerList ordered list of players names
     * @return a list of player names where firstPlayer is set as first element of the list and the order of the others players is preserved (maintains clockwise order)
     */
    private List<String> initPlanningOrder(String firstPlayer, List<String> playerList) {
        int firstPlayerIndex = playerList.indexOf(firstPlayer);
        List<String> firstHalf = new ArrayList<>(playerList.subList(0, firstPlayerIndex));
        List<String> lastHalf =  new ArrayList<>(playerList.subList(firstPlayerIndex,playerList.size()));
        lastHalf.addAll(firstHalf);
        return lastHalf;
    }

    /**
     * sets the next player for the round considering the phase
     * @param playerMap map (playerName,Player) used for setting the currentPlayer
     */
    public void setNextPlayer(Map<String,Player> playerMap) {
        String nextPlayer = null;
        switch(this.currentPhase){
            case PLANNING -> nextPlayer = setNextPlanningTurn();
            case ACTION -> nextPlayer = setNextActionTurn();
        }
        if(nextPlayer != null)this.currentPlayer = playerMap.get(nextPlayer);
    }

    /**
     * if the Planning phase is ended and Action phase starts
     * @return the next playerName when we are in planning phase
     */
    private String setNextPlanningTurn() {
        String nextPlayer;
        try{
            int currentPlayerIndex = this.planningOrder.indexOf(this.currentPlayer.getNickName());;
            nextPlayer = this.planningOrder.get(currentPlayerIndex + 1);
        }catch (IndexOutOfBoundsException e){
            return setActionPhase();
        }
        return nextPlayer;
    }

    /**
     * Set action phase
     * actionOrder is sorted at the start of the method
     * currentPhase is set to ACTION
     * @return name of the first player in the Action phase
     */
    private String setActionPhase() {
        this.actionOrder.sort(new OrderComparator());
        this.currentPhase = Phase.ACTION;
        String nextPlayer = (this.actionOrder.remove(0)).playerId;
        this.nextRoundFirstPlayer = nextPlayer;
        return nextPlayer;
    }

    /**
     * actionOrder list is gradually reduced popping an element each time the function is called
     * @return next playerName when we are in Action phase
     */
    private String setNextActionTurn() {
        String nextPlayer;
        try{
            nextPlayer = this.actionOrder.remove(0).playerId;
        }catch (IndexOutOfBoundsException e){
            System.out.println("Missing next player for action turn");
            nextPlayer = null;
        }
        return nextPlayer;
    }


    /**
     * updates current round actionOrder with the card played by the current player
     * @param assistantCard card played by currentPlayer
     */
    public void updateWithPlayedAssistant(AssistantCard assistantCard) {
        this.currentPlayer.playAssistant(assistantCard.getId());
        updateActionOrder(assistantCard);
    }


    /**
     * updates actionOrder adding a new OrderElement
     * if the card has been already played by another player the new OrderElement is marked with playsSecond = true
     * @param card played card
     */
    private void updateActionOrder(AssistantCard card) {
        OrderElement element;
        List<Integer> playedAssistantsIds = getPlayedAssistants();
        if(playedAssistantsIds.contains(card.getId())) {
            element = new OrderElement(card.getId(), card.getValue(), currentPlayer.getNickName(), true);
        }else {
            element = new OrderElement(card.getId(), card.getValue(), currentPlayer.getNickName());
        }
        actionOrder.add(element);
    }


    /**
     * Method to get already played assistant cards
     * @return the list of assistant cards(Ids) played during this round
     */
    public List<Integer> getPlayedAssistants() {
        return this.actionOrder.stream().map(el -> el.cardId).toList();
    }

    /**
     * initialize and return the next round
     * @param playerMap map (playerName, Player)
     * @return the new round
     */
    public Round initNextRound(Map<String,Player> playerMap) {
        Player firstPlayer = playerMap.getOrDefault(this.nextRoundFirstPlayer,playerMap.values().stream().toList().get(0));
       return new Round(firstPlayer,playerMap.keySet().stream().toList(),roundNumber+1);
    }

    /**
     * Method to check if round is finished
     * @return true if the current player is the last player of this round
     */
    public boolean isEnded() {
        return this.actionOrder.size() < 1 && currentPhase == Phase.ACTION;
    }

    public RuleSet getCurrentRuleSet() {
        return currentRuleSet;
    }

    /**
     * used for influence calculation, checks the current ruleSet applied to the currentPlayer's turn
     * @return true if towers are excluded from influence calculation (character's effect)
     */
    public boolean excludeTowersFromInfluence() {
        return this.currentRuleSet.excludeTowers();
    }

    /**
     * used for influence calculation, increases the current player's influence
     * @param playerInfluenceMap map representing the influence points associated to each player
     * @return the same map received as parameter,where the current player's influence is increased by the value expected by the current ruleSet
     */
    public Map<TowerColour, Integer> sumAdditionalInfluence(Map<TowerColour, Integer> playerInfluenceMap) {
        int additionalInfluence = this.currentRuleSet.getAdditionalInfluence();
        int initialInfluence = playerInfluenceMap.getOrDefault(currentPlayer.getTowerColour(),0);
        playerInfluenceMap.put(currentPlayer.getTowerColour(),initialInfluence+additionalInfluence);
        return playerInfluenceMap;
    }

    /**
     * Method to check professor assignment
     * @param studentCount students to check
     * @param professorCount professor count
     * @return true if the professor is assigned
     */
    public boolean checkProfessorAssignment(Integer studentCount, int professorCount) {
        return this.currentRuleSet.isToAssignProfessor(studentCount,professorCount);
    }


    /**
     * returns the assistants that can be played by the current player.
     * They are obtained by subtracting the cards played by other players during this round to the cards owned by the player
     * @return a set of cardIds representing the playable cards
     */
    public Set<Integer> getPlayableAssistants() {
        Set<Integer> playedAssistants = new HashSet<>(getPlayedAssistants());
        Set<Integer> currentPlayerAssistants = this.currentPlayer.getDeck().keySet();
        Set<Integer> availableAssistants  = currentPlayerAssistants.stream().filter(assistant -> !playedAssistants.contains(assistant)).collect(Collectors.toSet());
        if(availableAssistants.isEmpty()){
            return currentPlayerAssistants;
        }
        return availableAssistants;
    }
}
