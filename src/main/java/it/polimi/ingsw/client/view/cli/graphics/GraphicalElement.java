package it.polimi.ingsw.client.view.cli.graphics;

import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

/**
 * This class represents the basic information that each element of the {@link it.polimi.ingsw.client.view.cli.CLI} has
 */
public abstract class GraphicalElement {
    int width;
    int height;

    final char[][] symbols;
    final Colour[][] colours;
    final BackGroundColour[][] backGroundColours;

    /**
     * Constructor of the {@link it.polimi.ingsw.client.view.cli.graphics.GraphicalElement}
     * @param width element's width
     * @param height element's height
     */
    public GraphicalElement(int width, int height) {
        this.width = width;
        this.height = height;
        this.symbols = new char[height][width];
        this.colours = new Colour[height][width];
        this.backGroundColours = new BackGroundColour[height][width];
    }

    /**
     * Display the element on the terminal
     */
    void display(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                System.out.print(backGroundColours[i][j].getCode() + colours[i][j].getCode() + symbols[i][j]);
            }
            System.out.print("\n");
        }
    }

    /**
     * Clear all the symbols, colours and background colours of the graphical element
     */
    protected void reset(){
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                symbols[i][j] = ' ';
                colours[i][j] = Colour.ANSI_DEFAULT;
                backGroundColours[i][j] = BackGroundColour.ANSI_DEFAULT;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getSymbols() {
        return symbols;
    }

    public Colour[][] getColours() {
        return colours;
    }

    public BackGroundColour[][] getBackGroundColours() {
        return backGroundColours;
    }

    /**
     * Draws the ID of the element
     * @param x heigth of the ID
     * @param y width of the ID
     * @param index index of the element to draw
     */
    public void drawID(int x, int y, int index) {
        if(index <10) {
            symbols[x][y + 7] = String.valueOf(index).charAt(0);
        }
        else {
            symbols[x][y + 7] = String.valueOf(index).charAt(0);
            symbols[x][y + 8] = String.valueOf(index).charAt(1);
        }
    }

    /**
     * Draws the element Student
     * @param x starting height to draw the students
     * @param y starting width to draw the students
     * @param students map with the students info on colours and amount
     */
    public void drawStudent(int x, int y, Map<PawnColour,Integer> students) {
        int j = 1;
        for (PawnColour colour : PawnColour.values()) {
            int stud = students.get(colour);
            symbols[x +1+j][y + 1] = '\u25C6';

            if(stud<10){
                symbols[x +1+j][y+3] = String.valueOf(stud).charAt(0);
            }
            if(stud >=10 && stud <100) {
                symbols[x +1+j][y+3] = String.valueOf(stud).charAt(0);
                symbols[x +1+j][y+4] = String.valueOf(stud).charAt(1);
            }
            if(stud >=100) {
                symbols[x +1+j][y+3] = String.valueOf(stud).charAt(0);
                symbols[x +1+j][y+4] = String.valueOf(stud).charAt(1);
                symbols[x +1+j][y+5] = String.valueOf(stud).charAt(2);
            }
            if (colour.equals(PawnColour.RED)) {
                colours[x +1+j][y + 1] = Colour.ANSI_BRIGHT_RED;
            }
            if (colour.equals(PawnColour.GREEN)) {
                colours[x +1+j][y + 1] = Colour.ANSI_BRIGHT_GREEN;
            }
            if (colour.equals(PawnColour.YELLOW)) {
                colours[x +1+j][y + 1] = Colour.ANSI_BRIGHT_YELLOW;
            }
            if (colour.equals(PawnColour.BLUE)) {
                colours[x +1+j][y + 1] = Colour.ANSI_BRIGHT_BLUE;
            }
            if (colour.equals(PawnColour.PINK)) {
                colours[x +1+j][y + 1] = Colour.ANSI_BRIGHT_PURPLE;
            }
            j++;
        }
    }
}
