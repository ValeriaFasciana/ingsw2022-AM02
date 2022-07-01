package it.polimi.ingsw.client.view.gui;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Thread.sleep;

/**
 * Class to manage the graphical user interface
 */

public class GUIApp extends Application implements ViewInterface {
    private SetUpSceneController setupSceneController;
    private GameSceneController gameSceneController;
    private OtherPlayerBoardsController otherPlayerBoardsController;
    private CharactersController characterController;
    private Stage stageCharacters;
    private FXMLLoader fxmlLoader;
    private FXMLLoader fxmlLoaderChar;
    private Stage stage;
    Stage stageOtherPlayerboards;
    private Client client;
    boolean gameMode;
    private static GUIApp instance;
    static final Logger LOGGER = Logger.getLogger(GUIApp.class.getName());
    private final Object lock = new Object();
    private String username;
    String nick;
    private boolean expertMode;
    private BoardData boardData;
    private boolean hasUsedCharacterCard = false;

    public void setHasUsedCharacterCard(boolean hasUsedCharacterCard) {this.hasUsedCharacterCard = hasUsedCharacterCard;}


    public GUIApp() {
        instance = this;
    }

    public static synchronized GUIApp getInstance() {
        if (instance == null) {
            new Thread(() -> {
                // Have to run in a thread because launch doesn't return
                Application.launch(GUIApp.class);
            }).start();
        }

        while (instance == null) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }

        return instance;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void start(Stage stage)throws IOException {
        this.stage = stage;
        stage.setOnCloseRequest((WindowEvent t) -> {
            Platform.exit();
            System.exit(0);
        });
        instantiateSetupScene();
    }

