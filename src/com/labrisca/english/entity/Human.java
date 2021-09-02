package com.labrisca.english.entity;

import com.labrisca.english.io.UserInput;
import com.labrisca.english.Card;
import com.labrisca.english.util.Color;
import com.labrisca.english.Game;
import com.labrisca.english.util.Str;

public class Human extends Player {
    // constructor
    public Human(String name, Game game) {
        super(name, game);
    }

    @Override
    public void takeCard() {
        // exit method if there aren't cards in the deck
        if (getGame().deckIsEmpty()) return;

        // else, take top deck card
        Card card = topDeckCard();
        getInHandCards().add(card);
        System.out.println(getName() + " took -> " + Color.name(card.getName(), false));
    }

    @Override
    public void printCardsInHand() {
        System.out.println("\n[?] " + getName() + "'s turn. In-hand cards:");
        for (int i = 0; i < getInHandCards().size(); i++) {
            String cardName = Str.upperFirstChar(getInHandCards().get(i).getName());
            System.out.println((i + 1) + ") " + Color.name(cardName, false));
        }
    }

    @Override
    public void throwCard(int round) {
        // print available in-hand cards to be thrown
        printCardsInHand();

        // ask which card must be thrown or / and change latest card
        String input = UserInput.askThrowCard(this);

        // call common method used by Human and Bot classes with user's chosen card
        commonThrowCard(getInHandCards().get(Integer.parseInt(input) - 1), round);
    }
}
