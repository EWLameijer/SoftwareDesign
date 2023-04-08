package softwaredesigndemo;

import softwaredesigndemo.cards.Card;
import softwaredesigndemo.cards.SpellCard;
import softwaredesigndemo.side.Side;
import softwaredesigndemo.utils.Color;

import java.util.Random;

public class Game {
    private final Side firstSide;
    private final Side secondSide;

    private final Random random = new Random();



    public Game(Player playerA, Player playerB) {
        if (random.nextInt(2) == 0) {
            firstSide = new Side(playerA);
            secondSide = new Side(playerB);
        } else {
            firstSide = new Side(playerB);
            secondSide = new Side(playerA);
        }
        var firstPlayerName = firstSide.getPlayerName();
        System.out.println("The game starts! " + firstPlayerName + " begins!");
        System.out.println(firstPlayerName + ", please keep or mulligan your cards...");
        firstSide.mulligan(3);
        System.out.println(secondSide.getPlayerName()+ ", please keep or mulligan your cards...");
        secondSide.mulligan(4);
        Card COIN = new SpellCard("The Coin", 0, "Gain 1 mana crystal this turn only");
        secondSide.giveCard(COIN);
        do {
            firstSide.giveTurn(secondSide);
            secondSide.giveTurn(firstSide);
        } while (true);
    }
}
