# Programma's begrijpen

Voor programmeren heb je een zekere mate van creativiteit nodig; je moet bouwstenen met elkaar kunnen combineren om een oplossing te maken. Maar dat betekent ook dat je de bouwstenen moet kennen en begrijpen, en een oppervlakkige kennis helpt niet voldoende.

Zoals Charles Duhigg bescrijft in "Smarter, Faster, Better" hebben de toppers in een gebied de neiging mentale modellen te maken en voorspellingen te doen, en die dan te toetsen. Je kunt immers alles verklaren (zoals bepaalde geloofsovertuigers die ik ooit tegenkwam alles konden verklaren "Waarom is de hemel blauw? -Omdat God het wil. Waarom leven mensen maar zo'n 80 tot 120 jaar? Omdat God het wil. Waarom bestaat er kwaad op aarde? Omdat God het wil...). Maar pas als je iets kunt voorspellen, bewijs je dat je iets ook voldoende begrijpt.

Hier wat voorbeeldcode, als demonstratie hoe je iets kunt leren door te voorspellen.

```
String[] gifts = {"partridge in a pear tree", "turtle doves", "french hens", "calling birds", "gold rings",
"geese a-laying", "swans a-swimming", "maids a-milking", "ladies dancing", "lords a-leaping",
"pipers piping", "drummers drumming"};

for (int dayIndex = 0; dayIndex < gifts.length; dayIndex++) {
    System.out.printf("I'll give you %d %s!%n", dayIndex + 1,    gifts[dayIndex]);
}

```
Hoe analyseer je zoiets? Ik begin met de code te lezen en de variabelen en constanten die ik tegenkom te noteren:

gifts = {"partridge ..", "turtle doves", "french hens", ...}
dayIndex = 0 

Stel je voor dat ik op dit moment het model van for-loops heb:
for (a;b;c) {d}: doe eerst a, dan b, dan c, dan d. Dan weer a, dan b, dan c, dan d

Dan voorspel ik:
``` 
gifts = {"partridge ..", "turtle doves", "french hens", ...}
dayIndex = 0 
```
 // is dayIndex < gifts.length? Ik tel de gifts, gifts.length is 12, dat noteer ik dus ook 
 
 ``` 
gifts = {"partridge ..", "turtle doves", "french hens", ...}
dayIndex = 0 
gifts.length = 12
```
Hoe dan ook, het is true, dan ga ik door.
dayIndex ++ => dus dayIndex wordt 1


- gifts = {"partridge ..", "turtle doves", "french hens", ...}
- dayIndex = ~~0~~ 1
- gifts.length = 12

Wel, laat ik ook aannemen dat het eerste element de "partridge" is (is toch de eerste in de array), dan verwacht ik dat eruit komt 
"I'll give you 2 partridge in a pear tree!". Daarna wordt dayIndex weer 0, dus ik krijg een oneindige hoeveelheid "I'll give you 2 partridge in a pear tree!". Nogal stomme code! Ik heb mijn voorspelling gedaan, dus ik run het.

Er komt uit "I'll give you 1 partridge in a pear tree!\nI'll give you 2 turtle doves" // etc.

Mijn voorspelling klopt dus niet. Ik kan dus de volgende dingen afleiden:
1) kennelijk was dayIndex nog steeds 0 bij het begin van de inhoud van de loop; de dayIndex++ was dus nog niet uitgevoerd.
2) Kennelijk begint Java arrays bij 0 te tellen
3) Omdat de loop ophoudt, wordt kennelijk het eerste commando van de for-loop maar 1x uitgevoerd.
4) de dayIndex++ WORDT kennelijk wel uitgevoerd elke ronde, maar pas aan het einde

Ik maak een nieuwe voorspelling, en laat het opnieuw in mijn hoofd starten:
- gifts = {"partridge ..", "turtle doves", "french hens", ...}
- dayIndex = 0
- gifts.length = 12

