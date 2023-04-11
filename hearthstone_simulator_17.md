## Stap 17: Unit tests!

Met de documentatie van IntelliJ (https://www.jetbrains.com/help/idea/junit.html#intellij) en
Baeldung (https://www.baeldung.com/junit-5) blijkt dat ik alleen maar een soort
simpele test hoef te maken in mijn
tests-folder, iets als

``` 
@Test
void lambdaExpressions() {
    List numbers = Arrays.asList(1, 2, 3);
    assertTrue(numbers.stream()
      .mapToInt(Integer::intValue)
      .sum() > 5, () -> "Sum should be greater than 5");
}
```

en dat IntelliJ dan automatisch alle dependencies toevoegt. Wel, dat werkt!

Nu alleen nog tests maken die werken op MIJN systeem!

Omdat er veel functionaliteiten zijn wil ik niet de hele code in testen omzetten; er zijn gewoon kwetsbare en minder
kwetsbare gebieden. Maar waar regressie optreedt, moet ik zeker testen!

In pseudocode:

- een charge minion moet zijn eerste turn kunnen aanvallen
- een niet-charge minion moet NIET zijn eerste turn kunnen aanvallen

Ik zou dit ingewikkelder kunnen maken, maar voor een eerste test is dit al goed genoeg. De vraag is alleen hoe ik dit
goed opzet: immers, spellen worden nu random gegenereerd.

Dus:

1. 