package it.polimi.ingsw.network.messages;

import java.util.UUID;

/**
 * A generic message sent over a network link.
 */
public abstract class NetworkMessage
{
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

}
