package it.polimi.ingsw.client.view.cli.graphics;


import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphicalCard extends GraphicalElement{


    private final int squareHeight = 8;
    private final int squareWidth = 14;

    public GraphicalCard() {
        super(200, 9);
    }

    public void drawCards(Map<Integer, CharacterCardData> characters){
        int x=0;
        int y=0;
       reset();
//        for(Map.Entry<Integer, CharacterCardData> cardEntry : characters.entrySet()) {
//
//            Map<PawnColour,Integer> studentMap = cardEntry.getValue().getStudents();
//            String description = cardEntry.getValue().getDescription();
//            drawIsland(x,y, studentMap,);
//            y+=16; //offset per stampare isole separate
//        }
//        display();
   }


   public void drawCard(){

   }

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
