package com.labrisca;

import com.labrisca.english.io.UserInput;

public class App {
    public static void main(String[] args) {
        System.out.println("Choose language: Catalan or English");
        while (true) {
            String input = UserInput.getInput();

            // initialize and run game
            if (input.equals("catalan") || input.equals("cat")) {
                System.out.println();
                new com.labrisca.catalan.Game().run();
                break;
            } else if (input.equals("english") || input.equals("en")) {
                System.out.println();
                new com.labrisca.english.Game().run();
                break;
            }
            System.out.println("Type \"Catalan\" or \"English\"");
        }
    }
}
