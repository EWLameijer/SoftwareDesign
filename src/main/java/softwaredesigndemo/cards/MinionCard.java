package softwaredesigndemo.cards;

import softwaredesigndemo.cards.Card;
import softwaredesigndemo.side.Side;

import java.util.Set;

public class MinionCard extends Card {
    private final int attack;

    private final int health;

    private final Set<MinionProperty> minionProperties;

    public MinionCard(String name, int cost, String description, int attack, int health, Set<MinionProperty> minionProperties) {
        super(name, cost, description);
        this.attack = attack;
        this.health = health;
        this.minionProperties = minionProperties;
    }

    @Override
    public void play(Side ownSide, Side opponentsSide) {
        if (ownSide.getTerritory().canAddMinion()) {
            ownSide.getTerritory().addMinion(name, attack, health, minionProperties);
        } else throw new IllegalArgumentException("MinionCard.play() exception: board is full!");
    }
}
