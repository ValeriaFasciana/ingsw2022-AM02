package it.polimi.ingsw.server.model.game;

import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.server.model.board.GameBoard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterEffect;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.Phase;
import it.polimi.ingsw.shared.enums.TowerColour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    static Game game;
    static Map<String, TowerColour> players;
    @BeforeEach
    void setUp() {
        players = new HashMap<>();
        players.put("player1",TowerColour.BLACK);
        players.put("player2",TowerColour.WHITE);
        players.put("player3",TowerColour.GREY);

        List<String> playerNames = new ArrayList<>();
        playerNames.add("player1");
        playerNames.add("player2");
        playerNames.add("player3");
        game = new Game(playerNames,2, true);

    }
    @Test
    void create() {
    }

    @Test
    void getCharacterEffect() {
        List<String> players = new ArrayList<>();
        players.add("Vale");
        players.add("Paolo");
        Game game = new Game(players,2,true );
        Set<Integer> keys = game.getBoardData().getCharacters().keySet();
        CharacterCardData cardData = game.getBoardData().getCharacters().get(keys.iterator().next());
        int id = cardData.getId();
        CharacterCard card = game.getCharacter(id);
        CharacterEffect effect = card.getEffect();
        assertEquals(effect, game.getCharacterEffect(id) );
    }

    @Test
    void getPlayers() {
        List<String> list = new ArrayList<>();
        list.add("Marco");
        list.add("Paolo");
        Game game = new Game(list,2,true);
        Set<String> gameSet = game.getPlayers().keySet();
        assertEquals(gameSet.iterator().next(),game.getPlayers().get("Marco").getNickName());

    }

    @Test
    void getAvailableClouds() {
        List<String> list = new ArrayList<>();
        list.add("Marco");
        list.add("Paolo");
        Game game = new Game(list,2,true);
        GameBoard gameBoard = new GameBoard(2,12,3);
        Set<Integer> availableClouds = gameBoard.getAvailableClouds();
        assertEquals(game.getAvailableClouds(), availableClouds);
    }

    @Test
    void getSettings() {
        List<String> list = new ArrayList<>();
        list.add("Marco");
        list.add("Paolo");
        Game game = new Game(list,2,true);
        assertEquals(2,game.getSettings().getNumberOfPlayers());
    }

    @Test
    void getPlayableAssistants() {
        List<String> list = new ArrayList<>();
        list.add("Marco");
        list.add("Paolo");
        Game game = new Game(list,2,true);
        Set<Integer> assistantCards = game.getPlayableAssistants();
        Integer card = assistantCards.iterator().next();
        assertEquals(card, game.getPlayableAssistants().iterator().next());
    }

    @Test
    void playAssistantCard() {
        List<String> list = new ArrayList<>();
        list.add("Marco");
        list.add("Paolo");
        Game game = new Game(list,2,true);
    }

    @Test
    void addStudentToCurrentPlayerHall() {
        Player player = game.getCurrentPlayer();
        int hall = player.getBoard().getHall().getNumberOfStudents();
        game.addStudentToCurrentPlayerHall(PawnColour.BLUE);
        assertEquals(hall, player.getBoard().getHall().getNumberOfStudents() -1);

    }

    @Test
    void addStudentsToCurrentPlayerHall() {
        Map<PawnColour,Integer> studentMap = new HashMap<>();
        studentMap.put(PawnColour.RED, 1);
        studentMap.put(PawnColour.BLUE, 2);
        game.addStudentsToCurrentPlayerHall(studentMap);
        Player player = game.getCurrentPlayer();
        assertEquals(3, player.getBoard().getHall().getNumberOfStudents());
    }

    @Test
    void assignProfessorsToPlayer() {
        Player player = game.getCurrentPlayer();
        game.addStudentToCurrentPlayerHall(PawnColour.RED);
        game.assignProfessorsToPlayer(player);
        assertTrue(game.getBoardData().getPlayerBoards().get(game.getCurrentPlayer().getNickName()).getProfessors().contains(PawnColour.RED))
        ;

    }

    @Test
    void notifyBoardListeners() {
    }

    @Test
    void getGameBoard() {
        GameBoard gameboard = game.getGameBoard();
        assertEquals(gameboard.getBag(), game.getGameBoard().getBag());
    }

    @Test
    void getCharacter() {
        Set<Integer> keys = game.getBoardData().getCharacters().keySet();
        CharacterCardData cardData = game.getBoardData().getCharacters().get(keys.iterator().next());
        int id = cardData.getId();
        assertEquals(game.getCharacter(id).getId(), cardData.getId());
    }

    @Test
    void excludeColourFromInfluence() {
        game.excludeColourFromInfluence(PawnColour.RED);
        assertEquals(game.getInfluenceExcludedColour().get(), PawnColour.RED);
    }

    @Test
    void deactivatePlayer() {
        List<String> list = new ArrayList<>();
        list.add("Marco");
        list.add("Paolo");
        Game game = new Game(list,2,true);
        game.deactivatePlayer("Marco");
        assertFalse(game.getPlayers().get("Marco").isActive());
    }

    @Test
    void activatePlayer() {
        List<String> list = new ArrayList<>();
        list.add("Marco");
        list.add("Paolo");
        Game game = new Game(list,2,true);
        game.activatePlayer("Marco");
        assertTrue(game.getPlayers().get("Marco").isActive());
    }

    @Test
    void moveMotherNature() {
        game.moveMotherNature(3);
        assertEquals(3, game.getGameBoard().getMotherNaturePosition());
    }

    @Test
    void calculateInfluence() {
        game.getProfessorMap().get(PawnColour.YELLOW).setPlayer("player1");
        Map<PawnColour,Integer> yellowStudentMap = new HashMap<>();
        yellowStudentMap.put(PawnColour.YELLOW,3);
        game.getGameBoard().getIsleCircle().get(3).empty();
        game.getGameBoard().getIsleCircle().get(3).addStudents(yellowStudentMap);
        game.calculateInfluence(3,Optional.empty());
        assertEquals(TowerColour.BLACK,game.getGameBoard().getIsleCircle().get(3).getTower());
        game.getProfessorMap().get(PawnColour.BLUE).setPlayer("player2");
        Map<PawnColour,Integer> blueStudentMap = new HashMap<>();
        blueStudentMap.put(PawnColour.BLUE,3);
        game.getGameBoard().getIsleCircle().get(3).addStudents(blueStudentMap);
        game.calculateInfluence(3,Optional.empty());
        assertEquals(TowerColour.BLACK,game.getGameBoard().getIsleCircle().get(3).getTower());
        Map<PawnColour,Integer> redStudentMap = new HashMap<>();
        game.getProfessorMap().get(PawnColour.RED).setPlayer("player3");
        redStudentMap.put(PawnColour.RED,4);
        game.getGameBoard().getIsleCircle().get(3).addStudents(redStudentMap);
        game.calculateInfluence(3,Optional.empty());
        assertEquals(TowerColour.BLACK,game.getGameBoard().getIsleCircle().get(3).getTower());
        game.getProfessorMap().get(PawnColour.GREEN).setPlayer("player3");
        Map<PawnColour,Integer> greenStudentMap = new HashMap<>();
        greenStudentMap.put(PawnColour.GREEN,1);
        game.getGameBoard().getIsleCircle().get(3).addStudents(greenStudentMap);
        game.calculateInfluence(3,Optional.empty());
    }

    @Test
    void endCurrentPlayerTurn() {
        assertEquals("player1",game.getCurrentPlayer().getNickName());
        System.out.print("\nplayer: "+game.getCurrentPlayer().getNickName()+ "\navailableAssistants:\n"+ game.getPlayableAssistants());
        game.playAssistantCard(3);
        game.endCurrentPlayerTurn();
        assertEquals("player2",game.getCurrentPlayer().getNickName());
        assertFalse(game.getPlayableAssistants().contains(3));
        System.out.print("\nplayer: "+game.getCurrentPlayer().getNickName()+ "\navailableAssistants:\n"+ game.getPlayableAssistants());
        game.playAssistantCard(2);
        game.endCurrentPlayerTurn();
        assertFalse(game.getPlayableAssistants().contains(3));
        assertFalse(game.getPlayableAssistants().contains(2));
        assertEquals(Phase.PLANNING,game.getRoundPhase());
        assertEquals("player3",game.getCurrentPlayer().getNickName());
        game.playAssistantCard(6);
        game.endCurrentPlayerTurn();
        assertEquals(Phase.ACTION,game.getRoundPhase());
        assertEquals("player2",game.getCurrentPlayer().getNickName());
        System.out.print("\nplayer: "+game.getCurrentPlayer().getNickName()+ "\navailableAssistants:\n"+ game.getPlayableAssistants());
        assertEquals(Phase.ACTION,game.getRoundPhase());
        game.endCurrentPlayerTurn();
        assertEquals("player1",game.getCurrentPlayer().getNickName());
        assertEquals(Phase.ACTION,game.getRoundPhase());
        game.endCurrentPlayerTurn();
        assertEquals("player3",game.getCurrentPlayer().getNickName());
        assertEquals(Phase.ACTION,game.getRoundPhase());
        game.endCurrentPlayerTurn();
        assertEquals(Phase.PLANNING,game.getRoundPhase());
        assertEquals("player2",game.getCurrentPlayer().getNickName());
        game.playAssistantCard(5);
        game.endCurrentPlayerTurn();
        assertEquals("player3",game.getCurrentPlayer().getNickName());
        assertEquals(Phase.PLANNING,game.getRoundPhase());
        game.playAssistantCard(7);
        game.endCurrentPlayerTurn();
        assertEquals(Phase.PLANNING,game.getRoundPhase());
        assertEquals("player1",game.getCurrentPlayer().getNickName());
        game.playAssistantCard(9);
        game.endCurrentPlayerTurn();
        assertEquals(Phase.ACTION,game.getRoundPhase());
        assertEquals("player2",game.getCurrentPlayer().getNickName());

    }

    @Test
    void getMotherNatureAvailableIslands() {
        game.playAssistantCard(3);
        int isle = game.getGameBoard().getMotherNaturePosition();
        assertTrue(game.getMotherNatureAvailableIslands().contains(isle+1));
    }

    @Test
    void getPlayerHallAvailability() {
        Map<PawnColour, Boolean> available = game.getPlayerHallAvailability("player1");
        assertTrue(available.get(PawnColour.RED));
    }

    @Test
    void getCurrentPlayerName() {
        assertEquals("player1", game.getCurrentPlayerName());
    }

    @Test
    void useAction() {
    }

    @Test
    void setBanOnIsland() {
        game.setBanOnIsland(1);
        assertEquals(1,game.getBoardData().getGameBoard().getIsleCircle().getIsles().get(1).getBanCounter());
    }

    @Test
    void emptyCloud() {
        game.emptyCloud(0);
        int quantity = 0;
        for(PawnColour colour : PawnColour.values()) {
            quantity += game.getBoardData().getGameBoard().getClouds().get(0).getStudentMap().get(colour);
        }
        assertEquals(0, quantity);
    }

    @Test
    void activateCharacter() {
    }


    @Test
    void addBoardUpdateListener() {
    }

    @Test
    void addEndGameListener() {
    }

    @Test
    void getProfessorMap() {
        assertTrue(game.getProfessorMap().containsKey(PawnColour.RED));
    }
}