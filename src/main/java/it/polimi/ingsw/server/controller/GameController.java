package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.BoardUpdateResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.GameCreatedEvent;
import it.polimi.ingsw.network.messages.servertoclient.events.NotYourTurnResponse;
import it.polimi.ingsw.server.ServerMessageHandler;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.server.controller.state.ChooseAssistantState;
import it.polimi.ingsw.server.controller.state.GameState;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.model.TowerColour;
import it.polimi.ingsw.server.model.action.*;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.GameInterface;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.Map;
import java.util.Objects;

public class GameController implements BoardUpdateListener {

    private GameInterface game;
    private ServerMessageVisitor messageHandler;
    private GameState state;

    public GameController(ServerMessageVisitor messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void createGame(Map<String, TowerColour> players,Integer numberOfPlayers, Boolean expertVariant){
        game = new Game(players,numberOfPlayers,expertVariant);
        game.addBoardUpdateListener(this);
        game.create();
    }

    public void setChosenAssistant(String username, int chosenAssistantIndex) {
        if(!validPlayer(username))
            return;
        this.game.useAction(new PlayAssistantAction(chosenAssistantIndex));
        setNextState();
    }

    public void moveStudentToHall(String username, PawnColour studentColour) {
        if(!validPlayer(username))
            return;
        this.game.useAction(new MoveStudentToHallAction(studentColour));
        setNextState();
    }

    public void moveStudentToIsle(String username,PawnColour studentColour, int isleIndex) {
        if(!validPlayer(username))
            return;
        this.game.useAction(new MoveStudentToIsleAction(studentColour,isleIndex));
        setNextState();
    }

    public void moveMotherNature(String username, int isleIndex){
        if(!validPlayer(username))
            return;
        this.game.useAction(new MoveMotherNatureAction(isleIndex));
        setNextState();
    }

    public void setChosenCloud(String username, int chosenCloudIndex) {
        if(!validPlayer(username))
            return;
        this.game.useAction(new ChooseCloudAction(chosenCloudIndex));
        setNextState();
    }

    private boolean validPlayer(String username) {
        boolean valid = Objects.equals(game.getCurrentPlayerName(), username);
        if(!valid){
            messageHandler.parseMessageFromServerToClient(new NotYourTurnResponse(username,Type.NOT_YOUR_TURN));
        }
        return valid;
    }

    public GameInterface getGame() {
        return game;
    }

    public String getCurrentPlayerName() {
        return game.getCurrentPlayerName();
    }

    public void respond(Message message) {
        messageHandler.parseMessageFromServerToClient(message);
    }

    private void setNextState() {
        state.setNext();
    }

    public void setState(GameState state) {
        this.state = state;
        state.onInit();
    }

    @Override
    public void onGameInit(BoardData boardData) {
        messageHandler.parseMessageFromServerToClient(new GameCreatedEvent(ReservedRecipients.BROADCAST.toString(), Type.SERVER_RESPONSE,boardData));
        this.state = new ChooseAssistantState(this);
        this.state.onInit();
    }

    @Override
    public void onBoardUpdate(BoardData boardData) {
        messageHandler.parseMessageFromServerToClient(new BoardUpdateResponse(ReservedRecipients.BROADCAST.toString(), Type.SERVER_RESPONSE,boardData));
    }


}
