## Stap 19: Te wapen!

Ik durf nu nog niet te concluderen dat het spel bugvrij is, maar het spel blijft wat saai, zeker voor de warriors:
wapens werken nog niet!

De regels van wapens zijn simpel:

1) als je een wapenkaart speelt wordt je huidige wapen (als je dat hebt) vervangen. Je kan echter geen extra attack
   krijgen als je die beurt al hebt aangevallen.
2) Elke keer dat je met het wapen aanvalt gaat de durability achteruit. Bij durability 0 wordt het wapen vernietigd.

Dat zou dus niet moeilijk te maken moeten zijn!

Al merk ik dat ik wel code zou moeten bekijken na schrijven, ik maak teveel kleine foutjes (misschien beloning voor elke
check?)

Hoe dan ook:

```  
// in WeaponCard.java 

@Override
public void play(Sides sides) {
    sides.own().getHero().equip(new Weapon(attack, durability));
}
```

``` 
// in Weapon.java

public class Weapon {
    private final int attack;
    
    private int durability;

    public Weapon(int attack, int durability) {
        this.attack = attack;
        this.durability = durability;
    }

    public void reduceDurability() {
        durability--;
    }

    public int getDurability() {
        return durability;
    }

    public int getAttack() {
        return attack;
    }
}
```

``` 
// in HearthstoneCharacter.java

public boolean canAttack() {
    return getAttack() > 0 && !isFrozen();
}

abstract protected int getAttack();

public String attack(HearthstoneCharacter attackee) {
    attackee.takeDamage(getAttack());
    attacksRemainingThisTurn--;
    if (attackee instanceof Minion otherMinion) {
        // heroes CANNOT counterattack
        takeDamage(otherMinion.getAttack());
    }
    return name + " attacks " + attackee.name;
}
```

``` 
// in Hero.java

private Weapon weapon;

public void equip(Weapon weapon) {
    this.weapon = weapon;
}

public void show(String playerName, UnaryOperator<String> colorFunction, char symbol) {
    String armorText = armor > 0 ? " [+" + armor + " ARMOR]" : "";
    int attack = getAttack();
    String attackText = attack > 0 ? "<" + attack + " ATTACK>" : "";
    System.out.println(colorFunction.apply("%s (%s): %d HP%s %s(%c)".
            formatted(playerName, getType().name(), getHealth(), armorText, attackText, symbol)));
}

protected int getAttack() {
    int weaponAttack = weapon != null ? weapon.getAttack() : 0;
    return weaponAttack + stats.getAttack();
}

@Override
public String attack(HearthstoneCharacter attackee) {
    String outcome = super.attack(attackee);
    if (weapon != null) {
        weapon.reduceDurability();
        if (weapon.getDurability() <= 0) weapon = null;
    }
    return outcome;
}
```

``` 
// in Stats.java (bugfix)

public Stats changeAttack(int attackChange) {
    return new Stats(attack + attackChange, maxHealth, health, enhancements, properties);
}
```

Nog een klein bugje, `Your hero attacks Your hero` is een beetje raar...

``` 
// in HearthstoneCharacter.java 

public String attack(HearthstoneCharacter attackee) {
    attackee.takeDamage(getAttack());
    attacksRemainingThisTurn--;
    if (attackee instanceof Minion otherMinion) {
        // heroes CANNOT counterattack
        takeDamage(otherMinion.getAttack());
    }
    String attackeeName = attackee.name.equals(DEFAULT_HERO_NAME) ? "the enemy hero" : attackee.name;
    return name + " attacks " + attackeeName;
}
```

``` 
// in Hero.java

protected static String DEFAULT_HERO_NAME = "Your hero";

private Hero(HeroType type) {
    super(0, MAXIMUM_HP, List.of(), DEFAULT_HERO_NAME);
    this.type = type;
    attacksRemainingThisTurn = 1;
}
```

Nog op de TODO-list: Hero powers! Hero powers:

1) werken ongeveer zoals spells (elke klasse hero heeft zijn of haar eigen hero power)
2) kosten 2 mana
3) kunnen (net als attacks) maar 1x per turn gebruikt worden.

``` 
// in Hero.java

private boolean hasUsedHeroPowerThisTurn = false;

@Override
public void startTurn() {
    super.startTurn();
    hasUsedHeroPowerThisTurn = false;
}

public String useHeroPower(Sides sides) {
    if (sides.own().getManaBar().getAvailableMana() < 2)
        return Color.RED.color("You need two mana to use your hero power.");
    if (hasUsedHeroPowerThisTurn) return Color.RED.color("You have already used your hero power this turn!");
    var spell = type.getSpell();
    if (spell.canCast(sides)) {
        spell.cast(sides);
        sides.own().getManaBar().consume(2);
        return "Hero power used!";
    } else return Color.RED.color("Invalid target for your hero power!");
}
```

``` 
// in GameDeck.java

static final TargetedSpell MAGE_HERO_POWER = new TargetedSpell(ANY, (t, s) -> t.takeDamage(1));

static final UntargetedSpell WARRIOR_HERO_POWER = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED, s -> s.own().getHero().increaseArmor(2));
```

``` 
// in HeroType.java

public enum HeroType {
    MAGE(MAGE_HERO_POWER), WARRIOR(WARRIOR_HERO_POWER);

    final private Spell spell;
    
    HeroType(Spell spell) {
        this.spell = spell;
    }

    public Spell getSpell() {
        return spell;
    }
}
```

``` 
// in Side.java

public void giveTurn() {
    startTurn();
    Scanner in = new Scanner(System.in);
    do {
        showStatus();
        System.out.print("Which card do you want to play? (0-9), a letter to attack (b# lets your second " +
                "minion attack the third minion of the opponent), '+' to use your hero power or Q to end your turn: ");
        String choice = in.nextLine();
        if (choice.isBlank()) continue;
        if (choice.equalsIgnoreCase("Q")) return;
        System.out.println(execute(choice));
    } while (isAlive() && opponentsSide.isAlive());
    endTurn();
}

private String execute(String choice) {
    var sides = new Sides(this, opponentsSide);
    if (choice.equals("+")) return hero.useHeroPower(sides);
    char first = choice.charAt(0);
    String response;
    if (Character.isDigit(first)) {
        int chosenCardIndex = Integer.parseInt(choice.substring(0, 1));
        response = hand.play(chosenCardIndex, sides);
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
```

En de consume in ManaBar moet nu ook public worden.

Wel, de Hero powers werken nu. Al zal ik best nog wel wat werk kunnen hebben aan de minion-abilities. Maar dat is van
later zorg- laten we eerst maar weer eens refactoren!
