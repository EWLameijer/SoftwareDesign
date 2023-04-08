package softwaredesigndemo.cards;

import softwaredesigndemo.Minion;
import softwaredesigndemo.side.Side;
import softwaredesigndemo.utils.Color;

import java.util.Set;

public class MinionCard extends Card {
    private final int attack;

    private final int health;

    private final Set<MinionProperty> properties;

    public MinionCard(String name, int cost, String description, int attack, int health, Set<MinionProperty> properties) {
        super(name, cost, description);
        this.attack = attack;
        this.health = health;
        this.properties = properties;
    }

    @Override
    public void play(Side ownSide, Side opponentsSide) {
        if (ownSide.getTerritory().canAddMinion()) {
            ownSide.getTerritory().addMinion(toMinion());
        } else throw new IllegalArgumentException("MinionCard.play() exception: board is full!");
    }

    @Override
    public boolean canPlay(Side ownSide, Side opponentsSide) {
        return super.canPlay(ownSide, opponentsSide) && ownSide.getTerritory().canAddMinion();
    }

    private Minion toMinion() {
        return new Minion(name, attack, health, properties);
    }

    @Override
    public void communicateInvalidPlay(Side ownSide, Side opponentsSide) {
        if (!super.canPlay(ownSide, opponentsSide)) super.communicateInvalidPlay(ownSide,opponentsSide);
        else Color.RED.println("You can't play any more minions! The board is full!");
    }
}
