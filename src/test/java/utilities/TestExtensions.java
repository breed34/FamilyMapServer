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
}
