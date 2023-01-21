package daos;

import exceptions.DataAccessException;
import models.User;

/**
 * The object for performing database operations with users.
 */
public class UserDao {
    /**
     * Inserts a user into the database.
     *
     * @param user the user to insert into the database.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void insert(User user) throws DataAccessException {

    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the desired user.
     * @return the desired user.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public User find(String username) throws DataAccessException {
        return null;
    }

    /**
     * Removes all the users from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {

    }
}
