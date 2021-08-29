package com.labrisca.catalan.entities;

import com.labrisca.catalan.Card;
import com.labrisca.catalan.Color;
import com.labrisca.catalan.Game;

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

        System.out.println("Has agafat: " + Color.name(card.getName()));
    }

    @Override
    public void printCardsInHand() {
        System.out.println("\n[?] Et toca tirar. Les teves cartes:");

        for (int i = 0; i < getInHandCards().size(); i++) {
            String cardName = Game.capitalizeStr(getInHandCards().get(i).getName());
            System.out.println((i + 1) + ") " + Color.name(cardName));
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
                System.out.println("No pots canviar l'última carta...");
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
                System.out.println("Introdueix un número vàlid...");
            }
        }

        // call common method used by Human and Bot classes with the user's chosen card
        commonThrowCard(getInHandCards().get(Integer.parseInt(input) - 1), round);
    }
}
