package com.labrisca;

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
    public static String colorizeRandomly(String s) {
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
        return switch (s) {
            case "club" -> Color.ANSI_GREEN + s + Color.ANSI_RESET;
            case "cup" -> Color.ANSI_RED + s + Color.ANSI_RESET;
            case "sword" -> Color.ANSI_BLUE + s + Color.ANSI_RESET;
            default -> Color.ANSI_YELLOW + s + Color.ANSI_RESET;
        };
    }

    // returns colorized card's num
    public static String colorizeNum(int n) {
        return Color.ANSI_BLUE + n + Color.ANSI_RESET;
    }

    // returns colorized card's name
    public static String colorizeName(String s) {
        if (s.contains("club")) return aux(s, Color.ANSI_GREEN);
        if (s.contains("cup")) return aux(s, Color.ANSI_RED);
        if (s.contains("sword")) return aux(s, Color.ANSI_BLUE);
        return aux(s, Color.ANSI_YELLOW);
    }
    private static String aux(String s, String color) {
        return color + s.substring(0, s.indexOf("'")) + Color.ANSI_RESET + s.substring(s.indexOf("'"));
    }

    // returns colorized card's boolean
    public static String colorizeBool(boolean b) {
        String color;
        if (b) color = Color.ANSI_GREEN + true;
        else color = Color.ANSI_RED + false;
        return color + Color.ANSI_RESET;
    }
}
