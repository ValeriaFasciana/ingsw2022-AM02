package it.polimi.ingsw.client.utilities;

import it.polimi.ingsw.client.view.cli.CLI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Predicate;


//Class to manage the input given by the user when using the CLI
public class InputParser {
    private static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        //Method to read a line, return the line read from the InputParser

        public static String getLine(){
            String line;

           try {
                    //Reset buffer
                    while(InputParser.input.ready())
                        InputParser.input.readLine(); //Flush
                    //Wait for user
                    while (!InputParser.input.ready()) {
                        Thread.sleep(100);
                    }
                    line = InputParser.input.readLine();
                } catch (InterruptedException | IOException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            return line;
        }

    /**
     * Get a line from the terminal with a condition to be respected
     * @param condition the conditions to respect when inserting a line in the terminal
     * @return the string read from the InputParser
     */

    public static String getString(Predicate<String> condition){
        String line;
        do{
            line = getLine();
            if (condition.test(line))
                return line;
            else
                System.out.println("error");
        } while (true);
    }

    /**
     * Method to read an int from the terminal
     * @return the Integer selected
     */
    public static Integer getInt(){
        String numString;
        Integer num = null;
        boolean done = false;
        try {
            do {
                //Reset buffer
                while(input.ready())
                    input.readLine(); //Flush
                //Wait for user
                while (!input.ready()) {
                    Thread.sleep(200);
                }
                numString = input.readLine();
                try {
                    num = Integer.parseInt(numString);
                    done=true;
                } catch (NumberFormatException e) {
                    System.out.println("Error: please insert a number");
                }
            }while(!done);

        }catch (InterruptedException | IOException e){
            Thread.currentThread().interrupt();
            return null;
        }
        return num;
    }

    /**
     * Method to get an int by a given condition
     * @param errorMessage the message to display if the condition is not respected
     * @param condition the condition to be respected when inserting an integer
     * @return the integer read form the InputParser
     */
    public static Integer getInt(String errorMessage, Predicate<Integer> condition){
        String numString;
        Integer num = null;
        boolean done = false;

        try {
            //Reset buffer
            while(input.ready())
                input.readLine(); //Flush
            //Wait for user
            while (!input.ready()) {
                Thread.sleep(100);
            }

            do {
                numString = input.readLine();
                try {
                    num = Integer.parseInt(numString);
                    if (condition.test(num)) {
                        done = true;
                    }else{
                        System.out.println(errorMessage);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error: please insert a number");
                }
            }while(!done);

        }catch (InterruptedException | IOException e){
            Thread.currentThread().interrupt();
            return null;
        }
        return num;
    }


    /**
     * Method to let the player choose a command of the list using an integer
     * @param commands the list of the available commands
     * @return the string corresponding to the command chosen
     * @throws IOException when the connection with the server is closed
     */
    public static String getCommandFromList(List<String> commands) throws IOException {
        Integer selection = getInt("Please insert a valid command", CLI.conditionOnIntegerRange(1, commands.size()));
        if (selection == null){
            throw new IOException();
        }
        return commands.get(selection-1);
    }


    /**
     * Method to get a command that can be either a number of the list or a text command
     * @param textCommands list of available text commands
     * @param intCommands list of the available commands of the list
     * @return the string corresponding to the command chosen
     * @throws IOException when the connection with the server is closed
     */
    public static String getCommandFromList(List<String> textCommands, List<String> intCommands) throws IOException {
        return getCommand(CLI.conditionOnIntegerRange(1, intCommands.size()), textCommands, intCommands,
                "Please insert a valid command");
    }


    /**
    * Method to get a command from client
    * @param integerPredicate the predicate that checks the range of integer commands
    * @param textCommands list of available text commands
    * @param intCommands list of the available commands of the list
    * @param errorMessage message to be printed in case the client insert a wrong command
    * @return the string corresponding to the command chosen
    */
    public static String getCommand(Predicate integerPredicate, List<String> textCommands, List<String> intCommands, String errorMessage) throws IOException {
        if (textCommands.isEmpty())
            return getCommandFromList(intCommands);
        String command;

        try {
            //Reset buffer
            while (InputParser.input.ready())
                InputParser.input.readLine(); //Flush
            //Wait for user
            while (!InputParser.input.ready()) {
                Thread.sleep(100);

            }
        }catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            return null;
        }

        do {
            try{
                command = InputParser.input.readLine();
                if (textCommands.contains(command))
                    return command;
                try {
                    if (integerPredicate.test(Integer.parseInt(command)))
                        return intCommands.get(Integer.parseInt(command) - 1);
                    else
                        System.out.println(errorMessage);
                } catch (NumberFormatException e) {
                    System.out.println(errorMessage);
                }
            } catch (IOException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        } while (true);
    }
}
