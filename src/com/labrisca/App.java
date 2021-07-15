package com.labrisca;

import com.labrisca.entities.Bot;
import com.labrisca.entities.Human;
import com.labrisca.entities.Player;

public class App {
    public static void main(String[] args) {
        // initialize game, players and run game
        Game game = new Game();
        Player jug = new Human("@ericmp33", game);
        Player bot = new Bot("Bot", game);

        game.run(jug, bot);
    }
}

// todo: preguntar quin nom de cartes vol, catala o angles, canviar el constructor traduint a lidioma que vol lusuari?