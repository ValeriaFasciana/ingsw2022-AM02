package it.polimi.ingsw.server.controller.action;

import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;

public class ExchangeStudentsAction implements Action {

    int characterId;
    MovementDestination from;
    MovementDestination to;
    Map<PawnColour, Integer> fromMap;
    Map<PawnColour, Integer> toMap;

    public ExchangeStudentsAction(int characterId,
                                  MovementDestination from,
                                  MovementDestination to,
                                  Map<PawnColour, Integer> fromMap,
                                  Map<PawnColour, Integer> toMap) {
        this.characterId = characterId;
        this.from = from;
        this.to = to;
        this.fromMap = fromMap;
        this.toMap = toMap;
    }

    @Override
    public void accept(ActionVisitor game) {
        switch (from){
            case CARD -> game.getCharacter(characterId).removeStudents(fromMap);
            case ENTRANCE -> game.getCurrentPlayer().getBoard().removeStudentsFromEntrance(fromMap);
        }
        switch (to){
            case HALL -> game.addStudentsToCurrentPlayerHall(toMap);
        }
    }
}
