package it.polimi.ingsw.server.controller.action;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.EnumMap;
import java.util.Map;

public class DiscardStudentsAction implements Action {
    private PawnColour toDiscardColour;
    private int numberOfStudents;

    /**
     * Action handled by the Game Controller
     * @param toDiscardColour colour to discard
     * @param numberOfStudents  number of students
     */
    @JsonCreator
    public DiscardStudentsAction(PawnColour toDiscardColour, int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
        this.toDiscardColour = toDiscardColour;
    }

    /**
     * Method to handle the actions of the visitor
     * @param game visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor game) {
        Map<PawnColour,Integer> toRemoveMap = new EnumMap<>(PawnColour.class);
        toRemoveMap.put(toDiscardColour,numberOfStudents);
        game.getPlayers().values().forEach(player -> player.getBoard().getHall().removeStudents(toRemoveMap));
        game.notifyBoardListeners();
    }
}
