# Hoe leer je programmeren? Een paar hints en een stappenplan

## TLDR
- lees een goed programmeerboek
- type code in en maak notities
- experimenteer (en maak meer notities)
- werk samen met anderen
- schrijf een 'handleiding tot feature X' als je iets moeilijk vindt
- monitor jezelf: welke stappen heb je goed gedaan. Wat heb je geleerd?
- als je meer wilt weten over zeg een methode of een begrip zoals 'klasse' en het boek is niet duidelijk (genoeg): google gerust!
- maar Google de eerste maanden niet naar probleemoplossende voorbeeldcode!

## Tradities en misverstanden

### 1. Misverstand 1: programmeren leer je door het te doen
Het klinkt logisch dat je leert programmeren door het te doen. En code schrijven is zeker onderdeel van het leren programmeren.

Een focus op 'programmeren, programmeren, programmeren' produceert echter twee problemen:

1) Alleen maar programmeren is niet effectief - het is sneller en beter om programmeren af te wisselen met andere studieactiviteiten- zelfs al zijn die misschien minder leuk.

2) focusen op het resultaat -"de opdracht moet af"- voelt vanzelfsprekend aan maar is even gevaarlijk als een wapperende rode doek voor een stier is. Het kan juist voorkomen dat je de vaardigheden opdoet die je voor een baan nodig hebt!

### Feit 1: Voor optimaal leren heb je verschillende activiteiten nodig

Het ideale leren is een mengsel van activiteiten.

#### 1.1 Reflectie/monitoren

