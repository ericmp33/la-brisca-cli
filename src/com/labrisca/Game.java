package com.labrisca;

import com.labrisca.entities.Player;

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
    private boolean hacker; // if human player can see bot cards
    private boolean ai; // if bot player is smart
    private static final Scanner sc = new Scanner(System.in);

    // getters
    public List<Card> getDeck() { return deck; }
    public List<Card> getThePlay() { return thePlay; }
    public int getRound() { return round; }
    public boolean isHacker() { return hacker; }
    public boolean isAi() { return ai; }
    public static Scanner getSc() { return sc; }

    // constructor
    public Game() {
        // put cards into deck
        deck = List.of(
                new Card("club's ace", "club", 1, 12, 11),
                new Card("club's two", "club", 2, 1, 0),
                new Card("club's trey", "club", 3, 11, 10),
                new Card("club's four", "club", 4, 2, 0),
                new Card("club's five", "club", 5, 3, 0),
                new Card("club's six", "club", 6, 4, 0),
                new Card("club's seven", "club", 7, 5, 0),
                new Card("club's eight", "club", 8, 6, 0),
                new Card("club's nine", "club", 9, 7, 0),
                new Card("club's jack", "club", 10, 8, 2),
                new Card("club's knight", "club", 11, 9, 3),
                new Card("club's king", "club", 12, 10, 4),

                new Card("cup's ace", "cup", 1, 12, 11),
                new Card("cup's two", "cup", 2, 1, 0),
                new Card("cup's trey", "cup", 3, 11, 10),
                new Card("cup's four", "cup", 4, 2, 0),
                new Card("cup's five", "cup", 5, 3, 0),
                new Card("cup's six", "cup", 6, 4, 0),
                new Card("cup's seven", "cup", 7, 5, 0),
                new Card("cup's eight", "cup", 8, 6, 0),
                new Card("cup's nine", "cup", 9, 7, 0),
                new Card("cup's jack", "cup", 10, 8, 2),
                new Card("cup's knight", "cup", 11, 9, 3),
                new Card("cup's king", "cup", 12, 10, 4),

                new Card("sword's ace", "sword", 1, 12, 11),
                new Card("sword's two", "sword", 2, 1, 0),
                new Card("sword's trey", "sword", 3, 11, 10),
                new Card("sword's four", "sword", 4, 2, 0),
                new Card("sword's five", "sword", 5, 3, 0),
                new Card("sword's six", "sword", 6, 4, 0),
                new Card("sword's seven", "sword", 7, 5, 0),
                new Card("sword's eight", "sword", 8, 6, 0),
                new Card("sword's nine", "sword", 9, 7, 0),
                new Card("sword's jack", "sword", 10, 8, 2),
                new Card("sword's knight", "sword", 11, 9, 3),
                new Card("sword's king", "sword", 12, 10, 4),

                new Card("coin's ace", "coin", 1, 12, 11),
                new Card("coin's two", "coin", 2, 1, 0),
                new Card("coin's trey", "coin", 3, 11, 10),
                new Card("coin's four", "coin", 4, 2, 0),
                new Card("coin's five", "coin", 5, 3, 0),
                new Card("coin's six", "coin", 6, 4, 0),
                new Card("coin's seven", "coin", 7, 5, 0),
                new Card("coin's eight", "coin", 8, 6, 0),
                new Card("coin's nine", "coin", 9, 7, 0),
                new Card("coin's jack", "coin", 10, 8, 2),
                new Card("coin's knight", "coin", 11, 9, 3),
                new Card("coin's king", "coin", 12, 10, 4)
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
        System.out.println("Welcome to \"La Brisca\" CLI cards game");
        System.out.println("Made by @ericmp33 with <3, june 2021");
        System.out.println("Shuffling cards...");
        System.out.println("Cards shuffled! Let the game begin!");
        System.out.println("Change the latest card inputting \"7\"");
        System.out.println();
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
                System.out.println("Hacker mode on!");
                System.out.print(Color.ANSI_PURPLE + "Purple " + Color.ANSI_RESET);
                System.out.println("text = text you wouldn't see ;)");
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
    private void askPrintCardsInfo() {
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
            // will start player with index 1, so move player to 1st pos
            Player temp = players.get(1);
            players.remove(1);
            players.add(0, temp);
        }
        // else, will start other player (at index 0)
    }

    // deal 3 first cards to each player
    private void deal3FirstCards() {
        System.out.println("Dealing 3 first cards to each player:");
        // for each player, take 3 cards
        for (Player p : players) {
            for (int i = 0; i < 3; i++) p.takeCard();

            // if hacker mode is on, make a print (more friendly look)
            if (hacker) System.out.println();
        }

        // else, wait till it finishes iteration and print it now
        if (!hacker) System.out.println();
    }

    // set trump
    private void setTrump() {
        String trump;

        while (true) {
            // assign random card
            Card card = deck.get(ThreadLocalRandom.current().nextInt(0, deck.size()));

            // if card hasn't been taken
            if (!card.isTaken()) {
                // will be latest one
                card.setLatest(true);

                // set game's trump
                trump = card.getType();

                System.out.println(capitalizeStr(Color.colorizeName(capitalizeStr(card.getName())) + " appeared"));
                System.out.println("So.. trump is " + Color.colorizeType(trump) + "!");
                break;
            }
        }

        // set trump to cards
        for (Card card : deck) {
            // if the card type has the trump
            if (card.getType().equals(trump)) {
                card.setTrump(true);

                // increase trump cards value
                card.setValue(card.getValue() + 100);
            }
        }
    }

    public static String capitalizeStr(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    // print all cards and their attributes
    private void printAllCards() {
        System.out.println("Information of all the cards:");
        for (Card card : deck) card.printAttributes();
    }

    // game's main loop
    private void mainLoop() {
        System.out.println(players.get(0).getName() + " starts throwing");

        round = 0;
        while (true) {
            System.out.println("-----------------------------------------------");
            round++;
            System.out.println("Round " + round + " (trump -> " + Color.colorizeName(latestCard().getName()) + ")");

            // for each player, throw a card
            for (Player p : players) p.throwCard();

            // validate and set who wins the play and saves the cards
            validateThePlay();

            // if any player hasn't cards, game finishes
            if (playersWithoutCards() == players.size()) break;

            // else, for each player, take a card
            for (Player p : players) p.takeCard();

            // and if hacker mode is on
            if (hacker) {
                // for each player, print obtained points and cards won
                System.out.print("\n" + Color.ANSI_PURPLE);
                for (Player p : players) System.out.println(p.getName() + "'s points: " + p.getPoints());
                System.out.println();
                for (Player p : players) System.out.println(p.getName() + "'s won cards: " + p.getWonCards().size());
                System.out.print(Color.ANSI_RESET);
            }
        }
    }

    // returns count of players that doesn't have cards
    private int playersWithoutCards() {
        int count = 0;
        for (Player p : players) {
            if (p.getInHandCards().isEmpty()) count++;
        }
        return count;
    }

    // print total points and cards won
    private void printPointsAndCards() {
        System.out.println();
        for (Player p : players) System.out.println(p.getName() + "'s total points: " + p.getPoints());
        int totalPoints = players.get(0).getPoints() + players.get(1).getPoints();
        System.out.println("Game points: " + totalPoints + "\n");

        for (Player p : players) System.out.println(p.getName() + "'s total won cards: " + p.getWonCards().size());
        int totalCards = players.get(0).getWonCards().size() + players.get(1).getWonCards().size();
        System.out.println("Game cards: " + totalCards + "\n");
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

        System.out.println(Color.colorizeRand(ln) + "\n" + centerStr(ln.length(), s) + "\n" + Color.colorizeRand(ln));
    }

    // returns centered String inside lines
    private String centerStr(int lnLength, String s) {
        int i = (lnLength - s.length()) / 2;
        return " ".repeat(i) + s;
    }

    // returns latest card
    public Card latestCard() {
        for (Card card : deck) {
            if (card.isLatest()) return card;
        }
        return latestCard();
    }

    // print how much the game took to finish
    private void printGameTime() {
        // save how much the game took to finish
        Duration gameTime = Duration.between(startTime, Instant.now());
        int seconds = Integer.parseInt(String.valueOf(gameTime.toSeconds()));
        String sentence = "\nThe game has lasted ";

        // if seconds are less or equal than 0
        if (seconds <= 0) sentence += "zero or less seconds??";

        // if seconds are less than 60
        else if (seconds < 60) sentence += seconds + " seconds";

        // else, if seconds are 60
        else if (seconds == 60) sentence += "1 minute";

        // else, if the game took less than 1 hour
        else if (seconds < 3600) {
            int minutes = seconds / 60;
            seconds %= 60;

            // if else to well-print it
            if (minutes == 1) sentence += "1 minute and " + seconds + " seconds";
            else if (seconds == 1) sentence += minutes + " minutes and 1 second";
            else if (seconds == 0) sentence += minutes + " minutes";
            else sentence += minutes + " minutes and " + seconds + " seconds";
        }

        else {
            sentence = sentence.substring(0, sentence.length() - 1);
            sentence += "... something went wrong";
        }

        sentence += "!!";
        System.out.println(sentence);
    }

    // returns true if deck has cards
    public boolean deckHasCards() {
        for (Card card : deck) {
            // if card is not taken, deck still has cards
            if (!card.isTaken()) return true;
        }
        // else, it doesn't
        return false;
    }

    // returns how many cards are left to take
    public int howManyLeftToTake() {
        int count = 48;
        for (Card card : deck) {
            // decrement counter if card is taken
            if (card.isTaken()) count--;
        }
        return count;
    }

    // print game's author
    public void printAuthor() {
        System.out.println("\"La Brisca\" CLI cards game - by @ericmp33 with <3, june 2021.");
    }

    // validate the play and set who wins it and collects play's cards
    private void validateThePlay() {
        // assign play winner to variable
        Player playWinner = validateThePlayWinner(thePlay.get(0), thePlay.get(1));

        // play's winner collects the cards
        for (Card card : thePlay) {
            playWinner.getWonCards().add(card);
            card.setTakenBy(playWinner);
        }

        // remove cards from the play
        this.thePlay.clear();

        // print who won the play
        System.out.println("\n" + Color.colorizePlayWinner(playWinner));

        // well-print it
        if (round < 22) System.out.println();

        // if player on index 0 is not the same as the winner, means it is not at 1st post
        if (!players.get(0).equals(playWinner)) {
            // so, put it at index 0 (1st pos) to be the first to throw in next round
            Player temp = players.get(1);
            players.remove(1);
            players.add(0, temp);
        }
    }

    // returns the player who thrown the play's winner card
    private Player validateThePlayWinner(Card card0, Card card1) {
        return validateThePlayWinnerCard(card0, card1).getThrownBy();
    }

    // returns which card wins the play
    private Card validateThePlayWinnerCard(Card card0, Card card1) {
        // if one of the cards is trump and other isn't
        if (card0.isTrump() && !card1.isTrump() || card1.isTrump() && !card0.isTrump()) {
            // trump card wins
            return card0.isTrump() ? card0 : card1;
        }

        // if both cards have same type
        if (card0.getType().equals(card1.getType())) {
            // most valuable card wins
            return card0.getValue() > card1.getValue() ? card0 : card1;
        }

        // else, both aren't trump and have different type
        else {
            // thrown first wins
            return card0.isThrownFirst() ? card0 : card1;
        }
    }

    // returns the position of the param card
    public int posCard(Card card) {
        for (int i = 0; i < deck.size(); i++) {
            if (card == deck.get(i)) return i;
        }
        return -1;
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

        // set trump
        setTrump();

        // game's main loop
        mainLoop();

        // print total points and cards won
        printPointsAndCards();

        // print game's winner
        printWinner();

        // print how much the game took to finish
        printGameTime();

        // ask if print cards final information
        askPrintCardsInfo();
        printAllCards();

        // print game's author
        printAuthor();
    }
}
