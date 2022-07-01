package it.polimi.ingsw.server.model.cards.characters;

public abstract class RuleSetDecorator implements RuleSet {
    protected RuleSet ruleSet;


    /**
     *
     * @param studentCount number of students of a given colour contained in the player entrance
     * @param professorCount number of students associated to the professor of the given colour
     * @return
     */
    public abstract boolean isToAssignProfessor(Integer studentCount, Integer professorCount);

    /**
     *
     * @return
     */
    public abstract int getAdditionalMotherNatureMoves();

    /**
     *
     * @return
     */
    public abstract Integer getAdditionalInfluence();

    /**
     *
     * @return
     */
    public abstract boolean excludeTowers();

}
