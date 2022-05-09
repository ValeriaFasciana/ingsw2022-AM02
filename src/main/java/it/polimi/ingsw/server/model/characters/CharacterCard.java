package it.polimi.ingsw.server.model.characters;

public class CharacterCard {
    private String name;
    private int price;
    private RuleSet ruleSet;


    public CharacterCard(String name, int price, RuleSet ruleSet) {
        this.name = name;
        this.price = price;
        this.ruleSet = ruleSet;
    }
}
