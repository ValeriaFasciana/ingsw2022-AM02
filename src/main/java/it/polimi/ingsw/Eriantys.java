package it.polimi.ingsw;

import org.apache.commons.cli.*;

public class Eriantys {

    public static final Option optServer = new Option("s", "server", false,
            "Starts server with port");
    public static final Option optCLI = new Option("c", "cli", false,
            "Starts the CLI and connects to server with port, ip and nickname\n" +
                    "without arguments it will let you choose them later"
    );
    public static final Option optGUI = new Option("g", "gui", false,
            "Starts the GUI and connects to server with port, ip and nickname\n" +
                    "without arguments it will let you choose them later");

    public static void main (String[]args) {

    }
}

