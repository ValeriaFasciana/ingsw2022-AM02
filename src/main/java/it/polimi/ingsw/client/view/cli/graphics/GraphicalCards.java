package it.polimi.ingsw.client.view.cli.graphics;

import it.polimi.ingsw.shared.LightAssistantCard;
import it.polimi.ingsw.shared.LightCards;

public abstract class GraphicalCards extends GraphicalElement{

    String nickname;
    protected LightCards lightCard;
    public GraphicalCards(LightCards ldc, String nickname) {
        super(14, 8);
        reset();
        this.lightCard = ldc;
        this.nickname = nickname;
    }

     //Method to draw all the elements of a card
    abstract void drawCard();

    //Draws the ID of the card in the right-upper corner
    protected void drawID() {
        int ID = lightCard.getId();
        if(ID>9){
            symbols[1][3] = String.valueOf(ID/10).charAt(0);
            colours[1][3] = Colour.ANSI_BRIGHT_WHITE;
        }
        symbols[1][4] = String.valueOf(ID%10).charAt(0);
        colours[1][4] = Colour.ANSI_BRIGHT_WHITE;
    }
    protected void drawMovement() {
        int movement = lightCard.getMovement();
        if(movement >9){
            symbols[1][width - 4] = String.valueOf(movement/10).charAt(0);
            colours[1][width - 4] = Colour.ANSI_BRIGHT_WHITE;
        }
        symbols[1][width - 3] = String.valueOf(movement%10).charAt(0);
        colours[1][width - 3] = Colour.ANSI_BRIGHT_WHITE;
    }

    public static void main(String[] args) {
        LightAssistantCard lac = new LightAssistantCard(1, 2, 10, false);

        GraphicalAssistantCards card = new GraphicalAssistantCards(lac, "valeria");
        card.drawCard();
    }
}








    /*
    public void printCard(int value, int movement) {
        if(value<10) {
            System.out.printf("This is the card you have chosen:\n" + Colour.ANSI_BLUE.getCode() +
            "█████████ \n" +
            "█ %d   %d █ \n" +
            "█       █ \n" +
            "█       █ \n" +
            "█████████ \n" + Colour.ANSI_RESET, value, movement);
    }
        else {
            System.out.printf("This is the card you have chosen:\n" + Colour.ANSI_BLUE.getCode() +
                    "█████████ \n" +
                    "█ %d  %d █ \n" +
                    "█       █ \n" +
                    "█       █ \n" +
                    "█████████ \n" + Colour.ANSI_RESET, value, movement);
        }
        */

