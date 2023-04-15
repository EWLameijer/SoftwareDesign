package softwaredesigndemo.side.characters;

import java.util.List;
import java.util.function.UnaryOperator;

import static softwaredesigndemo.side.characters.Hero.DEFAULT_HERO_NAME;

public abstract class HearthstoneCharacter {
    protected Stats stats; // cannot be final due to polymorph

    protected int attacksRemainingThisTurn;

    private String name; // cannot be final due to polymorph

    protected HearthstoneCharacter(int attack, int maxHealth, List<Enhancement> enhancements, String name) {
        this.stats = new Stats(attack, maxHealth, enhancements);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return stats.getHealth();
    }

    public void takeDamage(int damage) {
        stats.takeDamage(damage);
    }

    public void enhance(UnaryOperator<Stats> transform) {
        stats.enhance(transform);
    }

    public void enhance(UnaryOperator<Stats> transform, int turns) {
        stats.enhance(transform, turns);
    }

    public int getMaxHealth() {
        return stats.getMaxHealth();
    }

    public void destroy() {
        stats.destroy();
    }

    public boolean canAttack() {
        return getAttack() > 0 && !isFrozen();
    }

    abstract protected int getAttack();

    public String attack(HearthstoneCharacter attackee) {
        attackee.takeDamage(getAttack());
        attacksRemainingThisTurn--;
        if (attackee instanceof Minion otherMinion) {
            // heroes CANNOT counterattack
            takeDamage(otherMinion.getAttack());
        }
        String attackeeName = attackee.name.equals(DEFAULT_HERO_NAME) ? "the enemy hero" : attackee.name;
        return name + " attacks " + attackeeName;
    }

    public void startTurn() {
        attacksRemainingThisTurn = 1; // TODO: may need to update for WindFury minions
        stats.countDownTemporaryEnhancements();
    }

    public void freeze() {
        if (!stats.isFrozen()) {
            attacksRemainingThisTurn--;
            if (attacksRemainingThisTurn <= 0) stats.enhance(Enhancement.FROZEN);
        }
    }

    public void tryUnfreeze() {
        if (attacksRemainingThisTurn >= 0) stats.tryUnfreeze();
    }

    public boolean isFrozen() {
        return stats.isFrozen();
    }

    public boolean hasTaunt() {
        return false; // for hero. Can differ for minions
    }

    public void polymorph(String newName, int attack, int health) {
        name = newName;
        stats = new Stats(attack, health, List.of());
    }
}
