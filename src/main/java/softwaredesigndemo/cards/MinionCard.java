package softwaredesigndemo.cards;

import softwaredesigndemo.cards.Card;
import softwaredesigndemo.side.Side;

public class MinionCard extends Card {
    private final int attack;

    private final int health;

    public MinionCard(String name, int cost, String description, int attack, int health) {
        super(name, cost, description);
        this.attack = attack;
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void play(Side ownSide, Side opponentsSide) {
        if (ownSide.getTerritory().canAddMinion()) {
            ownSide.getTerritory().addMinion(name, attack, health);
        } else throw new IllegalArgumentException("MinionCard.play() exception: board is full!");
    }
}
