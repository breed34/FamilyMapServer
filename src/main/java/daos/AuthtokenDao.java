package daos;

import exceptions.DataAccessException;
import models.Authtoken;
import models.User;

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
        String sql = "INSERT INTO Authtoken (Authtoken, Username) VALUES(?,?);";
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
        Authtoken token;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE Authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new Authtoken(rs.getString("Authtoken"),
                        rs.getString("Username"));
                return token;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authtoken in the database");
        }
    }

    /**
     * Removes all the authtokens from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtoken;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the authtoken table");
        }
    }
}
