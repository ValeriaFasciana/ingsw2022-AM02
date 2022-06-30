package it.polimi.ingsw.client.view.cli.graphics;


import it.polimi.ingsw.client.view.Constants;
import it.polimi.ingsw.network.data.CharacterCardData;
import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.server.model.cards.AssistantCard;
import it.polimi.ingsw.server.model.cards.characters.CharacterCard;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the common features of any card
 */
public class GraphicalCard extends GraphicalElement{


    private final int squareHeight = 8;
    private final int squareWidth = 14;

    public GraphicalCard() {
        super(200, 9);
    }

    /**
     * Draws all the Character cards for the game
     * @param characters map of the chosen character cards
     */
    public void drawCards(Map<Integer, CharacterCardData> characters){
        int x=0;
        int y=0;
       reset();
        for(Map.Entry<Integer, CharacterCardData> cardEntry : characters.entrySet()) {

            Map<PawnColour,Integer> studentMap = cardEntry.getValue().getStudents();
            String description = cardEntry.getValue().getDescription();
            int price = cardEntry.getValue().getPrice();
            //drawCard(x,y, cardEntry.getKey(),studentMap);
            y+=16; //offset per stampare isole separate
        }
        display();
   }

   /**
    * Draws all the elements of a Character card
    * @param id card's id
    * @param price card's price
    * @param students map of the students on the card
    * @param x height coordinate
    * @param y width coordinate
    */
   public void drawCard(int x,int y, int id, Map<PawnColour,Integer> students, int price){
        reset();
       for (int i = 0; i < squareHeight; i++) {
           for (int j = 0; j < squareWidth; j++) {
               if (!(i > 0 && j > 0) || i == squareHeight - 1 || j == squareWidth - 1) {
                   symbols[i + x +1][j + y] = Constants.ISLE_BORDER;
                   colours[i + x +1][j + y] = Colour.ANSI_YELLOW;
               }
           }
       }
    if(!students.isEmpty()) {
        drawStudent(x, y, students);
    }
    drawID(x,y,id);
    drawPrice(x,y,price);
       display();
//
//       if(isMotherNature) {
//           drawMotherNature(x,y);
//       }
//       if(tColour != null) {
//           drawTower(x,y,tColour);
//       }


   }

    /**
     * Draws the price of the card on the bottom of a character card
     * @param x height coordinate
     * @param y width coordinate
     * @param price card's price
     */
    private void drawPrice(int x, int y, int price) {
        symbols[x + 7][y + 1] = 'P';
        symbols[x + 7][y + 2] = 'R';
        symbols[x + 7][y + 3] = 'I';
        symbols[x + 7][y + 4] = 'C';
        symbols[x + 7][y + 5] = 'E';
        symbols[x + 7][y + 5] = 'E';
        symbols[x + 7][y + 6] = ':';

        symbols[x + 7][y + 7] = String.valueOf(price).charAt(0);

    }

    /**
     * Draws all the element of an Assistant Card
     * @param id card's id
     * @param value card's value
     * @param movement
     */
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
