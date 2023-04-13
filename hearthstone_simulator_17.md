## Stap 17: Unit tests!

Met de documentatie van IntelliJ (https://www.jetbrains.com/help/idea/junit.html#intellij) en
Baeldung (https://www.baeldung.com/junit-5) blijkt dat ik alleen maar een soort
simpele test hoef te maken in mijn
tests-folder, iets als

``` 
@Test
void lambdaExpressions() {
    List numbers = Arrays.asList(1, 2, 3);
    assertTrue(numbers.stream()
      .mapToInt(Integer::intValue)
      .sum() > 5, () -> "Sum should be greater than 5");
}
```

en dat IntelliJ dan automatisch alle dependencies toevoegt. Wel, dat werkt!

Nu alleen nog tests maken die werken op MIJN systeem!

Omdat er veel functionaliteiten zijn wil ik niet de hele code in testen omzetten; er zijn gewoon kwetsbare en minder
kwetsbare gebieden. Maar waar regressie optreedt, moet ik zeker testen!

In pseudocode:

- een charge minion moet zijn eerste turn kunnen aanvallen
- een niet-charge minion moet NIET zijn eerste turn kunnen aanvallen

Ik zou dit ingewikkelder kunnen maken, maar voor een eerste test is dit al goed genoeg. De vraag is alleen hoe ik dit
goed opzet: immers, spellen worden nu random gegenereerd.

Voor de eerste test zou het genoeg zijn als ik had:

1) 1 zijde zonder minions,met zeg 1 mana (noem ik even zijde A)
2) 1 zijde met gewoon een held, de tegenstander (noem ik even zijde B)
3) A heeft 1 kaart, een voodoo doctor. Speelt hem, en probeert aan te vallen. Dat lukt niet (test of het WEL lukt -die
   test moet falen)

Laat ik eerst een testgame kunnen maken...

```
// in Game.java

// for unit testing purposes
private Game(Side firstSide, Side secondSide) {
    this.firstSide = firstSide;
    this.secondSide = secondSide;
    coupleSides();
}

private void coupleSides() {
    firstSide.setOpponentsSide(secondSide);
    secondSide.setOpponentsSide(firstSide);
}

// for unit testing purposes
public static Game createTestGame(Side friendlySide, Side enemySide) {
    return new Game(friendlySide, enemySide);
}
```

En daarvoor is dus ook een testSide nodig

``` 
// in Side.java

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

public static Side createTestSide(Hero hero, Territory territory, Deck deck, ManaBar manaBar, Hand hand, String playerName) {
    return new Side(hero, territory, deck, manaBar, hand, playerName);
}
```

Dus ook nodig: het kunnen maken van een Hero, Territory, Deck, ManaBar, Hand.
-Hero is al okee: je kan al Hero.from(HeroType) gebruiken om een Hero te maken; voorlopig is dat goed genoeg
-Territory: begint ook leeg. Is nu nog voldoende, dus laat ik het maar even zo.
-Deck: Het lijkt me niet handig om voor een test deck 30 kaarten te vragen, noch om ze te shuffelen.

``` 
// in Deck.java

// for unit testing 
private Deck(Card... cards) {
    this.cards = new ArrayList<>(List.of(cards));
}

// for unit testing 
public static Deck createTestDeck(Card... cards) {
    return new Deck(cards);
}
```

Het maken van een Deck met een varargs van Card lijkt me sowieso wel handig. Die truc kan ik ook kopiëren voor Hand...

``` 
// in Hand.java

public Hand() {}

// for unit testing
private Hand(Card... cards) {
    this.cards.addAll(List.of(cards));
}

public static Hand createTestHand(Card... cards) {
    return new Hand(cards);
}
```

Ik moest wel een extra constructor maken, omdat ik anders Hand niet meer normaal kan instantiëren. De laatste: ManaBar,
met een capaciteit

Dus:

``` 
// in ManaBar.java 

public ManaBar() {}

// for unit testing
private ManaBar(int capacity) {
    this.capacity = capacity;
    this.availableMana = capacity;
}

// for unit testing
public static ManaBar createTestManaBar(int capacity) {
    return new ManaBar(capacity);
}
```

En ja, die factory-methoden moeten public static zijn, anders zijn ze niet makkelijk te gebruiken. Pff.. dat is redelijk
wat werk. Compileert alles nog? Ja! Okee, laat ik dan de eerste test bedenken...

