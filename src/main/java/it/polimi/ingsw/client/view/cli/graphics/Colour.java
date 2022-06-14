package it.polimi.ingsw.client.view.cli.graphics;

public enum Colour {
    ANSI_BLACK("\u001B[30m"),
    ANSI_RED  ("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE ("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_CYAN ("\u001B[36m"),
    ANSI_GREY("\u001B[37m"),
    ANSI_WHITE_BRIGHT_BOLD("\033[1;97m"),
    ANSI_BRIGHT_BLACK("\u001B[90m"),
    ANSI_BRIGHT_RED  ("\u001B[91m"),
    ANSI_BRIGHT_GREEN("\u001B[92m"),
    ANSI_BRIGHT_YELLOW("\u001B[93m"),
    ANSI_BRIGHT_BLUE ("\u001B[94m"),
    ANSI_BRIGHT_PURPLE("\u001B[95m"),
    ANSI_BRIGHT_CYAN ("\u001B[96m"),
    ANSI_BRIGHT_WHITE("\u001B[97m"),
    ANSI_DEFAULT("\u001B[0m");

    public static final String ANSI_RESET = "\u001B[0m";


    private final String code;

    Colour(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
