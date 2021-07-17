package com.labrisca;

import com.labrisca.entities.Bot;
import com.labrisca.entities.Human;
import com.labrisca.entities.Player;

public class App {
    public static void main(String[] args) {
        System.out.println("Choose language: Català or English");
        while (true) {
            System.out.print("> ");
            String input = Game.getSc().nextLine().trim().toLowerCase();

            if (input.equals("català")) {
                System.out.println();

                // initialize game, players and run game
                catalan.Game game = new catalan.Game();
                catalan.entities.Player jug = new catalan.entities.Human("@ericmp33", game);
                catalan.entities.Player bot = new catalan.entities.Bot("Bot", game);

                game.run(jug, bot);
                break;
            }

            if (input.equals("english")) {
                System.out.println();

                // initialize game, players and run game
                Game game = new Game();
                Player jug = new Human("@ericmp33", game);
                Player bot = new Bot("Bot", game);

                game.run(jug, bot);
                break;
            }

            System.out.println("Type \"català\" or \"english\"");
        }
    }
}
