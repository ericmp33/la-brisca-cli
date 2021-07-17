package catalan.entities;

import catalan.Card;
import catalan.Color;
import catalan.Game;

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

        // else, take a random card
        Card card = randCard();

        card.setTaken(true);
        getInHandCards().add(card);

        // if hacker mode is enabled
        if (getGame().isHacker()) {
            // print purple colorized taken card
            System.out.print(Color.ANSI_PURPLE);
            System.out.println("El " + getName() + " ha agafat: " + Color.acolorirNom(card.getName()));
            System.out.print(Color.ANSI_RESET);
        }
    }

    @Override
    public void printCardsInHand() {
        if (getGame().isHacker()) {
            System.out.print(Color.ANSI_PURPLE);
            System.out.println("\n[?] Cartes a la mà del " + getName() + ":");

            for (int i = 0; i < getInHandCards().size(); i++) {
                String cardName = Game.capitalizeStr(getInHandCards().get(i).getName());

                System.out.print(Color.ANSI_PURPLE);
                System.out.print((i + 1) + ") " + Color.acolorirNom(cardName));
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
            // try to change last card
            changeLastCard();

            // assign AI thought of which card has to be thrown
            card = new AI(this, getGame()).throwCard();
        } else {
            // assign random card
            card = getInHandCards().get(ThreadLocalRandom.current().nextInt(0, getInHandCards().size()));
        }

        commonThrowCard(card);
    }
}
