package it.polimi.ingsw.client.json;

import it.polimi.ingsw.client.messages.servertoclient.*;
import it.polimi.ingsw.network.messages.clienttoserver.events.jsonutils.RuntimeTypeAdapterFactory;

public class GsonAdapters {

    private GsonAdapters(){}

    public static final RuntimeTypeAdapterFactory<ClientMessage> gsonToClientAdapter = gsonToClientAdapter();

    private static RuntimeTypeAdapterFactory<ClientMessage> gsonToClientAdapter() {
        RuntimeTypeAdapterFactory<ClientMessage> gsonToClientAdapter = RuntimeTypeAdapterFactory.of(ClientMessage.class);
        gsonToClientAdapter.registerSubtype(PingMessageFromServer.class);

        return gsonToClientAdapter;

    }

}
