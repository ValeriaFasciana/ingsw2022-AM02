package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CharacterCardData;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Map;

public class CharactersController {
    public AnchorPane cards;
    public Button returnToPlayerBoard;

    public void setGUI(GUIApp guiApp) {
    }

    public void setLock(Object lock) {
    }

    public void displayCharacterCards(BoardData boardData) {
        Map<Integer, CharacterCardData> characterCardsMap = boardData.getCharacters();

        for(int i :characterCardsMap.keySet()) {
            Image img = null;
            for(Node cardGrid : cards.getChildren()) { //per entrare in gridPane
                if(cardGrid instanceof  GridPane) {
                    for(Node card : ((GridPane) cardGrid).getChildren()) { //per iterare sulle immagini nel gridpane
                        if (card instanceof ImageView) {
                            if (((ImageView) card).getImage() == null) {
                                int characterId = characterCardsMap.get(i).getId();
                                System.out.println("gui/img/characterCards/character" + characterId + ".jpg");
                                img = new Image("gui/img/characterCards/character" + characterId + ".jpg");
                                ((ImageView) card).setImage(img);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
