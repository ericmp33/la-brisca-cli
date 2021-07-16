package com.labrisca;

import com.labrisca.entities.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Color {
    // src : https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    Color() {}

    // returns a random color
    private static String randomColor() {
        int rand = ThreadLocalRandom.current().nextInt(1, 6);
        return "\u001B[3" + rand + "m";
    }

    // returns given String colorized randomly
    static String colorizeRand(String s) {
        String oldRandom = randomColor();

        StringBuilder colorize = new StringBuilder(oldRandom);
        for (int i = 0; i < s.length(); i++) {
            String newRandom = randomColor();
            while (newRandom.equals(oldRandom)) {
                newRandom = randomColor();
            }

            oldRandom = newRandom;
            colorize.append(s.charAt(i)).append(newRandom);
        }
        return colorize + ANSI_RESET;
    }

    // returns colorized card's type
    static String type(String s) {
        if (s.toLowerCase().contains("club")) return Color.ANSI_GREEN + s + Color.ANSI_RESET;
        if (s.toLowerCase().contains("cup")) return Color.ANSI_RED + s + Color.ANSI_RESET;
        if (s.toLowerCase().contains("sword")) return Color.ANSI_BLUE + s + Color.ANSI_RESET;
        if (s.toLowerCase().contains("coin")) return Color.ANSI_YELLOW + s + Color.ANSI_RESET;
        throw new IllegalStateException("Unexpected error using colorizeType(String s)");
    }

    // returns colorized card's num
    static String num(int n) {
        return Color.ANSI_BLUE + n + Color.ANSI_RESET;
    }

    // returns colorized card's name
    public static String name(String cardName, Boolean hacker) {
        String color;
        if (hacker) color = Color.ANSI_PURPLE;
        else color = Color.ANSI_RESET;

        String subStr0 =  cardName.substring(0, cardName.indexOf("'"));
        String subStr1 = cardName.substring(cardName.indexOf("'"));

        if (cardName.toLowerCase().contains("club")) return Color.ANSI_GREEN + subStr0 + color + subStr1;
        if (cardName.toLowerCase().contains("cup")) return Color.ANSI_RED + subStr0 + color + subStr1;
        if (cardName.toLowerCase().contains("sword")) return Color.ANSI_BLUE + subStr0 + color + subStr1;
        if (cardName.toLowerCase().contains("coin")) return Color.ANSI_YELLOW + subStr0 + color + subStr1;

        throw new IllegalStateException("Unexpected error using colorizeNamePurple(String s)");
    }

    // returns colorized card's boolean
    static String bool(boolean b) {
        if (b) return Color.ANSI_GREEN + true + Color.ANSI_RESET;
        return Color.ANSI_RED + false + Color.ANSI_RESET;
    }

    // returns green text if player is not bot, else red
    static String playWinner(Player winner) {
        String color;
        if (winner.getName().equalsIgnoreCase("bot")) color = Color.ANSI_RED;
        else color = Color.ANSI_GREEN;
        return color + ">> " + winner.getName() + " won the play" + Color.ANSI_RESET;
    }
}