Een studie waarin meisjes leerden darten vond dat het eindniveau hoger werd als de meisjes een soort logboek bijhielden van de resultaten en/of welke dingen goed waren gegaan/welke stappen ze goed hadden gedaan ("[Developmental Phases in Self-Regulation: Shifting From Process Goals to Outcome Goals](https://www.researchgate.net/publication/232582156_Developmental_phases_in_self-regulation_Shifting_from_process_to_outcome_goals_Journal_of_Educational_Psychology_89_29-36)". De auteurs citerend: _"It was also found that self-recording, a formal form of self-monitoring, enhanced dart-throwing skill, self-efficacy, and self-reaction beliefs."_

#### 1.2 Lezen/luisteren en nadenken

_“Only a fool learns from his own mistakes. The wise man learns from the mistakes of others.”_ - Otto von Bismarck

Als je alleen maar programmeert raak je makkelijk in een soort bubbel-je leert niet van de ervaring van anderen. Dit is één van de redenen waarom we bij de C#-opleiding code-reviews hebben. Maar formele en informele feedback van anderen is niet de enige bron van leren over de mislukkingen (en ervaringen) van anderen. Van een goed Java-boek kan je sowieso methoden leren waar je zelf nooit (of pas over jaren) opgekomen zou zijn. En dan heb ik het nog niet eens over andere bronnen, zoals Martin Fowler's [Bliki ](https://martinfowler.com/bliki/), of 'klassiekers' zoals 'Code Complete 2' van Steve McConnell, 'The Pragmatic Programmer' van David Thomas en Andrew Hunt, 'Refactoring' en 'Patterns of Enterprise Applications' van Martin Fowler, 'Domain-Driven Design' van Eric Evans, en uiteraard Effective Java, 3rd Edition door Joshua Bloch. Misschien later zelfs boeken die breder naar het programmeren kijken, zoals Robert Glass' "Facts and Fallacies of Software Engineering" of (over het ontwerp van software die gebruikt moet worden) "The Inmates are Running the Asylum" van Alan Cooper.

Er zijn zeker meer beroemde boeken, zoals de boeken van Robert Martin (bijvoorbeeld 'Clean Code'). In het geval van Martin's boek: het kan enig nut hebben, maar ik vind ze redelijk dogmatisch en volgens mij heeft Martin zelf weinig belangrijke software geschreven - Fowler en McConnell hebben daarentegen heel veel praktijkervaring, dus raad ik mensen die werkende applicaties willen programmeren aan met hen te starten. Als je wat meer ervaring hebt kun je de soundbites van Martin beter in een praktisch perspectief plaatsen zonder je effectiviteit te verstikken met te ver doorgevoerde dogmas.

Boeken zijn meestal een solide basis die je in je eigen tempo kan doornemen; op een gegeven moment kan je voelen dat je basis goed genoeg is, en dat je informatie wilt die wat 'verser' is. In dat geval heb je een keuze uit videos die gratis op internet staan van vele programmeerconferenties: zelf ben ik vooral een fan van de [GOTO-conferenties](https://www.youtube.com/c/GotoConferences/videos), maar [NLJUG](https://www.youtube.com/c/nljug1) (de Nederlandse Java Users Group) en [Devoxx](https://www.youtube.com/c/Devoxx2015/videos) hebben vaak ook interessante dingen staan. Er zijn uiteraard meer conferenties, en ook gratis lokale meetups (via [meetup.com](https://www.meetup.com/nl-NL/), dus gelegenheden om je online en 'on site' te ontwikkelen zijn er meer dan voldoende als programmeur. Al kan het zijn dat je zonder een solide basis -die je van zelf programmeren en het grondig doorwerken van boeken krijgt) bij de eerste conferenties nog met je oren staat te klapperen!

Uiteraard helpt alleen 'boekenwijsheid' je niet om een baan te krijgen-je moet ook kunnen programmeren- maar als je leest en nadenkt zul je vast dingen zien die je kunt toepassen om beter en sneller en effectiever te programmeren. Aan de andere kant zullen je ervaringen bij het programmeren zelf er ook voor zorgen dat je de lessen van een boek beter begrijpt en langer onthoudt.

#### 1.3 Aantekeningen maken en experimenteren

Ik zal later terugkomen op aantekeningen maken en experimenteren, maar zeker als je iets leest is het nuttig aantekeningen ervan te maken, in je eigen woorden - dus zonder het boek over te schrijven. Dat kunnen korte aantekeningen zijn, het is vooral belangrijk dat je zeker weet dat kennis tenminste door je hersens heengaat. Want iedereen kan pagina's omslaan, maar daar heb je niets aan. Ook als je vastloopt met een programma of een rare bug: aantekeningen maken kan helpen, zowel om van te leren als om de bug op te lossen. Bij bugs -om een of andere reden-lijkt het mij vaak beter te helpen aantekeningen op papier te schrijven, bij gewoon kennis opdoen vind ik het praktischer dingen gelijk in een computerbestand te zetten.
Ik noem hier ook nadrukkelijk 'experimenteren'. 'Begrijpen/verklaren' is vaak gemakkelijk, net zoals de 'Geloofsovertuigers' die ik ooit sprak alles konden verklaren. Waarom is de lucht blauw? Omdat God het zo gewild heeft. Waarom is water nat? Omdat God het zo gewild heeft. Maar zo kun je uiteraard alles verklaren - maar ik betwijfel of dat zou helpen met programmeren. Je begrijpt iets pas echt als je het kunt _voorspellen_. Dus als je denkt dat je iets begrijpt, maak code waarin je het toepast. Als je dat kan laten werken (en kan laten breken) begrijp je het!

#### 1.4 Met anderen samenwerken

Met anderen praten is ook waardevol om beter te leren programmeren. Liefst  praat je uiteraard met meer gevorderde programmeurs, maar klasgenoten kunnen ook al leerzaam zijn-iedereen weet/ziet weer iets anders. Bovendien leer je ook dingen door ze zelf uit te leggen. Peer reviewen en peer-gereviewd worden, samen pair-programmeren aan een project... allebei nuttig. Zeker omdat een boek of youtube-video of pluralsight-cursus niet ziet waar je kunt verbeteren, maar een andere programmeur wel!

#### 1.5 Programmeren

Programmeren is natuurlijk ook belangrijk! Ik ken helaas geen wetenschappelijk onderzoek over hoeveel je moet programmeren versus moet lezen, maar zelf zou ik als ik in een opleiding zat ongeveer een lees : programmeer-verhouding nastreven van 1 op 2 (of 1 op 3), en minstens 100 regels code per dag willen schrijven. Of dat lukt is uiteraard ook afhankelijk van de opdrachten die jullie krijgen, het is niet de bedoeling dat jullie je thuis overspannen werken. Wees liever als de schildpad die iedere dag een stapje neemt dan als de haas die in de haast de snelweg neemt en overreden wordt.

#### 2. Focusen op het resultaat in plaats van op het leren programmeren is gevaarlijk

Ik heb met deelnemers gemerkt dat er een paar valkuilen zijn met programmeeropdrachten, zeker met opdrachten die niet 'licht lastig' maar gewoon veel te moeilijk zijn voor wat een deelnemer op dat moment kan. Zulke opgaven zijn helaas meestal geen zinvolle tijdsbesteding, want:

  - òf mensen zijn urenlang bezig naar het scherm te staren terwijl ze bitter weinig regels code produceren
  
  - òf mensen zoeken het antwoord op op Google en copy-pasten het. 
  

Bij de eerste methode leer je weinig per tijdseenheid wegens het lage volume code dat je produceert, bij de tweede leer je weinig wegens de lage hoeveelheid actief denkwerk dat je doet. Het tweede is een beetje te vergelijken met de vele 'kookboekpractica' die ik als scheikundestudent heb gedaan, waarvan ik bijna alles vergeten ben behalve dat ene practicum waarbij ik zelf mocht kiezen welke materialen ik gebruikte.

Het dartsonderzoek dat ik hierboven citeerde liet ook zien dat meisjes die begonnen met een 'procesoriëntatie' (de arm goed houden, enzovoorts) uiteindelijk veel hoger scoorden dan meisjes die moesten bijhouden wat hun score was. Ja, voor de allerbeste uitkomst moesten de meisjes na een tijd 'procesoriëntatie' overschakelen op een 'doeloriëntatie' voor de beste uitkomst, maar jij hoeft dat de eerste maanden bij ITvitae zeker niet te doen!

Het kan helpen aan het eind van de dag te tellen hoeveel regels code je hebt geschreven en hoeveel regels aantekeningen je hebt gemaakt, en dat ergens bij te houden - naast noteren wat je geleerd hebt en waar je trots op bent. Als je blijft leren en blijft programmeren-zelfs al is dat nu nog 'simpele' code, zul je waarschijnlijk ver voor het einde van het ITvitae leertraject veel beter en sneller programmeren dan mensen die hun weekopdrachten 'bij elkaar googelen'. Je hebt hier tijd! Probeer die te gebruiken om goed te leren...


### 2. Programmeren leer je via Google: alles is te vinden
Ja, je kunt alles vinden op internet. Maar allereerst, zoals ze zeggen "Het internet heeft alles behalve kwaliteitscontrole." Ten tweede: als je geen basiskennis hebt weet je vaak niet waar je moet zoeken. Ten derde weet je normaal niet wat je niet weet! Vaak is het volgen van een enkele (uitgebreide) youtube-cursus of een goed boek (al dan niet gratis) beter, als je daar alles van begrijpt ben je klaar om je kennis aan te vullen met Google.

## Een opmerking over de verdere tekst

Hieronder zal ik een aantal codevoorbeelden geven, vooral uit een beginnersboek voor Java. Zeker als je nog nooit geprogrammeerd hebt zal mijn uitleg over de code soms wat onduidelijk zijn. Maar dit is ook niet bedoeld als programmeerleerboek - daar heb je boeken met honderden pagina's voor. Onderstaande is bedoeld als illustratie van een aantal studietechnieken die in elk geval mij helpen. De code is alleen om een aantal technieken (aantekeningen maken enzo) te demonstrerern. Zolang je maar ongeveer begrijpt welke leertechnieken ik gebruik is precies begrip van de code overbodig - om code goed te leren begrijpen - daar zijn aparte en veel uitgebreidere boeken, videos, internetcursussen en andere materialen voor!



## Mijn huidige advies

### Begin met een boek
Stap 1: koop een goed boek voor de programmeertaal die je wilt leren. Bij Java kan ik "Learning Java: An Introduction to Real-World Programming with Java" van Loy, Niemeyer en Leuck aanbevelen, of anders Core Java Volume 1 door Horstmann. Voorzover ik uit onderzoek begrijp is voor de meeste mensen een boek beter dan een e-book, zelfs al is een boek voor velen minder prettig; het lijkt eraan te liggen dat mensen boeken langzamer lezen dan e-boeken, maar er wel meer van onthouden. Bovendien is het veel praktischer een boek met voorbeeldcode naast je scherm te zetten dat telkens van window te moeten wisselen. Maar een e-book is beter voor andere mensen, dus hoewel ik sterk een fysiek boek aanbeveel, zou het kunnen dat juist jij een persoon bent die beter omgaat met e-boeken.

Waarom geen youtube-video of audioboek? Dat heeft vooral te maken dat je in een boek sneller informatie kan opzoeken (inhoudsopgaven, en de index achterin) dan in een youtube-video of Udemy-cursus. En tijd besparen is handig! Ten tweede hebben boeken meestal een betere kwaliteitscontrole dan de gemiddelde video op internet. Natuurlijk, je kunt ook van videos veel leren als je echt geen geld hebt, maar als je tijd als geld beschouwt is een goed Java-boek, al is het 50 of 60 euro, zeker de moeite waard!

Dikkere boeken kunnen helaas intimiderend zijn, maar lees gewoon 5 bladzijden, al begrijp je er eerst niets van. Als je het echt niet begrijpt, ga dan een kwartier wat anders doen, en lees het dan opnieuw. Als je het dan nog steeds niet begrijpt, probeer het uit te leggen in zelf-geschreven tekst (bijvoorbeeld zoals je het zou schrijven aan een neefje/nichtje van 10 die slim is maar niets van programmeren weet). Lees het dan voor een derde keer door. Als je het dan nog niet begrijpt, lees het door voor het slapen gaan en dan nog eens de volgende dag.

Als je het dan echt nog steeds niet begrijpt, kijk gerust een introductievideo op youtube ofzo- maar de meeste boeken zijn zo goed geschreven dat ik niet verwacht dat zelfs een beginner meer dan 2 grondige lezingen nodig heeft om iets te begrijpen. 

### Probeer code uit 

Als je dus een boek hebt, en na het doorlezen van het algemene verhaal over de programmeertaal en hoe je een editor installeert begint met het eerste programma (ik heb hier bijvoorbeeld een oudere versie van Leuck's boek) dan begint het 'echte programmeren' op pagina 29, met 

```
public class HelloJava {
  public static void main(String[] args) {
    System.out.println("Hello, Java!");
  }
}
```

Dit intypen en runnen is handig als demonstratie. En typen is beter dan knippen en plakken, omdat je dingen verkeerd kunt overtypen en dus al leert nauwkeurig op te letten!

Run het, en dan komt de fase om er echt wat van te leren!

### Maak aantekeningen

Ik vind het erg handig aantekeningen van dingen te maken om later op te zoeken. Als programmeur heb ik daar uiteraard zelf een programma voor gemaakt, waar ik mezelf ook mee kan overhoren (https://github.com/EWLameijer/FermiEn als je een beetje handig bent met IntelliJ IDEA zou je kunnen proberen het zelf te installeren), maar een eenvoudig tekstbestand in zeg LibreOffice of Notepad++ of jouw favoriete editor kan ook goed werken, zolang je maar een zoek-functie tot je beschikking hebt!

Dat betekent overigens niet dat je zelf geen systeem mag bedenken of mag experimenteren met wat voor jou werkt. Ik kan zeggen dat mijn eigen leermethoden gemiddeld redelijk effectief waren (op mijn eindexamenlijst stond ik gemiddeld een 9.4, ik ben cum laude afgestudeerd aan de universiteit), dat ik zelf ook heb geëxperimenteerd met leermethoden (snellezen was geen succes, een encyclopedie maken over een onderwerp en daar elke avond stukjes van doorlezen lijkt echter te hebben bijgedragen aan het krijgen van een scriptieprijs), en dat ik doordat ik ooit een blog over onderwijs schreef het een en ander weet over wat wetenschappers hebben ontdekt over efficiënt leren (https://swimple.nl/page/blog/). Maar als jouw studiemethodes doorgaans goed genoeg werken is het natuurlijk niet erg om die te gebruiken. Al kan het nog steeds de moeite zijn af en toe met anderen die goed lijken te leren samen te leren en van hen en van boeken en internet af en toe een studietip uit te proberen.

Zelf zie ik de vaardigheid van het leren net als de vaardigheid van het programmeren: je kunt zelf of door instructie van een ouder, mentor of boek iets bedenken, maar het is iets waar je, als je er echt goed in wilt worden, met enige regelmaat mee experimenteert, erover leest/luistert, of samenwerkt met anderen die er ook goed in zijn of zelfs beter dan jou.

Hoe dan ook, ik lees eerst de tekst die de code begeleidt.

De uitleg van Leuck is als volgt:
-dit declareert een klasse, HelloJava
-en een methode genaamd 'main' 
-het gebruikt een 'predefined' method die 'println()' heet om tekst te schrijven

Ik maak aantekeningen:

<div style="background-color:lightgreen">
-klasse: je maakt een klasse met "public class Classname", bijvoorbeeld "public class HelloJava" 
</div>

Uiteraard, als je meer van programmeren weet, herken je 'public', en zou je de aantekening maken als: 

<div style="background-color:lightgreen">
-klasse: in Java definieer je een klasse met class Classname; classname is in UpperCamelCase, 
en een class kan public zijn (is dat een goede default?)</div>

Het gaat er niet om dat de aantekening perfect of alwetend is, je schrijft gewoon op wat je nu begrijpt, je kan het altijd later corrigeren.

Zo maak ik ook meer aantekeningen. Ikzelf zou (na 30 jaar programmeerervaring) vergelijkingen trekken met programmeertalen die ik al ken, maar voor een eerste programmeertaal kan ik me voorstellen dat je zelf eenvoudigere aantekeningen maakt, zoals onderstaande:

<div style="background-color:lightgreen">
-methode: wordt gedefinieerd als public static void main(String[] args) { ...}. 
Vragen: moet een methode altijd in een klasse zitten? En wat betekenen 
public, static, void, main, String[] en args?

-afbeelden tekst: System.out.println(tekst); bijvoorbeeld System.out.println("Hello, Java!"); 
Okee, dus een aanroep van een methode wordt vaak voorafgegaan door andere woorden met puntjes 
ertussen? En een regel - in elk geval van 'echte code', niet die van de klasse- of 
methode-declaratie, eindigt met ';'?
</div>

### Probeer het uit en experimenteer

Ik heb nu de Hello world-gemaakt. Nu sla ik de broncode op in een ander tekstbestandje (bijvoorbeeld 'voorbeeldcode.txt') en ga ik zelf experimenteren.

Eerst probeer ik dingen helemaal na te apen op dingen na waarvan ik zeker weet dat het 'veilig' is ze te veranderen. Ik maak dus iets als (na compilerwaarschuwingen als ik iets fout heb gedaan) 
```
public class Main {
    public static void main(String[] args) {
        System.out.println("Hallo Wubbo!");
    }
}
```

Nu ga ik experimenteren. Mag ik bijvoorbeeld de klassenaam Main veranderen in MyClass?

Nee, dan zegt de compiler iets als <div style="background-color:red">class MyClass is public and should be defined in a file called MyClass.java</div>

Dus ik update de aantekening van klasse

<div style="background-color:lightgreen">
-klasse: in Java definieer je een klasse met class Classname; classname is in UpperCamelCase,
 en een class kan public zijn (is dat een goede default?). Als een klasse public is, moet 
 de naam van de klasse hetzelfde zijn als de filenaam, dus in een public class MyClass moet 
 in een file genaamd MyClass.java staan. 
</div>

Ik probeer de file te hernoemen naar MyClass.java, maar krijg dan <div style="background-color:red">"Error: Could not find or load main class main.Main"</div> Dat kan ik nog even niet oplossen, dus ik breid de aantekening uit.

<div style="background-color:lightgreen">
-klasse: in Java definieer je een klasse met class Classname; classname is in UpperCamelCase,
 en een class kan public zijn (is dat een goede default?). Als een klasse public is, moet 
 de naam van de klasse hetzelfde zijn als de filenaam, dus in een public class MyClass 
 moet in een file genaamd MyClass.java staan. Moet de main klasse altijd Main heten of 
 heb ik iets verkeerds ingesteld?
</div>

Dan kan ik proberen de _methode_ om te noemen naar Main, of `static` weg te halen. Hoe dan ook krijg ik dingen als:

<div style="background-color:red">Main method is not static in class main.Main, please define the main method as: public static void main(String[] args)</div>

en 

<div style="background-color:red">Error: Main method not found in class main.Main, please define the main method as: public static void main(String[] args)</div>

Ik pas mijn aantekeningen aan: 

<div style="background-color:lightgreen">
-methode: wordt gedefinieerd als public static void main(String[] args) { ...}. 
Vragen: moet een methode altijd in een klasse zitten? En wat betekenen 
public, static, void, main, String[] en args? Kennelijk moet er in een programma
 altijd een public static void main(String[] args)-methode zitten!
</div>

Ik haal de puntkomma weg, en krijg de foutmelding "`; expected`". Ik pas mijn aantekeningen opnieuw aan:

<div style="background-color:lightgreen">
-afbeelden tekst: System.out.println(tekst); bijvoorbeeld 
System.out.println("Hello, Java"!); Okee, dus een aanroep van een methode wordt
 vaak voorafgegaan door andere woorden met puntjes ertussen? En een regel - 
 in elk geval van 'echte code', niet die van de klasse- of methodedeclaratie, 
 eindigt met ';' (dat lijkt te moeten, getest 20220826)
</div>

Afhankelijk van hoe ik me voel zou ik verder kunnen experimenteren (kan ik een _tweede_ println in de methode zetten? Of wat gebeurt er als ik lege regels tussenvoeg?), of ik besluit door te gaan met het volgende programma in het boek. Hoe dan ook, als ik een 'origineel nieuw programma' heb gemaakt, in mijn geval dus 'Hallo Wubbo!', ben ik tevreden. Ik kopieer het programma naar mijn programma-archief, en lees verder.

### Wat als ik een concept moeilijk vind?
Ik merk zelf dat als ik een concept nogal lastig vind, het me helpt in mijn encyclopedie een stappenplan te maken van hoe je iets moet toepassen, een beetje als een recept. 

Bijvoorbeeld: 
<div style="background-color:lightgreen">
-window maken: als ik een window wil maken in Java moet ik het volgende doen:

1) Ik definieer een window door een JFrame te maken, JFrame frame = new JFrame("Hello, Java!"); 
2) Ik geef het een grootte: frame.setSize(300, 300);
3) Ik maak het zichtbaar: frame.setVisible(true);
</div>

Uiteraard zou ik dit dan uitproberen en experimenteren. Daarbij zou ik mijn aantekeningen uitbreiden als volgt:

<div style="background-color:lightgreen">
-window maken: als ik een window wil maken in Java moet ik het volgende doen:

1) Ik heb kennelijk een 'import javax.swing.*;' nodig bovenaan mijn file. 
Maar die wordt al aangemaakt door de editor. Dat is dus nodig voor de JFrame?
2) Ik definieer een window door een JFrame te maken, 
JFrame frame = new JFrame("Hello, Java!"); De "Hello, Java" verschijnt in de 
bovenbalk van het window, links van de maximalisatie en sluit-ikoontjes
3) Ik geef het een grootte: frame.setSize(300, 300); // als ik geen 
setSize doe wordt het een miniscuul windowtje, alleen een zo klein 
mogelijke titelbalk, geen inhoud. Het eerste getal is de breedte, het tweede
 getal de hoogte van het scherm
