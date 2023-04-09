package softwaredesigndemo.side.characters;

import softwaredesigndemo.cards.MinionProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Stats {
    // TODO: Need some kind of enhancement method that increases health the FIRST time, but not on subsequent calculations.
    private final int maxHealth;

    private final int attack;

    private final List<Enhancement> enhancements;

    private int health;

    private final Set<MinionProperty> properties = new HashSet<>();

    public Stats(int maxHealth, int attack, List<Enhancement> enhancements) {
        this.maxHealth = maxHealth;
        health = maxHealth;
        this.attack = attack;
        this.enhancements = enhancements;
    }

    private Stats(int maxHealth, int health, int attack, List<Enhancement> enhancements) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.attack = attack;
        this.enhancements = enhancements;
    }

    int getHealth() {
        return health;
    }

    int getAttack() {
        return getEnhancedStats().attack;
    }

    private Stats getEnhancedStats() {
        var enhancedStats = this;
        for (var enhancement : enhancements) {
            enhancedStats = enhancement.apply(enhancedStats);
        }
        return enhancedStats;
    }

    public Stats addProperty(MinionProperty property) {
        var statsCopy = new Stats(maxHealth, health, attack, enhancements);
        statsCopy.properties.add(property);
        return statsCopy;
    }

    boolean hasCharge() {
        return getEnhancedStats().properties.contains(MinionProperty.CHARGE);
    }

    boolean hasTaunt() {
        return getEnhancedStats().properties.contains(MinionProperty.TAUNT);
    }

    public void takeDamage(int damage) {
        health -= damage;
    }
}
