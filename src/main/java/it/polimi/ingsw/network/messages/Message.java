package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.annotation.*;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;
import it.polimi.ingsw.network.messages.servertoclient.events.*;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
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
        @JsonSubTypes.Type(value = NotYourTurnResponse.class, name = "NotYourTurnResponse")
})
public abstract class Message {
    private final String username;
    private final Type type;
    public final UUID identifier = UUID.randomUUID();

    /**
     * Unique identifier for the message.
     * Allows matching a message with its response.
     * Not all messages have a response.
     * @return The identifier
     */
    @JsonGetter
    public UUID getIdentifier()
    {
        return identifier;
    }

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
}
