package daos;

import exceptions.DataAccessException;
import models.Person;

import java.util.ArrayList;

/**
 * The object for performing database operations with persons.
 */
public class PersonDao {
    /**
     * Finds a person by their personId.
     *
     * @param person the person to insert into the database.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void insert(Person person) throws DataAccessException {

    }

    /**
     * Finds a person by their personId.
     *
     * @param personId the personId of the desired person.
     * @return the desired person.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public Person find(String personId) throws DataAccessException {
        return null;
    }

    /**
     * Finds all persons associated with an associated user.
     *
     * @param associatedUsername the username of the associated user.
     * @return a list of all the persons associated with an associated user.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public ArrayList<Person> findALl(String associatedUsername) throws DataAccessException {
        return null;
    }

    /**
     * Removes all the persons from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {

    }
}
