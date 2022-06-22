package it.polimi.ingsw.client.view.gui;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.FunctionInterface;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.messages.clienttoserver.events.CreateLobbyResponse;
import it.polimi.ingsw.network.messages.clienttoserver.events.JoinLobbyResponse;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class GUIApp extends Application implements ViewInterface {
    private SetUpSceneController setupSceneController;
    private GameSceneController gameSceneController;
    private OtherPlayerBoardsController otherPlayerBoardsController;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Client client;
    boolean gameMode;
    private static GUIApp instance;
    static final Logger LOGGER = Logger.getLogger(GUI.class.getName());
    private final Object lock = new Object();
    private String username;
    String nick;
    private boolean expertMode;
    private BoardData boardData;

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
            Scene scene;
            try {
                scene = new Scene(fxmlLoader.load());
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
            stage.show();
            gameSceneController = fxmlLoader.getController();
            gameSceneController.setGUI(this);
            gameSceneController.setLock(lock);
            synchronized (lock) {
                lock.notify();
            }

        });

    }

    public void instantiateOtherPlayerboardsScene() {
        createMainScene("/gui/FXML/OtherPlayerboardsScene.fxml", () -> {
            stage.setTitle("Eriantys");
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
            otherPlayerBoardsController = fxmlLoader.getController();
            otherPlayerBoardsController.setGUI(this);
            otherPlayerBoardsController.setLock(lock);
            synchronized (lock) {
                lock.notify();
            }
        });

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



    @Override
    public void waiting() {
        SetUpSceneController controller = fxmlLoader.getController();
        controller.displayWaitingInTheLobbyMessage();
    }

    @Override
    public void displayMessage(String message) {

    }

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
                JoinLobbyResponse message = new JoinLobbyResponse(username, nick,controller.getLobbyButton().equals("r"));
                client.sendCommandMessage(message);
                this.waiting();
            }

        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void askLobbyInfo() {

    }

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

    @Override
    public void askUserInfo(boolean isRejoin) {
        try {
            SetUpSceneController controller = fxmlLoader.getController();
            controller.displayIncorrectNickname();
            synchronized (lock) {
                lock.wait();
            }
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        username = askNickname();
        SetUpSceneController controller = fxmlLoader.getController();
        JoinLobbyResponse message = new JoinLobbyResponse(username, nick,controller.getLobbyButton().equals("r"));
        client.sendCommandMessage(message);
        this.waiting();
    }



    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {
        GameSceneController controller = fxmlLoader.getController();
        controller.selectAssistantCard(availableAssistantIds);
    }

    @Override
    public void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability) {

    }

    @Override
    public void moveMotherNature(ArrayList<Integer> availableIsleIndexes) {

    }

    @Override
    public void askCloud(Set<Integer> availableCloudIndexes) {

    }

    @Override
    public void setBoard(BoardData boardData) {


    }

    @Override
    public void endGame(String winnerPlayer) {

    }

    @Override
    public void askChooseIsland(boolean setBan, boolean calculateInfluence) {

    }

    @Override
    public void askChooseColour(int toDiscard, boolean toExclude) {

    }

    @Override
    public void askMoveStudentsFromCard(int characterId, MovementDestination destination, int studentsToMove, boolean canMoveLess) {

    }

    @Override
    public void askExchangeStudents(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to) {

    }

    public boolean isExpertMode() {
        return expertMode;
    }

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
        try {
            GameSceneController controller = fxmlLoader.getController();
            controller.initialize(boardData, expertMode, nick);
            synchronized (lock) {
                lock.wait();
            }
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }

    public void displayYourBoard() {
        initBoard(boardData, expertMode);
    }

    public void displayOtherPlayerBoards() {
        try {
            synchronized (lock) {
                instantiateOtherPlayerboardsScene();
                lock.wait();
            }
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }

        OtherPlayerBoardsController controller = fxmlLoader.getController();
        controller.displayOtherPlayerBoards(boardData, expertMode, nick);

    }



    @Override
    public void notifyDisconnection(String disconnectedPlayerName) {

    }

    @Override
    public void notifyPlayerHasJoined(String joiningPlayer) {

    }

    /**
     * resets scene controllers instances
     */
    private void resetControllers() {
        setupSceneController = null;
        gameSceneController=null;
    }



}