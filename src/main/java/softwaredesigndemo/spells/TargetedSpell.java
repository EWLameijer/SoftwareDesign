package softwaredesigndemo.spells;

import softwaredesigndemo.Sides;
import softwaredesigndemo.side.Side;
import softwaredesigndemo.side.Territory;
import softwaredesigndemo.side.characters.HearthstoneCharacter;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class TargetedSpell extends Spell {

    private final TargetClassification targetClassification;

    BiConsumer<HearthstoneCharacter, Sides> spellEffect;

    private HearthstoneCharacter target = null;

    public TargetedSpell(TargetClassification targetClassification, BiConsumer<HearthstoneCharacter, Sides> spellEffect) {
        this.spellEffect = spellEffect;
        this.targetClassification = targetClassification;
    }

    @Override
    public boolean canCast(Sides sides) {
        Optional<HearthstoneCharacter> optionalTarget = getTarget(targetClassification, sides);
        if (optionalTarget.isEmpty()) return false;
        target = optionalTarget.get();
        return true;
    }

    @Override
    public void cast(Sides sides) {
        if (target == null) throw new IllegalStateException("TargetedSpell.cast() exception: no target!");
        spellEffect.accept(target, sides);
    }

    public static Optional<HearthstoneCharacter> getTarget(TargetClassification classification, Sides sides) {
        // if it is a minion-targeting spell, check if there are any suitable minions in the first place!
        var ownSide = sides.own();
        var opponentsSide = sides.opponent();

        if (classification.targetType() == TargetType.MINION) {
            boolean canBeCast = switch (classification.sideType()) {
                case ALLY -> hasMinions(ownSide);
                case ENEMY -> hasMinions(opponentsSide);
                case ALL -> hasMinions(ownSide, opponentsSide);
            };
            if (!canBeCast) return Optional.empty();
        }
        // during the game, both heroes are alive, so spell-casting in the other cases is always possible
        Scanner in = new Scanner(System.in);
        do {
            System.out.print("Please select the target to cast on (Q to abort): ");
            String targetSymbol = in.nextLine();
            if (targetSymbol.equalsIgnoreCase("Q")) return Optional.empty();
            if (targetSymbol.length() < 1) continue;
            Optional<HearthstoneCharacter> targetCharacter = getValidTarget(targetSymbol.charAt(0), classification, ownSide, opponentsSide);
            if (targetCharacter.isPresent() && classification.isValid().test(targetCharacter.get(), sides))
                return targetCharacter;
        } while (true);
    }

    private static Optional<HearthstoneCharacter> getValidTarget(char targetSymbol, TargetClassification classification, Side ownSide, Side opponentsSide) {
        if (Character.isLetter(targetSymbol)) {
            // is friendly
            char standardizedTargetSymbol = Character.toUpperCase(targetSymbol);
            if (standardizedTargetSymbol == Side.FRIENDLY_HERO_SYMBOL) {
                return classification.sideType().matches(SideType.ALLY) && classification.targetType().matches(TargetType.HERO) ?
                        Optional.of(ownSide.getHero()) : Optional.empty();
            } else return ownSide.getTerritory().getMinionCount() > standardizedTargetSymbol - 'A' &&
                    classification.sideType().matches(SideType.ALLY) && classification.targetType().matches(TargetType.MINION) ?
                    Optional.of(ownSide.getTerritory().getMinion(standardizedTargetSymbol)) : Optional.empty();
        } else {
            if (targetSymbol == Side.ENEMY_HERO_SYMBOL)
                return classification.sideType().matches(SideType.ENEMY) && classification.targetType().matches(TargetType.HERO) ?
                        Optional.of(opponentsSide.getHero()) : Optional.empty();
            else {
                int targetIndex = Territory.indexToSymbol.indexOf(targetSymbol);
                if (targetIndex < 0) return Optional.empty();
                return opponentsSide.getTerritory().getMinionCount() > targetIndex &&
                        classification.sideType().matches(SideType.ENEMY) && classification.targetType().matches(TargetType.MINION) ?
                        Optional.of(opponentsSide.getTerritory().getMinion(targetSymbol)) : Optional.empty();
            }
        }
    }

    private static boolean hasMinions(Side... sides) {
        return Arrays.stream(sides).anyMatch(s -> s.getTerritory().getMinionCount() >= 1);
    }
}
