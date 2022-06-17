package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.network.messages.servertoclient.events.*;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({

        //all possible messages
        @JsonSubTypes.Type(value = GameCreatedEvent.class, name = "GameCreatedEvent"),
        @JsonSubTypes.Type(value = ChooseAssistantResponse.class, name = "ChooseAssistantResponse"),
        @JsonSubTypes.Type(value = ChooseCloudResponse.class, name = "ChooseCloudResponse"),
        @JsonSubTypes.Type(value = LobbyInfoResponse.class, name = "LobbyInfoResponse"),
        @JsonSubTypes.Type(value = MoveMotherNatureResponse.class, name = "MoveMotherNatureResponse"),
        @JsonSubTypes.Type(value = MoveStudentToHallResponse.class, name = "MoveStudentToHallResponse"),
        @JsonSubTypes.Type(value = MoveStudentToIsleResponse.class, name = "MoveStudentToIsleResponse"),
        @JsonSubTypes.Type(value = NicknameResponse.class, name = "NicknameResponse"),
        @JsonSubTypes.Type(value = BoardUpdateResponse.class, name = "BoardUpdateResponse"),
        @JsonSubTypes.Type(value = ChooseAssistantRequest.class, name = "ChooseAssistantRequest"),
        @JsonSubTypes.Type(value = ChooseCloudRequest.class, name = "ChooseCloudRequest"),
        @JsonSubTypes.Type(value = JoinedLobbyResponse.class, name = "JoinedLobbyResponse"),
        @JsonSubTypes.Type(value = LobbyCreatedResponse.class, name = "LobbyCreatedResponse"),
        @JsonSubTypes.Type(value = MoveMotherNatureRequest.class, name = "MoveMotherNatureRequest"),
        @JsonSubTypes.Type(value = MoveStudentFromEntranceRequest.class, name = "MoveStudentFromEntranceRequest"),
        @JsonSubTypes.Type(value = NotYourTurnResponse.class, name = "NotYourTurnResponse"),
        @JsonSubTypes.Type(value = MoveStudentFromCardRequest.class, name = "MoveStudentFromCardRequest"),
        @JsonSubTypes.Type(value = ChooseIslandRequest.class, name = "ChooseIslandRequest"),
        @JsonSubTypes.Type(value = ChooseColourRequest.class, name = "ChooseColourRequest"),
        @JsonSubTypes.Type(value = ChooseColourResponse.class, name = "ChooseColourResponse"),
        @JsonSubTypes.Type(value = ChooseIslandResponse.class, name = "ChooseIslandResponse"),
        @JsonSubTypes.Type(value = UseCharacterEffectRequest.class, name = "UseCharacterEffectRequest"),
        @JsonSubTypes.Type(value = MoveStudentFromCardResponse.class, name = "MoveStudentFromCardResponse"),
        @JsonSubTypes.Type(value = ExchangeStudentsResponse.class, name = "ExchangeStudentsResponse"),
        @JsonSubTypes.Type(value = ExchangeStudentsRequest.class, name = "ExchangeStudentsRequest"),
        @JsonSubTypes.Type(value = InvalidUsernameResponse.class, name = "InvalidUsernameResponse"),
})
public abstract class Message {
    private String username;
    private final Type type;

    /**
     * Message constructor
     * @param username the sender's username
     * @param type     the message type
     */
    @JsonCreator
    public Message(@JsonProperty("username") String username,
                   @JsonProperty("type") Type type) {
        this.username = username;
        this.type = type;
    }
    @JsonGetter
    public String getUsername() {
        return username;
    }

    @JsonGetter
    public Type getType() {
        return type;
    }


    public void setUserName(String username){
        this.username = username;
    }
}
