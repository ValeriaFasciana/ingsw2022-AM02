package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.servertoclient.matchData.LoadAssistantCardGrid;
import it.polimi.ingsw.network.messages.servertoclient.matchData.LoadAssistantCardSlots;
import it.polimi.ingsw.network.messages.servertoclient.matchData.MatchDataMessage;
import it.polimi.ingsw.network.messages.servertoclient.matchData.TurnMessage;
import it.polimi.ingsw.shared.LightAssistantCard;
import it.polimi.ingsw.shared.enums.GameMode;
import it.polimi.ingsw.shared.enums.PawnColour;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a light version of the Model. It contains all the information that are in common
 * among all the players
 */

public class MatchData {
    private List<LightAssistantCard> lightAssistantCards;
    private String currentViewNickname;
    private String turnOwnerNickname;
    private View view;
    private LightClient thisClient;
    private List<LightClient> otherClients;
    private List<Integer> assistantCardGrid;
    private boolean isReloading;
    private GameMode gameMode;
    private PawnColour[][] studentsTray;
    private PawnColour slideStudent;

    public boolean isReloading() {
        return isReloading;
    }

    private static MatchData instance;

    public static MatchData getInstance(){
        if (instance == null){
            instance = new MatchData();
        }
        return instance;
    }

    private MatchData(){
        this.isReloading = false;
        this.lightAssistantCards = new ArrayList<>();
        this.thisClient = new LightClient();
        this.otherClients = new ArrayList<>();
    }

    //Method to reset other clients when a new list of nicknames is received (the {@link MatchData} will be reloaded}

    public void resetOtherClients(){
        this.otherClients = new ArrayList<>();
    }

    /**
     * Set the nickname of the LightClient representing the player himself
     * @param nickname the nickname chosen by the player
     */
    public void setThisClient(String nickname){
        thisClient.setNickname(nickname);
        currentViewNickname = nickname;
    }

    /**
     * Set the {@link View} of the ongoing match
     * @param view the {@link it.polimi.ingsw.client.view.cli.CLI} or the {@link it.polimi.ingsw.client.view.gui} used
     */
    public void setView(View view){
        this.view = view;
    }

    /**
     * Set the nickname of other players of the game, if any
     * @param nickname the nickname of the player
     */
    public void addLightClient(String nickname){
        LightClient lc = new LightClient();
        lc.setNickname(nickname);
        otherClients.add(lc);

    }

    /**
     * Return the {@link LightClient} object corresponding to a nickname of the players in game
     * @param nickname String containing the nickname of the player
     * @return {@link LightClient}
     */
    public LightClient getLightClientByNickname(String nickname) {
        for(LightClient lc : otherClients){
            if(lc.getNickname().equals(nickname))
                return lc;
        }
        return thisClient;
    }

    /**
     * Setter to set the boolean isReloading to "inform" the view to not display any scene when isReloading is True
     * @param reloading True if the reloading of all the information of the match is going on, false if it's not
     */
    public void setReloading(boolean reloading) {
        isReloading = reloading;
        display();
    }

    /**
     * Return the student Tray
     * @return a bi-dimensional array of {@link PawnColour} representing the Market Tray
     */
    public PawnColour[][] getStudentsTray() {
        return studentsTray;
    }

    /**
     * @return the slide {@link PawnColour}
     */
    public PawnColour getSlideStudent() {
        return slideStudent;
    }

    /**
     * Set all the {@link LightAssistantCard} present in the game
     * @param lightAssistantCards the list of light version of
     * {@link it.polimi.ingsw.server.model.cards.AssistantCard}
     */
    public void setAssistantCards(List<LightAssistantCard> lightAssistantCards) {
        this.lightAssistantCards = lightAssistantCards;
    }

    /**
     * Load the IDs of the {@link LightAssistantCard} to show in the assistantCardGrid
     * @param assistantCardGrid the List of IDs of the Cards
     */
    public void loadDevelopmentCardGrid(List<Integer> assistantCardGrid){
        this.assistantCardGrid = assistantCardGrid;
    }

    /**
     * Return a {@link LightAssistantCard} given an ID
     * @param ID the ID that identifies the Card
     * @return the {@link LightAssistantCard} corresponding of a given ID, null if the ID is not correct
     */
    public LightAssistantCard getAssistantCardByID(Integer ID){
        for (LightAssistantCard ldc : lightAssistantCards){
            if(ldc.getId() == ID){
                return ldc;
            }
        }
        return null;
    }

    public List<Integer> getAssistantCardGrid() {
        return assistantCardGrid;
    }

    public String getThisClientNickname(){
        return thisClient.getNickname();
    }

    public String getCurrentViewNickname(){
        return currentViewNickname;
    }

    public void setCurrentViewNickname(String nickname){
        this.currentViewNickname = nickname;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }


    /**
     * Return the nicknames of all the players
     * @return a List containing the nicknames
     */
    public List<String> getAllNicknames(){
        List<String> nicknames = new ArrayList<>();
        nicknames.add(thisClient.getNickname());
        for(LightClient lc : otherClients){
            nicknames.add(lc.getNickname());
        }
        return nicknames;
    }

    /**
     * Update the informations of a specific {@link LightClient} or of {@link MatchData}
     * @param message the message containing the informations to update and the nickname of the target
     * {@link LightClient}
     */
    public void update(MatchDataMessage message){
        if (message instanceof LoadAssistantCardGrid){
            this.assistantCardGrid = ((LoadAssistantCardGrid) message).getAvailableCardsIds();
            display();
        }
        if (message instanceof LoadAssistantCardSlots){
            getLightClientByNickname(message.getNickname()).setAssistantCardSlots(((LoadAssistantCardSlots) message).getSlots());
            display();
        }

        if (message instanceof TurnMessage){
            if (((TurnMessage) message).isStarted()) {
                this.turnOwnerNickname = message.getNickname();
                if(this.turnOwnerNickname.equals(getThisClientNickname()) && !currentViewNickname.equals(getThisClientNickname())){
                    setCurrentViewNickname(getThisClientNickname());
                    display();
                }
            }
        }
    }

    /**
     * Add to thisClient the {@link it.polimi.ingsw.server.model.cards.AssistantCard}
     * @param ID
     * @param active
     */
    public void addChosenAssistantCard(Integer ID, boolean active){
        thisClient.addAssistantCard(ID, active);
    }

    /**
     * Method to update the view when a {@link MatchDataMessage} is received and the message contains update information
     * about the elements that are in common with all the players
     */
    private void display(){
        if(!isReloading){
            view.displayStandardView();
        }
    }


}
