package it.polimi.ingsw.client.view.cli.graphics;

import it.polimi.ingsw.client.view.Constants;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.CloudData;
import it.polimi.ingsw.network.data.GameBoardData;
import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * This class represents the common features of the clouds
 */
public class GraphicalCloud extends GraphicalElement{

    private final int squareHeight = 7;
    private final int squareWidth = 14;

    public GraphicalCloud() {
        super(50, 9);
    }

    /**
     * Draws the elements for the clouds
     * @param x starting height
     * @param y starting width
     * @param cloudIndexes set of indexes of the clouds to draw
     * @param board data of the board
     */
    public void drawSetOfClouds(int x, int y, Set<Integer> cloudIndexes, BoardData board) {

        int numOfClouds = cloudIndexes.size();

        reset();

        for(Integer index : cloudIndexes) {
            Map<PawnColour,Integer> students = board.getGameBoard().getClouds().get(index).getStudentMap();
            drawCloud(x,y,index);
            drawStudent(x,y,students);
            y = y+16; //offset to draw set of cloud
        }
        display();
    }


    /**
     * Draws the features of a single cloud
     * @param x starting height
     * @param y starting width
     * @param indexCloud index of the cloud to draw
     */

    private void drawCloud(int x, int y, int indexCloud) {
        for (int i = 0; i < squareHeight; i++) {
            for (int j = 0; j < squareWidth; j++) {
                if (!(i > 0 && j>0) || i == squareHeight-1 || j == squareWidth - 1){
                    symbols[i + x+1][j + y] = Constants.CLOUD_BORDER;
                    colours[i + x+1][j + y] = Colour.ANSI_BRIGHT_BLUE;}
            }
        }
        drawID(x,y,indexCloud);
    }
}
