package it.polimi.ingsw.client.view.cli.graphics;

import it.polimi.ingsw.shared.LightCards;

public class GraphicalAssistantCards extends GraphicalCards {

    public GraphicalAssistantCards(LightCards ldc, String nickname) {
        super(ldc, nickname);
    }

    @Override
    public void drawCard() {
        reset();
        drawEdges(this.height, this.width);
        drawID();
        drawMovement();
    }
}
