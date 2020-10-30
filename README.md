# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

Jeg har brukt git til å dokumentere arbeidet mitt. Jeg har 20 commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.

* Oppgave 1: Kopiert inn programkode som anbefalt i oppgaveteksten. Den kopierte metoden tar inn en verdi som skal plasseres i treet, og bruker en while-løkke til å plassere den. Derretter gjorde jeg endringer så forelder fikk riktig verdi i hver node. ( La til if else ). Gjorde egne tester i Main metode, hvor alle besto. Derretter testet jeg det i EksamenTest. Denne består også. Antallet verduer i treet stiger gjennom antall++ og det blir også registrert at det er gjort en endring gjennom endringer++. Metoden returerer true da innlegget var vellykket.  

Oppgave 1: Warnings: 
"Return value of methodvis never used": Dette kunne vært en void metode, da return verdien aldri er bruket. 


* Oppgave 2: Lagde public int antall(T verdi). Lagde if else uttalelser. If sjekker om verdien eksisterer, ved bruk av inneholder metoden. Dersom den ikke eksisterer vil den hoppe over innholdet i uttalelsen og returnere holder, som vil være lik 0. Dersom den eksisterer vil den gå gjennom en while-løkke. While-løkken vil bevege seg gjennom treet og sammenligne verdien som vi satt som input med verdiene i treet. Holder vil øke dersom løkken finner en lik verdi. Til slutt vil metoden returnere holder. 

* Oppgave 3: Lagde førstePostorden(Node <T> p). Metoden tar inn en p, som er roten i treet. Derretter vil metoden bevege seg mot den første verdien i postorden. Dette gjør den ved bruk av en while-løkke. Derretter vil første postorden bli returnert. Lagde nestePostorden(Node<T> p). Denne metoden tar inn en node p, og vi ønsker å finne neste node i postorden ved bruk av noden p. Bruker if uttalelse for å bevege oss oppover i treet, og bruker while-løkker for å bevege oss nedover. Til slutt returnerer vi neste verdi i postorden. 

Oppgave 3: Warnings: 
Brukte k for å få while-løkken til å kjøre. Kanskje en dårlig måte å løse dette på. 
  
* Oppgave 4: Lagde public void postorden(Oppgave <? super T> oppgave). I denne metoden går den gjennom verdiene i postorden ved bruk av en while-løkke og legger dem i oppgave.utførtOppgave. Derretter lagde jeg private void postordenRecursive(Node<T> p, Oppgave <? super T> oppgave). Her benyttes rekusjon for å legge verdiene til utførtOppgave. Metoden går gjennom alle verdiene frem til p er null, og treet er gjennomgått. 

* Oppgave 5: Lagde serilize(). Metoden fyller opp en arraylist med verdier i rekkefølge fra nivåene, fra venstre til høyre. Metoden returnerer til slutt den nye listen. Derretter lagde jeg deserialize(). Her blir det lagd en ny klasse av EksamenSBinTre klassen. Jeg brukte den nye klassen og en for-loop med en lignende leggInn(), for å fylle treet. Metoden returnerer til slutt det nye treet. 

Oppgave 5: Warnings: 
Ikke body i if-uttlelse fordi jeg ønsket den skulle stoppe dersom h var lik null.


* Oppgave 6: I denne oppgaven lagde jeg fjern-metode, fjernAlle-metode og nullstill-metode. Fjern-metoden tar inn en verdi. Ved bruk av en while-loop leter den frem lignende verdi. Derretter er det enkelte if-else uttllelser som brukes til å sette pekeren riktig. Antallet verdier i listen blir -1 da gjennom antall--. Endringen blir også registrert gjennom endringer++. fjernAlle-metoden tar inn en verdi. Denne går gjennom en for-løkke og fjerner alle like verdier som inputen ved bruk av fjern-metoden. Den returnerer en teller som forteller hvor mange verdier som er blitt fjernet. Nullstill-metoden bruker hjelpemetoden nullstillHjelp. Hjelpemetoden traverserer treet og fjerner hver verdi ved bruk av fjern metoden. 

Oppgave 6: Warnings: 
" 'for' loop replaceable with enhanced 'for ": Bruker helst min egen for-løkke, den jeg er øvd opp til.  
