package it.polimi.ingsw.server.model.action;

public class PlayCharacterAction implements Action {
    Integer characterId;
    @Override
    public void accept(ActionVisitor visitor) {
        visitor.playCharacterCard(characterId);
    }
}
