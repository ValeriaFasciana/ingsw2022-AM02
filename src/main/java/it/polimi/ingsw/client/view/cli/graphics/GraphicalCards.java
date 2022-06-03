package it.polimi.ingsw.client.view.cli.graphics;


import it.polimi.ingsw.server.model.cards.AssistantCard;

import java.util.HashMap;

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
    public void printCardHorizontal(HashMap<Integer, AssistantCard> player) {
        String primariga = "";
        String secondariga= "";
        String terzariga = "";
        String quartariga= "";
        String quintariga= "";
        String sestariga= "";
        for (int i = 0; i < player.size(); i++) {
            primariga = primariga + "Card: "+player.get(i).getId()+"   ";
            secondariga = secondariga + "█████████ ";
            if(player.get(i).getValue()>=10) {
                terzariga = terzariga +"█ "+player.get(i).getValue()+"  "+player.get(i).getMovement()+" █ ";
            }
            else{
                terzariga = terzariga +"█ "+player.get(i).getValue()+"   "+player.get(i).getMovement()+" █ ";
            }
            quartariga = quartariga +"█       █ ";
            quintariga = quintariga +"█       █ ";
            sestariga = sestariga + "█████████ ";

        }
        System.out.print(primariga + "\n"+secondariga + "\n"+terzariga + "\n"+quartariga + "\n"+quintariga + "\n"+sestariga + "\n");

    }




}
