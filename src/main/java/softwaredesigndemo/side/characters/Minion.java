package softwaredesigndemo.side.characters;

import softwaredesigndemo.cards.MinionProperty;

import java.util.Set;

public class Minion extends HearthStoneCharacter {
    private final String name;

    private final int attack;
    private final Set<MinionProperty> properties;
    private boolean canAttack;

    public Minion(String name, int attack, int health, Set<MinionProperty> properties) {
        super(health);
        this.name = name;
        this.attack = attack;
        this.properties = properties;
        this.canAttack = properties.contains(MinionProperty.CHARGE);
    }

    public String getName() {
        return name;
    }

    public boolean canAttack() {
        return canAttack;
    }

    public void readyForAttack() {
        canAttack = true;
    }

    public boolean hasTaunt() {
        return properties.contains(MinionProperty.TAUNT);
    }

    public void attack(HearthStoneCharacter attackee) {
        attackee.currentHealth -= attack;
        canAttack = false;
        if (attackee instanceof Minion otherMinion) {
            // heroes CANNOT counterattack
            currentHealth -= otherMinion.attack;
        }
    }

    public int getAttack() {
        return attack;
    }
}
