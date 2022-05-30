package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.clienttoserver.PingMessageFromClient;
import it.polimi.ingsw.shared.jsonutils.JacksonMessageBuilder;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;


public class ServerHandler implements Runnable
{

    private final Socket server;
    private BufferedReader input;
    private OutputStreamWriter output;
    private final Client owner;
    private final AtomicBoolean shouldStop = new AtomicBoolean(false);
    private Thread ping;
    private final JacksonMessageBuilder jsonParser;
    private final ClientMessageVisitor messageHandler;
    private InetAddress clientAddress;
    public static final int PING_TIMEOUT = 5000000;
    public static final String PING = "ping";
    private ScheduledThreadPoolExecutor ex;

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
            this.input = new BufferedReader(new InputStreamReader(server.getInputStream()));
            this.output = new OutputStreamWriter(server.getOutputStream());
            this.output.flush();
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
                String next =input.readLine();
                if (PING.equals(next)){}

                else {
                    Message message = jsonParser.fromStringToMessage(next);
                    ((MessageFromServerToClient)message).callVisitor(this.messageHandler);
                }


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
            } catch (ClassCastException e) {
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
            output.write(json + "\n");
            output.flush();
        } catch (IOException e) {
            System.out.println("Communication error");
            owner.terminate();
        }
    }

    public void sendMessage(String string) throws IOException {
        try {
            output.write(string + "\n");
            output.flush();
        } catch (IOException e) {
            server.close();

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
                        sendCommandMessage(new PingMessageFromClient("client"));
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

    public void ping() throws IOException {
        if (ex != null)
            ex.shutdownNow();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        sendMessage(PING);
        ex = new ScheduledThreadPoolExecutor(5);
        ex.schedule(() -> {
            System.out.println("User  disconnected!");
            //messageHandler.disconnect();
        }, PING_TIMEOUT, TimeUnit.SECONDS);
    }

    public InetAddress getClientAddress() {
        return clientAddress;
    }

    public void notify(Message message) {
    }
}