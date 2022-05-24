
package it.polimi.ingsw.server.messages.clienttoserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.utils.GsonAdapters;

import static it.polimi.ingsw.network.messages.clienttoserver.events.jsonutils.JsonUtility.deserializeFromString;

/**
 * All the messages from the client to the server implement this interface.
 * It contains methods that can only be used in the server.
 */
public interface ServerMessage {
    void processMessage(ClientHandler clientHandler);

    static ServerMessage deserialize(String jsonString){

        Gson gson1 = new GsonBuilder()
                .registerTypeAdapterFactory(GsonAdapters.gsonServerMessageAdapter)
                .create();

        return deserializeFromString(jsonString, ServerMessage.class,gson1);
    }

}