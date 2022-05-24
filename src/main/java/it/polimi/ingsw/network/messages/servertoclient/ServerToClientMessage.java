package it.polimi.ingsw.network.messages.servertoclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.network.messages.clienttoserver.events.jsonutils.CommonGsonAdapters;
import it.polimi.ingsw.network.messages.NetworkMessage;

import static it.polimi.ingsw.network.messages.clienttoserver.events.jsonutils.JsonUtility.serialize;

/**
 * A network message from the server to the client visible to both.

 * Can be send even without being an answer to a command.<br>
 * Each subclass message has it's divided into two.
 * One class in the network package that contains common methods, constructors and attributes between client and server.<br>
 * A subclass of the class in the network package defined in the client that implements an interface with methods only working in the client.
 * All the subclasses defined in the network package are registered in serialized().
 * The class in the network and the class in the client packages have the same name to ease the serialization.
 */
public abstract class ServerToClientMessage extends NetworkMessage
{

    /**
     * Method used for serialization.
     * @return a json string representing the message and it's resourceType.
     */
    public String serialized()
    {

        Gson gson1 = new GsonBuilder()
                .registerTypeAdapterFactory(CommonGsonAdapters.gsonServerToClientMessageAdapter).enableComplexMapKeySerialization()
                .create();

        return serialize(this, ServerToClientMessage.class, gson1);

    }

}
