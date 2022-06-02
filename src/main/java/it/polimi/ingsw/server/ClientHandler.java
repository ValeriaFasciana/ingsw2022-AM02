package it.polimi.ingsw.server;


import it.polimi.ingsw.shared.Constants;
import it.polimi.ingsw.shared.jsonutils.JacksonMessageBuilder;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A class that represents the client inside the server.
 */
public class ClientHandler implements Runnable
{
    private transient Socket client;
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName());
    private BufferedReader inputStream;
    private OutputStreamWriter output;
    private InetAddress clientAddress;
    private String nickname;
    private final JacksonMessageBuilder jsonParser;
    private ServerMessageVisitor messageHandler;
    private ScheduledThreadPoolExecutor ex;
    public static final int PING_TIMEOUT = 5000000;
    public static final String PING = "ping";



    /**
     * Initializes a new handler using a specific socket connected to
     * a client.
     * @param client The socket connection to the client.
     */
    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        clientAddress = client.getInetAddress();
        this.jsonParser = new JacksonMessageBuilder();
        try {
            inputStream = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new OutputStreamWriter(client.getOutputStream());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            closeConnection();
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Connects to the client and runs the event loop.
     */
    @Override
    public void run()
    {
        try {
            while (true) {
                Message message;
                String input = inputStream.readLine();

                if (Constants.PING.equals(input))
                    new Thread(this::ping).start();
                else {
                    logger.log(Level.FINE, "Message received");
                    message = jsonParser.fromStringToMessage(input);
                    System.out.print("\nMessage arrived to server by user "+message.getUsername()+": "+message);
                    ((MessageFromClientToServer) message).callVisitor(messageHandler);
                }
            }
        } catch (IOException | NullPointerException e) {
            logger.log(Level.SEVERE, ("Message format non valid, kicking " + nickname + ": " + e.getMessage()) + "\n" );
            //server.onDisconnect(this.user);
        }
    }


    public void ping() {
        if (ex != null)
            ex.shutdownNow();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        notify(Constants.PING);
        ex = new ScheduledThreadPoolExecutor(5);
        ex.schedule(() -> {
            System.out.println("User  disconnected!");
        }, PING_TIMEOUT, TimeUnit.SECONDS);
    }




    public InetAddress getClientAddress() {
        return clientAddress;
    }

    public void notify(Message message) {
        String stringMessage = jsonParser.fromMessageToString(message);
        notify(stringMessage);
        System.out.println("Message sent from server to user "+ nickname+ ": "+stringMessage);
    }
    public void notify(String message){

        try {
            output.write(message + "\n");
            output.flush();
            //logger.log(Level.INFO, ("Message" + message.getClass() + " sent from room to: " + nickname).replace(ReservedRecipients.BROADCAST.toString(), "all players"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            try {
                logger.log(Level.SEVERE, "Message format non valid, disconnecting " );
                client.close();
            } catch (IOException e2) {
                logger.log(Level.SEVERE, " ");
                logger.log(Level.SEVERE, e2.getMessage(), e2);
            }

        }

    }
    public void closeConnection() {
        try {
            client.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void setMessageHandler(ServerMessageVisitor messageHandler) {
        this.messageHandler = messageHandler;
    }
}