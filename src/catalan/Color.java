package catalan;

import catalan.entities.Player;

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
        if (s.toLowerCase().contains("bast")) return Color.ANSI_GREEN + s + Color.ANSI_RESET;
        if (s.toLowerCase().contains("cop")) return Color.ANSI_RED + s + Color.ANSI_RESET;
        if (s.toLowerCase().contains("espas")) return Color.ANSI_BLUE + s + Color.ANSI_RESET;
        if (s.toLowerCase().contains("or")) return Color.ANSI_YELLOW + s + Color.ANSI_RESET;
        throw new IllegalStateException("Unexpected error using Color.type(String s)");
    }

    // returns colorized card's num
    static String num(int n) {
        return Color.ANSI_BLUE + n + Color.ANSI_RESET;
    }

    public static String acolorirNom(String s) {
        if (s.contains("bast")) return aux(s, "basto", Color.ANSI_GREEN);
        else if (s.contains("cop")) return aux(s, "copa", Color.ANSI_RED);
        else if (s.contains("espa")) return aux(s, "espasa", Color.ANSI_BLUE);
        else return aux(s, "or", Color.ANSI_YELLOW);
    }
    private static String aux(String s, String target, String tipus) {
        return s.substring(0, s.indexOf(target)) + tipus + s.substring(s.indexOf(target)) + Color.ANSI_RESET;
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
        return color + ">> El jugador " + winner.getName() + " ha guanyat la jugada" + Color.ANSI_RESET;
    }
}
