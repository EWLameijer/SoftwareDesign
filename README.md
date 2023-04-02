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

Dit ziet er goed uit! Ik lever hem in...

Al zie ik later dat String pigLatinSentence = ""; nu helemaal overbodig is; daarom is het normaal goed om in een editor als IDEA te werken, en alle code ook nog eens na te kijken voordat je commit. Is dit bovenstaande de beste of snelste oplossing? Nee, het kan ook in 1 regel met regexes (die ik zeker beter zou kunnen bestuderen). 
Maar het is in elk geval iets dat redelijk goed is. Op naar de 4e kyu!

4e kyu: https://www.codewars.com/kata/521c2db8ddc89b9b7a0000c1/train/java

Snail Sort
Given an n x n array, return the array elements arranged from outermost elements to the middle element, traveling clockwise.

array = [[1,2,3],
         [4,5,6],
         [7,8,9]]
snail(array) #=> [1,2,3,6,9,8,7,4,5]
For better understanding, please follow the numbers of the next array consecutively:

array = [[1,2,3],
         [8,9,4],
         [7,6,5]]
snail(array) #=> [1,2,3,4,5,6,7,8,9]
This image will illustrate things more clearly:


NOTE: The idea is not sort the elements from the lowest value to the highest; the idea is to traverse the 2-d array in a clockwise snailshell pattern.

NOTE 2: The 0x0 (empty matrix) is represented as en empty array inside an array [[]].

```
public class Snail {

    public static int[] snail(int[][] array) {
     // enjoy
   } 
}
```

Bij een geval als dit vraag ik me af hoe ik dit probleem zou uitleggen aan iemand die het met de hand zou moeten doen. Waarschijnlijk iets als:
- begin bij de eerste coordinaat (0,0)
- ga recht vooruit (de rij af, tot je bij het laatste element komt)
- draai naar rechts
- ga recht vooruit, tot je bij het laatste element komt
- draai naar rechts
- ga recht vooruit...

Ik maak ook een tekening (van 5x5). Dat heb ik uiteraard op papier gedaan, hier een grove reproductie voor de duidelijkheid.
```
. 1> . 2> . 3> . 4> .
                    1
                    v
. 1> . 2> . 3> .    .
^              1    2 
3              v    v
.    . 1> .    .    .
^    ^         2    3
2    1         v    v
.    . <2 . <1 .    .
^                   4
1                   v
. <4 . <3 . <2 . <1 .
```

Het grote probleem is dat je bij de vierde zijde 1 eerder moet stoppen, je moet onthouden dat je al het element linksboven hebt bezocht. Nu zie ik verschillende mogelijkheden: ik zou een even grote array kunnen maken om aan te geven welke punten ik al bezocht heb (of een set: (0,0), (1,0), (2,0) enzovoorts). Of bijhouden hoever ik mag gaan (dat rij 0 al gedaan is, en dan kolom 5). De tweede mogelijkheid neemt minder geheugen in beslag, maar lijkt me wel wat ingewikkelder.

En soms kan het helpen (een heuristiek/vuistregel die ik in de loop van jaren heb geleerd) het probleem _omgekeerd_ aan te pakken: dat ik een set maak van alle overblijvende coordinaten, die ik langzaam 1 voor 1 verminder. Dat heeft het voordeel dat ik niet hoef te controleren of ik buiten de grenzen van de array kom: als de volgende coordinaat in de richting die ik opga niet bestaat, maar er nog wel coordinaten over zijn, maak ik een bocht.

Ik denk dat ik dat maar doe. En draaien? Wel, een richting geef je als programmeur vaak aan als (dx, dy) (verandering in x, verandering in y). In de eerste rij: dx =1, dy =0 (je gaat naar rechts). Dan draai je naar rechts: dx = 0, dy = 1 (de y neemt toe). Dan weer naar rechts: dx = -1, dy = 0. Dan weer naar rechts: dx = 0, dy = -1.

