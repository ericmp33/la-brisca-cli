package com.labrisca;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

public class Game {
    // variables
    private final List<Card> deck;
    private final List<Card> thePlay; // cards list that participate in the play
    private final List<Player> players;
    private final Instant startTime;
    private int round;
    private boolean hacker; // if true, human player can see bot cards
    private boolean ai; // if true, bot player is smart
    private static final Scanner sc = new Scanner(System.in);

    // getters
    public List<Card> getDeck() {
        return deck;
    }
    public List<Card> getThePlay() {
        return thePlay;
    }
    public boolean isHacker() {
        return hacker;
    }
    public boolean isAi() {
        return ai;
    }
    public static Scanner getSc() {
        return sc;
    }

    // constructor
    public Game() {
        // put cards into deck
        deck = List.of(
                // bastos
                new Card("as de basto", "bastos", 1, 12, 11),
                new Card("dos de basto", "bastos", 2, 1, 0),
                new Card("tres de basto", "bastos", 3, 11, 10),
                new Card("quatre de basto", "bastos", 4, 2, 0),
                new Card("cinc de basto", "bastos", 5, 3, 0),
                new Card("sis de basto", "bastos", 6, 4, 0),
                new Card("set de basto", "bastos", 7, 5, 0),
                new Card("vuit de basto", "bastos", 8, 6, 0),
                new Card("nou de basto", "bastos", 9, 7, 0),
                new Card("sota de basto", "bastos", 10, 8, 2),
                new Card("cavall de basto", "bastos", 11, 9, 3),
                new Card("rei de basto", "bastos", 12, 10, 4),

                // copes
                new Card("as de copa", "copes", 1, 12, 11),
                new Card("dos de copa", "copes", 2, 1, 0),
                new Card("tres de copa", "copes", 3, 11, 10),
                new Card("quatre de copa", "copes", 4, 2, 0),
                new Card("cinc de copa", "copes", 5, 3, 0),
                new Card("sis de copa", "copes", 6, 4, 0),
                new Card("set de copa", "copes", 7, 5, 0),
                new Card("vuit de copa", "copes", 8, 6, 0),
                new Card("nou de copa", "copes", 9, 7, 0),
                new Card("sota de copa", "copes", 10, 8, 2),
                new Card("cavall de copa", "copes", 11, 9, 3),
                new Card("rei de copa", "copes", 12, 10, 4),

                // espases
                new Card("as d'espasa", "espases", 1, 12, 11),
                new Card("dos d'espasa", "espases", 2, 1, 0),
                new Card("tres d'espasa", "espases", 3, 11, 10),
                new Card("quatre d'espasa", "espases", 4, 2, 0),
                new Card("cinc d'espasa", "espases", 5, 3, 0),
                new Card("sis d'espasa", "espases", 6, 4, 0),
                new Card("set d'espasa", "espases", 7, 5, 0),
                new Card("vuit d'espasa", "espases", 8, 6, 0),
                new Card("nou d'espasa", "espases", 9, 7, 0),
                new Card("sota d'espasa", "espases", 10, 8, 2),
                new Card("cavall d'espasa", "espases", 11, 9, 3),
                new Card("rei d'espasa", "espases", 12, 10, 4),

                // oros
                new Card("as d'oro", "oros", 1, 12, 11),
                new Card("dos d'oro", "oros", 2, 1, 0),
                new Card("tres d'oro", "oros", 3, 11, 10),
                new Card("quatre d'oro", "oros", 4, 2, 0),
                new Card("cinc d'oro", "oros", 5, 3, 0),
                new Card("sis d'oro", "oros", 6, 4, 0),
                new Card("set d'oro", "oros", 7, 5, 0),
                new Card("vuit d'oro", "oros", 8, 6, 0),
                new Card("nou d'oro", "oros", 9, 7, 0),
                new Card("sota d'oro", "oros", 10, 8, 2),
                new Card("cavall d'oro", "oros", 11, 9, 3),
                new Card("rei d'oro", "oros", 12, 10, 4)
        );

        // initialize the play and list of players
        thePlay = new ArrayList<>();
        players = new ArrayList<>();

        // save game's start time
        startTime = Instant.now();
    }

    // input a player to the game
    private void inputPlayer(Player p) {
        players.add(p);
    }

    // welcome message
    public void welcomeMessage() {
        System.out.println("Welcome to \"La Brisca\" CLI cards game\nMade by @ericmp with <3, june 2021\nShuffling cards...\nCards shuffled! Let the game begin!\n");
    }

