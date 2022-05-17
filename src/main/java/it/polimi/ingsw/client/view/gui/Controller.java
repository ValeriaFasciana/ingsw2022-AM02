package it.polimi.ingsw.client.view.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private GridPane grid;

    @FXML
    private Label welcomeText;

    @FXML private Text actiontarget;

    public void startGame(ActionEvent e) {
        System.out.print("GameStart");
    }
    public void checkUsername(ActionEvent e) {
        if(this.actiontarget.getText().isEmpty()) {
            System.out.print("Name cannot be blank");
        }
    }
}