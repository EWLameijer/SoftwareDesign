## Stap 4: de Mulligan werkt, laat het spel beginnen!

Het spel gaat beginnen! Het game moet nu een Board krijgen waar beide partijen op kunnen strijden. Maar dat is niet de enige verandering.

In het spel worden de spelers namelijk gerepresenteerd door helden; de spelers hebben gewoon een naam, maar de helden (Heroes) hebben hitpoints en kunnen doodgaan.

Dat betekent een paar dingen

1) We moeten een Board maken (die bestaat uit simpelweg twee ArrayLists begrensd op 7 posities, laat ik daar maar een Territory-klasse van maken)
2) We moeten zorgen dat de spelers een Hero meeleveren (in feite bepaalt de hero welke kaarten er in een deck kunnen, maar dat implementeer ik liever nog even niet omdat dat de simulatie niet in de weg staat)
3) Die Hero moet 30 hitpoints krijgen, en een mana-capaciteit (die vooralsnog op 0 staat)
4) Elke beurt krijgt de hero 1 mana-kristal erbij en kan kaarten spelen zolang hij/zij daar mana voor heeft.
5) Oh ja... waarschijnlijk moeten we ook onderscheid maken tussen het Deck dat een player aanlevert en het Deck van de Hero, omdat het deck van de player gelijk blijft tussen games, maar het Deck van de Hero langzaam wordt leeggehaald tijdens een spel.

Aan de slag!

Wel, een board maken is makkelijk...
``` 
public class Board {
    private final Territory firstPlayersTerritory;
    private final Territory secondPlayersTerritory;
}
```

Een Territory, besef ik nu, is een lijst van minions...
```
public class Territory {
    private ArrayList<Minion> minions;
}
```

die ik even neutraal (ongeimplementeerd) laat 
``` 
public class Minion {
}
```

Hmm... Nu ik erover nadenk: elke speler heeft een soortgelijke set:
Hero 
Deck
Territory

dat zou ik een "Side" kunnen noemen. Ik verwijder even de Board klasse, en voeg een Side-klasse toe.

```
public class Side {
    private final Hero hero;
    private final Territory territory;
    private final Deck deck;
}
```

Ik maak een Hero aan, voorlopig alleen met een HeroType (MAGE, WARRIOR), waar ik dus maar een enum van maak.

``` 
enum HeroType { MAGE, WARRIOR }

public class Hero {
    private final HeroType type;
}
```

Ik maak constructoren aan omdat final types handig zijn om fouten te vermijden, maar een goede setup nodig hebben.

```
public class Side {
    private final Hero hero;
    private final Territory territory;
    private final Deck deck;

    public Side(Hero hero, Territory territory, Deck deck) {
        this.hero = hero;
        this.territory = territory;
        this.deck = deck;
    }
} 
```
en 
``` 
enum HeroType { MAGE, WARRIOR }

public class Hero {
    private final HeroType type;

    public Hero(HeroType type) {
        this.type = type;
    }
}
```

Het deck splits ik op: ik maak nu een GameDeck (van HeroType en een lijst Cards), dat apart is van een normaal Deck (dat kaarten kan verliezen)

```
public class GameDeck {
    private final HeroType heroType;
    List<Card> cards;

    public GameDeck(HeroType heroType, List<Card> cards) {
        this.heroType = heroType;
        this.cards = cards;
    }
}
```
Dat leidt in Main.java tot lichte aanpassingen...

```
static final GameDeck warriorDeck = new GameDeck(HeroType.WARRIOR, basicWarriorCards);

static final GameDeck mageDeck = new GameDeck(HeroType.MAGE, basicMageCards);

private static GameDeck askForDeck() { // ...
```

En natuurlijk voor de Player, die nu een GameDeck heeft in plaats van een normaal deck.
``` 
public record Player(String name, GameDeck deck) {}
```

Een side heeft overigens ook een "Hand": welke kaarten je in theorie kunt spelen, en mana crystals/ een ManaBar

``` 
public class Hand {
    private final List<Card> cards = new ArrayList<>();
}
```

```
public class ManaBar {
}
```

Dus: 
1. (het board) doe ik voorlopig niet: 2 sides zijn beter dan 1 board
2. ik heb de HeroTypes aangeleverd bij de Player. Laat ik nu het Game een Side aanmaken...

