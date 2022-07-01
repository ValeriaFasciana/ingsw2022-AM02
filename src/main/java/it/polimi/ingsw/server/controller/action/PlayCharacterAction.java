package it.polimi.ingsw.server.controller.action;

public class PlayCharacterAction implements Action {
    int characterId;

    /**
     * Action handled by the Game Controller
     * @param characterId chosen character card id
     */
    public PlayCharacterAction(Integer characterId) {
        this.characterId = characterId;
    }

    /**
     * Method to handle the actions of the visitor
     * @param visitor visitor of the overridden method
     */
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.activateCharacter(characterId);
    }
}
