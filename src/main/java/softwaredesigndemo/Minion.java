package softwaredesigndemo;

import softwaredesigndemo.cards.MinionProperty;
import softwaredesigndemo.utils.Color;

import java.util.Set;

public class Minion {
    private final String name;

    private final int attack;

    private final int maxHealth;

    private int currentHealth;

    private boolean canAttack;

    private final Set<MinionProperty> properties;

    public Minion(String name, int attack, int health, Set<MinionProperty> properties) {
        this.name = name;
        this.attack = attack;
        this.currentHealth = this.maxHealth = health;
        this.properties = properties;
        this.canAttack = properties.contains(MinionProperty.CHARGE);
    }

    public String getName() {
        return name;
    }

    public boolean canAttack() {
        return canAttack;
    }

    public String colorAsFriendly() {
        Color color = canAttack ? Color.YELLOW : Color.BLUE;
        return color.color(name);
    }

    public void readyForAttack() {
        canAttack = true;
    }

    public boolean hasTaunt() {
        return properties.contains(MinionProperty.TAUNT);
    }

    public void attack(Minion attackee) {
        attackee.currentHealth -= attack;
        currentHealth -= attackee.attack;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getAttack() {
        return attack;
    }
}
