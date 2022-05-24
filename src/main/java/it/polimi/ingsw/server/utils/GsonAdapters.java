
package it.polimi.ingsw.server.utils;

import it.polimi.ingsw.network.messages.clienttoserver.events.jsonutils.RuntimeTypeAdapterFactory;

import it.polimi.ingsw.server.messages.clienttoserver.*;



public class GsonAdapters {

    private GsonAdapters(){}

    public static final RuntimeTypeAdapterFactory<ServerMessage> gsonServerMessageAdapter = gsonServerMessageAdapter();




    private static RuntimeTypeAdapterFactory<ServerMessage> gsonServerMessageAdapter(){

        RuntimeTypeAdapterFactory<ServerMessage> gsonToServerMessageAdapter  = RuntimeTypeAdapterFactory.of(ServerMessage.class);

        gsonToServerMessageAdapter.registerSubtype(EventMessage.class);
        gsonToServerMessageAdapter.registerSubtype(SendNickname.class);
        gsonToServerMessageAdapter.registerSubtype(PingMessageFromClient.class);

        return gsonToServerMessageAdapter;

    }



}