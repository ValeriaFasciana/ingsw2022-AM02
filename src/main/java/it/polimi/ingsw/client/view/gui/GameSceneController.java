package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


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
    public AnchorPane professors;
    public AnchorPane coins;

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




    @FXML
    public Text messages;
    public void setMessages(String string){
        this.messages.setText(string);
    }

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
        charactersButton.setVisible(false);
        displayIsles();
        displayClouds();
        displayTowersOnPlayerBoard();
        displayEntrance();
        updateHall();

        if(!nick.equals(boardData.getRoundData().getCurrentPlayerName())){
            messages.setText(boardData.getRoundData().getCurrentPlayerName()+" is playing");
        }
        if(expertMode) {
            charactersButton.setVisible(true);
            displayCoins();
        }

    }

    @FXML
    public void initialize(BoardData boardData, Boolean expertMode, String nick) {
        this.boardData = boardData;
        this.nickname = nick;
        this.expertMode = expertMode;
        charactersButton.setVisible(false);
        displayIsles();
        displayClouds();
        displayTowersOnPlayerBoard();
        displayEntrance();

        if(!nick.equals(boardData.getRoundData().getCurrentPlayerName())){
            messages.setText(boardData.getRoundData().getCurrentPlayerName()+" is playing");

        }
        if(expertMode) {
            charactersButton.setVisible(true);
            displayCoins();
        }


    }


    public void selectStudentDestination() {
        messages.setText("Choose Student destination");
        glowNode(isles,Color.DARKBLUE);
        hall.setOnMouseClicked(event -> {
            chosenStudentDestination = "hall";
            synchronized (lock) {
                lock.notify();
            }
            event.consume();
        });

        for(Node node : isles.getChildren()) {
            if(node instanceof AnchorPane) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                    chosenStudentDestination = "isles";
                    chosenIsle=Integer.parseInt(node.getId().replace("island",""));
                    e.consume();
                    synchronized (lock) {
                        lock.notify();
                    }
                });
            }
        }
    }

    private void updateHall() {
        Map<PawnColour, Integer> hallMap = boardData.getPlayerBoards().get(nickname).getHall();
        List<PawnColour> professorsList = new ArrayList<>(boardData.getPlayerBoards().get(nickname).getProfessors());
        Image image = null;

        for(Node hallTable : hall.getChildren()){
            for(int i = 0; i < hallMap.get(PawnColour.valueOf(hallTable.getId().toUpperCase())); i++){
                Node hallSpot = ((AnchorPane)hallTable).getChildren().get(i);
                image = new Image("gui/img/board/" + hallTable.getId().toLowerCase()+"Student.png");
                ((ImageView) hallSpot).setImage(image);
            }
        }

        for(int i = 0; i < professorsList.size(); i++) {
            Image profImage = null;
            for(Node professor : professors.getChildren()) {
                if(professor instanceof ImageView) {
                    String profId = professor.getId();
                    if(profId.equals(professorsList.get(i).toString().toLowerCase()+"Prof")) {
                        if (((ImageView) professor).getImage() == null) {
                            profImage = new Image("/gui/img/board/"+professorsList.get(i).toString().toLowerCase()+"Professor.png");
                            ((ImageView) professor).setImage(profImage);
                            break;
                        }
                    }
                }
            }
        }
    }



    public void selectStudent() {
        messages.setText("Select a student from Entrance");
        for(Node node : entrance.getChildren()) {
            if(node instanceof ImageView) {
                node.setOnMouseClicked(e -> {
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
                    disableStudents();

                    synchronized (lock) {
                        lock.notify();
                    }
                });
            }
        }
    }

    public void selectAssistantCard(Set<Integer> availableAssistantIds) {
        this.messages.setText("Choose Assistant card");
        List<Integer> arr = new ArrayList<>(availableAssistantIds);
        Node grid=assistantCardPane.getChildren().get(0);


        if (grid instanceof GridPane) {
            for (Node card : ((GridPane) grid).getChildren()) {
                if (card.getId() != null) {
                    if (card instanceof ImageView && arr.contains(Integer.parseInt(card.getId().replace("card","")))) {
                        glowNode(card,Color.DARKBLUE);
                        card.setOnMouseClicked(e -> {
                            chosenCardId = Integer.parseInt(card.getId().replace("card",""));
                            disableCards(availableAssistantIds);
                            e.consume();
                            synchronized (lock) {
                                lock.notify();
                            }
                        });
                    }
                    else{
                        greyNode(card);
                    }
                }
            }
        }
    }

    public void disableCards(Set<Integer> availableAssistantIds) {
        List<Integer> arr = new ArrayList<>(availableAssistantIds);
        Node grid=assistantCardPane.getChildren().get(0);
        if (grid instanceof GridPane) {
            for (Node card : ((GridPane) grid).getChildren()) {
                if (card.getId() != null) {
                    if (card instanceof ImageView ) {
                        card.setOnMouseClicked(event -> {
                        });
                    }
                }
            }
        }
    }
    public void disableStudents() {
        for (Node node : entrance.getChildren()) {
            if (node instanceof ImageView) {
                node.setOnMouseClicked( e -> {
                });
            }
        }
    }
    public void selectMotherNature(Set<Integer> availableIsleIndexes) {
        messages.setText("Choose Mother nature destination");
            for(Node node : isles.getChildren()) {
                if(node instanceof AnchorPane) {
                    if (availableIsleIndexes.contains(Integer.parseInt(node.getId().replace("island","")))) {
                        glowNode(node,Color.DARKBLUE);
                        node.setOnMouseClicked(e -> {
                            chosenMotherNature= Integer.parseInt(node.getId().replace("island",""));
                            e.consume();
                            synchronized (lock) {
                                lock.notify();
                            }
                        });
                    }
                }
            }
    }

    public void selectCloud(Set<Integer> availableCloudIndexes) {
        messages.setText("Choose a Cloud");
        List<Integer> arr = new ArrayList<>(availableCloudIndexes);
            for(Node node : clouds.getChildren()) {
                if(node instanceof AnchorPane) {
                    if (arr.contains(Integer.parseInt(node.getId().replace("cloud","")))) {
                        glowNode(node,Color.DARKBLUE);
                        node.setOnMouseClicked(e -> {
                            chosenCloud=Integer.parseInt(node.getId().replace("cloud",""));
                            e.consume();
                            synchronized (lock) {
                                lock.notify();
                            }
                        });
                    }
                }
            }
        }


    private void displayEntrance() {
        Map<PawnColour, Integer> entranceMap = boardData.getPlayerBoards().get(nickname).getEntrance();
        Image image = null;
        int numEntrance = 0;
        ObservableList entranceChildren= entrance.getChildren();
        for(Map.Entry<PawnColour, Integer> studentEntry : entranceMap.entrySet()){
            for(int i = 0 ; i< studentEntry.getValue(); i++){
                Node entranceSpot = (Node) entranceChildren.get(numEntrance);
                image = new Image("/gui/img/board/"+studentEntry.getKey().toString().toLowerCase()+"Student.png");
                ((ImageView)entranceSpot).setImage(image);
                numEntrance++;
            }
        }


    }

    public void displayTowersOnPlayerBoard() {

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



        Image finalImage = image;
        for(int i = 0 ; i < towerCounter; i++){
            Node imageNode = ((GridPane)towers.getChildren().get(0)).getChildren().get(i);
            ((ImageView)imageNode).setImage(finalImage);
        }
    }

