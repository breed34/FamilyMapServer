package daos;

import exceptions.DataAccessException;
import models.Authtoken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The object for performing database operations with authtokens.
 */
public class AuthtokenDao {
    /**
     * The connection to the database.
     */
    private final Connection conn;

    /**
     * Creates a new object for performing database operations with authtokens.
     *
     * @param conn the connection to the database.
     */
    public AuthtokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts an authtoken into the database.
     *
     * @param authtoken the authtoken to insert into the database.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void insert(Authtoken authtoken) throws DataAccessException {
        String sql = "INSERT INTO authtoken (authtoken, username) VALUES(?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authtoken into the database");
        }
    }

    /**
     * Finds an authtoken object by its authtoken.
     *
     * @param authtoken the authtoken of the desired authtoken object.
     * @return the desired authtoken object.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public Authtoken find(String authtoken) throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();

            return getAuthtokenFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken by token in the database");
        }
    }

    /**
     * Removes all the authtokens from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM authtoken;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authtoken table");
        }
    }

    /**
     * Removes the authtokens from the database associated with a given user.
     *
     * @param  username the username associated with the authtokens to be removed.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clearByUser(String username) throws DataAccessException {
        String sql = "DELETE FROM authtoken WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing authtokens by user.");
        }
    }

    private Authtoken getAuthtokenFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new Authtoken(rs.getString("authtoken"),
                    rs.getString("username"));
        }
        else {
            return null;
        }
    }
}
