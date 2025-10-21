package uebung3.persistence;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    /**
     * Method for saving a list of Member-objects to a disk (HDD)
     * Look-up in Google for further help! Good source:
     * https://www.digitalocean.com/community/tutorials/objectoutputstream-java-write-object-file
     * (Last Access: Oct, 13th 2025)
     */
    public void save(List<E> member) throws PersistenceException
    {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try
        {
            fos = new FileOutputStream(location);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(member);

            oos.close();
            fos.close();
        }
        catch (FileNotFoundException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.FileNotFoundException, e.getMessage());
        }
        catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, e.getMessage());
        }
    }

    @Override
    /**
     * Method for loading a list of Member-objects from a disk (HDD)
     * Some coding examples come for free :-)
     * Take also a look at the import statements above ;-!
     */
    public List<E> load() throws PersistenceException  {

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        List<E> newListe = null;

        try
        {
            fis = new FileInputStream(location);
            ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();

            if (obj instanceof List<?>) {
                newListe = (List) obj;
            }

            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.FileNotFoundException, e.getMessage());
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.Unknown, e.getMessage());
        }


        return newListe;
    }
}
