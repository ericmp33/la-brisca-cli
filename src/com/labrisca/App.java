package com.labrisca;

public class App {
    public static void main(String[] args) {
        // initialize game, players and run game's principal method
        Game game = new Game();
        Player jug = new Player("@ericmp", false, game);
        Player bot = new Player("Bot", true, game);

        game.run(jug, bot);
    }
}

// todo: fer que si hi ha el 7 es pugui treure?

// todo: preguntar quin nom de cartes vol, catala o angles, canviar el constructor traduint a lidioma que vol lusuari?

// todo: de la classe Jugador -> extends Bot, Humà ?

// todo: agafar carta de dal? no aleat (posar ultima carta per agafar al final?):
//  Game: Lists: deckCards, thrownCards
//  Player: Lists inHandCards, savedCards

// todo: nasa update fer threads? i anar mostrant info a dos terminals diferents?