package it.polimi.ingsw.server;

import it.polimi.ingsw.network.messages.clienttoserver.PingMessageFromClient;
import it.polimi.ingsw.network.messages.servertoclient.PingMessageFromServer;
import it.polimi.ingsw.shared.jsonutils.JacksonMessageBuilder;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * A class that represents the client inside the server.
 */
public class ClientHandler implements Runnable
{
    private transient Socket client;
    private InetAddress clientAddress;
    private String nickname;
    private transient Thread ping;
    private final JacksonMessageBuilder jsonParser;
    private ServerMessageVisitor messageHandler;
    private BufferedReader input;
    private OutputStreamWriter output;
    private ScheduledThreadPoolExecutor ex;
    public static final int PING_TIMEOUT = 5000000;
    public static final String PING = "ping";



    /**
     * Initializes a new handler using a specific socket connected to
     * a client.
     * @param client The socket connection to the client.
     */
    public ClientHandler(Socket client)
    {
        this.client = client;
        clientAddress = client.getInetAddress();
        this.jsonParser = new JacksonMessageBuilder();
    }

    /**
     * Connects to the client and runs the event loop.
     */
    @Override
    public void run()
    {
        try {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new OutputStreamWriter(client.getOutputStream());
        } catch (IOException e) {
            System.out.println("could not open connection to " + client.getInetAddress());
            return;
        }

        System.out.println("Connected to " + client.getInetAddress());
        //addPlayerToLobby(this);

        try {
            handleClientConnection();

        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * An event loop that receives messages from the client and processes
     * them in the order they are received.
     * @throws IOException If a communication error occurs.
     */
    private void handleClientConnection() throws IOException
    {

        startPing();

        try {
            while (true) {


                /* read commands from the client, process them, and send replies */
                String next = input.readLine();
                Message command = jsonParser.fromStringToMessage(next);

                if (Objects.nonNull(command)) {
                    if (!(command instanceof PingMessageFromClient)) {
                        ((MessageFromClientToServer)command).callVisitor(this.messageHandler);
                    }


                }
            }

        } catch(ClassCastException e) {
            System.out.println("invalid stream from client" + e.toString());

        }catch (SocketTimeoutException | SocketException | EOFException to){ //no message from client
            System.out.println("No more messages from : " + client.getInetAddress());
        }
    }


    /**
     * it starts a pinging service from the server to the client writing a ping event to the socket every 5000 millis
     */
    private void startPing() {

        ping = new Thread(() -> {

            try {
                int counter = 0;
                while (true) {
                    Thread.sleep(5000);  // send a ping every SOCKET_TIMEOUT/4 seconds
                    MessageFromServerToClient pingMessage = (new PingMessageFromServer("server"));
                    sendAnswerMessage(pingMessage);
                    counter++;
                }
            } catch (InterruptedException e) {
                System.out.println("Ping Communication Interrupted with " + client.getInetAddress());
            } finally {
                Thread.currentThread().interrupt();
            }
        });

        ping.start();
    }

    /**
     * The game instance associated with this client.
     * @return The game instance.
     */




    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getNickname(){
        return nickname;
    }

    /**
     * Sends a message to the client.
     * @param answerMsg The message to be sent.
     * @throws IOException If a communication error occurs.
     */
    public void sendAnswerMessage(MessageFromServerToClient answerMsg)
    {
        String message = jsonParser.fromMessageToString(answerMsg);
        try {
            output.write(message + "\n");
            output.flush();
        } catch (IOException e){ //thrown when 2 or more players disconnects simultaneously and this method is called before updating online clients;

        }

    }
    public void sendMessage(String string) throws IOException {
        try {
            output.write(string + "\n");
            output.flush();
        } catch (IOException e) {
            client.close();

        }
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
            //disconnectFromServer();
        }, PING_TIMEOUT, TimeUnit.SECONDS);
    }


    private void stopPing(){
        ping.interrupt();
    }

    public InetAddress getClientAddress() {
        return clientAddress;
    }

    public void notify(Message message) {
    }


}