package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.messages.clienttoserver.events.GameModeRequest;
import it.polimi.ingsw.network.messages.clienttoserver.events.JoinLobbyRequest;
import it.polimi.ingsw.network.messages.clienttoserver.events.LoginRequest;

public class ServerMessageHandler implements ServerMessageVisitor {

    GameLobby lobby;
    GameController controller;


//
//    /**
//     * Default constructor
//     * <p>
//     * Every {@link Lobby} is linked to a parser, which manages the incoming messages from the lobby's users;
//     * The serverController has to be set separately, since it can be set up in different moments
//     *
//     * @param lobby the lobby associated to this parser
//     */
//    public ServerMessageHandler(GameLobby lobby) {
//            this.lobby = lobby;
//    }
//
//    /**
//     * Sets the parser's game controller
//     *
//     * @param gameController the game controller associated to this parser
//     */
//    public void setGameController(GameController gameController) {
//            this.controller = gameController;
//    }
//    //Client -> sends message request -> virtualClient -> lobby -> parseMessageFromClientToServer(MessageRequest) -> message parser will call methods of GameController and Lobby
//    //Controller will call methods of model -> the model will return responses to the Controller -> Controller will pass the message to the Parser with parseMessageFromServerToClient
//    //Parser will pass messages to the Lobby -> Lobby will pass messages to the virtualClient -> Client
//
    /**
     * Sends a message
     * <p>
     * Since the parser has no reference to the network interface, the message has to be sent to the lobby, which will take
     * care of sending the message, using the {@linkplain Lobby#sendMessage(String, Message)} method.
     *
     * @param message the message to send
     */
    public void parseMessageFromServerToClient(Message message) {
            lobby.sendMessage(message.getUsername(), message);
    }

    @Override
    public void login(LoginRequest message) {
        ServerMessageVisitor.super.login(message);
    }

    @Override
    public void joinLobby(JoinLobbyRequest message) {
        ServerMessageVisitor.super.joinLobby(message);
    }

    @Override
    public void gameMode(GameModeRequest message) {
        ServerMessageVisitor.super.gameMode(message);
    }

