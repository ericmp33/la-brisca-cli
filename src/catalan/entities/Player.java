package catalan.entities;

import catalan.Card;
import catalan.Color;
import catalan.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Player {
    // variables
    private final String name;
    private final List<Card> inHandCards;
    private final List<Card> wonCards;
    private final Game game;

    // getters
    public String getName() { return name; }
    public List<Card> getInHandCards() { return inHandCards; }
    public List<Card> getWonCards() { return wonCards; }
    public Game getGame() { return game; }

    // constructor
    protected Player(String name, Game game) {
        this.name = name;
        this.inHandCards = new ArrayList<>();
        this.wonCards = new ArrayList<>();
        this.game = game;
    }

    // returns a not-taken random card
    Card randCard() {
        while (true) {
            // randomly take a card
            int rand = ThreadLocalRandom.current().nextInt(0, getGame().getDeck().size());
            Card card = getGame().getDeck().get(rand);

            // if card hasn't been taken and isn't latest or is latest and how many cards are left to take is 1
            if (!card.isTaken() && !card.isLatest() || card.isLatest() && getGame().howManyLeftToTake() == 1) {
                return card;
            }
        }
    }

    // take a card
    public abstract void takeCard();

    // print available in-hand cards to be thrown
    public abstract void printCardsInHand();

    // throw a card
    public abstract void throwCard();

    // common code for bot and human
    void commonThrowCard(Card card) {
        System.out.println("\n[!] El jugador " + name + " ha tirat -> " + Color.acolorirNom((card.getName())));

        // throw the card
        card.setThrownBy(this);

        // if the play is empty
        if (getGame().getThePlay().isEmpty()) card.setThrownFirst(true);

        // put the card to the play
        getInHandCards().remove(card);
        getGame().getThePlay().add(card);
    }

    // returns the total obtained points
    public int getPoints() {
        int count = 0;
        for (Card card : wonCards) count += card.getPoints();
        return count;
    }

    // changes the last card for 7 of trump
    void changeLastCard() {
        // if can, change it
        if (canChangeLastCard()) {
            // unset the latest card
            Card oldLatest = game.latestCard();
            oldLatest.setLatest(false);
            oldLatest.setTaken(true);
            oldLatest.setWonBy(this);

            // and save it in the hand of the player
            inHandCards.add(oldLatest);

            // remove the seven trump from the hand of the player
            Card player7Trump = sevenTrump();
            inHandCards.remove(player7Trump);

            // and set it as the new latest card
            Card newLatest = game.getDeck().get(game.posCard(player7Trump));
            newLatest.setLatest(true);
            newLatest.setTaken(false);
            newLatest.setWonBy(null);

            System.out.println("\n[!] El jugador " + name + " ha canviat l'última carta");
        }
    }

    // returns if player can change latest card
    boolean canChangeLastCard() {
        // true if player has 7 of trump, its value is less than latest and round is less than 21
        return has7Trump() && sevenTrump().getValue() < game.latestCard().getValue() && game.getRound() < 21;
    }

    // returns if player has 7 of trump
    private boolean has7Trump() {
        for (Card card : inHandCards) {
            if (card.getValue() == 105) return true;
        }
        return false;
    }

    // returns 7 of trump
    private Card sevenTrump() {
        for (Card card : inHandCards) {
            if (card.getValue() == 105) return card;
        }
        return sevenTrump();
    }
}
