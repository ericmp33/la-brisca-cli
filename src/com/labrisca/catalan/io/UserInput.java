package com.labrisca.catalan.io;

import com.labrisca.catalan.entity.Player;
import com.labrisca.catalan.util.Color;

import java.util.Scanner;

public class UserInput {
    private static final Scanner SC = new Scanner(System.in);

    UserInput() {}

    // ask the game mode to the user
    public static String askGameMode() {
        System.out.println("[?] Activar el mode hacker?");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim().toLowerCase();
            if (input.equals("si")) {
                System.out.println("Mode hacker activat!");
                System.out.print("Text" + Color.ANSI_PURPLE + " lila " + Color.ANSI_RESET);
                System.out.println("= text que no veuries ;)");
                System.out.println();
                return "hacker";
            } else if (input.equals("no")) {
                System.out.println("Mode normal activat!");
                System.out.println();
                return "default";
            }
            System.out.println("Introdueix \"si\" o \"no\"...");
        }
    }

    // ask if enable bot's AI
    public static boolean askAIBot() {
        System.out.println("[?] Fer que el bot sigui intel·ligent?");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim().toLowerCase();
            if (input.equals("si")) {
                System.out.println("Perdràs :P!");
                System.out.println();
                return true;
            } else if (input.equals("no")) {
                System.out.println("Beep beep...");
                System.out.println();
                return false;
            }
            System.out.println("Introdueix \"si\" o \"no\"...");
        }
    }

    // ask if print cards final information
    public static boolean askPrintCardsInfo() {
        System.out.println("\n[?] Veure informació final de les cartes?");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim().toLowerCase();
            if (input.equals("si")) return true;
            else if (input.equals("no")) return false;
            System.out.println("Introdueix \"si\" o \"no\"...");
        }
    }

    // ask which card must be thrown or / and change latest card
    public static String askThrowCard(Player p) {
        String input;
        while (true) {
            System.out.print("> ");
            input = SC.nextLine().trim();

            // check if player wants and can change latest card
            if (input.equals("7") && p.canChangeLastCard()) {
                p.changeLastCard();
                p.printCardsInHand();
            } else if (input.equals("7") && !p.canChangeLastCard()) {
                System.out.println("No pots canviar l'última carta...");
            }

            // check if player can throw specified card
            else if (canThrowCard(input, p.getInHandCards().size())) return input;
            else System.out.println("Introdueix un número vàlid...");
        }
    }
    private static boolean canThrowCard(String input, int inHandCardsSize) {
        if (inHandCardsSize == 3 && (input.equals("3") || input.equals("2") || input.equals("1"))) return true;
        if (inHandCardsSize == 2 && (input.equals("2") || input.equals("1"))) return true;
        return inHandCardsSize == 1 && input.equals("1");
    }
}