``` 
public class Game {
    private final Side firstSide;
    private final Side secondSide;

    private final Random random = new Random();

    Card COIN = new SpellCard("The Coin", 0, "Gain 1 mana crystal this turn only");

    public Game(Player playerA, Player playerB) {
        if (random.nextInt(2) == 0) {
            firstSide = new Side(playerA);
            secondSide = new Side(playerB);
        } else {
            firstSide = new Side(playerB);
            secondSide = new Side(playerA);
        }
        // etc.
```

Side wordt nu wat uitgebreid: met een verwijzing naar Hand en de ManaBar. De naam van de speler lijkt me ook handig (om te zeggen wiens beurt het is). 

``` 
public class Side {
    private final Hero hero;

    private final Territory territory = new Territory();

    private final Deck deck;

    private final ManaBar manaBar = new ManaBar();

    private final Hand hand = new Hand();

    private final String playerName;

    public Side(Player player) {
        var playerDeck = player.deck();
        this.hero = Hero.from(playerDeck.getHeroType());
        this.deck = new Deck(playerDeck.getCards());
        this.playerName = player.name();
    }
```

Ik maak een factory method (Hero.from()), waarvoor ik de constructor voor de zekerheid private maak...
``` 
// Hero.java 
private Hero(HeroType type) {
    this.type = type;
}

public static Hero from(HeroType type) {
    return new Hero(type);
}
```

daarnaast moet GameDeck ook een getHeroType() krijgen, en getCards(). Gelukkig is dat niet moeilijk:

``` 
// GameDeck.java
public HeroType getHeroType() {
    return heroType;
}
public List<Card> getCards() {
    return cards;
}
```

Nu lijkt het me wel goed het mulligannen in de Game-constructor te updaten, omdat ik nu immers een Deck van een Side ga mulligannen, wat betekent dat er eerst een paar kaarten in de Hand van de Side komen...

``` 
// in Game() 
var firstPlayerName = firstSide.getPlayerName();
System.out.println("The game starts! " + firstPlayerName + " begins!");
System.out.println(firstPlayerName + ", please keep or mulligan your cards...");
firstSide.mulligan(3);
System.out.println(secondSide.getPlayerName()+ ", please keep or mulligan your cards...");
secondSide.mulligan(4);
```

```
// in Side (Side.mulligan() nodig!) en getPlayerName() 
public void mulligan(int numCardsToMulligan) {
    deck.shuffle();
    for (int i=0; i < numCardsToMulligan; i++) hand.add(deck.draw());
    hand.mulligan(deck);
}

public String getPlayerName() {
    return playerName;
}
```

``` 
// in Deck (Deck.shuffle() nodig!, en draw() ook! )
public void shuffle() {
    Collections.shuffle(cards);
}

public Card draw() {
    var firstCard = cards.get(0);
    cards.remove(0);
    return firstCard;
}
```

```
// in Hand (add en mulligan nodig) 
public void add(Card card) {
    if (cards.size() >= 10) {
        System.out.printf("You overdraw a card! %s is destroyed.\n", card.getName());
    } else {
        cards.add(card);
    }
}

public void mulligan(Deck deck) {
    show();
    System.out.println("\nPlease give the numbers of the cards you want to mulligan (swap) (like '1 3'): ");
    Scanner in = new Scanner(System.in);
    String[] numbers = in.nextLine().split(" ");
    if (!numbers[0].isEmpty()) {
        List<Integer> replacementIndices = new ArrayList<>();
        do {
            int nextRandom = random.nextInt(deck.size());
            if (!replacementIndices.contains(nextRandom)) replacementIndices.add(nextRandom);
        } while (replacementIndices.size() < numbers.length);

        for (int replacement = 0; replacement < numbers.length; replacement++) {
            int naturalCardIndex = Integer.parseInt(numbers[replacement]);
            var cardToSwap = cards.get(naturalCardIndex);
            int deckSwapPosition = replacementIndices.get(replacement);
            cards.set(naturalCardIndex, deck.get(deckSwapPosition));
            deck.set(deckSwapPosition, cardToSwap);
        }
    }
    System.out.println("Your new cards:");
    show();
}

// wat betekent dat Hand ook een show nodig heeft...

public void show() {
    for (int i = 0; i < cards.size(); i++) {
        System.out.println(i + ". " + cards.get(i));
    }
}
```

``` 
// En dus Deck een size(), get() en set()
public int size() {
    return cards.size();
}

public Card get(int index) {
    if (index < 0 || index >= cards.size()) throw new IllegalArgumentException("Deck.get() error: " + index + " is out of bounds!");
    return cards.get(index);
} 

public void set(int index, Card card) {
    if (index < 0 || index >= cards.size()) throw new IllegalArgumentException("Deck.set() error: " + index + " is out of bounds!");
    cards.set(index, card);
}
```

