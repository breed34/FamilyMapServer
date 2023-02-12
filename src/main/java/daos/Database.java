package daos;

import exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The object for common database functions.
 */
public class Database {
    /**
     * The database connection object.
     */
    private Connection conn;

    /**
     * Opens a new database connection.
     *
     * @return the new connection.
     * @throws DataAccessException if an error occurs when trying to open the connection.
     */
    public Connection openConnection() throws DataAccessException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";
            conn = DriverManager.getConnection(CONNECTION_URL);
            conn.setAutoCommit(false);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    /**
     * Gets the current connection. If the connection is null a new connection is opened.
     *
     * @return the connection.
     * @throws DataAccessException if an error occurs when trying to open a new connection.
     */
    public Connection getConnection() throws DataAccessException {
        if (conn == null) {
            return openConnection();
        }
        else {
            return conn;
        }
    }

    /**
     * Closes the current connection.
     *
     * @param commit whether the transaction should be committed.
     */
    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                conn.commit();
            }
            else {
                conn.rollback();
            }
            conn.close();
            conn = null;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