    // ask if enable hacker mode
    private void enableHacker() {
        System.out.println("[?] Choose gamemode: normal or hacker");
        while (true) {
            System.out.print("> ");
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equalsIgnoreCase("normal")) {
                System.out.println("Normal mode on!");
                this.hacker = false;
                break;
            } else if (s.equalsIgnoreCase("hacker")) {
                System.out.println("Hacker mode on!\n" + Color.ANSI_PURPLE + "Purple " + Color.ANSI_RESET + "text = text you wouldn't see ;)");
                this.hacker = true;
                break;
            }
            System.out.println("Input \"normal\" or \"hacker\"...");
        }
        System.out.println();
    }

    // ask if enable AI bot mode
    private void enableAIBot() {
        System.out.println("[?] Do you want to make the bot smart?");
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equalsIgnoreCase("yes")) {
                System.out.println("Lets see if you can win!");
                this.ai = true;
                break;
            } else if (input.equalsIgnoreCase("no")) {
                System.out.println("Beep beep...");
                this.ai = false;
                break;
            }
            System.out.println("Input \"yes\" or \"no\"...");
        }
        System.out.println();
    }

    // ask if print cards final information
    private void printFinalCardsInfo() {
        System.out.println("\n[?] Do you want to see the cards final information?");
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                System.out.println();
                printAllCards();
                break;
            } else if (input.equals("no")) {
                break;
            }
            System.out.println("Input \"yes\" or \"no\"...");
        }
        System.out.println();
    }

    // choose who starts the game
    private void whoStarts() {
        // if random number is 0
        if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
            // will start player with index 1, so move it to index 0 (1st pos)
            Player temp = players.get(1);
            players.remove(1);
            players.add(0, temp);
        }
        // else, will start other player (is actually at index 0)
    }

    // deal 3 first cards to each player
    private void deal3FirstCards() {
        System.out.println("Dealing three first cards to each player:");
        // for each player, take 3 cards
        for (Player p : players) {
            for (int i = 0; i < 3; i++) p.takeCard();

            // if hacker mode is on, make a print (more friendly look)
            if (hacker) System.out.println();
        }

        // else, wait till it finishes iteration and print it now
        if (!hacker) System.out.println();
    }

    // set triumph
    private void setTriumph() {
        String triumph;

        while (true) {
            // assign random card
            Card card = deck.get(ThreadLocalRandom.current().nextInt(0, deck.size()));

            // if card hasn't been taken
            if (!card.isTaken()) {
                // set it will be latest one
                card.setLatest(true);

                // set game's triumph
                triumph = card.getType();

                String firstLetter = Card.colorizeName(card.getName()).substring(0, 1).toUpperCase();
                System.out.println(firstLetter + Card.colorizeName(card.getName()).substring(1) + " appeared");
                System.out.println("So.. triumph is " + Card.colorizeType(triumph) + "!");
                break;
            }
        }

        // set triumph to needed cards
        for (Card card : deck) {
            // if the card type has the triumph
            if (card.getType().equals(triumph)) {
                card.setTriumph(true);

                // increase triumph cards value
                card.setValue(card.getValue() + 100);
            }
        }
    }

    // print all cards and their attributes
    private void printAllCards() {
        System.out.println("Information of all the cards:");
        for (Card card : deck) card.printAttributes();
    }

    // game's main loop
    private void mainLoop() {
        // print who will start the game
        System.out.println(players.get(0).getName() + " will start the game\n");

        round = 0;
        while (true) {
            System.out.println("-----------------------------------------------");
            round++;
            System.out.println("Round " + round + " (triumph -> " + Card.colorizeName(latestCard().getName()) + ")");

            // for each player, throw a card
            for (Player p : players) p.throwCard();

            // validate and set who wins the play and saves the cards
            validateThePlay();

            // for each player, take a card
            for (Player p : players) p.takeCard();

            // if no player has cards, game finishes
            if (playersWithoutCards() == players.size()) break;

            // else
            System.out.println();

            // if hacker mode is on
            if (hacker) {
                System.out.print(Color.ANSI_PURPLE);
                // for each player, print obtained points and cards won
                for (Player p : players) p.printPoints();
                System.out.println();
                for (Player p : players) System.out.println("Won cards " + p.getName() + ": " + p.getWonCards().size());
                System.out.print(Color.ANSI_RESET);
            }
        }
    }

    // returns how many players doesn't have cards
    private int playersWithoutCards() {
        int count = 0;
        for (Player p : players) {
            if (p.getHandCards().isEmpty()) count++;
        }
        return count;
    }

    // print total points and cards won
    private void printPointsAndCards() {
        for (Player p : players) p.printPoints();
        int totalPoints = players.get(0).getPoints() + players.get(1).getPoints();
        System.out.println("Total points: " + totalPoints + "\n");

        for (Player p : players) System.out.println("Won cards " + p.getName() + ": " + p.getWonCards().size());
        int totalCards = players.get(0).getWonCards().size() + players.get(1).getWonCards().size();
        System.out.println("Total cards: " + totalCards + "\n");
    }

    // print game's winner
    private void printWinner() {
        int temp = 0;
        Player winner = players.get(0);
        boolean draw = false;
        for (Player p : players) {
            int maxPoints = p.getPoints();

            if (maxPoints > temp) {
                temp = maxPoints;
                winner = p;
            } else if (maxPoints == temp) {
                draw = true;
                break;
            }
        }

        String ln = "######---------------------------------------######";
        String s;

        if (draw) s = "Draw! Both players got 60 points!";
        else s = winner.getName() + " won the game with " + winner.getPoints() + " points!!!";

        System.out.println(ln + "\n" + centerStr(ln.length(), s) + "\n" + ln);
    }

    // returns centered String inside lines
    private String centerStr(int lnLength, String s) {
        int i = (lnLength - s.length()) / 2;
        return " ".repeat(i) + s;
    }

    // returns latest card
    Card latestCard() {
        for (Card card : deck) {
            if (card.isLatest()) return card;
        }
        return deck.get(0);
    }

    // print how much the game took to finish
    private void printGameTime() {
        // save how much the game took to finish
        Duration gameTime = Duration.between(startTime, Instant.now());

        int seconds = Integer.parseInt(String.valueOf(gameTime.toSeconds()));
        String sentence = "\nThe game has lasted exactly ";

        // if seconds are greater than 0 and less than 60
        if (seconds > 0 && seconds < 60) {
            sentence += seconds + " seconds";
        }

        // else, if seconds are 60
        else if (seconds == 60) {
            sentence += "1 minute";
        }

        // else, if the game took more than 1 minute and less than 1 hour
        else if (seconds > 60 && seconds < 3600) {
            int minutes = seconds / 60;
            seconds %= 60;

            // if else to well-print
            if (minutes == 1) sentence += "1 minute and " + seconds + " seconds";
            else if (seconds == 1) sentence += minutes + " minutes and 1 second";
            else if (seconds == 0) sentence += minutes + " minutes";
            else sentence += minutes + " minutes and " + seconds + " seconds";
        }

        sentence += "!!";
        System.out.println(sentence);
    }

    // returns true if deck has cards
    boolean deckHasCards() {
        for (Card card : deck) {
            // if card is not taken, deck still has cards
            if (!card.isTaken()) return true;
        }
        // else, it doesn't
        return false;
    }

    // returns how many cards are left to take
    int howManyLeftToTake() {
        int count = 0;
        for (Card card : deck) {
            // increment counter if card is not taken
            if (!card.isTaken()) count++;
        }
        return count;
    }

    // print game's author
    public void printAuthor() {
        System.out.println("\"La Brisca\" CLI cards game - by @ericmp with <3, june 2021.");
    }

    // validate the play and set who wins it and collects the play's cards
    private void validateThePlay() {
        // validate possible options and assign return to variable
        Player playWinner = validateOptions(thePlay.get(0), thePlay.get(1));

        // the play's winner collects the cards
        for (Card card : thePlay) {
            playWinner.getWonCards().add(card);
            card.setTakenBy(playWinner);
        }

        // remove the cards from the play
        this.thePlay.clear();

        // print who won the play
        System.out.println("\n" + playWinner.getName() + " won the play."); // todo: print it with red and green?

        // well-print it
        if (round < 22 || round == 24) System.out.println();

        // if player on index 0 is not the same as the winner, means it is not at 1st post
        if (!players.get(0).equals(playWinner)) {
            // so, put it at index 0 (1st pos) to be the first to throw in next round
            Player temp = players.get(1);
            players.remove(1);
            players.add(0, temp);
        }
    }

    // returns the player who thrown the play's winner card
    private Player validateOptions(Card card0, Card card1) {
        return validateOptionsCard(card0, card1).getThrownBy();
    }

    // returns which card wins the play
    private Card validateOptionsCard(Card card0, Card card1) {
        // if one of the cards is triumph and other isn't
        if (card0.isTriumph() && !card1.isTriumph() || card1.isTriumph() && !card0.isTriumph()) {
            // triumph card wins
            return card0.isTriumph() ? card0 : card1;
        }

        // if both cards have same type
        if (card0.getType().equals(card1.getType())) {
            // the most valuable card wins
            return card0.getValue() > card1.getValue() ? card0 : card1;
        }

        // else, both aren't triumph and have different type
        else {
            // the thrown first wins
            return card0.isThrownFirst() ? card0 : card1;
        }
    }

    // principal method to run the game
    public void run(Player human, Player bot) {
        // input players into game
        inputPlayer(human);
        inputPlayer(bot);

        // welcome message
        welcomeMessage();

        // ask if enable hacker mode
        enableHacker();

        // ask if enable AI bot mode
        enableAIBot();

        // choose who starts the game
        whoStarts();

        // deal 3 first cards to each player
        deal3FirstCards();

        // set triumph
        setTriumph();

        // game's main loop
        mainLoop();

        // print total points and cards won
        printPointsAndCards();

        // print game's winner
        printWinner();

        // print how much the game took to finish
        printGameTime();

        // ask if print cards final information
        printFinalCardsInfo();

        // print game's author
        printAuthor();
    }
}
