## Stap 5: Laat het spelen van Cards iets doen...

Op dit moment hebben we Cards, maar het spelen ervan doet nog niks. Terwijl een minionCard een minion op het bord zou
moeten plaatsen, een SpellCard iets moet doen met een minion of hero, en een WeaponCard de Hero een attack en een
bijbehorende durability zou moeten geven.

Allereerst merk ik dat het hebben van 16 klassen dingen wat onoverzichtelijk maakt- wat waren ook alweer al mijn cards?
Ik maak dus een aparte package aan voor Card en de subklassen (cards). En ook maar gelijk een aparte package voor de
Side en bijbehorende dingen.

Dat geeft een paar problemen omdat HeroType niet public is, dus die laat ik door IntelliJ naar een aparte file
verplaatsen.

Hoe dan ook, het meest algemene geval als een kaart gespeeld wordt is er dat er wat gebeurt met iets in de Game, met (1
of meer karakters/dingen) op de friendly side, of 1 of meer karakters dingen aan de kant van de tegenstander (opponent's
side). Ik maak dus een play(ownSide, enemySide) methode in Card, die ik abstract maak.

```
// in Card.java
public abstract void play(Side ownSide, Side opponentsSide);
```

Die moet ik uiteraard implementeren in de subklassen; IntelliJ helpt weer

```
// in MinionCard, SpellCard en WeaponCard  
@Override
public void play(Side ownSide, Side opponentsSide) {
}
```

Laat ik met de MinionCard beginnen: als er minder dan 7 minions zijn op de vriendelijke zijde van het bord (het
Territory) voeg een minion toe met de gewenste stats.

```
// in Minion.java 
@Override
public void play(Side ownSide, Side opponentsSide) {
    if (ownSide.getTerritory().canAddMinion()) {
        ownSide.getTerritory().addMinion(name, attack, health);
    } else throw new IllegalArgumentException("MinionCard.play() exception: board is full!");
}
```

Dus dat betekent een getTerritory() in Side, en canAddMinion en addMinion in Territory.

``` 
// Side 
public Territory getTerritory() {
    return territory;
}
```

``` 
// Territory 
private static final int MAX_NUM_MINIONS = 7;

public boolean canAddMinion() {
    return minions.size() < MAX_NUM_MINIONS;
}

public void addMinion(String name, int attack, int health) {
    if (canAddMinion()) minions.add(new Minion(name, attack, health));
    else throw new IllegalStateException("Territory.addMinion() error: cannot add a minion to a full Territory!");
}
```

Je ziet, ik programmeer nogal defensief met het throwen van Excepties. Sneller een probleem kunnen vinden is meestal wel
wat extra typewerk waard.

Hoe dan ook, ik moet eindelijk nu die Minion gaan invullen:

``` 
public class Minion {
    private final String name;

    private final int attack;
    
    private final int maxHealth;

    private int currentHealth;

    public Minion(String name, int attack, int health) {
        this.name = name;
        this.attack = attack;
        this.currentHealth = this.maxHealth = health;
    }
}
```

Ik hou er al rekening mee dat een minion een currentHealth en een maxHealth heeft. Misschien een beetje voorbarig, maar
ik meen me te herinneren dat er een card was met healing, dus je mag niet kunnen healen boven de max health...

Maar nog steeds is er een probleem omdat MinionCard geen toegang heeft tot name; die moet ik dus protected maken in
Card. Wat IntelliJ desgevraagd doet:

```
// Card.java
protected final String name; 
```

Nu is dat bovenstaande allemaal wel leuk, maar ik wil die minion(s) ook kunnen zien! Dat betekent dat ook het territory
geshowd moet kunnen worden. Ik laat het eerst showen in Game (of beter in Side.giveTurn())

``` 
// in Side.java 
public void giveTurn() {
    System.out.printf("It is %s's turn!%n", playerName);
    manaBar.startTurn();

    hand.add(deck.draw());
    territory.show();
    hand.show();
    manaBar.show();
    do {
        System.out.print("Which card do you want to play? (0-9), E to end your turn: ");
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        if (choice.equalsIgnoreCase("E")) return;
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, manaBar);
        territory.show();
        hand.show();
        manaBar.show();
    } while (true);
}
```

Jou valt het mogelijk ook op dat ik die code van de drie 'show's herhaal... Waarschijnlijk betekent dit zowel dat ik ze
beter kan samenvatten naar een showStatus, en dat ik die methode 1x laat doen, aan het begin van de do-while-loop!

