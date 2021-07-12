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
    public String getName() { return name; }
    public boolean isBot() { return bot; }
    public List<Card> getInHandCards() { return inHandCards; }
    public List<Card> getWonCards() { return wonCards; }

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
                    String s = name + " took -> ";
                    // if is bot, print purple colorized taken card
                    if (bot) {
                        System.out.println(Color.ANSI_PURPLE + s + Color.colorizeNamePurple(card.getName()));
                    }

                    // else, print the given card to the human player
                    else {
                        System.out.println(s + Color.colorizeName(card.getName()));
                    }
                }
                System.out.print(Color.ANSI_RESET);
                break;
            }
        }
    }

    // print available in-hand cards to be thrown
    public void printCardsInHand() {
        // if bot and hacker are true
        if (bot && game.isHacker()) {
            // show bot in-hand cards
            System.out.println("\n" + Color.ANSI_PURPLE + "[?] Bot's in-hand cards:");
            for (int i = 0; i < inHandCards.size(); i++) {
                String s = Game.capitalizeStr(inHandCards.get(i).getName());
                // + 1 to make human readable nums
                System.out.println(Color.ANSI_PURPLE + (i + 1) + ") " + Color.colorizeNamePurple(s));
            }
            System.out.print(Color.ANSI_RESET);
        }

        // else, means its human
        else {
            // show human in-hands cards
            System.out.println("\n[?] Your turn. In-hand cards:");
            for (int i = 0; i < inHandCards.size(); i++) {
                String s = Game.capitalizeStr(inHandCards.get(i).getName());
                System.out.println((i + 1) + ") " + Color.colorizeName(s));
            }
        }
    }

    // throw a card
    public void throwCard() {
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
            // change last card
            changeLastCard();

            // assign AI thought of which card to be thrown
            card = new AI(this, game).throwCard();
        } else {
            // assign random card
            card = inHandCards.get(ThreadLocalRandom.current().nextInt(0, inHandCards.size()));
        }

        System.out.println("\n[!] Bot thrown -> " + Color.colorizeName(card.getName()));
        return card;
    }

    // throw a card by a human
    private Card throwCardHuman() {
        // print available in-hand cards to be thrown
        printCardsInHand();

        // ask which card must be thrown or / and change latest card
        String input;/*todo: uncomment to disable testing automation
        label:
        while (true) {
            System.out.print("> ");
            input = Game.getSc().nextLine().trim().toLowerCase();

            // check if player wants and can change latest card
            if (input.equals("7") && canChangeLastCard()) {
                changeLastCard();
                printCardsInHand();
            } else if (input.equals("7") && !canChangeLastCard()) {
                System.out.println("You can't change latest card...");
            } else {
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
                        throw new IllegalStateException("Unexpected inHandCards size");
                }
                System.out.println("Input a valid card number...");
            }
        }*/

        //todo delete this line if testing automation is disabled
        input = "1";

        // assign user's card choice to the card will be thrown
        Card card = inHandCards.get(Integer.parseInt(input) - 1);
        System.out.println("\n[!] " + name + " thrown -> " + Color.colorizeName(card.getName()));
        return card;
    }

    // returns the total obtained points
    public int getPoints() {
        int count = 0;
        for (Card card : wonCards) count += card.getPoints();
        return count;
    }

    // changes the last card for 7 of trump
    void changeLastCard() {
        // if can, change it
        if (canChangeLastCard()) {
            // unset the latest card
            Card theOne = game.latestCard();
            theOne.setLatest(false);
            theOne.setTaken(true);
            theOne.setTakenBy(this);

            // and save it in the hand of the player
            inHandCards.add(theOne);

            // remove the seven trump from the hand of the player
            Card oldSevenTrump = sevenTrump();
            inHandCards.remove(oldSevenTrump);

            // and set it as the new latest card
            Card newSevenTrump = game.getDeck().get(game.posCard(oldSevenTrump));
            newSevenTrump.setLatest(true);
            newSevenTrump.setTaken(false);
            newSevenTrump.setTakenBy(null);

            System.out.println("\n[!] " + name + " changed latest card");
        }
    }

    // returns if player can change latest card
    private boolean canChangeLastCard() {
        // if player has 7 of the trump and its value is less than it and round is less than 21
        return has7Trump() && sevenTrump().getValue() < game.latestCard().getValue() && game.getRound() < 21;
    }

    // returns if player has 7 of trump
    private boolean has7Trump() {
        for (Card card : inHandCards) {
            if (card.getValue() == 105) return true;
        }
        return false;
    }

    // returns 7 of trump
    private Card sevenTrump() {
        for (Card card : inHandCards) {
            if (card.getValue() == 105) return card;
        }
        return sevenTrump();
    }
}
