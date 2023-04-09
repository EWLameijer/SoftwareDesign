## Step 12: Spreuken slingeren!

Hearthstone heeft een grote variëteit aan toverspreuken - zelfs in zijn eenvoudigste versie. Maar laten we niet nodeloos
ontmoedigd raken, en eerst proberen te kijken of er overeenkomsten en patronen zijn.

```
final static Card CLEAVE = new SpellCard("Cleave", 2, "deal 2 damage to 2 random enemy minions");
final static Card CHARGE = new SpellCard("Charge", 3, "give a friendly minion +2 attack and Charge");
final static Card EXECUTE = new SpellCard("Execute", 2, "destroy a damaged enemy minion");
final static Card HEROIC_STRIKE = new SpellCard("Heroic Strike", 2, "give hero +4 attack this turn");
final static Card SHIELD_BLOCK = new SpellCard("Shield Block ", 3, "gain 5 armor, draw a card");
final static Card WHIRLWIND = new SpellCard("Whirlwind", 1, "1 damage to ALL minions");
final static Card ARCANE_EXPLOSION = new SpellCard("Arcane Explosion", 2, "deal 1 damage to all enemy minions");
final static Card ARCANE_INTELLECT = new SpellCard("Arcane Intellect", 3, "draw 2 cards");
final static Card ARCANE_MISSILES = new SpellCard("Arcane Missiles", 1, "deal 3 damage randomly split between all enemy characters");
final static Card FIREBALL = new SpellCard("Fireball", 4, "deal 6 damage");
final static Card FLAMESTRIKE = new SpellCard("Flamestrike", 7, "deal 4 damage to all enemy minions");
final static Card FROST_NOVA = new SpellCard("Frost Nova", 3, "freeze all enemy minions");
final static Card FROSTBOLT = new SpellCard("Frostbolt", 2, "deal 3 damage to a character and Freeze it");
final static Card MIRROR_IMAGE = new SpellCard("Mirror Image", 1, "summon 2 0/2 minions with Taunt");
final static Card POLYMORPH = new SpellCard("Polymorph", 4, "transform a minion into a 1/1 sheep");
``` 

Er lijken twee hoofdtypes spreuken te zijn. Of ze hebben een doelwit nodig (zoals Execute of Charge), of ze zoeken hun
eigen doelwit uit. Spreuken kunnen doelwitten hebben als alle (karakters), geallieerde karakters,
vijandelijke karakters, vriendelijke minions, vijandelijke minions, de eigen held, of de vijandelijke held. Nu zijn er
ook spreuken die wat specifieker zijn in hun vereisten, maar het is goed om eenvoudig te beginnen.

De spreuk kan een voorwaarde hebben, en een effect. Zowel de voorwaarde als het effect hangen af van de toestand van het
spel. Een spreuk heeft dus `canCast()` en `cast()`.
Laten we beginnen met 'untargeted spells'.

``` 
// in Spell.java

public abstract class Spell {
}
```

``` 
// in UntargetedSpell.java

public class UntargetedSpell extends Spell {
    final public BiPredicate<Side, Side> canCast;

    final public BiConsumer<Side, Side> cast;

    public UntargetedSpell(BiPredicate<Side, Side> canCast, BiConsumer<Side, Side> cast) {
        this.cast = cast;
        this.canCast = canCast;
    }
}
```

Door velden te definiëren die een functie bevatten heb ik maximale flexibiliteit om spreuken het juiste gedrag te geven;
ik geef de juiste functies gewoon door aan de constructor.

Nu nog de SpellCard een Spell doorgeven, en een TargetedSpell maken...

``` 
public class SpellCard extends Card {
    public SpellCard(String name, int cost, String description, Spell spell) {
        super(name, cost, description);
    }

    @Override
    public void play(Side ownSide, Side opponentsSide) {
    }
}
```

Ik denk dat ik mogelijk TargetedSpells hetzelfde zou kunnen maken als Untargeted spells- alleen dat een TargetedSpell
een dialoog heeft om de target te bepalen, en een veld om de target in op te slaan.

```
// in Spell.java 
public abstract class Spell {
    final public BiPredicate<Side, Side> canCast;

    final public BiConsumer<Side, Side> cast;

    public Spell(BiPredicate<Side, Side> canCast, BiConsumer<Side, Side> cast) {
        this.cast = cast;
        this.canCast = canCast;
    }
}
```

