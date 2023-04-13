package softwaredesigndemo.side;

import softwaredesigndemo.Player;
import softwaredesigndemo.Sides;
import softwaredesigndemo.cards.Card;
import softwaredesigndemo.side.characters.HearthstoneCharacter;
import softwaredesigndemo.side.characters.Hero;
import softwaredesigndemo.utils.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.UnaryOperator;

public class Side {
    public static final char ENEMY_HERO_SYMBOL = '*';
    public static final char FRIENDLY_HERO_SYMBOL = 'H';

    private final Hero hero;
    private final Territory territory;
    private final Deck deck;
    private final ManaBar manaBar;
    private final Hand hand;
    private final String playerName;
    private Side opponentsSide;

    public Side(Player player) {
        var playerDeck = player.getDeck();
        this.hero = Hero.from(playerDeck.heroType());
        this.deck = new Deck(playerDeck.cards());
        this.playerName = player.getName();
        // below code to make it easier to make Side easier to unit-test while still having the integrity of final fields.
        hand = new Hand();
        manaBar = new ManaBar();
        territory = new Territory();
    }

    // for unit tests
    private Side(Hero hero, Territory territory, Deck deck, ManaBar manaBar, Hand hand, String playerName) {
        this.hero = hero;
        this.territory = territory;
        this.deck = deck;
        this.manaBar = manaBar;
        this.hand = hand;
        this.playerName = playerName;
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
        System.out.println(playerName + ", please keep or mulligan your cards...");
        deck.shuffle();
        for (int i = 0; i < numCardsToMulligan; i++) hand.add(deck.draw());
        hand.mulligan(deck);
    }

    public void giveCard(Card card) {
        hand.add(card);
    }

    public void giveTurn() {
        startTurn();
        Scanner in = new Scanner(System.in);
        do {
            showStatus();
            System.out.print("Which card do you want to play? (0-9), a letter to attack (b# lets your second " +
                    "minion attack the third minion of the opponent), or Q to end your turn: ");
            String choice = in.nextLine();
            if (choice.isBlank()) continue;
            if (choice.equalsIgnoreCase("Q")) return;
            System.out.println(execute(choice));
        } while (isAlive() && opponentsSide.isAlive());
        endTurn();
    }

    private void endTurn() {
        for (var character : getCharacters()) {
            character.tryUnfreeze();
        }
    }

    List<HearthstoneCharacter> getCharacters() {
        List<HearthstoneCharacter> allCharacters = new ArrayList<>(territory.getMinions());
        allCharacters.add(hero);
        return allCharacters;
    }

    private void startTurn() {
        System.out.printf("It is %s's turn!%n", playerName);
        manaBar.startTurn();
        territory.startTurn();
        hero.startTurn();
        drawCard();
    }

    public void drawCard() {
        if (deck.canDraw()) {
            hand.add(deck.draw());
        } else {
            hero.takeExhaustionDamage();
        }
    }

    private String execute(String choice) {
        char first = choice.charAt(0);
        String response;
        if (Character.isDigit(first)) {
            int chosenCardIndex = Integer.parseInt(choice.substring(0, 1));
            response = hand.play(chosenCardIndex, new Sides(this, opponentsSide));
        } else {
            if (Character.isLetter(first)) {
                char attackerSymbol = Character.toUpperCase(first);
                if (isValidAttacker(attackerSymbol)) {
                    char second = choice.charAt(1);
                    if (opponentsSide.isValidAttackee(second)) {
                        var attacker = getAttacker(attackerSymbol);
                        var attackee = opponentsSide.getAttackee(second);
                        response = attacker.attack(attackee);
                    } else response = opponentsSide.territory.communicateInvalidAttackee(second);
                } else response = communicateInvalidAttacker(attackerSymbol);
            } else response = "'%s' is an invalid command".formatted(choice);
        }
        disposeOfDeceasedIfAny();
        return response;
    }

    private String communicateInvalidAttacker(char attackerSymbol) {
        return attackerSymbol == FRIENDLY_HERO_SYMBOL && !getHero().canAttack() ?
                "Your hero cannot attack (anymore)." : territory.communicateInvalidAttacker(attackerSymbol);
    }

    private boolean isValidAttacker(char attackerSymbol) {
        return territory.isValidAttacker(attackerSymbol) || (attackerSymbol == FRIENDLY_HERO_SYMBOL && getHero().canAttack());
    }

    private HearthstoneCharacter getAttacker(char attackerSymbol) {
        if (attackerSymbol == FRIENDLY_HERO_SYMBOL) return getHero();
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
        manaBar.show();
        UnaryOperator<String> colorFunction = Color.attackStatusColor(hero);
        hero.show(playerName, colorFunction, FRIENDLY_HERO_SYMBOL);
        hand.showDuringGame(this.getManaBar().getAvailableMana());
    }

    private void showAsEnemy() {
        var heroColorFunction = territory.colorEnemy(hero);
        hero.show(playerName, heroColorFunction, ENEMY_HERO_SYMBOL);
        territory.showAsEnemy();
    }

    public ManaBar getManaBar() {
        return manaBar;
    }

    public boolean isAlive() {
        return hero.getHealth() > 0;
    }

    public Hero getHero() {
        return hero;
    }

    public HearthstoneCharacter getRandomTarget() {
        int minionCount = territory.getMinionCount();
        Random random = new Random();
        int chosenIndex = random.nextInt(minionCount + 1); // the opposing hero is also a valid target
        return chosenIndex == minionCount ? hero : territory.getMinions().get(chosenIndex);
    }

    // for unit testing
    static Side createTestSide(Hero hero, Territory territory, Deck deck, ManaBar manaBar, Hand hand, String playerName) {
        return new Side(hero, territory, deck, manaBar, hand, playerName);
    }

    public String testExecute(String command) {
        return execute(command);
    }
}
