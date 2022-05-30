package it.polimi.ingsw.client.view.cli.graphics;

public class GraphicalIsland extends GraphicalElement{

    private final int squareHeight = 4;
    private final int squareWidth = 5;

    public GraphicalIsland() {
        super(18, 5);
    }

    //è da creare metodo per disegnare le isole a seconda se sono ancora indipendenti o unite
    /**
     * Draw a single square of island
     * @param x the x coord where the square has to be drawn
     * @param y the y coord where the square has to be drawn
     */
    private void drawSquare(int x, int y) {
        char[] boxChars = getChars();
        char upLeft = boxChars[0];
        char upRight = boxChars[1];
        char horizontal = boxChars[2];
        char bottomRight = boxChars[3];
        char bottomLeft = boxChars[4];
        char vertical = boxChars[5];

        for (int i = 0; i < squareHeight; i++) {
            for (int j = 0; j < squareWidth; j++) {
                if (i == 0 && j == 0)
                    symbols[i + x][j + y] = upLeft;
                else if (i == 0 && j == squareWidth - 1)
                    symbols[i + x][j + y] = upRight;
                else if (i == squareHeight - 1 && j == 0)
                    symbols[i + x][j + y] = bottomLeft;
                else if (i == squareHeight - 1 && j == squareWidth - 1)
                    symbols[i + x][j + y] = bottomRight;
                else if (i == 0 || i == squareHeight - 1)
                    symbols[i + x][j + y] = horizontal;
                else if (j == 0 || j == squareWidth - 1)
                    symbols[i + x][j + y] = vertical;
            }
        }
    }
    /**
     * Returns the symbols to be used when drawing a square
     * @return an array of four chars to be used in the corners of the square
     */
    private char[] getChars() {
        char [] boxChars = new char[6];
        boxChars[0] = '┌';
        boxChars[1] = '┐';
        boxChars[2] = '─';
        boxChars[3] = '┘';
        boxChars[4] = '└';
        boxChars[5] = '│';

        return boxChars;
    }
}
