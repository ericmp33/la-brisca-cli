package com.labrisca;

public class AI {
    // variables
    private final Player bot;
    private final Game game;

    // constructor
    AI(Player bot, Game game) {
        this.bot = bot;
        this.game = game;
    }

    public Card throwCard() {
        // if the play is empty, throw first
        if (game.getThePlay().isEmpty()) {
            // throw the card with less value
            System.out.println("so i throw first... i throw the less valuable card");
            return lessValuableCard();
        }

        // else, means that will throw after human player, save human's thrown card
        Card thrownByHuman = game.getThePlay().get(0);
//        System.out.println("sé que has tirat " + Card.colorizeName(thrownByHuman.getName()));

//        System.out.println("teCartesAmbPedres() = " + teCartesAmbPedres());
//        System.out.println("teCartesAmbTriomf() = " + teCartesAmbTriomf());
//        System.out.println("totesSonTriomf() = " + totesSonTriomf());
//        System.out.println("totesSonMateixTipus() = " + totesSonMateixTipus());
//        System.out.println("cartaAmbMenysValor() = " + lessValueCard().getName());
//        System.out.println("cartaAmbMenysValorTriomf() = " + cartaAmbMenysValorTriomf().getName());
//        System.out.println("cartaAmbMesValor() = " + cartaAmbMesValor().getName());
//        System.out.println("cartaMateixTipus() = " + cartaMateixTipus(thrownByHuman.getType()).getName());
//        System.out.println("cartaAmbMesValorComparantTipus() = " + cartaAmbMesValorComparantTipus(thrownByHuman.getType()).getName());
//        System.out.println("posAsTriomf() = " + posAsTriomf());
//        if (posAsTriomf() != -1) System.out.println("getAsTriomf() = " + getAsTriomf().getName());
//        System.out.println("teCartesMateixTipus() = " + teCartesMateixTipus(thrownByHuman.getType()));
//        System.out.println("teCartesMesGransDelMateixTipus() = " + teCartesMesGransDelMateixTipus(thrownByHuman));
//        System.out.println("........");

        // if human thrown triumph three (card with value 111) and bot has triumph ace, throw it
        if (thrownByHuman.getValue() == 111 && hasTriumphAce()) {
            System.out.println("Et caso el tres!");
            return triumphAce();
        }

        // else, if human's card is triumph
        else if (thrownByHuman.isTriumph()) {
            // que tiri la carta que té menys valor
            // throw less valuable card
            System.out.println("wepa, has tirat triomf? deixa'm tirar la que té menys valor a no ser que tingui triomf i valgui la pena matar");
            if (teCartesAmbTriomf() && teCartesMesGransDelMateixTipus(thrownByHuman) && thrownByHuman.getPoints() > 3) {
                System.out.println("oh tinc triomf i carta més gran");
                return cartaAmbMenysValorTriomf();
            }

            System.out.println("no tinc triomf o bé no tinc cartes més grans del mateix tipus que has tirat, o no has tirat pedres...");
            return lessValuableCard();
        }

        // sinó, si la carta té pedres
        else if (thrownByHuman.getPoints() > 0) {
            System.out.println("hmm, la carta té pedres, aveure si puc matar...");

            // si el bot té cartes del mateix tipus i més grans
            if (teCartesMateixTipus(thrownByHuman.getType()) && teCartesMesGransDelMateixTipus(thrownByHuman)) {
                // que mati
                System.out.println("tinc cartes del mateix tipus, i més grans! Et mato");
                return cartaAmbMesValorComparantTipus(thrownByHuman.getType());
            }

            // sinó, si el bot té cartes amb triomf
            else if (teCartesAmbTriomf()) {
                System.out.println("tinc triomf! Tiro la que té menys valor i et mato");
                // que mati
                return cartaAmbMenysValorTriomf();
            } else {
                System.out.println("oh.. no tinc triomf :V");
            }
        }

        // sinó, si la carta no té pedres
        else if (thrownByHuman.getPoints() == 0) {
            System.out.println("oh, la carta no té pedres, primer miro si puc matar de coll o algo, sinó tiraré la carta amb menys valor");
            return lessValuableCard();
        }

        // sinó
        System.out.println("ha arribat al return del final :v (tiro la carta amb menys valor)");
        return lessValuableCard();
    }

