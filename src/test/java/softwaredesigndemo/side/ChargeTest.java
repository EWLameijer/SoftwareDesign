package softwaredesigndemo.side;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import softwaredesigndemo.side.characters.Hero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChargeTest {
    @DisplayName("Normal minion cannot attack its first turn")
    @Test
    void Regular_minion_cannot_attack_its_first_turn() {
        var attackerHero = Hero.from(HeroType.MAGE);
        var attackerTerritory = new Territory();
        var attackerDeck = Deck.createTestDeck();
        var attackerManaBar = ManaBar.createTestManaBar(1);
        var attackerHand = Hand.createTestHand(GameDeck.VOODOO_DOCTOR);

        var attackerSide = Side.createTestSide(attackerHero, attackerTerritory, attackerDeck, attackerManaBar, attackerHand, "attacker");

        var defenderHero = Hero.from(HeroType.WARRIOR);
        var defenderTerritory = new Territory();
        var defenderDeck = Deck.createTestDeck();
        var defenderManaBar = ManaBar.createTestManaBar(2);
        var defenderHand = Hand.createTestHand();

        var defenderSide = Side.createTestSide(defenderHero, defenderTerritory, defenderDeck, defenderManaBar, defenderHand, "defender");
        attackerSide.setOpponentsSide(defenderSide);

        attackerSide.testExecute("0");
        var feedback2 = attackerSide.testExecute("a*");
        assertTrue(feedback2.contains("cannot currently attack!"));
        assertEquals(30, defenderHero.getHealth());
    }

    @DisplayName("Charge minion can attack its first turn")
    @Test
    void Charge_minion_can_attack_its_first_turn() {
        var attackerHero = Hero.from(HeroType.WARRIOR);
        var attackerTerritory = new Territory();
        var attackerDeck = Deck.createTestDeck();
        var attackerManaBar = ManaBar.createTestManaBar(4);
        var attackerHand = Hand.createTestHand(GameDeck.KOK_KRON_ELITE);

        var attackerSide = Side.createTestSide(attackerHero, attackerTerritory, attackerDeck, attackerManaBar, attackerHand, "attacker");

        var defenderHero = Hero.from(HeroType.MAGE);
        var defenderTerritory = new Territory();
        var defenderDeck = Deck.createTestDeck();
        var defenderManaBar = ManaBar.createTestManaBar(2);
        var defenderHand = Hand.createTestHand();

        var defenderSide = Side.createTestSide(defenderHero, defenderTerritory, defenderDeck, defenderManaBar, defenderHand, "defender");
        attackerSide.setOpponentsSide(defenderSide);

        attackerSide.testExecute("0");
        attackerSide.testExecute("a*");
        assertEquals(26, defenderHero.getHealth());
    }
}
