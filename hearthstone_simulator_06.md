## Stap 6: Minions, val aan!

We hebben nu minions, maar die doen nog niets. Ze moeten kunnen aanvallen!

Nu is er wel de regel dat een minion niet mag aanvallen op de beurt dat de minion geplaatst is, TENZIJ de minion charge heeft. Nu kan die charge gezet worden (bijvoorbeeld door die Warsong Commander). Dus allereerst moet een minion een charge-field krijgen, en een exhausted-field (als de minion heeft aangevallen, kan hij niet nog eens aanvallen).

Dus:
``` 
// Minion.java 
private boolean exhausted = true;

private boolean hasCharge = true;
```

Nu zegt mijn gevoel me dat dit niet helemaal de goede oplossing is, maar ik vertrouw erop dat ik later een betere oplossing vind, als ik refactor.

Dus nu moet ik ervoor zorgen dat de MinionCard de minion maakt (net als er een verschil is tussen een GameDeck en een Deck). Ik zie dat de constructor van Minion al behoorlijk wat argumenten heeft. Sommige minions hebben *charge*, andere *taunt*, er zijn mogelijk andere eigenschappen... Het kan goed zijn een MinionProperty enum te maken, en een set daarvan aan de Minion-constructor door te geven.

``` 
// in MinionCard.java

enum MinionProperty { CHARGE, TAUNT} 

public MinionCard(String name, int cost, String description, int attack, int health, Set<MinionProperty> minionProperties) { ...
```

En dan moeten dus de constructoraanroepen worden aangepast. De meeste kaarten krijgen een Set.of() laatste argument (ik ZOU de constructor kunnen overloaden, maar ik wil niet vergeten de properties mee te geven) en ik plaats eventuele TAUNT of CHARGE van de description naar de properties (geen dubbele data!) En oh ja... MinionProperty moet nu public zijn en moet dus in zijn eigen file.

``` 
// Main.java (paar van de wijzigingen)
final static Card KOK_KRON_ELITE = new MinionCard("Kok'kron Elite", 4, "", 4, 3, Set.of(MinionProperty.CHARGE));

final static Card RAID_LEADER = new MinionCard("Raid Leader", 3, "all your other minions have +1 attack", 2, 2, Set.of());
final static Card SEN_JIN_SHIELDMASTA = new MinionCard("Sen'jin Shieldmasta", 4, "", 3, 5, Set.of(MinionProperty.TAUNT));
```

CHARGE en TAUNT: ik kan velden maken voor beide, maar ik heb een donkerbruin vermoeden dat er nog meer properties zullen komen, en dan krijg je dus wel een heleboel velden! Dat suggereert een Map, maar een property is aanwezig of afwezig, wat een Map<MinionProperty, Boolean> suggereert, maar een Map<X, Boolean> is eigenlijk een set, dus maak ik een Set aan in Minion. Klopt ook mooi met de constructor van MinionCard!

```
// in MinionCard.java (ik haal gelijk ook de getAttack() en getHealth() weg, die worden toch niet gebruikt...
private final Set<MinionProperty> minionProperties;

public MinionCard(String name, int cost, String description, int attack, int health, Set<MinionProperty> minionProperties) {
    super(name, cost, description);
    this.attack = attack;
    this.health = health;
    this.minionProperties = minionProperties;
}
```

Uiteraard moeten die properties worden meegegeven aan de minion...
``` 
// in MinionCard.java
@Override
public void play(Side ownSide, Side opponentsSide) {
    if (ownSide.getTerritory().canAddMinion()) {
        ownSide.getTerritory().addMinion(name, attack, health, minionProperties);
    } else throw new IllegalArgumentException("MinionCard.play() exception: board is full!");
}
```

die ze moet kunnen verwerken (ik verwijder nu dus gelijk het charge-field)

``` 
// in Minion.java
private final Set<MinionProperty> properties;

public Minion(String name, int attack, int health, Set<MinionProperty> properties) {
    this.name = name;
    this.attack = attack;
    this.currentHealth = this.maxHealth = health;
    this.properties = properties;
}
```

Hmm... ik zie dat ik er nog niet ben, want de constructie gaat via Territory. Ik ben hier niet zo blij mee, 'estafetteparameters' die van methode naar methode worden doorgegeven zijn een 'code smell'. Mogelijk dat ik daar later een oplossing voor zie. Voorlopig update ik ook de code in Territory maar braaf...
``` 
// in Territory.java
public void addMinion(String name, int attack, int health, Set<MinionProperty> properties) {
    if (canAddMinion()) minions.add(new Minion(name, attack, health, properties));
    else throw new IllegalStateException("Territory.addMinion() error: cannot add a minion to a full Territory!");
}
```

En ik ben bijna vergeten waarom ik dit ookalweer deed. Oh ja, ik wil kunnen aanvallen! Wel: twee regels:
- als een minion wordt geplaatst, wordt hij exhausted TENZIJ hij de property CHARGE heeft.
- als een minion heeft aangevallen, wordt hij exhausted.
- bij het begin van een turn worden alle minions 'unexhausted'.

