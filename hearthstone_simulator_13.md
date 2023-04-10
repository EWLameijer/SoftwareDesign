## Stap 13: Charge!

Laten we nu nog eens de Charge proberen.

Ik besluit dat het het gemakkelijkste is aan een targetedSpell een record mee te geven dat aangeeft welk doel toegestaan
is, een een lambda die een target kan aannemen. Omdat Java op dit moment alleen nog maar BiFunctions en zo lijkt te
hebben, maak ik ook een record aan dat de Sides samenvat (genaamd 'Sides')

``` 
// in TargetedSpell.java

private final TargetClassification targetClassification;

BiConsumer<HearthstoneCharacter, Sides> spellEffect;

private HearthstoneCharacter target = null;

public TargetedSpell(TargetClassification targetClassification, BiConsumer<HearthstoneCharacter, Sides> spellEffect) {
    this.spellEffect = spellEffect;
    this.targetClassification = targetClassification;
}

public boolean canCast(Sides sides) {
    Optional<HearthstoneCharacter> optionalTarget = getTarget(targetClassification, sides);
    if (optionalTarget.isEmpty()) return false;
    target = optionalTarget.get();
    return true;
}

public void cast(Sides sides) {
    if (target == null) throw new IllegalStateException("TargetedSpell.cast() exception: no target!");
    spellEffect.accept(target, sides);
}

public static Optional<HearthstoneCharacter> getTarget(TargetClassification classification, Sides sides) {
    // if it is a minion-targeting spell, check if there are any suitable minions in the first place!
    var ownSide = sides.own();
    var opponentsSide = sides.opponent();
```

``` 
// in Sides.java (nieuw!)

public record Sides(Side own, Side opponent) {
}
```

`canCast` en `cast` is waarschijnlijk wat ik voor elke Spell wil: ik zet ze in Spell, en geef in TargetedSpell en
UntargetedSpell aan dat ik ze wil overriden.

``` 
// in Spell.java

public abstract class Spell {
    abstract public boolean canCast(Sides sides);

    public abstract void cast(Sides sides);
}
```

``` 
// in UntargetedSpell.java

public class UntargetedSpell extends Spell {
    final private Predicate<Sides> isCastingPossible;

    final private Consumer<Sides> spellEffect;

    public UntargetedSpell(Predicate<Sides> isCastingPossible, Consumer<Sides> spellEffect) {
        this.isCastingPossible = isCastingPossible;
        this.spellEffect = spellEffect;
    }

    @Override
    public boolean canCast(Sides sides) {
        return isCastingPossible.test(sides);
    }

    @Override
    public void cast(Sides sides) {
        if (!isCastingPossible.test(sides))
            throw new IllegalStateException("UntargetedSpell.cast() exception: cannot cast this spell!");
        spellEffect.accept(sides);
    }
}
```

en tenslotte in TargetedSpell

``` 
// in TargetedSpell.java

@Override
public boolean canCast(Sides sides) {
    Optional<HearthstoneCharacter> optionalTarget = getTarget(targetClassification, sides);
    if (optionalTarget.isEmpty()) return false;
    target = optionalTarget.get();
    return true;
}

@Override
public void cast(Sides sides) {
    if (target == null) throw new IllegalStateException("TargetedSpell.cast() exception: no target!");
    spellEffect.accept(target, sides);
}
```

Die `canCast` en `cast` moeten dus ook kunnen worden aangeroepen. Dus in Card.java (ook de argumenten aanpassen aan het
nieuwe Sides-record)

``` 
// in Card.java 

public boolean canPlay(Sides sides) {
    return cost <= sides.own().getManaBar().getAvailableMana();
}

public abstract void play(Sides sides);

public void communicateInvalidPlay(Sides sides) {
    if (cost > sides.own().getManaBar().getAvailableMana())
        Color.RED.println("You don't have enough mana to play this card!");
}
```

En in SpellCard.java (waar de `play` en `canPlay` methoden dus ook aangepast moeten worden)..

