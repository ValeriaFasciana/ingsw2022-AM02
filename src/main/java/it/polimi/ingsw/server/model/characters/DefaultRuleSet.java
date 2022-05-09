package it.polimi.ingsw.server.model.characters;

import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.action.Action;

import java.util.*;


public class DefaultRuleSet extends RuleSet{
    private ArrayList<Action> defaultPlanningActions;
    private ArrayList<Action> defaultActionPhaseActions;

    public DefaultRuleSet() {}



    public ArrayList<Action> getAvailableActions(Phase turnPhase){
        switch (turnPhase){
            case PLANNING -> {
                return defaultPlanningActions;
            }
            case ACTION -> {
                return defaultActionPhaseActions;
            }
        }
        return null;
    }


}
