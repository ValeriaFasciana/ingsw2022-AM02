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
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Thread.sleep;


public class GUIApp extends Application implements ViewInterface {
    private SetUpSceneController setupSceneController;
    private GameSceneController gameSceneController;
    private OtherPlayerBoardsController otherPlayerBoardsController;
    private CharactersController characterController;
    private Stage stageCharacters;
    private FXMLLoader fxmlLoader;
    private Stage stage;
    Stage stageOtherPlayerboards;
    private Client client;
    boolean gameMode;
    private static GUIApp instance;
    static final Logger LOGGER = Logger.getLogger(GUIApp.class.getName());
    private final Object lock = new Object();
    private String username;
    String nick;
    private int numcloud;
    private boolean expertMode;
    private BoardData boardData;
    private int chosenCharacterCard;
    private boolean hasUsedCharacterCard = false;

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
            gameSceneController.setLock(lock);
            gameSceneController.updateBoard(boardData, expertMode, nick);
            stage.show();
            synchronized (lock) {
                lock.notify();
            }
        });
    }

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

    public void instantiateCharacterCardsScene() {
        stageCharacters = new Stage();
        Scene scene;
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/gui/FXML/CharactersScene.fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
            scene = new Scene(new Label("Error loading the scene"));
        }
        stageCharacters.setScene(scene);
        stageCharacters.setTitle("Eriantys");
        stageCharacters.setResizable(false);
        stageCharacters.centerOnScreen();
        characterController = fxmlLoader.getController();
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
    @Override
    public void initBoard(BoardData boardData, boolean expertMode) {
        this.boardData = boardData;
        this.expertMode = expertMode;
        this.numcloud = boardData.getGameBoard().getClouds().size();
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
                System.out.println("controller.getLobbyButton().equals(\"r\"): "+controller.getLobbyButton().equals("Rejoin"));
                JoinLobbyResponse message = new JoinLobbyResponse(username, nick,controller.getLobbyButton().equals("Rejoin"));
                System.out.println("Rejoin Message:\n "+"userName"+ message.getUsername()+ "\nnickName" + message.getPlayerNickName()+ "\nisRejoin: "+message.isRejoin());
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
            username = controller.getNickname();
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        JoinLobbyResponse message = new JoinLobbyResponse(username, nick,isRejoin);
        client.sendCommandMessage(message);
        this.waiting();
    }



    @Override
    public void askAssistant(Set<Integer> availableAssistantIds) {
        GameSceneController controller = fxmlLoader.getController();

        try {
            synchronized (lock) {
                controller.selectAssistantCard(availableAssistantIds);
                lock.wait();
            }
        }
        catch(InterruptedException | NullPointerException e ) {
            System.out.println("exception in askAssistant: "+e.getMessage());
            Thread.currentThread().interrupt();
        }
        ChooseAssistantResponse message = new ChooseAssistantResponse(nick, controller.getChosenCardId());
        client.sendCommandMessage(message);
    }

    public void setHasUsedCharacterCard(boolean hasUsedCharacterCard) {
        this.hasUsedCharacterCard = hasUsedCharacterCard;
    }

    public boolean hasUsedCharacterCard() {
        return hasUsedCharacterCard;
    }

    @Override
    public void askMoveStudentFromEntrance(Map<PawnColour, Boolean> hallColourAvailability) {

        try{
            GameSceneController controller = fxmlLoader.getController();

            MessageFromClientToServer toReturnMessage = null;
            try {
                synchronized (lock) {
                    controller.selectStudent();
                    lock.wait();
                }
            }
            catch(InterruptedException e) {
                System.out.println("exception in selectStudent" +e.getMessage());
            }

        PawnColour selectedStudentColour = PawnColour.valueOf(controller.getChosenStudentColour());


            try {
                synchronized (lock) {
                    controller.selectStudentDestination();
                    lock.wait();
                }
            }
            catch(InterruptedException e) {
                System.out.println("exception in selectStudent" +e.getMessage());
            }

            String selectStudentDestination = controller.getChosenStudentDestination();
            if(selectStudentDestination.equals("isles")) {
                int isleId = controller.getChosenIsle();
                toReturnMessage = new MoveStudentToIsleResponse(nick, isleId, selectedStudentColour);
            }
            if(selectStudentDestination.equals("hall")) {
                toReturnMessage = new MoveStudentToHallResponse(nick, selectedStudentColour);
            }
            client.sendCommandMessage(toReturnMessage);
            hasUsedCharacterCard = false;
        }catch(Exception e ){
            System.out.println("exception in selectStudent" +e.getMessage());
        }

    }

    @Override
    public void moveMotherNature(Set<Integer> availableIsleIndexes) {
        GameSceneController controller = fxmlLoader.getController();


        try {
            synchronized (lock) {
                controller.selectMotherNature(availableIsleIndexes);
                lock.wait();
            }
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        Integer mothernaturedestination = controller.getChosenMotherNature();
        MoveMotherNatureResponse message = new MoveMotherNatureResponse(nick,mothernaturedestination);
        client.sendCommandMessage(message);

    }

    /**
     *
     * @param availableCloudIndexes
     */

    @Override
    public void askCloud(Set<Integer> availableCloudIndexes) {
        GameSceneController controller = fxmlLoader.getController();

        try {
            synchronized (lock) {
                controller.selectCloud(availableCloudIndexes);
                lock.wait();
            }
        }
        catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }
        Integer chosenCloud = controller.getChosenCloud();
        ChooseCloudResponse message = new ChooseCloudResponse(nick,chosenCloud);
        client.sendCommandMessage(message);

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



    public void handleReturnButtonCharacters() {
        stageCharacters.close();
    }
    public void handleReturnButtonOtherBoards() {
        stageOtherPlayerboards.close();
    }

    @Override
    public void notifyDisconnection(String disconnectedPlayerName) {

    }

    @Override
    public void notifyPlayerHasJoined(String joiningPlayer) {

    }



    public void setChosenCharacterCard(int chosenCharacterCard) {
        this.chosenCharacterCard = chosenCharacterCard;
        UseCharacterEffectRequest message = new UseCharacterEffectRequest(nick, chosenCharacterCard);
        client.sendCommandMessage(message);
    }

}