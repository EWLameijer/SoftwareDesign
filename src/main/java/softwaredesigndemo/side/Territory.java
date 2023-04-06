package softwaredesigndemo.side;

import softwaredesigndemo.Minion;

import java.util.ArrayList;

public class Territory {
    private static final int MAX_NUM_MINIONS = 7;
    private final ArrayList<Minion> minions = new ArrayList<>();

    public boolean canAddMinion() {
        return minions.size() < MAX_NUM_MINIONS;
    }

    public void addMinion(String name, int attack, int health) {
        if (canAddMinion()) minions.add(new Minion(name, attack, health));
        else throw new IllegalStateException("Territory.addMinion() error: cannot add a minion to a full Territory!");
    }

    public void show() {
        System.out.printf("[%s]\n", String.join(" | ", minions.stream().map(Minion::getName).toList()));
    }
}