``` 
// in Side.java
public void giveTurn() {
    System.out.printf("It is %s's turn!%n", playerName);
    manaBar.startTurn();

    hand.add(deck.draw());
    do {
        showStatus();
        System.out.print("Which card do you want to play? (0-9), E to end your turn: ");
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        if (choice.equalsIgnoreCase("E")) return;
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, manaBar);
    } while (true);
}

private void showStatus() {
    territory.show();
    hand.show();
    manaBar.show();
}
```

Al moet ik nu nog steeds de territory.show() implementeren...

``` 
// in Territory.java
public void show() {
    if (minions.isEmpty()) System.out.println("[]");
    else System.out.println(String.join(" | ", minions.stream().map(m -> m.getName()).toList()));
}
```

En dan nog een m.getName()... of kan ik van Minion gewoon een record maken? (wel... nee, want een instance field is niet
allowed in een record :(. Maar goed, dan een getName())

``` 
// in Minion.java
public String getName() {
    return name;
}
```

IntelliJ wil nu de lambda in Territory vervangen door een method reference (okee..)

``` 
// in Territory.java
else System.out.println(String.join(" | ", minions.stream().map(Minion::getName).toList()));
```

En ik test het uit!

Al geeft dat snel een exceptie:
`Exception in thread "main" java.lang.NullPointerException: Cannot invoke "java.util.ArrayList.isEmpty()" because "this.minions" is null`

Ugh... vergeten minions op een lege ArrayList te initialiseren... Maar is zo gepiept... (en IntelliJ suggereert ook de
ArrayList final te maken...)

```
// in Territory.java
private final ArrayList<Minion> minions = new ArrayList<>();
```

Weer uittesten... Waaruit blijkt dat de card niet wordt afgebeeld. Ehm... ben ik mogelijk vergeten de play-methode toe
te voegen?
Inderdaad! Ik corrigeer Hand.java

``` 
// in Hand.java
public void play(int index, ManaBar manaBar, Side ownSide, Side opponentsSide) {
    if (index < 0 || index > cards.size())
        throw new IllegalArgumentException("Hand.play() error: " + index + " is not a valid index!");
    var chosenCard = cards.get(index);
    if (chosenCard.getCost() > manaBar.getCurrentCapacity())
        System.out.println("I need more mana to play that card!");
    else {
        manaBar.consume(chosenCard.getCost());
        System.out.println("Playing " + chosenCard.getName());
        chosenCard.play(ownSide, opponentsSide);
        cards.remove(index);
    }
}
```

Dus ik moet nu de sides ook meekrijgen...

``` 
// in Side.java
public void giveTurn(Side opponentsSide) {
    System.out.printf("It is %s's turn!%n", playerName);
    manaBar.startTurn();

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

En om de opponent's side te pakken te krijgen, in Game.java...

``` 
// in Game.java, de constructor 
secondSide.giveCard(COIN);
do {
    firstSide.giveTurn(secondSide);
    secondSide.giveTurn(firstSide);
} while (true);
```

Ik test het opnieuw... (ik zie overigens dat als ik een spatie teveel in een spelersnaam type, dat niet wordt
gecorrigeerd...)
Ik zie overigens ook dat het eruit komt te zien als `Voodoo Doctor | Kobold Geomancer | Magma Rager`. Dus [] eromheen
zou mooier zijn.

Ik voer die twee correcties uit (trim() in askForName, [] in Territory.show()))

``` 
// in Main.java
private static String askForName(String provisionalName) {
    System.out.print(provisionalName + ", enter your name: ");
    return in.nextLine().trim();
}
```

```
// in Territory.java
public void show() {
    if (minions.isEmpty()) System.out.println("[]");
    else System.out.printf("[%s]\n",String.join(" | ", minions.stream().map(Minion::getName).toList()));
}
```

Ik test opnieuw... `[Voodoo Doctor | Kobold Geomancer]`. Ziet er mooi uit!

Hoe dan ook, ik kan minions spelen, maar ik kan er nog niets mee doen (en ik kan ook nog geen spells of weapons spelen).
Dat worden dus onderwerpen voor volgende afleveringen... (Ik zie overigens dat ik nog geen autoformat on save had
ingesteld; ik doe dat dus nu alsnog, via de Settings)

Ik zie ook dat de show() in Territory simpeler kan; een lege lijst IS een lijst...

```
// in Territory.java
public void show() {
    System.out.printf("[%s]\n",String.join(" | ", minions.stream().map(Minion::getName).toList()));
}
```

