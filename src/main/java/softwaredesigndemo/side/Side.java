package softwaredesigndemo.side;

import softwaredesigndemo.Player;
import softwaredesigndemo.cards.Card;
import softwaredesigndemo.side.characters.HearthstoneCharacter;
import softwaredesigndemo.side.characters.Hero;
import softwaredesigndemo.side.characters.Minion;
import softwaredesigndemo.utils.Color;

import java.util.Scanner;

public class Side {
    public final static char ENEMY_HERO_SYMBOL = '*';
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
            if (choice.isBlank()) continue;
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
                    if (opponentsSide.isValidAttackee(second)) {
                        Minion attacker = territory.getMinion(first);
                        HearthstoneCharacter attackee = opponentsSide.getAttackee(second);
                        attacker.attack(attackee);
                        disposeOfDeceasedIfAny();
                    } else opponentsSide.territory.communicateInvalidAttackee(second);
                } else territory.communicateInvalidAttacker(first);
            }
        }
    }

    private HearthstoneCharacter getAttackee(char attackeeSymbol) {
        if (attackeeSymbol == ENEMY_HERO_SYMBOL) return hero;
        else return territory.getMinion(attackeeSymbol);
    }

    private boolean isValidAttackee(char attackeeSymbol) {
        if (attackeeSymbol == ENEMY_HERO_SYMBOL && territory.noTauntMinionsPresent()) return true; // attack on hero
        else return territory.isValidAttackee(attackeeSymbol);
    }


    private void disposeOfDeceasedIfAny() {
        opponentsSide.territory.disposeOfDeceased();
        if (opponentsSide.hero.getHealth() <= 0) Color.GREEN.println("You are victorious!");
        territory.disposeOfDeceased();
    }

    private void showStatus() {
        opponentsSide.showAsEnemy();
        territory.show();
        hand.show();
        manaBar.show();
    }

    private void showAsEnemy() {
        var heroColorFunction = territory.colorEnemy(territory.noTauntMinionsPresent());
        System.out.println(heroColorFunction.apply("%s (%s): %d HP (*)".formatted(playerName, hero.getType().name(), hero.getHealth())));
        territory.showAsEnemy();
    }

    public ManaBar getManaBar() {
        return manaBar;
    }

    public boolean hasLost() {
        return hero.getHealth() <= 0;
    }

    public HearthstoneCharacter getHero() {
        return hero;
    }
}
