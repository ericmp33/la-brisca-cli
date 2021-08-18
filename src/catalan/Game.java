package catalan;

import catalan.entities.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game {
    // variables
    private final List<Card> deck;
    private final List<Card> thePlay; // cards list that participate in the play
    private final List<Player> players;
    private final Instant startTime;
    private int round;
    private boolean hacker; // if human player can see bot cards
    private boolean ai; // if turn on AI's bot
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
        System.out.println("Benvingut al joc de cartes de La Brisca");
        System.out.println("Fet per @ericmp33 amb <3, juny 2021");
        System.out.println("Barrejant les cartes...");
        System.out.println("Cartes barrejades! Que començi el joc!");
        System.out.println("Canvia l'última carta introduïnt \"7\"");
        System.out.println();
    }

    // ask if enable hacker mode
    private void enableHacker() {
        System.out.println("[?] Tria mode de joc: normal o hacker");
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("normal")) {
                System.out.println("Mode normal activat!");
                hacker = false;
                break;
            } else if (input.equals("hacker")) {
                System.out.println("Mode hacker activat!");
                System.out.print("Text" + Color.ANSI_PURPLE + " lila " + Color.ANSI_RESET);
                System.out.println("= text que no veuries ;)");
                hacker = true;
                break;
            }
            System.out.println("Introdueix \"normal\" o \"hacker\"...");
        }
        System.out.println();
    }

    // ask if enable AI bot mode
    private void enableAIBot() {
        System.out.println("[?] Fer que el bot sigui intel·ligent?");
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("si")) {
                System.out.println("Perdràs :P!");
                ai = true;
                break;
            } else if (input.equals("no")) {
                System.out.println("Beep beep...");
                ai = false;
                break;
            }
            System.out.println("Introdueix \"si\" o \"no\"...");
        }
        System.out.println();
    }

    // ask if print cards final information
    private void askPrintCardsInfo() {
        System.out.println("\n[?] Veure informació final de les cartes?");
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("si")) {
                System.out.println();
                printAllCards();
                break;
            } else if (input.equals("no")) {
                break;
            }
            System.out.println("Introdueix \"si\" o \"no\"...");
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
        System.out.println("Repartint les 3 primeres cartes a cada jugador:");
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

                System.out.println("Ha aparegut -> " + Color.acolorirNom(card.getName()));

                if (trump.contains("bast") || trump.contains("cop")) System.out.println("Va de " + trump + ".\n");
                else System.out.println("Va d'" + trump + ".\n");
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
        System.out.println("Informació de totes les cartes:");
        for (Card card : deck) card.printAttributes();
    }

    // game's main loop
    private void mainLoop() {
        System.out.println("El jugador " + players.get(0).getName() + " comença tirant");

        round = 0;
        while (true) {
            round++;
            System.out.println("-----------------------------------------------");
            System.out.print("Ronda " + round);
            System.out.println(" (triomf -> " + Color.acolorirNom(latestCard().getName()) + ")");

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
                for (Player p : players) System.out.println("Punts " + p.getName() + ": " + p.getPoints());
                System.out.println();
                for (Player p : players) System.out.println("Cartes guanyades " + p.getName() + ": " + p.getWonCards().size());
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
        for (Player p : players) System.out.println("Punts totals " + p.getName() + ": " + p.getPoints());
        int totalPoints = players.get(0).getPoints() + players.get(1).getPoints();
        System.out.println("Punts totals partida: " + totalPoints + "\n");

        for (Player p : players) System.out.println("Total cartes guanyades " + p.getName() + ": " + p.getWonCards().size());
        int totalCards = players.get(0).getWonCards().size() + players.get(1).getWonCards().size();
        System.out.println("Total cartes partida: " + totalCards + "\n");
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

        String ln = "###########--------------------------------------------###########";
        String s;

        if (draw) s = "Empat! Els dos jugadors han fet 60 pedres!";
        else s = "El jugador " + winner.getName() + " ha guanyat la partida!!! Ha fet " + winner.getPoints() + " pedres!";

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
        int sec = (int) Duration.between(startTime, Instant.now()).toSeconds();
        int min = 0;
        while (sec >= 60) {
            sec -= 60;
            min++;
        }
        System.out.println("\nLa partida ha durat " + min + " min, " + sec + " sec!!");
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
        System.out.println("El joc de la Brisca per entorn de comandes - @ericmp33, juny 2021.");
    }

    // validate the play and set who wins it and collects play's cards
    private void validateThePlay() {
        // assign play winner to variable
        Player playWinner = validateThePlayWinner(thePlay.get(0), thePlay.get(1));

        // play's winner collects the cards
        for (Card card : thePlay) {
            playWinner.getWonCards().add(card);
            card.setWonBy(playWinner);
        }

        // remove cards from the play
        thePlay.clear();

        // print who won the play
        System.out.println("\n" + Color.playWinner(playWinner));

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

        // print game's author
        printAuthor();
    }
}
