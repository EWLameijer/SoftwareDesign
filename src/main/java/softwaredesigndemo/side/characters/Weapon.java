package softwaredesigndemo.side.characters;

public class Weapon {
    private final int attack;
    
    private int durability;

    public Weapon(int attack, int durability) {
        this.attack = attack;
        this.durability = durability;
    }

    public void reduceDurability() {
        durability--;
    }

    public int getDurability() {
        return durability;
    }

    public int getAttack() {
        return attack;
    }
}
