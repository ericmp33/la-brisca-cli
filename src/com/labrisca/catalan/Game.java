package com.labrisca.catalan;

import com.labrisca.catalan.entity.AI;
import com.labrisca.catalan.entity.Bot;
import com.labrisca.catalan.entity.Human;
import com.labrisca.catalan.entity.Player;
import com.labrisca.catalan.util.Color;
import com.labrisca.catalan.util.Str;
import com.labrisca.catalan.io.UserInput;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    // fields
    private final List<Card> deck;
    private final List<Card> thePlay;
    private final List<Player> players;
    private int gameTime;
    private int round;
    private String gameMode;

    // getters
    public List<Card> getDeck() { return deck; }
    public List<Card> getThePlay() { return thePlay; }
    public int getRound() { return round; }
    public String getGameMode() { return gameMode; }

    // constructor
    public Game() {
        // put cards into deck
        deck = new ArrayList<>(List.of(
                new Card("as de basto", "bastos", 12, 11),
                new Card("dos de basto", "bastos", 1, 0),
                new Card("tres de basto", "bastos", 11, 10),
                new Card("quatre de basto", "bastos", 2, 0),
                new Card("cinc de basto", "bastos", 3, 0),
                new Card("sis de basto", "bastos", 4, 0),
                new Card("set de basto", "bastos", 5, 0),
                new Card("vuit de basto", "bastos", 6, 0),
                new Card("nou de basto", "bastos", 7, 0),
                new Card("sota de basto", "bastos", 8, 2),
                new Card("cavall de basto", "bastos", 9, 3),
                new Card("rei de basto", "bastos", 10, 4),

                new Card("as de copa", "copes", 12, 11),
                new Card("dos de copa", "copes", 1, 0),
                new Card("tres de copa", "copes", 11, 10),
                new Card("quatre de copa", "copes", 2, 0),
                new Card("cinc de copa", "copes", 3, 0),
                new Card("sis de copa", "copes", 4, 0),
                new Card("set de copa", "copes", 5, 0),
                new Card("vuit de copa", "copes", 6, 0),
                new Card("nou de copa", "copes", 7, 0),
                new Card("sota de copa", "copes", 8, 2),
                new Card("cavall de copa", "copes", 9, 3),
                new Card("rei de copa", "copes", 10, 4),

                new Card("as d'espasa", "espases", 12, 11),
                new Card("dos d'espasa", "espases", 1, 0),
                new Card("tres d'espasa", "espases", 11, 10),
                new Card("quatre d'espasa", "espases", 2, 0),
                new Card("cinc d'espasa", "espases", 3, 0),
                new Card("sis d'espasa", "espases", 4, 0),
                new Card("set d'espasa", "espases", 5, 0),
                new Card("vuit d'espasa", "espases", 6, 0),
                new Card("nou d'espasa", "espases", 7, 0),
                new Card("sota d'espasa", "espases", 8, 2),
                new Card("cavall d'espasa", "espases", 9, 3),
                new Card("rei d'espasa", "espases", 10, 4),

                new Card("as d'oro", "oros", 12, 11),
                new Card("dos d'oro", "oros", 1, 0),
                new Card("tres d'oro", "oros", 11, 10),
                new Card("quatre d'oro", "oros", 2, 0),
                new Card("cinc d'oro", "oros", 3, 0),
                new Card("sis d'oro", "oros", 4, 0),
                new Card("set d'oro", "oros", 5, 0),
                new Card("vuit d'oro", "oros", 6, 0),
                new Card("nou d'oro", "oros", 7, 0),
                new Card("sota d'oro", "oros", 8, 2),
                new Card("cavall d'oro", "oros", 9, 3),
                new Card("rei d'oro", "oros", 10, 4)
        ));

        // shuffle deck and initialize the play and list of players
        Collections.shuffle(deck);
        thePlay = new ArrayList<>();
        players = new ArrayList<>();
    }

    // principal method to run the game
    public void run() {
        // welcome message
        welcomePrint();

        // set the game mode
        setGameMode();

        // create and add players into game
        addPlayers();

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
        printCardsInfo();

        // print game's author
        printAuthor();
    }

    // welcome message
    public void welcomePrint() {
        System.out.println("Benvingut al joc de cartes de La Brisca");
        System.out.println("Fet per @ericmp33, juny 2021");
        System.out.println("Que comenci el joc!");
        System.out.println("Canvia l'última carta introduïnt \"7\"\n");
    }

    // set the game mode
    private void setGameMode() {
        gameMode = UserInput.askGameMode();
    }

    // create and add players into game
    private void addPlayers() {
        players.add(new Human("@ericmp33", this));
        if (UserInput.askAIBot()) players.add(new AI(this));
        else players.add(new Bot(this));
    }

    // ask if print cards final information
    private void printCardsInfo() {
        if (UserInput.askPrintCardsInfo()) printAllCards();
    }

    // deal 3 first cards to each player
    private void deal3FirstCards() {
        System.out.println("Repartint les 3 primeres cartes a cada jugador:");
        // for each player, take 3 cards
        for (Player p : players) {
            for (int i = 0; i < 3; i++) p.takeCard();
            // if hacker mode is on, make a print (more friendly look)
            if (gameMode.equals("hacker")) System.out.println();
        }

        // else, wait till it finishes iteration and print it now
        if (!gameMode.equals("hacker")) System.out.println();
    }

    // set game trump
    private void setTrump() {
        Card trumpCard = deck.get(6);
        deck.remove(6);
        trumpCard.setLatest("true");
        deck.add(trumpCard);

        String trump = trumpCard.getType();

        System.out.println("Ha aparegut -> " + Color.name(trumpCard.getName()));
        if (trump.contains("bast") || trump.contains("cop")) System.out.println("Va de " + trump + "!\n");
        else System.out.println("Va d'" + trump + "!\n");

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

    // print all cards and their attributes
    private void printAllCards() {
        System.out.println("\nInformació de totes les cartes:");
        for (Player player : players) {
            System.out.println("Cartes guanyades " + player.getName() + ":");
            for (Card card : player.getWonCards()) {
                card.printAttributes();
            }
            System.out.println();
        }
    }

    // game's main loop
    private void mainLoop() {
        System.out.println(players.get(0).getName() + " comença tirant");

        // save game's start time
        Instant startTime = Instant.now();
        System.out.println("[!] Cronòmetre activat!");

        String trumpCard = latestCard().getName();
        while (true) {
            // if the deck is not empty, update the new trump card
            if (!deckIsEmpty()) trumpCard = latestCard().getName();

            round++;
            System.out.println("-----------------------------------------------");
            System.out.print("Ronda " + round);
            System.out.println(" (triomf -> " + Color.name(trumpCard) + ")");

            // for each player, throw a card
            for (Player p : players) p.throwCard(round);

            // validate and set who wins the play and saves the cards
            validateThePlay();

            // if any player hasn't cards, game finishes
            if (playersWithoutCards() == players.size()) {
                gameTime = (int) Duration.between(startTime, Instant.now()).toSeconds();
                System.out.println("\n[!] Cronòmetre parat!");
                break;
            }

            // else, for each player, take a card
            for (Player p : players) p.takeCard();

            // and if hacker mode is on
            if (gameMode.equals("hacker")) {
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

    // set and print game's winner
    private void setAndPrintWinner() {
        Player winner = null;
        boolean draw = false;
        Player player0 = players.get(0);
        Player player1 = players.get(1);
        int pointsPlayer0 = player0.getPoints();
        int pointsPlayer1 = player1.getPoints();

        if (pointsPlayer0 > pointsPlayer1) winner = player0;
        else if (pointsPlayer1 > pointsPlayer0) winner = player1;
        else draw = true;

        String ln = "#########--------------------------------------------#########";
        String s;

        if (draw) s = "Empat! Els dos jugadors han fet 60 pedres!";
        else s = winner.getName() + " ha guanyat la partida amb " + winner.getPoints() + " pedres!!!";
        s = Str.centerStr(ln.length(), s);

        System.out.println(Color.colorizeRand(ln) + "\n" + s + "\n" + Color.colorizeRand(ln));
    }

    // print how much the game took to finish
    private void printGameTime() {
        int sec = gameTime;
        int min = 0;
        while (sec >= 60) {
            sec -= 60;
            min++;
        }
        System.out.println("\nLa partida ha durat " + min + " min, " + sec + " sec!!");
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
        System.out.println("El joc de la Brisca per entorn de comandes - @ericmp33, juny 2021.");
    }

    // validate the play and set who wins it and collects play's cards
    private void validateThePlay() {
        // assign play winner to variable
        Player playWinner = validateThePlayWinner(thePlay.get(0), thePlay.get(1));

        // play's winner collects the cards
        for (Card card : thePlay) playWinner.getWonCards().add(card);
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
}
