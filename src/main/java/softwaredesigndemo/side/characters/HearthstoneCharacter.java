package softwaredesigndemo.side.characters;

import java.util.List;
import java.util.function.UnaryOperator;

public abstract class HearthstoneCharacter {
    final protected Stats stats;

    protected boolean attackedThisTurn = false;

    protected HearthstoneCharacter(int maxHealth, int attack, List<Enhancement> enhancements) {
        this.stats = new Stats(maxHealth, attack, enhancements);
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
        return stats.getAttack() > 0 && !attackedThisTurn;
    }

    public void attack(HearthstoneCharacter attackee) {
        attackee.takeDamage(stats.getAttack());
        attackedThisTurn = true;
        if (attackee instanceof Minion otherMinion) {
            // heroes CANNOT counterattack
            takeDamage(otherMinion.stats.getAttack());
        }
    }

    public void startTurn() {
        attackedThisTurn = false;
        stats.countDownTemporaryEnhancements();
    }
}
