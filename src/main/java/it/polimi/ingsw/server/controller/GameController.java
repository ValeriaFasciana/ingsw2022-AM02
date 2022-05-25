package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.ServerMessageHandler;
import it.polimi.ingsw.server.model.BoardData;
import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.model.action.MoveMotherNatureAction;
import it.polimi.ingsw.server.model.action.PlayAssistantAction;
import it.polimi.ingsw.server.model.game.GameInterface;

public class GameController implements BoardUpdateListener {

    private GameInterface game;
    private ServerMessageHandler messageHandler;

    public GameController(GameInterface game, ServerMessageHandler messageHandler) {
        this.game = game;
        this.messageHandler = messageHandler;
    }

    public void playAssistant(String userName, PlayAssistantAction playAssistantAction){
        this.game.useAction(playAssistantAction);
    }

    public void moveMotherNature(String userName, MoveMotherNatureAction moveMotherNatureAction){}

    @Override
    public void onBoardUpdate(BoardData boardData) {
       // messageHandler.parseMessageFromServerToClient(new BoardUpdateResponse(ReservedRecipients.BROADCAST.toString(),boardData));
    }

}
