## Stap 4: de Mulligan werkt, laat het spel beginnen!

Het spel gaat beginnen! Het game moet nu een Board krijgen waar beide partijen op kunnen strijden. Maar dat is niet de enige verandering.

In het spel worden de spelers namelijk gerepresenteerd door helden; de spelers hebben gewoon een naam, maar de helden (Heroes) hebben hitpoints en kunnen doodgaan.

Dat betekent een paar dingen

1) We moeten een Board maken (die bestaat uit simpelweg twee ArrayLists begrensd op 7 posities, laat ik daar maar een Territory-klasse van maken)
2) We moeten zorgen dat de spelers een Hero meeleveren (in feite bepaalt de hero welke kaarten er in een deck kunnen, maar dat implementeer ik liever nog even niet omdat dat de simulatie niet in de weg staat)
3) Die Hero moet 30 hitpoints krijgen, en een mana-capaciteit (die vooralsnog op 0 staat)
4) Elke beurt krijgt de hero 1 mana-kristal erbij en kan kaarten spelen zolang hij/zij daar mana voor heeft.
5) Oh ja... waarschijnlijk moeten we ook onderscheid maken tussen het Deck dat een player aanlevert en het Deck van de Hero, omdat het deck van de player gelijk blijft tussen games, maar het Deck van de Hero langzaam wordt leeggehaald...

Aan de slag!