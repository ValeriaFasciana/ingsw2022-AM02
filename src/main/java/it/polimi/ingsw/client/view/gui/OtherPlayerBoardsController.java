package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.PlayerBoardData;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OtherPlayerBoardsController {

    public AnchorPane otherPlayerboardsPane;

    public AnchorPane playerBoard2;
    public AnchorPane playerBoard3;

    public Button returnToOwnPlayerBoard;

    public AnchorPane professors;
    public AnchorPane professors3;
    public AnchorPane professors2;


    private AnchorPane entrance;
    public AnchorPane entrance2;
    public AnchorPane entrance3;
    private AnchorPane hall;
    public AnchorPane hall2;
    public AnchorPane hall3;

    AnchorPane towers;
    public AnchorPane towers2;
    public AnchorPane towers3;
    private GUIApp gui;
    private Object lock;
    BoardData boardData;
    boolean expertMode;
    String callerNickname; //nickname of the player that called the otherplayerboard scene
    int playerBoardNumber= 2;



    public void setGUI(GUIApp gui) {
        this.gui = gui;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    /**
     * Method to handle the closing of the stage
     * @param event
     */
    @FXML
    public void handleReturnToOwnPlayerboardButton(ActionEvent event) {
        gui.handleReturnButtonOtherBoards();
    }

    /**
     * Method to initialize and display the other player board stage
     * @param boardData the board data
     * @param expertMode the game mode
     * @param callerNickname the nickname of the player that wants to view the other playerboard scene
     */
    public void displayOtherPlayerBoards(BoardData boardData, boolean expertMode, String callerNickname) {
        this.boardData = boardData;
        this.expertMode = expertMode;
        this.callerNickname = callerNickname;
        int numPlayers = boardData.getPlayerBoards().size();

        if(numPlayers == 2) {
            playerBoard3.setVisible(false);
        }

        List<String> nicknames = new ArrayList<>(boardData.getPlayerBoards().keySet());
        for(int i = 0; i < numPlayers; i++) {
            if(!(nicknames.get(i).equals(callerNickname))) {
                updateBoard(nicknames.get(i));
                playerBoardNumber++;
            }
        }

    }

    /**
     * it updates the other playerboard on the stage
     * @param currNickname
     */
    @FXML
    public void updateBoard(String currNickname) {
        displayEntrance(currNickname);
        displayHall(currNickname);
        displayTowersOnPlayerBoard(currNickname);
    }

    /**
     * Method to display the tower on the other playerboard
     * @param currNickname the nickname of the player that wants to view the other playerboard scene
     */
    private void displayTowersOnPlayerBoard(String currNickname) {

        TowerColour colour = boardData.getPlayerBoards().get(currNickname).getTowerColour();
        int towerCounter = boardData.getPlayerBoards().get(currNickname).getTowerCounter();
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

        if(playerBoardNumber == 2) {
            towers = towers2;
        }
        if(playerBoardNumber == 3) {
            towers = towers3;
        }

        for(Node gridpane : towers.getChildren()) {
            if (gridpane instanceof GridPane) {
                int i = 0;
                for(Node imageNode : ((GridPane) gridpane).getChildren()) {
                    if (imageNode instanceof ImageView) {
                        ((ImageView)imageNode).setImage(image);

                    }

                    i++; //prints the right quantity of towers
                    if (i == towerCounter) {
                        return; //when it gets to 6, id doesn't print any more towers
                    }
                }
            }
        }
    }

    /**
     * Method to display the hall on the other playerboard
     * @param currNickname the nickname of the player that wants to view the other playerboard scene
     */
    private void displayHall(String currNickname) {
        Map<PawnColour, Integer> hallMap = boardData.getPlayerBoards().get(currNickname).getHall();
        Set<PawnColour> professorsSet = boardData.getPlayerBoards().get(currNickname).getProfessors();
        Image image = null;
        if(playerBoardNumber == 2) {
            hall = hall2;
        }
        if(playerBoardNumber == 3) {
            hall = hall3;
        }
        for(int colour = 0; colour < PawnColour.values().length; colour++) { //cicla sui colori
            for (Node hallTables : hall.getChildren()) { //cicla sui tavoli dei posti in entrance
                if (hallTables instanceof AnchorPane) {
                    String hallTablesId = hallTables.getId();

                    if (hallTablesId != null && hallTablesId.contains(PawnColour.valueOf(colour).toString().toLowerCase())) {
                        for(int i = 0; i < hallMap.get(PawnColour.valueOf(colour)); i++) {
                            for (Node hallSpot : ((AnchorPane) hallTables).getChildren()) {
                                if (hallSpot instanceof ImageView) {
                                    if (((ImageView) hallSpot).getImage() == null) {
                                        image = new Image("gui/img/board/" + PawnColour.valueOf(colour).toString().toLowerCase()+"Student.png");
                                        ((ImageView) hallSpot).setImage(image);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int i = 0; i < professorsSet.size(); i++) {
            if(playerBoardNumber == 2) {
                professors = professors2;

            }
            if(playerBoardNumber == 3) {
                professors = professors3;
            }
            for(PawnColour colour : professorsSet) {
                Image profImage = new Image("/gui/img/board/"+colour.toString().toLowerCase()+"Professor.png");
                if(playerBoardNumber == 2) {
                    professors.getChildren().stream().filter(child -> (child.getId().equals((colour.toString().toLowerCase() + "Prof2")))).forEach(prof -> ((ImageView) prof).setImage(profImage));
                }
                if(playerBoardNumber == 3) {
                    professors.getChildren().stream().filter(child -> (child.getId().equals((colour.toString().toLowerCase() + "Prof3")))).forEach(prof -> ((ImageView) prof).setImage(profImage));
                }
            }

        }
    }

    /**
     * Method to display the entrance on the other playerboard
     * @param currNickname the nickname of the player that wants to view the other playerboard scene
     */
    private void displayEntrance(String currNickname) {
        Map<PawnColour, Integer> entranceMap = boardData.getPlayerBoards().get(currNickname).getEntrance();
        Image image = null;
        int numEntrance = 0;
        if(playerBoardNumber == 2) {
            entrance = entrance2;
        }
        if(playerBoardNumber == 3) {
            entrance = entrance3;
        }
        for(int colour = 0; colour < PawnColour.values().length; colour++) { //cicla sui colori

            for (int i = 0; i < entranceMap.get(PawnColour.valueOf(colour)); i++) { //cicla su numero di studenti per colore
                for (Node entranceSpot : entrance.getChildren()) { //cicla sulle immagini dei posti in entrance
                    if (entranceSpot instanceof ImageView) {
                        String entranceSpotId = entranceSpot.getId();

                        if (entranceSpotId != null && entranceSpotId.equals("entranceStudent"+numEntrance+playerBoardNumber)) {
                            image = new Image("/gui/img/board/"+PawnColour.valueOf(colour).toString().toLowerCase()+"Student.png");
                            ((ImageView)entranceSpot).setImage(image);
                            numEntrance++;
                            break;
                        }
                    }
                }
            }
        }
    }

}
