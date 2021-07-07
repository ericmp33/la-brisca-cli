package com.labrisca;

public class Card {
    // variables
    private final String name;
    private final String type; // "bastos", "copes", "espases" or "oros"
    private final int num;
    private int value;
    private final int points;
    private boolean thrown;
    private boolean taken;
    private boolean triumph;
    private boolean latest; // if will be the latest card to be taken
    private Player thrownBy;
    private Player takenBy;
    private boolean thrownFirst;

    // getters
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public int getPoints() {
        return points;
    }
    public int getValue() {
        return value;
    }
    public boolean isTaken() {
        return taken;
    }
    public boolean isTriumph() {
        return triumph;
    }
    public boolean isLatest() {
        return latest;
    }
    public Player getThrownBy() {
        return thrownBy;
    }
    public boolean isThrownFirst() {
        return thrownFirst;
    }

    // setters
    public void setValue(int value) {
        this.value = value;
    }
    public void setThrown(boolean thrown) {
        this.thrown = thrown;
    }
    public void setTaken(boolean taken) {
        this.taken = taken;
    }
    public void setTriumph(boolean triumph) {
        this.triumph = triumph;
    }
    public void setLatest(boolean latest) {
        this.latest = latest;
    }
    public void setThrownBy(Player thrownBy) {
        this.thrownBy = thrownBy;
    }
    public void setTakenBy(Player takenBy) {
        this.takenBy = takenBy;
    }
    public void setThrownFirst(boolean thrownFirst) {
        this.thrownFirst = thrownFirst;
    }

    // constructor
    public Card(String name, String type, int num, int value, int points) {
        this.name = name;
        this.type = type;
        this.num = num;
        this.value = value;
        this.points = points;
        this.thrown = false;
        this.taken = false;
        this.triumph = false;
        this.latest = false;
        this.thrownBy = null;
        this.takenBy = null;
        this.thrownFirst = false;
    }

    public void printAttributes() {
        System.out.printf(
            "%-25s %-25s %-20s %-21s %-21s %-24s %-24s %-25s %-25s %-20s %-20s %-20s %n",
            "{ name: " + name,
            "type: " + colorizeType(type),
            "num: " + colorizeNum(num),
            "value: " + colorizeNum(value),
            "points: " + colorizeNum(points),
            "thrown: " + colorizeBool(thrown),
            "taken: " + colorizeBool(taken),
            "triumph: " + colorizeBool(triumph),
            "latest: " + colorizeBool(latest),
            "thrownBy(): " + thrownBy.getName(),
            "takenBy(): " + takenBy.getName(),
            "thrownFirst(): " + addFinalSpace(colorizeBool(thrownFirst)) + " }"
        );
    }

    // returns colorized card's type
    public static String colorizeType(String s) {
        return switch (s) {
            case "bastos" -> Color.ANSI_GREEN + s + Color.ANSI_RESET;
            case "copes" -> Color.ANSI_RED + s + Color.ANSI_RESET;
            case "espases" -> Color.ANSI_BLUE + s + Color.ANSI_RESET;
            default -> Color.ANSI_YELLOW + s + Color.ANSI_RESET;
        };
    }

    // returns colorized card's num
    private static String colorizeNum(int n) {
        return Color.ANSI_BLUE + n + Color.ANSI_RESET;
    }

    // returns colorized card's name
    public static String colorizeName(String s) {
        if (s.contains("basto")) return aux(s, "basto", Color.ANSI_GREEN);
        else if (s.contains("copa")) return aux(s, "copa", Color.ANSI_RED);
        else if (s.contains("espasa")) return aux(s, "espasa", Color.ANSI_BLUE);
        else return aux(s, "oro", Color.ANSI_YELLOW);
    }
    private static String aux(String s, String target, String type) {
        return s.substring(0, s.indexOf(target)) + type + s.substring(s.indexOf(target)) + Color.ANSI_RESET;
    }

    // returns colorized card's booleans
    private static String colorizeBool(boolean b) {
        // if is true, returns it green
        if (b) return Color.ANSI_GREEN + true + Color.ANSI_RESET;

        // else, red
        return Color.ANSI_RED + false + Color.ANSI_RESET;
    }

    // add spaces at the end of the printf
    private static String addFinalSpace(String s) {
        if (s.contains("true")) return s + " ";
        return s;
    }
}
