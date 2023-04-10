package softwaredesigndemo;

import softwaredesigndemo.cards.*;
import softwaredesigndemo.side.HeroType;
import softwaredesigndemo.side.characters.Enhancement;
import softwaredesigndemo.side.characters.HearthstoneCharacter;
import softwaredesigndemo.spells.*;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public record GameDeck(HeroType heroType, List<Card> cards) {

    // https://outof.cards/hearthstone/decks/29388-warrior-basic-starter-deck
    static final Card ARCANITE_REAPER = new WeaponCard("Arcanite Reaper", 5, "", 5, 2);

    static final UntargetedSpell CLEAVE_SPELL = new UntargetedSpell(
            s -> s.opponent().getTerritory().getMinionCount() >= 2,
            s -> s.opponent().getTerritory().getRandomMinions(2).forEach(m -> m.takeDamage(2)));
    static final Card CLEAVE = new SpellCard("Cleave", 2, "deal 2 damage to 2 random enemy minions", CLEAVE_SPELL);

    static final BiPredicate<HearthstoneCharacter, Sides> NO_FURTHER_REQUIREMENTS = (t, s) -> true;
    static final TargetClassification ALLIED_MINION = new TargetClassification(TargetType.MINION, SideType.ALLY, NO_FURTHER_REQUIREMENTS);
    static final TargetedSpell CHARGE_SPELL = new TargetedSpell(
            ALLIED_MINION,
            (t, s) -> t.enhance(stats -> stats.addProperty(MinionProperty.CHARGE).changeAttack(2)));
    static final Card CHARGE = new SpellCard("Charge", 3, "give a friendly minion +2 attack and Charge", CHARGE_SPELL);

    static final TargetedSpell EXECUTE_SPELL = new TargetedSpell(
            new TargetClassification(TargetType.MINION, SideType.ENEMY, (t, s) -> t.getHealth() < t.getMaxHealth()),
            (t, s) -> t.destroy());
    static final Card EXECUTE = new SpellCard("Execute", 2, "destroy a damaged enemy minion", EXECUTE_SPELL);
    static final Card FIERY_WAR_AXE = new WeaponCard("Fiery War Axe", 2, "", 3, 2);
    static final Card FROSTWOLF_GRUNT = new MinionCard("Frostwolf Grunt", 2, "taunt", 2, 2, List.of(Enhancement.TAUNT));
    static final Card FROSTWOLF_WARLORD = new MinionCard("Frostwolf Warlord", 5, "gain +1/+1 for each other friendly minion on the battlefield", 4, 4, List.of());
    static final Card GURUBASHI_BERSERKER = new MinionCard("Gurubashi Berserker", 5, "whenever this minion takes damage, gain +3 attack", 2, 7, List.of());

    static final Predicate<Sides> NO_PRECONDITIONS_FOR_UNTARGETED = s -> true;

    static final UntargetedSpell HEROIC_STRIKE_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> s.own().getHero().enhance(stats -> stats.changeAttack(4), 1));
    static final Card HEROIC_STRIKE = new SpellCard("Heroic Strike", 2, "give hero +4 attack this turn", HEROIC_STRIKE_SPELL);
    static final Card KOK_KRON_ELITE = new MinionCard("Kok'kron Elite", 4, "", 4, 3, List.of(Enhancement.CHARGE));
    static final Card RAID_LEADER = new MinionCard("Raid Leader", 3, "all your other minions have +1 attack", 2, 2, List.of());
    static final Card SEN_JIN_SHIELDMASTA = new MinionCard("Sen'jin Shieldmasta", 4, "", 3, 5, List.of(Enhancement.TAUNT));

    static final UntargetedSpell SHIELD_BLOCK_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> {
                s.own().drawCard();
                s.own().getHero().increaseArmor(5);
            });
    static final Card SHIELD_BLOCK = new SpellCard("Shield Block ", 3, "gain 5 armor, draw a card", SHIELD_BLOCK_SPELL);
    static final Card WARSONG_COMMANDER = new MinionCard("Warsong Commander", 3, "when you summon a minion with 3 or less attack, give it Charge", 2, 3, List.of());

    static final UntargetedSpell WHIRLWIND_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> {
                s.own().getTerritory().getMinions().forEach(m -> m.takeDamage(1));
                s.opponent().getTerritory().getMinions().forEach(m -> m.takeDamage(1));
            });

    static final Card WHIRLWIND = new SpellCard("Whirlwind", 1, "1 damage to ALL minions", WHIRLWIND_SPELL);

    static final List<Card> basicWarriorCards = List.of(
            ARCANITE_REAPER, ARCANITE_REAPER, CLEAVE, CLEAVE, CHARGE, CHARGE, EXECUTE, EXECUTE, FIERY_WAR_AXE,
            FIERY_WAR_AXE, FROSTWOLF_GRUNT, FROSTWOLF_GRUNT, FROSTWOLF_WARLORD, FROSTWOLF_WARLORD,
            GURUBASHI_BERSERKER, GURUBASHI_BERSERKER, HEROIC_STRIKE, HEROIC_STRIKE, KOK_KRON_ELITE, KOK_KRON_ELITE,
            RAID_LEADER, RAID_LEADER, SEN_JIN_SHIELDMASTA, SEN_JIN_SHIELDMASTA, SHIELD_BLOCK, SHIELD_BLOCK,
            WARSONG_COMMANDER, WARSONG_COMMANDER, WHIRLWIND, WHIRLWIND);

    public static final GameDeck WARRIOR_DECK = new GameDeck(HeroType.WARRIOR, basicWarriorCards);
    //https://outof.cards/hearthstone/decks/29385-mage-basic-starter-deck

    static final UntargetedSpell ARCANE_EXPLOSION_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> s.opponent().getTerritory().getMinions().forEach(m -> m.takeDamage(1)));
    static final Card ARCANE_EXPLOSION = new SpellCard("Arcane Explosion", 2, "deal 1 damage to all enemy minions", ARCANE_EXPLOSION_SPELL);

    static final UntargetedSpell ARCANE_INTELLECT_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> {
                s.own().drawCard();
                s.own().drawCard();
            });

    static final Card ARCANE_INTELLECT = new SpellCard("Arcane Intellect", 3, "draw 2 cards", ARCANE_INTELLECT_SPELL);

    static final UntargetedSpell ARCANE_MISSILES_SPELL = new UntargetedSpell(NO_PRECONDITIONS_FOR_UNTARGETED,
            s -> {
                for (int i = 0; i < 3; i++) {
                    s.opponent().getRandomTarget().takeDamage(1);
                }
            });
    static final Card ARCANE_MISSILES = new SpellCard("Arcane Missiles", 1, "deal 3 damage randomly split between all enemy characters", ARCANE_MISSILES_SPELL);
    static final Card ARCHMAGE = new MinionCard("Archmage", 6, "Spell Damage + 1", 4, 7, List.of());
    static final Card DARKSCALE_HEALER = new MinionCard("Darkscale Healer", 5, "Battlecry: restore 2 health to all friendly characters", 4, 5, List.of());
    static final Card FIREBALL = new SpellCard("Fireball", 4, "deal 6 damage");
    static final Card FLAMESTRIKE = new SpellCard("Flamestrike", 7, "deal 4 damage to all enemy minions");
    static final Card FROST_NOVA = new SpellCard("Frost Nova", 3, "freeze all enemy minions");
    static final Card FROSTBOLT = new SpellCard("Frostbolt", 2, "deal 3 damage to a character and Freeze it");
    static final Card KOBOLD_GEOMANCER = new MinionCard("Kobold Geomancer", 2, "Spell Damage  +1", 2, 2, List.of());
    static final Card MAGMA_RAGER = new MinionCard("Magma Rager", 3, "", 5, 1, List.of());
    static final Card MIRROR_IMAGE = new SpellCard("Mirror Image", 1, "summon 2 0/2 minions with Taunt");
    static final Card POLYMORPH = new SpellCard("Polymorph", 4, "transform a minion into a 1/1 sheep");
    static final Card VOODOO_DOCTOR = new MinionCard("Voodoo Doctor", 1, "battlecry: restore 2 health", 2, 1, List.of());
    static final Card WATER_ELEMENTAL = new MinionCard("Water Elemental", 4, "freeze any character damaged by this minion", 3, 6, List.of());

    static final List<Card> basicMageCards = List.of(
            ARCANE_EXPLOSION, ARCANE_EXPLOSION, ARCANE_INTELLECT, ARCANE_INTELLECT, ARCANE_MISSILES, ARCANE_MISSILES,
            ARCHMAGE, ARCHMAGE, DARKSCALE_HEALER, DARKSCALE_HEALER, FIREBALL, FIREBALL, FLAMESTRIKE, FLAMESTRIKE,
            FROST_NOVA, FROST_NOVA, FROSTBOLT, FROSTBOLT, KOBOLD_GEOMANCER, KOBOLD_GEOMANCER,
            MAGMA_RAGER, MAGMA_RAGER, MIRROR_IMAGE, MIRROR_IMAGE, POLYMORPH, POLYMORPH, VOODOO_DOCTOR, VOODOO_DOCTOR,
            WATER_ELEMENTAL, WATER_ELEMENTAL);
    public static final GameDeck MAGE_DECK = new GameDeck(HeroType.MAGE, basicMageCards);
}
