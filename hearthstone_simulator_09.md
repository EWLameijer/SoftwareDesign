## Stap 9: Wie valt wat aan?

Okee. We zien vijanden. Dus kunnen we aanvallen.

Een getal intoetsen (0..9) speelt een kaart, laten we voor het aanvallen letters gebruiken. Zeg A tot en met G. H is dan dat de held aanvalt (Hero-makkelijk om te onthouden). Ik pas de instructies aan. En zorg voor doelsymbolen: zeg de uppercase-varianten van de cijfers. Dus `b#`: val met jouw tweede minion de derde minion van de tegenstander aan. Dus even een TODO-list maken:
1. breid de instructies uit 
2. beeld het symbool van de minion en tegenstander bij de minion/tegenstander af
3. interpreteer de instructies als een aanval.
4. voer de aanval uit.

Eerst de instructies uitbreiden...
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
        if (choice.equalsIgnoreCase("Q")) return;
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, manaBar, this, opponentsSide);
    } while (true);
}
```

Dan de symbolen afbeelden...

``` 
// in Side.java

private void showAsEnemy() {
    var heroColorFunction = territory.colorEnemy(!territory.isTauntMinionPresent());
    System.out.println(heroColorFunction.apply("%s (%s): %d HP (*)".formatted(playerName, hero.getType().name(), hero.getHitPoints())));
    territory.showAsEnemy();
}
```

```
// in Territory.java

private final static List<Character> indexToSymbol = List.of('!', '@', '#', '$', '%', '^', '&');

private String colorAsEnemy(Minion minion) {
    int minionIndex = minions.indexOf(minion);
    return colorEnemy(isAttackable(minion)).apply("%s (%c)".formatted(minion.getName(), indexToSymbol.get(minionIndex)));
}

public void show() {
    System.out.printf("[%s]\n", String.join(" | ", minions.stream().map(this::colorAsFriendly).toList()));
}

private String colorAsFriendly(Minion minion) {
    int minionIndex = minions.indexOf(minion);
    return colorFriendly(minion).apply("%s (%c)".formatted(minion.getName(), minionIndex+'A'));
}

public UnaryOperator<String> colorFriendly(Minion minion) {
    return minion.canAttack() ? Color.YELLOW::color : Color.BLUE::color;
}
```

Ik ben niet helemaal blij omdat de colorAsFriendly en colorAsEnemy enigszins met elkaar lijken te overlappen qua code. Misschien dat ik daar later nog iets beters op verzin. Maar ik test het uit (dit werkt!) en ga nu door met het interpreteren van de aanval.

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
        if (choice.equalsIgnoreCase("Q")) return;
        execute(choice);
    } while (true);
}

private void execute(String choice) {
    char first = choice.charAt(0);
    if (Character.isDigit(first)) {
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, manaBar, this, opponentsSide);
    } else {
        if (Character.isLetter(first)) {
            if (territory.isValidAttacker(first)) {
                char second = choice.charAt(1);
                if (opponentsSide.territory.isValidAttackee(second)) {
                    Minion attacker = territory.getMinion(first);
                    Minion attackee = opponentsSide.territory.getMinion(second);
                    attacker.attack(attackee);
                } else opponentsSide.territory.communicateInvalidAttackee(second);
            } else territory.communicateInvalidAttacker(first);
        }
    }
}
```
Dus zijn er een paar methoden nodig voor Territory...

