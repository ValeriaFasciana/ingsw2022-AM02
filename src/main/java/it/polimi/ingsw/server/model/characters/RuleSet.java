package it.polimi.ingsw.server.model.characters;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.action.Action;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.player.Player;

import java.util.*;

public abstract class RuleSet {



    public TowerColour calculateInfluence(IsleGroup isle, EnumMap<PawnColour,Professor> professorMap, HashMap<String,Player> playerMap, Player currentPlayer, PawnColour excludedColour){

        if(isle.isEmpty())return null;

        //Map<nickName,influence> contains influence value for ech player
        HashMap<String,Integer> playerInfluenceMap = initPlayerInfluenceMap(playerMap,currentPlayer);


        //add to current player additional influence (decorated by character card)
        playerInfluenceMap.put(currentPlayer.getNickName(),getAdditionalInfluence());


        //if the IsleGroup has towers on it, add to the player influence an amount equal to the size of the isleGroup
        if(isle.getTower()!= null && !excludeTowers()) {
            playerInfluenceMap.put(getPlayerByTowerColour(playerMap,isle.getTower()), isle.getSize());
        }


        Set<PawnColour> influentialColours = getInfluentialColours(isle,professorMap);

        // calculate influence for each player checking the professor distribution
        for(PawnColour colour : influentialColours){
            String playerName = professorMap.get(colour).getPlayer();
            playerInfluenceMap.put(playerName, playerInfluenceMap.get(playerName) + isle.getStudentsByColour(colour));
        }

        // find the most influential players
        int maxInfluence = Collections.max(playerInfluenceMap.values());
        List<String> mostInfluentialPlayers = playerInfluenceMap.entrySet().stream()
                .filter(entry -> entry.getValue() == maxInfluence)
                .map(Map.Entry::getKey).toList();

        //if there is a unique most influential player return his towerColour
        if(mostInfluentialPlayers.size()== 1)return playerMap.get(mostInfluentialPlayers.get(0)).getTowerColour();

        //if one of the most influential players already controls the isleGroup return his towerColour
        for(String playerName : mostInfluentialPlayers){
            if(playerMap.get(playerName).getTowerColour() == isle.getTower())return isle.getTower();
        }

        //in there are multiple players with the same influence return null
        return null;
    }

    private HashMap<String, Integer> initPlayerInfluenceMap(HashMap<String,Player> playerMap,Player currentPlayer) {
        HashMap<String, Integer> playerInfluenceMap = new HashMap<>();
        for(String nickName : playerMap.keySet()){
            playerInfluenceMap.put(nickName,0);
        }
        return playerInfluenceMap;
    }


    public ArrayList<IsleGroup> getMotherNatureAvailableMoves(Player currentPlayer, GameBoard board){
        IsleGroup motherNatureCurrentPosition= board.getMotherNaturePosition();
        int playerMoves = currentPlayer.getChosenAssistantMovements() + getAdditionalMotherNatureMoves();
        ArrayList<IsleGroup> motherNatureAvailableMoves = board.getIsleCircle().getNextIslands(motherNatureCurrentPosition,playerMoves);
        motherNatureAvailableMoves.remove(0);
        return motherNatureAvailableMoves;
    }

    public EnumMap<PawnColour,Professor> assignProfessorsToPlayer(Player player, EnumMap<PawnColour,Professor> professorMap){
        EnumMap<PawnColour,Integer> studentsInHall = player.getBoard().getStudentsInHall();
        for(PawnColour colour : studentsInHall.keySet()){
            if(isToAssignProfessor(studentsInHall.get(colour),professorMap.get(colour).getCounter())){
                professorMap.get(colour).setPlayer(player.getNickName());
                professorMap.get(colour).setCounter(studentsInHall.get(colour));
            }
        }
        return professorMap;
    }

    /**
     * contains the condition for assigning a professor
     * this method will be overridden by characters effects
     * @param studentCount number of students of a given colour contained in the player entrance
     * @param professorCount number of students associated to the professor of the given colour
     */

    public boolean isToAssignProfessor(Integer studentCount, Integer professorCount) {
        return (studentCount > professorCount);
    }

    public int getAdditionalMotherNatureMoves() {
        return 0;
    }


    public Integer getAdditionalInfluence() {
        return 0;
    }


    public EnumSet<PawnColour> getInfluentialColours(IsleGroup isle,EnumMap<PawnColour,Professor> professorMap) {
        EnumSet<PawnColour> availableColours = EnumSet.noneOf(PawnColour.class);
        EnumSet<PawnColour> isleColours = isle.getAvailableColours();
        for(PawnColour colour : PawnColour.values()){
            if(isleColours.contains(colour) && professorMap.containsKey(colour) && (professorMap.get(colour).getCounter()>0))availableColours.add(colour);
        }
        return availableColours;
    }

    public String getPlayerByTowerColour(HashMap<String,Player> playerMap, TowerColour colour){
        for(String nickName : playerMap.keySet()){
            if(playerMap.get(nickName).getTowerColour() == colour)return nickName;
        }
        return null;
    }

    public boolean excludeTowers(){
        return false;
    }

    public abstract ArrayList<Action> getAvailableActions(Phase turnPhase);


//    public boolean isToAssignProfessor(Integer studentCount, Integer professorCount){}
//
//    int getAdditionalMotherNatureMoves(){}
//
//
//    Integer getAdditionalInfluence(){}
//
//
//    Set<PawnColour> getInfluentialColours(IsleGroup isle);
//
//    boolean excludeTowers();
//
//    void assignProfessorsToPlayer(Player player, EnumMap<PawnColour,Professor> professorMap);

//    void getAvailableCards(String playerName);
//    void planningPhase();
//    void calculateActionOrder();
//
//    void assignProfessor();
//
//    void getMotherNatureAvailableMoves();
//
//    void moveMotherNature();
//    void mergeIsle();
//    void calculateInfluence();
//    void checkWinningConditions();
//    void declareWinner();
//    void checkAvailableClouds();
//    void checkStudentsEntrance();
//    void playerHasNoTowers();
//
//    void playerHasNoCards();
//    void lessThanThreeIsleGroups();
//    void bagIsEmpty();
//    void generateCharacter();

}
