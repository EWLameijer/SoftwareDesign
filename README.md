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

De 7e kyu: vowel count (https://www.codewars.com/kata/54ff3102c1bad923760001f3/train/java)
```
Return the number (count) of vowels in the given string.

We will consider a, e, i, o, u as vowels for this Kata (but not y).

The input string will only consist of lower case letters and/or spaces.

public class Vowels {

  public static int getCount(String str) {
    return 0;
  }

}
```

// Versie 1
-ga elk karakter van de String na 
-als het karakter een 'a', 'e', 'i', 'o', of 'u' is, tel 1 op bij de score
-geef de score terug

Ik lees het door: ik heb nergens een score gedefinieerd!
// Versie 1
-zet de score op 0
-ga elk karakter van de string na 
-als het karakter een 'a', 'e', 'i', 'o', of 'u' is, tel 1 op bij de score
-geef de score terug

De code kopieren en de pseudocode invullen:

```
public class Vowels {

  public static int getCount(String str) {
  -zet de score op 0
-ga elk karakter van de string na 
-als het karakter een 'a', 'e', 'i', 'o', of 'u' is, tel 1 op bij de score
-geef de score terug
    return 0;
  }
}
```

Nu de pseudocode omzetten in commentaren, en vertalen naar Java
```
public class Vowels {

  public static int getCount(String str) {
   // -zet de score op 0
   int score = 0;
   // -ga elk karakter van de string na 
   for (char character : str) {
      // -als het karakter een 'a', 'e', 'i', 'o', of 'u' is, tel 1 op bij de score
      if (character  == 'a' || character  == 'e' || character  == 'i' || character  == 'o' || character  == 'u') score++;
    }
    // -geef de score terug
    return score;
  }
}
```

Ik test hem...  (zie dat ik vergeten was dat enhanced for-loops in Java niet over Strings lopen, moet hem eerst omzetten in een charArray

```
public class Vowels {

  public static int getCount(String str) {
   // -zet de score op 0
   int score = 0;
   // -ga elk karakter van de string na 
   for (char character : str.toCharArray()) {
      // -als het karakter een 'a', 'e', 'i', 'o', of 'u' is, tel 1 op bij de score
      if (character  == 'a' || character  == 'e' || character  == 'i' || character  == 'o' || character  == 'u') score++;
    }
    // -geef de score terug
    return score;
  }
}
```

Dit werkt. Weer ruim ik het commentaar op en verengels waar ik het houd

```
public class Vowels {

  // return the number of vowels ('a', 'e', 'i', 'o' and 'u') in the String
  public static int getCount(String str) {
   int score = 0;
   for (char character : str.toCharArray()) {
      if (character  == 'a' || character  == 'e' || character  == 'i' || character  == 'o' || character  == 'u') score++;
    }
    return score;
  }
}
```

Nu vind ik " if (character  == 'a' || character  == 'e' || character  == 'i' || character  == 'o' || character  == 'u')" wat lang. Ik bedoel: als het karakter een klinker is. Nu kent de computer geen klinkers (zie ik aan de Character-API), maar je zou klinkers wel kunnen beschouwen als een verzameling letters. Dus ik verander de code nog eens:

```
public class Vowels {
  private static final Set<Character> VOWELS = Set.of('a','e','i','o','u');

  // return the number of vowels ('a', 'e', 'i', 'o' and 'u') in the String
  public static int getCount(String str) {
   int score = 0;
   for (char character : str.toCharArray()) {
      if (VOWELS.contains(character)) score++;
    }
    return score;
  }
}
```

en niet de java.util.* vergeten voor de set...

```
import java.util.*;

public class Vowels {
  private static final Set<Character> VOWELS = Set.of('a','e','i','o','u');

  // return the number of vowels ('a', 'e', 'i', 'o' and 'u') in the String
  public static int getCount(String str) {
   int score = 0;
   for (char character : str.toCharArray()) {
      if (VOWELS.contains(character)) score++;
    }
    return score;
  }
}
```

Als je eenmaal een algoritme hebt bedacht, kun je altijd nog code verbeteren; pseudocode is niet in steen geschreven!

Op naar de 6e kyu...

https://www.codewars.com/kata/514b92a657cdc65150000006/train/java

If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.

Finish the solution so that it returns the sum of all the multiples of 3 or 5 below the number passed in. Additionally, if the number is negative, return 0 (for languages that do have them).

Note: If the number is a multiple of both 3 and 5, only count it once.

```
public class Solution {

  public int solution(int number) {
    //TODO: Code stuff here
  }
}
```

Ik lees de beschrijving, dus

// Versie 1
-als number < 0 is, return 0
-doe voor elk nummer van 0 tot 'number' (niet inclusief 'number')
  -als het getal een meervoud van drie of van vijf is, tel het op bij de som
-geef de som terug

// Versie 2
// ik zie dat als ik begin bij 0 en loop tot 'number', ik bij negatieve getallen automatisch geen getallen krijg.
// en dat ik natuurlijk een som moet definieren en op 0 moet zetten
- zet de som op 0.
- doe voor elk nummer van 0 tot kleiner dan 'number'
  -als het getal een meervoud van drie of van vijf is, tel het op bij de som
- geef de som terug

// Versie 3: hoe weet ik dat het een meervoud is van 3 of 5? Wel, de modulus-operator
- zet de som op 0.
- doe voor elk nummer van 0 tot kleiner dan 'number'
  -als het getal modulus drie nul is, of het getal modulus vijf nul is, tel het op bij de som
- geef de som terug

Dat ziet er doenlijk uit! Ik plak het in de code 

``` 
public class Solution {

  public int solution(int number) {
    - zet de som op 0.
- doe voor elk nummer van 0 tot kleiner dan 'number'
  -als het getal modulus drie nul is, of het getal modulus vijf nul is, tel het op bij de som
- geef de som terug
  }
}
```

En maak er commentaren van die ik uitwerk
```
public class Solution {

  public int solution(int number) {
    // - zet de som op 0.
    int sum = 0;
    // - doe voor elk nummer van 0 tot kleiner dan 'number'
    for (int i = 0; i < number; i++) {
       //-als het getal modulus drie nul is, of het getal modulus vijf nul is, tel het op bij de som
       if (i % 3 == 0 || i % 5 == 0) sum += i;
    }
    // - geef de som terug
    return sum;
  }
}
```

Ik test het, en ruim daarna het overbodige commentaar op 

```
public class Solution {

  // returns the sum of all numbers from [0..`number`> that are divisible by 3 and/or 5. If `number` is negative, returns 0.
  public int solution(int number) {
    int sum = 0;
    for (int i = 0; i < number; i++) {
       if (i % 3 == 0 || i % 5 == 0) sum += i;
    }
    return sum;
  }
}
```
Ik zou dit waarschijnlijk kunnen doen met een lambda en een range, maar daar heb ik even geen zin in. In elk geval een geslaagde oplossing!

5e Kyu: https://www.codewars.com/kata/520b9d2ad5c005041100000f/train/java
Move the first letter of each word to the end of it, then add "ay" to the end of the word. Leave punctuation marks untouched.

Examples
pigIt('Pig latin is cool'); // igPay atinlay siay oolcay
pigIt('Hello world !');     // elloHay orldway !

```
public class PigLatin {
    public static String pigIt(String str) {
        // Write code here
    }
}
```

// Versie 1
- neem elk woord in de zin 
- haal de letter aan het begin weg
- plak die letter aan het einde
- plak er "ay" achter
- en zet die nieuwe woorden aan elkaar

// Versie 2: hoe haal ik de woorden uit de zin? Wel, ik kan zoeken op de String API naar iets dat me iets geeft als een lijst of array van strings... https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html . split lijkt me wel handig! En ik moet nog een soort loop maken

// Versie 2
- splits de zin op in woorden
- voor elk woord in de zin\
  - haal de letter aan het begin weg
  - plak die letter aan het einde
  - plak er "ay" achter
  - en zet het nieuwe woord aan het einde van de 'gecodeerde' zin.
  
// Versie 3: moet dus nog een gecodeerde zin maken.
- maak een nieuwe, lege zin (die ik bv pigLatin noem)
- splits de originele zin op in woorden
- voor elk woord in de zin:
  - haal de letter aan het begin weg
  - plak die letter aan het einde
  - plak er "ay" achter
  - en zet het nieuwe woord aan het einde van de 'gecodeerde' zin.
- geef de gecodeerde zin terug

Ik check dit, het ziet er goed uit. Ik fuseer het met de opdrachtcode:
```
public class PigLatin {
    public static String pigIt(String str) {
        - maak een nieuwe, lege zin (die ik bv pigLatin noem)
- splits de originele zin op in woorden
- voor elk woord in de zin:
  - haal de letter aan het begin weg
  - plak die letter aan het einde
  - plak er "ay" achter
  - en zet het nieuwe woord aan het einde van de 'gecodeerde' zin.
- geef de gecodeerde zin terug
        
    }
}
```

Becommentarieer het en vertaal voorzover mogelijk:
```
public class PigLatin {
    public static String pigIt(String str) {
        // - maak een nieuwe, lege zin (die ik bv pigLatin noem)
        String pigLatinSentence = "";
        // - splits de originele zin op in woorden
        String[] words = str.split(" ");
        // - voor elk woord in de zin:
        for (String word : words) {
          // - haal de letter aan het begin weg
          // ! NOOT: ik besef nu dat een String onveranderbaar is. Dus ik maak een nieuw word, pigWord, waar ik alle karakters behalve het eerste naar kopieer
          String pigWord = word.substring(1);
          //- plak die letter aan het einde
          pigWord += word[0];
          // - plak er "ay" achter
          pigWord += "ay"
          // - en zet het nieuwe woord aan het einde van de 'gecodeerde' zin. (ik zie overigens nu dat er nog een spatie achter moet!)
         pigLatinSentence += pigWord + " ";  
       }
      //- geef de gecodeerde zin terug
      return pigLatinSentence;  
    }
}
```

Nu zegt mijn intuitie dat ik iets wantrouwig moet zijn: allereerst wil ik niet dat de zin eindigt met een spatie, maar ten tweede wil ik niet ! in een !ay omzetten...

Bij het testen ziet de compiler uberhaupt twee fouten (vergeten ;, en word[0] moet natuurlijk word.charAt(0) zijn, dit is geen Kotlin... Maar de spatie aan het einde frustreert me meer; nu heeft Java (het is handig de String API door te lezen) een join methode die alleen op tussenliggende plekken spaties zet. Dat vind ik netter dan na elk woord een spatie zetten en die aan het eind weer weghalen. Dus zonder de bugs en met String.join wordt het iets als

```
import java.util.*;

public class PigLatin {
    public static String pigIt(String str) {
        // - maak een nieuwe, lege zin (die ik bv pigLatin noem)
        String pigLatinSentence = "";
        // - splits de originele zin op in woorden
        String[] words = str.split(" ");
        List<String> pigLatinWords = new ArrayList<>();
        // - voor elk woord in de zin:
        for (String word : words) {
          // - haal de letter aan het begin weg
          // ! NOOT: ik besef nu dat een String onveranderbaar is. Dus ik maak een nieuw word, pigWord, waar ik alle karakters behalve het eerste naar kopieer
          String pigWord = word.substring(1);
          //- plak die letter aan het einde
          pigWord += word.charAt(0);
          // - plak er "ay" achter
          pigWord += "ay";
          // - en zet het nieuwe woord aan het einde van de 'gecodeerde' zin. (ik zie overigens nu dat er nog een spatie achter moet!)
          pigLatinWords.add(pigWord)
       }
      //- geef de gecodeerde zin terug
      return String.join(" ", pigLatinWords);  
    }
}
```

Nu bleek er WEER een ; te ontbreken (Kotlin-gewoonte)

```
import java.util.*;

public class PigLatin {
    public static String pigIt(String str) {
        // - maak een nieuwe, lege zin (die ik bv pigLatin noem)
        String pigLatinSentence = "";
        // - splits de originele zin op in woorden
        String[] words = str.split(" ");
        List<String> pigLatinWords = new ArrayList<>();
        // - voor elk woord in de zin:
        for (String word : words) {
          // - haal de letter aan het begin weg
          // ! NOOT: ik besef nu dat een String onveranderbaar is. Dus ik maak een nieuw word, pigWord, waar ik alle karakters behalve het eerste naar kopieer
          String pigWord = word.substring(1);
          //- plak die letter aan het einde
          pigWord += word.charAt(0);
          // - plak er "ay" achter
          pigWord += "ay";
          // - en zet het nieuwe woord aan het einde van de 'gecodeerde' zin. (ik zie overigens nu dat er nog een spatie achter moet!)
          pigLatinWords.add(pigWord);
       }
      //- geef de gecodeerde zin terug
      return String.join(" ", pigLatinWords);  
    }
}
```

maar ernstiger is dat er inderdaad een probleem komt met het uitroepteken. Laat ik aannemen dat 1 karakter te weinig is, dan worden leestekens overgeslagen. De pseudocode EN het programma aanpassend

```
import java.util.*;

public class PigLatin {
    public static String pigIt(String str) {
        // - maak een nieuwe, lege zin (die ik bv pigLatin noem)
        String pigLatinSentence = "";
        // - splits de originele zin op in woorden
        String[] words = str.split(" ");
        List<String> pigLatinWords = new ArrayList<>();
        // - voor elk woord in de zin:
        for (String word : words) {
          // - haal de letter aan het begin weg
          // ! NOOT: ik besef nu dat een String onveranderbaar is. Dus ik maak een nieuw word, pigWord, waar ik alle karakters behalve het eerste naar kopieer
          // WOORDEN VAN 1 letter (dus automatisch leestekens) moeten niet 'verpigt' worden;
          if (word.length() == 1) pigLatinWords.add(word);
          else {
            String pigWord = word.substring(1);
            //- plak die letter aan het einde
            pigWord += word.charAt(0);
            // - plak er "ay" achter
            pigWord += "ay";
            // - en zet het nieuwe woord aan het einde van de 'gecodeerde' zin. (ik zie overigens nu dat er nog een spatie achter moet!)
            pigLatinWords.add(pigWord);
          }
       }
      //- geef de gecodeerde zin terug
      return String.join(" ", pigLatinWords);  
    }
}
```

Wel, de tests zeggen dat het dus NIET geldt voor woorden van 1 letter. Ik zorg dus dat het niet-piggen alleen voor niet-letters geldt (karakterklasse), en draai de if-else om (ook voor "...", dat moet ook niet gepigd worden)

```
import java.util.*;

public class PigLatin {
    public static String pigIt(String str) {
        // - maak een nieuwe, lege zin (die ik bv pigLatin noem)
        String pigLatinSentence = "";
        // - splits de originele zin op in woorden
        String[] words = str.split(" ");
        List<String> pigLatinWords = new ArrayList<>();
        // - voor elk woord in de zin:
        for (String word : words) {
          // - haal de letter aan het begin weg
          // ! NOOT: ik besef nu dat een String onveranderbaar is. Dus ik maak een nieuw word, pigWord, waar ik alle karakters behalve het eerste naar kopieer
          // WOORDEN VAN 1 letter (dus automatisch leestekens) moeten niet 'verpigt' worden;
          if (Character.isLetter(word.charAt(0))) {
            String pigWord = word.substring(1);
            //- plak die letter aan het einde
            pigWord += word.charAt(0);
            // - plak er "ay" achter
            pigWord += "ay";
            // - en zet het nieuwe woord aan het einde van de 'gecodeerde' zin. (ik zie overigens nu dat er nog een spatie achter moet!)
            pigLatinWords.add(pigWord);
          }
       } else pigLatinWords.add(word);
      //- geef de gecodeerde zin terug
      return String.join(" ", pigLatinWords);  
    }
}
```

en de else goed zettend...

```
import java.util.*;

public class PigLatin {
    public static String pigIt(String str) {
        // - maak een nieuwe, lege zin (die ik bv pigLatin noem)
        String pigLatinSentence = "";
        // - splits de originele zin op in woorden
        String[] words = str.split(" ");
        List<String> pigLatinWords = new ArrayList<>();
        // - voor elk woord in de zin:
        for (String word : words) {
          // - haal de letter aan het begin weg
          // ! NOOT: ik besef nu dat een String onveranderbaar is. Dus ik maak een nieuw word, pigWord, waar ik alle karakters behalve het eerste naar kopieer
          // WOORDEN VAN 1 letter (dus automatisch leestekens) moeten niet 'verpigt' worden;
          if (Character.isLetter(word.charAt(0))) {
            String pigWord = word.substring(1);
            //- plak die letter aan het einde
            pigWord += word.charAt(0);
            // - plak er "ay" achter
            pigWord += "ay";
            // - en zet het nieuwe woord aan het einde van de 'gecodeerde' zin. (ik zie overigens nu dat er nog een spatie achter moet!)
            pigLatinWords.add(pigWord);
          } else pigLatinWords.add(word);
       } 
      //- geef de gecodeerde zin terug
      return String.join(" ", pigLatinWords);  
    }
}
```

Dat werkt eindelijk! Ik ruim de commentaren/pseudocode op

```
import java.util.*;

public class PigLatin {
    // convert a sentence into Pig Latin
    public static String pigIt(String str) {
        String pigLatinSentence = "";
        String[] words = str.split(" ");
        List<String> pigLatinWords = new ArrayList<>();
        for (String word : words) {
          if (Character.isLetter(word.charAt(0))) {
            String pigWord = word.substring(1);
            pigWord += word.charAt(0);
            pigWord += "ay";
            pigLatinWords.add(pigWord);
          } else pigLatinWords.add(word);
       } 
      return String.join(" ", pigLatinWords);  
    }
}
```
Ik zie nu dat ik het pigWord in 1 regel zou kunnen maken...

```
import java.util.*;

public class PigLatin {
    // convert a sentence into Pig Latin
    public static String pigIt(String str) {
        String pigLatinSentence = "";
        String[] words = str.split(" ");
        List<String> pigLatinWords = new ArrayList<>();
        for (String word : words) {
          if (Character.isLetter(word.charAt(0))) {
            String pigWord = word.substring(1) + word.charAt(0) + "ay";
            pigLatinWords.add(pigWord);
          } else pigLatinWords.add(word); // handle punctuation marks and numbers
       } 
      return String.join(" ", pigLatinWords);  
    }
}
```

Dit ziet er goed uit!