4) Ik maak het zichtbaar: frame.setVisible(true); // als ik dit niet doe zie
 ik helemaal niets! Lijkt een stomme default-waarde - waarom die extra code
 intypen, waarom zou je een window aan willen maken dat onzichtbaar is? 
 (okee, tenzij je niet wilt dat de gebruiker ziet hoe je het inricht. 
 Maar voelt toch als overdreven aan...)
</div>

Nu zijn er uiteraard een boel reserve-strategieën, van het doorlezen van de beschrijving voordat je naar bed gaat, of iemand anders (bijvoorbeeld op een Java-Discord-kanaal) om uitleg vragen, of het aan een rubber eendje of je kamerplant uitleggen, of een tekening maken, of je afvragen waarom een bepaalde constructie sowieso in de programmeertaal is - van alles in een programmeertaal kan je redelijkerwijs aannemen dat het ergens nuttig voor moet zijn. Maar een stappenplan/voorschrift lijkt in elk geval mijzelf in de praktijk het beste te helpen.

### Codedrills
Mogelijk heb je al gemerkt dat zelfs als je programma's kan begrijpen en uiteindelijk - na veel tijd en veel gezeur van de compiler- goed kan krijgen, dat het proces zeker in het begin moeizaam zal gaan. 

Dat is niet erg, en het is een natuurlijke fase van het leerproces (specifiek het verplaatsen van de hersenbelasting van het krappe werkgeheugen naar het enorme 'long-term working memory'). Hoe meer code je schrijft, hoe makkelijker het gaat. Maar wat als het een uur duurt voordat je 6 regels code hebt geschreven? 

