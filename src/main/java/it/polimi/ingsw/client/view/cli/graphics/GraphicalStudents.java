package it.polimi.ingsw.client.view.cli.graphics;


import it.polimi.ingsw.client.view.Constants;
import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

public class GraphicalStudents {

    /**
     * Draws the Students depending on their colour
     * @param studentMap map with students colour and amount
     */
    public void drawStudents(Map<PawnColour,Integer> studentMap) {
        //green students
        int greenStudents = studentMap.get(PawnColour.GREEN);
        for (int i = 0; i<greenStudents; i++) {
            System.out.print(Colour.ANSI_GREEN.getCode() + Constants.STUDENT);
        }
        if(greenStudents>0)
            System.out.print("\n");
        //red students
        int redStudents = studentMap.get(PawnColour.RED);
        for (int i = 0; i<redStudents; i++) {
            System.out.print(Colour.ANSI_RED.getCode() + Constants.STUDENT);
        }
        if(redStudents>0)
            System.out.print("\n");
        //yellow students
        int yellowStudents = studentMap.get(PawnColour.YELLOW);
        for (int i = 0; i<yellowStudents; i++) {
            System.out.print(Colour.ANSI_YELLOW.getCode() + Constants.STUDENT);
        }
        if(yellowStudents>0)
            System.out.print("\n");
        //pink students
        int pinkStudents = studentMap.get(PawnColour.PINK);
        for (int i = 0; i<pinkStudents; i++) {
            System.out.print(Colour.ANSI_PURPLE.getCode() + Constants.STUDENT);
        }
        if(pinkStudents>0)
            System.out.print("\n");
        //blue students
        int blueStudents = studentMap.get(PawnColour.BLUE);
        for (int i = 0; i<blueStudents; i++) {
            System.out.print(Colour.ANSI_BLUE.getCode() + Constants.STUDENT);
        }
        System.out.print("\n");
    }

}
