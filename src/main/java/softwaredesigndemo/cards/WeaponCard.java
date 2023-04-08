package softwaredesigndemo.cards;

import softwaredesigndemo.cards.Card;
import softwaredesigndemo.side.Side;

public class WeaponCard extends Card {
    private final int attack;

    private final int durability;

    public WeaponCard(String name, int cost, String description, int attack, int durability) {
        super(name, cost, description);
        this.attack = attack;
        this.durability = durability;
    }

    @Override
    public void play(Side ownSide, Side opponentsSide) {
    }
}

