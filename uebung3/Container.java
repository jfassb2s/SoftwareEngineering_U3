package uebung3;


import uebung3.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Container {

    private static Container instance;

    /*
     * Interne ArrayList zur Abspeicherung der Objekte
     * Alternative: HashMap oder Set. HashMap hat vor allem Probleme
     * bei der Bewahrung der Konsistenz vom Key und Value (siehe TestStore, letzter Test)
     */
    private List<Member> liste = new ArrayList<Member>();
    private PersistenceStrategy<Member> persistenceStrategy = new PersistenceStrategyStream<>();

    public static Container getInstance() {
        if (instance == null) {
            instance = new Container();
        }
        return instance;
    }

    /*
     * Methode zum Hinzufuegen einer Member.
     * @throws ContainerException
     */
    public void addMember ( Member r ) throws ContainerException {

        if (r == null) {
            ContainerException ex = new ContainerException();
            throw ex;
        }

        if ( contains( r ) == true ) {
            ContainerException ex = new ContainerException(
                    r.getID().toString() );
            throw ex;
        }
        liste.add( r );

    }

    /*
     * Methode zur Ueberpruefung, ob ein Member-Objekt in der Liste enthalten ist
     *
     */
    private boolean contains(Member r) {
        Integer ID = r.getID();
        for ( Member rec : liste) {
            // wichtig: Check auf die Values innerhalb der Integer-Objekte!
            if ( rec.getID().intValue() == ID.intValue() ) {
                return true;
            }
        }
        return false;

        // liste.contains(r), falls equals-Methode in Member ueberschrieben.
    }
    /*
     * Methode zum Loeschen einer Member
     * In Praxis durchaus verwendet: C-Programme; beim HTTP-Protokoll; SAP-Anwendung (R3); Mond-Landung ;-)
     *
     */
    public String deleteMember( Integer id ) {
        Member rec = getMember( id );
        if (rec == null) return "Member nicht enthalten - ERROR"; else {
            liste.remove(rec);
            return "Member mit der ID " + id + " konnte geloescht werden";
        }
    }

    /*
     * Methode zur Bestimmung der Anzahl der von Member-Objekten
     * Aufruf der Methode size() aus List
     *
     */
    public int size(){
        return liste.size();
    }


    public List<Member> getCurrentList(){
        return liste;
    }

    public void deleteAllMember() {
        liste.clear();
    }

    /**
     * Interne Methode zur Ermittlung einer Member
     *
     */
    public Member getMember(Integer id) {
        for ( Member rec : liste) {
            if (id == rec.getID().intValue() ){
                return rec;
            }
        }
        return null;
    }

    public void setPersistenceStrategy(PersistenceStrategy<Member> persistenceStrategy) {
        this.persistenceStrategy = persistenceStrategy;
    }

    public void store() throws PersistenceException {
        if (persistenceStrategy == null)
            throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, "No strategy is set!");

        persistenceStrategy.save(liste);
    }

    public void load() throws PersistenceException {
        if (persistenceStrategy == null)
            throw new PersistenceException(PersistenceException.ExceptionType.NoStrategyIsSet, "No strategy is set!");

        liste = persistenceStrategy.load();
    }
}
