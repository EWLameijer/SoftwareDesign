Stap 16: Weer wat Mage spells

Fireball!

``` 
// in GameDeck.java
static final TargetClassification ANY = new TargetClassification(TargetType.CHARACTER, SideType.ALL, NO_FURTHER_REQUIREMENTS);
static final TargetedSpell FIREBALL_SPELL = new TargetedSpell(ANY, (t, s) -> t.takeDamage(6));
static final Card FIREBALL = new SpellCard("Fireball", 4, "deal 6 damage", FIREBALL_SPELL);
```

Flamestrike

``` 
// in GameDeck.java
static final UntargetedSpell FLAMESTRIKE_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED, s -> s.opponent().getTerritory().getMinions().forEach(m -> m.takeDamage(4)));
static final Card FLAMESTRIKE = new SpellCard("Flamestrike", 7, "deal 4 damage to all enemy minions", FLAMESTRIKE_SPELL);
```

Frost nova:

``` 
static final UntargetedSpell FROST_NOVA_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED, s -> s.opponent().getTerritory().getMinions().forEach(m -> m.freeze()));
static final Card FROST_NOVA = new SpellCard("Frost Nova", 3, "freeze all enemy minions", FROST_NOVA_SPELL);
```

Dit wordt wat interessanter, omdat ik een .freeze() moet toevoegen. Frozen karakters missen hun eerstvolgende
aanval. (https://hearthstone.fandom.com/wiki/Freeze). Zelfs bij het doorscannen blijken er complicaties te zijn: zo zijn
er karakters met 2 of meer aanvallen per beurt. En als een minion frozen wordt, betekent het niet dat hij automatisch
zijn ene attack verliest en dan gelijk unfrozen wordt: de freeze blijft tot het einde van de beurt.

In pseudocode:

- aan het begin van de beurt krijgt elke minion 1 attack -of meer, als het een windfury minion ofzo is (of bij plaatsing
  als de minion CHARGE heeft)
- freeze zet de status van de minion op Frozen, en trekt 1 van de attacks af
- als er 1 attack of meer over is, unfreezet de minion gelijk. Anders wordt aan het eind van de turn gecheckt of er 0
  attacks zijn; in dat geval unfreezet de minion
- als de minion geen attacks had, krijgt de minion dus -1 +1 = 0 attacks bij de volgende ronde

``` 
// in HearthstoneCharacter.java 

protected int attacksRemainingThisTurn = 0; 

public boolean canAttack() {
    return stats.getAttack() > 0 && attacksRemainingThisTurn > 0;
}

public void attack(HearthstoneCharacter attackee) {
    attackee.takeDamage(stats.getAttack());
    attacksRemainingThisTurn--;
    if (attackee instanceof Minion otherMinion) {
        // heroes CANNOT counterattack
        takeDamage(otherMinion.stats.getAttack());
    }
}

public void startTurn() {
    attacksRemainingThisTurn = 1; // TODO: may need to update for WindFury minions
    stats.countDownTemporaryEnhancements();
}

public void freeze() {
    if (!stats.isFrozen()) {
        attacksRemainingThisTurn--;
        if (attacksRemainingThisTurn <= 0) stats.enhance(Enhancement.FROZEN);
    }
}
```

```
// in MinionProperty.java

public enum MinionProperty {CHARGE, FROZEN, TAUNT}
```

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
```

``` 
// in Stats.java

public boolean isFrozen() {
    return enhancements.contains(Enhancement.FROZEN);
}
```

Laten we nu proberen te 'unfreezen':

```
// in HearthstoneCharacter.java

public void tryUnfreeze() {
    if (attacksRemainingThisTurn >= 0) stats.tryUnfreeze();
}
```

Maar hoe verwijder ik de juiste enhancement? Het getimed maken zal niet werken; het zal soms aan het einde van de beurt
verdwijnen, soms aan het einde van de volgende beurt. Maar als ik een literal maak (Enhancement.FROZEN) kan ik daar op
zoeken. Het 'enige' dat ik dan nog hoef te doen is zorgen dat Stats een enhance krijgt die een Enhancement als argument
neemt!

``` 
// in Stats.java

public void tryUnfreeze() {
    for (int i = enhancements.size() - 1; i >= 0; i--) {
        var enhancement = enhancements.get(i);
        if (enhancement == Enhancement.FROZEN) enhancements.remove(i);
    }
}

public void enhance(Enhancement enhancement) {
    enhancements.add(enhancement);
}
```

Gezien `Which card do you want to play? (0-9), a letter to attack (b# lets your second minion attack the third minion of the opponent), or Q to end your turn: 5
Exception in thread "main" java.lang.IllegalArgumentException: Hand.play() error: 5 is not a valid index!` zal ik
gelijk `play` wat robuuster maken...

``` 
// in Hand.java

public void play(int index, Sides sides) {
    if (index < 0 || index >= cards.size()) {
        Color.RED.println("Card number " + index + " does not exist!");
        return;
    }
    var chosenCard = cards.get(index);
    if (chosenCard.canPlay(sides)) {
        sides.own().getManaBar().consume(chosenCard.getCost());
        System.out.println("Playing " + chosenCard.getName());
        chosenCard.play(sides);
        cards.remove(index);
    } else {
        chosenCard.communicateInvalidPlay(sides);
    }
}
```

Zo, dat lijkt best goed te werken! Freeze/unfreeze moet ik echter nog uittesten. Ik kan het wel grafisch proberen te
maken

``` 
// in Side.java

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
``` 

Ik vul Color aan:

```
// in Color.java

public static UnaryOperator<String> attackStatusColor(HearthstoneCharacter character) {
    if (character.canAttack()) return Color.YELLOW::color;
    if (character.isFrozen()) return Color.CYAN::color;
    return Color.BLUE::color;
}
```

``` 
// in HearthstoneCharacter.java

public boolean isFrozen() {
    return stats.isFrozen();
}
```

Ik laat Territory hier ook naar verwijzen.

``` 
// in Territory.java

private String colorAsFriendly(Minion minion) {
    int minionIndex = minions.indexOf(minion);
    return Color.attackStatusColor(minion).apply(minionDisplayString(minion, (char) (minionIndex + 'A')));
}

```

Wel, ik merk dat er nog een bug is: frozen karakters kunnen nog steeds aanvallen!

``` 
// in HearthstoneCharacter.java

public boolean canAttack() {
    return stats.getAttack() > 0 && attacksRemainingThisTurn > 0 && !isFrozen();
}

public boolean isFrozen() {
    return stats.isFrozen();
}
```

En nu zorgen dat ook het freezing effect goed te zien is op de vijanden... Ik update de methoden zodat ze een
HearthstoneCharacter kunnen nemen, in
plaats van een minion.

``` 
// in Territory.java

public UnaryOperator<String> colorEnemy(HearthstoneCharacter character) {
    if (character.isFrozen()) return Color.CYAN::color;
    return isAttackable(character) ? Color.RED::color : Color.PURPLE::color;
}

private boolean isAttackable(HearthstoneCharacter character) {
    return minions.stream().noneMatch(Minion::hasTaunt) || character.hasTaunt();
}
```

``` 
// in HearthstoneCharacter.java

public boolean hasTaunt() {
        return false; // for hero. Can differ for minions
    }
```

En dan een bug verwijderen die ervoor zorgt dat Charge minions niet meer kunnen chargen...

``` 
// in HearthstoneCharacter.java

protected int attacksRemainingThisTurn;

protected HearthstoneCharacter(int maxHealth, int attack, List<Enhancement> enhancements) {
    this.stats = new Stats(maxHealth, attack, enhancements);
}

public boolean canAttack() {
    return stats.getAttack() > 0 && !isFrozen();
}
```

```  
// in Minion.java

public Minion(String name, int attack, int health, List<Enhancement> enhancements) {
    super(health, attack, enhancements);
    this.name = name;
    attacksRemainingThisTurn = 0; // won't be really appropriate for a charge minion, but since charge can also be
    // obtained by a spell or lost by silence, in the first turn I will count 1 less for now
}

@Override
public boolean canAttack() {
    if (!super.canAttack()) return false;
    if (isFirstTurn) return stats.hasCharge() && attacksRemainingThisTurn >= 0;
    return attacksRemainingThisTurn >= 1;
}
```

``` 
// in Hero.java 

private Hero(HeroType type) {
    super(MAXIMUM_HP, 0, List.of());
    this.type = type;
    attacksRemainingThisTurn = 1;
}

@Override
public boolean canAttack() {
    return super.canAttack() && attacksRemainingThisTurn > 0;
}
```

Dat Charge minions niet meer kunnen chargen is een 'regression bug': oftewel iets dat het eerst wel deed, en nu niet
meer. Zulke dingen zijn altijd zorgelijk als developer, maar kunnen ook een teken zijn dat je programma boven een
bepaalde basiscomplexiteit is gekomen. Mogelijk kan ik dingen beter opsplitsen, maar sowieso wordt het denk ik tijd voor
extra maatregelen tegen bugs: unit tests! Maar dat is dus voor de volgende keer...
