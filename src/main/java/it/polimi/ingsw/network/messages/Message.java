package it.polimi.ingsw.network.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.network.messages.clienttoserver.PingMessageFromClient;
import it.polimi.ingsw.network.messages.servertoclient.PingMessageFromServer;
import it.polimi.ingsw.network.messages.servertoclient.events.GameCreatedEvent;
import it.polimi.ingsw.network.messages.servertoclient.events.JoinLobbyResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.LoginResponse;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({

        //all possible messages
        @JsonSubTypes.Type(value = LoginResponse.class, name = "Login"),
        @JsonSubTypes.Type(value = JoinLobbyResponse.class, name = "JoinLobby"),
        @JsonSubTypes.Type(value = GameCreatedEvent.class, name = "Event"),
        @JsonSubTypes.Type(value = PingMessageFromClient.class, name = "Ping"),
        @JsonSubTypes.Type(value = PingMessageFromServer.class, name = "Ping"),




        /*
        @JsonSubTypes.Type(value = LobbyCreatedEvent.class, name = "CreateLobby"),
        @JsonSubTypes.Type(value = WorkerAddedEvent.class, name = "AddWorker"),
        @JsonSubTypes.Type(value = BuildableCellsResponse.class, name = "BuildableCells"),
        @JsonSubTypes.Type(value = EndTurnEvent.class, name = "EndTurn"),
        @JsonSubTypes.Type(value = PlayerBuildEvent.class, name = "PlayerBuild"),
        @JsonSubTypes.Type(value = PlayerMoveEvent.class, name = "PlayerMove"),
        @JsonSubTypes.Type(value = WorkerSelectedResponse.class, name = "SelectWorker"),
        @JsonSubTypes.Type(value = WalkableCellsResponse.class, name = "WalkableCells"),
        @JsonSubTypes.Type(value = ChooseInitialGodsResponse.class, name = "ChooseInitialGodsResponse"),
        @JsonSubTypes.Type(value = ChosenGodsEvent.class, name = "ChooseYourGodResponse"),
        @JsonSubTypes.Type(value = ChooseStartingPlayerRequest.class, name = "ChooseStartingPlayer"),
        @JsonSubTypes.Type(value = ChooseStartingPlayerResponse.class, name = "ChooseStartingPlayer"),
        @JsonSubTypes.Type(value = PlayerRemovedEvent.class, name = "PlayerRemoved"),
        @JsonSubTypes.Type(value = WinnerDeclaredEvent.class, name = "WinnerDeclared"),
        @JsonSubTypes.Type(value = EndTurnEvent.class, name = "EndTurn"),
        @JsonSubTypes.Type(value = PlayerMoveEvent.class, name = "PlayerMove"),
        @JsonSubTypes.Type(value = GameStartEvent.class, name = "GameStarted"),
        @JsonSubTypes.Type(value = PlayerRemovedEvent.class, name = "PlayerRemoved"),
        @JsonSubTypes.Type(value = WinnerDeclaredEvent.class, name = "WinnerDeclared"),
        */

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

    public String getUsername() {
        return username;
    }

    public Type getType() {
        return type;
    }
}
