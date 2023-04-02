## Stap 3: We hebben Cards, laten we Decks gaan maken

We hebben nu dus cards, we kunnen een Deck gaan maken. Niet gewoon een List van cards? Wel, dat liever niet, want een deck heeft een vaste grootte, in tegenstelling tot een List. Je zou wel een Deck met een List kunnen initialiseren...

```
import java.util.List;

public class Deck {
    private final static int DECK_SIZE = 30;
    private final List<Card> cards;

    public Deck(List<Card> cards) {
        if (cards.size() != DECK_SIZE) throw new IllegalArgumentException("Deck(): a deck must have 30 cards, this deck has " + cards.size() );
        this.cards = cards;
    }
}
```

En nu de decks maken in de main. 

```
public static void main(String[] args) {
    List<Card> basicWarrior = List.of(
            ARCANITE_REAPER, ARCANITE_REAPER, CLEAVE, CLEAVE, CHARGE, CHARGE, EXECUTE, EXECUTE, FIERY_WAR_AXE,
            FIERY_WAR_AXE, FROSTWOLF_GRUNT, FROSTWOLF_GRUNT, FROSTWOLF_WARLORD, FROSTWOLF_WARLORD,
            GURUBASHI_BERSERKER, GURUBASHI_BERSERKER, HEROIC_STRIKE, HEROIC_STRIKE, KOK_KRON_ELITE, KOK_KRON_ELITE,
            RAID_LEADER, RAID_LEADER, SEN_JIN_SHIELDMASTA, SEN_JIN_SHIELDMASTA, SHIELD_BLOCK, SHIELD_BLOCK,
            WARSONG_COMMANDER, WARSONG_COMMANDER, WHIRLWIND, WHIRLWIND);
    var warriorDeck = new Deck(basicWarrior);
    List<Card> basicMage = List.of(
            ARCANE_EXPLOSION, ARCANE_EXPLOSION, ARCANE_INTELLECT, ARCANE_INTELLECT, ARCANE_MISSILES, ARCANE_MISSILES,
            ARCHMAGE, ARCHMAGE, DARKSCALE_HEALER, DARKSCALE_HEALER, FIREBALL, FIREBALL, FLAMESTRIKE, FLAMESTRIKE,
            FROST_NOVA, FROST_NOVA, FROSTBOLT, FROSTBOLT, KOBOLD_GEOMANCER, KOBOLD_GEOMANCER,
            MAGMA_RAGER, MAGMA_RAGER, MIRROR_IMAGE, MIRROR_IMAGE, POLYMORPH, POLYMORPH, VOODOO_DOCTOR, VOODOO_DOCTOR,
            WATER_ELEMENTAL, WATER_ELEMENTAL);
    var mageDeck = new Deck(basicMage);
}
```

Dat is okee, maar het voelt aan als tijd voor actie! Laten we ook twee spelers aanmaken, beide met een name en een Deck. Daarna gooien we een muntje op en laten we ze hun eerste kaarten trekken.

```
public class Player {
    final private String name;
    final private Deck deck;

    public Player(String name, Deck deck) {
        this.name = name;
        this.deck = deck;
    }

    public String getName() {
        return name;
    }

    public Deck getDeck() {
        return deck;
    }
}
```

Ik denk nu dat ik ook een Game-klasse nodig heb, die dus twee spelers als argumenten neemt, en een eerste aanwijst...

Ik maak een wat leukere main om mee te starten... 

```
static final List<Card> basicWarrior = List.of(
        ARCANITE_REAPER, ARCANITE_REAPER, CLEAVE, CLEAVE, CHARGE, CHARGE, EXECUTE, EXECUTE, FIERY_WAR_AXE,
        FIERY_WAR_AXE, FROSTWOLF_GRUNT, FROSTWOLF_GRUNT, FROSTWOLF_WARLORD, FROSTWOLF_WARLORD,
        GURUBASHI_BERSERKER, GURUBASHI_BERSERKER, HEROIC_STRIKE, HEROIC_STRIKE, KOK_KRON_ELITE, KOK_KRON_ELITE,
        RAID_LEADER, RAID_LEADER, SEN_JIN_SHIELDMASTA, SEN_JIN_SHIELDMASTA, SHIELD_BLOCK, SHIELD_BLOCK,
        WARSONG_COMMANDER, WARSONG_COMMANDER, WHIRLWIND, WHIRLWIND);
static final Deck warriorDeck = new Deck(basicWarrior);
static final List<Card> basicMage = List.of(
        ARCANE_EXPLOSION, ARCANE_EXPLOSION, ARCANE_INTELLECT, ARCANE_INTELLECT, ARCANE_MISSILES, ARCANE_MISSILES,
        ARCHMAGE, ARCHMAGE, DARKSCALE_HEALER, DARKSCALE_HEALER, FIREBALL, FIREBALL, FLAMESTRIKE, FLAMESTRIKE,
        FROST_NOVA, FROST_NOVA, FROSTBOLT, FROSTBOLT, KOBOLD_GEOMANCER, KOBOLD_GEOMANCER,
        MAGMA_RAGER, MAGMA_RAGER, MIRROR_IMAGE, MIRROR_IMAGE, POLYMORPH, POLYMORPH, VOODOO_DOCTOR, VOODOO_DOCTOR,
        WATER_ELEMENTAL, WATER_ELEMENTAL);
static final Deck mageDeck = new Deck(basicMage);

final static Scanner in = new Scanner(System.in);

public static void main(String[] args) {
    var player1Name = askForName("Player 1");
    var player1Deck = askForDeck();
    var player2Name = askForName("Player 2");
    var player2Deck = askForDeck();
    var player1 = new Player(player1Name, player1Deck);
    var player2 = new Player(player2Name, player2Deck);
    var Game = new Game(player1, player2);
}

private static Deck askForDeck() {
    System.out.print("Warrior (W) or Mage(anything else)? ");
    var isWarrior = in.nextLine().toUpperCase().startsWith("W");
    return isWarrior ? warriorDeck : mageDeck;
}

private static String askForName(String provisionalName) {
    System.out.print(provisionalName + ", enter your name: ");
    return in.nextLine();
}
```

