package it.polimi.ingsw.network.messages.clienttoserver.events.jsonutils;


import it.polimi.ingsw.network.messages.clienttoserver.*;
import it.polimi.ingsw.network.messages.clienttoserver.events.*;

import it.polimi.ingsw.network.messages.servertoclient.*;


public class CommonGsonAdapters {

    private CommonGsonAdapters(){}


    public static final RuntimeTypeAdapterFactory<ServerToClientMessage> gsonServerToClientMessageAdapter = gsonServerToClientMessageAdapter();
    public static final RuntimeTypeAdapterFactory<ClientToServerMessage> gsonClientToServerMessageAdapter = gsonClientToServerMessageAdapter();





    private static RuntimeTypeAdapterFactory<ServerToClientMessage> gsonServerToClientMessageAdapter(){

        RuntimeTypeAdapterFactory<ServerToClientMessage> s2cAdapter = RuntimeTypeAdapterFactory.of(ServerToClientMessage.class);


        s2cAdapter.registerSubtype(EventNotValid.class);

        s2cAdapter.registerSubtype(PingMessageFromServer.class);

        return s2cAdapter;

    }

    private static RuntimeTypeAdapterFactory<ClientToServerMessage> gsonClientToServerMessageAdapter(){

        RuntimeTypeAdapterFactory<ClientToServerMessage> clientToJsonAdapter = RuntimeTypeAdapterFactory.of(ClientToServerMessage.class);

        clientToJsonAdapter.registerSubtype(EventMessage.class);
        clientToJsonAdapter.registerSubtype(SendNickname.class);
        clientToJsonAdapter.registerSubtype(PingMessageFromClient.class);

        return clientToJsonAdapter;

    }








}
