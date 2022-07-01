package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


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
    private String nickname;
    Boolean expertMode;
    private int chosenStudentColour;
    private String chosenStudentDestination;
    private int chosenIsle;

    private Map<PawnColour, Boolean> hallColourAvailability;
    @FXML
    public Text messages;
    @FXML
    public Text disconnectLobby;
    @FXML
    public Text Nickname;

    public int getChosenStudentColour() {return chosenStudentColour;}
    public String getChosenStudentDestination() { return chosenStudentDestination;}
    public int getChosenIsle() {return chosenIsle;}
    public void setGUI(GUIApp gui){this.gui=gui;}
    public void setLock(Object lock) {this.lock = lock;}

    /**
     * Method to update the display of the board after an event or action
     * @param boardData the board data
     * @param expertMode if it's true, it display the coins
     * @param nick nickname of the board to dislay
     */
    @FXML
    public void updateBoard(BoardData boardData, boolean expertMode, String nick) {
        try{
            this.boardData = boardData;
            this.nickname = nick;
            this.expertMode = expertMode;
            charactersButton.setVisible(false);
            displayIsles();
            displayClouds();
            displayTowersOnPlayerBoard();
            displayEntrance();
            displayAssistant();
            Nickname.setText(this.nickname);
            updateHall();
            if(expertMode) {
                charactersButton.setVisible(true);
                displayCoins();
            }else{
                coins.setVisible(false);
            }
            if(!nick.equals(boardData.getRoundData().getCurrentPlayerName())){
                messages.setText(boardData.getRoundData().getCurrentPlayerName()+" is playing");

            }
        }catch(NullPointerException exception){
            System.out.println("Exception in boardUpdate: "+exception.getMessage());
        }
    }

    /**
     * Method to display the isles
     */
    private void displayIsles() {
        List<IsleData> islesData = boardData.getGameBoard().getIsleCircle().getIsles();
        ObservableList<Node> observableIsles = isles.getChildren();
        Integer motherNaturePosition = boardData.getGameBoard().getMotherNaturePosition();
        isles.getChildren().forEach(node -> node.setVisible(false));
        for(IsleData isle : islesData){
            TowerColour towerColour = isle.getTowerColour();
            Map<PawnColour, Integer> studentMap= isle.getStudentMap();
            Node obsIsle = observableIsles.stream().filter(node -> node.getId().equals("island"+islesData.indexOf(isle))).toList().get(0);
            obsIsle.setVisible(true);
            if(isle.getSize()> 1){
                ((AnchorPane) obsIsle).getChildren().stream()
                        .filter(children -> children.getId() != null && children.getId().equals("islandImage" + islesData.indexOf(isle)))
                        .forEach(node -> ((ImageView)node).setImage(new Image("gui/img/board/mergedIsles.png")));
            }
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

    /**
     * Method to display the clouds
     */
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
                        if (node2 instanceof Text) {
                            ((Text) node2).setText(cloudMap.get(colour).toString());
                            i++;
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to display the towers on the playerboard
     */
    public void displayTowersOnPlayerBoard() {
        int towerCounter = boardData.getPlayerBoards().get(nickname).getTowerCounter();
        Image image = new Image("gui/img/board/"+boardData.getPlayerBoards().get(nickname).getTowerColour().toString().toLowerCase()+"Tower.png");
        for(int i = 0 ; i < towerCounter; i++){
            Node imageNode = ((GridPane)towers.getChildren().get(0)).getChildren().get(i);
            ((ImageView)imageNode).setImage(image);
        }
    }

    /**
     * Method to display the students in the player's entrance
     */
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

    /**
     * Method to set invisible the chosen assistant cards
     */
    private void displayAssistant() {
        Node grid=assistantCardPane.getChildren().get(0);
        for (Node card : ((GridPane) grid).getChildren()) {
            if (!boardData.getPlayerBoards().get(nickname).getDeck().keySet().contains(Integer.parseInt(card.getId().replace("card",""))) ) {
                card.setVisible(false);
            }
        }
    }

    /**
     * Method to update the halls when a student is chosen, and the professors
     */
    private void updateHall() {
        Map<PawnColour, Integer> hallMap = boardData.getPlayerBoards().get(nickname).getHall();
        Set<PawnColour> professorsSet = boardData.getPlayerBoards().get(nickname).getProfessors();

        for(Node hallTable : hall.getChildren()){
            for(int i = 0; i < hallMap.get(PawnColour.valueOf(hallTable.getId().toUpperCase())); i++){
                Node hallSpot = ((AnchorPane)hallTable).getChildren().get(i);
                Image image = new Image("gui/img/board/" + hallTable.getId().toLowerCase()+"Student.png");
                ((ImageView) hallSpot).setImage(image);
            }
        }

        for(PawnColour colour : professorsSet) {
            Image profImage = new Image("/gui/img/board/"+colour.toString().toLowerCase()+"Professor.png");
            professors.getChildren().stream().filter(child -> (child.getId().equals((colour.toString().toLowerCase()+"Prof")))).forEach(prof->((ImageView)prof).setImage(profImage));
        }
    }

    /**
     * Method to display the coins of a player
     */
    public void displayCoins() {
        Node node = coins.getChildren().get(0);
        Node text = ((GridPane) node).getChildren().get(1);
        String numCoins = String.valueOf(boardData.getPlayerBoards().get(nickname).getCoins());
        ((Text) text).setText(numCoins);
    }

    /**
     * Method to make the assistant cards interactive
     * @param availableAssistantIds available indexes of the clickable cards
     */
    public void selectAssistantCard(Set<Integer> availableAssistantIds) {
        this.messages.setText("Choose Assistant card");
        List<Integer> arr = new ArrayList<>(availableAssistantIds);
        Node grid=assistantCardPane.getChildren().get(0);
        for (Node card : ((GridPane) grid).getChildren()) {
            if (card.getId() != null) {
                if (card instanceof ImageView && arr.contains(Integer.parseInt(card.getId().replace("card","")))) {
                    glowNode(card,Color.DARKBLUE);
                    card.setOnMouseClicked(e -> {
                        int chosenCardId = Integer.parseInt(card.getId().replace("card",""));
                        disableCards(availableAssistantIds);
                        e.consume();
                        gui.askAssistantResponse(chosenCardId);
                    });
                }
                else{
                    greyNode(card);
                }
            }
        }
    }

    /**
     * Method to disable the possibility to click on the assistant cards
     * @param availableAssistantIds available indexes of assistant cards
     */
    public void disableCards(Set<Integer> availableAssistantIds) {
        List<Integer> arr = new ArrayList<>(availableAssistantIds);
        Node grid=assistantCardPane.getChildren().get(0);
        ((GridPane) grid).getChildren().forEach(card -> ((GridPane) grid).getChildren());
    }

    /**
     * Method to choose a student
     * @param hallColourAvailability
     */
    public void selectStudent(Map<PawnColour, Boolean> hallColourAvailability) {
        messages.setText("Select a student from Entrance");
        this.hallColourAvailability=hallColourAvailability;
        try{
            entrance.getChildren().forEach(node ->{
                glowNode(node,Color.DARKBLUE);
                node.setOnMouseClicked(e -> {
                    if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/redStudent.png")) {
                        chosenStudentColour = 0;
                    }
                    if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/yellowStudent.png")) {
                        chosenStudentColour = 1;
                    }
                    if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/greenStudent.png")) {
                        chosenStudentColour = 2;
                    }
                    if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/blueStudent.png")) {
                        chosenStudentColour = 3;
                    }
                    if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/pinkStudent.png")) {
                        chosenStudentColour = 4;
                    }
                    disableStudents();
                    selectStudentDestination();
                    e.consume();

                });
            });
        }catch (NullPointerException exception){
            System.out.println("Exception in selectStudent: "+ exception.getMessage());
        }
    }

    /**
     * Method to select a student destination
     */

    public void selectStudentDestination() {
        messages.setText("Choose Student destination");
        glowNode(isles,Color.DARKBLUE);
        if(hallColourAvailability.get(PawnColour.valueOf(chosenStudentColour))){
            hall.setBorder(new Border(new BorderStroke(Color.DARKBLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5, 5, 5, 5))));
            hall.setOnMouseClicked(event -> {
                chosenStudentDestination = "hall";
                gui.studentDestinationResponse();
                event.consume();
            });
        }
        for(Node node : isles.getChildren()) {
            if(node instanceof AnchorPane) {
                node.setOnMouseClicked( e -> {
                    chosenStudentDestination = "isles";
                    chosenIsle=Integer.parseInt(node.getId().replace("island",""));
                    gui.studentDestinationResponse();
                    e.consume();
                });
            }
        }
    }

    /**
     * Method to disable the possibility to click on the students
     */
    public void disableStudents() {
        entrance.getChildren().forEach(student -> {
            student.setEffect(null);
            student.setOnMouseClicked( e -> {
        });
        });
    }

    /**
     * Method to select mother nature destination
     * @param availableIsleIndexes available isle indexes to move mother nature to
     */
    public void selectMotherNature(Set<Integer> availableIsleIndexes) {
        messages.setText("Choose Mother nature destination");
        for(Integer index : availableIsleIndexes){
            isles.getChildren().stream().filter(node -> node.getId().equals("island"+index.toString())).forEach(node -> {
                glowNode(node,Color.DARKBLUE);
                node.setOnMouseClicked(e -> {
                    Integer chosenMotherNature= Integer.parseInt(node.getId().replace("island",""));
                    gui.moveMotherNatureResponse(chosenMotherNature);
                    e.consume();
                });
            });
        }
    }

    /**
     * Method to choose a cloud
     * @param availableCloudIndexes
     */
    public void selectCloud(Set<Integer> availableCloudIndexes) {
        messages.setText("Choose a Cloud");
        List<Integer> arr = new ArrayList<>(availableCloudIndexes);
            for(Node node : clouds.getChildren()) {
                if(node instanceof AnchorPane) {
                    if (arr.contains(Integer.parseInt(node.getId().replace("cloud","")))) {
                        glowNode(node,Color.DARKBLUE);
                        node.setOnMouseClicked(e -> {
                            Integer chosenCloud=Integer.parseInt(node.getId().replace("cloud",""));
                            gui.cloudResponse(chosenCloud);
                            e.consume();

                        });
                    }
                }
            }
        }

    /**
     * Initialize the other playerboard scene when clicked
     * @param event mouse click
     */
    @FXML
    public void handleOtherPlayerBoardsButton(ActionEvent event) {
        gui.instantiateOtherPlayerboardsScene();
    }

    /**
     * Initialize the character scene when clicked
     * @param event mouse click
     */
    @FXML
    public void handleCharactersButton(ActionEvent event) {
        gui.instantiateCharacterCardsScene();
    }

    /**
     * Method that creates an array of the anchor panes of the clouds
     * @param i index of the selected cloud
     * @return the anchor pane of the selected cloud
     */
    private AnchorPane getCloudPane(int i) {
        ArrayList<AnchorPane> clouds = new ArrayList<>();
        clouds.add(cloud0);
        clouds.add(cloud1);
        clouds.add(cloud2);

        return clouds.get(i);
    }

    /**
     * Method to handle when a player gets disconnected
     * @param disconnectedPlayerName
     */
    public void playerDisconnected(String disconnectedPlayerName) {
                disconnectLobby.setText(disconnectedPlayerName+" disconnected");
                disconnectLobby.setFill(Color.BLACK);
                disconnectLobby.setFont(Font.font(null, FontWeight.SEMI_BOLD, 40));
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), disconnectLobby);
                fadeTransition.setFromValue(2.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.setCycleCount(1);
                fadeTransition.play();

            }




    /**
     * Applies a glowing effect on given node
     * @param nodeToGlow node that will glow
     * @param color glowing color
     */
    private void glowNode(Node nodeToGlow,Color color){
        DropShadow borderGlow= new DropShadow();
        borderGlow.setColor(color);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        borderGlow.setWidth(15);
        borderGlow.setHeight(15);
        borderGlow.setSpread(5);
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