``` 
// in SpellCard.java

@Override
public void play(Side ownSide, Side opponentsSide) {
public boolean canPlay(Sides sides) {
    if (!super.canPlay(sides)) return false;
    return spell.canCast(sides);
}

@Override
public void play(Sides sides) {
    spell.cast(sides);
}
```

Dat ik nu Sides hebt betekent ook aanpassingen aan WeaponCard en MinionCard...

``` 
// in WeaponCard.java

@Override
public void play(Sides sides) {
}
```

```
// in MinionCard.java 

@Override
public void play(Sides sides) {
    var ownSide = sides.own();
    if (ownSide.getTerritory().canAddMinion()) {
        ownSide.getTerritory().addMinion(toMinion());
    } else throw new IllegalArgumentException("MinionCard.play() exception: board is full!");
}

@Override
public boolean canPlay(Sides sides) {
    return super.canPlay(sides) && sides.own().getTerritory().canAddMinion();
}

@Override
public void communicateInvalidPlay(Sides sides) {
    if (!super.canPlay(sides)) super.communicateInvalidPlay(sides);
    else Color.RED.println("You can't play any more minions! The board is full!");
}
```

De `CLEAVE_SPELL` verandert ook, omdat de signature van UntargetedSpell verandert..

``` 
// in Main.java

static final UntargetedSpell CLEAVE_SPELL = new UntargetedSpell(
        s -> s.opponent().getTerritory().getMinionCount() >= 2,
        s -> s.opponent().getTerritory().getRandomMinions(2).forEach(m -> m.takeDamage(2)));
```

Maar laten we nu eens kijken naar hoe een `CHARGE_SPELL` eruit zou moeten zien.

``` 
// in Main.java

static final TargetedSpell CHARGE_SPELL = new TargetedSpell(
        ALLIED_MINION,
        (t, s) -> t.enhance(stats -> stats.addProperty(MinionProperty.CHARGE).changeAttack(2)));
```

Dat wil zeggen dat ik op een HearthstoneCharacter een `enhance` methode moet maken

``` 
// in HearthstoneCharacter.java

public void enhance(UnaryOperator<Stats> transform) {
    stats.enhance(transform);
}
```

En dus ook bij Stats...

``` 
// in Stats.java 

public void enhance(UnaryOperator<Stats> transform) {
    enhancements.add(new Enhancement(transform));
}

public Stats changeAttack(int attackChange) {
    return new Stats(maxHealth, health, attack + attackChange, enhancements);
}
```

Ik wil `Enhancement` (stond al in de TODO) geen enum meer maken, maar flexibeler. Ik pas Enhancement aan:

``` 
// in Enhancement.java

public class Enhancement {

    public static final Enhancement TAUNT = new Enhancement(s -> s.addProperty(MinionProperty.TAUNT));

    public static final Enhancement CHARGE = new Enhancement(s -> s.addProperty(MinionProperty.CHARGE));
```

De constructor die de transform als argument neemt mag blijven. Ik kan het spel echter nog niet starten, omdat ook de
signature van `Hand.play` moet worden aangepast:

``` 
// in Hand.java 

public void play(int index, Sides sides) {
    if (index < 0 || index >= cards.size())
        throw new IllegalArgumentException("Hand.play() error: " + index + " is not a valid index!");
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

Dus ook in Side.execute: `hand.play(chosenCardIndex, new Sides(this, opponentsSide));`

Ik run alles om te testen, waaruit blijkt dat het toevoegen van een enhancement een Exception geeft, omdat List een
ImmutableCollection is (maar dus wel een `add` heeft) - vervelend! Ik pas Stats aan.

```
// in Stats.java
 
private final ArrayList<Enhancement> enhancements;

public Stats(int maxHealth, int attack, List<Enhancement> enhancements) {
    this.maxHealth = maxHealth;
    health = maxHealth;
    this.attack = attack;
    this.enhancements = new ArrayList<>(enhancements);
}

private Stats(int maxHealth, int health, int attack, List<Enhancement> enhancements) {
    this.maxHealth = maxHealth;
    this.health = health;
    this.attack = attack;
    this.enhancements = new ArrayList<>(enhancements);
}
```

Ook worden minions niet opgeruimd als ze gedood zijn door een spell. Ik zet het opruimen nu elders in de logica.

``` 
// in Side.java

