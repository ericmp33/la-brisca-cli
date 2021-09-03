package com.labrisca.catalan.util;

import com.labrisca.catalan.entity.Player;

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
    public static String type(String s) {
        if (s.toLowerCase().contains("bast")) return ANSI_GREEN + s + ANSI_RESET;
        if (s.toLowerCase().contains("cop")) return ANSI_RED + s + ANSI_RESET;
        if (s.toLowerCase().contains("espas")) return ANSI_BLUE + s + ANSI_RESET;
        if (s.toLowerCase().contains("or")) return ANSI_YELLOW + s + ANSI_RESET;
        throw new IllegalStateException("Unexpected error using type(String s)");
    }

    // returns colorized card's num
    public static String num(int n) {
        return ANSI_BLUE + n + ANSI_RESET;
    }

    // returns colorized card's name
    public static String name(String s) {
        if (s.contains("bast")) return aux(s, "basto", ANSI_GREEN);
        else if (s.contains("cop")) return aux(s, "copa", ANSI_RED);
        else if (s.contains("espa")) return aux(s, "espasa", ANSI_BLUE);
        else return aux(s, "or", ANSI_YELLOW);
    }
    private static String aux(String s, String target, String tipus) {
        return s.substring(0, s.indexOf(target)) + tipus + s.substring(s.indexOf(target)) + ANSI_RESET;
    }

    // returns colorized card's boolean
    public static String bool(boolean b) {
        if (b) return ANSI_GREEN + true + ANSI_RESET;
        return ANSI_RED + false + ANSI_RESET;
    }

    // returns colorized latest card
    public static String bool(String s) {
        if (s.equals("false")) return ANSI_RED + s + ANSI_RESET;
        return ANSI_GREEN + s + ANSI_RESET;
    }

    // returns green text if player is not bot, else red
    public static String playWinner(Player winner) {
        String color;
        if (winner.getName().equalsIgnoreCase("bot")) color = ANSI_RED;
        else color = ANSI_GREEN;
        return color + ">> " + winner.getName() + " ha guanyat la jugada" + ANSI_RESET;
    }
}
