package utilities;

import daos.*;
import exceptions.DataAccessException;
import models.Authtoken;

/**
 * The utility object containing miscellaneous methods that
 * are needed for multiple unit tests.
 * NOTE: All of these methods should only be called in Unit Tests.
 */
public class TestExtensions {
    /**
     * Clears all data from the database associated with a specific user.
     *
     * @param username the username of the user associated with the data to be deleted.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public static void clearDataByUser(String username) throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        new EventDao(db.getConnection()).clearByUser(username);
        new PersonDao(db.getConnection()).clearByUser(username);
        new AuthtokenDao(db.getConnection()).clearByUser(username);
        new UserDao(db.getConnection()).removeUser(username);

        db.closeConnection(true);
    }

    /**
     * Adds an authtoken to the database and returns that same token.
     *
     * @return the newly added authtoken.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public static Authtoken addAuthtoken() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        Authtoken authtoken = new Authtoken("abcd1234", "Gale");
        new AuthtokenDao(db.getConnection()).insert(authtoken);
        db.closeConnection(true);
        return authtoken;
    }

    /**
     * Removes the token created in the addAuthtoken() method.
     *
     * @see #addAuthtoken()
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public static void removeAuthtoken() throws DataAccessException {
        Database db = new Database();
        db.openConnection();

        new AuthtokenDao(db.getConnection()).clearByUser("Gale");
        db.closeConnection(true);
    }
}