``` 
// in TargetedSpell.java

public class TargetedSpell extends Spell {
    private Character target; 
    
    public TargetedSpell(BiPredicate<Side, Side> canCast, BiConsumer<Side, Side> cast) {
        super(canCast, cast);
    }
}
```

``` 
// in UntargetedSpell.java

public class UntargetedSpell extends Spell {
    public UntargetedSpell(BiPredicate<Side, Side> canCast, BiConsumer<Side, Side> cast) {
        super(canCast, cast);
    }
}
```

Nu nog één voor een de SpellCards aanpassen...

``` 
final static UntargetedSpell CLEAVE_SPELL = new UntargetedSpell(
            (o, e) -> e.getTerritory().getMinionCount() >= 2,
            (o, e) -> e.getTerritory().getRandomMinions(2).forEach(m-> m.takeDamage(2)));
final static Card CLEAVE = new SpellCard("Cleave", 2, "deal 2 damage to 2 random enemy minions", CLEAVE_SPELL);
```

Wat betekent dat Territory en Minion extra methoden moeten krijgen:

``` 
// in Territory.java

public int getMinionCount() {
    return minions.size();
}

public List<Minion> getRandomMinions(int numberOfTargets) {
    if (minions.size() < numberOfTargets)
        throw new IllegalArgumentException("Territory.getRandomMinions() exception: not enough minions to target!");
    var indicesToChooseFrom = new ArrayList<>(IntStream.range(0, minions.size()).boxed().toList());
    Random random = new Random();
    List<Minion> output = new ArrayList<>();
    for (int targetNumber = 0; targetNumber < numberOfTargets; targetNumber++) {
        int targetIndex = random.nextInt(minions.size() - targetNumber);
        output.add(minions.get(indicesToChooseFrom.get(targetIndex)));
        indicesToChooseFrom.remove(targetIndex);
    }
    return output;
}
```

``` 
// in Minion.java

public void takeDamage(int damage) {
   currentHealth -= damage;
}
```

De tweede spell, `CHARGE`, is targeted. Ik heb eerst het idee om een soort statische methode te maken in TargetedSpell
die ik een parameter meegeef met de vereisten, en dan het target vraagt. Maar als het een statische methode is, kan het
het target niet opslaan in het uiteindelijke object! Ik moet dus iets aanpassen in de logica: `canCast` is goed voor een
UntargetedSpell, maar een TargetedSpell moet een `getTarget()` krijgen die een HearthStoneCharacter (of null, netter is
dus een Optional<HearthStoneCharacter>) teruggeeft.

Ik pas 'Spell' aan:

```
// in Spell.java

public abstract class Spell {
    final public BiConsumer<Side, Side> cast;

    public Spell(BiConsumer<Side, Side> cast) {
        this.cast = cast;
    }
}
```

en dan UntargetedSpell:

``` 
// in UntargetedSpell.java

public class UntargetedSpell extends Spell {
    final public BiPredicate<Side, Side> canCast;
    
    public UntargetedSpell(BiPredicate<Side, Side> canCast, BiConsumer<Side, Side> cast) {
        super(cast);
        this.canCast = canCast;
    }
}
```

en tenslotte TargetedSpell:

``` 
// in TargetedSpell.java

public class TargetedSpell extends Spell { 
    private final BiFunction<Side, Side, Optional<HearthStoneCharacter>> getTarget;

    public TargetedSpell(BiFunction<Side, Side, Optional<HearthStoneCharacter>> getTarget, BiConsumer<Side, Side> cast) {
        super(cast);
        this.getTarget = getTarget;
    }
}
```

En dan nu de goede methode(s) in TargetedSpell toevoegen:

