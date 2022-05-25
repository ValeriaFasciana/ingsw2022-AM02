package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.board.GameBoardData;

import java.util.ArrayList;

public class BoardData {
    private ArrayList<PlayerBoardData> playerBoards;
    private GameBoardData gameBoard;

    public BoardData(ArrayList<PlayerBoardData> playerBoards, GameBoardData gameBoard) {
        this.playerBoards = playerBoards;
        this.gameBoard = gameBoard;
    }
}
