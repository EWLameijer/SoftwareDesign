package softwaredesigndemo;

public class Minion {
    private final String name;

    private final int attack;

    private final int maxHealth;

    private int currentHealth;

    public Minion(String name, int attack, int health) {
        this.name = name;
        this.attack = attack;
        this.currentHealth = this.maxHealth = health;
    }

    public String getName() {
        return name;
    }
}
