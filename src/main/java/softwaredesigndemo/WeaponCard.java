package softwaredesigndemo;

public class WeaponCard extends Card {
    private final int attack;

    private final int durability;

    public WeaponCard(String name, int cost, String description, int attack, int durability) {
        super(name, cost, description);
        this.attack = attack;
        this.durability = durability;
    }

    public int getAttack() {
        return attack;
    }

    public int getDurability() {
        return durability;
    }
}

