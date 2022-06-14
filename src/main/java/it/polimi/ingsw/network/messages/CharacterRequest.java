package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.network.messages.clienttoserver.events.ExchangeStudentsResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseColourRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseIslandRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.ExchangeStudentsRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.MoveStudentFromCardRequest;
import it.polimi.ingsw.shared.Constants;


@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ChooseColourRequest.class, name = "ChooseColourRequest"),
        @JsonSubTypes.Type(value = ChooseIslandRequest.class, name = "ChooseIslandRequest"),
        @JsonSubTypes.Type(value = MoveStudentFromCardRequest.class, name = "MoveStudentFromCardRequest"),
        @JsonSubTypes.Type(value = ExchangeStudentsResponse.class, name = "ExchangeStudentsResponse"),
        @JsonSubTypes.Type(value = ExchangeStudentsRequest.class, name = "ExchangeStudentsRequest")
})

@JsonTypeName("CharacterRequest")
public abstract class CharacterRequest extends MessageFromServerToClient{
    @JsonIgnore
    private int characterId;

    @JsonCreator
    public CharacterRequest() {
        super(Constants.tempUsername, Type.SERVER_REQUEST);
    }


    public void setCharacterId(int characterId){
        this.characterId = characterId;
    }

    @JsonGetter
    public int getCharacterId() {
        return characterId;
    }
}
