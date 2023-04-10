Stap 15: Weer refactoren!

Ik ga nu van onder naar boven langs de klassen. In Main valt me op dat het creëren van de players enigszins gedupliceerd
lijkt. Kan ik een en ander dedupliceren?

```
// in Main.java

public static void main(String[] args) {
    var player1 = new Player("Player 1");
    var player2 = new Player("Player 2");
    var game = new Game(player1, player2);
    game.play();
}
```

``` 
// in Player.java

public class Player {
    private final String name; //= askForName("Player 1");
    private final GameDeck deck; //
    
    private final static Scanner in = new Scanner(System.in);
    
    public Player(String provisionalName) {
        name = askForName(provisionalName);
        deck = askForDeck();
    }

    private static GameDeck askForDeck() {
        System.out.print("Warrior (W) or Mage(anything else)? ");
        var isWarrior = in.nextLine().toUpperCase().startsWith("W");
        return isWarrior ? warriorDeck : mageDeck;
    }

    private static String askForName(String provisionalName) {
        System.out.print(provisionalName + ", enter your name: ");
        return in.nextLine().trim();
    }
}
```

Dit ziet er mooier uit, maar waarom de Players aanmaken en ze dan gelijk in Game stoppen? Dat kan Game toch ook doen.
Daarnaast: de decks staan nu in Main. Beter een GameDecks-klasse maken? Of gewoon de statische velden in GameDeck
zetten...

