package it.polimi.ingsw.server.model.characters;

import it.polimi.ingsw.server.model.PawnColour;
import it.polimi.ingsw.server.model.Professor;
import it.polimi.ingsw.server.model.board.IsleGroup;
import it.polimi.ingsw.server.model.player.Player;

import java.util.EnumMap;
import java.util.Set;

public abstract class RuleSetDecorator extends RuleSet{
    protected RuleSet ruleSet;


    public abstract boolean isToAssignProfessor(Integer studentCount, Integer professorCount);

    public abstract int getAdditionalMotherNatureMoves();

    public abstract Integer getAdditionalInfluence();

    public abstract boolean excludeTowers();

}
