package com.labrisca.catalan.entity;

import com.labrisca.catalan.Card;
import com.labrisca.catalan.Game;

public class AI extends Bot {
    // constructor
    public AI(Game game) {
        super(game);
    }

    @Override
    public void throwCard(int round) {
        // if gamemode is hacker show bot's in-hand cards
        if (getGame().getGameMode().equals("hacker")) printCardsInHand();

        // try to change last card
        changeLastCard();

        // "think", and throw a proper card
        commonThrowCard(theChosenOne(), round);
    }

    // returns which card has to be thrown
    public Card theChosenOne() {
        // if the play is empty, means bot throws first
        if (getGame().getThePlay().isEmpty()) {
            // throw lessValuableCard
            return lessValuableCard();
        }

        // else, means it throws after human player, save human's thrown card
        Card hCard = getGame().getThePlay().get(0);

        // if human's card is trump
        if (hCard.isTrump()) {
            // if points > 3, bot has trump cards and more valuable cards with same type
            if (hCard.getPoints() > 3 && hasTrumpCards() && hasMoreValuableCardsSameType(hCard)) {
                // win the play
                return lessValuableCardTrump();
            }

            // else, throw lessValuableCard
            return lessValuableCard();
        }

        // else, if human's card has points
        if (hCard.getPoints() > 0) {
            // if bot has more valuable cards with same type
            if (hasMoreValuableCardsSameType(hCard)) {
                // win the play
                return mostValuableCardSameType(hCard.getType());
            }

            // else, if bot has trump cards
            if (hasTrumpCards()) {
                // throw lessValuableCard
                return lessValuableCardTrump();
            }
        }

        // else, if card doesn't have points
        if (hCard.getPoints() == 0) {
            String type = hCard.getType();
            // find most valuable card with same type
            Card mostVal = mostValuableCardSameType(type);

            // check if bot can win the play
            for (Card card : getInHandCards()) {
                // if cards have same type and if bot's card has points > 0 and if card is the most valuable
                if (type.equals(card.getType()) && card.getPoints() > 0 && card.equals(mostVal)) {
                    // win the play
                    return card;
                }
            }
        }

        // else, throw lessValuableCard
        return lessValuableCard();
    }

    // returns true if has cards with trump
    private boolean hasTrumpCards() {
        for (Card card : getInHandCards()) {
            if (card.isTrump()) return true;
        }
        return false;
    }

    // returns a card with the same type as the param
    private Card sameCardType(String type) {
        for (Card card : getInHandCards()) {
            if (type.equals(card.getType())) return card;
        }
        return lessValuableCard();
    }

    // returns a card with trump
    private Card trumpCard() {
        for (Card card : getInHandCards()) {
            if (card.isTrump()) return card;
        }

        // if there is not trump cards returns lessValuableCard
        return lessValuableCard();
    }

    // returns the less valuable card
    private Card lessValuableCard() {
        // first, check if has 2 trump cards and other one isn't
        int trumpCount = 0;
        for (Card card : getInHandCards()) if (card.isTrump()) trumpCount++;

        if (trumpCount == 2) {
            // if the possible card to be thrown has less than 10 points and is not trump
            for (Card card : getInHandCards()) {
                if (card.getPoints() < 10 && !card.isTrump()) return card;
            }
            return lessValuableCardTrump();
        }

        // if not, returns less valuable card
        Card lessVal = getInHandCards().get(0);
        for (Card card : getInHandCards()) {
            // if "lessValuable" card value is greater than "card" value
            if (lessVal.getValue() > card.getValue()) {
                // less valuable card is "card"
                lessVal = card;
            }
        }
        return lessVal;
    }

    // returns the less valuable card with trump
    private Card lessValuableCardTrump() {
        Card lessVal = trumpCard();
        for (Card card : getInHandCards()) {
            if (lessVal.getValue() > card.getValue() && card.isTrump()) {
                lessVal = card;
            }
        }
        return lessVal;
    }

    // returns most valuable card with same card type
    private Card mostValuableCardSameType(String type) {
        Card mostVal = sameCardType(type);
        for (Card card : getInHandCards()) {
            // if card's value is greater than mostVal and both have same card type as parsed type
            if (mostVal.getValue() < card.getValue() && mostVal.getType().equals(type) && card.getType().equals(type)) {
                // most valuable card is "card"
                mostVal = card;
            }
        }
        return mostVal;
    }

    // returns true if has cards more value than the param card within same card type
    private boolean hasMoreValuableCardsSameType(Card hCard) {
        for (Card card : getInHandCards()) {
            // if card's value is greater than human's thrown card and have same card type
            if (card.getValue() > hCard.getValue() && card.getType().equals(hCard.getType())) {
                return true;
            }
        }
        return false;
    }
}
