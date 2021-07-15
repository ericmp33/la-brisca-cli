package com.labrisca.entities;

import com.labrisca.Card;
import com.labrisca.Color;
import com.labrisca.Game;

import java.util.concurrent.ThreadLocalRandom;

public class Bot extends Player {
    // constructor
    public Bot(String name, Game game) {
        super(name, game);
    }

    @Override
    public void takeCard() {
        // exit method if there is not cards in the deck
        if (!getGame().deckHasCards()) return;

        // else
        while (true) {
            // randomly take a card
            int rand = ThreadLocalRandom.current().nextInt(0, getGame().getDeck().size());
            Card card = getGame().getDeck().get(rand);

            // if card hasn't been taken and isn't latest or is latest and how many cards are left to take is 1
            if (!card.isTaken() && !card.isLatest() || card.isLatest() && getGame().howManyLeftToTake() == 1) {
                // take it
                card.setTaken(true);
                getInHandCards().add(card);

                // if hacker mode is enabled
                if (getGame().isHacker()) {
                    // print purple colorized taken card
                    System.out.print(Color.ANSI_PURPLE);
                    System.out.println(getName() + " took -> " + Color.colorizeNamePurple(card.getName()));
                }

                System.out.print(Color.ANSI_RESET);
                break;
            }
        }
    }

    @Override
    public void printCardsInHand() {
        if (getGame().isHacker()) {
            System.out.print(Color.ANSI_PURPLE);

            System.out.println("\n[?] " + getName() + "'s turn. In-hand cards:");
            for (int i = 0; i < getInHandCards().size(); i++) {
                String cardName = Game.capitalizeStr(getInHandCards().get(i).getName());

                System.out.print(Color.ANSI_PURPLE);
                System.out.print((i + 1) + ") " + Color.colorizeNamePurple(cardName));
                System.out.println(Color.ANSI_RESET);
            }
        }
    }

    @Override
    public void throwCard() {
        // if game is hacker show bot's in-hand cards
        if (getGame().isHacker()) printCardsInHand();

        Card card;
        // if bot's AI is on
        if (getGame().isAi()) {
            // change last card
            changeLastCard();

            // assign AI thought of which card has to be thrown
            card = new AI(this, getGame()).throwCard();
        } else {
            // assign random card
            card = getInHandCards().get(ThreadLocalRandom.current().nextInt(0, getInHandCards().size()));
        }

        System.out.println("\n[!] Bot thrown -> " + Color.colorizeName(card.getName()));

        // throw the card
        card.setThrownBy(this);

        // if the play is empty
        if (getGame().getThePlay().isEmpty()) {
            // card will be thrown first
            card.setThrownFirst(true);
        }

        // put the card to the play
        getInHandCards().remove(card);
        getGame().getThePlay().add(card);
    }
}
