package softwaredesigndemo;

import softwaredesigndemo.cards.Card;
import softwaredesigndemo.cards.SpellCard;
import softwaredesigndemo.side.Side;

import java.util.Random;

public class Game {
    private final Side firstSide;

    private final Side secondSide;

    public Game() {
        var playerA = new Player("Player 1");
        var playerB = new Player("Player 2");
        Random random = new Random();
        if (random.nextInt(2) == 0) {
            firstSide = new Side(playerA);
            secondSide = new Side(playerB);
        } else {
            firstSide = new Side(playerB);
            secondSide = new Side(playerA);
        }
        firstSide.setOpponentsSide(secondSide);
        secondSide.setOpponentsSide(firstSide);
    }

    public void play() {
        System.out.println("The game starts! " + firstSide.getPlayerName() + " begins!");
        firstSide.mulligan(3);
        secondSide.mulligan(4);
        Card COIN = new SpellCard("The Coin", 0, "Gain 1 mana crystal this turn only");
        secondSide.giveCard(COIN);
        int turn = 0;
        do {
            turn++;
            Side activeSide = turn % 2 == 1 ? firstSide : secondSide;
            activeSide.giveTurn();
        } while (firstSide.isAlive() && secondSide.isAlive());
    }
}
