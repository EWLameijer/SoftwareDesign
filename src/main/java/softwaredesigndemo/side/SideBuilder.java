package softwaredesigndemo.side;

import softwaredesigndemo.cards.Card;
import softwaredesigndemo.side.characters.Hero;

public class SideBuilder {
    private final Hero hero;
    
    private final Territory territory;

    private final Deck deck;

    private Hand hand;

    private ManaBar manaBar;

    public SideBuilder() {
        hero = Hero.from(HeroType.WARRIOR);
        territory = new Territory();
        deck = Deck.createTestDeck();
        hand = Hand.createTestHand();
        manaBar = ManaBar.createTestManaBar(0);
    }

    public SideBuilder setMana(int mana) {
        manaBar = ManaBar.createTestManaBar(mana);
        return this;
    }

    public SideBuilder setHand(Card... cards) {
        hand = Hand.createTestHand(cards);
        return this;
    }

    public Side build(String name) {
        return Side.createTestSide(hero, territory, deck, manaBar, hand, name);
    }
}