En voeg een Game toe:
```
import java.util.Random;

public class Game {
    private final Player firstPlayer;
    private final Player secondPlayer;

    private final Random random = new Random();
    public Game(Player playerA, Player playerB) {
        if (random.nextInt(2) == 0) {
            firstPlayer = playerA;
            secondPlayer = playerB;
        } else {
            firstPlayer = playerB;
            secondPlayer = playerA;
        }
        System.out.println("The game starts! " +firstPlayer.getName() + " begins!");
    }
} 
```

Als ik dit uittest, werkt het best goed. Althans, het kondigt aan dat één van de twee spelers begint.

Om het begin af te maken: ik laat de eerste speler 3 kaarten kiezen, de tweede speler 4 kaarten. Elke kaart kan gemarkeerd worden voor een mulligan (ruilen met een random kaart uit het deck).

Een paar kleine problemen: voor het randomiseren van de kaarten moet ik Collections.shuffle gebruiken, maar dat werkt niet op een normale lijst dus mijn List in Deck moet een ArrayList worden. En ik moet zorgen voor een mooiere toString functie in Card... 

Dus ik pas Deck aan:
```
import java.util.*;

public class Deck {
    private final static int DECK_SIZE = 30;
    private final ArrayList<Card> cards;

    public Deck(List<Card> cards) {
        if (cards.size() != DECK_SIZE) throw new IllegalArgumentException("Deck(): a deck must have 30 cards, this deck has " + cards.size() );
        this.cards = new ArrayList<>(cards);
    }

    public void mulligan(int numCardsToMulligan) {
        Collections.shuffle(cards);
        for (int i=1; i<= numCardsToMulligan; i++) {
            System.out.println(i + ". " + cards.get(i-1));
        }
        System.out.println("\nPlease give the numbers of the cards you want to mulligan (swap) (like '1 3'): ");
        Scanner in = new Scanner(System.in);
        String[] numbers = in.nextLine().split(" ");
        Random random = new Random();
        Set<Integer> swappedPositions = new HashSet<>(); // avoid swapping out card A, and returning it via another swap!
        int swapIndex;
        for (String cardToSwap : numbers) {
            var cardIndex = Integer.parseInt(cardToSwap) - 1;
            do {
                swapIndex = random.nextInt(DECK_SIZE - numCardsToMulligan) + numCardsToMulligan;
            } while (swappedPositions.contains(swapIndex));
            Card replacement = cards.get(swapIndex);
            cards.set(swapIndex, cards.get(cardIndex));
            cards.set(cardIndex, replacement);
            swappedPositions.add(swapIndex);
        }
        System.out.println("Your new cards:");
        for (int i=1; i<= numCardsToMulligan; i++) {
            System.out.println(i + ". " + cards.get(i-1));
        }
    }
}
```

Geef Card een mooiere toString:
```
@Override
public String toString() {
    return name + ": " + cost + " mana, " + description;
}
```

En zorg ervoor dat het spel laat mulligannen:

```
public Game(Player playerA, Player playerB) {
    if (random.nextInt(2) == 0) {
        firstPlayer = playerA;
        secondPlayer = playerB;
    } else {
        firstPlayer = playerB;
        secondPlayer = playerA;
    }
    var firstPlayerName = firstPlayer.getName();
    System.out.println("The game starts! " + firstPlayerName + " begins!");
    System.out.println(firstPlayerName + ", please keep or mulligan your cards...");
    firstPlayer.getDeck().mulligan(3);
    System.out.println(secondPlayer.getName() + ", please keep or mulligan your cards...");
    secondPlayer.getDeck().mulligan(4);
}
```

Ik splits de lange code van Deck.mulligan op (kan makkelijk met Ctrl+Alt+M) en dedupliceer het tonen van de kaarten:
```
public void mulligan(int numCardsToMulligan) {
    Collections.shuffle(cards);
    showFirst(numCardsToMulligan);
    System.out.println("\nPlease give the numbers of the cards you want to mulligan (swap) (like '1 3'): ");
    Scanner in = new Scanner(System.in);
    String[] numbers = in.nextLine().split(" ");
    performTheActualMulligan(numCardsToMulligan, numbers);
    System.out.println("Your new cards:");
    showFirst(numCardsToMulligan);
}

private void showFirst(int numCardsToMulligan) {
    for (int i = 1; i<= numCardsToMulligan; i++) {
        System.out.println(i + ". " + cards.get(i-1));
    }
}

private void performTheActualMulligan(int numCardsToMulligan, String[] numbers) {
    Random random = new Random();
    Set<Integer> swappedPositions = new HashSet<>(); // avoid swapping out card A, and returning it via another swap!
    int swapIndex;
    for (String cardToSwap : numbers) {
        var cardIndex = Integer.parseInt(cardToSwap) - 1;
        do {
            swapIndex = random.nextInt(DECK_SIZE - numCardsToMulligan) + numCardsToMulligan;
        } while (swappedPositions.contains(swapIndex));
        Card replacement = cards.get(swapIndex);
        cards.set(swapIndex, cards.get(cardIndex));
        cards.set(cardIndex, replacement);
        swappedPositions.add(swapIndex);
    }
}
```

Het lijkt erop dat we kunnen beginnen met het echte spel :) [ehm... na de aanwijzing van IDEA opvolgen om van de Player een record te maken...]