``` 
// in ChargeTest.java

@DisplayName("Normal minion cannot attack its first turn")
@Test
void Regular_minion_cannot_attack_its_first_turn() {
    var attackerHero = Hero.from(HeroType.MAGE);
    var attackerTerritory = new Territory();
    var attackerDeck = Deck.createTestDeck();
    var attackerManaBar = ManaBar.createTestManaBar(1);
    var attackerHand = Hand.createTestHand(GameDeck.VOODOO_DOCTOR);

    //createTestSide(Hero hero, Territory territory, Deck deck, ManaBar manaBar, Hand hand, String playerName)
    var attackerSide = Side.createTestSide(attackerHero, attackerTerritory, attackerDeck, attackerManaBar, attackerHand, "attacker");

    var defenderHero = Hero.from(HeroType.WARRIOR);
    var defenderTerritory = new Territory();
    var defenderDeck = Deck.createTestDeck();
    var defenderManaBar = ManaBar.createTestManaBar(2);
    var defenderHand = Hand.createTestHand();
    
    var defenderSide = Side.createTestSide(defenderHero, defenderTerritory, defenderDeck, defenderManaBar, defenderHand, "defender");
    var testGame = Game.createTestGame(attackerSide, defenderSide);
}
```

Dat lijkt me een goede setup, maar ik moet nog steeds een kaart kunnen spelen, zonder dat ik dat aan de speler moet
vragen via de console. Sowieso wil ik eventuele foutmeldingen als String kunnen terugkrijgen, dus moet ik execute wat
laten teruggeven (en aan kunnen laten roepen via een public method)

``` 
// in Side.java

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
            } else response =  communicateInvalidAttacker(attackerSymbol);
        } else response = "'%s' is an invalid command".formatted(choice);
    }
    disposeOfDeceasedIfAny();
    return response;
}

public String testExecute(String command) {
    return execute(command);
}
```

``` 
// in Hand.java

public String play(int index, Sides sides) {
    if (index < 0 || index >= cards.size()) 
        return Color.RED.color("Card number " + index + " does not exist!");
    var chosenCard = cards.get(index);
    if (chosenCard.canPlay(sides)) {
        sides.own().getManaBar().consume(chosenCard.getCost());
        chosenCard.play(sides);
        cards.remove(index);
        return "Playing " + chosenCard.getName() ;
    } else return chosenCard.communicateInvalidPlay(sides);
}
```

Maar er moeten nog meer strings worden teruggegeven...

```  
// in Card.java

public String communicateInvalidPlay(Sides sides) {
    return cost > sides.own().getManaBar().getAvailableMana() ?
            Color.RED.color("You don't have enough mana to play this card!") : null;
}
```

``` 
// in MinionCard.java

@Override
public String communicateInvalidPlay(Sides sides) {
    if (!super.canPlay(sides)) return super.communicateInvalidPlay(sides);
    return Color.RED.color("You can't play any more minions! The board is full!");
}
```

``` 
// in Side.java

private String communicateInvalidAttacker(char attackerSymbol) {
    return attackerSymbol == FRIENDLY_HERO_SYMBOL && !getHero().canAttack() ?
            "Your hero cannot attack (anymore)." : territory.communicateInvalidAttacker(attackerSymbol);
}
```

``` 
// in Territory.java

public String communicateInvalidAttacker(char minionSymbol) {
    int minionIndex = getFriendlyMinionIndex(minionSymbol);
    char standardizedMinionSymbol = (char) (minionIndex + 'A');
    return minionIndex >= minions.size() ?
            "There is no minion '%c'!\n".formatted(standardizedMinionSymbol) :
            Color.RED.color("Minion %c cannot currently attack!\n".formatted(standardizedMinionSymbol));
}


public String communicateInvalidAttackee(char attackeeSymbol) {
    int minionIndex = indexToSymbol.indexOf(attackeeSymbol);
    String message = (attackeeSymbol != Side.ENEMY_HERO_SYMBOL && minionIndex < 0) || minionIndex >= minions.size() ?
            "There is no minion '%c'!\n".formatted(attackeeSymbol) : "A minion with taunt is in the way!\n";
    return Color.RED.color(message);
}
```

Ik wil attacks ook loggen, heb dus ook een naam nodig voor elk HearthstoneCharacter:

