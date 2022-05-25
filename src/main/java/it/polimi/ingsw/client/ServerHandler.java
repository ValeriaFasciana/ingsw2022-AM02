package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.clienttoserver.PingMessageFromClient;
import it.polimi.ingsw.shared.JacksonMessageBuilder;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;


public class ServerHandler implements Runnable
{

    private final Socket server;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private final Client owner;
    private final AtomicBoolean shouldStop = new AtomicBoolean(false);
    private Thread ping;
    private final JacksonMessageBuilder jsonParser;
    private final ClientMessageVisitor messageHandler;

    public ServerHandler(Socket server, Client owner)
    {
        this.server = server;
        this.owner = owner;
        this.jsonParser = new JacksonMessageBuilder();
        this.messageHandler = new ClientMessageHandler();
    }

    @Override
    public void run()
    {
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + server.getInetAddress());
            return;
        }

        startPing();

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("server " + server.getInetAddress() + " connection dropped");
        }

        try {
            server.close();
        } catch (IOException ignored) { }
        if(ping.isAlive()){
            stopPing();
        }
        owner.terminate();
    }

    private void handleClientConnection() throws IOException {

        boolean stop = false;
        while (!stop) {
            /* read commands from the server and process them */
            try {
                String next = input.readObject().toString();
                Message message = jsonParser.fromStringToMessage(next);
                ((MessageFromServerToClient)message).callVisitor(this.messageHandler);


            } catch (SocketTimeoutException e) {
                //   MessageUtility.displayErrorMessage("Lost connection with the server");
                /* Check if we were interrupted because another thread has asked us to stop */
                if (shouldStop.get()) {
                    /* Yes, exit the loop gracefully */
                    stop = true;
                } else {
                    /* No, rethrow the exception */
                    throw e;
                }
            } catch (IOException e) {
                System.out.println("Disconnected from the server");
                if (shouldStop.get()) {
                    stop = true;
                } throw e;
            } catch (ClassNotFoundException | ClassCastException e) {
                System.out.println("invalid stream from server : " + e.toString());
                break;
            }

        }
    }

    public Client getClient()
    {
        return owner;
    }



    public void sendCommandMessage(MessageFromClientToServer message)
    {
        try {
            String json = jsonParser.fromMessageToString(message);
            output.writeObject(json);
        } catch (IOException e) {
            System.out.println("Communication error");
            owner.terminate();
        }
    }

    public void stop()
    {
        shouldStop.set(true);
        try {
            server.shutdownInput();
        } catch (IOException ignored) { }
    }

    private void startPing(){
        try {
            InetAddress serverInetAddress = InetAddress.getByName(getClient().getIp());

            ping = new Thread(() -> {

                try {
                    int counter = 0;
                    while (true) {
                        Thread.sleep(5000);
                        MessageFromClientToServer pingMessage = new PingMessageFromClient("client", Type.PING);
                        sendCommandMessage(pingMessage);
                        counter++;
                        System.out.println(counter);
                    }
                } catch (InterruptedException e) {
                } finally {
                    Thread.currentThread().interrupt();
                }
            });
            ping.start();

        } catch (UnknownHostException e) {
            System.out.println("Unable to convert IP address to InetAddress");
        }
    }

    private void stopPing(){
        ping.interrupt();
    }
}