package com.labrisca.english.entities;

import com.labrisca.english.Card;
import com.labrisca.english.Color;
import com.labrisca.english.Game;

public class Human extends Player {
    // constructor
    public Human(String name, Game game) {
        super(name, game);
    }

    @Override
    public void takeCard() {
        // exit method if there aren't cards in the deck
        if (getGame().deckIsEmpty()) return;

        // else, take a random card
        Card card = topDeckCard();
        getInHandCards().add(card);

        System.out.println(getName() + " took -> " + Color.name(card.getName(), false));
    }

    @Override
    public void printCardsInHand() {
        System.out.println("\n[?] " + getName() + "'s turn. In-hand cards:");

        for (int i = 0; i < getInHandCards().size(); i++) {
            String cardName = Game.capitalizeStr(getInHandCards().get(i).getName());
            System.out.println((i + 1) + ") " + Color.name(cardName, false));
        }
    }

    @Override
    public void throwCard(int round) {
        // print available in-hand cards to be thrown
        printCardsInHand();

        // ask which card must be thrown or / and change latest card
        String input;
        label:
        while (true) {
            System.out.print("> ");
            input = Game.getSc().nextLine().trim();

            // check if player wants and can change latest card
            if (input.equals("7") && canChangeLastCard()) {
                changeLastCard();
                printCardsInHand();
            } else if (input.equals("7") && !canChangeLastCard()) {
                System.out.println("You can't change latest card...");
            } else {
                switch (getInHandCards().size()) {
                    case 3:
                        if (input.equals("1") || input.equals("2") || input.equals("3")) break label;
                        break;
                    case 2:
                        if (input.equals("1") || input.equals("2")) break label;
                        break;
                    case 1:
                        if (input.equals("1")) break label;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected inHandCards size");
                }
                System.out.println("Input a valid card number...");
            }
        }

        // call common method used by Human and Bot classes with the user's chosen card
        commonThrowCard(getInHandCards().get(Integer.parseInt(input) - 1), round);
    }
}