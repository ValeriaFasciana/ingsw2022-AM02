package it.polimi.ingsw.client.view.cli.graphics;

public class Waiting {
    public static void printWaiting() {
        System.out.println(Colour.ANSI_BLUE.getCode() +
                "██            ██    ████      ██   ██████████   ██   ██      ██   ██████████   \n" +
                " ██          ██    ██  ██     ██       ██       ██   ██ ██   ██   ██           \n" +
                "  ██   ██   ██    ████████    ██       ██       ██   ██  ██  ██   ██   █████   \n" +
                "   ██ ████ ██    ██      ██   ██       ██       ██   ██   ██ ██   ██      ██   \n" +
                "    ██    ██    ██        ██  ██       ██       ██   ██    ████   ██████████   \n" +
                "\nWaiting for other players..." +
                "\nPress Enter to start");
    }
}
