package it.polimi.ingsw.client.view.cli.graphics;

import it.polimi.ingsw.client.MatchData;
import it.polimi.ingsw.shared.LightAssistantCard;
import it.polimi.ingsw.shared.enums.Resource;

import java.util.*;

/**
 * Class to collect all the graphical element together
 */
public class Screen extends GraphicalElement{
    private final int assistCardGridXAnchor = 0;
    private final int assistCardGridYAnchor = 0;

    private final int assistCardSlotsXAnchor = 16;
    private final int assistCardSlotsYAnchor = 62;

    private final int ownedAssistantCardXAnchor = 16;
    private final int ownedAssistantCardYAnchor = 122;

    public Screen(int width, int height) {
        super(width, height);
    }

    GraphicalCardGrid graphicalAssistantCardGrid;
    List<Integer> assistantCardGridCardsToDisplay;
    private String nickname;
    private static Screen instance;
    private int longestNickname;

    public static Screen getInstance(){
        if(instance == null)
            instance = new Screen();
        return instance;
    }
    private Screen() {
        super(201, 26);
        graphicalAssistantCardGrid = new GraphicalCardGrid();
        assistantCardGridCardsToDisplay = new ArrayList<>();
        reset();
    }

    public void setClientToDisplay(String nickname){
        this.nickname = nickname;
    }

    /**
     * Display the view of a player with all the graphical elements
     */
    public void displayStandardView(){
        nickname = MatchData.getInstance().getCurrentViewNickname();
        System.out.println("\033[H\033[2J");
        System.out.println("\033[H\033[3J");
        System.out.flush();
        reset();
        drawAllElements();
        display();
    }

    /**
     * Draw all the graphical elements
     */
    private void drawAllElements() {
        drawAssistantCardGrid();
        if(MatchData.getInstance().getLightClientByNickname(this.nickname).getOwnedAssistantCards().size()<3)
            drawAssistantCards();
        drawAssistantCardSlots();
        drawSideInformations();

    }

    /**
     * Method to draws the infos on the right side of the screen
     */
    private void drawSideInformations() {
        int currentViewNicknameYAnchor = longestNickname + 11;
        String s1 = "You are viewing the personal";
        String s2 = "board of " + MatchData.getInstance().getCurrentViewNickname();
        String s3 = "Type <-pb + nickname> to see";
        String s4 = "other players' personal board";
        List<String> text= new ArrayList<>(Arrays.asList(s1, s2, s3, s4));

        int i = 1;
        for (String s : text){
            for(int j = 0; j < s.length(); j++){
                symbols[i][j + currentViewNicknameYAnchor] = s.charAt(j);
            }
            i++;
        }
        drawResourceLegend(currentViewNicknameYAnchor, i + 1);
    }
    private void drawResourceLegend(int currentViewNicknameYAnchor, int i) {
        List<Resource> resources = Resource.realValues();
        String s1 = "The resources present in the";
        String s2 = "game are:";
        List<String> text= new ArrayList<>(Arrays.asList(s1, s2));
        for (String s : text){
            for(int j = 0; j < s.length(); j++){
                symbols[i][j + currentViewNicknameYAnchor] = s.charAt(j);
            }
            i++;
        }
        for(Resource r : resources){
            symbols[i][currentViewNicknameYAnchor] = '>';
            symbols[i][currentViewNicknameYAnchor + 2] = r.symbol.charAt(0);
            colours[i][currentViewNicknameYAnchor + 2] = Colour.getColourByResource(r);
            for(int j = 0; j < r.toString().length(); j++){
                symbols[i][currentViewNicknameYAnchor + 4 + j] = r.toString().charAt(j);
            }
            i++;
        }
    }

    private void drawAssistantCardSlots() {
        int yStep = GraphicalCardGrid.cardWidth +1;
        Stack<Integer>[] assistantCardSlots = MatchData.getInstance().getLightClientByNickname(this.nickname).getAssistantCardSlots();

        for(int i = 0; i < assistantCardSlots.length; i++){
            for(int j = 0; j < assistantCardSlots[i].size(); j++){
                LightAssistantCard ldc = MatchData.getInstance().getAssistantCardByID(assistantCardSlots[i].get(j));
                GraphicalAssistantCards gdc = new GraphicalAssistantCards(ldc, this.nickname);
                gdc.drawCard();

                int x_anchor = this.assistCardSlotsXAnchor + (assistantCardSlots[i].size()*(-2) + 2) + j*2;
                drawElement(gdc.getHeight(), gdc.getWidth(), gdc.getColours(), gdc.getSymbols(), gdc.getBackGroundColours(),
                        x_anchor, this.assistCardGridYAnchor+i*yStep);

                if(assistantCardSlots[i].size() > 1 && j != 0){
                    symbols[x_anchor][this.assistCardGridYAnchor+i*yStep] = '╠';
                    symbols[x_anchor][this.assistCardSlotsYAnchor+i*yStep+gdc.getWidth()-1] = '╣';

                }
            }
        }
        drawHorizontalLabel(assistCardSlotsXAnchor + GraphicalCardGrid.cardHeight, assistCardSlotsYAnchor + 10, "Assistant Cards Slots");
    }

