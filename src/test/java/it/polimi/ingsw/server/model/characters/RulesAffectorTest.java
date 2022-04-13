package it.polimi.ingsw.server.model.characters;

import it.polimi.ingsw.server.model.AssistantCard;
import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.Professor;
import it.polimi.ingsw.server.model.TowerColour;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.board.IsleCircle;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RulesAffectorTest {
    RuleSet ruleSet;
    EnumMap<PawnColour, Professor> professorMap = new EnumMap<>(PawnColour.class);
    HashMap<String,Player> playerMap = new HashMap<>();
    GameBoard gameBoard =new GameBoard(3,12,3);


    @BeforeEach
    public void setUp() {
        Player player1 = new Player("testPlayer",3,8, TowerColour.BLACK);
        Player player2 = new Player("testPlayer2",3,8, TowerColour.WHITE);
        this.playerMap.put("testPlayer",player1);
        this.playerMap.put("testPlayer2",player2);
        EnumMap<PawnColour,Integer> studentMap =new EnumMap<>(PawnColour.class);
        studentMap.put(PawnColour.RED,4);
        studentMap.put(PawnColour.YELLOW,3);
        studentMap.put(PawnColour.BLUE,8);
        player1.addStudentsToHall(studentMap);
        player1.setChosenAssistant(new AssistantCard(5,4));
        IsleGroup isle = gameBoard.getIsleCircle().get(0);
        isle.addStudents(studentMap);
        Professor professor = new Professor();
        professor.setPlayer(this.playerMap.get("testPlayer2").getNickName());
        professor.setCounter(8);
        this.professorMap.put(PawnColour.BLUE,professor);
        this.professorMap.put(PawnColour.RED, new Professor());
        this.professorMap.put(PawnColour.YELLOW,new Professor());
        this.professorMap.put(PawnColour.GREEN, new Professor());
        this.professorMap.put(PawnColour.PINK,new Professor());
    }

    @Test
    void assignProfessorsToPlayerWithDefaultRuleSet() {
        this.ruleSet = new DefaultRuleSet();
        this.ruleSet.assignProfessorsToPlayer(this.playerMap.get("testPlayer"),this.professorMap);
        assertEquals("testPlayer2",this.professorMap.get(PawnColour.BLUE).getPlayer());
    }

    @Test
    void assignProfessorsToPlayerWithAffector() {
        this.ruleSet = new RulesAffector(new DefaultRuleSet(),false,0,0,true);
        this.ruleSet.assignProfessorsToPlayer(this.playerMap.get("testPlayer"),this.professorMap);
        assertEquals("testPlayer",this.professorMap.get(PawnColour.BLUE).getPlayer());
    }

    @Test
    void calculateInfluenceWithDefaultRuleSet(){
        this.ruleSet = new DefaultRuleSet();
        TowerColour towerColour = this.ruleSet.calculateInfluence(gameBoard.getIsleCircle().get(0),this.professorMap,this.playerMap,this.playerMap.get("testPlayer"),null);
        assertEquals(TowerColour.WHITE,towerColour);
    }

    @Test
    void calculateInfluenceWithAffector(){
        this.ruleSet = new RulesAffector(new DefaultRuleSet(),true,10,0,false);
        TowerColour towerColour = this.ruleSet.calculateInfluence(gameBoard.getIsleCircle().get(0),this.professorMap,this.playerMap,this.playerMap.get("testPlayer"),null);
        assertEquals(TowerColour.BLACK,towerColour);
    }

    @Test
    void getAvailableMotherNatureMovesWithDefaultRules(){
        this.ruleSet = new DefaultRuleSet();
        ArrayList<IsleGroup> availableIsles = this.ruleSet.getMotherNatureAvailableMoves(this.playerMap.get("testPlayer"),this.gameBoard);
        assertEquals(4,availableIsles.size());

    }
}