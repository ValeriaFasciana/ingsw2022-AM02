package it.polimi.ingsw.client.view.cli.graphics;

public class GraphicalIsland extends GraphicalElement{

    private final int squareHeight = 4;
    private final int squareWidth = 10;

    public GraphicalIsland() {
        super(200, 5);
    }

    //è da creare metodo per disegnare le isole a seconda se sono ancora indipendenti o unite
    /**
     * Draw a single square of island
     * @param x the x coord for height
     * @param y the y coord for width
     */

    public void drawIslandInfo(int x, int y) {
        char i = '2';
        symbols[x + 2][y + 1] = 'S';
        symbols[x + 2][y + 2] = 'I';
        symbols[x + 2][y + 3] = 'Z';
        symbols[x + 2][y + 4] = 'E';
        symbols[x + 2][y + 5] = ':';
        symbols[x + 2][y + 6] = i;

    }

    public void drawStudent(int x, int y) {

    }
    public void drawTower(int x, int y) {
        symbols[x + 1][y + squareWidth -3] = 'T';
        colours[x + 1][y + squareWidth -3] = Colour.ANSI_BRIGHT_BLACK;
        backGroundColours[x + 1][y + squareWidth -2] = BackGroundColour.ANSI_DEFAULT;

    }
    public void drawMotherNature(int x, int y){
        symbols[x+1][y +2] = 'M';
        colours[x+1][y +2] = Colour.ANSI_BRIGHT_RED;
    }

    public void drawIsland(int x, int y) {

        for (int i = 0; i < squareHeight; i++) {
            for (int j = 0; j < squareWidth; j++) {
                if (i == 0 && j == 0){
                    symbols[i + x][j + y] = '▋';
                    colours[i + x][j + y] = Colour.ANSI_BRIGHT_GREEN;}
                else if (i == 0 && j == squareWidth - 1){
                    symbols[i + x][j + y] = '▋';
                    colours[i + x][j + y] = Colour.ANSI_BRIGHT_GREEN;}
                else if (i == squareHeight - 1 && j == 0){
                    symbols[i + x][j + y] = '▋';
                    colours[i + x][j + y] = Colour.ANSI_BRIGHT_GREEN;}
                else if (i == squareHeight - 1 && j == squareWidth - 1){
                    symbols[i + x][j + y] = '▋';
                    colours[i + x][j + y] = Colour.ANSI_BRIGHT_GREEN;}
                else if (i == 0 || i == squareHeight - 1) {
                    symbols[i + x][j + y] = '▋';
                colours[i + x][j + y] = Colour.ANSI_BRIGHT_GREEN;}
                else if (j == 0 || j == squareWidth - 1){
                    symbols[i + x][j + y] = '▋';
                    colours[i + x][j + y] = Colour.ANSI_BRIGHT_GREEN;}
            }
        }
    }

    public static void main(String[] args) {
        GraphicalIsland island = new GraphicalIsland();
        island.reset();
        int x = 0;
        int y = 0;
        for(int i = 0; i < 12; i++) {
            island.drawIsland(x,y);
            island.drawMotherNature(x,y);
            island.drawTower(x,y);
            island.drawIslandInfo(x,y);
            y+= 15;

        }

        island.display();
    }

}
