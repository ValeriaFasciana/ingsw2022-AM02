package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.PopupWindow;

import java.util.*;

public class GameSceneController {
    public AnchorPane mainPane;

    public AnchorPane assistantCardPane;
    public AnchorPane islesAndCloudsPane;
    public AnchorPane cloud0;
    public AnchorPane cloud1;
    public AnchorPane cloud2;
    public AnchorPane island0;
    public AnchorPane island1;
    public AnchorPane island2;
    public AnchorPane island3;
    public AnchorPane island4;
    public AnchorPane island5;
    public AnchorPane island6;
    public AnchorPane island7;
    public AnchorPane island8;
    public AnchorPane island9;
    public AnchorPane island10;
    public AnchorPane island11;
    public AnchorPane towers;
    public Button otherPlayerBoardsButton;
    public Button charactersButton;
    public AnchorPane blueHallStudents;
    public AnchorPane pinkHallStudents;
    public AnchorPane yellowHallStudents;
    public AnchorPane redHallStudents;
    public AnchorPane professors;
    public AnchorPane greenHallStudents;
    public AnchorPane entrance;

    Color colorToGlow= Color.CYAN;

    private GUIApp gui;
    private Object lock;

    public void setGUI(GUIApp gui){
        this.gui=gui;
    }
    public void setLock(Object lock) {
        this.lock = lock;
    }
    private BoardData boardData;
    String nickname;
    Boolean expertMode;

    @FXML
    public void initialize(BoardData boardData, Boolean expertMode, String nickname) {
        this.boardData = boardData;
        this.nickname = nickname;
        this.expertMode = expertMode;

        displayIsles();
        displayClouds();
        displayTowers();
        displayEntrance();
    }

    private void displayEntrance() {
        Map<PawnColour, Integer> entranceMap = boardData.getPlayerBoards().get(nickname).getEntrance();
        Image image = null;

        int numEntrance = 0;

        for(int colour = 0; colour < PawnColour.values().length; colour++) { //cicla sui colori
            if(colour == 0) {
                image = new Image("gui/img/board/redStudent.png");
            }
            if(colour == 1) {
                image = new Image("gui/img/board/yellowStudent.png");
            }
            if(colour == 2) {
                image = new Image("gui/img/board/greenStudent.png");
            }
            if(colour == 3) {
                image = new Image("gui/img/board/blueStudent.png");
            }
            if(colour == 4) {
                image = new Image("gui/img/board/pinkStudent.png");
            }

            for (int i = 0; i < entranceMap.get(PawnColour.valueOf(colour)); i++) { //cicla su numero di studenti per colore
                for (Node entranceSpot : entrance.getChildren()) { //cicla sulle immagini dei posti in entrance
                    if (entranceSpot instanceof ImageView) {
                        String entranceSpotId = entranceSpot.getId();

                        if (entranceSpotId != null && entranceSpotId.equals("entranceStudent"+numEntrance)) {
                            ((ImageView)entranceSpot).setImage(image);
                            numEntrance++;
                            break;
                        }
                    }
                    //when the students per colour are finished, id goes to the next colour
                    }
                }
            }
        }

/*
    public void updateBoard(BoardData boardData, Boolean expertMode, String nickname) {

    }

 */

    public void displayTowers() {

        TowerColour colour = boardData.getPlayerBoards().get(nickname).getTowerColour();
        int towerCounter = boardData.getPlayerBoards().get(nickname).getTowerCounter();
        Image image = null;
        if(colour == TowerColour.WHITE) {
            image = new Image("gui/img/board/whiteTower.png");
        }
        if(colour == TowerColour.BLACK) {
            image = new Image("gui/img/board/blackTower.png");
        }
        if(colour == TowerColour.GREY) {
            image = new Image("gui/img/board/greyTower.png");
        }

            for(Node gridpane : towers.getChildren()) {
                if (gridpane instanceof GridPane) {
                    int i = 0;
                    for(Node imageNode : ((GridPane) gridpane).getChildren()) {
                        if (imageNode instanceof ImageView) {
                            ((ImageView)imageNode).setImage(image);
                        }

                        i++; //prints the right quantity of towers
                        if (i == towerCounter) {
                            return; //when it gets to 6, id doesn't print any more towers
                        }
                    }
                }
            }
        }
    public void displayIsles() {
       displayStudentsOnIsles();
       displayMotherNature();
    }

