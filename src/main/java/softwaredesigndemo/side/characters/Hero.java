package softwaredesigndemo.side.characters;

import softwaredesigndemo.side.HeroType;
import softwaredesigndemo.utils.Color;

import java.util.List;
import java.util.function.UnaryOperator;

public class Hero extends HearthstoneCharacter {
    static final int MAXIMUM_HP = 30;
    private final HeroType type;
    private int exhaustionDamage = 0;

    private int armor = 0;

    private Hero(HeroType type) {
        super(MAXIMUM_HP, 0, List.of());
        this.type = type;
    }

    public static Hero from(HeroType type) {
        return new Hero(type);
    }

    public void takeExhaustionDamage() {
        Color.RED.println("Out of cards! You take " + ++exhaustionDamage + "damage!");
        takeDamage(exhaustionDamage);
    }

    @Override
    public void takeDamage(int damage) {
        if (damage > armor) {
            armor = 0;
            super.takeDamage(damage - armor);
        } else {
            armor -= damage;
        }
    }

    public HeroType getType() {
        return type;
    }

    public void increaseArmor(int extraArmor) {
        armor += extraArmor;
    }

    public void show(String playerName, UnaryOperator<String> colorFunction, char symbol) {
        String armorString = armor > 0 ? " [+" + armor + " ARMOR]" : "";
        String attackString = stats.getAttack() > 0 ? "<" + stats.getAttack() + " ATTACK>" : "";
        System.out.println(colorFunction.apply("%s (%s): %d HP%s %s(%c)".
                formatted(playerName, getType().name(), getHealth(), armorString, attackString, symbol)));
    }
}
