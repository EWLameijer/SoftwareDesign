package softwaredesigndemo;

import softwaredesigndemo.side.GameDeck;

import java.util.Scanner;

public class Player {
    private final String name;
    private final GameDeck deck;

    private final static Scanner in = new Scanner(System.in);

    public Player(String provisionalName) {
        name = askForName(provisionalName);
        deck = askForDeck();
    }

    private static GameDeck askForDeck() {
        System.out.print("Warrior (W) or Mage(anything else)? ");
        var isWarrior = in.nextLine().toUpperCase().startsWith("W");
        return isWarrior ? GameDeck.WARRIOR_DECK : GameDeck.MAGE_DECK;
    }

    private static String askForName(String provisionalName) {
        System.out.print(provisionalName + ", enter your name: ");
        return in.nextLine().trim();
    }

    public String getName() {
        return name;
    }

    public GameDeck getDeck() {
        return deck;
    }
}
