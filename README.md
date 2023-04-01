# SoftwareDesign
Het ontwerpen van programma's met onder andere het Pseudocode Programming Process

Als programmeur moet je vaak een programma ontwerpen of aanpassen om een probleem op te lossen. Meestal betekent dat twee uitdagingen. De tweede uitdaging is dat je de commando's van een programmeertaal moet kennen, en de tools die je nodig hebt om een project op te zetten (een IDE, servers, version control, etcetera.) Het grootste deel van opleidingen tot programmeur wordt daaraan besteed.

Maar de eerste stap is het bedenken van hoe je de oplossing zou aanpakken. Als het ontwerpen van software soms kan aanvoelen als het schrijven van een brief in het Chinees, waarbij de programmeertaal Chinees is, kan de echt moeilijke stap zijn het schrijven van de Nederlandse versie van die brief. Hoe pak je dat aan?

Hoe langer je software schrijft, en hoe meer verschillende problemen je hebt aangepakt, des te makkelijker het is kleine problemen op te lossen zonder diep te hoeven nadenken, net zoals veel Nederlanders Engels kunnen spreken zonder eerst aan de Nederlandse woorden te hoeven denken. Maar zelf merk ik dat ik bij voor mij nog nieuwe of ingewikkelde problemen ook moet nadenken, en een oplossing beschrijven.

Een formele naam hiervoor is het Pseudocode Programming Process (PPP), zoals beschreven door Steve McConnell in "Code Complete"

Het houdt in dat je
1) het probleem opsplitst in stappen. Bijvoorbeeld als je een reis naar Japan plant, dat je eerst kijkt wanneer je vrij hebt, dan een reisgids koopt om te bekijken wat je wilt zien, dan vliegtickets boekt, dan hotels boekt, enzovoorts. Op dat moment hoef je nog niet alles te weten (bijvoorbeeld WELKE reisgids je gaat kopen), maar je hebt dan in elk geval een grof stappenplan op papier. Omdat je dan problemen steeds verder opsplitst en je niet het hele probleem in je hoofd hoeft vast te houden ontlast je je werkgeheugen, waardoor je beter kunt nadenken.

2) als een stap makkelijk genoeg is om gelijk te programmeren, doe dat! Maak anders de stap het hoofd van een nieuwe alinea ('methode') en splits het verder uit, net zolang tot je alles in code hebt omgezet.

McConnell geeft wel een paar waarschuwingen:
1) dat je pseudocode in normale schrijf/spreektaal moet schrijven, en dat je constructies uit je programmeertaal moet vermijden. 
2) dat je pseudocode op het 'level of intent' moet schrijven: WAT er moet gebeuren, niet hoe precies. 
3) vergeet niet je pseudocode te checken. Is het correct? Ben je geen stappen vergeten?

Hieronder volgen een paar voorbeelden van steeds complexere opgaven gedaan met het PPP

1) CodeWars 8e Kyu: https://www.codewars.com/kata/56f699cd9400f5b7d8000b55
```You're at the zoo... all the meerkats look weird. Something has gone terribly wrong - someone has gone and switched their heads and tails around!

Save the animals by switching them back. You will be given an array which will have three values (tail, body, head). It is your job to re-arrange the array so that the animal is the right way round (head, body, tail).

Same goes for all the other arrays/lists that you will get in the tests: you have to change the element positions with the same exact logic```

Simples!

// template
public class WrongEndHead {
  public static String[] fixTheMeerkat(String[] arr) {
    return null;
  }
}
```

WrongEndHead: // versie 1
-ik krijg een array
-ik moet het eerste en laatste element ervan omwisselen
// gedachte: als ik het laatste element aan het eerste element van de array toeken, verlies ik de waarde van het eerste element, dus kan ik dat niet meer omwisselen.
// dus ik moet eerst de oorspronkelijke waarde van het eerste element opslaan

WrongEndHead: // versie 2
-ik krijg een array

-ik moet het eerste en laatste element ervan omwisselen
  -ik sla het eerste element op in een aparte variabele
  -ik kopieer de laatste waarde van de array naar de eerste positie van de array
  -ik kopieer de oorspronkelijke eerste waarde van de array naar de laatste positie
  -ik geef de array terug.
  
 Ik zet dat in de code:
 
 ```
 public class WrongEndHead {
  public static String[] fixTheMeerkat(String[] arr) {
  -ik moet het eerste en laatste element ervan omwisselen
  -ik sla het eerste element op in een aparte variabele
  -ik kopieer de laatste waarde van de array naar de eerste positie van de array
  -ik kopieer de oorspronkelijke eerste waarde van de array naar de laatste positie
  -ik geef de array terug.
    return null;
  }
}
```

En verwerk de commentaren

 ```
 public class WrongEndHead {
  // -ik moet het eerste en laatste element ervan omwisselen
  public static String[] fixTheMeerkat(String[] arr) {
    // -ik sla het eerste element op in een aparte variabele
    String first = arr[0];
  
    // -ik kopieer de laatste waarde van de array naar de eerste positie van de array
    arr[0] = arr[arr.length - 1];
    
    // -ik kopieer de oorspronkelijke eerste waarde van de array naar de laatste positie
    arr[arr.length - 1] = first;
    
    // -ik geef de array terug.
    return arr;
  }
}
```

Ik test het, en het werkt. Laatste fase: verwijder overbodige commentaren, en vertaal nuttige commentaren (wat betekent 'fixTheMeerkat'? Normaal zou ik dat wijzigen, maar in deze omstandigheden kan dat niet)

```
 public class WrongEndHead {
 
  // Swap first and last element of an array
  public static String[] fixTheMeerkat(String[] arr) {
    String first = arr[0];
    arr[0] = arr[arr.length - 1];
    arr[arr.length - 1] = first;
    return arr;
  }
}
```






