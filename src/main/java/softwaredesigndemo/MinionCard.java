package softwaredesigndemo;

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
}