private void execute(String choice) {
    char first = choice.charAt(0);
    if (Character.isDigit(first)) {
        int chosenCardIndex = Integer.parseInt(choice.substring(0, 1));
        hand.play(chosenCardIndex, new Sides(this, opponentsSide));
    } else {
        if (Character.isLetter(first)) {
            char attackerSymbol = Character.toUpperCase(first);
            if (territory.isValidAttacker(first) || (attackerSymbol == 'H' && getHero().canAttack())) {
                char second = choice.charAt(1);
                if (opponentsSide.isValidAttackee(second)) {
                    HearthstoneCharacter attacker = getAttacker(attackerSymbol);
                    HearthstoneCharacter attackee = opponentsSide.getAttackee(second);
                    attacker.attack(attackee);
                } else opponentsSide.territory.communicateInvalidAttackee(second);
            } else territory.communicateInvalidAttacker(first);
        }
    }
    disposeOfDeceasedIfAny();
}
```

En dan wil ik execute implementeren. Die een DAMAGED enemy minion nodig heeft. Het hebben van een simpele record met
SideType en TargetType is dus niet meer genoeg; er moet dus een lambda worden meegegeven, op basis waarvan moet worden
bepaald of een target geschikt is.

``` 
// in TargetClassification.java

public record TargetClassification(TargetType targetType, SideType sideType,
                                   BiPredicate<HearthstoneCharacter, Sides> isValid) {
}
```

``` 
// in Main.java

static final BiPredicate<HearthstoneCharacter, Sides> NO_FURTHER_REQUIREMENTS = (t, s) -> true;
static final TargetClassification ALLIED_MINION = new TargetClassification(TargetType.MINION, SideType.ALLY, NO_FURTHER_REQUIREMENTS);
```

Ik pas ook `getTarget` aan om die kennis te kunnen gebruiken

``` 
// in TargetedSpell.java

public static Optional<HearthstoneCharacter> getTarget(TargetClassification classification, Sides sides) {
    // if it is a minion-targeting spell, check if there are any suitable minions in the first place!
    var ownSide = sides.own();
    var opponentsSide = sides.opponent();

    if (classification.targetType() == TargetType.MINION) {
        boolean canBeCast = switch (classification.sideType()) {
            case ALLY -> hasMinions(ownSide);
            case ENEMY -> hasMinions(opponentsSide);
            case ALL -> hasMinions(ownSide, opponentsSide);
        };
        if (!canBeCast) return Optional.empty();
    }
    // during the game, both heroes are alive, so spell-casting in the other cases is always possible
    Scanner in = new Scanner(System.in);
    do {
        System.out.print("Please select the target to cast on (Q to abort): ");
        String targetSymbol = in.nextLine();
        if (targetSymbol.equalsIgnoreCase("Q")) return Optional.empty();
        if (targetSymbol.length() < 1) continue;
        Optional<HearthstoneCharacter> targetCharacter = getValidTarget(targetSymbol.charAt(0), classification, ownSide, opponentsSide);
        if (targetCharacter.isPresent() && classification.isValid().test(targetCharacter.get(), sides)) // HIER DE EXTRA TEST!!!
            return targetCharacter;
    } while (true);
}
```

Dus in Main.java

``` 
// in Main.java

static final TargetedSpell EXECUTE_SPELL = new TargetedSpell(
        new TargetClassification(TargetType.MINION, SideType.ENEMY, (t, s) -> t.getHealth() < t.getMaxHealth()),
        (t, s) -> t.destroy());
static final Card EXECUTE = new SpellCard("Execute", 2, "destroy a damaged enemy minion", EXECUTE_SPELL);
```

Ik heb dus een `getMaxHealth` en een `destroy` nodig in HearthstoneCharacter.

``` 
// in HearthstoneCharacter 

public int getMaxHealth() {
    return stats.getMaxHealth();
}

public void destroy() {
    stats.destroy();
}
```

Die dingen delegeren aan Stats!

``` 
// in Stats.java

public int getMaxHealth() {
    return maxHealth;
}

