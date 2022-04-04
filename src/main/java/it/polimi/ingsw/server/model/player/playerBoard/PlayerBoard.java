package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.server.model.Game;

public class PlayerBoard {
    private Entrance entrance;
    private Hall hall;


    public PlayerBoard(int studentsInEntrance) {
        this.entrance = new Entrance(studentsInEntrance);
        this.hall = new Hall();
    }



}