``` 
// in HearthstoneCharacter.java 

private final String name;

protected HearthstoneCharacter(int maxHealth, int attack, List<Enhancement> enhancements, String name) {
    this.stats = new Stats(maxHealth, attack, enhancements);
    this.name = name;
}

public String getName() {
    return name;
}

public String attack(HearthstoneCharacter attackee) {
    attackee.takeDamage(stats.getAttack());
    attacksRemainingThisTurn--;
    if (attackee instanceof Minion otherMinion) {
        // heroes CANNOT counterattack
        takeDamage(otherMinion.stats.getAttack());
    }
    return name + " attacks " + attackee.name;
}
```

Wat ook gevolgen heeft voor Hero en Minion.

``` 
// in Hero.java

private Hero(HeroType type) {
    super(MAXIMUM_HP, 0, List.of(), "Your hero");
    this.type = type;
    attacksRemainingThisTurn = 1;
}
```

``` 
// in Minion.java

public Minion(String name, int attack, int health, List<Enhancement> enhancements) {
    super(health, attack, enhancements, name);
    attacksRemainingThisTurn = 0; // won't be really appropriate for a charge minion, but since charge can also be
    // obtained by a spell or lost by silence, in the first turn I will count 1 less for now
}
```

Het enige dat ik nu nog moet doen is de feedback die ik normaal naar stdout printte in play enzo moet redirecten
naar `giveTurn()`.

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
        System.out.println(execute(choice));
    } while (isAlive() && opponentsSide.isAlive());
    endTurn();
}
```

En dan nog de tests! (let wel, ik laat de uiteindelijke versies zien, maar ik begon uiteraard met falende tests, dus dat
een niet-charge minion de vijand de eerste beurt kon beschadigen).

``` 
// in ChargeTest.java

@DisplayName("Normal minion cannot attack its first turn")
@Test
void Regular_minion_cannot_attack_its_first_turn() {
    var attackerHero = Hero.from(HeroType.MAGE);
    var attackerTerritory = new Territory();
    var attackerDeck = Deck.createTestDeck();
    var attackerManaBar = ManaBar.createTestManaBar(1);
    var attackerHand = Hand.createTestHand(GameDeck.VOODOO_DOCTOR);

    var attackerSide = Side.createTestSide(attackerHero, attackerTerritory, attackerDeck, attackerManaBar, attackerHand, "attacker");

    var defenderHero = Hero.from(HeroType.WARRIOR);
    var defenderTerritory = new Territory();
    var defenderDeck = Deck.createTestDeck();
    var defenderManaBar = ManaBar.createTestManaBar(2);
    var defenderHand = Hand.createTestHand();

    var defenderSide = Side.createTestSide(defenderHero, defenderTerritory, defenderDeck, defenderManaBar, defenderHand, "defender");
    attackerSide.setOpponentsSide(defenderSide);

    attackerSide.testExecute("0");
    var feedback2 = attackerSide.testExecute("a*");
    assertTrue(feedback2.contains("cannot currently attack!"));
    assertEquals(30, defenderHero.getHealth());
}

@DisplayName("Charge minion can attack its first turn")
@Test
void Charge_minion_can_attack_its_first_turn() {
    var attackerHero = Hero.from(HeroType.WARRIOR);
    var attackerTerritory = new Territory();
    var attackerDeck = Deck.createTestDeck();
    var attackerManaBar = ManaBar.createTestManaBar(4);
    var attackerHand = Hand.createTestHand(GameDeck.KOK_KRON_ELITE);

    var attackerSide = Side.createTestSide(attackerHero, attackerTerritory, attackerDeck, attackerManaBar, attackerHand, "attacker");

    var defenderHero = Hero.from(HeroType.MAGE);
    var defenderTerritory = new Territory();
    var defenderDeck = Deck.createTestDeck();
    var defenderManaBar = ManaBar.createTestManaBar(2);
    var defenderHand = Hand.createTestHand();

    var defenderSide = Side.createTestSide(defenderHero, defenderTerritory, defenderDeck, defenderManaBar, defenderHand, "defender");
    attackerSide.setOpponentsSide(defenderSide);

    attackerSide.testExecute("0");
    attackerSide.testExecute("a*");
    assertEquals(26, defenderHero.getHealth());
}
```

Dit ziet er allemaal redelijk netjes uit, al voelt het als nogal veel werk aan om alle waarden van de attackerSide en
defenderSide expliciet te initialiseren; de factory-methode `createTestSide()` heeft gewoon erg veel parameters! Waarvan
er veel (zoals Deck) niet altijd hoeven te worden ingevuld. Dat suggereerst een Builder-patroon, maar dat laat ik over
aan de volgende stap!

(Ik kan verder veel 'publics' verwijderen door de ChargeTest in het softwaredesigndemo.side package te plaatsen)

