package softwaredesigndemo;

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
}