``` 
// in Territory.java 

public boolean isValidAttacker(char minionSymbol) {
    int minionIndex = getMinionIndex(minionSymbol);
    if (minionIndex >= minions.size()) return false;
    return minions.get(minionIndex).canAttack();
}

private static int getMinionIndex(char minionSymbol) {
    char normalizedSymbol = Character.toUpperCase(minionSymbol);
    return normalizedSymbol - 'A';
}

public void communicateInvalidAttacker(char minionSymbol) {
    int minionIndex = getMinionIndex(minionSymbol);
    if (minionIndex >= minions.size()) System.out.printf("There is no minion '%c'!\n", minionIndex + 'A');
    if (!minions.get(minionIndex).canAttack()) System.out.printf("Minion %c cannot currently attack!\n", minionIndex + 'A');
}

public boolean isValidAttackee(char minionSymbol) {
    int minionIndex = indexToSymbol.indexOf(minionSymbol);
    if (minionIndex < 0 || minionIndex >= minions.size()) return false; // no such minion
    return isAttackable(minions.get(minionIndex));
}

public void communicateInvalidAttackee(char minionSymbol) {
    int minionIndex = indexToSymbol.indexOf(minionSymbol);
    if (minionIndex < 0 || minionIndex >= minions.size()) System.out.printf("There is no minion '%c'!\n", minionSymbol);
    if (!isAttackable(minions.get(minionIndex))) System.out.println("A minion with taunt is in the way!");
}

public Minion getMinion(char minionSymbol) {
    if (Character.isLetter(minionSymbol)) return minions.get(Character.toUpperCase(minionSymbol) - 'A');
    else return minions.get(indexToSymbol.indexOf(minionSymbol));
}
```

En in Minion.java

```
// in Minion.java
 
public void attack(Minion attackee) {
    attackee.currentHealth -= attack;
    currentHealth -= attackee.attack;
}
```

En nu nog zorgen dat uitgeschakelde minions verdwijnen...

``` 
// in Side.java

private void execute(String choice) {
    char first = choice.charAt(0);
    if (Character.isDigit(first)) {
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, manaBar, this, opponentsSide);
    } else {
        if (Character.isLetter(first)) {
            if (territory.isValidAttacker(first)) {
                char second = choice.charAt(1);
                if (opponentsSide.territory.isValidAttackee(second)) {
                    Minion attacker = territory.getMinion(first);
                    Minion attackee = opponentsSide.territory.getMinion(second);
                    attacker.attack(attackee);
                    disposeOfDeceasedIfAny(attacker, attackee);
                } else opponentsSide.territory.communicateInvalidAttackee(second);
            } else territory.communicateInvalidAttacker(first);
        }
    }
}

private void disposeOfDeceasedIfAny() {
    territory.disposeOfDeceased();
    opponentsSide.territory.disposeOfDeceased();
}
```

``` 
// in Territory.java

public void disposeOfDeceased() {
    for (int i=0; i < minions.size(); i++) {
        if (minions.get(i).getCurrentHealth() <= 0) minions.remove(i);
    }
}
```

En dus in Minion.java laat ik IDEA eindelijk de getter voor currentHealth maken... 
```
// in Minion.java
 
public int getCurrentHealth() {
    return currentHealth;
}
```

Om het wat mooier te doen wil ik ook attack en health afbeelden bij elke minion

```
// in Territory.java 
private String colorAsFriendly(Minion minion) {
    int minionIndex = minions.indexOf(minion);
    return colorFriendly(minion).apply(minionDisplayString(minion, (char)(minionIndex+'A')));
}

private String colorAsEnemy(Minion minion) {
    int minionIndex = minions.indexOf(minion);
    return colorEnemy(isAttackable(minion)).apply(minionDisplayString(minion,  indexToSymbol.get(minionIndex)));
}

private String minionDisplayString(Minion minion, char minionSymbol) {
    return "%s %d/%d (%c)".formatted(minion.getName(), minion.getAttack(), minion.getCurrentHealth(), minionSymbol);
}
``` 
En in Minion.java: 
``` 
// in Minion.java
public int getAttack() {
    return attack;
}
```

Ik test het uit. Het werkt, al zou een mededeling dat een minion sterft wel netjes zijn...

``` 
// in Territory.java

public void disposeOfDeceased() {
    for (int i=0; i < minions.size(); i++) {
        var minion = minions.get(i);
        if (minion.getCurrentHealth() <= 0)  {
            Color.RED.println("The " + minion.getName() + " dies!");
            minions.remove(i);
            i--; // needed to not skip next minion
        }
    }
}
```

IntelliJ ziet ook een logische fout; ik moet i verlagen na verwijdering. Mooi! Er is alleen nog 1 probleem: de hero attacken gaat nog niet! Maar goed, eerst refactoren... 