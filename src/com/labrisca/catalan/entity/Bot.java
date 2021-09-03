package com.labrisca.catalan.entity;

import com.labrisca.catalan.Card;
import com.labrisca.catalan.Game;
import com.labrisca.catalan.util.Color;
import com.labrisca.catalan.util.Str;

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
            System.out.println("El " + getName() + " ha agafat: " + Color.name(card.getName()));
            System.out.print(Color.ANSI_RESET);
        }
    }

    @Override
    public void printCardsInHand() {
        if (getGame().getGameMode().equals("hacker")) {
            System.out.print(Color.ANSI_PURPLE);
            System.out.println("\n[?] Cartes a la mà del " + getName() + ":");

            for (int i = 0; i < getInHandCards().size(); i++) {
                String cardName = Str.upperFirstChar(getInHandCards().get(i).getName());
                System.out.print(Color.ANSI_PURPLE);
                System.out.print((i + 1) + ") " + Color.name(cardName));
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
