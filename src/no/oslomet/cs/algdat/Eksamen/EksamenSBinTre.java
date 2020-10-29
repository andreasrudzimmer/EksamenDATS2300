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

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int sml = comp.compare(verdi, p.verdi);
            if (sml < 0) p = p.venstre;
            else if (sml > 0) p = p.høyre;
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
        int sml = 0;                             // hjelpevariabel

        while (p != null)                        // fortsetter til p er ute av treet
        {
            q = p;                               // q er forelder til p
            sml = comp.compare(verdi,p.verdi);   // bruker komparatoren
            p = sml < 0 ? p.venstre : p.høyre;   // flytter p
        }

                                                 // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<T>(verdi, null);            // oppretter en ny node

        if (q == null) {
            rot = p;                            // Rot har ingen eksisterende forelder
        }
        else if (sml < 0) {
            q.venstre = p;
            p.forelder = q;
        }
        else {
            q.høyre = p;
            p.forelder = q;
        }

        antall++;
        return true;
    }

    public boolean fjern(T verdi) {
        if(verdi == null) {
            return false;
        }

        Node<T> p = rot;

        while(p != null){
            int sml = comp.compare(verdi,p.verdi);

            if(sml < 0){
                p = p.venstre;
            }
            else if(sml > 0){
                p = p.høyre;
            }
            else break;
        }

        if (p == null){
            return false;
        }

        if (p.venstre==null || p.høyre==null) {

            Node<T> b = (p.venstre!=null) ? p.venstre : p.høyre;

            if (p == rot) {
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
                    b.forelder = p.forelder;
                }
                p.forelder.høyre = b;
            }
        }
        else {

            Node<T> r = p.høyre;
            while (r.venstre != null) {
                r = r.venstre;
            }
            p.verdi = r.verdi;

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
        antall--;
        return true;
    }

    public int fjernAlle(T verdi) {
        ArrayList<T> liste = serialize();
        int teller = 0;
        for (int i = 0; i < liste.size(); i++){
            if (verdi.equals(liste.get(i)) || verdi == liste.get(i)){
                fjern(liste.get(i));
                teller++;
            }
        }
        return teller;
    }

    public int antall(T verdi) {
        int holder = 0;
        if (inneholder(verdi)) {
            Node<T> p = rot, q = null;
            int sml = 0;
            while (p != null) {
                q = p;
                sml = comp.compare(verdi, p.verdi);
                p = sml < 0 ? p.venstre : p.høyre;
                if (q.verdi == verdi) {
                    holder++;
                }
            }
        }
        return holder;
    }

    public void nullstill() {
        ArrayList<T> liste = serialize();
        int teller = 0;
        for (int i = 0; i < liste.size(); i++){
            fjern(liste.get(i));
            teller++;
        }
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        int k = 2;
        while (k != 1){
            if (p.venstre != null){
                p = p.venstre;
            }
            else if (p.høyre != null){
                p = p.høyre;
            }
            else {
                return p;
            }
        }
        return null;
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {

        if (p.forelder == null){                            // Sjekker om noden er rotnoden. Rot-node har ingen forelder, derfor returnerer den null.
            return null;
        }


        Node<T> f = p.forelder;
        if (f.høyre == null || f.høyre == p){               // Sjekker om høyre gren er sjekket.
            return f;
        }


        Node<T> n = f.høyre;
        while (n.venstre != null){
            n = n.venstre;
            }
        while (n.høyre != null) {
            n = n.høyre;
        }
        while (n.venstre != null){
                n = n.venstre;
            }

        p = n;
        return p;
    }

    public void postorden(Oppgave<? super T> oppgave) {

        Node<T> p = rot;
        p = førstePostorden(p);

        while (p != null){
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
        ArrayList<T> list = new ArrayList<>();
        LinkedList<Node> q = new LinkedList<>();
        q.offer(rot);

        while (!q.isEmpty()) {
            Node<T> h = q.poll();
            if (h == null) {
            } else {
                list.add(h.verdi);
                q.offer(h.venstre);
                q.offer(h.høyre);
            }
        }
        return list;
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        EksamenSBinTre<K> nyttTre = new EksamenSBinTre<>(c);
        for (K item: data) {
            Node<K> en = nyttTre.rot, to = null;
            int sml = 0;
            while (en != null) {
                to = en;
                sml = c.compare(item,en.verdi);
                en = sml < 0 ? en.venstre : en.høyre;
            }

            en = new Node<K>(item, null);

            if (to == null) {
                nyttTre.rot = en;
            } else if (sml < 0)  {
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

    public static void main(String[] args) {
        int[] a = {6, 6, 4, 14, 1, 3, 8, 12, 12, 3, 3, 7, 9, 9, 11, 13, 8, 2, 5, 4, 4, 9, 4, 2, 2, 4 ,5, 6};
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);
        System.out.println(tre.antall());
        System.out.println("[2, 2, 2, 3, 3, 3, 1, 4, 4, 4, 4, 5, 5, 4, 6, 7, 8, 9, 11, 9, 9, 13, 12, 12, 8, 14, 6, 6]");
        System.out.println(tre.toStringPostOrder());
        tre.fjern(4);
        System.out.println(tre.toStringPostOrder());
    }

} // ObligSBinTre

