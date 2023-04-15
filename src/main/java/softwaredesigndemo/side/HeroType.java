package softwaredesigndemo.side;


import softwaredesigndemo.spells.Spell;

import static softwaredesigndemo.side.GameDeck.MAGE_HERO_POWER;
import static softwaredesigndemo.side.GameDeck.WARRIOR_HERO_POWER;

public enum HeroType {
    MAGE(MAGE_HERO_POWER), WARRIOR(WARRIOR_HERO_POWER);

    final private Spell spell;

    HeroType(Spell spell) {
        this.spell = spell;
    }

    public Spell getSpell() {
        return spell;
    }
}
