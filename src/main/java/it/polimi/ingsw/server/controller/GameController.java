package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.clienttoserver.events.ExchangeStudentsResponse;
import it.polimi.ingsw.network.messages.servertoclient.events.*;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.server.controller.listeners.EndGameListener;
import it.polimi.ingsw.server.controller.state.CharacterState;
import it.polimi.ingsw.server.controller.state.ChooseAssistantState;
import it.polimi.ingsw.server.controller.state.GameState;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
import it.polimi.ingsw.server.model.action.*;
import it.polimi.ingsw.server.model.cards.characters.CharacterEffect;
import it.polimi.ingsw.server.model.game.Game;
import it.polimi.ingsw.server.model.game.GameInterface;
import it.polimi.ingsw.shared.enums.MovementDestination;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class GameController implements BoardUpdateListener,EndGameListener {

    private GameInterface game;
    private ServerMessageVisitor messageHandler;
    private GameState state;

    public GameController(ServerMessageVisitor messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void createGame(List<String> playerNames, Integer numberOfPlayers, Boolean expertVariant){
        game = new Game(playerNames,numberOfPlayers,expertVariant);
        game.addBoardUpdateListener(this);
        game.addEndGameListener(this);
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
    public void onGameInit(BoardData boardData, boolean expertMode) {
        messageHandler.parseMessageFromServerToClient(new GameCreatedEvent(expertMode,boardData));
        this.state = new ChooseAssistantState(this);
        this.state.onInit();
    }

    @Override
    public void onBoardUpdate(BoardData boardData) {
        messageHandler.parseMessageFromServerToClient(new BoardUpdateResponse(ReservedRecipients.BROADCAST.toString(), Type.SERVER_RESPONSE,boardData));
    }


    public void useCharacterEffect(String username, int characterId) {
        if(!validPlayer(username))
            return;
        game.useAction(new PlayCharacterAction(characterId));
        CharacterEffect effect = game.getCharacterEffect(characterId);
        if(effect != null){
            CharacterState charState = new CharacterState(this,characterId,effect,state);
            setState(charState);
        }else{
            setNextState();
        }

    }

    @Override
    public void onEndGame(String winnerPlayer) {
        messageHandler.parseMessageFromServerToClient(new EndGameEvent(ReservedRecipients.BROADCAST.toString(),winnerPlayer, "has won"));
    }

    public void handleColourChoosing(String username, PawnColour chosenColour, int toDiscard, boolean toExclude) {
        if(!validPlayer((username)))
            return;
        if(toExclude){
            game.excludeColourFromInfluence(chosenColour);
        }
        if(toDiscard >0){
            game.useAction(new DiscardStudentsAction(chosenColour,toDiscard));
        }
        setNextState();

    }

    public void handleIsleChoosing(String username, int chosenIsle, boolean calculateInfluence, boolean setBan) {
        if(!validPlayer(username))
            return;
        if(calculateInfluence)
            game.useAction(new CalculateInfluenceAction(chosenIsle));
        if(setBan)
            game.useAction(new SetBanOnIsleAction(chosenIsle));
        setNextState();
    }

    public void moveStudentsFromCard(String username, int characterId, MovementDestination destination, Map<PawnColour, Integer> movedStudents, int isleIndex) {
        if(!validPlayer(username))
            return;
        game.useAction(new MoveStudentsFromCardAction(characterId,destination,movedStudents, Optional.of(isleIndex)));
        setNextState();
    }


    public void handleStudentExchange(String username, int characterId, MovementDestination from, MovementDestination to, Map<PawnColour, Integer> fromMap, Map<PawnColour, Integer> toMap) {
        if(!validPlayer(username))
            return;
        game.useAction(new ExchangeStudentsAction(characterId,from,to, fromMap,toMap));
        setNextState();
    }
}
