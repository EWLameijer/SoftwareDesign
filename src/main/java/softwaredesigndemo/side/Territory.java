package softwaredesigndemo.side;

import softwaredesigndemo.Minion;
import softwaredesigndemo.utils.Color;

import java.util.ArrayList;
import java.util.List;
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
        System.out.printf("[%s]\n", String.join(" | ", minions.stream().map(this::colorAsFriendly).toList()));
    }

    private String colorAsFriendly(Minion minion) {
        int minionIndex = minions.indexOf(minion);
        return colorFriendly(minion).apply(minionDisplayString(minion, (char)(minionIndex+'A')));
    }

    public UnaryOperator<String> colorFriendly(Minion minion) {
        return minion.canAttack() ? Color.YELLOW::color : Color.BLUE::color;
    }

    public void showAsEnemy() {
        System.out.printf("[%s]%n", String.join(" | ", minions.stream().map(this::colorAsEnemy).toList()));
    }

    public UnaryOperator<String> colorEnemy(boolean isAttackable) {
        return isAttackable ? Color.RED::color : Color.PURPLE::color;
    }

    private final static List<Character> indexToSymbol = List.of('!', '@', '#', '$', '%', '^', '&');

    private String colorAsEnemy(Minion minion) {
        int minionIndex = minions.indexOf(minion);
        return colorEnemy(isAttackable(minion)).apply(minionDisplayString(minion,  indexToSymbol.get(minionIndex)));
    }

    private String minionDisplayString(Minion minion, char minionSymbol) {
        return "%s %d/%d (%c)".formatted(minion.getName(), minion.getAttack(), minion.getCurrentHealth(), minionSymbol);
    }

    private boolean isAttackable(Minion minion) {
        return minions.stream().noneMatch(Minion::hasTaunt) || minion.hasTaunt();
    }

    public boolean isTauntMinionPresent() {
        return minions.stream().anyMatch(Minion::hasTaunt);
    }

    public boolean isValidAttacker(char minionSymbol) {
        int minionIndex = getMinionIndex(minionSymbol);
        if (minionIndex >= minions.size()) return false;
        return minions.get(minionIndex).canAttack();
    }

    private static int getMinionIndex(char minionSymbol) {
        char normalizedSymbol = Character.toUpperCase(minionSymbol);
        return normalizedSymbol - 'A';
    }

    public void communicateInvalidAttacker(char minionSymbol) {
        int minionIndex = getMinionIndex(minionSymbol);
        if (minionIndex >= minions.size()) System.out.printf("There is no minion '%c'!\n", minionIndex + 'A');
        if (!minions.get(minionIndex).canAttack()) System.out.printf("Minion %c cannot currently attack!\n", minionIndex + 'A');
    }

    public boolean isValidAttackee(char minionSymbol) {
        int minionIndex = indexToSymbol.indexOf(minionSymbol);
        if (minionIndex < 0 || minionIndex >= minions.size()) return false; // no such minion
        return isAttackable(minions.get(minionIndex));
    }

    public void communicateInvalidAttackee(char minionSymbol) {
        int minionIndex = indexToSymbol.indexOf(minionSymbol);
        if (minionIndex < 0 || minionIndex >= minions.size()) System.out.printf("There is no minion '%c'!\n", minionSymbol);
        if (!isAttackable(minions.get(minionIndex))) System.out.println("A minion with taunt is in the way!");
    }

    public Minion getMinion(char minionSymbol) {
        if (Character.isLetter(minionSymbol)) return minions.get(Character.toUpperCase(minionSymbol) - 'A');
        else return minions.get(indexToSymbol.indexOf(minionSymbol));
    }

    public void disposeOfDeceased() {
        for (int i=0; i < minions.size(); i++) {
            var minion = minions.get(i);
            if (minion.getCurrentHealth() <= 0)  {
                Color.RED.println("The " + minion.getName() + " dies!");
                minions.remove(i);
                i--; // needed to not skip next minion
            }
        }
    }
}
