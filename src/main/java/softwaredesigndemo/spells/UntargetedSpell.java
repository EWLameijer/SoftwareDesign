package softwaredesigndemo.spells;

import softwaredesigndemo.Sides;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class UntargetedSpell extends Spell {
    final private Predicate<Sides> isCastingPossible;

    final private Consumer<Sides> spellEffect;

    public UntargetedSpell(Predicate<Sides> isCastingPossible, Consumer<Sides> spellEffect) {
        this.isCastingPossible = isCastingPossible;
        this.spellEffect = spellEffect;
    }

    @Override
    public boolean canCast(Sides sides) {
        return isCastingPossible.test(sides);
    }

    @Override
    public void cast(Sides sides) {
        if (!isCastingPossible.test(sides))
            throw new IllegalStateException("UntargetedSpell.cast() exception: cannot cast this spell!");
        spellEffect.accept(sides);
    }
}