    // retorna si té cartes amb pedres
//    private boolean teCartesAmbPedres() {
//        // si no té cartes amb pedres
//        for (Card carta : player.getHandCards()) {
//            if (carta.getPoints() > 0) return true;
//        }
//        return false;
//    }

    // retorna si té cartes amb triomf
    private boolean teCartesAmbTriomf() {
        for (Card carta : bot.getHandCards()) {
            // si té cartes amb triomf
            if (carta.isTriumph()) return true;
        }
        return false;
    }

    // retorna si totes les cartes son triomf
//    private boolean totesSonTriomf() {
//        for (Card carta : player.getHandCards()) {
//            // si no té cartes amb triomf
//            if (!carta.isTriumph()) return false;
//        }
//        return true;
//    }

    // retorna si totes les cartes son del mateix tipus
//    private boolean totesSonMateixTipus() {
//        String tipus = player.getHandCards().get(0).getType();
//        for (Card carta : player.getHandCards()) {
//            // si el tipus és diferent
//            if (!tipus.equals(carta.getType())) return false;
//        }
//        return true;
//    }

    // returns the less valuable card
    private Card lessValuableCard() {
        Card lessValuable = bot.getHandCards().get(0);
        for (Card card : bot.getHandCards()) {
            // if "lessValuable" card value is greater than "card" value
            if (lessValuable.getValue() > card.getValue()) {
                // less valuable card is "card"
                lessValuable = card;
            }
        }
        return lessValuable;
    }

    // retorna la carta amb menys valor dins de les que té triomf
    private Card cartaAmbMenysValorTriomf() {
        Card menysValuosa = cartaAmbTriomf();
        for (Card carta : bot.getHandCards()) {
            if (menysValuosa.getValue() > carta.getValue() && carta.isTriumph()) {
                menysValuosa = carta;
            }
        }
        return menysValuosa;
    }

    // retorna una carta amb triomf
    private Card cartaAmbTriomf() {
        for (Card carta : bot.getHandCards()) {
            if (carta.isTriumph()) return carta;
        }
        return bot.getHandCards().get(0);
    }

    // retorna la carta amb més valor
//    private Card cartaAmbMesValor() {
//        Card mesValuosa = player.getHandCards().get(0);
//        for (Card carta : player.getHandCards()) {
//            if (mesValuosa.getValue() < carta.getValue()) {
//                mesValuosa = carta;
//            }
//        }
//        return mesValuosa;
//    }

    // retorna la primera carta que troba si és del mateix tipus
    private Card cartaMateixTipus(String tipus) {
        Card mateixTipus = bot.getHandCards().get(0);
        for (Card carta : bot.getHandCards()) {
            if (tipus.equals(carta.getType())) {
                mateixTipus = carta;
            }
        }
        return mateixTipus;
    }

    // retorna la carta amb més valor dins del mateix tipus
    private Card cartaAmbMesValorComparantTipus(String tipus) {
        Card mesValuosa = cartaMateixTipus(tipus);
        for (Card carta : bot.getHandCards()) {
            if (mesValuosa.getValue() < carta.getValue() && mesValuosa.getType().equals(tipus) && tipus.equals(carta.getType())) {
                mesValuosa = carta;
            }
        }
        return mesValuosa;
    }

    // returns triumph ace
    private Card triumphAce() {
        for (int i = 0; i < bot.getHandCards().size(); i++) {
            // if finds triumph ace, return it (triumph ace's value is 112)
            if (bot.getHandCards().get(i).getValue() == 112) return bot.getHandCards().get(i);
        }
        return null;
    }

    // returns true if bot has triumph ace
    private boolean hasTriumphAce() {
        for (Card card : bot.getHandCards()) {
            if (card.getValue() == 112) return true;
        }
        return false;
    }

    // retorna si té cartes del mateix tipus passat per paràmetre
    private boolean teCartesMateixTipus(String tipus) {
        for (Card carta : bot.getHandCards()) {
            if (carta.getType().equals(tipus)) return true;
        }
        return false;
    }

    // retorna si té cartes més grans que la passada per paràmetre
    private boolean teCartesMesGransDelMateixTipus(Card tiradaPelJugador) {
        for (Card carta : bot.getHandCards()) {
            // si el valor de la carta és més gran que el valor que la carta tirada pel jugador i la carta és del mateix tipus que la tirada pel jugador
            if (carta.getValue() > tiradaPelJugador.getValue() && carta.getType().equals(tiradaPelJugador.getType())) return true;
        }
        return false;
    }
}
