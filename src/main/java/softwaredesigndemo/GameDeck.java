package softwaredesigndemo;

import softwaredesigndemo.cards.Card;
import softwaredesigndemo.side.HeroType;

import java.util.List;

public record GameDeck(HeroType heroType, List<Card> cards) {
}
