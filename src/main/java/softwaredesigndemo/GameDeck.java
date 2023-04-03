package softwaredesigndemo;

import java.util.List;

public class GameDeck {
    private final HeroType heroType;
    private final List<Card> cards;

    public GameDeck(HeroType heroType, List<Card> cards) {
        this.heroType = heroType;
        this.cards = cards;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public List<Card> getCards() {
        return cards;
    }
}
