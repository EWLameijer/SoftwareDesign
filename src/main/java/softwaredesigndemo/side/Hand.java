package softwaredesigndemo.side;

import softwaredesigndemo.Sides;
import softwaredesigndemo.cards.Card;
import softwaredesigndemo.utils.Color;

import java.util.*;

public class Hand {
    final Random random = new Random();

    private final List<Card> cards = new ArrayList<>();

    public void add(Card card) {
        final int maxHandSize = 10;
        if (cards.size() >= maxHandSize) {
            Color.RED.println("You overdraw a card! " + card.getName() + " is destroyed.");
        } else {
            cards.add(card);
        }
    }

    private void show(int availableMana, boolean gameHasStarted) {
        for (int i = 0; i < cards.size(); i++) {
            var chosenCard = cards.get(i);
            // don't color a card green during the mulligan
            Color playableColor = gameHasStarted && chosenCard.getCost() <= availableMana ? Color.GREEN : Color.BLACK;
            playableColor.println(i + ". " + chosenCard);
        }
    }

    public void showDuringGame(int availableMana) {
        show(availableMana, true);
    }

    private void showAsMulligan() {
        show(0, false);
    }

    public void play(int index, Sides sides) {
        if (index < 0 || index >= cards.size()) {
            Color.RED.println("Card number " + index + " does not exist!");
            return;
        }
        var chosenCard = cards.get(index);
        if (chosenCard.canPlay(sides)) {
            sides.own().getManaBar().consume(chosenCard.getCost());
            System.out.println("Playing " + chosenCard.getName());
            chosenCard.play(sides);
            cards.remove(index);
        } else {
            chosenCard.communicateInvalidPlay(sides);
        }
    }

    public void mulligan(Deck deck) {
        showAsMulligan();
        System.out.println("\nPlease give the numbers of the cards you want to mulligan (swap) (like '1 3'): ");
        Scanner in = new Scanner(System.in);
        String[] numbers = in.nextLine().split(" ");

        if (!numbers[0].isEmpty()) {
            mulliganCards(deck, numbers);
            System.out.println("Your new cards:");
            showAsMulligan();
        }
    }

    private void mulliganCards(Deck deck, String[] numbers) {
        ArrayList<Integer> validNumbers = new ArrayList<>(Arrays.stream(numbers).map(Integer::parseInt).filter(n -> n >= 0 && n < cards.size()).toList());
        List<Integer> replacementIndices = new ArrayList<>();
        do {
            int nextRandom = random.nextInt(deck.size());
            if (!replacementIndices.contains(nextRandom)) replacementIndices.add(nextRandom);
        } while (replacementIndices.size() < validNumbers.size());

        for (int replacement = 0; replacement < validNumbers.size(); replacement++) {
            int naturalCardIndex = validNumbers.get(replacement);
            var cardToSwap = cards.get(naturalCardIndex);
            int deckSwapPosition = replacementIndices.get(replacement);
            cards.set(naturalCardIndex, deck.get(deckSwapPosition));
            deck.set(deckSwapPosition, cardToSwap);
        }
    }
}
