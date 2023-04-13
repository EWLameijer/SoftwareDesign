package softwaredesigndemo.side;

import softwaredesigndemo.side.characters.HearthstoneCharacter;
import softwaredesigndemo.side.characters.Minion;
import softwaredesigndemo.utils.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

public class Territory {
    public static final List<Character> indexToSymbol = List.of('!', '@', '#', '$', '%', '^', '&');
    private final ArrayList<Minion> minions = new ArrayList<>();

    private static int getFriendlyMinionIndex(char minionSymbol) {
        char normalizedSymbol = Character.toUpperCase(minionSymbol);
        return normalizedSymbol - 'A';
    }

    public int getMinionCount() {
        return minions.size();
    }

    public void startTurn() {
        minions.forEach(Minion::startTurn);
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
        return Color.attackStatusColor(minion).apply(minionDisplayString(minion, (char) (minionIndex + 'A')));
    }

    public void showAsEnemy() {
        System.out.printf("[%s]%n", String.join(" | ", minions.stream().map(this::colorAsEnemy).toList()));
    }

    public UnaryOperator<String> colorEnemy(HearthstoneCharacter character) {
        if (character.isFrozen()) return Color.CYAN::color;
        return isAttackable(character) ? Color.RED::color : Color.PURPLE::color;
    }

    private String colorAsEnemy(Minion minion) {
        int minionIndex = minions.indexOf(minion);
        return colorEnemy(minion).apply(minionDisplayString(minion, indexToSymbol.get(minionIndex)));
    }

    private String minionDisplayString(Minion minion, char minionSymbol) {
        return "%s %d/%d (%c)".formatted(minion.getName(), minion.getAttack(), minion.getHealth(), minionSymbol);
    }

    private boolean isAttackable(HearthstoneCharacter character) {
        return minions.stream().noneMatch(Minion::hasTaunt) || character.hasTaunt();
    }

    public boolean noTauntMinionsPresent() {
        return minions.stream().noneMatch(Minion::hasTaunt);
    }

    public boolean isValidAttacker(char minionSymbol) {
        int minionIndex = getFriendlyMinionIndex(minionSymbol);
        if (minionIndex >= minions.size()) return false;
        return minions.get(minionIndex).canAttack();
    }

    public String communicateInvalidAttacker(char minionSymbol) {
        int minionIndex = getFriendlyMinionIndex(minionSymbol);
        char standardizedMinionSymbol = (char) (minionIndex + 'A');
        return minionIndex >= minions.size() ?
                "There is no minion '%c'!\n".formatted(standardizedMinionSymbol) :
                Color.RED.color("Minion %c cannot currently attack!\n".formatted(standardizedMinionSymbol));
    }

    public boolean isValidAttackee(char minionSymbol) {
        int minionIndex = indexToSymbol.indexOf(minionSymbol);
        if (minionIndex < 0 || minionIndex >= minions.size()) return false; // no such minion
        return isAttackable(minions.get(minionIndex));
    }

    public String communicateInvalidAttackee(char attackeeSymbol) {
        int minionIndex = indexToSymbol.indexOf(attackeeSymbol);
        String message = (attackeeSymbol != Side.ENEMY_HERO_SYMBOL && minionIndex < 0) || minionIndex >= minions.size() ?
                "There is no minion '%c'!\n".formatted(attackeeSymbol) : "A minion with taunt is in the way!\n";
        return Color.RED.color(message);
    }

    public Minion getMinion(char minionSymbol) {
        if (Character.isLetter(minionSymbol)) return minions.get(Character.toUpperCase(minionSymbol) - 'A');
        else return minions.get(indexToSymbol.indexOf(minionSymbol));
    }

    public void disposeOfDeceased() {
        for (int i = 0; i < minions.size(); i++) {
            var minion = minions.get(i);
            if (minion.getHealth() <= 0) {
                Color.RED.println("The " + minion.getName() + " dies!");
                minions.remove(i);
                i--; // needed to not skip next minion
            }
        }
    }

    public List<Minion> getRandomMinions(int numberOfTargets) {
        if (minions.size() < numberOfTargets)
            throw new IllegalArgumentException("Territory.getRandomMinions() exception: not enough minions to target!");
        var indicesToChooseFrom = new ArrayList<>(IntStream.range(0, minions.size()).boxed().toList());
        Random random = new Random();
        List<Minion> output = new ArrayList<>();
        for (int targetNumber = 0; targetNumber < numberOfTargets; targetNumber++) {
            int targetIndex = random.nextInt(minions.size() - targetNumber);
            output.add(minions.get(indicesToChooseFrom.get(targetIndex)));
            indicesToChooseFrom.remove(targetIndex);
        }
        return output;
    }

    public List<Minion> getMinions() {
        return minions;
    }
}
