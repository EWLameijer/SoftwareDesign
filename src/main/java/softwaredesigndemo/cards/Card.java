package softwaredesigndemo.cards;

import softwaredesigndemo.side.Side;

public abstract class Card {
    protected final String name;

    private final int cost;

    private final String description;

    public Card(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public abstract void play(Side ownSide, Side opponentsSide);

    @Override
    public String toString() {
        return name + ": " + cost + " mana, " + description;
    }
}
