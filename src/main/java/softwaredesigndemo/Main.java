package softwaredesigndemo;


import softwaredesigndemo.cards.Card;
import softwaredesigndemo.cards.MinionCard;
import softwaredesigndemo.cards.SpellCard;
import softwaredesigndemo.cards.WeaponCard;
import softwaredesigndemo.side.HeroType;

import java.util.List;
import java.util.Scanner;

public class Main {
    // https://outof.cards/hearthstone/decks/29388-warrior-basic-starter-deck
    final static Card ARCANITE_REAPER = new WeaponCard("Arcanite Reaper", 5, "", 5, 2);
    final static Card CLEAVE = new SpellCard("Cleave", 2, "deal 2 damage to 2 random enemy minions");
    final static Card CHARGE = new SpellCard("Charge", 3, "give a friendly minion +2 attack and Charge");
    final static Card EXECUTE = new SpellCard("Execute", 2, "destroy a damaged enemy minion");
    final static Card FIERY_WAR_AXE = new WeaponCard("Fiery War Axe", 2, "", 3, 2);

    final static Card FROSTWOLF_GRUNT = new MinionCard("Frostwolf Grunt", 2, "taunt", 2, 2);
    final static Card FROSTWOLF_WARLORD = new MinionCard("Frostwolf Warlord", 5, "gain +1/+1 for each other friendly minion on the battlefield", 4, 4);
    final static Card GURUBASHI_BERSERKER = new MinionCard("Gurubashi Berserker", 5, "whenever this minion takes damage, gain +3 attack", 2, 7);
    final static Card HEROIC_STRIKE = new SpellCard("Heroic Strike", 2, "give hero +4 attack this turn");
    final static Card KOK_KRON_ELITE = new MinionCard("Kok'kron Elite", 4, "charge", 4, 3);

    final static Card RAID_LEADER = new MinionCard("Raid Leader", 3, "all your other minions have +1 attack", 2, 2);
    final static Card SEN_JIN_SHIELDMASTA = new MinionCard("Sen'jin Shieldmasta", 4, "taunt", 3, 5);
    final static Card SHIELD_BLOCK = new SpellCard("Shield Block ", 3, "gain 5 armor, draw a card");
    final static Card WARSONG_COMMANDER = new MinionCard("Warsong Commander", 3, "when you summon a minion with 3 or less attack, give it Charge", 2, 3);
    final static Card WHIRLWIND = new SpellCard("Whirlwind", 1, "1 damage to ALL minions");


    //https://outof.cards/hearthstone/decks/29385-mage-basic-starter-deck
    final static Card ARCANE_EXPLOSION = new SpellCard("Arcane Explosion", 2, "deal 1 damage to all enemy minions");
    final static Card ARCANE_INTELLECT = new SpellCard("Arcane Intellect", 3, "draw 2 cards");
    final static Card ARCANE_MISSILES = new SpellCard("Arcane Missiles", 1, "deal 3 damage randomly split between all enemy characters");
    final static Card ARCHMAGE = new MinionCard("Archmage", 6, "Spell Damage + 1", 4, 7);
    final static Card DARKSCALE_HEALER = new MinionCard("Darkscale Healer", 5, "Battlecry: restore 2 health to all friendly characters", 4, 5);

    final static Card FIREBALL = new SpellCard("Fireball", 4, "deal 6 damage");
    final static Card FLAMESTRIKE = new SpellCard("Flamestrike", 7, "deal 4 damage to all enemy minions");
    final static Card FROST_NOVA = new SpellCard("Frost Nova", 3, "freeze all enemy minions");
    final static Card FROSTBOLT = new SpellCard("Frostbolt", 2, "deal 3 damage to a character and Freeze it");
    final static Card KOBOLD_GEOMANCER = new MinionCard("Kobold Geomancer", 2, "Spell Damage  +1", 2, 2);

    final static Card MAGMA_RAGER = new MinionCard("Magma Rager", 3, "", 5, 1);
    final static Card MIRROR_IMAGE = new SpellCard("Mirror Image", 1, "summon 2 0/2 minions with Taunt");
    final static Card POLYMORPH = new SpellCard("Polymorph", 4, "transform a minion into a 1/1 sheep");
    final static Card VOODOO_DOCTOR = new MinionCard("Voodoo Doctor", 1, "battlecry: restore 2 health", 2, 1);
    final static Card WATER_ELEMENTAL = new MinionCard("Water Elemental", 4, "freeze any character damaged by this minion", 3, 6);

    static final List<Card> basicWarriorCards = List.of(
            ARCANITE_REAPER, ARCANITE_REAPER, CLEAVE, CLEAVE, CHARGE, CHARGE, EXECUTE, EXECUTE, FIERY_WAR_AXE,
            FIERY_WAR_AXE, FROSTWOLF_GRUNT, FROSTWOLF_GRUNT, FROSTWOLF_WARLORD, FROSTWOLF_WARLORD,
            GURUBASHI_BERSERKER, GURUBASHI_BERSERKER, HEROIC_STRIKE, HEROIC_STRIKE, KOK_KRON_ELITE, KOK_KRON_ELITE,
            RAID_LEADER, RAID_LEADER, SEN_JIN_SHIELDMASTA, SEN_JIN_SHIELDMASTA, SHIELD_BLOCK, SHIELD_BLOCK,
            WARSONG_COMMANDER, WARSONG_COMMANDER, WHIRLWIND, WHIRLWIND);
    static final GameDeck warriorDeck = new GameDeck(HeroType.WARRIOR, basicWarriorCards);
    static final List<Card> basicMageCards = List.of(
            ARCANE_EXPLOSION, ARCANE_EXPLOSION, ARCANE_INTELLECT, ARCANE_INTELLECT, ARCANE_MISSILES, ARCANE_MISSILES,
            ARCHMAGE, ARCHMAGE, DARKSCALE_HEALER, DARKSCALE_HEALER, FIREBALL, FIREBALL, FLAMESTRIKE, FLAMESTRIKE,
            FROST_NOVA, FROST_NOVA, FROSTBOLT, FROSTBOLT, KOBOLD_GEOMANCER, KOBOLD_GEOMANCER,
            MAGMA_RAGER, MAGMA_RAGER, MIRROR_IMAGE, MIRROR_IMAGE, POLYMORPH, POLYMORPH, VOODOO_DOCTOR, VOODOO_DOCTOR,
            WATER_ELEMENTAL, WATER_ELEMENTAL);
    static final GameDeck mageDeck = new GameDeck(HeroType.MAGE, basicMageCards);

    final static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        var player1Name = askForName("Player 1");
        var player1Deck = askForDeck();
        var player2Name = askForName("Player 2");
        var player2Deck = askForDeck();
        var player1 = new Player(player1Name, player1Deck);
        var player2 = new Player(player2Name, player2Deck);
        var Game = new Game(player1, player2);
    }

    private static GameDeck askForDeck() {
        System.out.print("Warrior (W) or Mage(anything else)? ");
        var isWarrior = in.nextLine().toUpperCase().startsWith("W");
        return isWarrior ? warriorDeck : mageDeck;
    }

    private static String askForName(String provisionalName) {
        System.out.print(provisionalName + ", enter your name: ");
        return in.nextLine().trim();
    }
}