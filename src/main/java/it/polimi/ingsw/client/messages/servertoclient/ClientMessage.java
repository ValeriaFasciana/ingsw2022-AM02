package it.polimi.ingsw.client.messages.servertoclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.json.GsonAdapters;
import it.polimi.ingsw.network.messages.clienttoserver.events.jsonutils.JsonUtility;
import it.polimi.ingsw.network.messages.clienttoserver.events.jsonutils.UUIDTypeAdapter;

import java.nio.file.Path;
import java.util.UUID;

import static it.polimi.ingsw.network.messages.clienttoserver.events.jsonutils.JsonUtility.deserializeFromString;

/**
 * All the messages from the server to the client implement this interface.<br>
 * It contains methods that can only be used in the client
 */
public interface ClientMessage {

    static ClientMessage deserialize(String jsonString) {

        Gson gson1 = new GsonBuilder()
                .registerTypeAdapterFactory(GsonAdapters.gsonToClientAdapter)
                .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
                .registerTypeHierarchyAdapter(Path.class, new JsonUtility.PathConverter())
                .create();

        return deserializeFromString(jsonString, ClientMessage.class, gson1);
    }

    /**
     * Will update values in the client that will fire view updates.
     */
    void processMessage(ServerHandler serverHandler);
}
