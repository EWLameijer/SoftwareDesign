package softwaredesigndemo.cards;

import softwaredesigndemo.cards.Card;
import softwaredesigndemo.side.Side;

public class SpellCard extends Card {
    public SpellCard(String name, int cost, String description) {
        super(name, cost, description);
    }

    @Override
    public void play(Side ownSide, Side opponentsSide) {

    }
}
