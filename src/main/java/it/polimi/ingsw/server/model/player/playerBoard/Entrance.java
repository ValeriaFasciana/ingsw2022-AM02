package it.polimi.ingsw.server.model.player.playerBoard;

import it.polimi.ingsw.server.model.StudentContainer;
import it.polimi.ingsw.server.model.board.Bag;

public class Entrance extends StudentContainer {
    Bag bag;
    public Entrance(Integer capacity){
        super(capacity);
        bag.pick(capacity);
    }
}
