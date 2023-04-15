package softwaredesigndemo.side.characters;

import softwaredesigndemo.Sides;
import softwaredesigndemo.side.HeroType;
import softwaredesigndemo.utils.Color;

import java.util.List;
import java.util.function.UnaryOperator;

public class Hero extends HearthstoneCharacter {
    static final int MAXIMUM_HP = 30;

    private final HeroType type;

    private Weapon weapon;

    private int exhaustionDamage = 0;

    private int armor = 0;

    private boolean hasUsedHeroPowerThisTurn = false;

    protected static String DEFAULT_HERO_NAME = "Your hero";

    private Hero(HeroType type) {
        super(0, MAXIMUM_HP, List.of(), DEFAULT_HERO_NAME);
        this.type = type;
        attacksRemainingThisTurn = 1;
    }

    @Override
    public void startTurn() {
        super.startTurn();
        hasUsedHeroPowerThisTurn = false;
    }

    public void equip(Weapon weapon) {
        this.weapon = weapon;
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
            super.takeDamage(damage - armor);
            armor = 0;
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
        String armorText = armor > 0 ? " [+" + armor + " ARMOR]" : "";
        int attack = getAttack();
        String attackText = attack > 0 ? "<" + attack + " ATTACK>" : "";
        System.out.println(colorFunction.apply("%s (%s): %d HP%s %s(%c)".
                formatted(playerName, getType().name(), getHealth(), armorText, attackText, symbol)));
    }

    protected int getAttack() {
        int weaponAttack = weapon != null ? weapon.getAttack() : 0;
        return weaponAttack + stats.getAttack();
    }

    @Override
    public boolean canAttack() {
        return super.canAttack() && attacksRemainingThisTurn > 0;
    }

    @Override
    public String attack(HearthstoneCharacter attackee) {
        String outcome = super.attack(attackee);
        if (weapon != null) {
            weapon.reduceDurability();
            if (weapon.getDurability() <= 0) weapon = null;
        }
        return outcome;
    }

    public String useHeroPower(Sides sides) {
        if (sides.own().getManaBar().getAvailableMana() < 2)
            return Color.RED.color("You need two mana to use your hero power.");
        if (hasUsedHeroPowerThisTurn) return Color.RED.color("You have already used your hero power this turn!");
        var spell = type.getSpell();
        if (spell.canCast(sides)) {
            spell.cast(sides);
            sides.own().getManaBar().consume(2);
            return "Hero power used!";
        } else return Color.RED.color("Invalid target for your hero power!");
    }
}
