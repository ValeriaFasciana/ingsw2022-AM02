package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Eriantys {

    private static final String APP_PARAMETERS_CLIENT = "client";
    private static final String APP_PARAMETERS_DEFAULT_PORT = "d";
    private static final String APP_PARAMETERS_CLIENT_CLI = "cli";

    public static void main (String[]args) {
            List<String> arguments = new ArrayList<>(Arrays.asList(args));
            System.out.print(arguments);

            Runnable application;
            if (arguments.contains(APP_PARAMETERS_CLIENT)) {
                boolean defaultPort = arguments.contains(APP_PARAMETERS_DEFAULT_PORT);
                boolean cli = arguments.contains(APP_PARAMETERS_CLIENT_CLI);
                application = new Client(defaultPort,cli);
            } else {
                application = new Server();
            }

            application.run();

    }
}

