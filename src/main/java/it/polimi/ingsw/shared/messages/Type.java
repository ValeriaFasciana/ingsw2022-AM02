package it.polimi.ingsw.shared.messages;

public enum Type {
    // all available messages
    OK,
    ERROR_GENERAL,
    NOTIFY,
    CLIENT_REQUEST,
    SERVER_REQUEST,

    INVALID_NAME,
    GAME_ALREADY_EXISTING,
    LOBBY_FULL,
    NO_LOBBY_AVAILABLE, //player chooses an empty cloud during Planning Mode
    NOT_ENOUGH_COINS,
}
