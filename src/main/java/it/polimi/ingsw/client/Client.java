package it.polimi.ingsw.client;

import it.polimi.ingsw.client.utilities.InputParser;
import it.polimi.ingsw.client.view.ViewInterface;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.gui.GUIApp;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Client implements Runnable {
    private ServerHandler serverHandler;
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

        while(ip.equals("")) {
            System.out.println("Be sure to type something");
            ip = InputParser.getLine();
        }

        if (ip.equals("d")) {
            ip = DEFAULT_ADDRESS;
            port = DEFAULT_PORT;
        }
        else {
            System.out.println("Enter the port you want to connect to: (enter an integer between 1024 and 65535)");
            port = InputParser.getLine();
            while (port.equals("")) {
                System.out.println("Be sure to type something");
                port = InputParser.getLine();
            }
        }
        System.out.printf("IPAddress: %s %nPort: %s%n", ip, port);

        System.out.println("Choose your view mode: CLI or GUI" );
        String inputcli = InputParser.getLine();
        while(!(inputcli.equals("CLI")&&!(inputcli.equals("GUI")))) {
            System.out.println("Be sure to type CLI or GUI");
            inputcli = InputParser.getLine();
        }
        if(inputcli.equals("CLI")) {
            isCli=true;

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

        try {
            ViewInterface view;
            if(isCli) {
                view = new CLI();
                ((CLI) view).initCLI();
            }
            else view = new GUIApp();
            ClientMessageVisitor messageVisitor = new ClientMessageHandler(view);
            serverHandler = new ServerHandler(this,messageVisitor);

            view.setServerHandler(serverHandler); // è la network handler, non controlla il server

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
