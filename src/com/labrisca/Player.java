package com.labrisca;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    // variables
    private final String name;
    private final boolean bot; // know if the player is a bot
    private final List<Card> inHandCards; // cards available to be thrown
    private final List<Card> wonCards;
    private final Game game;

    // getters
    public String getName() {
        return name;
    }
    public List<Card> getInHandCards() {
        return inHandCards;
    }
    public List<Card> getWonCards() {
        return wonCards;
    }

    // constructor
    public Player(String name, boolean bot, Game game) {
        this.name = name;
        this.bot = bot;
        this.inHandCards = new ArrayList<>();
        this.wonCards = new ArrayList<>();
        this.game = game;
    }

    // take a card
    public void takeCard() {
        // exit method if there is not cards in the deck
        if (!game.deckHasCards()) return;

        // else
        while (true) {
            // randomly take a card
            Card card = game.getDeck().get(ThreadLocalRandom.current().nextInt(0, game.getDeck().size()));

            // if card hasn't been taken and isn't latest or is latest and how many cards are left to take is 1
            if (!card.isTaken() && !card.isLatest() || card.isLatest() && game.howManyLeftToTake() == 1) {
                // take it
                card.setTaken(true);
                inHandCards.add(card);

                // if hacker mode is enabled or player is not bot
                if (game.isHacker() || !bot) {
                    String s = name + " took -> " + Card.colorizeName(card.getName());
                    // if is bot, print purple colorized taken card
                    if (bot) {
                        System.out.println(Color.ANSI_PURPLE + s + Color.ANSI_PURPLE + Color.ANSI_RESET);
                    }

                    // else, print the given card to the human player
                    else {
                        System.out.println(s);
                    }
                }
                break;
            }
        }
    }

    // print available cards in hand to be thrown
    public void printCardsInHand() {
        // if bot and hacker are true
        if (bot && game.isHacker()) {
            // show bot in-hand cards
            System.out.println("\n" + Color.ANSI_PURPLE + "[?] Bot's cards in hand:");
            for (int i = 0; i < inHandCards.size(); i++) {
                String s = Card.colorizeName(inHandCards.get(i).getName());
                // + 1 to make human readable nums
                System.out.println(Color.ANSI_PURPLE + (i + 1) + ") " + Game.capitalizeStr(s) + Color.ANSI_RESET);
            }
        }

        // else, means its human
        else {
            // show human in-hands cards
            System.out.println("\n[?] Your turn. In-hand cards:");
            for (int i = 0; i < inHandCards.size(); i++) {
                String s = Card.colorizeName(inHandCards.get(i).getName());
                System.out.println((i + 1) + ") " + Game.capitalizeStr(s));
            }
        }
    }

    // throw a card
    public void throwCard() {
        // exit method if player doesn't have in-hand cards
        if (inHandCards.isEmpty()) return;

        // else, has in-hand cards
        Card card;

        // if is bot, assign return of throwCardBot to "card"
        if (bot) card = throwCardBot();

        // else, is human
        else card = throwCardHuman();

        // throw the card
        card.setThrown(true);
        card.setThrownBy(this);

        // if the play is empty
        if (game.getThePlay().isEmpty()) {
            // card has been thrown first
            card.setThrownFirst(true);
        }

        // move the card to the play
        inHandCards.remove(card);
        game.getThePlay().add(card);
    }

    // throw a card by a bot
    private Card throwCardBot() {
        // if game is hacker show bot's in-hand cards
        if (game.isHacker()) printCardsInHand();

        Card card;
        // if bot's AI is on
        if (game.isAi()) {
            // assign AI thought of which card to be thrown
            card = new AI(this, game).throwCard();
        } else {
            // assign random card
            card = inHandCards.get(ThreadLocalRandom.current().nextInt(0, inHandCards.size()));
        }

        System.out.println("\n[!] Bot has thrown -> " + Card.colorizeName(card.getName()));
        return card;
    }

    // throw a card by a human
    private Card throwCardHuman() {
        // print available in-hand cards to be thrown
        printCardsInHand();

        // ask which card must be thrown
        String input;
        label:
        while (true) {
            System.out.print("> ");
            input = Game.getSc().nextLine().trim().toLowerCase();
            switch (inHandCards.size()) {
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
                    throw new IllegalStateException("Unexpected value: " + inHandCards.size());
            }
            System.out.println("Input a valid card number...");
        }

        // assign user's card choice to the card will be thrown
        Card card = inHandCards.get(Integer.parseInt(input) - 1);
        System.out.println("\n[!] You thrown -> " + Card.colorizeName(card.getName()));
        return card;
    }

    // returns the obtained points
    public int getPoints() {
        int count = 0;
        for (Card card : wonCards) count += card.getPoints();
        return count;
    }
}
