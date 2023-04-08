## Stap 10: Weer refactoren

Ik ga eens kijken of ik nieuwe inspiratie heb om te refactoren...

Ik voeg wat witregels in, en ik zie de oude TODO in Hand:

`// TODO: better add logic whether you can play a card to the Card class`

Ik verander dus de play in Hand:

```
// in Hand.java

public void play(int index, Side ownSide, Side opponentsSide) {
    if (index < 0 || index > cards.size())
        throw new IllegalArgumentException("Hand.play() error: " + index + " is not a valid index!");
    var chosenCard = cards.get(index);
    if (chosenCard.canPlay(ownSide, opponentsSide)) {
        ownSide.getManaBar().consume(chosenCard.getCost());
        System.out.println("Playing " + chosenCard.getName());
        chosenCard.play(ownSide, opponentsSide);
        cards.remove(index);
    } else {
        chosenCard.communicateInvalidPlay(ownSide, opponentsSide);
    }
}
```

Dus Card heeft nu een `canPlay` en een `communicateInvalidPlay` nodig. Dat kan geregeld worden!

``` 
// in Card.java

public boolean canPlay(Side ownSide, Side opponentsSide) {
    return cost <= ownSide.getManaBar().getAvailableMana();
}

public void communicateInvalidPlay(Side ownSide, Side opponentsSide) {
    if (cost > ownSide.getManaBar().getAvailableMana()) Color.RED.println("You don't have enough mana to play this card!");
}
```

Een minioncard moet naast letten op mana cost (wat gedelegeerd kan worden aan Card) er ook op letten dat het board niet
vol is!

``` 
// in MinionCard.java

@Override
public boolean canPlay(Side ownSide, Side opponentsSide) {
    return super.canPlay(ownSide, opponentsSide) && ownSide.getTerritory().canAddMinion();
}

@Override
public void communicateInvalidPlay(Side ownSide, Side opponentsSide) {
    if (!super.canPlay(ownSide, opponentsSide)) super.communicateInvalidPlay(ownSide,opponentsSide);
    else Color.RED.println("You can't play any more minions! The board is full!");
}
```

Ik heb uiteraard wel een getManaBar() nodig in Side om dingen voor elkaar te krijgen en niet al te veel argumenten te
moeten doorgeven...

```
// in Side.java

public ManaBar getManaBar() {
    return manaBar;
}
```

Ik vervang herhaalde X + 'A' code (duplicaties zijn nooit echt goed...)

``` 
// in Territory.java

public void communicateInvalidAttacker(char minionSymbol) {
    int minionIndex = getMinionIndex(minionSymbol);
    char standardizedMinionSymbol = (char) (minionIndex + 'A');
    if (minionIndex >= minions.size()) System.out.printf("There is no minion '%c'!\n", standardizedMinionSymbol);
    if (!minions.get(minionIndex).canAttack())
        System.out.printf("Minion %c cannot currently attack!\n", standardizedMinionSymbol);
}
```

Op IDEA's aandringen zet ik in Game random in de constructor. Maar voor de rest zie ik niets bijzonders. Hoeft ook niet,
net waren er twee rondes, waarschijnlijk heb ik na 4 rondes wel weer meer te refactoren. (ik zet overigens ook de
autoformat on save (weer)? aan).