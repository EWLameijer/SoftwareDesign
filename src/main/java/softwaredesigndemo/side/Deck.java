package softwaredesigndemo.side;

import softwaredesigndemo.cards.Card;

import java.util.*;

public class Deck {
    private final static int DECK_SIZE = 30;
    private final ArrayList<Card> cards;

    public Deck(List<Card> cards) {
        if (cards.size() != DECK_SIZE) throw new IllegalArgumentException("Deck(): a deck must have 30 cards, this deck has " + cards.size() );
        this.cards = new ArrayList<>(cards);
    }

    private void showFirst(int numCardsToMulligan) {
        for (int i = 1; i<= numCardsToMulligan; i++) {
            System.out.println(i + ". " + cards.get(i-1));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

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

    public Card draw() {
        var firstCard = cards.get(0);
        cards.remove(0);
        return firstCard;
    }
}
