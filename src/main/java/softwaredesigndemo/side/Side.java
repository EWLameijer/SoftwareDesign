package softwaredesigndemo.side;

import softwaredesigndemo.Player;
import softwaredesigndemo.Sides;
import softwaredesigndemo.cards.Card;
import softwaredesigndemo.side.characters.HearthstoneCharacter;
import softwaredesigndemo.side.characters.Hero;
import softwaredesigndemo.utils.Color;

import java.util.Scanner;

public class Side {
    public static final char ENEMY_HERO_SYMBOL = '*';
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
        hero.startTurn();
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
        } while (!hasLost() || !opponentsSide.hasLost());
    }

    private void execute(String choice) {
        char first = choice.charAt(0);
        if (Character.isDigit(first)) {
            int chosenCardIndex = Integer.parseInt(choice.substring(0, 1));
            hand.play(chosenCardIndex, new Sides(this, opponentsSide));
        } else {
            if (Character.isLetter(first)) {
                char attackerSymbol = Character.toUpperCase(first);
                if (isValidAttacker(attackerSymbol)) {
                    char second = choice.charAt(1);
                    if (opponentsSide.isValidAttackee(second)) {
                        HearthstoneCharacter attacker = getAttacker(attackerSymbol);
                        HearthstoneCharacter attackee = opponentsSide.getAttackee(second);
                        attacker.attack(attackee);
                    } else opponentsSide.territory.communicateInvalidAttackee(second);
                } else communicateInvalidAttacker(attackerSymbol);
            }
        }
        disposeOfDeceasedIfAny();
    }

    private void communicateInvalidAttacker(char attackerSymbol) {
        if (attackerSymbol == 'H' && !getHero().canAttack()) System.out.println("Your hero cannot attack (anymore).");
        else territory.communicateInvalidAttacker(attackerSymbol);
    }

    private boolean isValidAttacker(char attackerSymbol) {
        return territory.isValidAttacker(attackerSymbol) || (attackerSymbol == 'H' && getHero().canAttack());
    }

    private HearthstoneCharacter getAttacker(char attackerSymbol) {
        if (attackerSymbol == 'H') return getHero();
        else return territory.getMinion(attackerSymbol);
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
        hand.showDuringGame(this.getManaBar().getAvailableMana());
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
