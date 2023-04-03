package softwaredesigndemo;

enum HeroType { MAGE, WARRIOR }

public class Hero {
    private final HeroType type;

    private int hitPoints = 30;

    private Hero(HeroType type) {
        this.type = type;
    }

    public static Hero from(HeroType type) {
        return new Hero(type);
    }
}
