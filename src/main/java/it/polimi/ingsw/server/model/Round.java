package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.characters.DefaultRuleSet;
import it.polimi.ingsw.server.model.characters.RuleSet;
import it.polimi.ingsw.server.model.player.Player;

import java.util.*;

public class Round {
    private Phase currentPhase;
    private Player currentPlayer;
    private LinkedList<String>  planningOrder;
    private LinkedList<OrderElement> actionOrder;
    private RuleSet currentRuleSet;
    private String nextRoundFirstPlayer;


    public Round(Player firstPlayer, LinkedList<String> playerList) {
        this.currentPhase = Phase.PLANNING;
        this.currentPlayer = firstPlayer;
        this.planningOrder = initPlanningOrder(firstPlayer.getNickName(),playerList);
        this.actionOrder = new LinkedList<>();
        this.currentRuleSet = new DefaultRuleSet();
    }

    public Round(List<String> toList) {
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
                if (e1.playsSecond) return -1;
                if (e2.playsSecond) return 1;
            }
            return Integer.compare(e1.cardValue, e2.cardValue);
        }
    }


    /**
     * method that returns the initial planning order of one round given a list of player names and the name of the first player
     * @param firstPlayer name of the first player
     * @param playerList ordered list of players names
     * @return a linked list of player names where firstPlayer is set as first element of the list and the order of the others players is preserved (maintains clockwise order)
     */
    private LinkedList<String>  initPlanningOrder(String firstPlayer, LinkedList<String> playerList) {
        int firstPlayerIndex = playerList.indexOf(firstPlayer);
        LinkedList<String> firstHalf = new LinkedList<>(playerList.subList(0,firstPlayerIndex));
        LinkedList<String> lastHalf = new LinkedList<>(playerList.subList(firstPlayerIndex,playerList.size()));
        lastHalf.add(playerList.getLast());
        lastHalf.addAll(firstHalf);
        return (LinkedList<String>) lastHalf;
    }

    /**
     * sets the next player for the round considering the phase
     * @param playerMap map (playerName,Player) used for setting the currentPlayer
     */
    public void setNextPlayer(Map<String,Player> playerMap) {
        String nextPlayer = null;
        switch(currentPhase){
            case PLANNING -> nextPlayer = setNextPlanningTurn();
            case ACTION -> nextPlayer = setNextActionTurn();
        }
        if(nextPlayer != null)this.currentPlayer = playerMap.get(nextPlayer);
    }

    /**
     *
     * @return the next playerName when we are in planning phase
     *
     * if the Planning phase is ended and Action phase starts
     */
    private String setNextPlanningTurn() {
        String nextPlayer;
        try{
            nextPlayer = planningOrder.listIterator(planningOrder.indexOf(currentPlayer.getNickName())).next();
        }catch (NoSuchElementException e){
            return setActionPhase();
        }
        return nextPlayer;
    }

    /**
     *
     * @return name of the first player in the Action phase
     * actionOrder is sorted at the start of the method
     * currentPhase is set to ACTION
     */
    private String setActionPhase() {
        this.actionOrder.sort(new OrderComparator());
        this.currentPhase = Phase.ACTION;
        String nextPlayer = this.actionOrder.getFirst().playerId;
        this.nextRoundFirstPlayer = nextPlayer;
        return nextPlayer;
    }

    /**
     *
     * @return next playerName when we are in Action phase
     * actionOrder list is gradually reduced popping an element each time the function is called
     */
    private String setNextActionTurn() {
        String nextPlayer;
        try{
            nextPlayer = this.actionOrder.pop().playerId;
        }catch (NoSuchElementException e){
            System.out.println("Missing next player for action turn");
            nextPlayer = null;
        }
        return nextPlayer;
    }


    /**
     * updates current round actionOrder with the card played by the current player
     * @param currentAssistantCard card played by currentPlayer
     */
    public void updateWithPlayedAssistant(AssistantCard currentAssistantCard) {
        updateActionOrder(currentAssistantCard);
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
     *
     * @return the list of assistant cards(Ids) played during this round
     */
    private List<Integer> getPlayedAssistants() {
        return this.actionOrder.stream().map(el -> el.cardId).toList();
    }

    /**
     * initialize and return the next round
     * @param playerMap map (playerName, Player)
     * @return the new round
     */
    public Round initNextRound(Map<String,Player> playerMap) {
        Player firstPlayer = playerMap.get(this.nextRoundFirstPlayer);
       return new Round(firstPlayer,(LinkedList<String>) playerMap.keySet().stream().toList());
    }

    public boolean isEnded() {
        return this.actionOrder.size() <= 1;
    }


    public boolean excludeTowersFromInfluence() {
        return this.currentRuleSet.excludeTowers();
    }

    public HashMap<TowerColour, Integer> sumAdditionalInfluence(HashMap<TowerColour, Integer> playerInfluenceMap) {
        int additionalInfluence = this.currentRuleSet.getAdditionalInfluence();
        int initialInfluence = playerInfluenceMap.get(currentPlayer.getTowerColour()) != null ?  playerInfluenceMap.get(currentPlayer.getTowerColour()) : 0;
        playerInfluenceMap.put(currentPlayer.getTowerColour(),initialInfluence+additionalInfluence);
        return playerInfluenceMap;
    }

    public Set<Integer> getPlayableAssistants() {
        Set<Integer> playedAssistants = new HashSet<>(getPlayedAssistants());
        Set<Integer> currentPlayerAssistants = this.currentPlayer.getDeck().keySet();
        Set<Integer> availableAssistants = currentPlayerAssistants;
        availableAssistants.removeAll(playedAssistants);
        if(availableAssistants.isEmpty()){
            return currentPlayerAssistants;
        }
        return availableAssistants;
    }
}
