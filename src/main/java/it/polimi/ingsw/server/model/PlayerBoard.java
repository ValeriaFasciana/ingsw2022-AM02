package it.polimi.ingsw.server.model;

public class PlayerBoard {
    private Entrance entrance;
    private Hall hall;


    public PlayerBoard(Game game) {
        switch (game.getNumberOfPlayers()){
            case 2: case 4 :
                this.entrance = new Entrance(7);
                break;

            case 3 :
                this.entrance = new Entrance(9);
        }
        this.hall = new Hall();

    }



}
