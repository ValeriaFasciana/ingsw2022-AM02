package it.polimi.ingsw.client.view;

public final class Constants {



    private Constants(){}

    // NON-PRINTABLE CHARACTERS
    public static final int END_OF_TEXT = 0x03;
    public static final int BACKSPACE_0 = 0x08;
    public static final int BACKSPACE_1 = 0x7F;
    public static final int HORIZONTAL_TAB = 0x09;
    public static final int ESCAPE = 0x1B;
    public static final int CARRIAGE_RETURN = 0x0D;

    public static final int BRACKET = 0x5B;
    public static final int ARROW_UP = 0x41;
    public static final int ARROW_DOWN = 0x42;
    public static final int ARROW_RIGHT = 0x43;
    public static final int ARROW_LEFT = 0x44;

    public static final int EXIT = ESCAPE;


    // COLORS
    public static final String ANSI_RESET = "\033[0m";


    // DEFAULT INPUT STRINGS
    public static final String YES = "y";
    public static final String NO = "n";
    public static final String QUIT = "quit";
    public static final String CREATE_LOBBY = "1";
    public static final String JOIN_LOBBY = "2";


    // TABLE DRAWING UTILS
    public static final String LINE_VERTICAL = "┃";
    public static final String LINE_TOP_LEFT = "┏";
    public static final String LINE_TOP_RIGHT = "┓";
    public static final String LINE_BOTTOM_LEFT = "┗";
    public static final String LINE_BOTTOM_RIGHT = "┛";
    public static final String LINE_HORIZONTAL = "━";
    public static final String LINE_T_LEFT = "┣";
    public static final String LINE_T_RIGHT = "┫";
    public static final char STUDENT = '♟';
    public static final char TOWER = '\u2593';
    public static final char ISLE_BORDER = '\u258B';
    public static final char CLOUD_BORDER = '\u258B';


    public static final String LINE_SHADOW_RIGHT = "\u258c";
    public static final String LINE_SHADOW_BOTTOM = "\u2580";
    public static final String LINE_SHADOW_CORNER = "\u2598";

    // SPECIAL ANSI SEQUENCES
    public static final String CURSOR_BACK = "\033[1D";

    // MISC
    public static final String STOP_BLINK = "\033[25m";
    public static final String BLINKER = "\033[5m" + '\u2588' + CURSOR_BACK + STOP_BLINK;
}
