package it.polimi.ingsw.client;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Client implements Runnable {
    private ServerHandler serverHandler;
    private String ip;
    private int port;
    public final int SOCKET_TIMEOUT_S = 20000;
    private String nickname;

    public static void main(String[] args){
        Client client = new Client();
        client.setServerConnection(args[0], Integer.parseInt(args[1]));
        client.setNickname(args[2]);
        client.run();
    }

    public String getNickname() {
        return nickname;
    }

    public int getPort() {
        return port;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setServerConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {


        try {
            serverHandler = new ServerHandler(this);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread serverHandlerThread = new Thread(serverHandler);
        serverHandlerThread.start();
    }

    /**
     * The handler object responsible for communicating with the server.
     *
     * @return The server handler.
     */
    public ServerHandler getServerHandler() {
        return serverHandler;
    }


    /**
     * Terminates the application as soon as possible.
     */
    public synchronized void terminate() {
        if (Objects.nonNull(serverHandler))
            serverHandler.stop();
    }

    public String getIp() {
        return ip;
    }
}