    @Override
    public void cannotHandleMessage(Message message) {

    }

//    /**
//     * Handles a {@linkplain ChooseToReloadMatchResponse} message
//     *
//     * @param message the message to handle
//     * @see Lobby#reloadMatch(boolean)
//     */
//    @Override
//    public void onMatchReloadResponse(ChooseToReloadMatchResponse message) {
//        lobby.reloadMatch(message.wantToReload());
//    }
//
//    /**
//     * Handles a {@linkplain ChooseAssistantRequest} message
//     *
//     * @param message the message to handle
//     * @see Lobby#chooseGods(List)
//     */
//    public void playAssistantCard(PlayAssistantRequest message) {
//       // controller.playAssistant(message.getUserName(),message.getPayload());
//    }
//
//    /**
//     * Handles a {@linkplain ChooseInitialGodsResponse} message
//     *
//     * @param message the message to handle
//     * @see Lobby#chooseGods(List)
//     */
//    @Override
//    public void moveStudent(StudentMovementRequest message) {
//        controller.moveStudent(message.getUsername(), message.getPayload());
//    }
//
//    /**
//     * Handles a {@linkplain ChooseYourGodResponse} message
//     *
//     * @param message the message to handle
//     * @see Lobby#assignGod(String, GodData)
//     */
//    @Override
//    public void moveMotherNature(MotherNatureMovementRequest message) {
//       // controller.moveMotherNature(message.getUsername(), message.getGod());
//    }
//
//    /**
//     * Handles a {@linkplain ChooseStartingPlayerResponse} message
//     *
//     * @param message the message to handle
//     * @see Lobby#selectStartingPlayer(String)
//     */
//    @Override
//    public void chooseCloud(ChooseCloudRequest message) {
//        controller.chooseCloud(message.getUsername(),message.getPayload());
//    }
//
//    /**
//     * Handles a {@linkplain ChooseStartingPlayerResponse} message
//     *
//     * @param message the message to handle
//     * @see Lobby#selectStartingPlayer(String)
//     */
//    @Override
//    public void chooseIsleForInfluenceCalculation(ChooseIsleRequest message) {
//        controller.chooseIsleForInfluenceCalculation(message.getUsername(),message.getPayload());
//    }
//
//    /**
//     * Handles a {@linkplain SelectWorkerRequest} message
//     *
//     * @param message the message to handle
//     * @see ServerController#selectWorker(String, Worker)
//     */
//    @Override
//    public void setBanOnIsle(SetBanRequest message) {
//        controller.setBanOnIsle(message.getUsername(), message.getTargetWorker());
//    }
//
//    /**
//     * Handles a {@linkplain SelectWorkerRequest} message
//     *
//     * @param message the message to handle
//     * @see ServerController#selectWorker(String, Worker)
//     */
//    @Override
//    public void setExcludedColour(SetExcludedColourRequest message) {
//        controller.setExcludedColour(message.getUsername(), message.getTargetWorker());
//    }
//
//    /**
//     * Handles a {@linkplain SelectWorkerRequest} message
//     *
//     * @param message the message to handle
//     * @see ServerController#selectWorker(String, Worker)
//     */
//    @Override
//    public void setColourToDiscard(setExcludedColourRequest message) {
//        controller.setColourToDiscard(message.getUsername(), message.getTargetWorker());
//    }
//
//
//    /**
//     * Handles a {@linkplain WalkableCellsRequest} message
//     *
//     * @param message the message to handle
//     * @see ServerController#obtainWalkableCells(String)
//     */
//    @Override
//    public void walkableCells(WalkableCellsRequest message) {
//        serverController.obtainWalkableCells(message.getUsername());
//    }
//
//    /**
//     * Handles a {@linkplain BuildableCellsRequest} message
//     *
//     * @param message the message to handle
//     * @see ServerController#obtainBuildableCells(String)
//     */
//    @Override
//    public void buildableCells(BuildableCellsRequest message) {
//        controller.obtainBuildableCells(message.getUsername());
//    }
//
//    /**
//     * Handles a {@linkplain SelectBuildingCellRequest} message
//     *
//     * @param message the message to handle
//     * @see ServerController#selectBuildingCell(String, Cell)
//     */
//    @Override
//    public void selectCellToBuild(SelectBuildingCellRequest message) {
//            serverController.selectBuildingCell(message.getUsername(), message.getSelectedCell());
//            }
//
//    /**
//     * Handles a {@linkplain PlayerMoveRequest} message
//     *
//     * @param message the message to handle
//     * @see ServerController#handleMoveAction(String, MoveAction)
//     */
//    @Override
//    public void managePlayerMove(PlayerMoveRequest message) {
//        MoveAction moveAction = new MoveAction(message.getTargetWorker(), message.getTargetCell());
//        serverController.handleMoveAction(message.getUsername(), moveAction);
//    }
//
//    /**
//     * Handles a {@linkplain PlayerBuildRequest} message
//     *
//     * @param message the message to handle
//     * @see ServerController#handleBuildAction(String, BuildAction)
//     */
//    @Override
//    public void managePlayerBuild(PlayerBuildRequest message) {
//            BuildAction buildAction = new BuildAction(message.getTargetWorker(), message.getTargetCell(), message.getTargetBlock());
//            serverController.handleBuildAction(message.getUsername(), buildAction);
//            }
//
//    /**
//     * Handles a {@linkplain AddWorkerRequest} message
//     *
//     * @param message the message to handle
//     * @see ServerController#addWorker(String, Cell)
//     */
//    @Override
//    public void addWorkerOnBoard(AddWorkerRequest message) {
//            serverController.addWorker(message.getUsername(), message.getTargetCell());
//        }
}
