package it.polimi.ingsw.client.view.cli.graphics;

import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

public abstract class GraphicalElement {
    int width;
    int height;

    final char[][] symbols;
    final Colour[][] colours;
    final BackGroundColour[][] backGroundColours;

    public GraphicalElement(int width, int height) {
        this.width = width;
        this.height = height;
        this.symbols = new char[height][width];
        this.colours = new Colour[height][width];
        this.backGroundColours = new BackGroundColour[height][width];
    }

    /**
     * Display on the terminal the element
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

    public void drawID(int x, int y, int index) {
        if(index <10) {
            symbols[x][y + 7] = String.valueOf(index).charAt(0);
        }
        else {
            symbols[x][y + 7] = String.valueOf(index).charAt(0);
            symbols[x][y + 8] = String.valueOf(index).charAt(1);
        }
    }

    public void drawStudent(int x, int y, Map<PawnColour,Integer> students) {
        int j = 1;
        for (PawnColour colour : PawnColour.values()) {
            int stud = students.get(colour);
            symbols[x +1+j][y + 1] = '◆';

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
    /*
     * Given a {@link Marble} return the corresponding color code
     * @param marble the {@link Marble} to convert to a color
     * @return a String representing the color code

    public static String getMarbleColour(Marble marble){
        if (marble == Marble.YELLOW)
            return ANSI_BRIGHT_YELLOW.code;
        if (marble == Marble.GREY)
            return ANSI_WHITE.code;
        if (marble == Marble.PURPLE)
            return ANSI_BRIGHT_PURPLE.code;
        if (marble == Marble.BLUE)
            return ANSI_BRIGHT_BLUE.code;
        if (marble == Marble.RED)
            return ANSI_BRIGHT_RED.code;
        else
            return ANSI_BRIGHT_WHITE.code;
    }

     * Given a {@link Marble} return the corresponding color
     * @param marble the {@link Marble} to convert to a color
     * @return a {@link Colour}

    public static Colour getColourByMarble(Marble marble){
        if (marble == Marble.YELLOW)
            return ANSI_BRIGHT_YELLOW;
        if (marble == Marble.GREY)
            return ANSI_BRIGHT_BLACK;
        if (marble == Marble.PURPLE)
            return ANSI_BRIGHT_PURPLE;
        if (marble == Marble.BLUE)
            return ANSI_BRIGHT_BLUE;
        if (marble == Marble.RED)
            return ANSI_BRIGHT_RED;
        else
            return ANSI_BRIGHT_WHITE;
    }

    /**
     * Given a {@link Resource} return the corresponding color
     * @param resource the {@link Resource} to convert to a color
     * @return a {@link Colour}
    public static Colour getColourByResource(Resource resource){
        if (resource == Resource.COIN)
            return ANSI_BRIGHT_YELLOW;F
        if (resource == Resource.STONE)
            return ANSI_BRIGHT_BLACK;
        if (resource == Resource.SHIELD)
            return ANSI_BRIGHT_BLUE;
        else
            return ANSI_BRIGHT_PURPLE;
    }

    /**
     * Given a {@link Resource} return the corresponding color code
     * @param resource the {@link Resource} to convert to a color
     * @return a String representing the color code

    public static String getResourceColour(Resource resource){
        if (resource == Resource.COIN)
            return ANSI_BRIGHT_YELLOW.code;
        if (resource == Resource.STONE)
            return ANSI_WHITE.code;
        if (resource == Resource.SHIELD)
            return ANSI_BRIGHT_BLUE.code;
        else
            return ANSI_BRIGHT_PURPLE.code;
    }
    */
}