public void destroy() {
    health = 0;
}
```

Er zijn helaas nog een paar bugs: de CHARGE-kaart werkt niet goed omdat kennelijk de properties nog niet meegegeven
worden bij het bepalen van de (enhanced) Stats. Dat moet dus ook opgelost worden.

``` 
// in Stats.java

private Stats(int maxHealth, int health, int attack, List<Enhancement> enhancements, Set<MinionProperty> properties) {
    this.maxHealth = maxHealth;
    this.health = health;
    this.attack = attack;
    this.enhancements = new ArrayList<>(enhancements);
    this.properties.addAll(properties);
}

public Stats addProperty(MinionProperty property) {
    var statsCopy = new Stats(maxHealth, health, attack, enhancements, properties);
    statsCopy.properties.add(property);
    return statsCopy;
}
```

Voor de 'quality of life' besluit ik ook speelbare kaarten groen te kleuren (buiten de mulligan)

``` 
// in Hand.java

private void show(int availableMana, boolean gameHasStarted) {
    for (int i = 0; i < cards.size(); i++) {
        var chosenCard = cards.get(i);
        // don't color a card green during the mulligan
        Color playableColor = gameHasStarted && chosenCard.getCost() <= availableMana ? Color.GREEN : Color.BLACK;
        playableColor.println(i + ". " + chosenCard);
    }
}

public void showDuringGame(int availableMana) {
    show(availableMana, true);
}

private void showAsMulligan() {
    show(0, false);
}
```

```
// in Color.java
RESET("\u001B[0m"), BLACK("\u001B[30m"), BLUE("\u001B[34m"), GREEN("\u001B[32m"),
PURPLE("\u001B[35m"), RED("\u001B[31m"), YELLOW("\u001B[33m");
```

Ik maak het spel ook robuuster voor als ik een te hoog nummer probeer te mulligannen (zeg 1 5)

``` 
// in Hand.java

public void mulligan(Deck deck) {
    showAsMulligan();
    System.out.println("\nPlease give the numbers of the cards you want to mulligan (swap) (like '1 3'): ");
    Scanner in = new Scanner(System.in);
    String[] numbers = in.nextLine().split(" ");

    if (!numbers[0].isEmpty()) {
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
        System.out.println("Your new cards:");
        showAsMulligan();
    }
}
```

Ik zorg er ook voor dat het spel sneller eindigt als een held wordt verslagen! (dan moet de turn ook over zijn!)

```
// in Side.java

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
    } while (!hasLost() || !opponentsSide.hasLost());
}
```

Dan wil ik nog één spell oppakken: Heroic Strike.

```
// in Main.java
 
static final Predicate<Sides> NO_PRECONDITIONS_FOR_UNTARGETED = s -> true;

static final UntargetedSpell HEROIC_STRIKE_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
        s -> s.own().getHero().enhance(stats -> stats.changeAttack(4), 1));
```

Heroic Strike geeft slechts 1 beurt 4 meer attack, dus ik moet iets bedenken om enhancements tijdelijk te maken. Ik
overweeg enhancements te laten 'luisteren' naar een soort turn counter en ze dan te verwijderen, maar iets dat zichzelf
uit een lijst verwijdert voelt wat raar aan. Dus kies ik ervoor om bij elke turn de lijst door te gaan, om te kijken of
er enhancements verwijderd kunnen worden.

Ik maak dus wel TemporaryEnhancement, die met een `isActive` aangeeft of hij verwijderd kan worden.

``` 
// in TemporaryEnhancement.java

public class TemporaryEnhancement extends Enhancement {
    private int turns;

    TemporaryEnhancement(UnaryOperator<Stats> transform, int turns) {
        super(transform);
        this.turns = turns;
    }

    @Override
    public boolean isActive() {
        return turns > 0;
    }

    public void countDown() {
        turns--;
    }
}
```

De originele Enhancement moet dus ook een `isActive` krijgen (waarom niet alleen in temporaryEnhancement? Omdat er
mogelijk ook aura's van andere minions als Raid Leader moeten kunnen verdwijnen)

```
// in Enhancement.java

