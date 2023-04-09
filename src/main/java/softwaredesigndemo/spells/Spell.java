package softwaredesigndemo.spells;

import softwaredesigndemo.side.Side;

import java.util.function.BiConsumer;

public abstract class Spell {
    final public BiConsumer<Side, Side> cast;

    public Spell(BiConsumer<Side, Side> cast) {
        this.cast = cast;
    }
}
