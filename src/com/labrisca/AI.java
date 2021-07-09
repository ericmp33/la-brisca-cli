package com.labrisca;

public class AI {
    // variables
    private final Player bot;
    private final Game game;

    // constructor
    AI(Player bot, Game game) {
        this.bot = bot;
        this.game = game;
    }

    Card throwCard() {
        // if the play is empty, throw first
        if (game.getThePlay().isEmpty()) {
            // throw lessValuableCard
            return lessValuableCard();
        }

        // else, means that will throw after human player, save human's thrown card
        Card thrownByHuman = game.getThePlay().get(0);

        // if human's card is trump
        if (thrownByHuman.isTrump()) {
            // if bot has trump cards and more valuable cards with same type and there is more than 3 points to get
            if (hasTrumpCards() && hasMoreValuableCardsSameType(thrownByHuman) && thrownByHuman.getPoints() > 3) {
                // win the play
                return lessValuableCardWithTrump();
            }

            return lessValuableCard();
        }

        // if card has points
        if (thrownByHuman.getPoints() > 0) {
            // if bot has more valuable cards with same type
            if (hasMoreValuableCardsSameType(thrownByHuman)) {
                // win the play
                return mostValuableCardSameType(thrownByHuman.getType());
            }

            // if bot has trump cards
            if (hasTrumpCards()) {
                // win the play
                return lessValuableCardWithTrump();
            }
        }

        // if card doesn't have points
        if (thrownByHuman.getPoints() == 0) {
            String type = thrownByHuman.getType();
            // find most valuable card with same type
            Card mostValuable = mostValuableCardSameType(type);

            // check if bot can kill and win points
            for (Card card : bot.getInHandCards()) {
                // if cards have same type and if bot's card has points > 0 and if card is the most valuable
                if (type.equals(card.getType()) && card.getPoints() > 0 && card == mostValuable) {
                    // win the play
                    return card;
                }
            }
        }

        return lessValuableCard();
    }

    // returns true if has cards with trump
    private boolean hasTrumpCards() {
        for (Card card : bot.getInHandCards()) {
            if (card.isTrump()) return true;
        }
        return false;
    }

    // returns a card with the same type as the param
    private Card sameCardType(String type) {
        for (Card carta : bot.getInHandCards()) {
            if (type.equals(carta.getType())) {
                return carta;
            }
        }
        return lessValuableCard();
    }

    // returns a card with trump
    private Card trumpCard() {
        for (Card card : bot.getInHandCards()) {
            if (card.isTrump()) return card;
        }

        // if there is not trump cards return lessValuableCard
        return lessValuableCard();
    }

    // returns the less valuable card
    private Card lessValuableCard() {
        // check first if has 2 trump cards and other one isn't
        int trumpCount = 0;
        for (Card card : bot.getInHandCards()) if (card.isTrump()) trumpCount++;

        if (trumpCount == 2) {
            // check if the possible card to be thrown has less than 10 points and is not trump
            for (Card card : bot.getInHandCards()) {
                if (card.getPoints() < 10 && !card.isTrump()) return card;
            }

            // else
            return lessValuableCardWithTrump();
        }

        // if not, return the less valuable card
        Card lessValuable = bot.getInHandCards().get(0);
        for (Card card : bot.getInHandCards()) {
            // if "lessValuable" card value is greater than "card" value
            if (lessValuable.getValue() > card.getValue()) {
                // less valuable card is "card"
                lessValuable = card;
            }
        }
        return lessValuable;
    }

    // returns the less valuable card with trump
    private Card lessValuableCardWithTrump() {
        Card lessValuable = trumpCard();
        for (Card card : bot.getInHandCards()) {
            if (lessValuable.getValue() > card.getValue() && card.isTrump()) {
                lessValuable = card;
            }
        }
        return lessValuable;
    }

    // returns most valuable card with same card type
    private Card mostValuableCardSameType(String type) {
        Card mostValuable = sameCardType(type);
        for (Card card : bot.getInHandCards()) {
            if (mostValuable.getValue() < card.getValue() && mostValuable.getType().equals(type) && type.equals(card.getType())) {
                mostValuable = card;
            }
        }
        return mostValuable;
    }

    // returns true if has cards more value than the param card with same card type
    private boolean hasMoreValuableCardsSameType(Card thrownByHuman) {
        for (Card card : bot.getInHandCards()) {
            // if card's value is greater than thrownByHuman and has same card type
            if (card.getValue() > thrownByHuman.getValue() && card.getType().equals(thrownByHuman.getType())) {
                return true;
            }
        }
        return false;
    }
}
