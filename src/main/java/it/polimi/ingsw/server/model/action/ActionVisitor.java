package it.polimi.ingsw.server.model.action;

import it.polimi.ingsw.shared.enums.PawnColour;

public interface ActionVisitor {



    void playAssistantCard(int assistantId);

    void moveStudentToCurrentPlayerHall(PawnColour studentColour);

    void moveMotherNature(int isleIndex);

    void setBanOnIsland(int isleIndex);


    void emptyCloud(int cloudIndex);

    void playCharacterCard(int characterId);

    void moveStudentToIsle(PawnColour studentColour, int isleIndex);
}
