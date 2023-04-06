package softwaredesigndemo;

import java.util.Collections;
import java.util.Scanner;

public class Side {
    private final Hero hero;

    private final Territory territory = new Territory();

    private final Deck deck;

    private final ManaBar manaBar = new ManaBar();

    private final Hand hand = new Hand();

    private final String playerName;

    public Side(Player player) {
        var playerDeck = player.deck();
        this.hero = Hero.from(playerDeck.getHeroType());
        this.deck = new Deck(playerDeck.getCards());
        this.playerName = player.name();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void mulligan(int numCardsToMulligan) {
        deck.shuffle();
        for (int i=0; i < numCardsToMulligan; i++) hand.add(deck.draw());
        hand.mulligan(deck);
    }

    public void giveCard(Card card) {
        hand.add(card);
    }

    public void giveTurn() {
        System.out.printf("It is %s's turn!%n", playerName);
        manaBar.startTurn();

        hand.add(deck.draw());
        hand.show();
        manaBar.show();
        do {
            System.out.print("Which card do you want to play? (0-9), E to end your turn: ");
            Scanner in = new Scanner(System.in);
            String choice = in.nextLine();
            if (choice.equalsIgnoreCase("E")) return;
            int chosenCardIndex = Integer.parseInt(choice);
            hand.play(chosenCardIndex, manaBar);
            hand.show();
            manaBar.show();

        } while (true);
    }
}