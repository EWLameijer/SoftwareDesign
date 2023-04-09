package softwaredesigndemo.side.characters;

import softwaredesigndemo.cards.MinionProperty;

import java.util.function.UnaryOperator;

public enum Enhancement {
    // TODO: likely should NOT be an enum, and likely should have an get an argument to register itself to another minion or a timer...
    CHARGE(s -> s.addProperty(MinionProperty.CHARGE)), TAUNT(s -> s.addProperty(MinionProperty.TAUNT));

    private final UnaryOperator<Stats> transform;

    Enhancement(UnaryOperator<Stats> transform) {
        this.transform = transform;
    }

    public Stats apply(Stats stats) {
        return transform.apply(stats);
    }
}
