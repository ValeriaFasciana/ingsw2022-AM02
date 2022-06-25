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
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    public AnchorPane hall;
    public AnchorPane entrance;
    public AnchorPane isles;
    public AnchorPane clouds;

    private GUIApp gui;
    private Object lock;
    private BoardData boardData;
    String nickname;
    Boolean expertMode;
    private int chosenCardId;
    private int chosenStudentColour;
    private String chosenStudentDestination;

    private int chosenIsle;
    private int chosenMotherNature;
    private int chosenCloud;

    public int getChosenCloud() {
        return chosenCloud;
    }
    public int getChosenStudentColour() {
        return chosenStudentColour;
    }
    public String getChosenStudentDestination() { return chosenStudentDestination;}
    public int getChosenCardId() {
        return chosenCardId;
    }
    public int getChosenIsle() {return chosenIsle;}
    public int getChosenMotherNature() {
        return chosenMotherNature;
    }

    public void setGUI(GUIApp gui){
        this.gui=gui;
    }
    public void setLock(Object lock) {
        this.lock = lock;
    }

    @FXML
    public void updateBoard(BoardData boardData, boolean expertMode, String nick) {
        this.boardData = boardData;
        this.nickname = nick;
        this.expertMode = expertMode;
        displayIsles();
        displayClouds();
        displayTowers();
        displayEntrance();
    }

    @FXML
    public void initialize(BoardData boardData, Boolean expertMode, String nick) {
        this.boardData = boardData;
        this.nickname = nick;
        this.expertMode = expertMode;

        displayIsles();
        displayClouds();
        displayTowers();
        displayEntrance();
    }


    public void selectStudentDestination() {
        hall.setEffect(new Glow(0.5));
        isles.setEffect(new Glow(0.5));
        hall.setOnMouseClicked(event -> {
            chosenStudentDestination = "hall";
            fillHall();
            synchronized (lock) {
                lock.notify();
            }
            event.consume();
        });

        for(Node node : isles.getChildren()) {
            if(node instanceof AnchorPane) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    chosenStudentDestination = "isles";
                    if(node.getId().equals("island0")) {
                        chosenIsle = 0;
                    }
                    if(node.getId().equals("island1")) {
                        chosenIsle = 1;
                    }
                    if(node.getId().equals("island2")) {
                        chosenIsle = 2;
                    }
                    if(node.getId().equals("island3")) {
                        chosenIsle = 3;
                    }
                    if(node.getId().equals("island4")) {
                        chosenIsle = 4;
                    }
                    if(node.getId().equals("island5")) {
                        chosenIsle = 5;
                    }
                    if(node.getId().equals("island6")) {
                        chosenIsle = 6;
                    }
                    if(node.getId().equals("island7")) {
                        chosenIsle = 7;
                    }
                    if(node.getId().equals("island8")) {
                        chosenIsle = 8;
                    }
                    if(node.getId().equals("island9")) {
                        chosenIsle = 9;
                    }
                    if(node.getId().equals("island10")) {
                        chosenIsle = 10;
                    }
                    if(node.getId().equals("island11")) {
                        chosenIsle = 11;
                    }
                    e.consume();

                    synchronized (lock) {
                        lock.notify();
                    }
                });
            }
        }
    }

    private void fillHall() {
        for(Node hallTables : hall.getChildren()) {
            if(hallTables instanceof AnchorPane) {
                String hallId = hallTables.getId();
                Image image = null;
                if(chosenStudentColour == 0) {
                    image = new Image("/gui/img/board/redStudent.png");
                }
                if(chosenStudentColour == 1) {
                    image = new Image("/gui/img/board/yellowStudent.png");
                }
                if(chosenStudentColour == 2) {
                    image = new Image("/gui/img/board/greenStudent.png");
                }
                if(chosenStudentColour == 3) {
                    image = new Image("/gui/img/board/blueStudent.png");
                }
                if(chosenStudentColour == 4) {
                    image = new Image("/gui/img/board/pinkStudent.png");
                }
                for(Node hallSeat : ((AnchorPane) hallTables).getChildren()) {
                    if(hallSeat == null && hallId.contains(PawnColour.valueOf(chosenStudentColour).toString())) {
                        ((ImageView) hallSeat).setImage(image);
                        }
                    }
                }
            }
        }


    public void selectStudent() {
        chosenStudentColour=5;
        for(Node node : entrance.getChildren()) {
            if(node instanceof ImageView) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    if(((ImageView) node).getImage().getUrl().contains("/gui/img/board/redStudent.png")) {
                        chosenStudentColour = 0;
                    }
                    if(((ImageView) node).getImage().getUrl().contains("/gui/img/board/yellowStudent.png")) {
                        chosenStudentColour = 1;
                    }

                    if(((ImageView) node).getImage().getUrl().contains("/gui/img/board/greenStudent.png")) {
                        chosenStudentColour = 2;
                    }
                    if(((ImageView) node).getImage().getUrl().contains("/gui/img/board/blueStudent.png")) {
                        chosenStudentColour = 3;
                    }
                    if(((ImageView) node).getImage().getUrl().contains("/gui/img/board/pinkStudent.png")) {
                        chosenStudentColour = 4;
                    }
                    e.consume();

                    synchronized (lock) {
                        lock.notify();
                    }
                });
            }
        }
    }

    public void selectAssistantCard(Set<Integer> availableAssistantIds) {
        List<Integer> arr = new ArrayList<>(availableAssistantIds);

        for (int i = 0; i < arr.size(); i++) {
            int availableCard = arr.get(i);
            for (Node grid : assistantCardPane.getChildren()) {
                if (grid instanceof GridPane) {
                    for (Node card : ((GridPane) grid).getChildren()) {
                        String cardId = card.getId();
                        if (cardId != null) {
                            if (card instanceof ImageView && cardId.equals("card" + availableCard)) {
                                card.setEffect(new Glow(0.7));
                                card.setOnMouseClicked(e -> {
                                    chosenCardId = availableCard;
                                    disableCards(availableAssistantIds);
                                    e.consume();
                                    synchronized (lock) {
                                        lock.notify();
                                    }
                                });
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void disableCards(Set<Integer> availableAssistantIds) {
        List<Integer> arr = new ArrayList<>(availableAssistantIds);
        for (int i = 0; i < arr.size(); i++) {
            int availableCard = arr.get(i);
            for (Node grid : assistantCardPane.getChildren()) {
                if (grid instanceof GridPane) {
                    for (Node card : ((GridPane) grid).getChildren()) {
                        String cardId = card.getId();
                        if (cardId != null) {
                            if (card instanceof ImageView && cardId.equals("card" + availableCard)) {
                                card.setEffect(new Glow(0));
                                card.setOnMouseClicked(event -> {
                                });
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    public void selectMotherNature(ArrayList<Integer> availableIsleIndexes) {
        for (int i = 0; i < availableIsleIndexes.size(); i++) {
            int island = availableIsleIndexes.get(i);
            for(Node node : isles.getChildren()) {
                if(node instanceof AnchorPane) {
                    String isleId = node.getId();
                    if (isleId.equals("island" + island)) {
                        node.setEffect(new Glow(0.5));
                        node.setOnMouseClicked(e -> {
                            if(node.getId().equals("island0")) {
                                chosenMotherNature = 0;
                            }
                            if(node.getId().equals("island1")) {
                                chosenMotherNature = 1;
                            }
                            if(node.getId().equals("island2")) {
                                chosenMotherNature = 2;
                            }
                            if(node.getId().equals("island3")) {
                                chosenMotherNature = 3;
                            }
                            if(node.getId().equals("island4")) {
                                chosenMotherNature = 4;
                            }
                            if(node.getId().equals("island5")) {
                                chosenMotherNature = 5;
                            }
                            if(node.getId().equals("island6")) {
                                chosenMotherNature = 6;
                            }
                            if(node.getId().equals("island7")) {
                                chosenMotherNature = 7;
                            }
                            if(node.getId().equals("island8")) {
                                chosenMotherNature = 8;
                            }
                            if(node.getId().equals("island9")) {
                                chosenMotherNature = 9;
                            }
                            if(node.getId().equals("island10")) {
                                chosenMotherNature = 10;
                            }
                            if(node.getId().equals("island11")) {
                                chosenMotherNature = 11;
                            }
                            e.consume();

                            synchronized (lock) {
                                lock.notify();
                            }
                        });
                    }
                }
            }
        }
    }

    public void selectCloud(Set<Integer> availableCloudIndexes) {
        List<Integer> arr = new ArrayList<>(availableCloudIndexes);
        for (int i = 0; i < availableCloudIndexes.size(); i++) {
            int cloud = arr.get(i);
            for(Node node : clouds.getChildren()) {
                if(node instanceof AnchorPane) {
                    String cloudId = node.getId();
                    if (cloudId.equals("cloud" + cloud)) {
                        node.setEffect(new Glow(0.5));
                        node.setOnMouseClicked(e -> {
                            if(node.getId().equals("cloud0")) {
                                chosenCloud = 0;
                            }
                            if(node.getId().equals("cloud1")) {
                                chosenCloud = 1;
                            }
                            if(node.getId().equals("cloud2")) {
                                chosenCloud = 2;
                            }
                            e.consume();
                            synchronized (lock) {
                                lock.notify();
                            }
                        });
                    }
                }
            }
        }
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
                            ((ImageView)entranceSpot).autosize();
                            numEntrance++;
                            break;
                        }
                    }
                }
            }
        }
    }

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
                        ((ImageView)imageNode).autosize();
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
                    ((ImageView)node).autosize();
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

