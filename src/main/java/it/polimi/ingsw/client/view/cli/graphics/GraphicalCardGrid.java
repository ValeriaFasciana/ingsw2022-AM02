package it.polimi.ingsw.client.view.cli.graphics;

import it.polimi.ingsw.shared.LightAssistantCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphicalCardGrid extends GraphicalElement {

    //cancella, solo per prova
    LightAssistantCard card1 = new LightAssistantCard(1,1,1,false);
    LightAssistantCard card2 = new LightAssistantCard(2,2,2,false);


    private static final int h_space = 1; //horizontal_space between cards
    private static final int v_space = 0; //vertical_space between cards

    public static final int cardWidth = 14;
    public static final int cardHeight = 8;

    List<LightAssistantCard> cardsToDisplay;

    public GraphicalCardGrid() {
        super(cardWidth*4 + h_space*3, cardHeight*3 + v_space*3);
    }

    //Sets the card IDs that are going to be displayed
    private void setCardsToDisplay(List<Integer> cardsToDisplay) {
        this.cardsToDisplay = new ArrayList<>();
        for(int i = 0; i<2; i++) {
            //this.cardsToDisplay.add(MatchData.getInstance().getDevelopmentCardByID(ID));
            this.cardsToDisplay.add(card1);
            this.cardsToDisplay.add(card2);
        }
    }

        //Fill the symbols, colour and background colours matrices
    public void drawAssistantCardGrid(List<Integer> cardsToDisplay){
        setCardsToDisplay(cardsToDisplay);
        reset();
        int x_coord = 0;
        int y_coord = 0;
        for(LightAssistantCard ldc : this.cardsToDisplay){
            List<Integer> coordinates = retrieveCoordinates(ldc);
            x_coord = coordinates.get(0);
            y_coord = coordinates.get(1);
            GraphicalAssistantCards gac = new GraphicalAssistantCards(ldc, null);
            gac.drawCard();
            drawAssistantCard(gac, x_coord, y_coord);
        }
    }


    //Draw a single card in a specific slot of the grid
    private void drawAssistantCard(GraphicalAssistantCards gac, int x_coord, int y_coord) {

        char[][] cardSymbols = gac.getSymbols();
        Colour[][] cardColours = gac.getColours();
        BackGroundColour[][] cardBackColours = gac.getBackGroundColours();

        for(int i = 0; i < cardHeight; i++){
            for(int j = 0; j < cardWidth; j++){
                symbols[x_coord+i][y_coord+j] = cardSymbols[i][j];
                colours[x_coord+i][y_coord+j] = cardColours[i][j];
                backGroundColours[x_coord+i][y_coord+j] = cardBackColours[i][j];
            }
        }
    }

    //Returns the coordinates of a card
    private List<Integer> retrieveCoordinates(LightAssistantCard ldc) {
        int x = 0;
        int y = 0;
        x =(cardHeight + v_space);
        y =(cardWidth + h_space);
        return new ArrayList<>(Arrays.asList(x, y));
    }

        public static void main(String[] args) {
        List<Integer> cardsToDisplay = new ArrayList<>();

        GraphicalCardGrid grid = new GraphicalCardGrid();
        grid.drawAssistantCardGrid(cardsToDisplay);
    }

}


