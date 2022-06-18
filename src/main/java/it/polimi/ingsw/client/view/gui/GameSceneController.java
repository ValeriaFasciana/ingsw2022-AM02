package it.polimi.ingsw.client.view.gui;

import javafx.fxml.FXML;

import javafx.scene.layout.AnchorPane;

public class GameSceneController {
    public AnchorPane mainPane;
    public AnchorPane playerBoardPane;
    public AnchorPane assistantCardPane;
    public AnchorPane islesAndCloudsPane;

    private GUIApp gui;
    private Object lock;

    public void setGUI(GUIApp gui){
        this.gui=gui;
    }
    public void setLock(Object lock) {
        this.lock = lock;
    }

    @FXML
    public void initialize() {

    }

    public void displayAvailableAssistantCards() {

    }
}
