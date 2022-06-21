package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Map;

public class GameSceneController {
    public AnchorPane mainPane;
    public AnchorPane playerBoardPane;
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



    private GUIApp gui;
    private Object lock;

    public void setGUI(GUIApp gui){
        this.gui=gui;
    }
    public void setLock(Object lock) {
        this.lock = lock;
    }

    @FXML
    public void initialize(BoardData boardData, Boolean expertMode) {
        displayIsles(boardData);
        displayClouds(boardData);
        displayTowers(boardData);
    }

    private void displayTowers(BoardData boardData) {
        /*
        TowerColour colour = boardData.getPlayerBoards().
            for(Node node : towers.getChildren()) {
                if (node instanceof GridPane) {
                    for(Node node1 : ((GridPane) node).getChildren()) {
                        if (node1 instanceof ImageView) {
                        //da aggiustare per selezionare il colore giusto
                            ((ImageView) node1).setImage(new Image("gui/img/board/whiteTower.png"));
                        }
                    }
                }
            }
        }
         */
    }

    private void displayIsles(BoardData boardData) {
       displayStudents(boardData);
       displayMotherNature(boardData);
    }

    private void displayMotherNature(BoardData boardData) {
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

    private void displayClouds(BoardData boardData) {
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

    private void displayStudents(BoardData boardData) {
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
}

