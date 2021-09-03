package com.labrisca.english.entity;

import com.labrisca.english.Card;
import com.labrisca.english.Game;
import com.labrisca.english.util.Color;
import com.labrisca.english.util.Str;

import java.util.concurrent.ThreadLocalRandom;

public class Bot extends Player {
    // constructor
    public Bot(Game game) {
        super("Bot", game);
    }

    @Override
    public void takeCard() {
        // exit method if there aren't cards in the deck
        if (getGame().deckIsEmpty()) return;

        // else, take a random card
        Card card = topDeckCard();
        getInHandCards().add(card);

        // if hacker mode is enabled
        if (getGame().getGameMode().equals("hacker")) {
            // print purple colorized taken card
            System.out.print(Color.ANSI_PURPLE);
            System.out.println(getName() + " took -> " + Color.name(card.getName(), true));
            System.out.print(Color.ANSI_RESET);
        }
    }

    @Override
    public void printCardsInHand() {
        if (getGame().getGameMode().equals("hacker")) {
            System.out.print(Color.ANSI_PURPLE);
            System.out.println("\n[?] " + getName() + "'s turn. In-hand cards:");

            for (int i = 0; i < getInHandCards().size(); i++) {
                String cardName = Str.upperFirstChar(getInHandCards().get(i).getName());
                System.out.print(Color.ANSI_PURPLE);
                System.out.print((i + 1) + ") " + Color.name(cardName, true));
                System.out.println(Color.ANSI_RESET);
            }
        }
    }

    @Override
    public void throwCard(int round) {
        // if gamemode is hacker show bot's in-hand cards
        if (getGame().getGameMode().equals("hacker")) printCardsInHand();

        // throw a random card
        Card card = getInHandCards().get(ThreadLocalRandom.current().nextInt(0, getInHandCards().size()));
        commonThrowCard(card, round);
    }
}
