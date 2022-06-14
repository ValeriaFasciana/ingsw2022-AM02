package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.network.messages.clienttoserver.events.NicknameResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SetUpSceneController {


    List<Integer> selectedAssistantCard;
    private GUIApp gui = null;
    private Object lock = null;
    private String nickname;
    private int numPlayer;
    private boolean gameMode;

    public boolean getGameMode() {
        return gameMode;
    }

    public int getNumPlayer() {
        return numPlayer;
    }
    public String getNickname() {
        return nickname;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    @FXML
    public VBox vBoxGameMode;
    @FXML
    public Button simpleButton;
    @FXML
    public Button expertButton;
    @FXML
    public VBox vBoxNickname;
    @FXML
    public Label nicknameInfoLabel;
    @FXML
    public TextField nicknameField;
    @FXML
    public Button sendNicknameButton;
    @FXML
    public VBox vBoxNumOfPlayers;
    @FXML
    public ChoiceBox numOfPlayersChoiceBox;
    @FXML
    public Button sendNumOfPlayerButton;
    @FXML
    public VBox vBoxWaiting;
    @FXML
    public Label lastLabel;


    /**
     * Initializes scene's viewed Vbox and all controller's inner variables
     */
    @FXML
    public void initialize() {
        vBoxNickname.setVisible(false);
        vBoxGameMode.setVisible(false);
        vBoxNumOfPlayers.setVisible(false);
        vBoxWaiting.setVisible(false);
        selectedAssistantCard = new ArrayList<>();
    }

    public void setGUI(GUIApp gui){
        this.gui=gui;
    }


    @FXML
    public void portChanged(KeyEvent keyEvent) {
    }

    @FXML
    public void ipChanged(KeyEvent keyEvent) {
    }

    @FXML
    public void handleSimpleButton(ActionEvent event) {
        gameMode = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    @FXML
    public void handleExpertButton(ActionEvent event) {
        gameMode = true;
        synchronized (lock) {
            lock.notify();
        }
    }

    @FXML
    public void handleSendNicknameButton(ActionEvent event) {
        nickname = nicknameField.getText();
        synchronized (lock) {
            lock.notify();
        }
    }

    @FXML
    public void onNumOfPlayersChoiceBoxChosenButton(ActionEvent event) {
        numPlayer = Integer.parseInt((String)numOfPlayersChoiceBox.getValue());
        synchronized (lock) {
            lock.notify();
        }
    }

    public void displayNicknameRequest(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBoxGameMode.setVisible(false);
                vBoxNickname.setVisible(true);
                vBoxNumOfPlayers.setVisible(false);
                vBoxWaiting.setVisible(false);

                nicknameInfoLabel.setText("Insert your nickname");

            }
        });
    }

    public void displayNumberOfPlayersRequest() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBoxGameMode.setVisible(false);
                vBoxNickname.setVisible(false);
                vBoxNumOfPlayers.setVisible(true);
                vBoxWaiting.setVisible(false);
                numOfPlayersChoiceBox.getItems().add("2");
                numOfPlayersChoiceBox.getItems().add("3");
                numOfPlayersChoiceBox.getItems().add("4");
            }
        });
    }
    public void displaySelectGameMode(){
        Platform.runLater( new Runnable() {
            @Override
            public void run() {
                vBoxGameMode.setVisible(true);
                vBoxNickname.setVisible(false);
                vBoxNumOfPlayers.setVisible(false);
                vBoxWaiting.setVisible(false);
            }
        });

    }
}
