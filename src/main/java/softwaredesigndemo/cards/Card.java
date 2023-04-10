package softwaredesigndemo.cards;

import softwaredesigndemo.Sides;
import softwaredesigndemo.utils.Color;

public abstract class Card {
    protected final String name;

    private final int cost;

    private final String description;

    public Card(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public boolean canPlay(Sides sides) {
        return cost <= sides.own().getManaBar().getAvailableMana();
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public abstract void play(Sides sides);

    @Override
    public String toString() {
        return name + ": " + cost + " mana, " + description;
    }

    public void communicateInvalidPlay(Sides sides) {
        if (cost > sides.own().getManaBar().getAvailableMana())
            Color.RED.println("You don't have enough mana to play this card!");
    }
}
