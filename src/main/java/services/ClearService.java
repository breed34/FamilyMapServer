package services;

import daos.*;
import results.ClearResult;

/**
 * The service object for clearing the database.
 */
public class ClearService {
    /**
     * Clears the entire database.
     *
     * @return the result of the call to clear the entire database.
     */
    public ClearResult clear() {
        Database db = new Database();
        try {
            db.openConnection();

            new UserDao(db.getConnection()).clear();
            new PersonDao(db.getConnection()).clear();
            new AuthtokenDao(db.getConnection()).clear();
            new EventDao(db.getConnection()).clear();

            db.closeConnection(true);
            return new ClearResult("Clear succeeded.", true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new ClearResult("Error: An error occurred while trying to clear the" +
                    "database.", false);
        }
    }
}
