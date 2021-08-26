package com.labrisca;

import com.labrisca.entities.Player;

import java.util.ArrayList;
import java.util.Collections;
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
    private String gameMode;
    private boolean ai; // if turn on AI's bot
    private static final Scanner SC = new Scanner(System.in);

    // getters
    public List<Card> getDeck() { return deck; }
    public List<Card> getThePlay() { return thePlay; }
    public int getRound() { return round; }
    public String getGameMode() { return gameMode; }
    public boolean isAi() { return ai; }
    public static Scanner getSc() { return SC; }

    // constructor
    public Game() {
        // put cards into deck
        deck = new ArrayList<>(List.of(
            new Card("club's ace", "club", 12, 11),
            new Card("club's two", "club", 1, 0),
            new Card("club's three", "club", 11, 10),
            new Card("club's four", "club", 2, 0),
            new Card("club's five", "club", 3, 0),
            new Card("club's six", "club", 4, 0),
            new Card("club's seven", "club", 5, 0),
            new Card("club's eight", "club", 6, 0),
            new Card("club's nine", "club", 7, 0),
            new Card("club's jack", "club", 8, 2),
            new Card("club's knight", "club", 9, 3),
            new Card("club's king", "club", 10, 4),

            new Card("cup's ace", "cup", 12, 11),
            new Card("cup's two", "cup", 1, 0),
            new Card("cup's three", "cup", 11, 10),
            new Card("cup's four", "cup", 2, 0),
            new Card("cup's five", "cup", 3, 0),
            new Card("cup's six", "cup", 4, 0),
            new Card("cup's seven", "cup", 5, 0),
            new Card("cup's eight", "cup", 6, 0),
            new Card("cup's nine", "cup", 7, 0),
            new Card("cup's jack", "cup", 8, 2),
            new Card("cup's knight", "cup", 9, 3),
            new Card("cup's king", "cup", 10, 4),

            new Card("sword's ace", "sword", 12, 11),
            new Card("sword's two", "sword", 1, 0),
            new Card("sword's three", "sword", 11, 10),
            new Card("sword's four", "sword", 2, 0),
            new Card("sword's five", "sword", 3, 0),
            new Card("sword's six", "sword", 4, 0),
            new Card("sword's seven", "sword", 5, 0),
            new Card("sword's eight", "sword", 6, 0),
            new Card("sword's nine", "sword", 7, 0),
            new Card("sword's jack", "sword", 8, 2),
            new Card("sword's knight", "sword", 9, 3),
            new Card("sword's king", "sword", 10, 4),

            new Card("coin's ace", "coin", 12, 11),
            new Card("coin's two", "coin", 1, 0),
            new Card("coin's three", "coin", 11, 10),
            new Card("coin's four", "coin", 2, 0),
            new Card("coin's five", "coin", 3, 0),
            new Card("coin's six", "coin", 4, 0),
            new Card("coin's seven", "coin", 5, 0),
            new Card("coin's eight", "coin", 6, 0),
            new Card("coin's nine", "coin", 7, 0),
            new Card("coin's jack", "coin", 8, 2),
            new Card("coin's knight", "coin", 9, 3),
            new Card("coin's king", "coin", 10, 4)
        ));

        // shuffle the deck, initialize the play and list of players and save game's start time
        Collections.shuffle(deck);
        thePlay = new ArrayList<>();
        players = new ArrayList<>();
        startTime = Instant.now();
    }

    // welcome message
    public void welcomePrint() {
        System.out.println("Welcome to \"La Brisca\" CLI cards game");
        System.out.println("Made by @ericmp33, june 2021");
        System.out.println("Let the game begin!");
        System.out.println("Change the latest card inputting \"7\"\n");
    }

    // set the game mode
    private void setGameMode() {
        System.out.println("[?] Enable hacker mode?");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                System.out.println("Hacker mode on!");
                System.out.print(Color.ANSI_PURPLE + "Purple " + Color.ANSI_RESET);
                System.out.println("text = text you wouldn't see ;)");
                gameMode = "hacker";
                break;
            } else if (input.equals("no")) {
                System.out.println("Default mode on!");
                gameMode = "default";
                break;
            }
            System.out.println("Input \"yes\" or \"no\"...");
        }
        System.out.println();
    }

    // set AI bot mode
    private void setAIBot() {
        System.out.println("[?] Enable bot's AI?");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                System.out.println("You'll lose :P!");
                ai = true;
                break;
            } else if (input.equals("no")) {
                System.out.println("Beep beep...");
                ai = false;
                break;
            }
            System.out.println("Input \"yes\" or \"no\"...");
        }
        System.out.println();
    }

    // ask if print cards final information
    private void askPrintCardsInfo() {
        System.out.println("\n[?] See cards final information?");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                System.out.println();
                printAllCards();
                break;
            } else if (input.equals("no")) break;
            System.out.println("Input \"yes\" or \"no\"...");
        }
        System.out.println();
    }

    // deal 3 first cards to each player
    private void deal3FirstCards() {
        System.out.println("Dealing 3 first cards to each player:");
        // for each player, take 3 cards
        for (Player p : players) {
            for (int i = 0; i < 3; i++) p.takeCard();

            // if hacker mode is on, make a print (more friendly look)
            if (gameMode.equals("hacker")) System.out.println();
        }

        // else, wait till it finishes iteration and print it now
        if (!gameMode.equals("hacker")) System.out.println();
    }

    // set trump
    private void setTrump() {
        Card trumpCard = deck.get(6);
        deck.remove(6);
        trumpCard.setLatest("true");
        deck.add(trumpCard);

        String trump = trumpCard.getType();

        System.out.println(Color.name(capitalizeStr(trumpCard.getName()), false) + " appeared");
        System.out.println("So.. trump is " + Color.type(trump + "s") + "!");

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
        for (Player player : players) {
            System.out.println(player.getName() + " won cards:");
            for (Card card : player.getWonCards()) {
                card.printAttributes();
            }
            System.out.println();
        }
    }

    // game's main loop
    private void mainLoop() {
        System.out.println(players.get(0).getName() + " starts throwing");

        String trumpCard = latestCard().getName();
        while (true) {
            // if the deck is not empty, update the new trump card
            if (!deckIsEmpty()) trumpCard = latestCard().getName();

            round++;
            System.out.println("-----------------------------------------------");
            System.out.print("Round " + round);
            System.out.println(" (trump -> " + Color.name(trumpCard, false) + ")");

            // for each player, throw a card
            for (Player p : players) p.throwCard(round);

            // validate and set who wins the play and saves the cards
            validateThePlay();

            // if any player hasn't cards, game finishes
            if (playersWithoutCards() == players.size()) break;

            // else, for each player, take a card
            for (Player p : players) p.takeCard();

            // and if hacker mode is on
            if (gameMode.equals("hacker")) {
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

    // set and print game's winner
    private void setAndPrintWinner() {
        int temp = -1;
        Player winner = players.get(0);
        boolean draw = false;
        for (Player p : players) {
            int maxPoints = p.getPoints();

            if (maxPoints > temp) {
                temp = maxPoints;
                winner = p;
            } else if (maxPoints == temp) {
                draw = true;
                winner = null;
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

    // print how much the game took to finish
    private void printGameTime() {
        int sec = (int) Duration.between(startTime, Instant.now()).toSeconds();
        int min = 0;
        while (sec >= 60) {
            sec -= 60;
            min++;
        }
        System.out.println("\nThe game lasted " + min + " min, " + sec + " sec!!");
    }

    // returns true if deck is empty
    public boolean deckIsEmpty() {
        return deck.isEmpty();
    }

    // returns latest card in the deck
    public Card latestCard() {
        return deck.get(deck.size() - 1);
    }

    // print game's author
    public void printAuthor() {
        System.out.println("\"La Brisca\" CLI cards game - by @ericmp33, june 2021.");
    }

    // validate the play and set who wins it and collects play's cards
    private void validateThePlay() {
        // assign play winner to variable
        Player playWinner = validateThePlayWinner(thePlay.get(0), thePlay.get(1));

        // play's winner collects the cards
        for (Card card : thePlay) {
            playWinner.getWonCards().add(card);
        }

        // remove cards from the play
        thePlay.clear();

        // print who won the play
        System.out.println("\n" + Color.playWinner(playWinner));

        // well-print it
        if (round < 22) System.out.println();

        // if player on index 0 is not the same as the play winner, means it is not at 1st post
        if (!players.get(0).equals(playWinner)) {
            // so, put it at index 0 (1st pos) to be the first to throw in next round
            Collections.swap(players, 0, 1);
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

    // principal method to run the game
    public void run(Player human, Player bot) {
        // add players into game
        players.add(human);
        players.add(bot);

        welcomePrint();

        // set the game mode and AI bot mode
        setGameMode();
        setAIBot();

        // shuffle players list to choose who starts the game, set trump and deal 3 first cards to each player
        Collections.shuffle(players);
        setTrump();
        deal3FirstCards();

        // game's main loop
        mainLoop();

        // once game is over, print total points and cards won, game's winner and game time
        printPointsAndCards();
        setAndPrintWinner();
        printGameTime();

        // ask if print all the cards final information
        askPrintCardsInfo();

        // print game's author
        printAuthor();
    }
}