``` 
// in GameDeck.java

public record GameDeck(HeroType heroType, List<Card> cards) {

    // https://outof.cards/hearthstone/decks/29388-warrior-basic-starter-deck
    static final Card ARCANITE_REAPER = new WeaponCard("Arcanite Reaper", 5, "", 5, 2);

    static final UntargetedSpell CLEAVE_SPELL = new UntargetedSpell(
            s -> s.opponent().getTerritory().getMinionCount() >= 2,
            s -> s.opponent().getTerritory().getRandomMinions(2).forEach(m -> m.takeDamage(2)));
    static final Card CLEAVE = new SpellCard("Cleave", 2, "deal 2 damage to 2 random enemy minions", CLEAVE_SPELL);

    static final BiPredicate<HearthstoneCharacter, Sides> NO_FURTHER_REQUIREMENTS = (t, s) -> true;
    static final TargetClassification ALLIED_MINION = new TargetClassification(TargetType.MINION, SideType.ALLY, NO_FURTHER_REQUIREMENTS);
    static final TargetedSpell CHARGE_SPELL = new TargetedSpell(
            ALLIED_MINION,
            (t, s) -> t.enhance(stats -> stats.addProperty(MinionProperty.CHARGE).changeAttack(2)));
    static final Card CHARGE = new SpellCard("Charge", 3, "give a friendly minion +2 attack and Charge", CHARGE_SPELL);

    static final TargetedSpell EXECUTE_SPELL = new TargetedSpell(
            new TargetClassification(TargetType.MINION, SideType.ENEMY, (t, s) -> t.getHealth() < t.getMaxHealth()),
            (t, s) -> t.destroy());
    static final Card EXECUTE = new SpellCard("Execute", 2, "destroy a damaged enemy minion", EXECUTE_SPELL);
    static final Card FIERY_WAR_AXE = new WeaponCard("Fiery War Axe", 2, "", 3, 2);
    static final Card FROSTWOLF_GRUNT = new MinionCard("Frostwolf Grunt", 2, "taunt", 2, 2, List.of(Enhancement.TAUNT));
    static final Card FROSTWOLF_WARLORD = new MinionCard("Frostwolf Warlord", 5, "gain +1/+1 for each other friendly minion on the battlefield", 4, 4, List.of());
    static final Card GURUBASHI_BERSERKER = new MinionCard("Gurubashi Berserker", 5, "whenever this minion takes damage, gain +3 attack", 2, 7, List.of());

    static final Predicate<Sides> NO_PRECONDITIONS_FOR_UNTARGETED = s -> true;

    static final UntargetedSpell HEROIC_STRIKE_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> s.own().getHero().enhance(stats -> stats.changeAttack(4), 1));
    static final Card HEROIC_STRIKE = new SpellCard("Heroic Strike", 2, "give hero +4 attack this turn", HEROIC_STRIKE_SPELL);
    static final Card KOK_KRON_ELITE = new MinionCard("Kok'kron Elite", 4, "", 4, 3, List.of(Enhancement.CHARGE));
    static final Card RAID_LEADER = new MinionCard("Raid Leader", 3, "all your other minions have +1 attack", 2, 2, List.of());
    static final Card SEN_JIN_SHIELDMASTA = new MinionCard("Sen'jin Shieldmasta", 4, "", 3, 5, List.of(Enhancement.TAUNT));

    static final UntargetedSpell SHIELD_BLOCK_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> {
                s.own().drawCard();
                s.own().getHero().increaseArmor(5);
            });
    static final Card SHIELD_BLOCK = new SpellCard("Shield Block ", 3, "gain 5 armor, draw a card", SHIELD_BLOCK_SPELL);
    static final Card WARSONG_COMMANDER = new MinionCard("Warsong Commander", 3, "when you summon a minion with 3 or less attack, give it Charge", 2, 3, List.of());

    static final UntargetedSpell WHIRLWIND_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> {
                s.own().getTerritory().getMinions().forEach(m -> m.takeDamage(1));
                s.opponent().getTerritory().getMinions().forEach(m -> m.takeDamage(1));
            });

    static final Card WHIRLWIND = new SpellCard("Whirlwind", 1, "1 damage to ALL minions", WHIRLWIND_SPELL);

    static final List<Card> basicWarriorCards = List.of(
            ARCANITE_REAPER, ARCANITE_REAPER, CLEAVE, CLEAVE, CHARGE, CHARGE, EXECUTE, EXECUTE, FIERY_WAR_AXE,
            FIERY_WAR_AXE, FROSTWOLF_GRUNT, FROSTWOLF_GRUNT, FROSTWOLF_WARLORD, FROSTWOLF_WARLORD,
            GURUBASHI_BERSERKER, GURUBASHI_BERSERKER, HEROIC_STRIKE, HEROIC_STRIKE, KOK_KRON_ELITE, KOK_KRON_ELITE,
            RAID_LEADER, RAID_LEADER, SEN_JIN_SHIELDMASTA, SEN_JIN_SHIELDMASTA, SHIELD_BLOCK, SHIELD_BLOCK,
            WARSONG_COMMANDER, WARSONG_COMMANDER, WHIRLWIND, WHIRLWIND);

    public static final GameDeck WARRIOR_DECK = new GameDeck(HeroType.WARRIOR, basicWarriorCards);
    //https://outof.cards/hearthstone/decks/29385-mage-basic-starter-deck

    static final UntargetedSpell ARCANE_EXPLOSION_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> s.opponent().getTerritory().getMinions().forEach(m -> m.takeDamage(1)));
    static final Card ARCANE_EXPLOSION = new SpellCard("Arcane Explosion", 2, "deal 1 damage to all enemy minions", ARCANE_EXPLOSION_SPELL);

    static final UntargetedSpell ARCANE_INTELLECT_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> {
                s.own().drawCard();
                s.own().drawCard();
            });

    static final Card ARCANE_INTELLECT = new SpellCard("Arcane Intellect", 3, "draw 2 cards", ARCANE_INTELLECT_SPELL);

    static final UntargetedSpell ARCANE_MISSILES_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> {
                for (int i = 0; i < 3; i++) {
                    s.opponent().getRandomTarget().takeDamage(1);
                }
            });
    static final Card ARCANE_MISSILES = new SpellCard("Arcane Missiles", 1, "deal 3 damage randomly split between all enemy characters", ARCANE_MISSILES_SPELL);
    static final Card ARCHMAGE = new MinionCard("Archmage", 6, "Spell Damage + 1", 4, 7, List.of());
    static final Card DARKSCALE_HEALER = new MinionCard("Darkscale Healer", 5, "Battlecry: restore 2 health to all friendly characters", 4, 5, List.of());
    static final Card FIREBALL = new SpellCard("Fireball", 4, "deal 6 damage");
    static final Card FLAMESTRIKE = new SpellCard("Flamestrike", 7, "deal 4 damage to all enemy minions");
    static final Card FROST_NOVA = new SpellCard("Frost Nova", 3, "freeze all enemy minions");
    static final Card FROSTBOLT = new SpellCard("Frostbolt", 2, "deal 3 damage to a character and Freeze it");
    static final Card KOBOLD_GEOMANCER = new MinionCard("Kobold Geomancer", 2, "Spell Damage  +1", 2, 2, List.of());
    static final Card MAGMA_RAGER = new MinionCard("Magma Rager", 3, "", 5, 1, List.of());
    static final Card MIRROR_IMAGE = new SpellCard("Mirror Image", 1, "summon 2 0/2 minions with Taunt");
    static final Card POLYMORPH = new SpellCard("Polymorph", 4, "transform a minion into a 1/1 sheep");
    static final Card VOODOO_DOCTOR = new MinionCard("Voodoo Doctor", 1, "battlecry: restore 2 health", 2, 1, List.of());
    static final Card WATER_ELEMENTAL = new MinionCard("Water Elemental", 4, "freeze any character damaged by this minion", 3, 6, List.of());


    static final List<Card> basicMageCards = List.of(
            ARCANE_EXPLOSION, ARCANE_EXPLOSION, ARCANE_INTELLECT, ARCANE_INTELLECT, ARCANE_MISSILES, ARCANE_MISSILES,
            ARCHMAGE, ARCHMAGE, DARKSCALE_HEALER, DARKSCALE_HEALER, FIREBALL, FIREBALL, FLAMESTRIKE, FLAMESTRIKE,
            FROST_NOVA, FROST_NOVA, FROSTBOLT, FROSTBOLT, KOBOLD_GEOMANCER, KOBOLD_GEOMANCER,
            MAGMA_RAGER, MAGMA_RAGER, MIRROR_IMAGE, MIRROR_IMAGE, POLYMORPH, POLYMORPH, VOODOO_DOCTOR, VOODOO_DOCTOR,
            WATER_ELEMENTAL, WATER_ELEMENTAL);
    public static final GameDeck MAGE_DECK = new GameDeck(HeroType.MAGE, basicMageCards);
}
```

