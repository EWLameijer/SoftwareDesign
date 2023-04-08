package softwaredesigndemo.side;

import softwaredesigndemo.Minion;
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
            System.out.print("Which card do you want to play? (0-9), a letter to attack (b# lets your second " +
                    "minion attack the third minion of the opponent), or Q to end your turn: ");
            String choice = in.nextLine();
            if (choice.equalsIgnoreCase("Q")) return;
            execute(choice);
        } while (true);
    }

    private void execute(String choice) {
        char first = choice.charAt(0);
        if (Character.isDigit(first)) {
            int chosenCardIndex = Integer.parseInt(choice);
            hand.play(chosenCardIndex, this, opponentsSide);
        } else {
            if (Character.isLetter(first)) {
                if (territory.isValidAttacker(first)) {
                    char second = choice.charAt(1);
                    if (opponentsSide.territory.isValidAttackee(second)) {
                        Minion attacker = territory.getMinion(first);
                        Minion attackee = opponentsSide.territory.getMinion(second);
                        attacker.attack(attackee);
                        disposeOfDeceasedIfAny();
                    } else opponentsSide.territory.communicateInvalidAttackee(second);
                } else territory.communicateInvalidAttacker(first);
            }
        }
    }

    private void disposeOfDeceasedIfAny() {
        territory.disposeOfDeceased();
        opponentsSide.territory.disposeOfDeceased();
    }

    private void showStatus() {
        opponentsSide.showAsEnemy();
        territory.show();
        hand.show();
        manaBar.show();
    }

    private void showAsEnemy() {
        var heroColorFunction = territory.colorEnemy(!territory.isTauntMinionPresent());
        System.out.println(heroColorFunction.apply("%s (%s): %d HP (*)".formatted(playerName, hero.getType().name(), hero.getHitPoints())));
        territory.showAsEnemy();
    }

    public ManaBar getManaBar() {
        return manaBar;
    }
}
