package it.polimi.ingsw.client;

import it.polimi.ingsw.client.utilities.InputParser;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUIApp;
import it.polimi.ingsw.network.messages.MessageFromClientToServer;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Client implements Runnable  {
    private NetworkHandler networkHandler;
    private String ip;
    private String port;
    public final int SOCKET_TIMEOUT_S = 20000;
    private String nickname;
    private boolean isCli;
    private static final String DEFAULT_ADDRESS = "127.0.0.1";
    private static final String DEFAULT_PORT = "7831";

    public static void main(String[] args){
        Client client = new Client();
        client.askStartParameters();
        client.run();
    }

    public void askStartParameters() {

        System.out.println("Enter the server's IP address or d (default configuration): ");
        ip = InputParser.getLine();
        //ip="d";

        while(ip.equals("")) {
            System.out.println("Be sure to type something");
            ip = InputParser.getLine();
        }

        if (ip.equals("d")) {
            ip = DEFAULT_ADDRESS;
            port = DEFAULT_PORT;
            System.out.printf("IPAddress: %s \nPort: %s\n", ip, port);

        }
        else {
            System.out.println("Enter the port you want to connect to: (enter an integer between 1024 and 65535)");
            port = InputParser.getLine();
            while (port.equals("")) {
                System.out.println("Be sure to type something");
                port = InputParser.getLine();
            }
            System.out.printf("IPAddress: %s %nPort: %s%n", ip, port);
        }
        System.out.println("Choose your view mode: CLI or GUI" );
        String inputcli = InputParser.getLine();
        while(!(inputcli.equals("CLI"))&&!(inputcli.equals("GUI"))) {
            System.out.println("Be sure to type CLI or GUI");
            inputcli = InputParser.getLine();
        }
        if(inputcli.equals("CLI")) {
            isCli=true;
        }
        else {
            isCli=false;
        }
        }


    public String getNickname() {
        return nickname;
    }

    public String getPort() {
        return port;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    @Override
    public void run() {

        ClientMessageVisitor messageVisitor;
        try {
            ViewInterface view;
            if(isCli) {
                view = new CLI(this);
                ((CLI) view).initCLI();
                messageVisitor = new ClientMessageHandler(view);
            }
            else {
                GUIApp guiView = GUIApp.getInstance();
                guiView.setClient(this);
                messageVisitor = new ClientMessageHandler(guiView);

            }
            networkHandler = new NetworkHandler(this,messageVisitor);
            Thread serverHandlerThread = new Thread(networkHandler);
            serverHandlerThread.start();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * The handler object responsible for communicating with the server.
     *
     * @return The server handler.
     */
    public NetworkHandler getServerHandler() {
        return networkHandler;
    }


    /**
     * Terminates the application as soon as possible.
     */
    public synchronized void terminate() {
        if (Objects.nonNull(networkHandler))
            networkHandler.stop();
    }

    public String getIp() {
        return ip;
    }

    public void sendCommandMessage(MessageFromClientToServer toReturnMessage) {
        networkHandler.sendCommandMessage(toReturnMessage);
    }
}
