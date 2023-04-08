## Stap 7: Ik ga refactoren

Op dit moment heb ik 18 klassen. Eerst kijken wat IntelliJ vindt... 

Wel, getAttack en getDurability worden niet gebruikt in de WeaponCard, dus die haal ik weg...

showFirst() in Deck is OOK niet meer nodig! Dus ook verwijderen...

`DECK_SIZE` wordt maar op 1 plaats gebruikt, dus die verander ik van een static final field in een lokale variabele.

```
// in Deck.java
public Deck(List<Card> cards) {
    final int initialDeckSize = 30;
    if (cards.size() != initialDeckSize)
        throw new IllegalArgumentException("Deck(): a deck must have 30 cards, this deck has " + cards.size());
    this.cards = new ArrayList<>(cards);
}
```

De checks in get() en set() lijken heel erg op elkaar; ik factor ze uit...

``` 
public Card get(int index) {
    assertIndexIsLegal(index, "get");
    return cards.get(index);
}

private void assertIndexIsLegal(int index, String methodName) {
    if (index < 0 || index >= cards.size())
        throw new IllegalArgumentException("Deck.%s() error: %d is out of bounds!".formatted(methodName, index));
}

public void set(int index, Card card) {
    assertIndexIsLegal(index, "set");
    cards.set(index, card);
}
```

Ik zie nu ook dat ik bij draw() moet checken of er nog een kaart over is. Dus ook een canDraw() methode nodig. Ik gebruik nu het execute/canExecute-patroon, dat wil zeggen: een methode en een andere (public) methode die kan worden aangeroepen om te zien of de eerste methode mag worden aangeroepen...
``` 
public Card draw() {
    if (!canDraw()) throw new IllegalStateException("Deck.draw() error: cannot draw from an empty deck!");
    var firstCard = cards.get(0);
    cards.remove(0);
    return firstCard;
}

public boolean canDraw() {
    return size() > 0;
}
```

Nu nog de usages van draw checken, zodat ik zeker weet dat canDraw daarvoor wordt aangeroepen! Dat gebeurt 2x. In de mulligan (waar je altijd voldoende kaarten hebt, een check is daar niet nodig), maar ook bij het begin van een beurt. Ik pas die code dus wel aan!

```
// in Side.java
public void giveTurn(Side opponentsSide) {
    System.out.printf("It is %s's turn!%n", playerName);
    manaBar.startTurn();
    territory.startTurn();
    if (deck.canDraw()) {
        hand.add(deck.draw());
    } else {
        hero.exhaust();
    }
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

Ja, meestal heeft een if-statement ook een else-statement nodig, anders krijg je bugs. Ik voeg maar gelijk de exhaust-code toe aan Hero:

``` 
// in Hero.java
public void exhaust() {
    System.out.println(Color.RED.display("Out of cards! You take " + ++exhaustDamage + "damage!"));
    hitPoints -= exhaustDamage;
}
```
Dus nu ook een exhaustDamage-veld nodig in Hero...
``` 
// in Hero.java
private int exhaustDamage = 0;
```

Ik had ook nog geen rode kleur. Wat was die website ook alweer? Wel, een link kunnen toevoegen is een heel goede reden voor commentaar. Ik pas Color.java aan:

``` 
// in Color.java 

// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
public enum Color { RESET ("\u001B[0m"), BLUE ("\u001B[34m"), RED("\u001B[31m"), 
    YELLOW ("\u001B[33m");
```

Je ziet, ik doe de kleuren nu alfabetisch (reset wel aan het begin). Alfabetisch is handiger voor opzoeken en checken dat ik geen duplicaten heb...

In Hand zorg ik dat de `add`-methode nu kleuren gebruikt: 
``` 
public void add(Card card) {
    if (cards.size() >= 10) {
        System.out.println(Color.RED.display("You overdraw a card! " + card.getName() + " is destroyed."));
    } else {
        cards.add(card);
    }
}
```

Ik doe ook een `System.out.println(Color.RED.display("I need more mana to play that card!"));` in de methode eronder. Hmm... Het lijkt erop dat ik doorgaans de gekleurde string gelijk print! Waarom dan geen Color.RED.println inplaats van die dubbele aanroep? Ik hernoem de display-methode en maak ook een println-methode in Color:
``` 
// in Color.java
public void print(String text) {
    System.out.print(code + text + Color.RESET.code);
}

public void println(String text) {
    print(text + "\n");
}
```
En de errors die ik nu krijg omdat display niet meer bestaat los ik gewoon op :) Is alleen niet toepasselijk in Minion.getName(). Dus toch weer een display (of 'color') methode maken.

``` 
// in Color.java
public String color(String text) {
    return code + text + Color.RESET.code;
}
```

Ik verplaats de laatste twee regels in mulligan om alleen de nieuwe kaarten te laten zien als iemand daadwerkelijk kaarten wisselt.
``` 
// in Hand.java 
        }
        System.out.println("Your new cards:");
        show();
    }
}
```

Ik voeg nog een lege regel toe tussen de velden in ManaBar. Die letterlijke 10 (een zogenaamde 'magic constant') lijkt me ook goed om te vervangen. Maar terwijl ik dat doe merk ik dat ik eigenlijk dingen moet hernoemen; max-capacity is eigenlijk 10, je hebt ook currentCapacity en availableMana. Gelukkig helpt IntelliJ met hernoemen...

```
// in ManaBar.java 
private int capacity = 0;

