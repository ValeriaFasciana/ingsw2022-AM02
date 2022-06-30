package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.shared.enums.PawnColour;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CharactersController {
    public AnchorPane cards;
    public Button returnToPlayerBoard;
    public Text cost0;
    public Text cost1;
    public Text cost2;
    public Label info0;
    public Label info1;
    public Label info2;
    public AnchorPane students;
    private GUIApp guiApp;
    private Object lock;

    private int chosenCard;
    private int chosenStudentColour = 5;
    private boolean hasUsedCharacterCard;
    private String currPlayer;

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

    public void displayCharacterCards(BoardData boardData, String nickname) {
        Map<Integer, CharacterCardData> characterCardsMap = boardData.getCharacters();
        currPlayer = boardData.getRoundData().getCurrentPlayerName();
        hasUsedCharacterCard = guiApp.hasUsedCharacterCard();

        Iterator<Map.Entry<Integer, CharacterCardData>> characterIterator = characterCardsMap.entrySet().iterator();
        Iterator<Node> characterCardNodeIterator = cards.getChildren().iterator();
        Iterator<Node> studentsIterator = students.getChildren().iterator();
        int characterIndex = 0;

        while(characterIterator.hasNext()) { //If the array and map are the same size then you only need to check for one.  Otherwise you'll need to validate both iterators have a next
            Integer characterId = characterIterator.next().getKey();
            CharacterCardData characterCardData = characterCardsMap.get(characterId);
            Node characterCardNode = characterCardNodeIterator.next();
            Node studentsNode = studentsIterator.next();
            getCostText(characterIndex).setText(String.valueOf(characterCardData.getPrice()));
            getInfoLabel(characterIndex).setText(String.valueOf(characterCardData.getDescription()));
            characterIndex++;
            if (((ImageView) characterCardNode).getImage() == null) {
                Image img = new Image("gui/img/characterCards/character" + characterId + ".jpg");
                ((ImageView) characterCardNode).setImage(img);
            }
            if(!characterCardData.getStudents().values().stream().filter(numberOfStudents -> numberOfStudents>0).toList().isEmpty()){
                Iterator<Map.Entry<PawnColour, Integer>> studentMapIterator = characterCardData.getStudents().entrySet().iterator();
                Iterator<Node> studentsPositionIterator = ((AnchorPane) studentsNode).getChildren().iterator();
                while(studentMapIterator.hasNext()){
                    Map.Entry<PawnColour,Integer> studentEntry = studentMapIterator.next();
                    Integer numberOfStudents = studentEntry.getValue();
                    PawnColour studentColour = studentEntry.getKey();
                    Image studentImage = new Image("/gui/img/board/" + studentColour.toString().toLowerCase() + "Student.png");
                    for(int i = 0 ; i< numberOfStudents;i++){
                        ImageView entranceSpot = (ImageView) studentsPositionIterator.next();
                        entranceSpot.setImage(studentImage);
                    }
                }
            }

            characterCardNode.setOnMouseClicked(e -> {
                    System.out.println("Sono qui in card event handler");
                    if (!hasUsedCharacterCard && currPlayer.equals(nickname) && characterCardData.getPrice() <= boardData.getPlayerBoards().get(nickname).getCoins()) {
                        glowNode(characterCardNode, Color.DARKBLUE);
                        chosenCard = characterId;
                        System.out.println("ho mandato chosen card");
                        greyNode(characterCardNode);
                        guiApp.setHasUsedCharacterCard(true);
                        guiApp.setChosenCharacterCard(chosenCard);
                        e.consume();
                    }
                });
        }
        
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


    private void glowNode(Node nodeToGlow,Color color){
        DropShadow borderGlow= new DropShadow();
        borderGlow.setOffsetY(1f);
        borderGlow.setOffsetX(1f);
        borderGlow.setColor(color);
        borderGlow.setWidth(100);
        borderGlow.setHeight(100);
        nodeToGlow.setEffect(borderGlow);
    }

    private void greyNode(Node nodeToGrey){
        ColorAdjust colorAdjust=new ColorAdjust();
        colorAdjust.setSaturation(-100);
        nodeToGrey.setEffect(colorAdjust);
    }
}



