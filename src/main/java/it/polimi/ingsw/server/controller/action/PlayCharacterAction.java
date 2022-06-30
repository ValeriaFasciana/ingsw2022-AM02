package it.polimi.ingsw.server.controller.action;

public class PlayCharacterAction implements Action {
    int characterId;

    public PlayCharacterAction(Integer characterId) {
        this.characterId = characterId;
    }

    @Override
    public void accept(ActionVisitor visitor) {
        visitor.activateCharacter(characterId);
    }
}
