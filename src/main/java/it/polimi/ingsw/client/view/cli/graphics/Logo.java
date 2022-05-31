package it.polimi.ingsw.client.view.cli.graphics;

public class Logo {
    public static void printLogo() {
        System.out.println("\033[H\033[2J");
        System.out.println("\033[H\033[3J");
        System.out.println(Colour.ANSI_BLUE.getCode() +
                "███████    ███████    ██      ██████      ██        ██   ██████████   ██      ██   ███████     \n" +
                "██         ██   ██    ██    ██      ██    ██ ██     ██       ██        ██    ██    ██          \n" +
                "████       ████       ██   ██ ██████ ██   ██   ██   ██       ██         ██  ██     ███████     \n" +
                "██         ██  ███    ██   ██        ██   ██     ██ ██       ██           ██            ██     \n" +
                "███████    ██    ███  ██   ██        ██   ██        ██       ██           ██       ███████     \n" +
                "\nWelcome to Eriantys boardgame");
    }
}
