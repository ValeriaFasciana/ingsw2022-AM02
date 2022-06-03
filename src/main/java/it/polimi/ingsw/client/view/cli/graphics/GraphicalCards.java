package it.polimi.ingsw.client.view.cli.graphics;


import it.polimi.ingsw.server.model.cards.AssistantCard;

import java.util.HashMap;
import java.util.Map;

public class GraphicalCards{
    public void printCardVertical(int id,int value, int movement) {
        if(value<10) {
            System.out.print("Card " + id + ":\n" +
                    "█████████ \n" +
                    "█ "+value+"   "+movement+" █ \n"+
                    "█       █ \n" +
                    "█       █ \n" +
                    "█████████ \n");
    }
        else {
            System.out.print("Card " + id + ":\n" +
                    "█████████ \n" +
                    "█ "+value+"  "+movement+" █ \n"+
                    "█       █ \n" +
                    "█       █ \n" +
                    "█████████ \n");
        }
    }
    public void printCardHorizontal(HashMap<Integer, AssistantCard> deck) {
        String primariga = "";
        String secondariga= "";
        String terzariga = "";
        String quartariga= "";
        String quintariga= "";
        String sestariga= "";
        for(Map.Entry<Integer, AssistantCard> card : deck.entrySet()){
            primariga = primariga + "Card: "+card.getKey()+"   ";
            secondariga = secondariga + "█████████ ";
            if(card.getValue().getValue()>=10) {
                terzariga = terzariga +"█ "+card.getValue().getValue()+"  "+card.getValue().getMovement()+" █ ";
            }
            else{
                terzariga = terzariga +"█ "+card.getValue().getValue()+"   "+card.getValue().getMovement()+" █ ";
            }
            quartariga = quartariga +"█       █ ";
            quintariga = quintariga +"█       █ ";
            sestariga = sestariga + "█████████ ";

        }
        System.out.print(primariga + "\n"+secondariga + "\n"+terzariga + "\n"+quartariga + "\n"+quintariga + "\n"+sestariga + "\n");

    }




}
