package softwaredesigndemo.side.characters;

import softwaredesigndemo.cards.MinionProperty;

import java.util.function.UnaryOperator;

public class Enhancement {
    private final UnaryOperator<Stats> transform;

    public static final Enhancement TAUNT = new Enhancement(s -> s.addProperty(MinionProperty.TAUNT));

    public static final Enhancement CHARGE = new Enhancement(s -> s.addProperty(MinionProperty.CHARGE));

    Enhancement(UnaryOperator<Stats> transform) {
        this.transform = transform;
    }

    public Stats apply(Stats stats) {
        return transform.apply(stats);
    }

    public boolean isActive() {
        return true;
    }
}
