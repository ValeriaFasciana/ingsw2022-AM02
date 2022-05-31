package it.polimi.ingsw.client;

import it.polimi.ingsw.shared.enums.GameMode;

import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class Client implements Runnable {
    private ServerHandler serverHandler;
    private String ip;
    private int port;
    public final int SOCKET_TIMEOUT_S = 20000;
    private String nickname;
    private GameMode gameMode;

    public static void main(String[] args){
        Client client = new Client();
        client.setServerConnection(args[0], Integer.parseInt(args[1]));
        client.setNickname(args[2]);
        client.run();
    }

    public String getNickname() {
        return nickname;
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

        Socket server;
        try {
            server = new Socket(ip, port);
            server.setSoTimeout(SOCKET_TIMEOUT_S * 1000);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        serverHandler = new ServerHandler(server, this);
        Thread serverHandlerThread = new Thread(serverHandler, "server_" + server.getInetAddress().getHostAddress());
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