Voor deze situatie heb je code-drills. Daar begin je meestal niet mee bij hoofdstuk 1 van een boek, maar pas als je programma's overtypt (en zelf kan typen) die complexer zijn dan 'hello world'. Bijvoorbeeld als je aan de slag gaat met variabelen, methoden naast de hoofdmethode, if-statements, enzovoorts.

Ik zal een concreet voorbeeld geven.

In de oude versie van het Leuck-boek, dat ik hier heb, begint op pagina 50 met een groter programma, met meerdere methoden, . Dat ik hieronder (om zelf ook het goede voorbeeld te geven), over heb getypt.

```
// file: HelloJava3.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HelloJava3 {
  public static void main(String[] args) {
    JFrame frame = new JFrame("HelloJava3");
    frame.add(new HelloComponent3("Hello, Java!"));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 300);
    frame.setVisible(true);
  }
}

class HelloComponent3 extends JComponent implements MouseMotionListener, ActionListener {
  String theMessage;
  int messageX = 125, messageY = 95; // Coordinates of the message 
  
  JButton theButton;
  
  int colorIndex; // current index into someColors
  static Color[] someColors = { Color.black, Color.red, Color.green, 
    Color.blue, Color.magenta };
    
  public HelloComponent3(String message) {
    theMessage = message;
    theButton = new JButton("Change Color");
    setLayout(new FlowLayout());
    add(theButton);
    theButton.addActionListener(this);
    addMouseMotionListener(this);
  }
  
  public void paintComponent(Graphics g) {
    g.drawString(theMessage, messageX, messageY);
  }
  
  public void mouseDragged(MouseEvent e) {
    messageX = e.getX();
    messageY = e.getY();
    repaint();
  }
  
  public void mouseMoved(MouseEvent e) {}
  
  public void actionPerformed(ActionEvent e) {
    // Did somebody push our button?
    if (e.getSource() == theButton) changeColor();
  }
  
  synchronized private void changeColor() {
    // Change the index to the next color, awkwardly
    if (++colorIndex == someColors.length) colorIndex = 0;
    setForeground(currentColor());
    repaint();
  }
  
  synchronized private Color currentColor() {
    return someColors[colorIndex];
  }
}
```

