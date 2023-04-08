package softwaredesigndemo.side.characters;

public class HearthStoneCharacter {
    protected final int maxHealth;

    protected int currentHealth;

    protected HearthStoneCharacter(int maxHealth) {
        this.maxHealth = maxHealth;
        currentHealth = maxHealth;
    }

    public int getHealth() {
        return currentHealth;
    }
}
