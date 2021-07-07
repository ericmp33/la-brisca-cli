package com.labrisca;

public class App {
    public static void main(String[] args) {
        // instantiate game, players and run game's main method
        Game game = new Game();
        Player jug = new Player("Eric", false, game);
        Player bot = new Player("Bot", true, game);

        game.run(jug, bot);
    }
}

// todo: fer que si hi ha el 7 es pugui treure

// todo: de la classe Jugador -> Bot, Humà ?

// todo: agafar carta de dal? no aleat