package softwaredesigndemo.side;

import softwaredesigndemo.utils.Color;

public class Hero {
    private final HeroType type;

    private int hitPoints = 30;

    private int exhaustionDamage = 0;

    private Hero(HeroType type) {
        this.type = type;
    }

    public static Hero from(HeroType type) {
        return new Hero(type);
    }

    public void takeExhaustionDamage() {
        Color.RED.println("Out of cards! You take " + ++exhaustionDamage + "damage!");
        hitPoints -= exhaustionDamage;
    }

    public HeroType getType() {
        return type;
    }

    public int getHitPoints() {
        return hitPoints;
    }
}
