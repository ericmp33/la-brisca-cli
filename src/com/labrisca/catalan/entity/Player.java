package com.labrisca.catalan.entity;

import com.labrisca.catalan.Card;
import com.labrisca.catalan.util.Color;
import com.labrisca.catalan.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    // fields
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

    // returns the card on top of the deck card
    Card topDeckCard() {
        Card temp = getGame().getDeck().get(0);
        getGame().getDeck().remove(temp);
        return temp;
    }

    // take a card
    public abstract void takeCard();

    // print available in-hand cards to be thrown
    public abstract void printCardsInHand();

    // throw a card
    public abstract void throwCard(int round);

    // common code for child classes
    void commonThrowCard(Card card, int round) {
        System.out.println("\n[!] " + name + " ha tirat -> " + Color.name((card.getName())));

        // modify card attributes
        card.setThrownBy(this);
        card.setRoundThrown(round);

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
    public void changeLastCard() {
        // if it can, change it
        if (canChangeLastCard()) {
            // unset the latest card and save it in the hand of the player
            Card oldLatest = game.latestCard();
            oldLatest.setLatest("old");
            game.getDeck().remove(oldLatest);
            inHandCards.add(oldLatest);

            // remove the seven trump from the hand of the player and set it as the new latest card
            Card player7Trump = getSevenTrump();
            player7Trump.setLatest("true");
            inHandCards.remove(player7Trump);
            game.getDeck().add(player7Trump);

            System.out.println("\n[!] " + name + " ha canviat l'última carta");
        }
    }

    // returns if player can change latest card
    public boolean canChangeLastCard() {
        // true if player has 7 of trump, its value is less than latest and round is less than 21
        if (getGame().deckIsEmpty()) return false;
        return has7Trump() && getSevenTrump().getValue() < game.latestCard().getValue() && game.getRound() < 21;
    }

    // returns if player has 7 of trump
    private boolean has7Trump() {
        for (Card card : inHandCards) {
            if (card.getValue() == 105) return true;
        }
        return false;
    }

    // returns 7 of trump
    private Card getSevenTrump() {
        for (Card card : inHandCards) {
            if (card.getValue() == 105) return card;
        }
        return getSevenTrump();
    }
}
