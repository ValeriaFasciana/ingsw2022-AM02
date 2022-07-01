package it.polimi.ingsw.server.controller.state;

import it.polimi.ingsw.network.messages.CharacterRequest;
import it.polimi.ingsw.server.controller.GameController;
    import it.polimi.ingsw.server.model.cards.characters.CharacterEffect;

public class CharacterState extends GameState{

    private CharacterEffect characterEffect;
    private int characterId;
    private GameState previousState;


    public CharacterState(GameController controller, int characterId, CharacterEffect effect, GameState previousState) {
        super(controller);
        this.characterEffect = effect;
        this.previousState = previousState;
        this.characterId = characterId;
    }

    /**
     *
     */
    @Override
    public void onInit() {
        CharacterRequest request = characterEffect.getRequest();
        request.setUserName(controller.getCurrentPlayerName());
        request.setCharacterId(characterId);
        controller.respond(request);
    }

    /**
     *
     */
    @Override
    public void setNext() {
        isOver = true;
        controller.setState(previousState);
    }
}
