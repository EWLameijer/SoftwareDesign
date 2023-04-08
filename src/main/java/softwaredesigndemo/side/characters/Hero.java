package softwaredesigndemo.side.characters;

import softwaredesigndemo.side.HeroType;
import softwaredesigndemo.utils.Color;

public class Hero extends HearthStoneCharacter {
    static final int MAXIMUM_HP = 30;
    private final HeroType type;
    private int exhaustionDamage = 0;

    private Hero(HeroType type) {
        super(MAXIMUM_HP);
        this.type = type;
    }

    public static Hero from(HeroType type) {
        return new Hero(type);
    }

    public void takeExhaustionDamage() {
        Color.RED.println("Out of cards! You take " + ++exhaustionDamage + "damage!");
        currentHealth -= exhaustionDamage;
    }

    public HeroType getType() {
        return type;
    }
}
