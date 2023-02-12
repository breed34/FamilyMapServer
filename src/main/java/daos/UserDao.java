package daos;

import exceptions.DataAccessException;
import models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The object for performing database operations with users.
 */
public class UserDao {
    /**
     * The connection to the database.
     */
    private final Connection conn;

    /**
     * Creates a new object for performing database operations with users.
     *
     * @param conn the connection to the database.
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a user into the database.
     *
     * @param user the user to insert into the database.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO user (username, password, email, firstName, lastName, " +
                "gender, personId) VALUES(?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            addUserDataToStatement(user, stmt);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    /**
     * Finds a user by their username.
     *
     * @param username the username of the desired user.
     * @return the desired user.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public User find(String username) throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM user WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            return getUserFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    /**
     * Finds all users in the database.
     *
     * @return a list of all users or null if none are found.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public ArrayList<User> findAll() throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM user;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();

            return getUsersFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all users in the database");
        }
    }

    /**
     * Removes all the users from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM user;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

    /**
     * Removes a user with a given username.
     *
     * @param  username the username of the user to be deleted.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void removeUser(String username) throws DataAccessException {
        String sql = "DELETE FROM user WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while removing a user.");
        }
    }

    private void addUserDataToStatement(User user, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getFirstName());
        stmt.setString(5, user.getLastName());
        stmt.setString(6, user.getGender());
        stmt.setString(7, user.getPersonID());
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new User(rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("gender"),
                    rs.getString("personId"));
        }
        else {
            return null;
        }
    }

    private ArrayList<User> getUsersFromResultSet(ResultSet rs) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new User(rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("gender"),
                    rs.getString("personId")));
        }

        if (users.size() > 0) {
            return users;
        }
        else {
            return null;
        }
    }
}
