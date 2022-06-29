package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CharacterCardData;
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

import java.util.Map;

public class CharactersController {
    public AnchorPane cards;
    public Button returnToPlayerBoard;
    public AnchorPane costs;
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

        int cardPosition = 0;
        for (int i : characterCardsMap.keySet()) {
            Image img = null;
            for (Node cardGrid : cards.getChildren()) { //per entrare in gridPane
                if (cardGrid instanceof GridPane) {
                    for (Node card : ((GridPane) cardGrid).getChildren()) { //per iterare sulle immagini nel gridpane
                        if (card instanceof ImageView) {
                            if (((ImageView) card).getImage() == null) {
                                int characterId = characterCardsMap.get(i).getId();
                                img = new Image("gui/img/characterCards/character" + characterId + ".jpg");
                                ((ImageView) card).setImage(img);
                                if (!hasUsedCharacterCard && currPlayer.equals(nickname)) {
                                    glowNode(card, Color.DARKBLUE);
                                }
                                else {
                                    greyNode(card);
                                }
                                card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                                    if (!hasUsedCharacterCard && currPlayer.equals(nickname) && (characterCardsMap.get(i).getPrice()) <=boardData.getPlayerBoards().get(nickname).getCoins()) {
                                        chosenCard = characterId;
                                        greyNode(card);
                                        guiApp.setHasUsedCharacterCard(true);
                                        guiApp.setChosenCharacterCard(chosenCard);
                                        e.consume();
                                    }
                                });
                                break;
                            }
                        }
                    }
                }
                String cost = "cost"+ cardPosition;
                String info = "info"+ cardPosition;
                if(cost.equals("cost0")) {
                    cost0.setText(String.valueOf(characterCardsMap.get(i).getPrice()));
                }
                if(cost.equals("cost1")) {
                    cost1.setText(String.valueOf(characterCardsMap.get(i).getPrice()));
                }
                if(cost.equals("cost2")) {
                    cost2.setText(String.valueOf(characterCardsMap.get(i).getPrice()));
                }

                if(info.equals("info0")) {
                    info0.setText(characterCardsMap.get(i).getDescription());
                }
                if(info.equals("info1")) {
                    info1.setText(characterCardsMap.get(i).getDescription());
                }
                if(info.equals("info2")) {
                    info2.setText(characterCardsMap.get(i).getDescription());
                }
                cardPosition++;
            }

        }
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



