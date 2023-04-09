package softwaredesigndemo.side.characters;

import java.util.List;

public class Minion extends HearthstoneCharacter {
    private final String name;

    private boolean attackedThisTurn = false;

    private boolean isFirstTurn = true;

    public Minion(String name, int attack, int health, List<Enhancement> enhancements) {
        super(health, attack, enhancements);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean canAttack() {
        return !attackedThisTurn && (!isFirstTurn || stats.hasCharge());
    }

    public void readyForAttack() {
        attackedThisTurn = false;
        isFirstTurn = false;
    }

    public boolean hasTaunt() {
        return stats.hasTaunt();
    }

    public void attack(HearthstoneCharacter attackee) {
        attackee.takeDamage(stats.getAttack());
        attackedThisTurn = true;
        if (attackee instanceof Minion otherMinion) {
            // heroes CANNOT counterattack
            takeDamage(otherMinion.stats.getAttack());
        }
    }

    public int getAttack() {
        return stats.getAttack();
    }
}