    private void drawHorizontalLabel(int x, int y, String label) {
        for(int i = 0; i < label.length(); i++){
            symbols[x][y + i] = label.charAt(i);
            colours[x][y + i] = Colour.ANSI_BRIGHT_BLUE;
        }
    }

    private void drawAssistantCards() {
        int yStep = GraphicalCardGrid.cardWidth +1;
        List<Integer> assistantCards = MatchData.getInstance().getLightClientByNickname(this.nickname).getOwnedAssistantCards();

        for(int i = 0; i < assistantCards.size(); i++){
            LightAssistantCard lac = MatchData.getInstance().getAssistantCardByID(assistantCards.get(i));
            GraphicalAssistantCards gac = new GraphicalAssistantCards(lac, this.nickname);
            gac.drawCard();
            drawElement(GraphicalCardGrid.cardHeight, GraphicalCardGrid.cardWidth, gac.getColours(), gac.getSymbols(),
                    gac.getBackGroundColours(), this.ownedAssistantCardXAnchor, this.ownedAssistantCardYAnchor + i*yStep);
        }
        String label = "Your assistant cards";
        drawHorizontalLabel(ownedAssistantCardXAnchor + GraphicalCardGrid.cardHeight, ownedAssistantCardYAnchor + 6, label);

    }
    private void drawCompositeElement(GraphicalElement ge, int x_anchor, int y_anchor){
        int geWidth = ge.getWidth();
        int geHeight = ge.getHeight();

        Colour[][] colours = ge.getColours();
        char[][] symbols = ge.getSymbols();
        BackGroundColour[][] backGroundColours = ge.getBackGroundColours();

        drawElement(geHeight, geWidth, colours, symbols, backGroundColours, x_anchor, y_anchor);
    }

    private void drawAssistantCardGrid(){
        graphicalAssistantCardGrid.drawAssistantCardGrid(MatchData.getInstance().getAssistantCardGrid());
        drawCompositeElement(graphicalAssistantCardGrid, assistCardGridXAnchor, assistCardGridYAnchor);
        String label = "Assistant Card Grid";
        drawHorizontalLabel(assistCardGridXAnchor + GraphicalCardGrid.cardHeight*3, 19, label);
    }

    private void drawElement(int height, int width, Colour[][] colours, char[][] symbols, BackGroundColour[][] backGroundColours, int x_anchor, int y_anchor){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                this.symbols[i + x_anchor][j + y_anchor] = symbols[i][j];
                this.colours[i + x_anchor][j + y_anchor] = colours[i][j];
                this.backGroundColours[i + x_anchor][j + y_anchor] = backGroundColours[i][j];
            }
        }
    }

    /**
     * Method to display a list of card outside of the standard view
     * @param IDs the IDs to be displayed
     */
    public void displayCardSelection(List<Integer> IDs){
        reset();
        int x_anchor = height - GraphicalCardGrid.cardHeight;
        int y_anchor = 0;
        int y_step = GraphicalCardGrid.cardWidth + 1;
        LightAssistantCard ac;
        GraphicalAssistantCards gac;


        for(Integer ID : IDs){
            ac = MatchData.getInstance().getAssistantCardByID(ID);
            gac = new GraphicalAssistantCards(ac, this.nickname);
            gac.drawCard();
            drawElement(GraphicalCardGrid.cardHeight, GraphicalCardGrid.cardWidth, gac.getColours(), gac.getSymbols(),
                    gac.getBackGroundColours(), x_anchor, y_anchor);

            y_anchor += y_step;
        }
        int x_start = height - GraphicalCardGrid.cardHeight;
        int x_end = height;
        int y_start = 0;
        int y_end = width;
        displayASection(x_start, x_end, y_start, y_end);
    }

    /**
     * Method to display only a section of the screen
     * @param x_start the i-start of the section
     * @param x_end the i-end of the section
     * @param y_start the j-start of the section
     * @param y_end the j-end of the section
     */
    private void displayASection(int x_start, int x_end, int y_start, int y_end) {
        for(int i = x_start; i < x_end; i++){
            for(int j = y_start; j < y_end; j++){
                System.out.print(backGroundColours[i][j].getCode() + colours[i][j].getCode() + symbols[i][j] + Colour.ANSI_RESET); //
            }
            System.out.print("\n");
        }
    }
}
