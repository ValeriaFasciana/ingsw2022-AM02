package it.polimi.ingsw.server.model.cards.characters;

public class CharacterCard {
    private final int id;
    private int price;
    private boolean excludeTowersFromInfluence;
    private int addedInfluencePoints;
    private int motherNatureAdditionalMovements;
    private boolean affectProfessorAssignment;
    private RuleSet ruleSet;
    private boolean alreadyPlayed = false;

    public CharacterCard(int id, int price, boolean excludeTowersFromInfluence, int addedInfluencePoints, int motherNatureAdditionalMovements, boolean affectProfessorAssignment) {
        this.id = id;
        this.price = price;
        this.excludeTowersFromInfluence = excludeTowersFromInfluence;
        this.addedInfluencePoints = addedInfluencePoints;
        this.motherNatureAdditionalMovements = motherNatureAdditionalMovements;
        this.affectProfessorAssignment = affectProfessorAssignment;
        this.ruleSet = new RulesAffector(DefaultRuleSet.getInstance(),excludeTowersFromInfluence,addedInfluencePoints,motherNatureAdditionalMovements,affectProfessorAssignment);
    }

    public RuleSet getRuleSet() {
        return ruleSet;
    }

    public void increasePrice() {
        if(alreadyPlayed)return;
        price++;
        alreadyPlayed = true;
    }
}
