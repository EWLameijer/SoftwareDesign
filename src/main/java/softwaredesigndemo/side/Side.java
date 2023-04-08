package softwaredesigndemo.side;

import softwaredesigndemo.Player;
import softwaredesigndemo.cards.Card;
import softwaredesigndemo.utils.Color;

import java.util.Scanner;

public class Side {
    private final Hero hero;

    private final Territory territory = new Territory();

    private final Deck deck;

    private final ManaBar manaBar = new ManaBar();

    private final Hand hand = new Hand();

    private final String playerName;

    private Side opponentsSide;

    public Side(Player player) {
        var playerDeck = player.deck();
        this.hero = Hero.from(playerDeck.heroType());
        this.deck = new Deck(playerDeck.cards());
        this.playerName = player.name();
    }

    public void setOpponentsSide(Side opponentsSide) {
        this.opponentsSide = opponentsSide;
    }

    public Territory getTerritory() {
        return territory;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void mulligan(int numCardsToMulligan) {
        deck.shuffle();
        for (int i = 0; i < numCardsToMulligan; i++) hand.add(deck.draw());
        hand.mulligan(deck);
    }

    public void giveCard(Card card) {
        hand.add(card);
    }

    public void giveTurn() {
        System.out.printf("It is %s's turn!%n", playerName);
        manaBar.startTurn();
        territory.startTurn();
        if (deck.canDraw()) {
            hand.add(deck.draw());
        } else {
            hero.takeExhaustionDamage();
        }
        Scanner in = new Scanner(System.in);
        do {
            showStatus();
            System.out.print("Which card do you want to play? (0-9), E to end your turn: ");
            String choice = in.nextLine();
            if (choice.equalsIgnoreCase("E")) return;
            int chosenCardIndex = Integer.parseInt(choice);
            hand.play(chosenCardIndex, manaBar, this, opponentsSide);
        } while (true);
    }

    private void showStatus() {
        opponentsSide.showAsEnemy();
        territory.show();
        hand.show();
        manaBar.show();
    }

    private void showAsEnemy() {
        var heroColorFunction = territory.colorEnemy(!territory.isTauntMinionPresent());
        System.out.println(heroColorFunction.apply("%s (%s): %d HP".formatted(playerName, hero.getType().name(), hero.getHitPoints())));
        territory.showAsEnemy();
    }
}