    private void displayMotherNature() {
        int motherNaturePosition = boardData.getGameBoard().getMotherNaturePosition();
        AnchorPane isle = getIslandPane(motherNaturePosition);
        for (Node node : isle.getChildren()) {
            String nodeId = node.getId();
            if(nodeId != null) {
                if (node instanceof ImageView && nodeId.equals("motherNature" + motherNaturePosition)) {
                    ((ImageView) node).setImage(new Image("gui/img/board/motherNature.png"));
                }
            }
        }
    }

    public void displayClouds() {
        int numClouds = boardData.getGameBoard().getClouds().size();
        if(numClouds == 2) {
            cloud2.setVisible(false);
        }

        for(int cont = 0; cont<numClouds; cont++) {
            AnchorPane cloud = getCloudPane(cont);
            Map<PawnColour, Integer> cloudMap = boardData.getGameBoard().getClouds().get(cont).getStudentMap();

            for (Node node1 : cloud.getChildren()) {
                if (node1 instanceof GridPane) {
                    int i = 0;

                    for (Node node2 : ((GridPane) node1).getChildren()) {
                        PawnColour colour = PawnColour.valueOf(i);
                        //inside gridpane
                        if (node2 instanceof TextArea) {
                            ((TextArea) node2).setText(cloudMap.get(colour).toString());
                            i++;
                        }
                    }
                }

            }
        }
    }

    private void displayStudentsOnIsles() {
        int numIsles = boardData.getGameBoard().getIsleCircle().getIsles().size();

        for(int cont = 0; cont<numIsles; cont++) {
            AnchorPane isle = getIslandPane(cont);
            Map<PawnColour, Integer> studentMap= boardData.getGameBoard().getIsleCircle().getIsles().get(cont).getStudentMap();

            for(Node node1 : isle.getChildren()){
                // inside island
                if(node1 instanceof GridPane){
                    int i = 0;

                    for (Node node2 : ((GridPane) node1).getChildren()) {
                        PawnColour colour = PawnColour.valueOf(i);
                        //inside gridpane
                        if (node2 instanceof TextArea) {
                            ((TextArea) node2).setText(studentMap.get(colour).toString());
                            i++;
                        }
                    }
                }
            }
        }
    }

    public void selectAssistantCard(Set<Integer> availableAssistantIds) {

        Set<Integer> availableAssistantId = boardData.getPlayerBoards().get(nickname).getDeck().keySet();
        Iterator<Integer> iterator = availableAssistantId.iterator();

        for(Node grid : assistantCardPane.getChildren()) {
            if(grid instanceof GridPane){
                for(Node card: ((GridPane) grid).getChildren()) {
                    String cardId = card.getId();
                    if(cardId != null) {
                        if (card instanceof ImageView && cardId.equals("card" + iterator.next())) {
                            glowNode(card, colorToGlow);
                        }
                    }
                }
            }
        }
    }


    @FXML
    public void handleOtherPlayerBoardsButton(ActionEvent event) {
        gui.displayOtherPlayerBoards();
    }


    private AnchorPane getIslandPane(int i) {
        ArrayList<AnchorPane> islands = new ArrayList<>();
        islands.add(island0);
        islands.add(island1);
        islands.add(island2);
        islands.add(island3);
        islands.add(island4);
        islands.add(island5);
        islands.add(island6);
        islands.add(island7);
        islands.add(island8);
        islands.add(island9);
        islands.add(island10);
        islands.add(island11);

        return islands.get(i);
    }

    private AnchorPane getCloudPane(int i) {
        ArrayList<AnchorPane> clouds = new ArrayList<>();
        clouds.add(cloud0);
        clouds.add(cloud1);
        clouds.add(cloud2);

        return clouds.get(i);
    }

    /**
     * Applies a glowing effect on given node
     * @param nodeToGlow node that will glow
     * @param color glowing color
     */
    private void glowNode(Node nodeToGlow,Color color){
        DropShadow borderGlow = new DropShadow();
        int depth = 40;
        borderGlow.setColor(color);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        nodeToGlow.setEffect(borderGlow);
    }

    /**
     * Turns given node colors to a more grey scale of colors (lower brightness), giving it a 'disabled appearance'
     * @param nodeToGrey node to be greyed
     */
    private void greyNode(Node nodeToGrey){
        ColorAdjust colorAdjust=new ColorAdjust();
        colorAdjust.setBrightness(0.4);
        nodeToGrey.setEffect(colorAdjust);
    }
}

