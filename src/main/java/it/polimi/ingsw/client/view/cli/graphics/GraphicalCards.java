package it.polimi.ingsw.client.view.cli.graphics;


import it.polimi.ingsw.server.model.cards.AssistantCard;

import java.util.Map;
import java.util.Set;

public class GraphicalCards{
    public void printCard(int id,int value, int movement) {
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
    /*public void printCard2(Set<Map.Entry<Integer, AssistantCard>> player) {
        int i = 0;

        for (i<player.) {
        }
    }
    */



}