``` 
// in TargetedSpell.java

public static Optional<HearthStoneCharacter> getTarget(TargetClassification classification, Side ownSide, Side opponentsSide) {
    // if it is a minion-targeting spell, check if there are any suitable minions in the first place!
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
        Optional<HearthStoneCharacter> targetCharacter = getValidTarget(targetSymbol.charAt(0), classification, ownSide, opponentsSide);
        if (targetCharacter.isPresent()) return targetCharacter;
    } while (true);
}

private static Optional<HearthStoneCharacter> getValidTarget(char targetSymbol, TargetClassification classification, Side ownSide, Side opponentsSide) {
    if (Character.isLetter(targetSymbol)) {
        // is friendly
        char standardizedTargetSymbol = Character.toUpperCase(targetSymbol);
        if (standardizedTargetSymbol == FRIENDLY_HERO_SYMBOL) {
            return classification.sideType().matches(SideType.ALLY) && classification.targetType().matches(TargetType.HERO) ?
                    Optional.of(ownSide.getHero()) : Optional.empty();
        } else return ownSide.getTerritory().getMinionCount() > standardizedTargetSymbol - 'A' &&
                classification.sideType().matches(SideType.ALLY) && classification.targetType().matches(TargetType.MINION) ?
                Optional.of(ownSide.getTerritory().getMinion(standardizedTargetSymbol)) : Optional.empty();
    } else {
        if (targetSymbol == Side.ENEMY_HERO_SYMBOL)
            return classification.sideType().matches(SideType.ENEMY) && classification.targetType().matches(TargetType.HERO) ?
                    Optional.of(opponentsSide.getHero()) : Optional.empty();
        else {
            int targetIndex = Territory.indexToSymbol.indexOf(targetSymbol);
            if (targetIndex < 0) return Optional.empty();
            return opponentsSide.getTerritory().getMinionCount() > targetIndex &&
                    classification.sideType().matches(SideType.ENEMY) && classification.targetType().matches(TargetType.MINION) ?
                    Optional.of(opponentsSide.getTerritory().getMinion(targetSymbol)) : Optional.empty();
        }
    }
}
```

Ik heb het gevoel dat ik zeker bij de tweede wat zou kunnen refactoren en dedupliceren, maar voorlopig laat ik dit blok
maar zo. Nu de ondersteunende methoden schrijven:

``` 
// TargetClassification.java (record, 1 parameter in plaats van 2)

public record TargetClassification(TargetType targetType, SideType sideType) {
}
```

De hasMinions en de FRIENDLY_HERO_SYMBOL (die waarschijnlijk later ergens anders heenmoet)

```
// in TargetedSpell.java

private static final char FRIENDLY_HERO_SYMBOL = 'H';

private static boolean hasMinions(Side... sides) {
    return Arrays.stream(sides).anyMatch(s -> s.getTerritory().getMinionCount() >= 1);
}
```

Ik maak matches() methodes in de enums:

``` 
// in SideType.java

public enum SideType {
    ALL, ALLY, ENEMY;

    boolean matches(SideType other) {
        if (this == ALL) return true;
        else return this == other;
    }
}
```

``` 
// in TargetType.java

public enum TargetType {
    CHARACTER, HERO, MINION;

    boolean matches(TargetType other) {
        if (this == CHARACTER) return true;
        else return this == other;
    }
}
```

Side heeft nu ook een `getHero()` nodig...

``` 
// in Side.java

public HearthStoneCharacter getHero() {
    return hero;
}
```

Maar nu: geef de friendly minion +2 attack en charge. Nu weet ik dat er iets als silence bestaat in Hearthstone, dus
mogelijk is het beter de properties (zoals TAUNT en CHARGE) niet als een set te zien die een minion al dan niet heeft,
maar als een serie/lijst 'enchants/enhancements', waarvan elk de eigenschappen en base stats aanpast. Ik verander
properties dus in enhancements, en maak er een lijst van. Elke enhancement krijgt een methode die Stats van een minion
omzet - dus dingen als TAUNT toevoegt, of +2 attack.

Dit betekent dat ik de stats (attack, health) en properties (charge, taunt) niet meer als gewone velden van een
HearthstoneCharacter dien te zien, maar als een soort apart iets dat aan een HearthstoneCharacter wordt 'vastgeplakt'.
Dus maak ik een aparte klasse, Stats.

```
// in Stats.java

public class Stats {
    private final int maxHealth;

    private final int attack;

    private final List<Enhancement> enhancements;

    private final Set<MinionProperty> properties = new HashSet<>();

    public Stats(int health, int attack, List<Enhancement> enhancements) {
        this.maxHealth = health;
        this.attack = attack;
        this.enhancements = enhancements;
    }

    int getMaxHealth() {
        return getEnhancedStats().maxHealth;
    }

    int getAttack() {
        return getEnhancedStats().attack;
    }

    private Stats getEnhancedStats() {
        var enhancedStats = this;
        for (var enhancement : enhancements) {
            enhancedStats = enhancement.apply(enhancedStats);
        }
        return enhancedStats;
    }

    public Stats addProperty(MinionProperty property) {
        var statsCopy = new Stats(maxHealth, attack, enhancements);
        statsCopy.properties.add(property);
        return statsCopy;
    }

    boolean hasCharge() {
        return getEnhancedStats().properties.contains(MinionProperty.CHARGE);
    }

    boolean hasTaunt() {
        return getEnhancedStats().properties.contains(MinionProperty.TAUNT);
    }
}
```