```
// in Minion.java
private final Set<MinionProperty> properties;

public Minion(String name, int attack, int health, Set<MinionProperty> properties) {
    this.name = name;
    this.attack = attack;
    this.currentHealth = this.maxHealth = health;
    this.properties = properties;
    this.exhausted = !properties.contains(MinionProperty.CHARGE);
}
```

Ik vind dit zelf (met de !) wat lastig om over te redeneren. Ik denk dat 'exhausted' dingen lastiger te lezen maakt, zelfs al is het een officiële HearthStone-term. `canAttack` klinkt beter/makkelijker. Zeggen dat iets NIET kan kost vaak meer denkwerk. Dus...
```
// in Minion.java
private boolean canAttack;

private final Set<MinionProperty> properties;

public Minion(String name, int attack, int health, Set<MinionProperty> properties) {
    this.name = name;
    this.attack = attack;
    this.currentHealth = this.maxHealth = health;
    this.properties = properties;
    this.canAttack = properties.contains(MinionProperty.CHARGE);
}
```

Dit vind ik in elk geval logischer klinken! Nu ze unexhausten aan het begin van een turn...
```
// in Side.java 
public void giveTurn(Side opponentsSide) {
    System.out.printf("It is %s's turn!%n", playerName);
    manaBar.startTurn();
    territory.startTurn();

    hand.add(deck.draw());
    do {
        showStatus();
        System.out.print("Which card do you want to play? (0-9), E to end your turn: ");
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        if (choice.equalsIgnoreCase("E")) return;
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, manaBar, this, opponentsSide);
    } while (true);
}
```

Ik laat de Territory de unexhaustion regelen. Immers, de minions zitten in de Territory, zelfs al is dit weer een soort 'estafetteaanroep'.

```
// Territory.java 
public void startTurn() {
    minions.forEach(m -> m.readyForAttack());
}
```

En dan de minions hun attack laten regelen. Gelukkig maakt IDEA al wat code 
```
// Minion.java
public void readyForAttack() {
    canAttack = true;
} 
```

En nu suggereert IDEA dat mijn lambda van zojuist een method reference kan worden. Dan kan ik nog oefenen...

```
// Territory.java
public void startTurn() {
    minions.forEach(Minion::readyForAttack);
}
```

Het lijkt me overigens handig om bij het tonen van de minions aan te geven of ze kunnen aanvallen. Gelukkig heb ik ooit bij een programmeerpuzzel uit de C# Player's Guide geleerd dat je ook in Java de tekst in de console een kleur kan geven. Misschien geel voor kan aanvallen, blauw voor 'slapend/exhausted?'. Link: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

```
// in Minion.java
private final String YELLOW = "\u001B[33m";
private final String BLUE ="\u001B[33m";
private final String RESET = "\u001B[0m";

public String getName() {
    String color = canAttack ? YELLOW : BLUE;
    return color + name + RESET;
}
```

Dus ja, ik maak gelijk constanten voor leesbaarheid, al denk ik dat die later in een andere klasse zullen komen, want kleuren lijken me universeel handig!
Ik test het uit...

He? Een net geplaatste minion is geel! Ik ga dit uitzoeken, debugging met een breakpoint levert niets op, dus maak ik maar een aparte Color enum...
```
// in Color.java
public enum Color { YELLOW ("\u001B[33m"), BLUE ("\u001B[33m"), RESET ("\u001B[0m");
    private final String code;
    
    Color(String ansiCode) {
        code = ansiCode;
    }

    public String display(String text) {
        return code + text + Color.RESET.code;
    }
}
```

En doe ik een test in Game:
``` 
// in Game.Java
System.out.println(Color.BLUE.display("The game starts! " + firstPlayerName + " begins!"));
System.out.println(Color.YELLOW.display(firstPlayerName + ", please keep or mulligan your cards..."));
```

En dan blijkt dat ze beide geel zijn! En ze hebben ook dezelfde code. Slecht gecopypasted en opgelet... Afijn...

```
// in Color.java
public enum Color { YELLOW ("\u001B[33m"), BLUE ("\u001B[34m"), RESET ("\u001B[0m");
    private final String code;
    Color(String ansiCode) {
        code = ansiCode;
    }

    public String display(String text) {
        return code + text + Color.RESET.code;
    }
}
```
Testen... Nu lijkt het te werken :)

Nu lijkt dit me wel voldoende voor deze iteratie. Volgende keer wil ik echt gaan aanvallen!
Al geeft IDEA aan dat GameDeck een record kan zijn. Ik laat dat corrigeren...
```
// in GameDeck.java
public record GameDeck(HeroType heroType, List<Card> cards) {
}
```

Misschien goed om toch eerst eens door de code te gaan en wat op te schonen/te refactoren, dat kan me later veel tijd schelen. Vaak vind ik het het beste iets te programmeren, dan weer iets te refactoren, en niet alle refactoren uitstellen tot het einde.