public boolean isActive() {
    return true;
}
```

Stats moeten die enhancements dus kunnen opruimen.

``` 
// in Stats.java

public void countDownTemporaryEnhancements() {
    for (Enhancement enhancement : enhancements) {
        if (enhancement instanceof TemporaryEnhancement temporaryEnhancement) {
            temporaryEnhancement.countDown();
        }
    }
    for (int i = enhancements.size() - 1; i >= 0; i--) {
        var enhancement = enhancements.get(i);
        if (!enhancement.isActive()) enhancements.remove(i);
    }
}
```

(ik tel van boven naar beneden om verwijderen makkelijker te maken)

Deze countDown wordt uiteraard aangeroepen in Minion

``` 
// in Minion.java

public void startTurn() {
    attackedThisTurn = false;
    isFirstTurn = false;
    stats.countDownTemporaryEnhancements();
}
```

En dan moet een HearthstoneCharacter uiteraard nog worden enhanced:

``` 
// in HearthstoneCharacter.java

public void enhance(UnaryOperator<Stats> transform, int turns) {
    stats.enhance(transform, turns);
}
```

``` 
// in Stats.java

public void enhance(UnaryOperator<Stats> transform, int turns) {
    enhancements.add(new TemporaryEnhancement(transform, turns));
}
```

Een fundamentelere wijziging is dat een hero nu ook een attack moet krijgen! En ook een Hero kan maar 1x aanvallen, de
attack-logica moet dus deels van Minion naar HearthstoneCharacter, en mogelijk ook wat complexer...

``` 
// in HearthstoneCharacter.java

protected boolean attackedThisTurn = false;

public boolean canAttack() {
    return stats.getAttack() > 0 && !attackedThisTurn;
}

public void attack(HearthstoneCharacter attackee) {
    attackee.takeDamage(stats.getAttack());
    attackedThisTurn = true;
    if (attackee instanceof Minion otherMinion) {
        // heroes CANNOT counterattack
        takeDamage(otherMinion.stats.getAttack());
    }
}
```

``` 
// in Minion.java 

@Override
public boolean canAttack() {
    return super.canAttack() && (!isFirstTurn || stats.hasCharge());
}
```

`Side.execute()` moet ook anders, want Heroes kunnen nu ook aanvallen:

``` 
// in Side.java 

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
```

``` 
// in Territory.java

public void communicateInvalidAttacker(char minionSymbol) {
    int minionIndex = getMinionIndex(minionSymbol);
    char standardizedMinionSymbol = (char) (minionIndex + 'A');
    if (minionIndex >= minions.size()) {
        System.out.printf("There is no minion '%c'!\n", standardizedMinionSymbol);
        return; // TO SOLVE A BUG, there should only be one error message!
    }
    if (!minions.get(minionIndex).canAttack())
        Color.RED.println("Minion %c cannot currently attack!\n".formatted(standardizedMinionSymbol));
}

// solving a bug: there was a wrong message if you tried to attack a hero while a taunt minion was in the way.
public void communicateInvalidAttackee(char attackeeSymbol) {
    int minionIndex = indexToSymbol.indexOf(attackeeSymbol);
    if ((attackeeSymbol != Side.ENEMY_HERO_SYMBOL && minionIndex < 0) || minionIndex >= minions.size())
        System.out.printf("There is no minion '%c'!\n", attackeeSymbol);
    if (attackeeSymbol == Side.ENEMY_HERO_SYMBOL || !isAttackable(minions.get(minionIndex)))
        Color.RED.println("A minion with taunt is in the way!\n");
}
```

En uiteraard zorgen dat ook bij een hero de startTurn() goed verloopt...

``` 
// in Minion.java

@Override
public void startTurn() {
    super.startTurn();
    isFirstTurn = false;
}
```

``` 
// in HearthstoneCharacter.java 

public void startTurn() {
    attackedThisTurn = false;
    stats.countDownTemporaryEnhancements();
}
```

``` 
// in Side.java

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
    // ...
```

Dat was veel werk! Hopelijk wordt met het verbeteren van ons model de volgende Spell, Shield Block, minder gedoe...