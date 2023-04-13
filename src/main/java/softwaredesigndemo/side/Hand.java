package softwaredesigndemo.side;

import softwaredesigndemo.Sides;
import softwaredesigndemo.cards.Card;
import softwaredesigndemo.utils.Color;

import java.util.*;

public class Hand {
    final Random random = new Random();

    private final List<Card> cards = new ArrayList<>();

    public Hand() {
    }

    // for unit testing
    private Hand(Card... cards) {
        this.cards.addAll(List.of(cards));
    }

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

    public String play(int index, Sides sides) {
        if (index < 0 || index >= cards.size())
            return Color.RED.color("Card number " + index + " does not exist!");
        var chosenCard = cards.get(index);
        if (chosenCard.canPlay(sides)) {
            sides.own().getManaBar().consume(chosenCard.getCost());
            chosenCard.play(sides);
            cards.remove(index);
            return "Playing " + chosenCard.getName();
        } else return chosenCard.communicateInvalidPlay(sides);
    }

    public void mulligan(Deck deck) {
        showAsMulligan();
        System.out.println("\nPlease give the numbers of the cards you want to mulligan (swap) (like '1 3'): ");
        String line = getNumberString();
        String[] numbers = line.split(" ");
        if (!numbers[0].isEmpty()) {
            mulliganCards(deck, numbers);
            System.out.println("Your new cards:");
            showAsMulligan();
        }
    }

    private static String getNumberString() {
        Scanner in = new Scanner(System.in);
        do {
            String line = in.nextLine();
            if (!line.chars().allMatch(c -> Character.isWhitespace(c) || Character.isDigit(c)))
                Color.RED.println("Enter digits and spaces only!");
            else return line;
        } while (true);
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

    static Hand createTestHand(Card... cards) {
        return new Hand(cards);
    }
}
