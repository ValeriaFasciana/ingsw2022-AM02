package it.polimi.ingsw.network.messages;

public enum Type {
    // all available messages
    OK,
    PING,
    ERROR_GENERAL,
    NOTIFY,
    CLIENT_REQUEST,
    SERVER_REQUEST,
    SERVER_RESPONSE,
    INVALID_NAME,
    GAME_ALREADY_EXISTING,
    LOBBY_FULL,
    NO_LOBBY_AVAILABLE, //player chooses an empty cloud during Planning Mode
    NOT_ENOUGH_COINS, NEW_LOBBY, JOINED_LOBBY, CLIENT_RESPONSE, NOT_YOUR_TURN,
}
