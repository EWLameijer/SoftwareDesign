package softwaredesigndemo.side.characters;

import softwaredesigndemo.cards.MinionProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

public class Stats {
    // TODO: Need some kind of enhancement method that increases health the FIRST time, but not on subsequent calculations.
    private final int maxHealth;

    private final int attack;

    private final ArrayList<Enhancement> enhancements;

    private int health;

    private final Set<MinionProperty> properties = new HashSet<>();

    public Stats(int attack, int maxHealth, List<Enhancement> enhancements) {
        this.maxHealth = maxHealth;
        health = maxHealth;
        this.attack = attack;
        this.enhancements = new ArrayList<>(enhancements);
    }

    private Stats(int attack, int maxHealth, int health, List<Enhancement> enhancements, Set<MinionProperty> properties) {
        this.maxHealth = maxHealth;
        this.health = health;
        this.attack = attack;
        this.enhancements = new ArrayList<>(enhancements);
        this.properties.addAll(properties);
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
        var statsCopy = new Stats(attack, maxHealth, health, enhancements, properties);
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

    public Stats changeAttack(int attackChange) {
        return new Stats(attack + attackChange, maxHealth, health, enhancements, properties);
    }

    public void enhance(UnaryOperator<Stats> transform) {
        enhancements.add(new Enhancement(transform));
    }

    public void enhance(Enhancement enhancement) {
        enhancements.add(enhancement);
    }


    public void enhance(UnaryOperator<Stats> transform, int turns) {
        enhancements.add(new TemporaryEnhancement(transform, turns));
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void destroy() {
        health = 0;
    }

    public void countDownTemporaryEnhancements() {
        for (Enhancement enhancement : enhancements) {
            if (enhancement instanceof TemporaryEnhancement temporaryEnhancement) {
                temporaryEnhancement.countDown();
            }
        }
        for (int i = enhancements.size() - 1; i >= 0; i--) {
            var enhancement = enhancements.get(i);
            if (!enhancement.isActive()) enhancements.remove(i);
        }
    }

    public void tryUnfreeze() {
        for (int i = enhancements.size() - 1; i >= 0; i--) {
            var enhancement = enhancements.get(i);
            if (enhancement == Enhancement.FROZEN) enhancements.remove(i);
        }
    }

    public boolean isFrozen() {
        return enhancements.contains(Enhancement.FROZEN);
    }
}
