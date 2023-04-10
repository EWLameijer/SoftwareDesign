package softwaredesigndemo.cards;

import softwaredesigndemo.Sides;
import softwaredesigndemo.spells.Spell;

public class SpellCard extends Card {
    private final Spell spell;

    public SpellCard(String name, int cost, String description, Spell spell) {
        super(name, cost, description);
        this.spell = spell;
    }

    public SpellCard(String name, int cost, String description) {
        super(name, cost, description);
        this.spell = null;
    }

    @Override
    public boolean canPlay(Sides sides) {
        if (!super.canPlay(sides)) return false;
        return spell.canCast(sides);
    }

    @Override
    public void play(Sides sides) {
        spell.cast(sides);
    }
}
