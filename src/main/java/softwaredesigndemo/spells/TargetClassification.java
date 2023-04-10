package softwaredesigndemo.spells;

import softwaredesigndemo.Sides;
import softwaredesigndemo.side.characters.HearthstoneCharacter;

import java.util.function.BiPredicate;

public record TargetClassification(TargetType targetType, SideType sideType,
                                   BiPredicate<HearthstoneCharacter, Sides> isValid) {
}
