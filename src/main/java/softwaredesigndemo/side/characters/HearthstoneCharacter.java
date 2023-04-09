package softwaredesigndemo.side.characters;

import java.util.List;

public class HearthstoneCharacter {
    final protected Stats stats;

    protected HearthstoneCharacter(int maxHealth, int attack, List<Enhancement> enhancements) {
        this.stats = new Stats(maxHealth, attack, enhancements);
    }

    public int getHealth() {
        return stats.getHealth();
    }

    public void takeDamage(int damage) {
        stats.takeDamage(damage);
    }
}
