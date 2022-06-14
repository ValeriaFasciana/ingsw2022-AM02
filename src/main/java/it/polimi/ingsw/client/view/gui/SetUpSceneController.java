package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.NetworkHandler;
import it.polimi.ingsw.network.messages.clienttoserver.events.NicknameResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class SetUpSceneController {


    List<Integer> selectedAssistantCard;
    private GUIApp gui = null;
    private boolean gameMode;
    private String nickname;
    private Integer numPlayer;
    boolean isRetry = false;

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
        vBoxNickname.setVisible(true);
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
    }

    @FXML
    public void handleExpertButton(ActionEvent event) {
    }

    @FXML
    public void handleSendNicknameButton(ActionEvent event) {
        System.out.println("Sono qui");
        nickname = nicknameField.getText();

    }

    @FXML
    public void onNumOfPlayersChoiceBoxChosenButton(ActionEvent event) {
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
}
