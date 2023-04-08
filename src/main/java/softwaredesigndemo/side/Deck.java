package softwaredesigndemo.side;

import softwaredesigndemo.cards.Card;

import java.util.*;

public class Deck {
    private final ArrayList<Card> cards;

    public Deck(List<Card> cards) {
        final int initialDeckSize = 30;
        if (cards.size() != initialDeckSize)
            throw new IllegalArgumentException("Deck(): a deck must have %d cards, this deck has %d.".formatted(initialDeckSize, cards.size()));
        this.cards = new ArrayList<>(cards);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public int size() {
        return cards.size();
    }

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

    public Card draw() {
        if (!canDraw()) throw new IllegalStateException("Deck.draw() error: cannot draw from an empty deck!");
        var firstCard = cards.get(0);
        cards.remove(0);
        return firstCard;
    }

    public boolean canDraw() {
        return size() > 0;
    }
}