In Player dus nu `return isWarrior ? GameDeck.WARRIOR_DECK : GameDeck.MAGE_DECK;`

De Main wordt piepklein - zoals ook hoort bij grote programma's.

``` 
// in Main.java

public class Main {
    public static void main(String[] args) {
        var game = new Game();
        game.play();
    }
}
```

De Game-constructor wordt wat aangepast...

``` 
// in Game.java

public Game() {
    var playerA = new Player("Player 1");
    var playerB = new Player("Player 2");
    Random random = new Random();
    if (random.nextInt(2) == 0) {
        // ...

```

En dat is het! Toch nog een refactoring. Laten we naar boven gaan en kijken naar GameDeck en andere...

In Game kan ik play() wat compacter maken; de break vervangen door de loop-conditie aan te pakken, en de mulligan de
naam mee te geven. Al moet ik niet vergeten, nu Player geen record meer is, een paar getters te maken voor Player!

``` 
// in Player.java

public String getName() {
    return name;
}

public GameDeck getDeck() {
    return deck;
}
```

``` 
// in Game.java

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
    } while (!firstSide.hasLost() && !secondSide.hasLost());
}
```

``` 
// in Side.java

public Side(Player player) {
    var playerDeck = player.getDeck();
    this.hero = Hero.from(playerDeck.heroType());
    this.deck = new Deck(playerDeck.cards());
    this.playerName = player.getName();
}

public void mulligan(int numCardsToMulligan) {
    System.out.println(playerName + ", please keep or mulligan your cards...");
    deck.shuffle();
    for (int i = 0; i < numCardsToMulligan; i++) hand.add(deck.draw());
    hand.mulligan(deck);
}
```

Ik verklein de `getTarget` (methodes moeten niet te groot groeien, en het is makkelijker een middelgrote methode te
snoeien dan een reuzenmethode)

``` 
// in TargetedSpell.java

public static Optional<HearthstoneCharacter> getTarget(TargetClassification classification, Sides sides) {
    // if it is a minion-targeting spell, check if there are any suitable minions in the first place!
    var ownSide = sides.own();
    var opponentsSide = sides.opponent();

    if (classification.targetType() == TargetType.MINION && !canBeCastAtAll(classification, ownSide, opponentsSide)) 
        return Optional.empty();
    
    // during the game, both heroes are alive, so spell-casting in the other cases is always possible
    Scanner in = new Scanner(System.in);
    do {
        System.out.print("Please select the target to cast on (Q to abort): ");
        String targetSymbol = in.nextLine();
        if (targetSymbol.equalsIgnoreCase("Q")) return Optional.empty();
        if (targetSymbol.length() < 1) continue;
        Optional<HearthstoneCharacter> targetCharacter = getValidTarget(targetSymbol.charAt(0), classification, ownSide, opponentsSide);
        if (targetCharacter.isPresent() && classification.isValid().test(targetCharacter.get(), sides))
            return targetCharacter;
    } while (true);
}

private static boolean canBeCastAtAll(TargetClassification classification, Side ownSide, Side opponentsSide) {
    return switch (classification.sideType()) {
        case ALLY -> hasMinions(ownSide);
        case ENEMY -> hasMinions(opponentsSide);
        case ALL -> hasMinions(ownSide, opponentsSide);
    };
}
```

