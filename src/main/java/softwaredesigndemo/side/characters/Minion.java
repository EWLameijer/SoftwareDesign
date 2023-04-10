package softwaredesigndemo.side.characters;

import java.util.List;

public class Minion extends HearthstoneCharacter {
    private final String name;

    private boolean isFirstTurn = true;

    public Minion(String name, int attack, int health, List<Enhancement> enhancements) {
        super(health, attack, enhancements);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean canAttack() {
        return super.canAttack() && (!isFirstTurn || stats.hasCharge());
    }

    @Override
    public void startTurn() {
        super.startTurn();
        isFirstTurn = false;
    }

    public boolean hasTaunt() {
        return stats.hasTaunt();
    }


    public int getAttack() {
        return stats.getAttack();
    }
}
