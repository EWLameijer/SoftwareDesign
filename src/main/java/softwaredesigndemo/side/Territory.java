package softwaredesigndemo.side;

import softwaredesigndemo.Minion;
import softwaredesigndemo.cards.MinionProperty;

import java.util.ArrayList;
import java.util.Set;

public class Territory {
    private final ArrayList<Minion> minions = new ArrayList<>();

    public void startTurn() {
        minions.forEach(Minion::readyForAttack);
    }

    public boolean canAddMinion() {
        final int maximumNumberOfMinions = 7;
        return minions.size() < maximumNumberOfMinions;
    }

    public void addMinion(Minion minion) {
        if (canAddMinion()) minions.add(minion);
        else throw new IllegalStateException("Territory.addMinion() error: cannot add a minion to a full Territory!");
    }

    public void show() {
        System.out.printf("[%s]\n", String.join(" | ", minions.stream().map(Minion::getName).toList()));
    }
}
