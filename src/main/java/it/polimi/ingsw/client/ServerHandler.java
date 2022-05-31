package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.clienttoserver.PingMessageFromClient;
import it.polimi.ingsw.shared.jsonutils.JacksonMessageBuilder;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;
import it.polimi.ingsw.network.messages.MessageFromServerToClient;
import java.sql.Timestamp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;


public class ServerHandler implements Runnable
{

    private final Socket server;
    private BufferedReader input;
    private OutputStreamWriter output;
    private final Client owner;
    private final AtomicBoolean shouldStop = new AtomicBoolean(false);
    private final JacksonMessageBuilder jsonParser;
    private final ClientMessageVisitor messageHandler;
    private InetAddress clientAddress;
    public static final int PING_TIMEOUT = 5000000;
    public static final String PING = "ping";
    private ScheduledThreadPoolExecutor ex;
    private ScheduledFuture<?> pingTask;

    public ServerHandler( Client owner) throws ExecutionException, InterruptedException, TimeoutException {
        this.owner = owner;
        this.jsonParser = new JacksonMessageBuilder();
        this.messageHandler = new ClientMessageHandler();

        String clientIp = owner.getIp();
        int clientPort = owner.getPort();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Socket> socketFuture = executorService.submit(() -> new Socket(clientIp, clientPort));
        this.server = socketFuture.get(PING_TIMEOUT, TimeUnit.SECONDS);
        try {
            this.input = new BufferedReader(new InputStreamReader(server.getInputStream()));
            this.output = new OutputStreamWriter(server.getOutputStream());
            this.output.flush();
        } catch (IOException e) {
            System.out.println("could not open connection to " + server.getInetAddress());
            return;
        }
    }

    @Override
    public void run()
    {

        try {
            handleClientConnection();
        } catch (IOException e) {
            System.out.println("server " + server.getInetAddress() + " connection dropped");
        }

        try {
            server.close();
        } catch (IOException ignored) { }

        owner.terminate();
    }

    private void handleClientConnection() throws IOException {

        boolean stop = false;
        new Thread(this::ping).start();
        while (!stop) {
            /* read commands from the server and process them */
            try {
                String next =input.readLine();
                if (PING.equals(next)){
                    new Thread(this::ping).start();
                }

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


    public void ping() {
        if (ex != null) {
            ex.shutdownNow();
            pingTask.cancel(true);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.err.println("InterruptedException on ping sleep");
            Thread.currentThread().interrupt();
        }
        try {
            sendMessage(PING);
            Long datetime = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(datetime);
            System.out.println("Ping "+timestamp);

        } catch (IOException e) {
            /*
             * Mistakes were made
             */
        }
        if (!server.isClosed()) {
            ex = new ScheduledThreadPoolExecutor(5);
            ex.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
            ex.setRemoveOnCancelPolicy(true);
            pingTask = ex.schedule(() -> {
                closeConnection();
            }, PING_TIMEOUT, TimeUnit.SECONDS);
        }
    }

    public void closeConnection() {
        try {
            output.close();
            input.close();
            server.close();
            ex.shutdown();
            pingTask.cancel(true);
        } catch (Exception e) {
            //
        }
    }
}