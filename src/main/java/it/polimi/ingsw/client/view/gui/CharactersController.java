package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.Phase;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    private GUIApp guiApp;
    private Object lock;
    private int chosenCard;
    private boolean hasUsedCharacterCard;
    private String currPlayer;

    private int toDiscard;
    private boolean toExclude;

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
        Map<Integer, CharacterCardData> characterCardsMap = boardData.getCharacters();
        currPlayer = boardData.getRoundData().getCurrentPlayerName();
        hasUsedCharacterCard = guiApp.hasUsedCharacterCard();
        int characterIndex = 0;
        for(Map.Entry<Integer,CharacterCardData> characterCardDataEntry : characterCardsMap.entrySet()) {
            int finalCharacterIndex = characterIndex;
            CharacterCardData characterCardData = characterCardDataEntry.getValue();
            Image img = new Image("gui/img/characterCards/character" + finalCharacterIndex + ".jpg");
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
                    chosenCard = finalCharacterIndex;
                    guiApp.setHasUsedCharacterCard(true);
                    disableCharactherCard();
                    guiApp.setChosenCharacterCard(chosenCard);
                    e.consume();

                });
            } else {
                greyNode(characterCardNode);
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
    private void disableCharactherCard(){
        for (Node card : charactersPane.getChildren()) {
            if (card instanceof ImageView) {
                greyNode(card);
                card.setOnMouseClicked(e -> {
                });
            }
        }
    }

    public void askColour(int toDiscard, boolean toExclude) {
        chooseColour.setVisible(true);
        this.toDiscard=toDiscard;
        this.toExclude=toExclude;

    }

    @FXML
    public void handleRedButton(ActionEvent event) {
        guiApp.colourResponse(PawnColour.RED,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }
    @FXML
    public void handleYellowButton(ActionEvent event) {
        guiApp.colourResponse(PawnColour.YELLOW,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }
    @FXML
    public void handleGreenButton(ActionEvent event) {
        guiApp.colourResponse(PawnColour.GREEN,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }
    @FXML
    public void handleBlueButton(ActionEvent event) {
        guiApp.colourResponse(PawnColour.BLUE,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }
    @FXML
    public void handlePinkButton(ActionEvent event) {
        guiApp.colourResponse(PawnColour.PINK,toDiscard,toExclude);
        chooseColour.setVisible(false);
    }
}



