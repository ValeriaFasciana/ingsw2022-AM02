package it.polimi.ingsw.server.model.cards.characters;


public class DefaultRuleSet implements RuleSet{

    private static DefaultRuleSet single_instance = null;

    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself
    private DefaultRuleSet()
    {
    }

    // Static method
    // Static method to create instance of Singleton class
    public static DefaultRuleSet getInstance()
    {
        if (single_instance == null)
            single_instance = new DefaultRuleSet();

        return single_instance;
    }

    public boolean isToAssignProfessor(Integer studentCount, Integer professorCount) {
        return (studentCount > professorCount);
    }

    public int getAdditionalMotherNatureMoves() {
        return 0;
    }

    public Integer getAdditionalInfluence() {
        return 0;
    }

    public boolean excludeTowers(){
        return false;
    }


}
