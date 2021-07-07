package com.labrisca;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    // variables
    private final String name;
    private final boolean bot; // know if the player is a bot
    private final List<Card> handCards; // cards that the player has in his hands, available to be thrown
    private final List<Card> wonCards;
    private final Game game;

    // getters
    public String getName() {
        return name;
    }
    public List<Card> getHandCards() {
        return handCards;
    }
    public List<Card> getWonCards() {
        return wonCards;
    }

    // constructor
    public Player(String name, boolean bot, Game game) {
        this.name = name;
        this.bot = bot;
        this.handCards = new ArrayList<>();
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
                handCards.add(card);

                // if hacker mode is enabled or player is not bot
                if (game.isHacker() || !bot) {
                    String s = "Player " + name + " took -> " + Card.colorizeName(card.getName());
                    // if is bot, print purple colorized taken card
                    if (bot) {
                        System.out.println(Color.ANSI_PURPLE + s + Color.ANSI_PURPLE + "." + Color.ANSI_RESET);
                    }

                    // else, print the given card to the human player
                    else {
                        System.out.println(s + ".");
                    }
                }
                break;
            }
        }
    }

    // print available cards in hand to be thrown
    public void printHandCards() {
        // if bot and hacker are true
        if (bot && game.isHacker()) {
            // show bot hand cards
            System.out.println("\n" + Color.ANSI_PURPLE + "[?] Bot's hand cards:");
            for (int i = 0; i < handCards.size(); i++) {
                String s = Card.colorizeName(handCards.get(i).getName());
                // + 1 to make human readable nums
                System.out.println(Color.ANSI_PURPLE + (i + 1) + ") " + s + Color.ANSI_RESET);
            }
        }

        // else, means its human
        else {
            // show human hand cards
            System.out.println("\n[?] Your turn. Hand cards (triumph -> " + game.getTriumph() + "):");
            for (int i = 0; i < handCards.size(); i++) {
                String s = Card.colorizeName(handCards.get(i).getName());
                System.out.println((i + 1) + ") " + s);
            }
        }
    }

    // throw a card
    public void throwCard() {
        // exit method if player doesn't have cards in hands
        if (handCards.isEmpty()) return;

        // else, has cards in hands
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
        handCards.remove(card);
        game.getThePlay().add(card);
    }

    // throw a card by a bot
    private Card throwCardBot() {
        // show hand cards if game is hacker
        if (game.isHacker()) printHandCards();

        Card card;
        // if bot is ai - todo: bot is ai o potser la partida es ai?
        if (game.isAi()) {
            // assign AI thought of which card to throw
            card = new AI(this, game).throwCard();
        } else {
            // assign random card
            card = handCards.get(ThreadLocalRandom.current().nextInt(0, handCards.size()));
        }

        System.out.println("\n[!] Bot has thrown -> " + Card.colorizeName(card.getName()) + ".");
        return card;
    }

    // throw a card by a human
    private Card throwCardHuman() {
        // print available cards in hand to be thrown
        printHandCards();

        // ask which card must be thrown
        Scanner sc = new Scanner(System.in);
        String input;
        label:
        while (true) {
            System.out.print("> ");
            input = sc.nextLine().trim().toLowerCase();
            switch (handCards.size()) {
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
                    throw new IllegalStateException("Unexpected value: " + handCards.size());
            }
            System.out.println("Input a valid card number...");
        }

        // assign user's card choice to the card will be thrown
        Card card = handCards.get(Integer.parseInt(input) - 1);
        System.out.println("\n[!] You thrown -> " + Card.colorizeName(card.getName()) + ".");
        return card;
    }

    // returns the obtained points
    public int getPoints() {
        int count = 0;
        for (Card card : wonCards) count += card.getPoints();
        return count;
    }

    // print obtained points
    public void printPoints() {
        System.out.println("Points " + name + ": " + getPoints());
    }
}
