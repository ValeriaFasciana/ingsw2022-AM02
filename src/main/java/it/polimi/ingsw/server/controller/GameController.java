package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.network.ReservedRecipients;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.Type;
import it.polimi.ingsw.network.messages.servertoclient.events.*;
import it.polimi.ingsw.server.ServerMessageVisitor;
import it.polimi.ingsw.server.controller.action.*;
import it.polimi.ingsw.server.controller.listeners.EndGameListener;
import it.polimi.ingsw.server.controller.state.CharacterState;
import it.polimi.ingsw.server.controller.state.ChooseAssistantState;
import it.polimi.ingsw.server.controller.state.GameState;
import it.polimi.ingsw.network.data.BoardData;
import it.polimi.ingsw.server.controller.listeners.BoardUpdateListener;
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

    /**
     * Method to handle the creation of a game
     * @param playerNames list of the players
     * @param numberOfPlayers number of players
     * @param expertVariant if it's true the game mode is expert
     */
    public void createGame(List<String> playerNames, Integer numberOfPlayers, Boolean expertVariant){
        game = new Game(playerNames,numberOfPlayers,expertVariant);
        game.addBoardUpdateListener(this);
        game.addEndGameListener(this);
        game.create();
    }

    /**
     * Method to handle the choice of the assistant card
     * @param username current player
     * @param chosenAssistantIndex chosen assistant card id
     */
    public void setChosenAssistant(String username, int chosenAssistantIndex) {
        if(!validPlayer(username))
            return;
        this.game.useAction(new PlayAssistantAction(chosenAssistantIndex));
        setNextState();
    }

    /**
     * Method to handle moving a student to hall
     * @param username current player
     * @param studentColour chosen student to move
     */
    public void moveStudentToHall(String username, PawnColour studentColour) {
        if(!validPlayer(username))
            return;
        this.game.useAction(new MoveStudentToHallAction(studentColour));
        setNextState();
    }

    /**
     * Method to handle moving a student to isle
     * @param username current player
     * @param studentColour chosen student colour
     * @param isleIndex chosen isle id
     */
    public void moveStudentToIsle(String username,PawnColour studentColour, int isleIndex) {
        if(!validPlayer(username))
            return;
        this.game.useAction(new MoveStudentToIsleAction(studentColour,isleIndex));
        setNextState();
    }

    /**
     * Method to handle moving mother nature
     * @param username current player
     * @param isleIndex chosen isle id
     */
    public void moveMotherNature(String username, int isleIndex){
        if(!validPlayer(username))
            return;
        this.game.useAction(new MoveMotherNatureAction(isleIndex));
        setNextState();
    }

    /**
     * Method to handle choosing a cloud action
     * @param username current player
     * @param chosenCloudIndex chosen cloud id
     */
    public void setChosenCloud(String username, int chosenCloudIndex) {
        if(!validPlayer(username))
            return;
        this.game.useAction(new ChooseCloudAction(chosenCloudIndex));
        setNextState();
    }

    /**
     * It checks username is the current player
     * @param username current player
     * @return true if it's the current player
     */
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

    /**
     * Overridden method to respond to messages from client
     * @param message
     */
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

    /**
     * Method to handle initialization of the game
     * @param boardData board data
     * @param expertMode if true, game mode is expert
     */
    @Override
    public void onGameInit(BoardData boardData, boolean expertMode) {
        messageHandler.parseMessageFromServerToClient(new GameCreatedEvent(expertMode,boardData));
        this.state = new ChooseAssistantState(this);
        this.state.onInit();
    }

    /**
     * Method to handle the update of board
     * @param boardData board data
     */
    @Override
    public void onBoardUpdate(BoardData boardData) {
        messageHandler.parseMessageFromServerToClient(new BoardUpdateResponse(ReservedRecipients.BROADCAST.toString(), Type.SERVER_RESPONSE,boardData));
    }

    /**
     * Method to handle use of character effect
     * @param username current player
     * @param characterId chosen character card id
     */
    public void useCharacterEffect(String username, int characterId) {
        if(!validPlayer(username))
            return;
        game.useAction(new PlayCharacterAction(characterId));
        CharacterEffect effect = game.getCharacterEffect(characterId);
        if(effect != null){
            CharacterState charState = new CharacterState(this,characterId,effect,state);
            setState(charState);
        }else if(state.isOver()){
            setNextState();
        }else{
            state.onInit();
        }

    }

    /**
     * Method to handle end of game
     * @param winnerPlayer winner
     */
    @Override
    public void onEndGame(String winnerPlayer) {
        messageHandler.parseMessageFromServerToClient(new EndGameEvent(winnerPlayer, "has won",true));
        messageHandler.endLobby();
    }

    /**
     * Method to handle the choice of a colour
     * @param username current player
     * @param chosenColour chosen colour
     * @param toDiscard value of the colour to discard
     * @param toExclude if true, it exclude the colour from influence
     */
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

    /**
     * Method to handle the choice of an isle
     * @param username current player
     * @param chosenIsle chosen isle
     * @param calculateInfluence if true, its influence will be calculated
     * @param setBan if true, it sets a ban on the isle
     */
    public void handleIsleChoosing(String username, int chosenIsle, boolean calculateInfluence, boolean setBan) {
        if(!validPlayer(username))
            return;
        if(calculateInfluence)
            game.useAction(new CalculateInfluenceAction(chosenIsle));
        if(setBan)
            game.useAction(new SetBanOnIsleAction(chosenIsle));
        setNextState();
    }

    /**
     * Method to handle moving a student from a a card
     * @param username current player
     * @param characterId chosen character card id
     * @param destination destination of the students
     * @param movedStudents map of the students to move
     * @param isleIndex if destination is isle, move students to isle with this index
     */
    public void moveStudentsFromCard(String username, int characterId, MovementDestination destination, Map<PawnColour, Integer> movedStudents, int isleIndex) {
        if(!validPlayer(username))
            return;
        game.useAction(new MoveStudentsFromCardAction(characterId,destination,movedStudents, Optional.of(isleIndex)));
        setNextState();
    }

    /**
     * Method to handle student exchange
     * @param username current player
     * @param characterId chosen character card id
     * @param from where to take the students from
     * @param to where to put the students on
     * @param fromMap map to exchange the students from
     * @param toMap map to exchange the students to
     */
    public void handleStudentExchange(String username, int characterId, MovementDestination from, MovementDestination to, Map<PawnColour, Integer> fromMap, Map<PawnColour, Integer> toMap) {
        if(!validPlayer(username))
            return;
        game.useAction(new ExchangeStudentsAction(characterId,from,to, fromMap,toMap));
        setNextState();
    }

    /**
     * Method to deactivate a player
     * @param nickname selected player
     */
    public void deactivatePlayer(String nickname) {
        game.deactivatePlayer(nickname);
    }

    /**
     * Method to activate a player
     * @param nickname selected player
     */
    public void activatePlayer(String nickname) {
        game.activatePlayer(nickname);
    }

    /**
     * Method to handle disconnection of selected player
     * @param nickname selected player
     */
    public void manageDisconnection(String nickname) {
        state.onDisconnect(nickname);
    }

    /**
     * Method to handle rejoin
     * @param nickname selected player
     */
    public void handleRejoin(String nickname) {
        if(game.getCurrentPlayerName().equals(nickname)){
            state.onInit();
        }
    }
}
