package utilities;
import daos.AuthtokenDao;
import exceptions.DataAccessException;
import models.Authtoken;

import java.sql.Connection;
import java.util.Random;

/**
 * The utility object containing miscellaneous methods that
 * are needed throughout the application.
 */
public class Extensions {
    /**
     * Gets a random integer within two bounds.
     *
     * @param min the minimum value of the integer.
     * @param max the maximum value of the integer.
     * @return the random integer.
     */
    public static int getRandomIntInRange(int min, int max) {
        return new Random().nextInt((max - min)) + min;
    }

    /**
     * Checks if a give authtoken is valid.
     *
     * @param authtoken the authtoken to check.
     * @return whether the authtoken is valid.
     */
    public static boolean isValidAuthtoken(Connection conn, Authtoken authtoken) throws DataAccessException {
        return new AuthtokenDao(conn).findByToken(authtoken.getAuthtoken()) != null;
    }
}
