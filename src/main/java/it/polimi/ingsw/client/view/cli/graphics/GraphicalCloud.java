package it.polimi.ingsw.client.view.cli.graphics;

import it.polimi.ingsw.network.data.GameBoardData;
import it.polimi.ingsw.server.model.StudentContainer;


public class GraphicalCloud extends GraphicalElement{

    private final int squareHeight = 7;
    private final int squareWidth = 14;

    public GraphicalCloud() {
        super(50, 9);
    }

    public void drawSetOfClouds(int x, int y, GameBoardData cloudData, StudentContainer students) {
        int numOfClouds = cloudData.getClouds().size();

        reset();

        for(int i = 0; i < numOfClouds; i++) {
            drawCloud(x,y,i+1);
            drawStudent(x,y,students);
            y = y+16; //offset to draw set of cloud
        }
    }


    private void drawCloud(int x, int y, int indexCloud) {
        for (int i = 0; i < squareHeight; i++) {
            for (int j = 0; j < squareWidth; j++) {
                if (!(i > 0 && j>0) || i == squareHeight-1 || j == squareWidth - 1){
                    symbols[i + x+1][j + y] = '▋';
                    colours[i + x+1][j + y] = Colour.ANSI_BRIGHT_BLUE;}
            }
        }
        drawID(x,y,indexCloud);
    }
}
