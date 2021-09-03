package com.labrisca;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose language: Catalan or English");
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim().toLowerCase();

            // initialize and run game
            if (input.equals("catalan")) {
                System.out.println();
                new com.labrisca.catalan.Game().run();
                break;
            } else if (input.equals("english")) {
                System.out.println();
                new com.labrisca.english.Game().run();
                break;
            }
            System.out.println("Type \"Catalan\" or \"English\"");
        }
    }
}
