package uebung3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MemberView {

    /*
     * Methode zur Ausgabe aller IDs der Member-Objekte. Es werden verschiedene Varianten vorgestellt!
     * Fuer eine ordnungsgemaesse Ausgabe sollten die unpassenden Varianten und und Loesungen
     * natuerlich auskommentiert werden.
     *
     */
    public void dump(List<Member> liste) {
        System.out.println("Ausgabe aller Member-Objekte: ");

        // Loesung Nr. 1 mit For each Schleife: Sequentielle Bearbeitung der Schleife
        for ( Member p : liste ) {
            System.out.println( p.toString()  );
        }
    }
}
