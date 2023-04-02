package softwaredesigndemo;

import java.util.Random;

public class Game {
    private final Player firstPlayer;
    private final Player secondPlayer;

    private final Random random = new Random();

    public Game(Player playerA, Player playerB) {
        if (random.nextInt(2) == 0) {
            firstPlayer = playerA;
            secondPlayer = playerB;
        } else {
            firstPlayer = playerB;
            secondPlayer = playerA;
        }
        var firstPlayerName = firstPlayer.name();
        System.out.println("The game starts! " + firstPlayerName + " begins!");
        System.out.println(firstPlayerName + ", please keep or mulligan your cards...");
        firstPlayer.deck().mulligan(3);
        System.out.println(secondPlayer.name() + ", please keep or mulligan your cards...");
        secondPlayer.deck().mulligan(4);
    }
}
