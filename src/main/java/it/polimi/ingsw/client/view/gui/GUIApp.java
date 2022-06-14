package it.polimi.ingsw.client.view.gui;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.client.utilities.InputParser;
import it.polimi.ingsw.client.view.FunctionInterface;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.messages.clienttoserver.events.NicknameResponse;
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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;


public class GUIApp extends Application implements ViewInterface {
    private SetUpSceneController setupSceneController;
    //private GameSceneController gameSceneController;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    private Client client;
    private BoardData board;
    private static GUIApp instance;
    static final Logger LOGGER = Logger.getLogger(GUI.class.getName());
    private final Object lock = new Object();

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

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void askLobbyInfo() throws InterruptedException {
        System.out.print("\nlobbyInfo\n");
        SetUpSceneController controller = fxmlLoader.getController();
        controller.displayNicknameRequest();
        synchronized (lock) {
            lock.wait();
        }
        String nick = controller.getNickname();
        System.out.print(nick);
        NicknameResponse message = new NicknameResponse((nick));
        client.sendCommandMessage(message);

    }


    @Override
    public void askUserInfo(boolean invalidName) {

    }

    @Override
    public void askUserInfo() {

    }



    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {

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
    public void askChooseColour(boolean toDiscard, boolean toExclude) {

    }

    @Override
    public void askMoveStudentsFromCard(int characterId, MovementDestination destination, int studentsToMove, boolean canMoveLess) {

    }

    @Override
    public void askExchangeStudents(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to) {

    }

    @Override
    public void initBoard(BoardData boardData, boolean expertMode) {

    }

    /**
     * resets scene controllers instances
     */
    private void resetControllers() {
        setupSceneController = null;
        //gameSceneController=null;
    }



}