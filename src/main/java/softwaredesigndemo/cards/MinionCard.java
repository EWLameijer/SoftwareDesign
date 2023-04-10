package softwaredesigndemo.cards;

import softwaredesigndemo.Sides;
import softwaredesigndemo.side.characters.Enhancement;
import softwaredesigndemo.side.characters.Minion;
import softwaredesigndemo.utils.Color;

import java.util.List;

public class MinionCard extends Card {
    private final int attack;

    private final int health;

    private final List<Enhancement> enhancements;

    public MinionCard(String name, int cost, String description, int attack, int health, List<Enhancement> enhancements) {
        super(name, cost, description);
        this.attack = attack;
        this.health = health;
        this.enhancements = enhancements;
    }

    @Override
    public void play(Sides sides) {
        var ownSide = sides.own();
        if (ownSide.getTerritory().canAddMinion()) {
            ownSide.getTerritory().addMinion(toMinion());
        } else throw new IllegalArgumentException("MinionCard.play() exception: board is full!");
    }

    @Override
    public boolean canPlay(Sides sides) {
        return super.canPlay(sides) && sides.own().getTerritory().canAddMinion();
    }

    private Minion toMinion() {
        return new Minion(name, attack, health, enhancements);
    }

    @Override
    public void communicateInvalidPlay(Sides sides) {
        if (!super.canPlay(sides)) super.communicateInvalidPlay(sides);
        else Color.RED.println("You can't play any more minions! The board is full!");
    }
}
