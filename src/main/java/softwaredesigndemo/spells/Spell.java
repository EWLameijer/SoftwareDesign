package softwaredesigndemo.spells;

import softwaredesigndemo.Sides;

public abstract class Spell {
    abstract public boolean canCast(Sides sides);

    public abstract void cast(Sides sides);
}
