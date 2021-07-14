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
    public static String randomColor() {
        int rand = ThreadLocalRandom.current().nextInt(1, 6);
        return "\u001B[3" + rand + "m";
    }

    // returns given String colorized randomly
    public static String colorizeRand(String s) {
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
    public static String colorizeType(String s) {
        if (s.toLowerCase().contains("club")) return Color.ANSI_GREEN + s + Color.ANSI_RESET;
        if (s.toLowerCase().contains("cup")) return Color.ANSI_RED + s + Color.ANSI_RESET;
        if (s.toLowerCase().contains("sword")) return Color.ANSI_BLUE + s + Color.ANSI_RESET;
        if (s.toLowerCase().contains("coin")) return Color.ANSI_YELLOW + s + Color.ANSI_RESET;
        throw new IllegalStateException("Unexpected error using colorizeType(String s)");
    }

    // returns colorized card's num
    public static String colorizeNum(int n) {
        return Color.ANSI_BLUE + n + Color.ANSI_RESET;
    }

    // returns colorized card's name
    public static String colorizeName(String s) {
        if (s.toLowerCase().contains("club")) return aux(s, Color.ANSI_GREEN);
        if (s.toLowerCase().contains("cup")) return aux(s, Color.ANSI_RED);
        if (s.toLowerCase().contains("sword")) return aux(s, Color.ANSI_BLUE);
        if (s.toLowerCase().contains("coin")) return aux(s, Color.ANSI_YELLOW);
        throw new IllegalStateException("Unexpected error using colorizeName(String s)");
    }
    private static String aux(String s, String color) {
        return color + s.substring(0, s.indexOf("'")) + Color.ANSI_RESET + s.substring(s.indexOf("'"));
    }

    // returns colorized card's name and purple if "hacker" is true
    public static String colorizeNamePurple(String s) {
        if (s.toLowerCase().contains("club")) return auxPurple(s, Color.ANSI_GREEN);
        if (s.toLowerCase().contains("cup")) return auxPurple(s, Color.ANSI_RED);
        if (s.toLowerCase().contains("sword")) return auxPurple(s, Color.ANSI_BLUE);
        if (s.toLowerCase().contains("coin")) return auxPurple(s, Color.ANSI_YELLOW);
        throw new IllegalStateException("Unexpected error using colorizeNamePurple(String s)");
    }
    private static String auxPurple(String s, String color) {
        return color + s.substring(0, s.indexOf("'")) + Color.ANSI_PURPLE + s.substring(s.indexOf("'"));
    }

    // returns colorized card's boolean
    public static String colorizeBool(boolean b) {
        String color;
        if (b) color = Color.ANSI_GREEN + true;
        else color = Color.ANSI_RED + false;
        return color + Color.ANSI_RESET;
    }

    // returns green text if player is not bot, else red
    static String colorizePlayWinner(Player winner) {
        String color;
        if (winner.getName().equals("bot")) color = Color.ANSI_RED;
        else color = Color.ANSI_GREEN;
        return color + ">> " + winner.getName() + " won the play" + Color.ANSI_RESET;
    }
}