private int availableMana = 0;

void startTurn() {
    final int MAX_CAPACITY = 10;
    if (capacity < MAX_CAPACITY) capacity++;
    availableMana = capacity;
}
``` 

In Side plaats ik de Scanner-initialisatie buiten de loop (hoeft toch niet elke keer te gebeuren)

```
// in Side.java
public void giveTurn(Side opponentsSide) {
    System.out.printf("It is %s's turn!%n", playerName);
    manaBar.startTurn();
    territory.startTurn();
    if (deck.canDraw()) {
        hand.add(deck.draw());
    } else {
        hero.exhaust();
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
```

In Territory zet ik de max number minions in de `canAddMinion()`- methode zelf (ik gebruik hem toch niet erbuiten). En ik besluit de addMinion gewoon een minion te laten accepteren, wat de methode korter maakt.

``` 
// in Territory.java

public boolean canAddMinion() {
    final int maximumNumberOfMinions = 7;
    return minions.size() < maximumNumberOfMinions;
}

public void addMinion(Minion minion) {
    if (canAddMinion()) minions.add(minion);
    else throw new IllegalStateException("Territory.addMinion() error: cannot add a minion to a full Territory!");
}
```

Al moet ik dan natuurlijk ook de aanroep in MinionCard aanpassen. Al is een Minion.from(MinionCard) mogelijk de mooiste oplossing... Of een MinionCard.toMinion()-methode...

``` 
@Override
public void play(Side ownSide, Side opponentsSide) {
    if (ownSide.getTerritory().canAddMinion()) {
        ownSide.getTerritory().addMinion(toMinion());
    } else throw new IllegalArgumentException("MinionCard.play() exception: board is full!");
}

private Minion toMinion() {
    return new Minion(name, attack, health, properties);
}
```
(ja, ik heb ook minionProperties hernoemd). Nu is een toMinion-methode hier niet persé nodig, maar het maakt de code wel wat schoner: de play-methode kan zich nu richten op het spelen van een minion, zonder zich druk te hoeven maken over wat er allemaal precies in een minion zit. Dit zou je een voorbeeld van het 'Single Responsibility Principe' kunnen noemen, of 'single level of abstraction' - dat een methode/unit maar één taak heeft, en dat je geen details mengt met hoofdzaken ("Ik ga op reis naar China, koop een vliegticket, ruil geld, en buk en kijk dan of mijn veters goed vast zitten...". "Ik ga op reis naar China" is één niveau van abstractie (of een methodenaam), het wisselen van geld is een tweede niveau van abstractie, en checken of je schoenveters vast zitten is een lager niveau, onder het kopje "ik doe mijn kleren aan" onder het kopje "ik ga naar Schiphol").

In Main zie ik dat ik een new Game aanmaak waar niets mee lijkt te gebeuren. Dat is niet echt nette code; normaal construeer je een object en dan doe je er iets mee, een constructor is om een object op te bouwen, niet om dat object iets te laten doen!

Ik verander Game:
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
        firstSide.giveTurn(secondSide);
        secondSide.giveTurn(firstSide);
    } while (true);
}
```

en de Main...
``` 
// in Main.java
public static void main(String[] args) {
    var player1Name = askForName("Player 1");
    var player1Deck = askForDeck();
    var player2Name = askForName("Player 2");
    var player2Deck = askForDeck();
    var player1 = new Player(player1Name, player1Deck);
    var player2 = new Player(player2Name, player2Deck);
    var game = new Game(player1, player2);
    game.play();
}
```

Verder zie ik niet veel bijzonders. Maar deze refactoring was denk ik wel goed, ik denk dat ik dat voortaan gewoon elke 5e ronde doe...

Hoe dan ook, het spel lijkt nog steeds hetzelfde te werken (wat ook zo hoort bij refactoring), dus kan ik de volgende keer hopelijk ECHT letterlijk 'in de aanval'!

(wel, ik verander nog `exhaustDamage` in `exhaustionDamage`, en `exhaust()` in `takeExhaustionDamage()`; code checken voordat je commit is handig!). Ook voeg ik een TODO-commentaar in Hand.java toe omdat ik nu besef dat er meer redenen zijn dan alleen mana waarom een kaart niet gespeeld kan worden. Todo comments zijn overigens handig, IntelliJ markeert automatisch alle // TODO comments, en je ziet ze ook in een TODO-window onder je code-window! En ik vervang de magische constante 10 bij de handsize-check ook door een constante, voor de leesbaarheid. En ik verwijder de letterlijke 30 uit de Exceptie van de Deck-constructor. En MAX_CAPACITY naar maxCapacity, omdat die nu toch lokaal is.