//    public void displayIsles() {
//       displayStudentsOnIsles();
//       displayTowersOnIsles();
//    }

    private void displayIsles() {
        List<IsleData> islesData = boardData.getGameBoard().getIsleCircle().getIsles();
        ObservableList<Node> observableIsles = isles.getChildren();
        Integer motherNaturePosition = boardData.getGameBoard().getMotherNaturePosition();
        for(IsleData isle : islesData){
           TowerColour towerColour = isle.getTowerColour();
           Map<PawnColour, Integer> studentMap= isle.getStudentMap();
           Node obsIsle = observableIsles.stream().filter(node -> node.getId().equals("island"+islesData.indexOf(isle))).toList().get(0);
            ((AnchorPane) obsIsle).getChildren().stream()
                    .filter(children -> children.getId() != null && children.getId().equals("students" + islesData.indexOf(isle)))
                    .forEach(node ->((GridPane) node).getChildren().forEach(text -> ((Text) text).setText(String.valueOf(studentMap.get(PawnColour.valueOf((text.getId().replaceAll("\\d", "").toUpperCase())))))));

           if(towerColour!= null) {
               Image towerImg = new Image("gui/img/board/" + towerColour.toString().toLowerCase() + "Tower.png");
               ((AnchorPane) obsIsle).getChildren().stream()
                       .filter(children -> children.getId() != null && children.getId().contains("tower"))
                       .forEach(node ->((ImageView) node).setImage(towerImg));
           }
           if(motherNaturePosition.equals(islesData.indexOf(isle))){
               Image motherNatureImage = new Image("gui/img/board/motherNature.png");
               ((AnchorPane) obsIsle).getChildren().stream()
                       .filter(children -> children.getId() != null && children.getId().equals("motherNature" + motherNaturePosition))
                       .forEach(node ->((ImageView) node).setImage(motherNatureImage));
           }

        }
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
                        if (node2 instanceof Text) {
                            ((Text) node2).setText(cloudMap.get(colour).toString());
                            i++;
                        }
                    }
                }

            }
        }
    }
    public void displayCoins() {
        for(Node node: coins.getChildren()) {
            if(node instanceof GridPane) {
                for(Node text : ((GridPane) node).getChildren()) {
                    if(text instanceof Text) {
                        String numCoins = String.valueOf(boardData.getPlayerBoards().get(nickname).getCoins());
                        ((Text) text).setText(numCoins);
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
                        if (node2 instanceof Text) {
                            ((Text) node2).setText(studentMap.get(colour).toString());
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

    @FXML
    public void handleCharactersButton(ActionEvent event) {
        gui.displayCharacterCards();
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
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(1f);
        borderGlow.setOffsetX(1f);
        borderGlow.setColor(color);
        borderGlow.setWidth(100);
        borderGlow.setHeight(100);
        nodeToGlow.setEffect(borderGlow);
    }

    /**
     * Turns given node colors to a more grey scale of colors (lower brightness), giving it a 'disabled appearance'
     * @param nodeToGrey node to be greyed
     */
    private void greyNode(Node nodeToGrey){
        ColorAdjust colorAdjust=new ColorAdjust();
        colorAdjust.setSaturation(-100);
        nodeToGrey.setEffect(colorAdjust);
    }
}

