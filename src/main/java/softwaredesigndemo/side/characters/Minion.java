package softwaredesigndemo.side.characters;

import java.util.List;

public class Minion extends HearthstoneCharacter {

    private boolean isFirstTurn = true;

    public Minion(String name, int attack, int health, List<Enhancement> enhancements) {
        super(health, attack, enhancements, name);
        attacksRemainingThisTurn = 0; // won't be really appropriate for a charge minion, but since charge can also be
        // obtained by a spell or lost by silence, in the first turn I will count 1 less for now
    }


    @Override
    public boolean canAttack() {
        if (!super.canAttack()) return false;
        if (isFirstTurn) return stats.hasCharge() && attacksRemainingThisTurn >= 0;
        return attacksRemainingThisTurn >= 1;
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
