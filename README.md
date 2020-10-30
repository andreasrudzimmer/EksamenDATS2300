# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

Jeg har brukt git til å dokumentere arbeidet mitt. Jeg har 15 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1: Kopiert inn programkode som anbefalt i oppgaveteksten. Derretter gjorde jeg endringer så forelder fikk riktig verdi i hver node. ( La til if else ). Gjorde egne tester i Main metode, hvor alle besto. Derretter testet jeg det i EksamenTest. Denne består også. 

* Oppgave 2: Lagde if else uttalelser. If sjekker om verdien eksisterer, ved bruk av inneholder metoden. Dersom den ikke eksisterer vil den hoppe over innholdet i uttalelsen og returnere holder, som vil være lik 0. Dersom den eksisterer vil den gå gjennom en while-løkke. While-løkken vil bevege seg gjennom treet og sammenligne verdien som vi satt som input med verdiene i treet. Holder vil øke dersom løkken finner en lik verdi. Til slutt vil metoden returnere holder. 

* Oppgave 3: Lagde førstePostorden(Node <T> p). Metoden tar inn en p, som er roten i treet. Derretter vil metoden bevege seg mot den første verdien i postorden. Dette gjør den ved bruk av en while-løkke. Derretter vil første postorden bli returnert. Lagde nestePostorden(Node<T> p). Denne metoden tar inn en node p, og vi ønsker å finne neste node i postorden ved bruk av noden p. Bruker if uttalelse for å bevege oss oppover i treet, og bruker while-løkker for å bevege oss nedover. Til slutt returnerer vi neste verdi i postorden. 
  
* Oppgave 4: Lagde public void postorden(Oppgave <? super T> oppgave). I denne metoden går den gjennom verdiene i postorden ved bruk av en while-løkke og legger dem i oppgave.utførtOppgave. Derretter lagde jeg private void postordenRecursive(Node<T> p, Oppgave <? super T> oppgave). Her benyttes rekusjon for å legge verdiene til utførtOppgave. Metoden går gjennom alle verdiene frem til p er null, og treet er gjennomgått. 

* Oppgave 5: 