``` 
// Enhancement.java

public enum Enhancement {
    CHARGE(s -> s.addProperty(MinionProperty.CHARGE)), TAUNT(s -> s.addProperty(MinionProperty.TAUNT));

    private final UnaryOperator<Stats> transform;

    Enhancement(UnaryOperator<Stats> transform) {
        this.transform = transform;
    }

    public Stats apply(Stats stats) {
        return transform.apply(stats);
    }
}
```

Maar terwijl dit beter zou moeten zijn, is er nog iets kapot: want de health van minions is niet altijd hetzelfde als
hun maxhealth. Wat zijn de regels?

- health is gelijk aan maxHealth als de minion wordt geplaatst
- bij normale schade wordt health kleiner
- als er een 'enchantment' is die health(dwz maxHealth) verhoogt, wordt de health ook met zoveel verhoogd.
- als er een 'enhancement' is die health(dwz maxHealth) verlaagt, wordt de health alleen verlaagd als hij nu hoger is
  dan maxHealth.

Dit wordt complex, dus ik probeer nu weer pseudocode.

- Heroes en Minions hebben base stats (bv 3/5 bij de Shieldmasta) - dit zijn de Card stats.
- Minions kunnen enhancements hebben (kaartteksten als CHARGE en TAUNT)
- Bij een Silence verdwijnen alle enhancements (dus TAUNT wel, de 3/5 base stats niet)
- Een HearthstoneCharacter kan een enhancement krijgen. Elke enhancement kan een property toevoegen of iets veranderen
  aan de base stats.
- Je zou dus kunnen zeggen: een HearthstoneCharacter heeft ActualStats, die de MaxHealth en Health en Attack en andere
  dingen levert
    - die actualStats heeft intern een set properties (zoals Charge) en waarden zoals health en maxHealth. Die worden
      allemaal aan de buitenwereld teruggegeven.
    - actualStats heeft een enhance(Enhancement) die de huidige enhanced stats verandert. Damage en healing veranderen
      die stats uiteraard ook (in elk geval de health). Als de maxHealth verhoogd wordt, wordt Health met dezelfde
      hoeveelheid verhoogd.
    - bij Silence verdwijnen alle properties en worden de maxHealth en attack weer teruggezet op de base stats; health
      wordt gezet op het minimum van de huidige health en de maxHealth.
- Maar: er zijn ook temporary buffs (zoals van de raid leader, of van de Stormwind Commander, die +1/+1 geeft) En er
  zijn ook tijdelijke buffs, die maar 1 of een paar turns duren. Als die enhancedStats alleen maar de som zouden zijn
  van alle buffs ooit is het lastig ze ongedaan te maken.
- Je hebt dus sowieso base stats, en een lijst enhancements, die gelinkt kunnen zijn aan een Source
- bij bepaalde events (een minion wordt vernietigd of getransformeerd, een nieuwe beurt) moeten de stats van alle
  minions opnieuw worden berekend.

Ik moet het denk ik nog makkelijker maken: een concreet voorbeeld.

1) ik plaats een frostwolf grunt (2/2M2 (2M2=2 health, maxhealth 2). Taunt); base stats 2/2, taunt als property
2) ik speel "charge": de grunt krijgt nu 4/2M2, properties taunt, charge [2/2] [T,C,[2,0]PERM]
3) ik val een boar ofzo aan (1/1). Stats nu 4/1M2 properties taunt, charge. [2/1] [T,C,[2,0]PERM] // damage tast de base
   health aan!
4) Ik speel een raid leader: stats nu 5/1M2 properties taunt, charge [2/1] [T,C,[2,0]PERM,[1,0]RL] = [5/1M2]TC
5) ik speel een Stormwind Commander (+1/+1) nu 6/2M3, properties taunt, charge [2/1M2]+[T,C,[2,0]PERM,[1,0]RL,[1,1]SC]
   => [6/2M3]TC
6) de stormwind commander wordt vernietigd: nu 5/2M2, properties taunt, charge.

Wel, de belangrijkste conclusie is dat Enhancement waarschijnlijk geen enum moet worden maar een object dat ergens
gemaakt wordt en kan luisteren naar veranderingen.

Ik koppel dus health in Stats los van maxHealth:

```
// in Stats.java

public class Stats {
    // TODO: Need some kind of enhancement method that increases health the FIRST time, but not on subsequent calculations.
    private final int maxHealth;

    private final int attack;

    private final List<Enhancement> enhancements;

    private int health;

    private final Set<MinionProperty> properties = new HashSet<>();

    public Stats(int maxHealth, int attack, List<Enhancement> enhancements) {
        this.maxHealth = maxHealth;
        health = maxHealth;
        this.attack = attack;
        this.enhancements = enhancements;
    }

    private Stats(int maxHealth, int health, int attack, List<Enhancement> enhancements) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.attack = attack;
        this.enhancements = enhancements;
    }

    int getMaxHealth() {
        return getEnhancedStats().maxHealth;
    }

    int getHealth() {
        return health;
    }

    int getAttack() {
        return getEnhancedStats().attack;
    }

    private Stats getEnhancedStats() {
        var enhancedStats = this;
        for (var enhancement : enhancements) {
            enhancedStats = enhancement.apply(enhancedStats);
        }
        return enhancedStats;
    }

    public Stats addProperty(MinionProperty property) {
        var statsCopy = new Stats(maxHealth, health, attack, enhancements);
        statsCopy.properties.add(property);
        return statsCopy;
    }

    boolean hasCharge() {
        return getEnhancedStats().properties.contains(MinionProperty.CHARGE);
    }

    boolean hasTaunt() {
        return getEnhancedStats().properties.contains(MinionProperty.TAUNT);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }
}
```

Ik los overigens ook nog een bug op in Hand die ik bij testen tegenkwam... (moet >= size() zijn)

``` 
// in Hand.java

public void play(int index, Side ownSide, Side opponentsSide) {
    if (index < 0 || index > cards.size())
    if (index < 0 || index >= cards.size())
        throw new IllegalArgumentException("Hand.play() error: " + index + " is not a valid index!");
    var chosenCard = cards.get(index);
    if (chosenCard.canPlay(ownSide, opponentsSide)) {
```

Ik update HearthstoneCharacter:

``` 
// in HearthstoneCharacter.java

public class HearthstoneCharacter {
    final protected Stats stats;
    
    protected HearthstoneCharacter(int maxHealth, int attack, List<Enhancement> enhancements) {
        this.stats = new Stats(maxHealth, attack, enhancements);
    }

    public int getHealth() {
        return stats.getHealth();
    }

    public void takeDamage(int damage) {
        stats.takeDamage(damage);
    }
}
```

En dus update ik ook Hero en Minion

```
// in Hero.java

private Hero(HeroType type) {
    super(MAXIMUM_HP, 0, List.of());
    this.type = type;
}

public void takeExhaustionDamage() {
    Color.RED.println("Out of cards! You take " + ++exhaustionDamage + "damage!");
    takeDamage(exhaustionDamage);
}
```

``` 
// in Minion.java 

private boolean attackedThisTurn = false;

private boolean isFirstTurn = true;

public boolean canAttack() {
    return !attackedThisTurn && (!isFirstTurn || stats.hasCharge());
}

public void readyForAttack() {
    attackedThisTurn = false;
    isFirstTurn = false;
}

public boolean hasTaunt() {
    return stats.hasTaunt();
}

public void attack(HearthstoneCharacter attackee) {
    attackee.takeDamage(stats.getAttack());
    attackedThisTurn = true;
    if (attackee instanceof Minion otherMinion) {
        // heroes CANNOT counterattack
        takeDamage(otherMinion.stats.getAttack());
    }
}

public int getAttack() {
    return stats.getAttack();
}
```

MinionCard pas ik ook aan omdat minion nu enhancements heeft:

``` 
// in MinionCard.java 

private final List<Enhancement> enhancements;

public MinionCard(String name, int cost, String description, int attack, int health, List<Enhancement> enhancements) {
    super(name, cost, description);
    this.attack = attack;
    this.health = health;
    this.enhancements = enhancements;
}

private Minion toMinion() {
    return new Minion(name, attack, health, enhancements);
}
```

En SpellCard geeft ik een aparte constructor zodat ik het spel kan blijven testen zolang ik nog niet alle spells heb
geïmplementeerd...

``` 
// in SpellCard.java 

private final Spell spell;

public SpellCard(String name, int cost, String description, Spell spell) {
    super(name, cost, description);
    this.spell = spell;
}

public SpellCard(String name, int cost, String description) {
    super(name, cost, description);
    this.spell = null;
}
```

Dit was een heel stuk, en nu heb ik nog maar 1 spell toegevoegd! Maar hopelijk gaat het met deze voorbereiding de
volgende keren sneller...


