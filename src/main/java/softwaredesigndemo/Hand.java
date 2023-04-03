package softwaredesigndemo;

import java.util.*;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void add(Card card) {
        if (cards.size() >= 10) {
            System.out.printf("You overdraw a card! %s is destroyed.\n", card.getName());
        } else {
            cards.add(card);
        }
    }

    public void show() {
        for (int i = 0; i < cards.size(); i++) {
            System.out.println(i + ". " + cards.get(i));
        }
    }

    public void play(int index, ManaBar manaBar) {
        if (index < 0 || index > cards.size())
            throw new IllegalArgumentException("Hand.play() error: " + index + " is not a valid index!");
        var chosenCard = cards.get(index);
        if (chosenCard.getCost() > manaBar.getCurrentCapacity()) System.out.println("I need more mana to play that card!");
        else {
            manaBar.consume(chosenCard.getCost());
            System.out.println("Playing " +chosenCard.getName());
            cards.remove(index);
        }
    }

    final Random random = new Random();

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
}
