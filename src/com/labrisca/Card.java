package com.labrisca;

import com.labrisca.entities.Player;

public class Card {
    // variables
    private final String name;
    private final String type;
    private final int num;
    private int value;
    private final int points;
    private boolean taken;
    private boolean trump;
    private boolean latest;
    private Player thrownBy;
    private Player savedBy;
    private boolean thrownFirst;

    // getters
    public String getName() { return name; }
    public String getType() { return type; }
    public int getPoints() { return points; }
    public int getValue() { return value; }
    public boolean isTaken() { return taken; }
    public boolean isTrump() { return trump; }
    public boolean isLatest() { return latest; }
    public Player getThrownBy() { return thrownBy; }
    public boolean isThrownFirst() { return thrownFirst; }

    // setters
    public void setValue(int value) { this.value = value; }
    public void setTaken(boolean taken) { this.taken = taken; }
    public void setTrump(boolean trump) { this.trump = trump; }
    public void setLatest(boolean latest) { this.latest = latest; }
    public void setThrownBy(Player thrownBy) { this.thrownBy = thrownBy; }
    public void setSavedBy(Player savedBy) { this.savedBy = savedBy; }
    public void setThrownFirst(boolean thrownFirst) { this.thrownFirst = thrownFirst; }

    // constructor
    public Card(String name, String type, int num, int value, int points) {
        this.name = name;
        this.type = type;
        this.num = num;
        this.value = value;
        this.points = points;
        this.taken = false;
        this.trump = false;
        this.latest = false;
        this.thrownBy = null;
        this.savedBy = null;
        this.thrownFirst = false;
    }

    public void printAttributes() {
        System.out.printf(
            "%-33s %-24s %-19s %-21s %-21s %-24s %-25s %-25s %-24s %-23s %-20s %n",
            "{ name: " + Color.colorizeName(name),
            "type: " + Color.colorizeType(type),
            "num: " + Color.colorizeNum(num),
            "value: " + Color.colorizeNum(value),
            "points: " + Color.colorizeNum(points),
            "taken: " + Color.colorizeBool(taken),
            "trump: " + Color.colorizeBool(trump),
            "latest: " + Color.colorizeBool(latest),
            "thrownBy(): " + checkNull(thrownBy),
            "savedBy(): " + checkNull(savedBy),
            "thrownFirst(): " + addFinalSpace(Color.colorizeBool(thrownFirst)) + " }"
        );
    }

    // prevent null pointer exception
    private static String checkNull(Player p) {
        if (p == null) return "null";
        return p.getName();
    }

    // add space at the end of the printf
    private static String addFinalSpace(String s) {
        if (s.contains("true")) return s + " ";
        return s;
    }
}
