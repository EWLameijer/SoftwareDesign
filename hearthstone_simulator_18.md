## Stap 18: Beter testen met builders

Als een klasse veel constructorparameters heeft kan een Builder-klasse handig zijn (zoek op internet naar Java builder).
Dus ik maak een SideBuilder-klasse.

``` 
// in SideBuilder.java

public class SideBuilder {
    private final Hero hero;
    
    private final Territory territory;

    private final Deck deck;

    private Hand hand;

    private ManaBar manaBar;

    public SideBuilder() {
        hero = Hero.from(HeroType.WARRIOR);
        territory = new Territory();
        deck = Deck.createTestDeck();
        hand = Hand.createTestHand();
        manaBar = ManaBar.createTestManaBar(0);
    }

    public SideBuilder setMana(int mana) {
        manaBar = ManaBar.createTestManaBar(mana);
        return this;
    }

    public SideBuilder setHand(Card... cards) {
        hand = Hand.createTestHand(cards);
        return this;
    }

    public Side build(String name) {
        return Side.createTestSide(hero, territory, deck, manaBar, hand, name);
    }
}
```

Builder-klassen zijn best wel interessant; ze hebben doorgaans een constructor, een build() methode die het gewenste
object teruggeeft, en een paar setX()-methoden die `this` teruggeven, zodat je een fluent interface krijgt (ofwel: je
kunt commando's aan elkaar koppelen met . in plaats van over verschillende regels met ;).

Hoe dan ook: ChargeTest wordt nu een stuk compacter! (ik factor ook de defender-code uit)

``` 
// in ChargeTest 

@DisplayName("Normal minion cannot attack its first turn")
@Test
void Regular_minion_cannot_attack_its_first_turn() {
    var attackerSide = new SideBuilder().setMana(1).setHand(GameDeck.VOODOO_DOCTOR).build("attacker");
    Side defenderSide = defaultDefenderFor(attackerSide);

    attackerSide.testExecute("0");
    var feedback2 = attackerSide.testExecute("a*");
    
    assertTrue(feedback2.contains("cannot currently attack!"));
    assertEquals(30, defenderSide.getHero().getHealth());
}

@DisplayName("Charge minion can attack its first turn")
@Test
void Charge_minion_can_attack_its_first_turn() {
    var attackerSide = new SideBuilder().setMana(4).setHand(GameDeck.KOK_KRON_ELITE).build("attacker");
    Side defenderSide = defaultDefenderFor(attackerSide);

    attackerSide.testExecute("0");
    attackerSide.testExecute("a*");

    assertEquals(26, defenderSide.getHero().getHealth());
}

private static Side defaultDefenderFor(Side attackerSide) {
    var defenderSide = new SideBuilder().build("defender");
    attackerSide.setOpponentsSide(defenderSide);
    return defenderSide;
}
```

En dat allemaal wegens de Frost Nova-spell! Maar goed, laten we nu gelijk de frostbolt doen... Die is nu makkelijk.

``` 
// in GameDeck.java

static final TargetedSpell FROSTBOLT_SPELL = new TargetedSpell(ANY, (t, s) -> {
    t.takeDamage(3);
    t.freeze();
}
);

static final Card FROSTBOLT = new SpellCard("Frostbolt", 2, "deal 3 damage to a character and Freeze it", FROSTBOLT_SPELL);
```

En Mirror Image

``` 
// in GameDeck.java

static final Minion MIRROR_IMAGE_MINION = new Minion("Mirror image", 0, 2, List.of(Enhancement.TAUNT));

static final UntargetedSpell MIRROR_IMAGE_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED, s -> {
    var ownTerritory = s.own().getTerritory();
    for (int minionCount=0; minionCount <2; minionCount++) {
        if (ownTerritory.canAddMinion()) ownTerritory.addMinion(MIRROR_IMAGE_MINION);
    }
});

static final Card MIRROR_IMAGE = new SpellCard("Mirror Image", 1, "summon 2 0/2 minions with Taunt", MIRROR_IMAGE_SPELL);
```

En Polymorph

```
// in GameDeck.java

static final TargetClassification ANY_MINION = new TargetClassification(TargetType.MINION, SideType.ALL, NO_FURTHER_REQUIREMENTS);

static final TargetedSpell POLYMORPH_SPELL = new TargetedSpell(ANY_MINION, (t, s) -> t.polymorph("sheep", 1, 1));
static final Card POLYMORPH = new SpellCard("Polymorph", 4, "transform a minion into a 1/1 sheep", POLYMORPH_SPELL);
```

Ik moet polymorph introduceren - dat betekent dat bepaalde velden niet meer final kunnen zijn (of ik zou de minion
moeten vervangen, wat denk ik lastiger is). Ik wissel gelijk attack en health om, attack/health is de normale volgorde
in Hearthstone (en ook alfabetisch!)

``` 
// in HearthstoneCharacter.java

protected Stats stats; // cannot be final due to polymorph

private String name; // cannot be final due to polymorph

protected HearthstoneCharacter(int attack, int maxHealth, List<Enhancement> enhancements, String name) {
    this.stats = new Stats(attack, maxHealth, enhancements);
    this.name = name;
}

public void polymorph(String newName, int attack, int health) {
    name = newName;
    stats = new Stats(attack, health, List.of());
}
```

``` 
// in Hero.java

private Hero(HeroType type) {
    super(0, MAXIMUM_HP, List.of(), "Your hero");
    this.type = type;
    attacksRemainingThisTurn = 1;
}
```

``` 
// in Minion.java

public Minion(String name, int attack, int health, List<Enhancement> enhancements) {
    super(attack, health, enhancements, name);
    attacksRemainingThisTurn = 0; // won't be really appropriate for a charge minion, but since charge can also be
    // obtained by a spell or lost by silence, in the first turn I will count 1 less for now
}
```

``` 
// in Stats.java

public Stats(int attack, int maxHealth, List<Enhancement> enhancements) {
    this.maxHealth = maxHealth;
    health = maxHealth;
    this.attack = attack;
    this.enhancements = new ArrayList<>(enhancements);
}

private Stats(int attack, int maxHealth, int health, List<Enhancement> enhancements, Set<MinionProperty> properties) {
    this.maxHealth = maxHealth;
    this.health = health;
    this.attack = attack;
    this.enhancements = new ArrayList<>(enhancements);
    this.properties.addAll(properties);
}
```

En tenslotte nog de coin!

``` 
// in GameDeck.java 

static final UntargetedSpell COIN_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED, s -> s.own().getManaBar().increase(1));
public static final Card COIN = new SpellCard("The Coin", 0, "Gain 1 mana crystal this turn only", COIN_SPELL);
```

``` 
// in ManaBar.java

void increase(int amount) {
    if (amount < 0)
        throw new IllegalArgumentException("ManaBar.increase() error: " + amount + "  is out of range!");
    availableMana += amount;
}
```

``` 
// in Game.java 

public void play() {
    System.out.println("The game starts! " + firstSide.getPlayerName() + " begins!");
    firstSide.mulligan(3);
    secondSide.mulligan(4);
    secondSide.giveCard(GameDeck.COIN);
    int turn = 0;
    do {
        turn++;
        Side activeSide = turn % 2 == 1 ? firstSide : secondSide;
        activeSide.giveTurn();
    } while (firstSide.isAlive() && secondSide.isAlive());
}
```

Ik test, haal een klein foutje eruit (had eerst increase bij COIN_SPELL op -1 gezet).
En Cleave op 1 minion geeft nu null- dat kan beter.
Ook heeft mijn Kok'ron elite nu 3 attack - kennelijk had ik niet OVERAL de signature veranderd.
En de Mirror Images hebben hetzelfde teken... Waarschijnlijk moet ik ze uniek maken...

Hoe dan ook

``` 
// in Card.java

public String communicateInvalidPlay(Sides sides) {
    String message = cost > sides.own().getManaBar().getAvailableMana() ?
            "You don't have enough mana to play this card!" : "This card's preconditions have not been met!";
    return Color.RED.color(message);
}
```

``` 
// in Stats.java 

public Stats addProperty(MinionProperty property) {
    var statsCopy = new Stats(attack, maxHealth, health, enhancements, properties);
    statsCopy.properties.add(property);
    return statsCopy;
}
```

``` 
// in Hand.java

public void mulligan(Deck deck) {
    showAsMulligan();
    System.out.println("\nPlease give the numbers of the cards you want to mulligan (swap) (like '1 3'): ");
    String line = getNumberString();
    String[] numbers = line.split(" ");
    if (!numbers[0].isEmpty()) {
        mulliganCards(deck, numbers);
        System.out.println("Your new cards:");
        showAsMulligan();
    }
}

private static String getNumberString() {
    Scanner in = new Scanner(System.in);
    do {
        String line = in.nextLine();
        if (!line.chars().allMatch(c -> Character.isWhitespace(c) || Character.isDigit(c)))
            Color.RED.println("Enter digits and spaces only!");
        else return line;
    } while (true);
}
```