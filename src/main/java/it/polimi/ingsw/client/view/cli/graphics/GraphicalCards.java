package it.polimi.ingsw.client.view.cli.graphics;

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
            symbols[1][width - 4] = String.valueOf(ID/10).charAt(0);
            colours[1][width - 4] = Colour.ANSI_BRIGHT_WHITE;
        }
        symbols[1][width - 3] = String.valueOf(ID%10).charAt(0);
        colours[1][width - 3] = Colour.ANSI_BRIGHT_WHITE;
    }
    protected void drawMovement() {
        int movement = lightCard.getMovement();
        int h_center = width /2;
        symbols[3][h_center-2] = 'V';
        colours[3][h_center-2] = Colour.ANSI_BRIGHT_YELLOW;
        symbols[3][h_center-1] = 'P';
        colours[3][h_center-1] = Colour.ANSI_BRIGHT_YELLOW;
        symbols[3][h_center] = ':';
        colours[3][h_center] = Colour.ANSI_BRIGHT_YELLOW;
        if(movement > 9){
            symbols[3][h_center+1] = String.valueOf(movement/10).charAt(0);
            colours[3][h_center+1] = Colour.ANSI_BRIGHT_YELLOW;
        }
        symbols[3][h_center+2] = String.valueOf(movement%10).charAt(0);
        colours[3][h_center+2] = Colour.ANSI_BRIGHT_YELLOW;
    }
    /*
    protected void drawValue() {
        int center = width /2;
        int begin = center - cost.size()/2;
        for (int j = 0; j < cost.size(); j++){
            if(j%2==0){
                symbols[posix][begin+j] = cost.get(j).charAt(0);
                colours[posix][begin+j] = Colour.ANSI_BRIGHT_WHITE;
            }
            else {
                try {
                    Resource r = Resource.valueOf(cost.get(j));
                    Colour c = getResourceColor(r);

                    symbols[posix][begin+j] = r.symbol.charAt(0);
                    colours[posix][begin+j] = c;
                }catch(IllegalArgumentException e){
                    symbols[posix][begin+j] = '†';
                    colours[posix][begin+j] = Colour.ANSI_RED;
                }
            }
        }
     */
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

