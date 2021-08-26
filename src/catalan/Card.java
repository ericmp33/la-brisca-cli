package catalan;

import catalan.entities.Player;

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
        this.trump = false;
        this.latest = "false";
        this.thrownBy = null;
        this.thrownFirst = false;
    }

    public void printAttributes() {
        System.out.printf(
//            "%-33s %-24s %-19s %-22s %-21s %-24s %-24s %-24s %-24s %-26s %-20s %n",
            "%-33s %-22s %-22s %-21s %-26s %-23s %-24s %-21s %-5s %n", //todo arreglar
            "{ nom: " + Color.name(name),
            "tipus: " + Color.type(type),
            "valor: " + Color.num(value),
            "pedres: " + Color.num(points),
            "tiradaRonda: " + Color.num(roundThrown),
            "triomf: " + Color.bool(trump),
            "ultima: " + Color.bool(latest),
            "tiradaPer: " + Card.checkNull(thrownBy),
            "tiradaPrimer: " + addFinalSpace(Color.bool(thrownFirst)) + " }"
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
