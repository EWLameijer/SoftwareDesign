# Hearthstone-Simulator

Als oefenproject met klassen stel ik een Hearthstone-Simulator voor.

Hiervoor kies ik voor een Hearthstone-simulator. Voor degenen die het nog niet kennen: Hearthstone is een electronisch kaartspel van Blizzard/Activision/Microsoft, een soort versimpelde Magic the Gathering. Je hebt twee spelers die elk een deck hebben met 30 kaarten (maximaal 2 van elk type, slechts 1 van 'legendarische' kaarten).

Bij het begin van het spel wordt een munt opgegooid over wie mag beginnen, de eerste speler krijgt 3 willekeurige kaarten uit zijn collectie, de tweede speler 4 willekeurige kaarten en een 'manakaart') (spelers mogen hun eerste kaartenset - behalve de manakaart- omruilen voor willekeurige andere kaarten).

Beide spelers beginnen met 0 mana-kristallen en 30 hitpoints, telkens als een speler een beurt krijgt komt er een mana-kristal bij, tot maximaal 10. Er zijn overigens kaarten die dat beïnvloeden, maar om te beginnen is dat wel voldoende detail. Ook krijgt de speler een nieuwe kaart; als de hand van de speler vol is (10 kaarten) 'verbrandt' de getrokken kaart ('overdraw')

Een speler kan op zijn beurt meerdere dingen doen:
1. een kaart spelen, zolang de speler meer volle manakristallen heeft dan de kaart kost. Een kaart is oftewel:
   - een minion: wordt geplaatst op een positie aan de kant van het bord, er zijn maximaal 7 plekken aan elke kant
   - een spell: heeft soms (maar niet altijd) een 'target' (een minion, een minion of de tegenstander, alle minions, alle vijandelijke minions, alle eigen minions)
   - een weapon: zorgt dat de speler een wapen krijgt, meestal met een attack en een durability (hoevaak de speler ermee kan aanvallen)
2. een 'hero power' gebruiken (voor 2 mana)
3. een aanval doen met één van de kaarten/wezens/minions op het bord. Elke minion heeft een attack en een health, door een aanval wordt die health verminderd met de attack van de tegenstander. Als er minions met taunt op het bord zijn, kunnen alleen zij worden aangevallen. Een minion kan  ook de tegenspeler aanvallen als er geen vijandelijke minion met 'taunt' op het bord is.
4. als de speler een wapen heeft, zelf aanvallen. Als hij een minion aanvalt, neemt hij evenveel schade als de attack van die minion.

Het spel eindigt als een speler al zijn 30 hitpoints kwijtraakt- meestal oftewel door aanvallen van vijandelijke minions, of door 'exhaustion': als alle kaarten getrokken zijn krijgt de speler bij de eerste 'lege' trekking 1 punt schade, bij de tweede lege trekking 2 punten schade, enzovoorts.
