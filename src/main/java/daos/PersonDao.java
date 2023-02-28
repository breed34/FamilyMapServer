package daos;

import exceptions.DataAccessException;
import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The object for performing database operations with persons.
 */
public class PersonDao {
    /**
     * The connection to the database.
     */
    private final Connection conn;

    /**
     * Creates a new object for performing database operations with persons.
     *
     * @param conn the connection to the database.
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Finds a person by their personID.
     *
     * @param person the person to insert into the database.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO person (personID, associatedUsername, firstName, lastName, " +
                "gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            addPersonDataToStatement(person, stmt);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    /**
     * Finds a person by their personID and the username of the active user.
     *
     * @param personID the personID of the desired person.
     * @param associatedUsername the username of the active user.
     * @return the desired person or null if none is found.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public Person find(String personID, String associatedUsername) throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM person WHERE personID = ? AND associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            stmt.setString(2, associatedUsername);
            rs = stmt.executeQuery();

            return getPersonFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a person in the database");
        }
    }

    /**
     * Finds all persons associated with an associated user.
     *
     * @param associatedUsername the username of the associated user.
     * @return a list of all the persons associated with an associated user or null if none are found.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public List<Person> findByUser(String associatedUsername) throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();

            return getPersonsFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding persons by username in the database");
        }
    }

    /**
     * Finds all persons in the database.
     *
     * @return a list of all the persons or null if none are found.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public List<Person> findAll() throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM person;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();

            return getPersonsFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all persons in the database");
        }
    }

    /**
     * Removes all the persons from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM person;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }

    /**
     * Removes all the persons from the database associated with a given user.
     *
     * @param  associatedUsername the username associated with the events to be removed.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clearByUser(String associatedUsername) throws DataAccessException {
        String sql = "DELETE FROM person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing events by user.");
        }
    }

    private void addPersonDataToStatement(Person person, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, person.getPersonID());
        stmt.setString(2, person.getAssociatedUsername());
        stmt.setString(3, person.getFirstName());
        stmt.setString(4, person.getLastName());
        stmt.setString(5, person.getGender());
        stmt.setString(6, person.getFatherID());
        stmt.setString(7, person.getMotherID());
        stmt.setString(8, person.getSpouseID());
    }

    private List<Person> getPersonsFromResultSet(ResultSet rs) throws SQLException {
        List<Person> persons = new ArrayList<>();
        while (rs.next()) {
            persons.add(new Person(rs.getString("personId"),
                    rs.getString("associatedUsername"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("gender"),
                    rs.getString("fatherID"),
                    rs.getString("motherID"),
                    rs.getString("spouseID")));
        }

        if (persons.size() > 0) {
            return persons;
        }
        else {
            return null;
        }
    }

    private Person getPersonFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new Person(rs.getString("personId"),
                    rs.getString("associatedUsername"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("gender"),
                    rs.getString("fatherID"),
                    rs.getString("motherID"),
                    rs.getString("spouseID"));
        }
        else {
            return null;
        }
    }
}
