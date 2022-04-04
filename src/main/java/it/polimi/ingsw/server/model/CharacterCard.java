package it.polimi.ingsw.server.model;

public class CharacterCard {
    private String name;
    private int price;
    private Strategy strategy;


    public CharacterCard(String name, int price, Strategy strategy) {
        this.name = name;
        this.price = price;
        this.strategy = strategy;
    }
}
