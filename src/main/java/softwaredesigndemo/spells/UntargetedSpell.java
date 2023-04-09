package softwaredesigndemo.spells;

import softwaredesigndemo.side.Side;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class UntargetedSpell extends Spell {
    final public BiPredicate<Side, Side> canCast;

    public UntargetedSpell(BiPredicate<Side, Side> canCast, BiConsumer<Side, Side> cast) {
        super(cast);
        this.canCast = canCast;
    }
}
