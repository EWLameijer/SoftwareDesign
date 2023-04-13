package softwaredesigndemo.side.characters;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class HearthstoneCharacter {
    final protected Stats stats;

    protected int attacksRemainingThisTurn;

    private final String name;

    protected HearthstoneCharacter(int maxHealth, int attack, List<Enhancement> enhancements, String name) {
        this.stats = new Stats(maxHealth, attack, enhancements);
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
        return stats.getAttack() > 0 && !isFrozen();
    }

    public String attack(HearthstoneCharacter attackee) {
        attackee.takeDamage(stats.getAttack());
        attacksRemainingThisTurn--;
        if (attackee instanceof Minion otherMinion) {
            // heroes CANNOT counterattack
            takeDamage(otherMinion.stats.getAttack());
        }
        return name + " attacks " + attackee.name;
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
}
