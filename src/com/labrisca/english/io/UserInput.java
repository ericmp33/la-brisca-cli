package com.labrisca.english.io;

import com.labrisca.english.entity.Player;
import com.labrisca.english.util.Color;

import java.util.Scanner;

public class UserInput {
    private static final Scanner SC = new Scanner(System.in);

    UserInput() {}

    // ask the game mode to the user
    public static String askGameMode() {
        System.out.println("[?] Enable hacker mode?");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                System.out.println("Hacker mode on!");
                System.out.print(Color.ANSI_PURPLE + "Purple " + Color.ANSI_RESET);
                System.out.println("text = text you wouldn't see ;)");
                System.out.println();
                return "hacker";
            } else if (input.equals("no")) {
                System.out.println("Default mode on!");
                System.out.println();
                return "default";
            }
            System.out.println("Input \"yes\" or \"no\"...");
        }
    }

    // ask if enable bot's AI
    public static boolean askAIBot() {
        System.out.println("[?] Enable bot's AI?");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                System.out.println("You'll lose :P!");
                System.out.println();
                return true;
            } else if (input.equals("no")) {
                System.out.println("Beep beep...");
                System.out.println();
                return false;
            }
            System.out.println("Input \"yes\" or \"no\"...");
        }
    }

    // ask if print cards final information
    public static boolean askPrintCardsInfo() {
        System.out.println("\n[?] See cards final information?");
        while (true) {
            System.out.print("> ");
            String input = SC.nextLine().trim().toLowerCase();
            if (input.equals("yes")) return true;
            else if (input.equals("no")) return false;
            System.out.println("Input \"yes\" or \"no\"...");
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
                System.out.println("You can't change latest card...");
            }

            // check if player can throw specified card
            else if (canThrowCard(input, p.getInHandCards().size())) return input;
            else System.out.println("Input a valid number...");
        }
    }
    private static boolean canThrowCard(String input, int inHandCardsSize) {
        if (inHandCardsSize == 3 && (input.equals("3") || input.equals("2") || input.equals("1"))) return true;
        if (inHandCardsSize == 2 && (input.equals("2") || input.equals("1"))) return true;
        return inHandCardsSize == 1 && input.equals("1");
    }
}