print: "I'll give you (0+1) (gifts[0]==partridge)" == "Ill give you 1 partridge in a pear tree.\n"
dan denk ik dus: dayIndex++ (ik streep nu 0 door en vervang het door 1)

- gifts = {"partridge ..", "turtle doves", "french hens", ...}
- dayIndex = ~~0~~ 1
- gifts.length = 12

Omdat de loop niet oneindig is weet ik dat ik de a in for(a;b;c) moet overslaan; ik moet waarschijnlijk wel b doen, omdat de loop anders niet eindigt.

dayIndex = 1 < 12 => is nog okee
"I'll give you (1+1) (gifts[1]=="turtle doves")" == "Ill give you 2 turtle doves.\n"

Dan ongetwijfeld dayIndex++ =>
- gifts = {"partridge ..", "turtle doves", "french hens", ...}
- dayIndex = ~~0~~ ~~1~~ 2
- gifts.length = 12

3 French hens... Hoe eindigt het? Op een gegeven moment is dayIndex 11 (nog steeds kleiner dan 12,) dus krijg je 11+1 = 12 drummers drumming, dayIndex wordt 11+1 = 12, is NIET kleiner dan 12, dus dan eindigt het met 12 drummers drumming.

Nu klinkt het bovenstaande misschien een beetje stom: kun je niet in een boek of op internet vinden dat een for-loop for(a;b;c) {d;}net zo werkt als 

``` 
a;
while (b) {
    d;
    c;
}
```

? 

Ja, natuurlijk kan je dat! (in feite is het overigens
``` 
{ 
    a;
    while (b) {
        d;
        c;	
    }
}
```
omdat variabelen gedefinieerd in het eerste deel niet meer in scope zijn na de for-loop.

Maar er is een verschil tussen iets lezen en 'begrepen' hebben en het zodanig begrepen hebben dat je het kan toepassen. In het geval van de for-loops moet je waarschijnlijk het voorspellen een paar keer herhalen, met verschillende for-loops op verschillende dagen. Maar in feite oefen je als developer voortdurend het simuleren van code in je hoofd, dat is zowel hoe je code nakijkt, peer-reviewt en debugt. In het begin is dat nog traag en moeilijk - maar dus wel leerzaam, maar op een gegeven moment heb je zowel een goed mentaal model van hoe een programmeertaal werkt als dat het ook een _snel_ mentaal model is dat bij de meeste code weinig tijd en inspanning meer kost.

Een tweede demonstratie, dat systematisch bijhouden van variabelen ook helpt met complexe gevallen, bekijk het volgende probleem. Iemand had een recursief programma geschreven om van 1 naar 10 te tellen

``` 
static void countUp(int number) {
    if (number > 10) return;
    countUp(number + 1);
    System.out.println(number);
}

public static void main(String[] args) 
{
    countUp(1);
}
``` 

Maar het print niet 1/2/3/4/5 ... maar 10/9/8/7/..1 Hoe komt dat?

Zelf kon ik het probleem snel zien na korte mentale 'simulatie', maar als ik zelf zou beginnen met recursie zou ik het explicieter aanpakken, met papier!

countup: aanroep met 1 
	number = 1 
	// hoeft niet te returnen, 1 is niet groter dan 10  
	countup(2)
		countup: aanroep met 2
		number = 2
		// hoef niet te returnen, 2 is niet groter dan 10 
		countUp(3)
			countup: aanroep met 3
			number = 3
			// hoef niet te returnen, 3 is niet groter dan 10 
			... etc.
				... countup: aanroep met 11
				// MOET nu wel returnen
			println(10)// dat zal 10 zijn 
			// nu eindigt de methode, dus return
		// print nu 9 uit...
		
Het probleem is dus dat de recursieve countUp-aanroep VOOR de println wordt aangeroepen. Ik wissel ze om... 

``` 
static void countUp(int number) {
    if (number > 10) return;
    System.out.println(number);
    countUp(number + 1);
}

public static void main(String[] args) 
{
    countUp(1);
}
``` 
en het werkt zoals bedoeld!
