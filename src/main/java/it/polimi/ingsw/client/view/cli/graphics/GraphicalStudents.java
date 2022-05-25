package it.polimi.ingsw.client.view.cli.graphics;

//class that shows the students a player has

import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.StudentContainer;

public class GraphicalStudents {
    public void drawStudents(StudentContainer students) {
        System.out.println("These are the students available:");
        //green students
        int greenStudents = students.getStudentsByColour(PawnColour.GREEN);
        for (int i = 0; i<greenStudents; i++) {
            System.out.print(Colour.ANSI_GREEN.getCode() + "♟ ");
        }
        System.out.println("");
        //red students
        int redStudents = students.getStudentsByColour(PawnColour.RED);
        for (int i = 0; i<redStudents; i++) {
            System.out.print(Colour.ANSI_RED.getCode() + "♟ ");
        }
        System.out.println("");
        //yellow students
        int yellowStudents = students.getStudentsByColour(PawnColour.YELLOW);
        for (int i = 0; i<yellowStudents; i++) {
            System.out.print(Colour.ANSI_YELLOW.getCode() + "♟ ");
        }
        System.out.println("");
        //pink students
        int pinkStudents = students.getStudentsByColour(PawnColour.PINK);
        for (int i = 0; i<pinkStudents; i++) {
            System.out.print(Colour.ANSI_PURPLE.getCode() + "♟ ");
        }
        System.out.println("");
        //blue students
        int blueStudents = students.getStudentsByColour(PawnColour.BLUE);
        for (int i = 0; i<blueStudents; i++) {
            System.out.print(Colour.ANSI_BLUE.getCode() + "♟ ");
        }
        System.out.println("");
    }
}
