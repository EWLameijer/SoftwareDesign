## Stap 14: Shield Block

De volgende spell is Shield Block, een UntargetedSpell die een kaart trekt en armor verhoogt.

Ik factor de drawCard uit bij Side:

``` 
// in Side.java

public void giveTurn() {
    System.out.printf("It is %s's turn!%n", playerName);
    manaBar.startTurn();
    territory.startTurn();
    hero.startTurn();
    drawCard();
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

public void drawCard() {
    if (deck.canDraw()) {
        hand.add(deck.draw());
    } else {
        hero.takeExhaustionDamage();
    }
}
```

``` 
// in Main.java
 
static final UntargetedSpell SHIELD_BLOCK_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED, 
        s -> { s.own().drawCard(); s.own().getHero().increaseArmor(5);});
static final Card SHIELD_BLOCK = new SpellCard("Shield Block ", 3, "gain 5 armor, draw a card", SHIELD_BLOCK_SPELL);
```

Helden hebben ook armor, die damage kan absorberen. Ik zie nu overigens dat getHero() een HearthstoneCharacter
teruggeeft, geen Hero! Dat verander ik...

``` 
// in Side.java

public Hero getHero() {
    return hero;
}
```

``` 
// in Hero.java

private int armor = 0;

public void increaseArmor(int extraArmor) {
    armor += extraArmor;
}
```

Nu besef ik ook dat het handig zou zijn dat armor ook zichtbaar te maken. Net als de gegevens van 'jouw' held als het
jouw beurt is.

```
// in Side.java

private void showStatus() {
    opponentsSide.showAsEnemy();
    territory.show();
    manaBar.show();
    UnaryOperator<String> colorFunction = hero.canAttack() ? Color.YELLOW::color : Color.BLUE::color;
    hero.show(playerName, colorFunction, FRIENDLY_HERO_SYMBOL);
    hand.showDuringGame(this.getManaBar().getAvailableMana());
}

private void showAsEnemy() {
    var heroColorFunction = territory.colorEnemy(territory.noTauntMinionsPresent());
    hero.show(playerName, heroColorFunction, ENEMY_HERO_SYMBOL);
    territory.showAsEnemy();
}
```

(ik heb gelijk de FRIENDLY_HERO_SYMBOL naar Side verplaatst, en het overal hernoemd)

``` 
// in Hero.java

public void show(String playerName, UnaryOperator<String> colorFunction, char symbol) {
    String armorString = armor > 0 ? "[+" + armor + " ARMOR]" : "";
    System.out.println(colorFunction.apply("%s (%s): %d HP %s (%c)".formatted(playerName, getType().name(), getHealth(), armorString, symbol)));
}
```

Maar armor moet ook nog iets doen: schade absorberen...

``` 
// in Hero.java

@Override public void takeDamage(int damage) {
    if (damage > armor) {
        armor = 0;
        super.takeDamage(damage - armor);
    } else {
        armor -= damage;
    }
}
```

Testen... Één spatie teveel bij de held (`Wubbo (WARRIOR): 26 HP  (*)`) en ik zie nog geen attack...

``` 
// in Hero.java

public void show(String playerName, UnaryOperator<String> colorFunction, char symbol) {
    String armorString = armor > 0 ? " [+" + armor + " ARMOR]" : "";
    String attackString = stats.getAttack() > 0 ? "<" + stats.getAttack() + " ATTACK>" : "";
    System.out.println(colorFunction.apply("%s (%s): %d HP%s %s(%c)".
            formatted(playerName, getType().name(), getHealth(), armorString, attackString, symbol)));
}
```

Dit lijkt wel te werken. Volgende spell!

``` 
// in Main.java 

static final UntargetedSpell WHIRLWIND_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
        s -> {
            s.own().getTerritory().getMinions().forEach(m -> m.takeDamage(1));
            s.opponent().getTerritory().getMinions().forEach(m -> m.takeDamage(1));
        });

static final Card WHIRLWIND = new SpellCard("Whirlwind", 1, "1 damage to ALL minions", WHIRLWIND_SPELL);
```

```
// in Territory.java 
public Iterable<Minion> getMinions() {
    return minions;
}
```

Ik test hem... deze lijkt ook te werken! Dat beeindigt alle warrior spells. Er is nog wel een en ander te doen, ook
omdat veel warrior-minions bepaalde eigenschappen hebben, maar laat ik eerst de mage spells doen; weapons, minion
abilities, en hero powers moeten wel later komen. Laat ik gelijk een todo-list maken! (ik maak een TODO.txt)

De eerste op die lijst is ARCANE_EXPLOSION.

``` 
// in Main.java

static final UntargetedSpell ARCANE_EXPLOSION_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
        s -> s.opponent().getTerritory().getMinions().forEach(m -> m.takeDamage(1)));
static final Card ARCANE_EXPLOSION = new SpellCard("Arcane Explosion", 2, "deal 1 damage to all enemy minions", ARCANE_EXPLOSION_SPELL);
```

De volgende: ARCANE_INTELLECT

``` 
// in Main.java

static final UntargetedSpell ARCANE_INTELLECT_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
        s -> {
            s.own().drawCard();
            s.own().drawCard();
        });

static final Card ARCANE_INTELLECT = new SpellCard("Arcane Intellect", 3, "draw 2 cards", ARCANE_INTELLECT_SPELL);
```

Dan ARCANE_MISSILES

``` 
// in Main.java

static final UntargetedSpell ARCANE_MISSILES_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
        s -> {
            for (int i=0; i <3; i++) {
                s.opponent().getRandomTarget().takeDamage(1);   
            }
        });
static final Card ARCANE_MISSILES = new SpellCard("Arcane Missiles", 1, "deal 3 damage randomly split between all enemy characters", ARCANE_MISSILES_SPELL);
```

dan in Side

``` 
// in Side.java 

public HearthstoneCharacter getRandomTarget() {
    int minionCount = territory.getMinionCount();
    Random random = new Random();
    int chosenIndex = random.nextInt(minionCount + 1); // the opposing hero is also a valid target
    return chosenIndex == minionCount ? hero : territory.getMinions().get(chosenIndex);
}
```

(waarbij ik chosenMinions heb aangepast om een List terug te geven in plaats van een Iterable)

Ik ben aardig op dreef... maar het is denk ik goed dat er weer een refactoring-ronde aankomt!