In Territory kunnen de communication-stukken wat eenvoudiger omdat er telkens toch maar 2 mogelijkheden zijn:

``` 
// in Territory.java

public void communicateInvalidAttacker(char minionSymbol) {
    int minionIndex = getFriendlyMinionIndex(minionSymbol);
    char standardizedMinionSymbol = (char) (minionIndex + 'A');
    if (minionIndex >= minions.size()) 
        System.out.printf("There is no minion '%c'!\n", standardizedMinionSymbol);
    else Color.RED.println("Minion %c cannot currently attack!\n".formatted(standardizedMinionSymbol));
}

public void communicateInvalidAttackee(char attackeeSymbol) {
    int minionIndex = indexToSymbol.indexOf(attackeeSymbol);
    if ((attackeeSymbol != Side.ENEMY_HERO_SYMBOL && minionIndex < 0) || minionIndex >= minions.size())
        System.out.printf("There is no minion '%c'!\n", attackeeSymbol);
    else Color.RED.println("A minion with taunt is in the way!\n");
}
```

Verder suggereert IDEA dat hasLost() altijd inverted is - die hernoem ik dus naar isAlive(). Ik factor daarnaast een
startTurn-methode uit (vind ik leesbaarder).

``` 
// in Side.java

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
        execute(choice);
    } while (isAlive() && opponentsSide.isAlive());
}

private void startTurn() {
    System.out.printf("It is %s's turn!%n", playerName);
    manaBar.startTurn();
    territory.startTurn();
    hero.startTurn();
    drawCard();
}
```

Ik splits ook de mulligan op:

``` 
// in Hand.java

public void mulligan(Deck deck) {
    showAsMulligan();
    System.out.println("\nPlease give the numbers of the cards you want to mulligan (swap) (like '1 3'): ");
    Scanner in = new Scanner(System.in);
    String[] numbers = in.nextLine().split(" ");

    if (!numbers[0].isEmpty()) {
        mulliganCards(deck, numbers);
        System.out.println("Your new cards:");
        showAsMulligan();
    }
}

private void mulliganCards(Deck deck, String[] numbers) {
    ArrayList<Integer> validNumbers = new ArrayList<>(Arrays.stream(numbers).map(Integer::parseInt).filter(n -> n >= 0 && n < cards.size()).toList());
    List<Integer> replacementIndices = new ArrayList<>();
    do {
        int nextRandom = random.nextInt(deck.size());
        if (!replacementIndices.contains(nextRandom)) replacementIndices.add(nextRandom);
    } while (replacementIndices.size() < validNumbers.size());

    for (int replacement = 0; replacement < validNumbers.size(); replacement++) {
        int naturalCardIndex = validNumbers.get(replacement);
        var cardToSwap = cards.get(naturalCardIndex);
        int deckSwapPosition = replacementIndices.get(replacement);
        cards.set(naturalCardIndex, deck.get(deckSwapPosition));
        deck.set(deckSwapPosition, cardToSwap);
    }
}
```

Ik hernoem verder armorString tot armorText (namen van typen gebruiken in variabelennamen is zelden nodig of nuttig,
meestal is het het beste je bij termen uit het probleemdomein te houden).

In Card maak ik de toString ook iets mooier (zodat ik geen losse komma aan het eind meer heb)

``` 
// in Card.java

public String toString() {
    String separatedDescription = description.isBlank() ? "" : ", " + description;
    return name + ": " + cost + " mana" + separatedDescription;
}
```

Ik check of het programma nog runt, wat het geval blijkt te zijn. Mooi! Toch nog een paar refactoringen. Kijkend naar
mijn TODO.txt: door naar het implementeren van Fireball!