Okee, dat was wat typewerk (9 minuten voor mij), wat niet automatisch leuk is, maar het gaat om het leren! Mogelijk komen door het overtypen van de code automatisch wat Java-patronen in mijn hoofd. 

Maar belangrijker zijn mijn eigen aantekeningen, waar ik op dit punt meerdere pagina's van zou hebben. Hoe dan ook, op dit moment zou ik weten hoe je klassen maakt, hoe je je eigen methodes maakt en aanroept, en hoe je naar het scherm schrijft. Helaas is er nog niets behandeld over hoe je naar het scherm leest, en er staat in het Leuck-boek (in elk geval de oude versie) geen duidelijke verwijzing naar keyboard of input. Dus ik 'speel even vals' en Google: "java input keyboard". De bovenste hit is één van "[Stack Overflow](https://stackoverflow.com/questions/17538182/getting-keyboard-input)", het bovenste antwoord daarvan geeft als voorbeeld: 
```
import java.util.Scanner;

Scanner keyboard = new Scanner(System.in);
System.out.println("enter an integer");
int myint = keyboard.nextInt();
```

Ik maak een aantekening, met waarschuwing (ik gebruik meestal @ voor waarschuwingen, makkelijk te vinden)
<div style="background-color:lightgreen">
keyboard input: @! -mogelijk niet de beste manier, Stack Overflow!
 (https://stackoverflow.com/questions/17538182/getting-keyboard-input). 
Mogelijk kom ik later iets beters tegen... Eerst Scanner-object maken en
 initialiseren met System.in, dan met een nextInt() (of zoiets) iets inlezen. 
 Dus Scanner keyboard = new Scanner(System.in); int myint = keyboard.nextInt();
</div>

Hoe dan ook, rond dit punt heb ik voldoende aantekeningen en bouwstenen om zelf kleine programma's te schrijven. Zelf kies ik console apps (dus met System.out.println) omdat ik dan veel minder hoef te typen dan bij het maken van een window. Ik maak een nieuw project (IntelliJ IDEA > New> Project > Gradle > SDK 17 + Java> probjectnaam: Drills), maak in de src/main/java folder een nieuwe file aan (Drills.java), met daarin het volgende:
```
public class Drills {
    public static void main(String[] args) {
        System.out.println("Begin drills!");
        System.out.println("Eindig drills!");
    }
}
```

Ik run het, en het gaat wel goed, maar er verschijnt een boel oninteressante Gradle-informatie op het scherm. Ik ken toevallig een oude programmeurstruc (jullie vanaf nu hopelijk ook): wacht tot de gebruiker op een toets heeft gedrukt. In Java (ik google weer) gaat zoiets (redelijk) met System.in.read(). Die helaas een exceptie nodig heeft, die IDEA vriendelijk aan het begin van de methode plakt. Ik heb nu
```
import java.io.IOException;

public class Drills {
    public static void main(String[] args) throws IOException {
        System.out.println("Begin drills!");
        System.out.println("Eindig drills!");
        System.in.read();
    }
}
```

Het is wat veel code, maar werkt zoals gewenst. En nu kan ik met het echte werk beginnen!

Ik drill de onderdelen die ik tot nog toe heb geleerd. Eerst drie keer een System.out.println
```
public class Drills {
    public static void main(String[] args) throws IOException {
        System.out.println("Begin drills!");

        System.out.println("Het hemelsche gerecht heeft zich ten lange lesten");
        System.out.println("Erbarremt over my en mijn benaeuwde vesten");
        System.out.println("En arme burgery, en op mijn volcx gebed");
        System.out.println("En dagelix geschrey de bange stad ontzet.");

        System.out.println("Eindig drills!");
        System.in.read();
    }
}
```

Dat werkt, het is wel een heel brok tekst, ik experimenteer wat (en zoek online op) en voeg wat "\n"s toe aan de printlns. En noteer het ook in mijn aantekeningen over System.out.println!

```
public class Drills {
    public static void main(String[] args) throws IOException {
        System.out.println("Begin drills!\n");

        System.out.println("Het hemelsche gerecht heeft zich ten lange lesten");
        System.out.println("Erbarremt over my en mijn benaeuwde vesten");
        System.out.println("En arme burgery, en op mijn volcx gebed");
        System.out.println("En dagelix geschrey de bange stad ontzet.");

        System.out.println("\nEindig drills!");
        System.in.read();
    }
}
```

Mooi. Nu doe ik iets met variabelen. We hebben al String en int gehad, en die probeer ik nu te mixen. Ik verplaats de lijnen van Gijsbrecht naar mijn archief-file om de code schoner te maken, en begin:

```
public class Drills {
    public static void main(String[] args) throws IOException {
        System.out.println("Begin drills!\n");

        int myHouseNumber = 8;
        String myName = "Bill Sykes";
        String longString = "1 2 3 4\n hoedje van hoedje van\n1 2 3 4\nhoedje van papier";
        
        System.out.println("\nEindig drills!");
        System.in.read();
    }
}
```

Wel, dit is leuk, maar ik moet ook checken dat het werkt! Dus weer een paar printlns (en probeer die ook op de int!)

overigens: pro-tip voor IDEA-gebruikers: "sout" intypen en dan TAB produceert een System.out.println() . Dat scheelt typewerk!

```
public class Drills {
    public static void main(String[] args) throws IOException {
        System.out.println("Begin drills!\n");

        int myHouseNumber = 8;
        System.out.println(myHouseNumber);
        String myName = "Bill Sykes";
        System.out.println(myName);
        String longString = "1 2 3 4\nhoedje van hoedje van\n1 2 3 4\nhoedje van papier";
        System.out.println(longString);

        System.out.println("\nEindig drills!");
        System.in.read();
    }
}
```

Dat werkt ook!

Het laatste dat ik nu wil gaan drillen zijn methoden, ik check mijn aantekeningen dat methoden een returntype hebben, en ze kunnen ook 'argumenten' hebben (de `String message`, bijvoorbeeld). Ik save eerst de variabelen naar mijn archief-file, en maak een methode aan. Eerst een eenvoudige, zonder argumenten en zonder returntype. 

```
public class Drills {
    public static void main(String[] args) throws IOException {
        System.out.println("Begin drills!\n");
        
        System.out.println("\nEindig drills!");
        System.in.read();
    }

    void helloMethod() {
        System.out.println("Hello, method!");
    }
}
```

Dat compileert. Hem nu nog aanroepen... Waarbij de compiler aangeeft dat hij static moet zijn. Afijn...
```
import java.io.IOException;

public class Drills {
    public static void main(String[] args) throws IOException {
        System.out.println("Begin drills!\n");

        helloMethod();

        System.out.println("\nEindig drills!");
        System.in.read();
    }

    static void helloMethod() {
        System.out.println("Hello, method!");
    }
}
```

Dat werkt!

Ik maak voor de zekerheid nog drie methoden: één met een parameter maar zonder returnwaarde, één met een returnwaarde maar zonder parameter, en één met allebei.

```
import java.io.IOException;

public class Drills {
    public static void main(String[] args) throws IOException {
        System.out.println("Begin drills!\n");

        helloMethod();
        withParamNoReturn(16);
        String fromMethod = noParamWithReturn();
        System.out.println(fromMethod);
        String fromBigMethod = withParamAndReturn("Wouter Wilgebeen");
        System.out.println(fromBigMethod);
        
        System.out.println("\nEindig drills!");
        System.in.read();
    }

    private static String withParamAndReturn(String name) {
        System.out.println("Hallo, mijn naam is:");
        return name;
    }

    private static String noParamWithReturn() {
        return "Uit een methode!";
    }

    static void helloMethod() {
        System.out.println("Hello, method!");
    }

    static void withParamNoReturn(int n) {
        System.out.println("Het gekozen getal is:...");
        System.out.println(n);
    }
}
```

En dat werkt zoals verwacht. Als ik merk dat dit moeizaam gaat, zou ik het bijvoorbeeld de volgende dag opnieuw proberen.

### Tenslotte: probeer er lol in te houden

_"Get interested in programming, and do some because it is fun. Make sure that it keeps being enough fun so that you will be willing to put in your ten years/10,000 hours."_ - Peter Norvig, voormalig Director of Research, Google 

Programmeren is voor een belangrijk deel nadenken. En niemand kan je dwingen na te denken; je bent het effectiefst en creatiefst als je het iets zelf leuk en/of interessant vindt.
Wees daarom niet zo geobsedeerd met 'presteren' dat je jezelf dwingt 60 uur per week te studeren. Als je zin hebt, programmeer je meer. Als je geen zin hebt, programmeer je minder of niet. Als je blijft oefenen, zul je een heel goede programmeur worden, het enige dat dat kan voorkomen is als je jezelf een burnout bezorgt, net zoals een wetenschapper die zichzelf dwong om alle avonden op het lab te blijven, maar daar geen carrièreboost door ondervond.

Dus probeer voldoende tijd te houden voor dingen die je leuk of zinvol vindt, of dat nou vrienden, familie, hobbies, vrijwilligerswerk of sporten is. Maak desnoods een lijst van dingen die het leven leuk of waardevol voor jou maken, of waarmee/waarin je voor anderen waardevol bent, zelfs al kan je niet (goed) programmeren - of zelfs als je geen baan hebt!

Aanvullend kan je proberen iets te doen als Isaac Newton, die een "Philosophical Notebook" bijhield van dingen die hij interessant vond - bijvoorbeeld in de natuurkunde. Dus een notitieboekje hebben over dingen die je interessant of raadselachtig vindt in het leven (of in programmeren) kan ook helpen je te inspireren om die gedeeltes te verkennen die jijzelf interessant vindt, dat je programmeert/leert uit vrijwilligheid en nieuwsgierigheid in plaats van uit plicht.


## Moet ik niet iets doen met _'spaced repetition'_?
Spaced repetition-verspreid leren- is mogelijk de krachtigste leermethode die er momenteel bestaat, zeker als je hem koppelt aan jezelf testen. Spaced repetition + zelf testen staat in de praktijk meestal gelijk aan flashcards.

Flashcards kun je uiteraard zowel op papier maken en bijhouden (zoek bijvoorbeeld op [Leitnerbox](https://www.vernieuwenderwijs.nl/leren-met-de-leitnerbox/)), maar meestal gebruik je electronische flashcards-als je ze zelf aanmaakt, wordt normaal [Anki](https://apps.ankiweb.net/) aanbevolen, hoewel ik als eigenwijze programmeur iets heb gemaakt dat [voor mij](https://github.com/EWLameijer/FermiEn) beter werkt dan Anki (of waar ik tenminste makkelijk aan kan sleutelen als mij iets niet bevalt!)

Maar de belangrijkere vraag is: helpen flashcards bij het leren van programmeertalen?

In mijn eigen ervaring (en ook die van anderen) kunnen flashcards uitstekend helpen bij het efficiënter leren van natuurlijke talen. En dingen uit het hoofd leren voor het OCA-examen zou ik zeker ook met flashcards doen. 

Maar (en ik heb het uitgeprobeerd met Haskell en C# en wat JavaScript) lijken in elk geval voor mij flashcards minder goed te werken voor programmeertalen. Van programmeertaalterm naar betekenis is - omdat programmeertalen over het algemeen gewone Engelse woorden zijn - doorgaans nogal trivial. Ook jullie hebben waarschijnlijk geen flashcards nodig om te onthouden dat System.out.println() iets naar het scherm uitprint ('print line'). Aan de andere kant, van concept naar programmeertaalterm zit vaak wel na een aantal keren doen in je hoofd, of de IDE helpt je met suggesties. Als `frame` bijvoorbeeld een JFrame is, kun je "frame." intypen, en er verschijnt een hele lijst met wat je met het JFrame kan doen. En als iets zeldzamer is, is er doorgaans [officiële documentatie](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html) die over het algemeen duidelijk is, en als iets onduidelijk is kun je het normaal gewoon in een (test)programma uitproberen of verdere informatie vinden op internet.

Maar, zoals de Engelsen zeggen 'your mileage may vary': als jij baat blijkt te hebben van flashcards ook voor het leren van programmeertermen - probeer het! 


## Mag ik echt geen Google gebruiken?

Toen ik leerde programmeren (rond 1988) was er nog geen Google. Ook geen internet, alles moest uit boeken en tijdschriften. Er waren misschien 'hoe leer ik programmeren'-videos, maar die ben ik indertijd niet tegengekomen. Mogelijk ook omdat de computer in die tijd vaak de televisie als beeldscherm gebruikte, wat dergelijke leermiddelen onpraktisch kon maken!

Ik heb niet de nostalgie naar die 'goede oude tijd' dat ik dat nieuwe generaties ook aan wil doen. Google (en DuckDuckGo e.d.) zijn tijdsbesparend en waardevol! Ik gebruik ze ook af en toe als ik iets relatief zeldzaams op moet zoeken (bijvoorbeeld hoe ik een tabel moet maken in Swing).

Echter, je bent hier om te leren programmeren, en beroepsprogrammeurs zouden te traag werken als ze alles bij elkaar zouden moeten googelen. Iets uit je hoofd kunnen is altijd sneller dan Google (en hopelijk vrij van irritante advertenties!) Ja, soms zit je als programmeur de hele dag op Google, zeker als je een senior-programmeur bent die een nukkig framework moet opzetten. Maar normaal zou slechts 10-20% van je tijd Google moeten zijn.

Bij de opleiding bij ITvitae krijg je als het goed is voldoende lesmateriaal dat je de eerste maanden geen Google hoeft te gebruiken, hoogstens als je toelichting zoekt over een bepaalde begrip of methode.

_Ideaal google je de eerste zes maanden echter NIET naar codevoorbeelden om een probleem op te lossen._ Dat betekent meer inspanning (je hersenen moeten meer nadenken, in het geheugen graven, en dingen in (e)-boeken opzoeken, maar dat zijn vanuit het opzicht van jouw opleiding juist 'desirable difficulties' waar je veel meer van leert dan van 'easy shortcuts'. Na zes maanden van dergelijke inspanning is je basiskennis als het goed is zo stevig dat je dan zowel effectiever kunt googelen als goede van minder goede antwoorden kan onderscheiden als kunt onthouden wat je copy-paste en het kan aanpassen als het -zoals vaak het geval is- niet helemaal past bij je probleem.

Lukt het echt niet zonder Google en er is geen boek of docent of collega of iemand op een Java-Discord-server die dingen kan uitleggen? In dat geval: je MAG googelen, maar noteer de code op een blaadje, en zorg ervoor dat je aan een docent of mede-student kan uitleggen wat je hebt gedaan en waarom, en zorg ervoor dat je elke dag uit het hoofd de code probeert uit te schrijven tot je het drie keer goed hebt gedaan. Dan heb je waarschijnlijk zelfs met Google voldoende 'desirable difficulties' (en ook voldoende ontmoediging om voor het geringste wissewasje gelijk naar Google te grijpen...)

Want vergeet niet: jouw toekomstige werkgever is niet geïnteresseerd in je 'produktie' hier bij ITvitae-ze zullen vast niet jouw PhoneShop of iets dergelijks willen overnemen. Maar als je een grondig inzicht hebt in Java en de belangrijkste frameworks, vlot kan programmeren, en ideaal nog een redelijke aardige collega bent, dan zullen ze erom staan te springen jou je carrière bij hen verder te laten opbouwen!

## Boeken lezen en aantekeningen maken is zwaar/saai/minder leuk dan CodeWars
Soms is er een leuk boek, maar net zoals gewichtheffers hard moeten zweten om sterker te worden is een efficiënt en effectief leerproces niet altijd leuk. Onderwijsonderzoekers hebben het vaak over 'desirable difficulties' - als iets makkelijk is, is het vaak niet erg leerzaam!

Dat wil uiteraard niet zeggen dat je je overspannen moet werken, ik zou zelf lezen/aantekeningen maken beschouwen als intervaltraining in de sport: doe het eerst 5 minuten, of 10 minuten, of 15 minuten, neem dan een minuut of twee pauze en ga dan een half uur experimenteren en/of met codewars spelen of aan een eigen programma werken. En dan misschien weer die 5 tot 15 minuten lezen/aantekeningen maken, dan weer ontspannen...

Houd ook in gedachten dat zelfs de dikste boeken eindig zijn, na een week of een maand of drie maanden heb je je eerste goede Java-boek grondig doorgewerkt, en dan hoef je zowel minder te lezen per dag en is je 'leerconditie' zo goed dat het bestuderen van aanvullend materiaal (of dat nou over Spring of JavaScript of Maven is) relatief makkelijk is geworden.

## Maar ik heb geen tijd...

Het kan gebeuren dat aan het begin de weekopdrachten voor jou teveel zijn om in een week af te krijgen. Beschouw dat niet als een persoonlijk falen, maar als een normaal gevolg van de grote diversiteit in ervaring en voorkennis die mensen hebben: als we een lesprogramma zouden hebben dat door iedereen gemakkelijk bijgehouden zou worden, zouden andere mensen weer veel minder leren dan ze aankunnen, met daardoor verminderde baankansen...

Als je voelt dat je achterloopt zijn er een paar dingen die weliswaar relatief vaak worden gedaan door andere deelnemers, maar die niet aan te raden zijn:

1) op de niet-lesdagen, in het weekend en 's avonds extra hard doorwerken in de hoop de opdrachten af te krijgen. 

2) door Googelen en aan begeleiders/docenten/mededeelnemers code te vragen de weekopdracht bij elkaar sprokkelen.

Zoals je misschien kan raden is (1) geen goed idee omdat iedereen  voldoende vrije tijd, ontspanning en slaap nodig heeft. En als je er mogelijk overspannen door wordt, of een burnout of depressie van krijgt, of 'alleen maar' gedemotiveerd raakt - dat kan gewoon een einde zijn van je opleiding en je programmeercarrière, zonder echt goede reden. Maar er is ook nog een andere reden waarom (1) geen goed idee is: programmeren is niet vooral typewerk; als je niet uit een opdracht komt komt dat niet omdat je niet snel genoeg kunt typen; meestal is het probleem eerder dat je òfwel (nog) niet goed bent in het ontwerpen van code, òfwel dat je kennis mist van de programmeertaal of het framework of een van de tools. Als je designkennis mist moet je juist eerst een boel eenvoudige projectjes afmaken en/of code gaan lezen, en als je programmeerkennis mist moet je eerst met boeken, videos, quizzes, aantekeningen, en docenten/begeleiders/mededeelnemers uithoren aan de slag.

En zoals je mogelijk al vermoedt leer je bijna niets door de tweede optie.

Dus als je merkt dat je achter gaat lopen: overleg met de docenten/begeleiders over een plan van aanpak, en maak je niet druk over de weekopdrachten: wat je nu twee weken kost, kost over een maand maar een dag ofzo; de tijd 'verloren' aan studie en testprogramma's maken zul je dubbel en dwars inhalen!


## Eigen projecten?

Is een eigen project een goed idee? 

Een eigen project klinkt fantastisch, maar in mijn ervaring tot nog toe met deelnemers is een eigen project niet universeel verstandig.

Een eigen project/eigen idee is heel goed voor de motivatie en kan heel leerzaam zijn - als het niet te moeilijk voor je is. En beginnende programmeurs hebben (noodzakelijkerwijs) door gebrek aan ervaring nog geen bijster goede 'kalibratie', vaak onderschatten ze hoe moeilijk een project is. Dan krijg je het fenomeen van uren naar het scherm staren, of erger-hun zelfvertrouwen verliezen, al is dat even irrationeel als een beginnende fitnesser die opgeeft als blijkt dat hij na de eerste les nog steeds geen eikenboom om kan duwen.

Als je een eigen project wilt, heb ik twee adviezen:
1) 'timebox het', zet een deadline van zeg maximaal 0.5 tot 1 uur per dag, de rest van de tijd is voor de lessen, het bestuderen van het boek, het maken en verbeteren van je aantekeningen, code-experimenten en codedrills. Uiteraard, als je echt een fatsoenlijk Java-leerboek van 500+ pagina's grondig bestudeerd, gedrilld en aantekeningen bij gemaakt hebt - dan kan de timebox van je eigen project uiteraard groter. Maar zeker in het begin: maak het niet te groot.

2) Als je vastloopt, probeer het project dan in kleinere stapjes en sub-doelen op te delen, tot je een subdoel ziet dat je wel makkelijk kan. Als je niet ziet _hoe_ je het kan opdelen, vraag dan een docent of medestudent (of desnoods een programmeur die je tegenkomt op een meetup), iemand met meer ervaring als programmeur is vaak ook beter in het opsplitsen van grote problemen in kleinere problemen!

## Adviezen van C#-deelnemers

1) Debuggen (stap voor stap het programma volgen) in een editor helpt ook met dingen begrijpen

