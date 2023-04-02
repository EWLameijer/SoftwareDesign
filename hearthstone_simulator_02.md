
## Stap 2: we beginnen ergens

Hoe gaan we dit aanpakken?

Het is een kaartspel, dus laten we eerst een kaart-klasse aanmaken

Wat moet er op die kaarten staan? Laten we met de 'default decks' beginnen van Warrior en Mage
https://outof.cards/hearthstone/decks/29388-warrior-basic-starter-deck

2x van elk:

Arcanite Reaper - weapon - 5 mana - 5 damage 2 durability
Cleave - spell - 2 mana - deal 2 damage to 2 random enemy minions
Charge - spell - 3 mana -give a friendly minion +2 attack and Charge
Execute - spell - 2 mana - destroy a damaged enemy minion
Fiery War Axe - weapon - 2 mana - 3 damage 2 durability
Frostwolf Grunt -minion 2/2 - 2 mana - taunt  
Frostwolf Warlord - minion 4/4 - 5 mana - battlecry: gain +1/+1 for each other friendly minion on the battlefield
Gurubashi Berserker - minion 2/7 - 5 mana - whenever this minion takes damage, gain +3 attack
Heroic Strike - spell - 2 mana - give hero +4 attack this turn
Kok'kron Elite - minion 4 attack 3 health - 4 mana - charge  
Raid Leader - minion 2 attack 2 health - 3 mana - all your other minions have +1 attack
Sen'jin Shieldmasta - minion 3 attack 5 health - 4 mana - taunt
Shield Block - spell - 3 mana - gain 5 armor, draw a card
Warsong Commander - minion 2attack, 3 health- 3 mana: when you summon a minion with 3 or less attack, give it Charge
Whirlwind - spell - 1 mana - 1 damage to ALL minions

Mage deck:
https://outof.cards/hearthstone/decks/29385-mage-basic-starter-deck
Arcane Explosion - spell - 2 mana - deal 1 damage to all enemy minions
Arcane Intellect - spell - 3 mana - draw 2 cards
Arcane Missiles - spell - 1 mana - deal 3 damage randomly split between all enemy characters
Archmage -minion 4/7 -6 mana - Spell Damage + 1
Darkscale Healer - minion 4/5 - 5 mana - Battlecry: restore 2 health to all friendly characters
Fireball - spell - 4 mana - deal 6 damage
Flamestrike - spell - 7 mana - deal 4 damage to all enemy minions
Frost nova - spell - 3 mana - freeze all enemy minions
Frostbolt - spell -2 mana - deal 3 damage to a character and Freeze it
Kobold Geomancer - minion 2/2 - 2 mana - Spell Damage  +1
Magma Rager - minion 5/1 - 3 mana
Mirror Image - spell  - 1 mana - summon 2 0/2 minions with Taunt
Polymorph - spell - 4 mana - transform a minion into a 1/1/ sheep
Voodoo Doctor - minion 2/1 - 1 mana - battlecry: restore 2 health  
Water Elemental - minion 3/6 - 4 mana - freeze any character damaged by this minion

Laten we niet alles tegelijkertijd proberen te implementeren

De eerste abstractie is dat er kaarten zijn. Dat impliceert een Card-klasse.
Een kaart heeft ALTIJD een naam en een mana cost.
Er zijn drie typen kaarten: WeaponCard, MinionCard en SpellCard - dat suggereert overerving. En ook dat ik Card abstract moet maken, omdat een Card altijd een van die drie typen moet zijn (van de 'mana-kaart' kunnen we een spellkaart maken...)

Minion cards hebben attack en health, weapon cards hebben attack en durability
In deze eerste ronde zal ik nog een description toevoegen aan een card, daar parkeren we dingen als "draw 2 cards" tot
als we (als we daar ooit komen) die tekst kunnen omzetten in programmacode.

Dus Card (met name en cost) wordt iets als (gelukkig helpt IDEA mee met het genereren van de constructor en getters!) - final omdat ik niet denk dat de naam van een kaart of de cost kan veranderen.
```
public abstract class Card {
    private final String name;
    private final int cost;

    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }
}
```
Ik zal nog een description toevoegen, die ik hopelijk later kan vervangen of gebruiken als documentatie

```
public abstract class Card {
    private final String name;
    private final int cost;
    
    private final String description;

    public Card(String name, int cost, String description) {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }
}
```

En nu de andere klassen: MinionCard is een card met Attack en Health, WeaponCard een card met Attack en Durability, SpellCard... wel, dat is meer categorie "overig". Waarschijnlijk moet het een soort "effect-methode" krijgen. Maar dat is later zorg.

```
public class MinionCard extends Card {
    private final int attack;

    private int health; // not final, as that will decrease during battle

    public MinionCard(String name, int cost, String description, int attack, int health) {
        super(name, cost, description);
        this.attack = attack;
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }
}
```

Maar ik besef nu (vaak wil je dingen final maken) dat ik onderscheid moet maken tussen een MinionCARD en een Minion; een MinionCard heeft een vaste health, een minion zal dat niet hebben.

```
public class MinionCard extends Card {
    private final int attack;

    private final int health; 

    public MinionCard(String name, int cost, String description, int attack, int health) {
        super(name, cost, description);
        this.attack = attack;
        this.health = health;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }
}
```

WeaponCard wordt basaal een soort kopie hiervan. Ik vraag me nu overigens af waarom ik hier niet gewoon records van maak. Scheelt veel typewerk... Oh ja, omdat overerving niet werkt met records :(. Afijn...

```
public class WeaponCard extends Card {
private final int attack;

    private final int durability;

    public WeaponCard(String name, int cost, String description, int attack, int durability) {
        super(name, cost, description);
        this.attack = attack;
        this.durability = durability;
    }

    public int getAttack() {
        return attack;
    }

    public int getDurability() {
        return durability;
    }
}
``` 

SpellCard is een beetje "miscellaneous"

```
public class SpellCard extends Card {
    public SpellCard(String name, int cost, String description) {
        super(name, cost, description);
    }
}
```

Nu ga de uitgewerkte decks 'copy-pasten' naar mijn main.

```
public class Main {
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

    final static Card RAID_LEADER = new MinionCard("Raid Leader ", 3, "all your other minions have +1 attack", 2, 2);
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
    
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
```

Nu heb ik dus kaarten aangemaakt, maar om te spelen heb ik wel Decks nodig. Dat wordt dus de volgende stap...