Als ik probeer daarin patronen te ontdekken lijkt het erop: draai naar rechts: nieuwe dy = oude dx, nieuwe dx is -oude dy. Dus dan inderdaad (1,0), (0,1), (-1,0), (0, -1) (en dan weer (1,0), want (dx = - (oude dy), dy = (oude dx)) == (dx = - (-1), dy = 0) == (1, 0)

Dus mijn strategie is als volgt:
// Versie 1
- bepaal de lengte van de array (grootste dx)
- bepaal de hoogte van de array 
- maak een set met daarin alle geldige coordinaten

- zolang er nog geldige coordinaten zijn in de set:


// Versie 2: ik zie dat een set maken met alle geldige coordinaten wat complexer is, dus daar maak ik een aparte alinea/methode van

- zet de huidige richting (dx, dy) op (1, 0)
- zet de huidige coordinaat op 0,0
- @ genereer een set met alle geldige coordinaten 
- zolang er nog geldige coordinaten zijn in de set
  - verwijder de huidige coordinaat uit de set, en voeg hem toe aan het einde van de lijst gesorteerde punten
  - als er een nieuwe coordinaat is op de huidige coordinaat + de huidige dx, dy, zet de huidige coordinaat op de nieuwe coordinaat
  - roteer anders de dx, dy, vervang de huidige coordinaat door de huidige coordinaat + de nieuwe dx, dy

- genereer een set met alle geldige coordinaten 
  - bepaal de lengte van de array (grootste dx)
  - bepaal de hoogte van de array 
  - voor elke x coordinaat van 0 tot de laatste 
    - voor elke y-coordinaat van 0 tot de laatste 
      - voeg deze (x,y) toe aan de set van te bezoeken coordinaten      

// Versie 3: ik moet uiteraard wel een lege lijst aanmaken voor het sorteren/opslaan van de punten. En hem ook teruggeven...
// Dat ik een coordinaat-klasse (wel, record) moet aanmaken en ook een richtings-klasse (record) dat zet ik hier niet expliciet. Of wel een beetje...
- maak een lege lijst van coordinaten aan
- zet de huidige richting (dx, dy) op (1, 0)
- zet de huidige coordinaat op 0,0
- @ genereer een set met alle geldige coordinaten 
- zolang er nog geldige coordinaten zijn in de set
  - verwijder de huidige coordinaat uit de set, en voeg hem toe aan het einde van de lijst gesorteerde punten
  - als er een nieuwe coordinaat is op de huidige coordinaat + de huidige dx, dy, zet de huidige coordinaat op de nieuwe coordinaat
  - roteer anders de dx, dy, vervang de huidige coordinaat door de huidige coordinaat + de nieuwe dx, dy
- geef de lijst coordinaten terug

- genereer een set met alle geldige coordinaten 
  - bepaal de lengte van de array (grootste dx)
  - bepaal de hoogte van de array 
  - voor elke x coordinaat van 0 tot de laatste 
    - voor elke y-coordinaat van 0 tot de laatste 
      - voeg deze (x,y) toe aan de set van te bezoeken coordinaten 

- # coordinaat
  - move(direction) => nieuwe coordinaat 

- # direction 
  - turnRight() => nieuwe direction    

Okee, dat ziet er denk ik voorlopig voldoende solide uit. Laat ik het gaan uitproberen... Invoegen in code:

```
import java.util.*; // for Set

public class Snail {

    public static int[] snail(int[][] array) {
     // - maak een lege lijst van coordinaten aan
     List<Coordinate> sortedCoordinates = new ArrayList<>();
     // - zet de huidige richting (dx, dy) op (1, 0)
     var currentDirection = new Direction(1, 0);
     // - zet de huidige coordinaat op 0,0
     var currentCoordinate = new Coordinate(0, 0);
     // - @ genereer een set met alle geldige coordinaten 
     Set<Coordinate> unexploredCoordinates = getAllCoordinates(array);
     // - zolang er nog geldige coordinaten zijn in de set
     while (!unexploredCoordinates.isEmpty()) {
       // - verwijder de huidige coordinaat uit de set, en voeg hem toe aan het einde van de lijst gesorteerde punten
       unexploredCoordinates.remove(currentCoordinate);
       sortedCoordinates.add(array[currentCoordinate.y][currentCoordinate.x]);
       // - als er een nieuwe coordinaat is op de huidige coordinaat + de huidige dx, dy, zet de huidige coordinaat op de nieuwe coordinaat
       var candidateCoordinate = currentCoordinate.move(currentDirection);
       if (unexploredCoordinates.contains(candidateCoordinate)) currentCoordinate = candidateCoordinate;
       else {
          // - roteer anders de dx, dy, vervang de huidige coordinaat door de huidige coordinaat + de nieuwe dx, dy
          currentDirection = currentDirection.turnRight();
          currentCoordinate = currentCoordinate.move(currentDirection);
       }
     }
     // - geef de lijst coordinaten terug // opzoeken hoe ik een arraylist omzet in een array via internet
     return sortedCoordinates.toArray();
   } 
   
   // - genereer een set met alle geldige coordinaten 
  private Set<Coordinate> getAllCoordinates(int[][] inputArray) {
    // VERGETEN: MAAK EEN NIEUWE SET AAN!
    Set<Coordinate> allCoordinates = new HashSet<>();
    // - bepaal de lengte van de array (grootste dx)
    int xDimension = inputArray[0].length; // {{1,2,3},{4,5,6}} is an array[2][3]
    // - bepaal de hoogte van de array 
    int yDimension = inputArray.length;
    //- voor elke x coordinaat van 0 tot de laatste 
    for (int x = 0; x < xDimension) {
      // - voor elke y-coordinaat van 0 tot de laatste 
      for (int y = 0; y < yDimension) {
        // - voeg deze (x,y) toe aan de set van te bezoeken coordinaten 
        allCoordinates.add(new Coordinate(x,y));
      }
    }
    // VERGETEN: GEEF DIE SET TERUG
    return allCoordinates;
  }



  //- # coordinaat
  //- move(direction) => nieuwe coordinaat 
  record  Coordinate(int x, int y) {
    public Coordinate move(Direction direction) {
      return Coordinate(x + direction.x(), y + direction.y());
    }
  }
  

  //- # direction 
  //- turnRight() => nieuwe direction    
  record Direction(int dx, int dy) {
     public Direction turnRight() {
       return new Direction(-dy, dx);
     }
  }
}
```

Ik copy-plak het in IDEA, en zie gelijk een aantal fouten: static vergeten voor de getAllCoordinates, en ik moet geen lijst coordinaten teruggeven, maar een lijst ints; ik was de x++ en y++ vergeten, en bij move moet ik uiteraard new toevoegen voor Coordinate en direction.dx() en .dy() gebruiken!

Nieuwe versie:
```
import java.util.*; // for Set

public class Snail {

    public static int[] snail(int[][] array) {
        // - maak een lege lijst van coordinaten aan
        List<Integer> sortedCoordinates = new ArrayList<>();
        // - zet de huidige richting (dx, dy) op (1, 0)
        var currentDirection = new Direction(1, 0);
        // - zet de huidige coordinaat op 0,0
        var currentCoordinate = new Coordinate(0, 0);
        // - @ genereer een set met alle geldige coordinaten
        Set<Coordinate> unexploredCoordinates = getAllCoordinates(array);
        // - zolang er nog geldige coordinaten zijn in de set
        while (!unexploredCoordinates.isEmpty()) {
            // - verwijder de huidige coordinaat uit de set, en voeg hem toe aan het einde van de lijst gesorteerde punten
            unexploredCoordinates.remove(currentCoordinate);
            sortedCoordinates.add(array[currentCoordinate.y][currentCoordinate.x]);
            // - als er een nieuwe coordinaat is op de huidige coordinaat + de huidige dx, dy, zet de huidige coordinaat op de nieuwe coordinaat
            var candidateCoordinate = currentCoordinate.move(currentDirection);
            if (unexploredCoordinates.contains(candidateCoordinate)) currentCoordinate = candidateCoordinate;
            else {
                // - roteer anders de dx, dy, vervang de huidige coordinaat door de huidige coordinaat + de nieuwe dx, dy
                currentDirection = currentDirection.turnRight();
                currentCoordinate = currentCoordinate.move(currentDirection);
            }
        }
        // - geef de lijst coordinaten terug // opzoeken hoe ik een arraylist omzet in een array via internet
        // https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array
        return sortedCoordinates.stream().mapToInt(i -> i).toArray();
    }

    // - genereer een set met alle geldige coordinaten
    private static Set<Coordinate> getAllCoordinates(int[][] inputArray) {
        // VERGETEN: MAAK EEN NIEUWE SET AAN!
        Set<Coordinate> allCoordinates = new HashSet<>();
        // - bepaal de lengte van de array (grootste dx)
        int xDimension = inputArray[0].length; // {{1,2,3},{4,5,6}} is an array[2][3]
        // - bepaal de hoogte van de array
        int yDimension = inputArray.length;
        //- voor elke x coordinaat van 0 tot de laatste
        for (int x = 0; x < xDimension; x++) {
            // - voor elke y-coordinaat van 0 tot de laatste
            for (int y = 0; y < yDimension; y++) {
                // - voeg deze (x,y) toe aan de set van te bezoeken coordinaten
                allCoordinates.add(new Coordinate(x, y));
            }
        }
        // VERGETEN: GEEF DIE SET TERUG
        return allCoordinates;
    }


    //- # coordinaat
    //- move(direction) => nieuwe coordinaat
    record Coordinate(int x, int y) {
        public Coordinate move(Direction direction) {
            return new Coordinate(x + direction.dx(), y + direction.dy());
        }
    }


    //- # direction
    //- turnRight() => nieuwe direction
    record Direction(int dx, int dy) {
        public Direction turnRight() {
            return new Direction(-dy, dx);
        }
    }
}
```

Mooi, het werkt in 1x! Nu ben ik me ervan bewust dat dit CodeWars is en er best een grote kans is dat iemand dit in 1 of 2 regels code heeft opgelost, maar als professioneel programmeur is het meestal beter om code af te krijgen die werkt dan dagen te besteden aan iets dat net iets mooier of eleganter is. Hoe dan ook, ik verwijder de overbodige commentaren... 

```
import java.util.*; // for Set

public class Snail {

    // snail sort: transform a 2D-array into a 1D-array with the first element being the top left corner of the 2D-array,
    // spiralling in clockwise towards the middle element.
    public static int[] snail(int[][] array) {
        List<Integer> sortedCoordinates = new ArrayList<>();
        var currentDirection = new Direction(1, 0);
        var currentCoordinate = new Coordinate(0, 0);
        Set<Coordinate> unexploredCoordinates = getAllCoordinates(array);
        while (!unexploredCoordinates.isEmpty()) {
            unexploredCoordinates.remove(currentCoordinate);
            sortedCoordinates.add(array[currentCoordinate.y][currentCoordinate.x]);
            var candidateCoordinate = currentCoordinate.move(currentDirection);
            if (unexploredCoordinates.contains(candidateCoordinate)) currentCoordinate = candidateCoordinate;
            else {
                currentDirection = currentDirection.turnRight();
                currentCoordinate = currentCoordinate.move(currentDirection);
            }
        }
        // https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array
        return sortedCoordinates.stream().mapToInt(i -> i).toArray();
    }

    private static Set<Coordinate> getAllCoordinates(int[][] inputArray) {
        Set<Coordinate> allCoordinates = new HashSet<>();
        int xDimension = inputArray[0].length; // {{1,2,3},{4,5,6}} is an array[2][3]
        int yDimension = inputArray.length;
        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                allCoordinates.add(new Coordinate(x, y));
            }
        }
        return allCoordinates;
    }

    record Coordinate(int x, int y) {
        public Coordinate move(Direction direction) {
            return new Coordinate(x + direction.dx(), y + direction.dy());
        }
    }

    record Direction(int dx, int dy) {
        public Direction turnRight() {
            return new Direction(-dy, dx);
        }
    }
}
```

Nu vind ik de snail-methode iets lang worden (meer dan 16 regels), en gebeurt er basaal 2x currentCoordinate = currentCoordinate.move(currentDirection);
Dus ik pas het nog licht aan... En vind nu "plus" een betere benaming dan "move", want ik pas de huidige coordinaat niet aan in de move-methode...

Laatste versie:
```
import java.util.*; // for Set

public class Snail {

    // snail sort: transform a 2D-array into a 1D-array with the first element being the top left corner of the 2D-array,
    // spiralling in clockwise towards the middle element.
    public static int[] snail(int[][] array) {
        List<Integer> sortedCoordinates = new ArrayList<>();
        var currentDirection = new Direction(1, 0);
        var currentCoordinate = new Coordinate(0, 0);
        Set<Coordinate> unexploredCoordinates = getAllCoordinates(array);
        while (!unexploredCoordinates.isEmpty()) {
            unexploredCoordinates.remove(currentCoordinate);
            sortedCoordinates.add(array[currentCoordinate.y][currentCoordinate.x]);
            if (!unexploredCoordinates.contains(currentCoordinate.plus(currentDirection)))
                currentDirection = currentDirection.turnRight();
            currentCoordinate = currentCoordinate.plus(currentDirection);
        }
        // https://stackoverflow.com/questions/718554/how-to-convert-an-arraylist-containing-integers-to-primitive-int-array
        return sortedCoordinates.stream().mapToInt(i -> i).toArray();
    }

    private static Set<Coordinate> getAllCoordinates(int[][] inputArray) {
        Set<Coordinate> allCoordinates = new HashSet<>();
        int xDimension = inputArray[0].length; // {{1,2,3},{4,5,6}} is an array[2][3]
        int yDimension = inputArray.length;
        for (int x = 0; x < xDimension; x++) {
            for (int y = 0; y < yDimension; y++) {
                allCoordinates.add(new Coordinate(x, y));
            }
        }
        return allCoordinates;
    }

    record Coordinate(int x, int y) {
        public Coordinate plus(Direction direction) {
            return new Coordinate(x + direction.dx(), y + direction.dy());
        }
    }

    record Direction(int dx, int dy) {
        public Direction turnRight() {
            return new Direction(-dy, dx);
        }
    }
}
```

Zo te zien niet de eenvoudigste oplossing die bedacht is, maar het werkt, dus zolang ik geen carrière ambieer in een uitermate wiskundig bedrijf denk ik dat het zo goed is!

Nu vraag je je misschien af: en nu dus een 3e kyu? Persoonlijk doe ik dat liever niet, om twee redenen.

Allereerst deed ik ongeveer een hele werkdag over de laatste 3e kyu die ik probeerde (de Faberge easter eggs crush test: https://www.codewars.com/kata/54cb771c9b30e8b5250011d4). Ten tweede is het voor enterprise-ontwikkelen (en voor vervolgopdrachten) belangrijker dat je een typischer industrieel probleem leert aanpakken, met meerdere klassen; en dat is niet echt het focus van CodeWars.

Nu dus een complex voorbeeld met klassen! Zie hiervoor de 'hearthstone_simulator.md'.