2) Voor een boek heb je geen koptelefoon, WiFi, en electriciteit nodig. En je hoeft ook niet zo lang te zoeken naar het moment waarop iets belangrijks werd gezegd. 

3) _Als_ je copy-pastet, probeer elke regel van de gekopieerde code te begrijpen.

4) Begin bij het begin, ga stapsgewijs

5) Lessen helpen.

6) Weekopdrachten/oefenen ook.

7) PluralSight kan ook helpen maar let op je doelen; niet alles is interessant voor het bedrijfsleven.

8) Aan mede-deelnemers vragen stellen helpt.

9) Voorbeelden/voorbeeldcode is handig.

10) Internet kan ook helpen.

11) Vragen stellen helpt veel. En wacht niet te lang, anders ben je een hele dag kwijt.

12) Als je iets niet kan vinden met Google, vraag om de steekwoorden

13) Laat je niet demotiveren "ik ben echt waardeloos".

14) Geduld en uithoudingsvermogen zijn belangrijk. Programmeren is ervaring, je kunt het niet 1-2-3 leren. Het kan zijn dat je het niet in 1 x haalt, maar geef het dan niet op.

15) Schrijf veel op tijdens de lessen, en maak eventueel foto's.

16) Voor Java: codingbat.com/java .

17) Probeer voorbeeldcode te debuggen, of aan te passen.