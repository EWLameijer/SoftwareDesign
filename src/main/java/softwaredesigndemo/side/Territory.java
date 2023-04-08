package softwaredesigndemo.side;

import softwaredesigndemo.Minion;
import softwaredesigndemo.cards.MinionProperty;

import java.util.ArrayList;
import java.util.Set;

public class Territory {
    private static final int MAX_NUM_MINIONS = 7;
    private final ArrayList<Minion> minions = new ArrayList<>();

    public void startTurn() {
        minions.forEach(Minion::readyForAttack);
    }

    public boolean canAddMinion() {
        return minions.size() < MAX_NUM_MINIONS;
    }

    public void addMinion(String name, int attack, int health, Set<MinionProperty> properties) {
        if (canAddMinion()) minions.add(new Minion(name, attack, health, properties));
        else throw new IllegalStateException("Territory.addMinion() error: cannot add a minion to a full Territory!");
    }

    public void show() {
        System.out.printf("[%s]\n", String.join(" | ", minions.stream().map(Minion::getName).toList()));
    }
}
