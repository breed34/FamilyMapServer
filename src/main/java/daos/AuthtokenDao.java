package daos;

import exceptions.DataAccessException;
import models.Authtoken;

/**
 * The object for performing database operations with authtokens.
 */
public class AuthtokenDao {
    /**
     * Inserts an authtoken into the database.
     *
     * @param authtoken the authtoken to insert into the database.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void insert(Authtoken authtoken) throws DataAccessException {

    }

    /**
     * Finds an authtoken object by its authtoken.
     *
     * @param authtoken the authtoken of the desired authtoken object.
     * @return the desired authtoken object.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public Authtoken find(String authtoken) throws DataAccessException {
        return null;
    }

    /**
     * Removes all the authtokens from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {

    }
}
