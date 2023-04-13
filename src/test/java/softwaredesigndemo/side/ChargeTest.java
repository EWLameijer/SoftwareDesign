package softwaredesigndemo.side;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChargeTest {
    @DisplayName("Normal minion cannot attack its first turn")
    @Test
    void Regular_minion_cannot_attack_its_first_turn() {
        var attackerSide = new SideBuilder().setMana(1).setHand(GameDeck.VOODOO_DOCTOR).build("attacker");
        Side defenderSide = defaultDefenderFor(attackerSide);

        attackerSide.testExecute("0");
        var feedback2 = attackerSide.testExecute("a*");

        assertTrue(feedback2.contains("cannot currently attack!"));
        assertEquals(30, defenderSide.getHero().getHealth());
    }

    @DisplayName("Charge minion can attack its first turn")
    @Test
    void Charge_minion_can_attack_its_first_turn() {
        var attackerSide = new SideBuilder().setMana(4).setHand(GameDeck.KOK_KRON_ELITE).build("attacker");
        Side defenderSide = defaultDefenderFor(attackerSide);

        attackerSide.testExecute("0");
        attackerSide.testExecute("a*");

        assertEquals(26, defenderSide.getHero().getHealth());
    }

    private static Side defaultDefenderFor(Side attackerSide) {
        var defenderSide = new SideBuilder().build("defender");
        attackerSide.setOpponentsSide(defenderSide);
        return defenderSide;
    }
}
