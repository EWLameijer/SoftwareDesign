package softwaredesigndemo.spells;

import softwaredesigndemo.side.Side;
import softwaredesigndemo.side.Territory;
import softwaredesigndemo.side.characters.HearthstoneCharacter;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class TargetedSpell extends Spell {
    private static final char FRIENDLY_HERO_SYMBOL = 'H';

    private final BiFunction<Side, Side, Optional<HearthstoneCharacter>> getTarget;
    
    public TargetedSpell(BiFunction<Side, Side, Optional<HearthstoneCharacter>> getTarget, BiConsumer<Side, Side> cast) {
        super(cast);
        this.getTarget = getTarget;
    }

    public static Optional<HearthstoneCharacter> getTarget(TargetClassification classification, Side ownSide, Side opponentsSide) {
        // if it is a minion-targeting spell, check if there are any suitable minions in the first place!
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
            if (targetCharacter.isPresent()) return targetCharacter;
        } while (true);
    }

    private static Optional<HearthstoneCharacter> getValidTarget(char targetSymbol, TargetClassification classification, Side ownSide, Side opponentsSide) {
        if (Character.isLetter(targetSymbol)) {
            // is friendly
            char standardizedTargetSymbol = Character.toUpperCase(targetSymbol);
            if (standardizedTargetSymbol == FRIENDLY_HERO_SYMBOL) {
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
