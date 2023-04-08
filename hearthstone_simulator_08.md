## Stap 8: Weer tot de aanval over 

Ik heb nu minions die klaar kunnen zijn voor de aanval. Maar ze hebben nog geen doelwit! Laat ik eerst de doelwitten zichtbaar maken...

``` 
// in Side.java 

public void giveTurn(Side opponentsSide) {
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
        showStatus(opponentsSide);
        System.out.print("Which card do you want to play? (0-9), E to end your turn: ");
        String choice = in.nextLine();
        if (choice.equalsIgnoreCase("E")) return;
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, manaBar, this, opponentsSide);
    } while (true);
}

private void showStatus(Side opponentsSide) {
    opponentsSide.showAsEnemy();
    territory.show();
    hand.show();
    manaBar.show();
}
```

Nu vind ik het niet mooi dat ik die 'opponentsSide' als estafettestokje moet doorgeven binnen een klasse. Als je doorgeven van data binnen een klasse ziet, moet je je altijd afvragen of een veld maken niet handiger zou zijn. En hier is dat zeker wel toegestaan, omdat de opponentsSide niet verandert in het hele spel!

Dus ik pas Side.java aan: 
``` 
// in Side.java

private Side opponentsSide;

public void setOpponentsSide(Side opponentsSide) {
    this.opponentsSide = opponentsSide;
}

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
        System.out.print("Which card do you want to play? (0-9), E to end your turn: ");
        String choice = in.nextLine();
        if (choice.equalsIgnoreCase("E")) return;
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, manaBar, this, opponentsSide);
    } while (true);
}

private void showStatus() {
    opponentsSide.showAsEnemy();
    territory.show();
    hand.show();
    manaBar.show();
}
```

en pas Game aan:
``` 
// in Game.java 

public Game(Player playerA, Player playerB) {
    if (random.nextInt(2) == 0) {
        firstSide = new Side(playerA);
        secondSide = new Side(playerB);
    } else {
        firstSide = new Side(playerB);
        secondSide = new Side(playerA);
    }
    firstSide.setOpponentsSide(secondSide);
    secondSide.setOpponentsSide(firstSide);
}

public void play() {
    var firstPlayerName = firstSide.getPlayerName();
    System.out.println("The game starts! " + firstPlayerName + " begins!");
    System.out.println(firstPlayerName + ", please keep or mulligan your cards...");
    firstSide.mulligan(3);
    System.out.println(secondSide.getPlayerName()+ ", please keep or mulligan your cards...");
    secondSide.mulligan(4);
    Card COIN = new SpellCard("The Coin", 0, "Gain 1 mana crystal this turn only");
    secondSide.giveCard(COIN);
    do {
        firstSide.giveTurn();
        secondSide.giveTurn();
    } while (true);
}
```

Fijn, dat minder doorgeven van parameters! Maar nu nog die `showAsEnemy()` implementeren...

``` 
// in Side.java

private void showAsEnemy() {
    Color.RED.println("%s (%s): %d HP".formatted(playerName, hero.getType().name(), hero.getHitPoints()));
    territory.showAsEnemy();
}
```

Wat betekent: `getType()` en `getHitPoints()` in Hero, en `showAsEnemy()` in Territory.

``` 
// in Hero.java 

public HeroType getType() {
    return type;
}

public int getHitPoints() {
    return hitPoints;
}
```

``` 
// in Territory.java

public void showAsEnemy() {
    Color.RED.println("[%s]\n".formatted(String.join(" | ", minions.stream().map(Minion::getName).toList())));
}
```

Laten we dit uittesten... Het gaat redelijk goed, zij het dat de vijandige minions ook als geel of blauw worden weergegeven. Dus toch een extra methode maken:
```
// in Territory.java

public void showAsEnemy() {
    System.out.print("[%s]\n".formatted(String.join(" | ", minions.stream().map(Minion::getNameAsEnemy).toList())));
}
``` 

Nu, als ik het goed wil doen, kan een minion alleen aangevallen worden als er geen taunt-minion in de weg staat. Maar of een ander minion taunt heeft: dat weet de minion niet, alleen de Territory. Dus de methode zou NIET in Minion moeten staan, maar in Territory.

```
// in Territory.java 
 
public void showAsEnemy() {
    System.out.printf("[%s]%n", String.join(" | ", minions.stream().map(this::colorAsEnemy).toList()));
}

private String colorAsEnemy(Minion minion) {
    Function<String, String> coloringFunction = isAttackable(minion) ? Color.RED::color : Color.PURPLE::color;
    return coloringFunction.apply(minion.getName());
}

private boolean isAttackable(Minion minion) {
    return minions.stream().noneMatch(Minion::hasTaunt) || minion.hasTaunt();
}
```

Hierboven experimenteer ik wat met lambda's en applying, omdat ik hier in feite de functie kies om de naam mee te coloren. Ik moet ook de getName() in Minion updaten omdat die nog steeds een gekleurde string geeft...

``` 
// in Minion.java 
public String getName() {
    return name;
}

public String getNameAsFriendly() {
    Color color = canAttack ? Color.YELLOW : Color.BLUE;
    return color.color(name);
}
```

en pas de show() in Territory aan:

``` 
// in Territory.java

public void show() {
    System.out.printf("[%s]\n", String.join(" | ", minions.stream().map(Minion::getNameAsFriendly).toList()));
}
```

Nu nog voor de netheid ook de Hero paars maken als hij niet kan worden aangevallen wegens een verdedigende Taunt-minion...

```
// in Side.java 
private void showAsEnemy() {
    var heroColorFunction = territory.colorEnemy(!territory.isTauntMinionPresent());
    System.out.println(heroColorFunction.apply("%s (%s): %d HP".formatted(playerName, hero.getType().name(), hero.getHitPoints())));
    territory.showAsEnemy();
}
```

Ik maak dus een colorEnemy-methode met daarin de rood/paars-keuze die ik kan hergebruiken.
``` 
// in Territory.java

public UnaryOperator<String> colorEnemy(boolean isAttackable) {
    return isAttackable ? Color.RED::color : Color.PURPLE::color;
}

private String colorAsEnemy(Minion minion) {
    return colorEnemy(isAttackable(minion)).apply(minion.getName());
}
```

`colorAsEnemy` vs `getNameAsFriendly` voelt inconsistent. Ik hernoem `getNameAsFriendly` tot `colorAsFriendly`, de getName zal dan (zoals per conventie) een normale String teruggeven. 

