package it.polimi.ingsw.server;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.polimi.ingsw.server.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.UUID;

public class Server
{

    public static int SOCKET_PORT = 7831;

    public static final int SOCKET_TIMEOUT_S = 2000;

    private static final LobbyManager lobbyManager = new LobbyManager();

    public Server() {

    }

    public static void main(String[] args)
    {
        if (args.length==2){
            SOCKET_PORT=Integer.parseInt(args[1]);
        }
        ServerSocket socket;
        System.out.println("My port is : "+ SOCKET_PORT);
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        while (true) {
            try {

                //debug mode
                Thread inputThread = new Thread(() -> {
                    Scanner scanner = new Scanner(System.in);
                    while (true) {

                        String input = scanner.nextLine();

                    }
                });
                inputThread.start();

                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a it.polimi.ingsw.server.ClientHandler */
                Socket client = socket.accept();
                client.setSoTimeout(SOCKET_TIMEOUT_S * 1000);
                ClientHandler clientHandler = new ClientHandler(client);

                //lobbyManager.handleNewClient(clientHandler);


                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();



            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }

}
