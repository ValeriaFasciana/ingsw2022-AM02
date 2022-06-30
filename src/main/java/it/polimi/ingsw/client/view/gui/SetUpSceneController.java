package it.polimi.ingsw.client.view.gui;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SetUpSceneController {

    private GUIApp gui = null;
    private Object lock = null;
    private String nickname;
    private int numPlayer;
    private boolean gameMode;
    private String lobbyButton;


    @FXML
    public VBox vBoxGameMode;
    @FXML
    public VBox vBoxLobby;
    @FXML
    public Button simpleButton;
    @FXML
    public Button createlobbyButton;
    @FXML
    public Button joinlobbyButton;
    @FXML
    public Button rejoinlobbyButton;
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
    }

    public String getLobbyButton() {
        return lobbyButton;
    }



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


    public void setGUI(GUIApp gui){
        this.gui=gui;
    }
    @FXML
    public void handlecreatelobbybutton(ActionEvent event) {
        lobbyButton= "Create";
        synchronized (lock) {
            lock.notify();
        }
    }
    @FXML
    public void handlejoinlobbybutton(ActionEvent event) {
        lobbyButton= "Join";
        synchronized (lock) {
            lock.notify();
        }
    }
    @FXML
    public void handlerejoinlobbybutton(ActionEvent event) {
        lobbyButton= "Rejoin";
        synchronized (lock) {
            lock.notify();
        }
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
        if(nicknameField.getText()!="") {
            if(nickname==null){
                nickname = nicknameField.getText();
                synchronized (lock) {
                    lock.notify();
                }
            }else{
                if(nickname!=null&&!nickname.equals(nicknameField.getText())){
                    nickname = nicknameField.getText();
                    synchronized (lock) {
                        lock.notify();
                    }

                }
            }
        }
    }

    @FXML
    public void onNumOfPlayersChoiceBoxChosenButton(ActionEvent event) {
        numPlayer = Integer.parseInt((String)numOfPlayersChoiceBox.getValue());
        synchronized (lock) {
            lock.notify();
        }
    }

    public void displayIncorrectNickname() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBoxGameMode.setVisible(false);
                vBoxNickname.setVisible(true);
                vBoxNumOfPlayers.setVisible(false);
                vBoxWaiting.setVisible(false);
                vBoxLobby.setVisible(false);

                nicknameInfoLabel.setText("Incorrect Nickname, please try again");
            }
        });

    }
    public void displayNicknameRequest(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBoxGameMode.setVisible(false);
                vBoxNickname.setVisible(true);
                vBoxNumOfPlayers.setVisible(false);
                vBoxWaiting.setVisible(false);
                vBoxLobby.setVisible(false);

                nicknameInfoLabel.setText("Insert your nickname");

            }
        });
    }
    public void displaySelectLobby(boolean canJoinLobby, boolean canRejoinLobby){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                joinlobbyButton.setVisible(canJoinLobby);
                rejoinlobbyButton.setVisible(canRejoinLobby);
                vBoxGameMode.setVisible(false);
                vBoxNickname.setVisible(false);
                vBoxNumOfPlayers.setVisible(false);
                vBoxWaiting.setVisible(false);
                vBoxLobby.setVisible(true);



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
                vBoxLobby.setVisible(false);
                numOfPlayersChoiceBox.getItems().add("2");
                numOfPlayersChoiceBox.getItems().add("3");

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
                vBoxLobby.setVisible(false);
            }
        });

    }
    /**
     * Displays Waiting in the lobby's Vbox
     */
    public void displayWaitingInTheLobbyMessage(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBoxGameMode.setVisible(false);
                vBoxNickname.setVisible(false);
                vBoxNumOfPlayers.setVisible(false);
                vBoxWaiting.setVisible(true);
                vBoxLobby.setVisible(false);
                lastLabel.setText("Waiting in the lobby..");
            }
        });
    }
}
