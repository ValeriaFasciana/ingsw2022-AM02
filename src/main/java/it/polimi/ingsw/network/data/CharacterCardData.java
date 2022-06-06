package it.polimi.ingsw.network.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CharacterCardData {
    int id;
    int price;

    @JsonCreator
    public CharacterCardData(@JsonProperty("id") int id,
                             @JsonProperty("cost") int price){
        this.id = id;
        this.price = price;
    }

    @JsonGetter
    public int getId() {
        return id;
    }

    @JsonGetter
    public int getPrice() {
        return price;
    }


}
