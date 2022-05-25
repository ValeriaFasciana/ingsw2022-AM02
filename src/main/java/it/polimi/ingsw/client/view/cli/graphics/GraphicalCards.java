package it.polimi.ingsw.client.view.cli.graphics;


public class GraphicalCards{
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
    }



}
