package softwaredesigndemo.cards;

import softwaredesigndemo.side.Side;
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
    public void play(Side ownSide, Side opponentsSide) {
    }
}
