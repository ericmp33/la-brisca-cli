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
            // throw the card with less value
            System.out.println("So I throw first... I throw the less valuable card");
            return lessValuableCardReplacement();//todo check if works better than lessvaluableard()
//            return lessValuableCard(); // todo take care if there's 2 triumph and one ace or three, then hmm (do like lessValuableCardWithSameType)
        }

        // else, means that will throw after human player, save human's thrown card
        Card thrownByHuman = game.getThePlay().get(0);

        // if human thrown triumph three (card with value 111) and bot has triumph ace, throw it
        if (thrownByHuman.getValue() == 111 && hasTriumphAce()) {
            System.out.println("Et caso el tres!");
            return triumphAce();
        }

        // else, if human's card is triumph
        if (thrownByHuman.isTriumph()) {
            // throw less valuable card with triumph
            System.out.println("You've thrown triumph");
            if (hasTriumphCards() && hasMoreValuableCardsWithSameType(thrownByHuman) && thrownByHuman.getPoints() > 3) {
                System.out.println("Oh I have triumph and more valuable card with same type and you thrown a card with more than 3 points");
                return lessValuableCardWithTriumph();
            }

            System.out.println("I don't have triumph or I don't have more valuable card with same type or u thrown a card with less than 3 points");
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

            // if bot has triumph cards
            if (hasTriumphCards()) {
                System.out.println("I have triumph! I throw the less valuable triumph card and I win the play");
                // win the play
                return lessValuableCardWithTriumph();
            }

            System.out.println("Oh.. I have neither more valuable cards with type nor triumph");
        }

        // if card doesn't have points
        if (thrownByHuman.getPoints() == 0) {
            System.out.println("Oh, your card doesn't have points, I throw the less valuable card");
            return lessValuableCard();
        }

        System.out.println("[log] -> AI arrived to -> return lessValuableCard()");
        return lessValuableCard();
    }

    // returns true if has cards with triumph
    private boolean hasTriumphCards() {
        for (Card card : bot.getHandCards()) {
            if (card.isTriumph()) return true;
        }
        return false;
    }

    // returns the less valuable card
    private Card lessValuableCard() {
        Card lessValuable = bot.getHandCards().get(0);
        for (Card card : bot.getHandCards()) {
            // if "lessValuable" card value is greater than "card" value
            if (lessValuable.getValue() > card.getValue()) {
                // less valuable card is "card"
                lessValuable = card;
            }
        }
        return lessValuable;
    }

    // returns the less valuable card todo version 2 - serveix perque -> take care if there's 2 triumph and one ace or three, then hmm (do like lessValuableCardWithSameType)
    private Card lessValuableCardReplacement() {
        // check first if has 2 triumph cards and other one isn't
        int triumphCount = 0;
        for (Card card : bot.getHandCards()) {
            if (card.isTriumph()) triumphCount++;
        }

        if (triumphCount == 2) {
            // check if the possible card to be thrown has less than 10 points
            for (Card card : bot.getHandCards()) {
                if (card.getPoints() < 10 && !card.isTriumph()) return card;
            }
        }

        Card lessValuable = bot.getHandCards().get(0);
        for (Card card : bot.getHandCards()) {
            // if "lessValuable" card value is greater than "card" value
            if (lessValuable.getValue() > card.getValue()) {
                // less valuable card is "card"
                lessValuable = card;
            }
        }
        return lessValuable;
    }

    // returns the less valuable card with triumph
    private Card lessValuableCardWithTriumph() {
        Card lessValuable = cardWithTriumph();
        for (Card card : bot.getHandCards()) {
            if (lessValuable.getValue() > card.getValue() && card.isTriumph()) {
                lessValuable = card;
            }
        }
        return lessValuable;
    }

    // returns a card with triumph
    private Card cardWithTriumph() {
        for (Card card : bot.getHandCards()) {
            if (card.isTriumph()) return card;
        }

        // if there is not cards with triumph returns lessValuableCard
        return lessValuableCard();
    }

    // returns most valuable card within same card type
    private Card mostValuableCardWithSameType(String type) {
        Card mostValuable = sameCardType(type);
        for (Card card : bot.getHandCards()) {
            if (mostValuable.getValue() < card.getValue() && mostValuable.getType().equals(type) && type.equals(card.getType())) {
                mostValuable = card;
            }
        }
        return mostValuable;
    }

    // returns a card with the same type as the param
    private Card sameCardType(String type) {
        for (Card carta : bot.getHandCards()) {
            if (type.equals(carta.getType())) {
                return carta;
            }
        }
        return lessValuableCard();
    }

    // returns triumph ace
    private Card triumphAce() {
        for (int i = 0; i < bot.getHandCards().size(); i++) {
            // if finds triumph ace, return it (triumph ace's value is 112)
            if (bot.getHandCards().get(i).getValue() == 112) return bot.getHandCards().get(i);
        }
        return null;
    }

    // returns true if bot has triumph ace
    private boolean hasTriumphAce() {
        for (Card card : bot.getHandCards()) {
            if (card.getValue() == 112) return true;
        }
        return false;
    }

    // returns true if has cards more value than the param card within same card type
    private boolean hasMoreValuableCardsWithSameType(Card thrownByHuman) {
        for (Card card : bot.getHandCards()) {
            // if card's value is greater than thrownByHuman and has same card type
            if (card.getValue() > thrownByHuman.getValue() && card.getType().equals(thrownByHuman.getType())) {
                return true;
            }
        }
        return false;
    }
}
