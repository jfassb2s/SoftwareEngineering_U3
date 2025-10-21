package uebung3;

import uebung3.Member;
import uebung3.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ContainerTest {

    private Container container;

    @BeforeEach
    void setUp() {
        container = Container.getInstance();
    }

    @Test
    void testMongoDBNotImplementedSolution() {
        // Set a strategy, which has not been implemented
        container.setPersistenceStrategy(new PersistenceStrategyMongoDB<Member>());

        // Testing store
        // Hinweis: Beim Aufruf der Methoden store() oder load() muss eine Exception vom Typ
        // PersistenceException zurückgegeben werden. Der ExceptionType lautet ImplementationNotAvailable
        // Die Message (abrufbar mit der Methode e.getMessage() ) muss einen eindeutigen Text haben, z.B.:

        PersistenceException e = assertThrows(PersistenceException.class, () -> {
            container.store();
        }, "Erwartet wird eine PersistenceException beim Aufruf von store() mit MongoDB-Strategie");

        assertEquals(PersistenceException.ExceptionType.ImplementationNotAvailable, e.getExceptionType());
        assertEquals("MongoDB is not implemented!", e.getMessage());
    }

    @Test
    void testNoStrategeySet() {
        container.setPersistenceStrategy(null);

        PersistenceException e = assertThrows(PersistenceException.class, () -> {
            container.store();
        }, "Erwartet wird eine PersistenceException beim Aufruf von store() mit MongoDB-Strategie");

        assertEquals(PersistenceException.ExceptionType.NoStrategyIsSet, e.getExceptionType());
        assertEquals("No strategy is set!", e.getMessage());
    }

    @Test
    void testWrongLocationOfFile() {
        try {
            PersistenceStrategyStream<Member> strat = new PersistenceStrategyStream<Member>();
            //FileStreams do not like directories, so try this out ;-)
            strat.setLocation("/Users/saschaalda/tmp");
            container.setPersistenceStrategy( strat );
            container.store();

        } catch (PersistenceException e) {
            assertEquals(PersistenceException.ExceptionType.FileNotFoundException, e.getExceptionType());
        }
    }

    @Test
    void testStoreDeleteAndLoad() {
        // Testen Sie folgenden RoundTrip:
        // 1. Lösche alle Member-Objekte (Sicher ist sicher! Dazu die Methode deleteAllMember implementieren!)
        // 2. Setzen der Strategie
        // 3. Hinzufügen eines Member-Objekts
        // 4. Abspeichern
        // 5. Löschen des Member-Objekts
        // 6. Laden
        // Die Zustandsänderungen mittels der size() bitte stets testen!

        container.deleteAllMember();
        container.setPersistenceStrategy(new PersistenceStrategyStream<Member>());

        try {
            assertEquals(container.size(), 0);

            container.addMember(new MemberKonkret(1337));

            assertEquals(container.size(), 1);

            container.store();

            assertEquals(container.size(), 1);

            container.deleteAllMember();

            assertEquals(container.size(), 0);

            container.load();

            assertEquals(container.size(), 1);

            assertNotEquals(container.getMember(1337), null);

        } catch (Exception e) {
            System.err.println(e.getMessage());
            assert(false);
        }
    }
}