package no.oslomet.cs.algdat.Eksamen;


import java.util.*;


public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public void nullstillHjelp(Node<T> n){                         // Lagde hjelpemetode for nullstill()
        if(n != null) {
            nullstillHjelp(n.venstre);
            nullstillHjelp(n.høyre);
            fjern(n.verdi);
        }
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)                        // fortsetter til p er ute av treet
        {
            q = p;                               // q er forelder til p
            cmp = comp.compare(verdi,p.verdi);   // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;   // flytter p
        }

                                                 // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<>(verdi, null);            // oppretter en ny node

        if (q == null) {
            rot = p;                            // p blir rotnode
        }
        else if (cmp < 0) {                     // venstre barn til q
            q.venstre = p;
            p.forelder = q;
        }
        else {                                     // Høyre barn til q
            q.høyre = p;
            p.forelder = q;
        }
        endringer++;                               // +1 i endringer gjort med treet
        antall++;                                // +1 verdi inn i treet
        return true;                            // innlegget er utført
    }

    public boolean fjern(T verdi) {
        if(verdi == null) {                 // Ingen null-verdier i treet
            return false;
        }

        Node<T> p = rot;

        while(p != null){                                   // Leter gjennom treet
            int cmp = comp.compare(verdi,p.verdi);          // Finner like verdier i treet. 

            if(cmp < 0){
                p = p.venstre;                         // går til venstre
            }
            else if(cmp > 0){                          // går til høyre
                p = p.høyre;
            }
            else break;                               // verdi ligger i p
        }

        if (p == null){                               // verdi ikke funnet
            return false;
        }

        if (p.venstre==null || p.høyre==null) {

            Node<T> b = (p.venstre!=null) ? p.venstre : p.høyre;      // b betyr barn

            if (p == rot) {                                     // Tilfelle
                rot =  b;
                if(b != null) {
                    b.forelder = null;
                }
            }
            else if (p == p.forelder.venstre) {
                if(b != null)b.forelder = p.forelder;
                p.forelder.venstre = b;
            } else {

                if(b != null){
                    b.forelder = p.forelder;                        // kopierer b sin forelder og setter den til p sin forelder
                }
                p.forelder.høyre = b;
            }
        }
        else {                                                      // tilfelle

            Node<T> r = p.høyre;
            while (r.venstre != null) {
                r = r.venstre;
            }
            p.verdi = r.verdi;                                          // kopierer r og setter den til p

            if(r.forelder != p) {
                Node<T> q = r.forelder;
                q.venstre = r.høyre;
                if(q.venstre != null){
                    q.venstre.forelder = q;
                }
            }
            else{
                p.høyre =  r.høyre;
                if(p.høyre != null){
                    p.høyre.forelder = p;
                }
            }
        }
        endringer++;                                          // +1 i endringer gjort med treet
        antall--;                                               // -1 verdi i treet
        return true;                                            // fjernet er utført
    }

    public int fjernAlle(T verdi) {
        ArrayList<T> liste = serialize();
        int teller = 0;
        for (int i = 0; i < liste.size(); i++){                                 // Går gjennom listen
            if (verdi.equals(liste.get(i)) || verdi == liste.get(i)){           // finner verdier lik verdi
                fjern(liste.get(i));                                            // fjerner verdien
                teller++;                                                       // anntall verdier fjernet +1
            }
        }
        return teller;                                                          // returnerer antall av verdien den fjernet
    }

    public int antall(T verdi) {
        int holder = 0;
        if (inneholder(verdi)) {                           // Bruker inneholder metoden til å se om verdien ligger i treet. kjøres dersom den gjør det.
            Node<T> p = rot, q;
            int cmp;
            while (p != null) {                           // Loopen kjøres så lenge p ikke er lik null.
                q = p;
                cmp = comp.compare(verdi, p.verdi);
                p = cmp < 0 ? p.venstre : p.høyre;
                if (q.verdi == verdi) {
                    holder++;
                }
            }
        }
        return holder;                                    // Returnerer antallet av verdien i treet.
    }

    public void nullstill() {                            // Bruker hjelpemetoden nullstillHjelp til å nullstille treet.
        endringer++;
        nullstillHjelp(rot);
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        int k = 2;
        while (k != 1){
            if (p.venstre != null){                 // Beveger seg på venstre side for p.
                p = p.venstre;
            }
            else if (p.høyre != null){              //  Beveger seg på høyre side for p, dersom p.venstre er lik null.
                p = p.høyre;
            }
            else {
                return p;                           //  Returnerer første node i Postorden
            }
        }
        return null;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        if (p.forelder == null){                            // Sjekker om noden er rotnoden. Rot-node har ingen forelder, derfor returnerer den null.
            return null;
        }


        Node<T> f = p.forelder;
        if (f.høyre == null || f.høyre == p){               // Sjekker om høyre gren er sjekket. Beverger deg opp til forelder.
            return f;
        }


        Node<T> n = f.høyre;
        while (n.venstre != null){                          // Beveger seg ned til neste node i postorden, i høyre gren.
            n = n.venstre;
            }
        while (n.høyre != null) {                           // Beveger seg derretter ned høyre gren.
            n = n.høyre;
        }
        while (n.venstre != null){                          // Beveger seg derretter til venstre gren i høyre gren.
                n = n.venstre;
            }

        p = n;
        return p;
    }

    public void postorden(Oppgave<? super T> oppgave) {

        Node<T> p = rot;
        p = førstePostorden(p);

        while (p != null){                                     // Beveger seg gjennom hele treet.
            oppgave.utførOppgave(p.verdi);
            p = nestePostorden(p);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {

        if (p != null){
            postordenRecursive(p.venstre, oppgave);
            postordenRecursive(p.høyre, oppgave);
            oppgave.utførOppgave(p.verdi);
        }
    }

    public ArrayList<T> serialize() {
        ArrayList<T> liste = new ArrayList<>();                          // oppretter ny liste
        LinkedList<Node> q = new LinkedList<>();                            // oppretter ny linket liste
        q.offer(rot);

        while (!q.isEmpty()) {                                          // loop
            Node<T> h = q.poll();
            if (h == null) {                                                // legges til dersom h ikke er null
            } else {
                liste.add(h.verdi);
                q.offer(h.venstre);
                q.offer(h.høyre);
            }
        }
        return liste;                                                     // returnerer listen.
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> nyttTre = new EksamenSBinTre<>(c);                   // Lager et nytt tre.
        for (K item: data) {                                                   // Looper gjennom array-listen som kommer inn i metoden
            Node<K> en = nyttTre.rot, to = null;
            int cmp = 0;
            while (en != null) {                                               // Utføres på lik måte som Legginn()
                to = en;
                cmp = c.compare(item,en.verdi);
                en = cmp < 0 ? en.venstre : en.høyre;
            }

            en = new Node<>(item, null);

            if (to == null) {
                nyttTre.rot = en;
            } else if (cmp < 0)  {
                to.venstre = en;
                en.forelder = to;
            } else {
                to.høyre = en;
                en.forelder = to;
            }
            nyttTre.antall++;
        }
        return nyttTre;
    }
} // ObligSBinTre

