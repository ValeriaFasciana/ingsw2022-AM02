package it.polimi.ingsw.server.model.cards.characters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.polimi.ingsw.network.messages.CharacterRequest;

/**
 *
 */
public class CharacterEffect {
    private CharacterRequest request;

    @JsonCreator
    public CharacterEffect(@JsonProperty("request") CharacterRequest request) {
        this.request = request;
    }

    @JsonGetter
    public CharacterRequest getRequest() {
        return request;
    }

}
