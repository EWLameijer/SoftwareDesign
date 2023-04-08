## Stap 11: Ook helden moeten kwetsbaar zijn!

In stap 9 lieten we minions andere minions aanvallen. Maar ze moeten ook de held van de tegenstander kunnen aanvallen!

Nu zou één OO(object-georienteerde)-manier om dat op te lossen zijn zowel Hero als Minion van Character te laten
overerven. In mijn eerste impuls was ik daar tegen (een held aanvallen heeft andere regels dan het aanvallen van een
minion, omdat de attack van een held niet meetelt). Aan de andere kant zijn er dingen qua healing die weer WEL hetzelfde
zijn voor helden en minions. Dus ik maak toch een Character!

Dus ik maak een Character-klasse:

``` 
// in Character.java (na wat aanpassingen op basis van meldingen van IntelliJ)

public class Character {
    protected final int maxHealth;

    protected int currentHealth;

    protected Character(int maxHealth) {
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
    }

    public int getHealth() {
        return currentHealth;
    }
}
```

Ik probeer nu overal HitPoints enzo in Health om te zetten... En ik wijzig de `Minion.attack()`:

``` 
// in Minion.java

public void attack(Character attackee) {
    attackee.currentHealth -= attack;
    if (attackee instanceof Minion otherMinion) {
        // heroes CANNOT counterattack
        currentHealth -= otherMinion.attack;
    }
}
```

Nu moet ik er nog voor zorgen dat heroes aangevallen kunnen worden...

Nu kom ik een probleem tegen: Character is een Hearthstone-term, maar ook een Java-term! Ik hernoem Character dus tot
HearthStoneCharacter.

Ik zorg er gelijk voor dat het spel ook kan eindigen:

``` 
// in Game.java 

public void play() {
    var firstPlayerName = firstSide.getPlayerName();
    System.out.println("The game starts! " + firstPlayerName + " begins!");
    System.out.println(firstPlayerName + ", please keep or mulligan your cards...");
    firstSide.mulligan(3);
    System.out.println(secondSide.getPlayerName() + ", please keep or mulligan your cards...");
    secondSide.mulligan(4);
    Card COIN = new SpellCard("The Coin", 0, "Gain 1 mana crystal this turn only");
    secondSide.giveCard(COIN);
    int turn = 0;
    do {
        turn++;
        Side activeSide = turn % 2 == 1 ? firstSide : secondSide;
        activeSide.giveTurn();
        if (firstSide.hasLost() || secondSide.hasLost()) break;
    } while (true);
}
```

``` 
// in Side.java

private void execute(String choice) {
    char first = choice.charAt(0);
    if (Character.isDigit(first)) {
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, this, opponentsSide);
    } else {
        if (Character.isLetter(first)) {
            if (territory.isValidAttacker(first)) {
                char second = choice.charAt(1);
                if (opponentsSide.isValidAttackee(second)) {
                    Minion attacker = territory.getMinion(first);
                    HearthStoneCharacter attackee = opponentsSide.getAttackee(second);
                    attacker.attack(attackee);
                    disposeOfDeceasedIfAny();
                } else opponentsSide.territory.communicateInvalidAttackee(second);
            } else territory.communicateInvalidAttacker(first);
        }
    }
}

private HearthStoneCharacter getAttackee(char attackeeSymbol) {
    if (attackeeSymbol == '*') return hero;
    else return territory.getMinion(attackeeSymbol);
}

private boolean isValidAttackee(char attackeeSymbol) {
    if (attackeeSymbol == '*') return true; // attack on hero
    else return territory.isValidAttackee(attackeeSymbol);
}

private void disposeOfDeceasedIfAny() {
    opponentsSide.territory.disposeOfDeceased();
    if (opponentsSide.hero.getHealth() <= 0) Color.GREEN.println("You are victorious!");
    territory.disposeOfDeceased();
}

public boolean hasLost() {
    return hero.getHealth() <= 0;
}
```

Ik ga het uittesten... Waaruit het enige probleem blijkt dat een minion ongelimiteerd kan aanvallen in zijn beurt. Dat
moet dus anders...

```
// in Minion.java
 
public void attack(HearthStoneCharacter attackee) {
    attackee.currentHealth -= attack;
    canAttack = false;
    if (attackee instanceof Minion otherMinion) {
        // heroes CANNOT counterattack
        currentHealth -= otherMinion.attack;
    }
}
```

Opnieuw testen... Beter, maar kennelijk kunnen minions taunt van de tegenstander ignoren (in elk geval bij het aanvallen
van de held)...

``` 
// in Side.java

private boolean isValidAttackee(char attackeeSymbol) {
    if (attackeeSymbol == '*'  && !territory.isTauntMinionPresent()) return true; // attack on hero
    else return territory.isValidAttackee(attackeeSymbol);
}
```

Werkt beter, maar ik krijg nog steeds een vervelende exceptie ("There is no minion '*'!") na het bevel 'a*' als er een
taunt minion is.

``` 
// in Territory.java 

public void communicateInvalidAttackee(char minionSymbol) {
    int minionIndex = indexToSymbol.indexOf(minionSymbol);
    if ((minionIndex < 0 && minionSymbol != '*') || minionIndex >= minions.size())
        System.out.printf("There is no minion '%c'!\n", minionSymbol);
    if (!isAttackable(minions.get(minionIndex))) Color.RED.println("A minion with taunt is in the way!\n");
}
```

Tot zover lijkt het goed te werken. Mooi! Maar het spelen wordt wel redelijk saai, omdat ik heel veel kaarten nog steeds
niet kan gebruiken. Bij de volgende ronde: laten we beginnen met spells! (nadat ik nog even de code check voor committen
en '*' vervang door een symbolische constante, public final static char ENEMY_HERO_SYMBOL = '*'; (in Side)).