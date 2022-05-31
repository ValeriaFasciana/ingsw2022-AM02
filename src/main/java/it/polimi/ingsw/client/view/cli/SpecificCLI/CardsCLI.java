package it.polimi.ingsw.client.view.cli.SpecificCLI;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.view.cli.CLI;
import it.polimi.ingsw.client.view.cli.graphics.Screen;
import it.polimi.ingsw.client.view.cli.utilities.InputParser;

import java.util.List;

/**
 * Class to manage all the {@link CLI} methods related to {@link it.polimi.ingsw.server.model.cards}
 */
public class CardsCLI {

    /**
     * Method to display a graphical request to the user when activating/discarding
     * {@link it.polimi.ingsw.server.model.cards.AssistantCard}
     * @param client the {@link Client} to who the message is directed
     * @param cardsIDs the IDs of the {@link it.polimi.ingsw.server.model.cards.AssistantCard} to be display
     */
    public static void displaySelectCardRequest(Client client, List<Integer> cardsIDs) {
        Screen.getInstance().displayCardSelection(cardsIDs);

        System.out.print("Insert the ID of the card you want to select: ");
        Integer selection = InputParser.getInt("Error: the ID provided is not available. Provide a valid ID", CLI.conditionOnInteger(cardsIDs));
        if (selection == null)
            return;
        //client.getServerHandler().sendCommandMessage(Message message) // new SelectCardResponse(selection));
    }
}
