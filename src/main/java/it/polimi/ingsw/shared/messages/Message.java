package it.polimi.ingsw.shared.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.polimi.ingsw.shared.messages.fromServerToClient.GameCreatedEvent;
import it.polimi.ingsw.shared.messages.fromServerToClient.GameModeResponse;
import it.polimi.ingsw.shared.messages.fromServerToClient.JoinLobbyResponse;
import it.polimi.ingsw.shared.messages.fromServerToClient.LoginResponse;
import it.polimi.ingsw.shared.messages.fromClientToServer.GameModeRequest;
import it.polimi.ingsw.shared.messages.fromClientToServer.JoinLobbyRequest;
import it.polimi.ingsw.shared.messages.fromClientToServer.LoginRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({

        //all possible messages
        @JsonSubTypes.Type(value = LoginRequest.class, name = "Login"),
        @JsonSubTypes.Type(value = LoginResponse.class, name = "Login"),
        @JsonSubTypes.Type(value = JoinLobbyRequest.class, name = "JoinLobby"),
        @JsonSubTypes.Type(value = JoinLobbyResponse.class, name = "JoinLobby"),
        @JsonSubTypes.Type(value = GameModeRequest.class, name = "GameMode"),
        @JsonSubTypes.Type(value = GameModeResponse.class, name = "GameMode"),
        @JsonSubTypes.Type(value = GameCreatedEvent.class, name = "Event"),




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
