package softwaredesigndemo.side.characters;

import java.util.function.UnaryOperator;

public class TemporaryEnhancement extends Enhancement {
    private int turns;

    TemporaryEnhancement(UnaryOperator<Stats> transform, int turns) {
        super(transform);
        this.turns = turns;
    }

    @Override
    public boolean isActive() {
        return turns > 0;
    }

    public void countDown() {
        turns--;
    }
}
