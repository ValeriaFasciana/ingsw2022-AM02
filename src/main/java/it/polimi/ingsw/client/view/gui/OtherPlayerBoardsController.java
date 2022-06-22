package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class OtherPlayerBoardsController {

    public AnchorPane otherPlayerboardsPane;
    public AnchorPane playerBoardPlayer2;
    public AnchorPane playerBoardPlayer3;
    public Button returnToOwnPlayerBoard;
    private GUIApp gui;
    private Object lock;

    public void setGUI(GUIApp gui) {
        this.gui = gui;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    @FXML
    public void handleReturnToOwnPlayerboardButton(ActionEvent event) {
        gui.displayYourBoard();
    }

    public void displayOtherPlayerBoards(BoardData boardData, boolean expertMode, String nickname) {
        System.out.println("Sono in displayOtherPlayerBoards");
    }
}
