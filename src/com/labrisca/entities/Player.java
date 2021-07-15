package com.labrisca.entities;

import com.labrisca.Card;
import com.labrisca.Game;

import java.util.ArrayList;
import java.util.List;

public class Player {
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
    public Player(String name, Game game) {
        this.name = name;
        this.inHandCards = new ArrayList<>();
        this.wonCards = new ArrayList<>();
        this.game = game;
    }

    // take a card
    public void takeCard() {
        // method overridden in Bot and Human classes
    }

    // print available in-hand cards to be thrown
    public void printCardsInHand() {
        // method overridden in Bot and Human classes
    }

    // throw a card
    public void throwCard() {
        // method overridden in Bot and Human classes
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
            oldLatest.setSavedBy(this);

            // and save it in the hand of the player
            inHandCards.add(oldLatest);

            // remove the seven trump from the hand of the player
            Card player7Trump = sevenTrump();
            inHandCards.remove(player7Trump);

            // and set it as the new latest card
            Card newLatest = game.getDeck().get(game.posCard(player7Trump));
            newLatest.setLatest(true);
            newLatest.setTaken(false);
            newLatest.setSavedBy(null);

            System.out.println("\n[!] " + name + " changed latest card");
        }
    }

    // returns if player can change latest card
    boolean canChangeLastCard() {
        // true if player has 7 of trump and its value is less than latest and round is less than 21
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