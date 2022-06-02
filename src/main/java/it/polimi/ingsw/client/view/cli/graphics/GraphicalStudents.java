package it.polimi.ingsw.client.view.cli.graphics;

//class that shows the students a player has

import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

public class GraphicalStudents {
    public void drawStudents(Map<PawnColour,Integer> studentMap) {
        System.out.println("These are the students available:");
        //green students
        int greenStudents = studentMap.get(PawnColour.GREEN);
        for (int i = 0; i<greenStudents; i++) {
            System.out.print(Colour.ANSI_GREEN.getCode() + "♟ ");
        }
        System.out.println("");
        //red students
        int redStudents = studentMap.get(PawnColour.RED);
        for (int i = 0; i<redStudents; i++) {
            System.out.print(Colour.ANSI_RED.getCode() + "♟ ");
        }
        System.out.println("");
        //yellow students
        int yellowStudents = studentMap.get(PawnColour.YELLOW);
        for (int i = 0; i<yellowStudents; i++) {
            System.out.print(Colour.ANSI_YELLOW.getCode() + "♟ ");
        }
        System.out.println("");
        //pink students
        int pinkStudents = studentMap.get(PawnColour.PINK);
        for (int i = 0; i<pinkStudents; i++) {
            System.out.print(Colour.ANSI_PURPLE.getCode() + "♟ ");
        }
        System.out.println("");
        //blue students
        int blueStudents = studentMap.get(PawnColour.BLUE);
        for (int i = 0; i<blueStudents; i++) {
            System.out.print(Colour.ANSI_BLUE.getCode() + "♟ ");
        }
        System.out.println("");
    }
}
