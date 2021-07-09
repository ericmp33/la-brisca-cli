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
            System.out.println("So I throw first...");
            return lessValuableCard();
        }

        // else, means that will throw after human player, save human's thrown card
        Card thrownByHuman = game.getThePlay().get(0);

        // if human thrown trump three (card with value 111) and bot has trump ace, throw it
        if (thrownByHuman.getValue() == 111 && hasTrumpAce()) {
            System.out.println("Et caso el tres!");
            return trumpAce();
        }

        // else, if human's card is trump
        if (thrownByHuman.isTrump()) {
            // throw less valuable card with trump
            System.out.println("You've thrown trump");
            if (hasTrumpCards() && hasMoreValuableCardsWithSameType(thrownByHuman) && thrownByHuman.getPoints() > 3) {
                System.out.println("Oh I have trump and more valuable card with same type and you thrown a card with more than 3 points");
                return lessValuableCardWithTrump();
            }

            System.out.println("I don't have trump or I don't have more valuable card with same type or u thrown a card with less than 3 points");
            return lessValuableCard();
        }

        // if card has points
        if (thrownByHuman.getPoints() > 0) {
            System.out.println("Hmm, card has points, let me see if I can win the play");

            // if bot has more valuable cards with same type
            if (hasMoreValuableCardsWithSameType(thrownByHuman)) {
                // win the play
                System.out.println("I have more valuable cards with same type! I win the play");
                return mostValuableCardWithSameType(thrownByHuman.getType());
            }

            // if bot has trump cards
            if (hasTrumpCards()) {
                System.out.println("I have trump! I throw the less valuable trump card and I win the play");
                // win the play
                return lessValuableCardWithTrump();
            }

            System.out.println("Oh.. I have neither more valuable cards with type nor trump");
        }

        // if card doesn't have points
        if (thrownByHuman.getPoints() == 0) {
            String type = thrownByHuman.getType();
            // find most valuable card with same type
            Card mostValuable = mostValuableCardWithSameType(type);

            // check if bot can kill and win points
            for (Card card : bot.getInHandCards()) {
                // if cards have same type and if bot's card has points > 0 and if card is the most valuable
                if (type.equals(card.getType()) && card.getPoints() > 0 && card == mostValuable) {
                    System.out.println("Your card doesn't have points, but I can kill and win points");
                    // win the play
                    return card;
                }
            }
        }

        System.out.println("[log] -> AI arrived to -> return lessValuableCard()");
        return lessValuableCard();
    }

    // returns true if has cards with trump
    private boolean hasTrumpCards() {
        for (Card card : bot.getInHandCards()) {
            if (card.isTrump()) return true;
        }
        return false;
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
            System.out.println("I throw the less valuable card with trump");
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
        System.out.println("I throw the less valuable card");
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

    // returns a card with trump
    private Card trumpCard() {
        for (Card card : bot.getInHandCards()) {
            if (card.isTrump()) return card;
        }

        // if there is not trump cards return lessValuableCard
        return lessValuableCard();
    }

    // returns most valuable card within same card type
    private Card mostValuableCardWithSameType(String type) {
        Card mostValuable = sameCardType(type);
        for (Card card : bot.getInHandCards()) {
            if (mostValuable.getValue() < card.getValue() && mostValuable.getType().equals(type) && type.equals(card.getType())) {
                mostValuable = card;
            }
        }
        return mostValuable;
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

    // returns trump ace
    private Card trumpAce() {
        for (int i = 0; i < bot.getInHandCards().size(); i++) {
            // if finds trump ace (= 112 value), return it
            if (bot.getInHandCards().get(i).getValue() == 112) return bot.getInHandCards().get(i);
        }
        return null;
    }

    // returns true if bot has trump ace
    private boolean hasTrumpAce() {
        for (Card card : bot.getInHandCards()) {
            if (card.getValue() == 112) return true;
        }
        return false;
    }

    // returns true if has cards more value than the param card within same card type
    private boolean hasMoreValuableCardsWithSameType(Card thrownByHuman) {
        for (Card card : bot.getInHandCards()) {
            // if card's value is greater than thrownByHuman and has same card type
            if (card.getValue() > thrownByHuman.getValue() && card.getType().equals(thrownByHuman.getType())) {
                return true;
            }
        }
        return false;
    }
}