    /**
     * It creates the scene in FXML file. It doesnt show it unless specified inside functionInterface's code.
     * @param pathOfFxmlFile FXML file path of the scene to be created
     * @param functionInterface the function to be called at the end of scene creation and assignation to stage.
     */
    private void createMainScene(String pathOfFxmlFile, FunctionInterface functionInterface) {
        Platform.runLater(() -> {
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource(pathOfFxmlFile));
            stage.setScene(null);
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load(), 1280, 720);
            } catch (IOException e) {
                e.printStackTrace();
                scene = new Scene(new Label("Error loading the scene"));
            }
            stage.setScene(scene);
            stage.setResizable(false);
            functionInterface.executeFunction();
        });
    }

    /**
     * It creates and shows the GameScene as well as instantiating its Game Scene Controller
     */
    private void instantiateGameScene() {
        createMainScene("/gui/FXML/GameScene.fxml", () -> {
            stage.setTitle("Eriantys");
            stage.setResizable(false);
            stage.centerOnScreen();
            gameSceneController = fxmlLoader.getController();
            gameSceneController.setGUI(this);
            gameSceneController.updateBoard(boardData, expertMode, nick);
            stage.show();
            synchronized (lock) {
                lock.notify();
            }
        });
    }

    /**
     * It creates and shows the OtherPlayerBoardsScene as well as instantiating its OtherPlayerBoard Controller
     */
    public void instantiateOtherPlayerboardsScene(){
        stageOtherPlayerboards = new Stage();
        Scene scene;
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/FXML/OtherPlayerboardsScene.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
            scene = new Scene(new Label("Error loading the scene"));
        }
        stageOtherPlayerboards.setScene(scene);
        stageOtherPlayerboards.setTitle("Eriantys");
        stageOtherPlayerboards.setResizable(false);
        stageOtherPlayerboards.centerOnScreen();
        otherPlayerBoardsController = fxmlLoader.getController();
        otherPlayerBoardsController.setGUI(this);
        otherPlayerBoardsController.setLock(lock);
        otherPlayerBoardsController.displayOtherPlayerBoards(boardData, expertMode, nick);
        stageOtherPlayerboards.show();

    }

    /**
     * It creates and shows the CharacterCardsScene as well as instantiating its Character Card Controller
     */

    public void instantiateCharacterCardsScene() {
        stageCharacters = new Stage();
        Scene scene;
        fxmlLoaderChar = new FXMLLoader();
        fxmlLoaderChar.setLocation(getClass().getResource("/gui/FXML/CharactersScene.fxml"));
        try {
            scene = new Scene(fxmlLoaderChar.load(), 900, 600);
        } catch (IOException e) {
            e.printStackTrace();
            scene = new Scene(new Label("Error loading the scene"));
        }
        stageCharacters.setScene(scene);
        stageCharacters.setTitle("Eriantys");
        stageCharacters.setResizable(false);
        stageCharacters.centerOnScreen();
        characterController = fxmlLoaderChar.getController();
        characterController.setGUI(this);
        characterController.setLock(lock);
        characterController.displayCharacterCards(boardData, nick);
        stageCharacters.show();
    }
    /**
     * It creates and shows the SetUp Scene as well as instantiating its SetUp Scene Controller
     */
    private void instantiateSetupScene() {
        createMainScene("/gui/FXML/SetupScene.fxml", () -> {
            stage.setTitle("Eriantys");
            stage.setResizable(false);
            stage.show();
            setupSceneController = fxmlLoader.getController();
            setupSceneController.setGUI(this);
            setupSceneController.setLock(lock);

        });

    }

    /**
     * Method to initialize the board
     * @param boardData board data
     * @param expertMode if it's true the game mode is expert
     */
    @Override
    public void initBoard(BoardData boardData, boolean expertMode) {
        this.boardData = boardData;
        this.expertMode = expertMode;
        try {
            synchronized (lock) {
                instantiateGameScene();
                lock.wait();
            }
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method that updates the board
     * @param boardData the board data
     */
    @Override
    public void setBoard(BoardData boardData) {
        this.boardData = boardData;
        try {
            synchronized (lock) {
                instantiateGameScene();
                lock.wait();
            }
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Method to display the waiting scene
     */
    @Override
    public void waiting() {
        SetUpSceneController controller = fxmlLoader.getController();
        controller.displayWaitingInTheLobbyMessage();
    }

    /**
     * Method to display a message
     * @param message the message to display
     */
    @Override
    public void displayMessage(String message) {

    }

    /**
     * Method to handle the request for lobby info
     * @param username the player's chosen username
     * @param canJoinLobby if it's true, the player can join a lobby
     * @param canRejoinLobby if it's true, the player can rejoin a lobby after disconnecting
     */
    @Override
    public void askLoginInfo(String username, boolean canJoinLobby, boolean canRejoinLobby) {
        this.username=username;
        try {
            SetUpSceneController controller = fxmlLoader.getController();
            controller.displaySelectLobby(canJoinLobby,canRejoinLobby);
            synchronized (lock) {
                lock.wait();
            }
            if(controller.getLobbyButton()=="Create"){
                nick = askNickname();
                int numPlayer = askNumberOfPlayers();
                boolean gameMode = askGameMode();
                CreateLobbyResponse message = new CreateLobbyResponse(username,nick, numPlayer, gameMode);
                client.sendCommandMessage(message);
                this.waiting();
            }
            else{
                nick = askNickname();
                JoinLobbyResponse message = new JoinLobbyResponse(username, nick,controller.getLobbyButton().equals("Rejoin"));
                client.sendCommandMessage(message);
                this.waiting();
            }

        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }


    }


    /**
     * Method to handle the request for game mode
     * @return if the return value is true, th game mode is expert
     */
    private boolean askGameMode() {
        try {
            SetUpSceneController controller = fxmlLoader.getController();
            controller.displaySelectGameMode();
            synchronized (lock) {
                lock.wait();
            }
            gameMode = controller.getGameMode();
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return gameMode;
    }

    /**
     * Method to handle the request for the client nickname
     * @return the string of the chosen nickname
     */
    private String askNickname(){
        try {
            SetUpSceneController controller = fxmlLoader.getController();
            controller.displayNicknameRequest();
            synchronized (lock) {
                lock.wait();
            }
            nick = controller.getNickname();
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return nick;
    }

    private int askNumberOfPlayers() {
        int numPlayer = 0;
        try {
            SetUpSceneController controller = fxmlLoader.getController();
            controller.displayNumberOfPlayersRequest();
            synchronized (lock) {
                lock.wait();
            }
            numPlayer = controller.getNumPlayer();
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        return numPlayer;
    }

    /**
     * Method to request the user info
     * @param isRejoin if it's true, the player can rejoin after disconnecting
     */

    @Override
    public void askUserInfo(boolean isRejoin) {
        try {
            SetUpSceneController controller = fxmlLoader.getController();
            controller.displayIncorrectNickname();
            synchronized (lock) {
                lock.wait();
            }
            username = controller.getNickname();
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        JoinLobbyResponse message = new JoinLobbyResponse(username, nick,isRejoin);
        client.sendCommandMessage(message);
        this.waiting();
    }

    /**
     * Method to handle the choice of the assistant card
     * @param availableAssistantIds available indexes of the assistant cards
     */
    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {
        hasUsedCharacterCard=false;
        GameSceneController controller = fxmlLoader.getController();
        controller.selectAssistantCard(availableAssistantIds);
    }

    public void askAssistantResponse(int Cardid){
        ChooseAssistantResponse message = new ChooseAssistantResponse(nick, Cardid);
        client.sendCommandMessage(message);


    }


    public boolean hasUsedCharacterCard() {
        return hasUsedCharacterCard;
    }

    /**
     * Method to handle the choice of the student to move from the entrance
     * @param hallColourAvailability map of the colour of the students and their amount
     */
    @Override
    public void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability) {

        GameSceneController controller = fxmlLoader.getController();
        controller.selectStudent(hallColourAvailability);
    }

    public void studentDestinationResponse(){
        GameSceneController controller = fxmlLoader.getController();
        MessageFromClientToServer toReturnMessage = null;
        String selectStudentDestination = controller.getChosenStudentDestination();
        PawnColour selectedStudentColour = PawnColour.valueOf(controller.getChosenStudentColour());
        if(selectStudentDestination.equals("isles")) {
            int isleId = controller.getChosenIsle();
            toReturnMessage = new MoveStudentToIsleResponse(nick, isleId, selectedStudentColour);
        }
        if(selectStudentDestination.equals("hall")) {
            toReturnMessage = new MoveStudentToHallResponse(nick, selectedStudentColour);
        }
        client.sendCommandMessage(toReturnMessage);

    }


    /**
     * Method to handle the selection of the island where to put mother nature on
     * @param availableIsleIndexes available indexes the player can choose from
     */
    @Override
    public void moveMotherNature(Set<Integer> availableIsleIndexes) {
        GameSceneController controller = fxmlLoader.getController();
        controller.selectMotherNature(availableIsleIndexes);



    }
    public void moveMotherNatureResponse(Integer mothernaturedestination){
        MoveMotherNatureResponse message = new MoveMotherNatureResponse(nick,mothernaturedestination);
        client.sendCommandMessage(message);
    }

    /**
     * Method to handle the selection of the cloud
     * @param availableCloudIndexes available indexes the player can choose from
     */
    @Override
    public void askCloud(Set<Integer> availableCloudIndexes) {
        GameSceneController controller = fxmlLoader.getController();
        controller.selectCloud(availableCloudIndexes);
    }

    public void cloudResponse(Integer chosenCloud){
        ChooseCloudResponse message = new ChooseCloudResponse(nick,chosenCloud);
        client.sendCommandMessage(message);
    }



    @Override
    public void endGame(String causingPlayer, String cause) {
        GameSceneController controller = fxmlLoader.getController();
        controller.endgame(causingPlayer,cause);

    }

    /**
     * Method to handle the client's island choice. If setBan is true, it
     * put the ban on. If calculateInfluence is true, it calculates the influence
     * @param setBan the island ban
     * @param calculateInfluence the island influence
     */
    @Override
    public void askChooseIsland(boolean setBan, boolean calculateInfluence) {
        GameSceneController controller = fxmlLoader.getController();
        controller.askChooseIsland(setBan,calculateInfluence);



    }
    public void ChooseIslandResponse(int selectedIsle,boolean setBan, boolean calculateInfluence){
        client.sendCommandMessage(new ChooseIslandResponse(nick,selectedIsle,setBan,calculateInfluence));
    }

    /**
     * Method to handle the colour choice. If discard > 0 it discard the colour
     * choice. If toExclude is true it exclude the colour choice when calculating influence
     * @param toDiscard value of the colour to discard
     * @param toExclude if it's true it discard the colour
     */
    @Override
    public void askChooseColour(int toDiscard, boolean toExclude) {
        characterController.askColour(toDiscard,toExclude);

    }
    public void colourResponse(PawnColour Colour,int toDiscard, boolean toExclude) {
        client.sendCommandMessage(new ChooseColourResponse(nick,Colour,toExclude,toDiscard));
    }

    /**
     * Method to handle the choice of the student on the Character Card
     * @param characterId character's cards id
     * @param destination destination where to move the student to
     * @param studentsToMove value of the colour of the student to move
     * @param canMoveLess if it's true the player can decide to move less students, instead than all of them
     */
    @Override
    public void askMoveStudentsFromCard(int characterId, MovementDestination destination, int studentsToMove, boolean canMoveLess) {
        CharactersController controller = fxmlLoaderChar.getController();
        controller.chooseStudentFromCharacter(characterId,destination,studentsToMove);

    }

    /**
     * Method to handle the exchange of students
     * @param characterId character's cards id
     * @param numberOfStudents max number that the player can exchange
     * @param from starting location of the students to exchange
     * @param to destination of the students
     */
    @Override
    public void askExchangeStudents(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to) {
        if(from.equals(MovementDestination.HALL)){
            GameSceneController controller = fxmlLoader.getController();
            controller.exchangeStudentHallToEntrance(characterId,numberOfStudents,from,to);
        }else {
            CharactersController controller = fxmlLoaderChar.getController();
            controller.exchangeStudentFromCharacter(characterId, numberOfStudents, from, to);
        }

    }


    /**
     * it closes the character window
     */
    public void handleReturnButtonCharacters() {
        stageCharacters.close();
    }

    /**
     * it closes the other playerboards window
     */
    public void handleReturnButtonOtherBoards() {
        stageOtherPlayerboards.close();
    }

    /**
     * Method to notify the disconnection of a player
     * @param disconnectedPlayerName username of the disconnected player
     */
    @Override
    public void notifyDisconnection(String disconnectedPlayerName) {
        if(fxmlLoader.getController() instanceof SetUpSceneController) {
            SetUpSceneController controller = fxmlLoader.getController();
            controller.playerDisconnected(disconnectedPlayerName);
        }else{
            GameSceneController controller = fxmlLoader.getController();
            controller.playerDisconnected(disconnectedPlayerName);

        }

    }

    /**
     * Method to notify that a player has joined
     * @param joiningPlayer username of the player that has joined the lobby
     */
    @Override
    public void notifyPlayerHasJoined(String joiningPlayer) {
        SetUpSceneController controller = fxmlLoader.getController();
        controller.playerJoined(joiningPlayer);

    }

    @Override
    public void askLobbyInfo() {

    }

    /**
     * It saves the selected character card on the gui
     * @param chosenCharacterCard
     */
    public void setChosenCharacterCard(int chosenCharacterCard) {
        UseCharacterEffectRequest message = new UseCharacterEffectRequest(nick, chosenCharacterCard);
        client.sendCommandMessage(message);
    }


    public void sendMoveFromCardResponse(int characterId, Map<PawnColour, Integer> toMoveStudentsMap, MovementDestination destination) {
        if(destination.equals(MovementDestination.ISLE)){
            gameSceneController.selectIsleForMovement(characterId,toMoveStudentsMap);
        }else{
            MoveStudentFromCardResponse message = new MoveStudentFromCardResponse(nick,characterId,-1,MovementDestination.HALL,toMoveStudentsMap);
            client.sendCommandMessage(message);

        }

    }

    public void moveFromCardToIsleResponse(int characterId,Integer chosenIsle, Map<PawnColour, Integer> toMoveStudentsMap) {
        MoveStudentFromCardResponse message = new MoveStudentFromCardResponse(nick,characterId,chosenIsle,MovementDestination.ISLE,toMoveStudentsMap);
        client.sendCommandMessage(message);
    }

    public void exchangeStudentsFromHall(int characterId, MovementDestination from, MovementDestination to, Map<PawnColour, Integer> fromMap, Map<PawnColour, Integer> toMap) {
        client.sendCommandMessage(new ExchangeStudentsResponse(nick,characterId,from ,to, fromMap, toMap));
    }
}