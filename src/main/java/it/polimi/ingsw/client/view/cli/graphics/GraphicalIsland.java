package it.polimi.ingsw.client.view.cli.graphics;

import it.polimi.ingsw.client.view.Constants;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.network.data.IsleCircleData;
import it.polimi.ingsw.network.data.IsleData;
import it.polimi.ingsw.shared.enums.PawnColour;
import it.polimi.ingsw.shared.enums.TowerColour;

import java.util.List;
import java.util.Map;


public class GraphicalIsland extends GraphicalElement {

    private final int squareHeight = 8;
    private final int squareWidth = 14;

    public GraphicalIsland() {
        super(200, 9);
    }

    public void drawIsles(List<IsleData> isles,int motherNatPos){
        int x=0;
        int y=0;
        reset();
        for(int i = 0; i< isles.size(); i++) {
            Map<PawnColour,Integer> studentMap =isles.get(i).getStudentMap();
            TowerColour tColour = isles.get(i).getTowerColour();
            int isleSize = isles.get(i).getSize();
            drawIsland(x,y,(i == motherNatPos), tColour, studentMap, i, isleSize);
            y+=16; //offset per stampare isole separate
        }
        display();
    }

    public void drawIsleCircle(int x, int y, BoardData boardData) {
        IsleCircleData isleCircleData = boardData.getGameBoard().getIsleCircle();
        int numOfIsles = isleCircleData.getIsles().size();
        int motherNatPos = boardData.getGameBoard().getMotherNaturePosition();
        reset();
        //stampa isole
        for(int i = 0; i< numOfIsles; i++) {
            Map<PawnColour,Integer> studentMap = boardData.getGameBoard().getIsleCircle().getIsles().get(i).getStudentMap();
            TowerColour tColour = isleCircleData.getIsles().get(i).getTowerColour();
            int isleSize = isleCircleData.getIsles().get(i).getSize();
            drawIsland(x,y,(i == motherNatPos), tColour, studentMap, i, isleSize);
            y+=16; //offset per stampare isole separate
        }
        display();

    }

    private void drawIsland(int x, int y, boolean isMotherNature, TowerColour tColour, Map<PawnColour,Integer> students, int id, int isleSize) {


        for (int i = 0; i < squareHeight; i++) {
            for (int j = 0; j < squareWidth; j++) {
                if (!(i > 0 && j > 0) || i == squareHeight - 1 || j == squareWidth - 1) {
                    symbols[i + x +1][j + y] = Constants.ISLE_BORDER;
                    colours[i + x +1][j + y] = Colour.ANSI_BRIGHT_GREEN;
                }
            }
        }
        drawStudent(x,y,students);
        drawID(x,y,id);
        drawIslandSize(x,y,isleSize);

        if(isMotherNature) {
            drawMotherNature(x,y);
        }
        if(tColour != null) {
            drawTower(x,y,tColour);
        }


    }

    private void drawIslandSize(int x, int y, int size) {
        symbols[x + 7][y + 1] = 'S';
        symbols[x + 7][y + 2] = 'I';
        symbols[x + 7][y + 3] = 'Z';
        symbols[x + 7][y + 4] = 'E';
        symbols[x + 7][y + 5] = ':';
        symbols[x + 7][y + 6] = String.valueOf(size).charAt(0);

    }

    private void drawMotherNature(int x, int y) {
        symbols[x + 2][y + squareWidth - 3] = 'M';
        colours[x + 2][y + squareWidth - 3] = Colour.ANSI_BRIGHT_RED;
    }

    private void drawTower(int x, int y, TowerColour tColour) {
        symbols[x + 4][y + squareWidth - 3] = Constants.TOWER;
        if (tColour == TowerColour.BLACK) {
            colours[x + 4][y + squareWidth - 3] = Colour.ANSI_BRIGHT_BLUE;
        }
        if (tColour == TowerColour.GREY) {
            colours[x + 4][y + squareWidth - 3] = Colour.ANSI_GREY;
        }
        if (tColour == TowerColour.WHITE) {
            colours[x + 4][y + squareWidth - 3] = Colour.ANSI_WHITE_BRIGHT_BOLD;
            backGroundColours[x + 4][y + squareWidth - 3] = BackGroundColour.ANSI_BG_YELLOW;
        }
    }
}



