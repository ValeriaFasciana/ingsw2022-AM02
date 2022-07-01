package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.Phase;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.concurrent.atomic.AtomicInteger;

public class CharactersController {
    public GridPane charactersPane;
    public AnchorPane chooseColour;
    public Button returnToPlayerBoard;
    public Text cost0;
    public Text cost1;
    public Text cost2;
    public Label info0;
    public Label info1;
    public Label info2;
    public Button stopButton;
    private GUIApp guiApp;
    private Object lock;
    private int chosenCard;
    private boolean hasUsedCharacterCard;
    private String currPlayer;
    private Map<Integer, CharacterCardData> characterCardsMap;
    private int toDiscard;
    private boolean toExclude;
    private Map<PawnColour,Integer> toMoveStudentsMap = new EnumMap<>(PawnColour.class);



    public void setGUI(GUIApp guiApp) {
        this.guiApp = guiApp;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    @FXML
    public void handleReturnButton() {
        guiApp.handleReturnButtonCharacters();
    }

    /**
     * Method to display the characters cards
     * @param boardData the board data
     * @param nickname nickname of the player who is viewing the cards
     */
    public void displayCharacterCards(BoardData boardData, String nickname) {
        characterCardsMap = boardData.getCharacters();
        currPlayer = boardData.getRoundData().getCurrentPlayerName();
        hasUsedCharacterCard = guiApp.hasUsedCharacterCard();
        int characterIndex = 0;
        for(Map.Entry<Integer,CharacterCardData> characterCardDataEntry : characterCardsMap.entrySet()) {
            int finalCharacterIndex = characterIndex;
            CharacterCardData characterCardData = characterCardDataEntry.getValue();
            Image img = new Image("gui/img/characterCards/character" + characterCardDataEntry.getKey() + ".jpg");
            Node characterCardNode = charactersPane.getChildren().filtered(cardPane -> cardPane.getId()!= null && cardPane.getId().equals("cardPane" + (finalCharacterIndex))).get(0);
            ((ImageView) (((AnchorPane) characterCardNode).getChildren().filtered(child -> child.getId().equals("card" + finalCharacterIndex)).get(0))).setImage(img);
            getCostText(characterIndex).setText(String.valueOf(characterCardData.getPrice()));
            getInfoLabel(characterIndex).setText(String.valueOf(characterCardData.getDescription()));
            if (!characterCardData.getStudents().values().stream().filter(numberOfStudents -> numberOfStudents > 0).toList().isEmpty()) {
                Iterator<Map.Entry<PawnColour, Integer>> studentMapIterator = characterCardData.getStudents().entrySet().iterator();
                Iterator<Node> studentsPositionIterator = ((AnchorPane) (((AnchorPane) characterCardNode).getChildren()
                        .filtered(child -> child.getId().equals("students" + finalCharacterIndex)).get(0)))
                        .getChildren()
                        .iterator();
                while (studentMapIterator.hasNext()) {
                    Map.Entry<PawnColour, Integer> studentEntry = studentMapIterator.next();
                    Integer numberOfStudents = studentEntry.getValue();
                    PawnColour studentColour = studentEntry.getKey();
                    Image studentImage = new Image("/gui/img/board/" + studentColour.toString().toLowerCase() + "Student.png");
                    for (int i = 0; i < numberOfStudents; i++) {
                        ImageView entranceSpot = (ImageView) studentsPositionIterator.next();
                        entranceSpot.setImage(studentImage);
                    }
                }


            }
            if (nickname.equals(currPlayer)
                    && boardData.getRoundData().getRoundPhase().equals(Phase.ACTION)
                    && !hasUsedCharacterCard && boardData.getPlayerBoards().get(nickname).getCoins() >= characterCardData.getPrice()) {
                glowNode(characterCardNode, Color.DARKBLUE);
                characterCardNode.setOnMouseClicked(e -> {
                    chosenCard = characterCardDataEntry.getKey();
                    guiApp.setHasUsedCharacterCard(true);
                    disableCharacterCard();
                    guiApp.setChosenCharacterCard(chosenCard);
                    e.consume();

                });
            }
            characterIndex++;
        }

//
//        while(characterIterator.hasNext()) { //If the array and map are the same size then you only need to check for one.  Otherwise you'll need to validate both iterators have a next
//            Integer characterId = characterIterator.next().getKey();
//            CharacterCardData characterCardData = characterCardsMap.get(characterId);
//            Node characterCardNode = characterCardNodeIterator.next();
//            Node studentsNode = studentsIterator.next();
//
//            characterIndex++;
//            if (((ImageView) characterCardNode).getImage() == null) {
//                Image img = new Image("gui/img/characterCards/character" + characterId + ".jpg");
//                ((ImageView) characterCardNode).setImage(img);
//            }
//
//            }

    }

    private Text getCostText(int characterIndex) {
        List<Text> costList= new ArrayList<Text>();
        costList.add(cost0);
        costList.add(cost1);
        costList.add(cost2);
        return costList.get(characterIndex);
    }

    private Label getInfoLabel(int characterIndex) {
        List<Label> infoList= new ArrayList<>();
        infoList.add(info0);
        infoList.add(info1);
        infoList.add(info2);
        return infoList.get(characterIndex);
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

    private void removeGrey(Node node){
        ColorAdjust colorAdjust=new ColorAdjust();
        colorAdjust.setSaturation(100);
        node.setEffect(colorAdjust);
    }
    private void disableCharacterCard(){
        charactersPane.getChildren().filtered(cardPane -> cardPane.getId()!= null && cardPane.getId().contains("cardPane")).forEach(node ->  {
            node.setEffect(null);
            node.setOnMouseClicked(e -> {
        });
        });

    }

    public void askColour(int toDiscard, boolean toExclude) {
        chooseColour.setVisible(true);
        this.toDiscard=toDiscard;
        this.toExclude=toExclude;

    }

    public void chooseStudentFromCharacter(int characterId, MovementDestination destination, int studentsToMove) {
        int characterIndex = characterCardsMap.keySet().stream().toList().indexOf(characterId);
        AtomicInteger movedStudents = new AtomicInteger();
        Node characterCardNode = charactersPane.getChildren().filtered(card -> card.getId()!= null && card.getId().equals("cardPane" + characterIndex)).stream().toList().get(0);
        ((AnchorPane) ((AnchorPane) characterCardNode).getChildren()
                .filtered(child -> child.getId().equals("students" + characterIndex)).get(0))
                .getChildren().forEach(node -> {
                    glowNode(node,Color.DARKBLUE);
                    node.setOnMouseClicked(e -> {
                        if(movedStudents.get() < studentsToMove) {
                            movedStudents.getAndIncrement();
                            if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/redStudent.png")) {
                                toMoveStudentsMap.put(PawnColour.RED,1);
                            }
                            if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/yellowStudent.png")) {
                                toMoveStudentsMap.put(PawnColour.YELLOW,1);
                            }
                            if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/greenStudent.png")) {
                                toMoveStudentsMap.put(PawnColour.GREEN,1);
                            }
                            if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/blueStudent.png")) {
                                toMoveStudentsMap.put(PawnColour.BLUE,1);
                            }
                            if (((ImageView) node).getImage().getUrl().contains("/gui/img/board/pinkStudent.png")) {
                                toMoveStudentsMap.put(PawnColour.PINK,1);
                            }
                        }
                        if(movedStudents.get() == studentsToMove) {
                            ((AnchorPane) ((AnchorPane) characterCardNode).getChildren()
                                    .filtered(child -> child.getId().equals("students" + characterIndex)).get(0))
                                    .getChildren().forEach(this::greyNode);
                        }
                        stopButton.setVisible(true);
                        stopButton.setOnMouseClicked(event -> {
                            guiApp.sendMoveFromCardResponse(characterId,toMoveStudentsMap,destination);
                            stopButton.setVisible(false);
                            toMoveStudentsMap.clear();
                        });

                    }
                    );
                });

    }



    public void exchangeStudentFromCharacter(int characterId, int numberOfStudents, MovementDestination from, MovementDestination to) {



    }

    @FXML
    public void handleRedButton(MouseEvent event) {
        guiApp.colourResponse(PawnColour.RED,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }
    @FXML
    public void handleYellowButton(MouseEvent event) {
        guiApp.colourResponse(PawnColour.YELLOW,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }
    @FXML
    public void handleGreenButton(MouseEvent event) {
        guiApp.colourResponse(PawnColour.GREEN,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }
    @FXML
    public void handleBlueButton(MouseEvent event) {
        guiApp.colourResponse(PawnColour.BLUE,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }
    @FXML
    public void handlePinkButton(MouseEvent event) {
        guiApp.colourResponse(PawnColour.PINK,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }



}



