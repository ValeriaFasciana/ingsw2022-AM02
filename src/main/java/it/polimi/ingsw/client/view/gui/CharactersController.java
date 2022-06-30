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

        int cardPosition = 0;
        for (int i : characterCardsMap.keySet()) {
            Image img = null;
            for (Node card : cards.getChildren()) { //per iterare sulle immagini nel gridpane
                if (card instanceof ImageView) {
                    if (((ImageView) card).getImage() == null) {
                        int characterId = characterCardsMap.get(i).getId();
                        img = new Image("gui/img/characterCards/character" + characterId + ".jpg");
                        ((ImageView) card).setImage(img);

                        card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            System.out.println("Sono qui in card event handler");
                            if (!hasUsedCharacterCard && currPlayer.equals(nickname) && (characterCardsMap.get(i).getPrice()) <= boardData.getPlayerBoards().get(nickname).getCoins()) {
                                glowNode(card, Color.DARKBLUE);
                                chosenCard = characterId;
                                System.out.println("ho mandato chosen card");
                                greyNode(card);
                                guiApp.setHasUsedCharacterCard(true);
                                guiApp.setChosenCharacterCard(chosenCard);
                                e.consume();
                            }
                        });
                        displayCostsAndInfos(i, cardPosition, characterCardsMap);

                        for (Integer value: characterCardsMap.get(i).getStudents().values()) {
                            if (value != 0) {
                                displayStudentsOnCards(i, cardPosition, characterCardsMap);
                                break;
                            }
                        }

                        if (hasUsedCharacterCard || !currPlayer.equals(nickname)) {
                            greyNode(card);
                        }
                        break;
                    }
                }
            }
            cardPosition++;
        }
    }


    private void displayCostsAndInfos(int i, int cardPosition, Map<Integer, CharacterCardData> characterCardsMap ) {
        String cost = "cost" + cardPosition;
        String info = "info" + cardPosition;
        if (cost.equals("cost0")) {
            cost0.setText(String.valueOf(characterCardsMap.get(i).getPrice()));
        }
        if (cost.equals("cost1")) {
            cost1.setText(String.valueOf(characterCardsMap.get(i).getPrice()));
        }
        if (cost.equals("cost2")) {
            cost2.setText(String.valueOf(characterCardsMap.get(i).getPrice()));
        }
        if (info.equals("info0")) {
            info0.setText(characterCardsMap.get(i).getDescription());
        }
        if (info.equals("info1")) {
            info1.setText(characterCardsMap.get(i).getDescription());
        }
        if (info.equals("info2")) {
            info2.setText(characterCardsMap.get(i).getDescription());
        }
    }

    private void displayStudentsOnCards(int i, int cardPosition, Map<Integer, CharacterCardData> characterCardsMap) {
        int numStudent = 0;

        for(Node student : students.getChildren()) {
            if(student instanceof AnchorPane) {
                String studentPane = student.getId();
                if (studentPane.equals("students" + cardPosition)) {
                    System.out.println(studentPane);
                    ObservableList studentsChildren = ((AnchorPane) student).getChildren(); //the images where to put students
                    for (Map.Entry<PawnColour, Integer> cardEntry : characterCardsMap.get(i).getStudents().entrySet()) {
                        for (int j = 0; j < cardEntry.getValue(); j++) {
                            ImageView entranceSpot = (ImageView) studentsChildren.get(numStudent);
                            Image image = new Image("/gui/img/board/" + cardEntry.getKey().toString().toLowerCase() + "Student.png");
                            (entranceSpot).setImage(image);

                            if(hasUsedCharacterCard) {
                                if (chosenCard == 0 || chosenCard == 6 || chosenCard == 10) {
                                    entranceSpot.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                                        glowNode(entranceSpot, Color.DARKBLUE);
                                        if (entranceSpot.getImage().getUrl().contains("/gui/img/board/redStudent.png")) {
                                            chosenStudentColour = 0;
                                        }
                                        if (entranceSpot.getImage().getUrl().contains("/gui/img/board/yellowStudent.png")) {
                                            chosenStudentColour = 1;
                                        }
                                        if (entranceSpot.getImage().getUrl().contains("/gui/img/board/greenStudent.png")) {
                                            chosenStudentColour = 2;
                                        }
                                        if (entranceSpot.getImage().getUrl().contains("/gui/img/board/blueStudent.png")) {
                                            chosenStudentColour = 3;
                                        }
                                        if (entranceSpot.getImage().getUrl().contains("/gui/img/board/pinkStudent.png")) {
                                            chosenStudentColour = 4;
                                        }
                                        e.consume();
                                    });
                                }
                            }
                            numStudent++;
                        }
                    }
                }
            }
        }
        System.out.println(chosenStudentColour);
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



