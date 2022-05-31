package it.polimi.ingsw.client.view.cli.SpecificCLI;

import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.graphics.Colour;
import it.polimi.ingsw.client.view.cli.utilities.InputParser;

import java.io.IOException;
import java.util.List;

public class LobbyCLI {
    String nickname;
    Integer numberOfPlayers;
    boolean gameMode;


    public void
    /**
     * Asks the nickname to the player
     * @param isRetry true if it is not the first time that the nickname is asked
     * @param alreadyTaken true if the previous nickname was not valid because equals to someone else's nickname
     */
    public void displayNicknameRequest(boolean isRetry, boolean alreadyTaken) {

        if (!isRetry)
            System.out.println("Insert your nickname");
        else if (!alreadyTaken)
            System.out.println("Your nickname was invalid, be sure to insert only valid characters (A-Z, a-z, 0-9)");
        else {
            System.out.println("Your nickname has already been taken, insert another one");
        }
        this.nickname = InputParser.getLine();
        if (this.nickname == null)
            System.out.println("Your nickname was invalid, be sure to insert only valid characters (A-Z, a-z, 0-9)");
    }

    /**
     * Method to display game mode request
     */
    public void displayGameModeRequest() {
        System.out.println(Colour.ANSI_BRIGHT_GREEN.getCode() + "\nConnection established!" + Colour.ANSI_RESET);
        System.out.println("\nInsert a game mode, simple or expert mode: s | e");
        this.gameMode = false;
        try {
            this.gameMode = getGameMode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //visitor.sendMessageToServer(new GameModeResponse(gameMode));
    }

    /**
     * Get the gamemode chosen by the client
     * @return a {boolean} (simple or expert player
     * @throws IOException if the chosen gamemode is null
     */
    private static boolean getGameMode() throws IOException {
        while(true) {
            String gameModeString = InputParser.getLine();
            if (gameModeString == null)
                throw new IOException();
            if (gameModeString.equals("s"))
                return false; //simple mode
            else if (gameModeString.equals("e"))
                return true; //expert mode
            else {
                System.out.println("Invalid game mode: s for simple mode or e for expert mode");
            }
        }
    }

    /**
     * Method to ask the number of player for the next game
     */
    public void displayNumberOfPlayersRequest(){
        System.out.println("Insert desired number of players: 2, 3 or 4");
        this.numberOfPlayers = InputParser.getInt("Invalid number of players: please insert an integer number between 2 and 4", CLI.conditionOnIntegerRange(2, 4));
    }

    public static void displayWaitingInTheLobbyMessage() {
        System.out.println("Waiting in the lobby...");
    }

    public static void displayPlayersReadyToStartMessage(List<String> nicknames) {

        System.out.println("All the players are ready to start, players in game are:");
        for (String nickname : nicknames)
            System.out.println("- " + nickname);
    }
}
