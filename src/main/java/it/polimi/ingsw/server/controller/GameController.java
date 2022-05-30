package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.BoardUpdateResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.ChooseAssistantRequest;
import it.polimi.ingsw.network.messages.servertoclient.events.NotYourTurnResponse;
import it.polimi.ingsw.server.ServerMessageHandler;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.model.TowerColour;
import it.polimi.ingsw.server.model.action.MoveMotherNatureAction;
import it.polimi.ingsw.server.model.action.PlayAssistantAction;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.GameInterface;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class GameController implements BoardUpdateListener {

    private GameInterface game;
    private ServerMessageVisitor messageHandler;
    //private GameStateHandler stateHandler;

    public GameController() {
        this.messageHandler = new ServerMessageHandler();
    }

    public void startGame(Map<String, TowerColour> players,Integer numberOfPlayers, Boolean expertVariant) throws IOException {
        game = new Game(players,numberOfPlayers,expertVariant);
        game.addBoardUpdateListener(this);
        String firstPlayer = game.getCurrentPlayerName();
        messageHandler.parseMessageFromServerToClient(new ChooseAssistantRequest(firstPlayer,Type.SERVER_REQUEST,game.getPlayableAssistants()));
    }



    public void moveMotherNature(String userName, MoveMotherNatureAction moveMotherNatureAction){}

    @Override
    public void onBoardUpdate(BoardData boardData) {
       messageHandler.parseMessageFromServerToClient(new BoardUpdateResponse(ReservedRecipients.BROADCAST.toString(), Type.SERVER_RESPONSE,boardData));
    }

    public void setChosenAssistant(String username, int chosenAssistantIndex) {
        if(!validPlayer(username))return;
        this.game.useAction(new PlayAssistantAction(chosenAssistantIndex));
        this.game.endCurrentPlayerTurn();

    }

    private boolean validPlayer(String username) {
        boolean valid = Objects.equals(game.getCurrentPlayerName(), username);
        if(!valid)messageHandler.parseMessageFromServerToClient(new NotYourTurnResponse(username,Type.NOT_YOUR_TURN));
        return valid;
    }

}
