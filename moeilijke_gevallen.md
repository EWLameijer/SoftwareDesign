# Hoe om te gaan met echt moeilijke gevallen?

Wat als je een moeilijk geval hebt, waar zelfs ontwerpen in pseudocode nog te lastig lijkt?

In zo'n geval kan het helpen één concreet voorbeeld uit te werken, hoe simpel ook, en dan niet proberen ingewikkelde loops of recursie of methodenaanroepen te gebruiken, maar code extreem simpel te maken, desnoods met handmatig herhalen. Als je het dan concreet hebt uitgewerkt, kan je het of letterlijk zo programmeren (dat doe ik ook weleens in noodgevallen: code die werkt is beter dan code die niet werkt, al zou ik doorgaans een collega om advies vragen) of zie je een patroon.

Om met iets eenvoudigs te beginnen: wat als je alle drievouden van 10 tot 50 wilt uitprinten?

Wel, een drievoud is een getal dat je door drie kan delen en dan een rest van 0 heeft. Dus 3, 6, 9, 12. En 12 is groter dan 10!

Dus ik kan code schrijven als 
```
System.out.println(12);
System.out.println(15);
System.out.println(18);
System.out.println(21);
System.out.println(24);
System.out.println(27);
System.out.println(30);
System.out.println(33);
System.out.println(36);
System.out.println(39);
System.out.println(42);
System.out.println(45);
System.out.println(48);
// volgende is 51, maar dat is boven de 50
```

Okee, in het bovenstaande zit een duidelijk patroon. Telkens wordt het getal 3 hoger. Dan kan ik iets maken als

```
for (int number = 12; number < 50; number +=3 ) {
    System.out.println(number);
}
``` 

Is dit een ideale oplossing? Wel, hier is hij goed genoeg. Mocht er ooit gevraagd worden: maar wat als ik niet bij 10 wil beginnen maar bij 9? of 15? Dan zou je de boeken in moeten duiken omdat je wilt weten: als ik bij n begin: is n een drievoud? Ik zoek op java triple, dan java triplicate (maar daar zie ik niets) - dan op java divisible by 3. En daar zie ik % 3 == 0 (https://www.tutorialspoint.com/Check-if-a-large-number-is-divisible-by-3-or-not-in-java)

Dan kan ik iets maken als 

``` 
void printAllTriplesFromTo(int start, int end) {
	if (start % 3 != 0) start++;
		else { 
			for (int number = start; number < end; number +=3 ) {
    		System.out.println(number);	
		}
		return;
	}
	if (start % 3 != 0) start++;
		else { 
		    for (int number = start; number < end; number +=3 ) {
    		    System.out.println(number);
    		}
    		return;
		}
	// if n is not a triple and n+1 is not a triple, n+2 MUST be a triple
	for (int number = start; number < end; number +=3 ) {
    	System.out.println(number);
	}
}
```

Dit is lelijke code, maar hij werkt!

Je kan dit aanpassen (na wat nadenken) naar 
``` 
void printAllTriplesFromTo(int start, int end) {
	while (start % 3 != 0) start++;

	for (int number = start; number < end; number +=3 ) {
    	System.out.println(number);	
    }
}
```

Nog steeds geen absoluut mooie code, maar hij werkt nog steeds.

Mogelijk word je dan die avond in bed wakker omdat je beseft dat je nu % 3 kan gebruiken, en het om kan schrijven naar 

``` 
void printAllTriplesFromTo(int start, int end) {
	for (int number = start; number < end; number++ ) {
		if (number % 3 == 0) System.out.println(number);	
    }
}
```

Maar het kan ook zijn dat dat pas na maanden en vele keren het oefenen met % in je hoofd opkomt. 

En let wel: zeker in het begin is het allerbelangrijkst om code te schrijven die werkt, het gaat erom de 'creatieve sappen' te laten stromen. Uiteindelijk zal met meer ervaring (en het lezen van code van anderen, het anderen om advies vragen, en nadenken over hoe je code beter kan) je codeerkwaliteiten steeds beter worden, maar het gaat erom dat je een begin maakt.

Zoals schrijvers wel zeggen '*You can always rewrite a bad page. You can never rewrite a blank page*'.

En wat als je iets probeert dat nog te moeilijk is? Bijvoorbeeld

``` 
assertTrue(Kata.feast("great blue heron","garlic nann"));
assertTrue(Kata.feast("chickadee","chocolate cake"));
assertFalse(Kata.feast("brown bear","bear claw"));
    
```
https://www.codewars.com/kata/5aa736a455f906981800360d/train/java

En je hebt geen idee hoe dat aan te pakken?

Desnoods begin je met 1 geval uit te werken 

```
class Kata {
	static boolean feast(String animalName, String dishName) {
		if (animalName.equals("great blue heron") && dishName.equals("garlic nann")) return true;
		if (animalName.equals("chickadee") && dishName.equals("chocolate cake")) return true;
		else return false;
	}
}
```

Nu lijkt het alsof je niets bent opgeschoten, maar je hebt in elk geval wat geschreven, dus mogelijk dat je creatieve sappen wat op gang zijn gekomen. Je kan het nog eens proberen, bijvoorbeeld googelen op "first letter of string java" en last letter of string java 
