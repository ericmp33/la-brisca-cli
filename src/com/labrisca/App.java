package com.labrisca;

public class App {
    public static void main(String[] args) {
        // initialize game, players and run game's principal method
        Game game = new Game();
        Player jug = new Player("Eric", false, game);
        Player bot = new Player("Bot", true, game);

        game.run(jug, bot);
    }
}

// todo: fer que si hi ha el 7 es pugui treure?

// todo: de la classe Jugador -> extends Bot, Humà ?

// todo: agafar carta de dal? no aleat

// todo: si te 2 cartes de triomf i una amb un as o un tres, que no tiri las ni el 3