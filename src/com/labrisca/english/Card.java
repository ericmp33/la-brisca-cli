package com.labrisca.english;

import com.labrisca.english.entities.Player;

public class Card {
    // variables
    private final String name;
    private final String type;
    private int value;
    private final int points;
    private int roundThrown;
    private boolean trump;
    private String latest;
    private Player thrownBy;
    private boolean thrownFirst;

    // getters
    public String getName() { return name; }
    public String getType() { return type; }
    public int getPoints() { return points; }
    public int getValue() { return value; }
    public boolean isTrump() { return trump; }
    public Player getThrownBy() { return thrownBy; }
    public boolean isThrownFirst() { return thrownFirst; }

    // setters
    public void setValue(int value) { this.value = value; }
    public void setRoundThrown(int roundThrown) { this.roundThrown = roundThrown; }
    public void setTrump(boolean trump) { this.trump = trump; }
    public void setLatest(String latest) { this.latest = latest; }
    public void setThrownBy(Player thrownBy) { this.thrownBy = thrownBy; }
    public void setThrownFirst(boolean thrownFirst) { this.thrownFirst = thrownFirst; }

    // constructor
    public Card(String name, String type, int value, int points) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.points = points;
        this.roundThrown = -1;
        this.latest = "false";
    }

    public void printAttributes() {
        System.out.printf(
            "%-33s %-22s %-22s %-21s %-26s %-23s %-24s %-21s %-5s %n",
            "{ name: " + Color.name(name, false),
            "type: " + Color.type(type),
            "value: " + Color.num(value),
            "points: " + Color.num(points),
            "roundThrown: " + Color.num(roundThrown),
            "trump: " + Color.bool(trump),
            "latest: " + Color.bool(latest),
            "thrownBy: " + checkNull(thrownBy),
            "thrownFirst: " + addFinalSpace(Color.bool(thrownFirst)) + " }"
        );
    }

    // prevent null pointer exception when printing "thrownBy"
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
