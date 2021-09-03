package com.labrisca;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose language: Catalan or English");
        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim().toLowerCase();

            // initialize game, players and run game
            if (input.equals("catalan")) {
                var game = new com.labrisca.catalan.Game();
                var jug = new com.labrisca.catalan.entity.Human("@ericmp33", game);
                var bot = new com.labrisca.catalan.entity.Bot(game);

                System.out.println();
                game.run(jug, bot);
                break;
            } else if (input.equals("english")) {
                var game = new com.labrisca.english.Game();
                var jug = new com.labrisca.english.entity.Human("@ericmp33", game);
                var bot = new com.labrisca.english.entity.Bot(game);

                System.out.println();
                game.run(jug, bot);
                break;
            }
            System.out.println("Type \"Catalan\" or \"English\"");
        }
    }
}
