package softwaredesigndemo.side;

import softwaredesigndemo.Minion;
import softwaredesigndemo.utils.Color;

import java.util.ArrayList;
import java.util.function.UnaryOperator;

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
        System.out.printf("[%s]\n", String.join(" | ", minions.stream().map(Minion::colorAsFriendly).toList()));
    }

    public void showAsEnemy() {
        System.out.printf("[%s]%n", String.join(" | ", minions.stream().map(this::colorAsEnemy).toList()));
    }

    public UnaryOperator<String> colorEnemy(boolean isAttackable) {
        return isAttackable ? Color.RED::color : Color.PURPLE::color;
    }

    private String colorAsEnemy(Minion minion) {
        return colorEnemy(isAttackable(minion)).apply(minion.getName());
    }

    private boolean isAttackable(Minion minion) {
        return minions.stream().noneMatch(Minion::hasTaunt) || minion.hasTaunt();
    }

    public boolean isTauntMinionPresent() {
        return minions.stream().anyMatch(Minion::hasTaunt);
    }
}
