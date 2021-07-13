package com.labrisca;

public class App {
    public static void main(String[] args) {
        while (true){
        // initialize game, players and run game's principal method
        Game game = new Game();
        Player jug = new Player("@ericmp33", false, game);
        Player bot = new Player("Bot", true, game);

        game.run(jug, bot);
        }
    }
}

// todo longitud maxima de nom de lusuari a preguntar, 9 caracters, minima 2

// todo: preguntar quin nom de cartes vol, catala o angles, canviar el constructor traduint a lidioma que vol lusuari?

// todo: de la classe Jugador -> extends Bot, Humà ?

// todo: nasa update fer threads? i anar mostrant info a dos terminals diferents? cartes del bot, o info de cartes

// todo: preguntar huma contra huma, bot vs bot o bot vs huma? problema - huma vs huma es veuen les cartes de laltre player