Nu ben ik helaas weer feitelijk waar ik de vorige keer was: ik kan nog steeds alleen maar mulligannen, al is mijn datamodel een stuk uitgebreider (16 klassen nu!) Ik wil nu nog wel de coin-kaart toevoegen, en basaal de turns laten lopen.

Voor de turns, omdat iemand 10 kaarten kan hebben zijn nummers van 0 tot 9 handiger voor spelen van cards, dus ook voor mulligan nu.

```
// in Game 
Card COIN = new SpellCard("The Coin", 0, "Gain 1 mana crystal this turn only");
secondSide.giveCard(COIN);
do {
    firstSide.giveTurn();
    secondSide.giveTurn();
} while (true);
```

``` 
// Dus geef Side een giveTurn() en een giveCard():

public void giveTurn() {
    System.out.printf("It is %s's turn!%n", playerName);
    manaBar.startTurn();
    hand.add(deck.draw());
    hand.show();
    manaBar.show();
    do {
        System.out.print("Which card do you want to play? (0-9), E to end your turn: ");
        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        if (choice.equalsIgnoreCase("E")) return;
        int chosenCardIndex = Integer.parseInt(choice);
        hand.play(chosenCardIndex, manaBar);
        hand.show();
        manaBar.show();
    } while (true);
}

public void giveCard(Card card) {
     hand.add(card);
}
```
Dus dingen te doen voor hand (play) en manaBar (startTurn en show)
```
// Hand.java 
public void play(int index, ManaBar manaBar) {
    if (index < 0 || index > cards.size())
        throw new IllegalArgumentException("Hand.play() error: " + index + " is not a valid index!");
    var chosenCard = cards.get(index);
    if (chosenCard.getCost() > manaBar.getCurrentCapacity()) System.out.println("I need more mana to play that card!");
    else {
        manaBar.consume(chosenCard.getCost());
        System.out.println("Playing " + chosenCard.getName());
        cards.remove(index);
    }
}
```
Dus voor manaBar OOK getCurrentCapacity en consume... (en de velden maxCapacity en currentCapacity om dat te ondersteunen...)
```
// ManaBar: startTurn(), show(), getCurrentCapacity() en consume()
private int maxCapacity = 0;
private int currentCapacity = 0;

void startTurn() {
    if (maxCapacity < 10) maxCapacity++;
    currentCapacity = maxCapacity;
}

void consume(int amount) {
    if (amount < 0 || amount >currentCapacity) throw new IllegalArgumentException("ManaBar.consume() error: " + amount + "  is out of range!");
    currentCapacity -= amount;
}

void show() {
    System.out.printf("MANA: %d out of %d available\n", currentCapacity, maxCapacity);
}

public int getCurrentCapacity() {
    return currentCapacity;
}
```

Kijkend naar mijn oorspronkelijke lijst:
1. We moeten een Board maken (die bestaat uit simpelweg twee ArrayLists begrensd op 7 posities, laat ik daar maar een Territory-klasse van maken)
    *gedaan!*
2. We moeten zorgen dat de spelers een Hero meeleveren (in feite bepaalt de hero welke kaarten er in een deck kunnen, maar dat implementeer ik liever nog even niet omdat dat de simulatie niet in de weg staat)
    *gedaan!*
3. Die Hero moet 30 hitpoints krijgen, en een mana-capaciteit (die vooralsnog op 0 staat)
    *gedaan! (al is de mana-capaciteit feitelijk niet bij de Hero, maar bij de Side)*
4. Elke beurt krijgt de hero 1 mana-kristal erbij en kan kaarten spelen zolang hij/zij daar mana voor heeft.
    *gelukt!*
5. Oh ja... waarschijnlijk moeten we ook onderscheid maken tussen het Deck dat een player aanlevert en het Deck van de Hero, omdat het deck van de player gelijk blijft tussen games, maar het Deck van de Hero langzaam wordt leeggehaald tijdens een spel.
    *gedaan!*

In elk geval kunnen we nu kaarten spelen. Wel een klein probleem: er gebeurt nog niets als we kaarten spelen (behalve dat ze uit onze hand verdwijnen). Laten we de volgende keer kijken of we ervoor kunnen zorgen dat er daadwerkelijk wat gebeurt als je een kaart speelt!


