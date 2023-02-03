package daos;

import exceptions.DataAccessException;
import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
     * Finds a person by their personId.
     *
     * @param person the person to insert into the database.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void insert(Person person) throws DataAccessException {
        String sql = "INSERT INTO Person (PersonId, AssociatedUsername, FirstName, LastName, " +
                "Gender, FatherId, MotherId, SpouseId) VALUES(?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonId());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherId());
            stmt.setString(7, person.getMotherId());
            stmt.setString(8, person.getSpouseId());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a person into the database");
        }
    }

    /**
     * Finds a person by their personId.
     *
     * @param personId the personId of the desired person.
     * @return the desired person or null if none is found.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public Person find(String personId) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE PersonId = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("PersonId"),
                        rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("FatherId"),
                        rs.getString("MotherId"),
                        rs.getString("SpouseId"));
                return person;
            }
            else {
                return null;
            }
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
    public ArrayList<Person> findAll(String associatedUsername) throws DataAccessException {
        ArrayList<Person> persons = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                persons.add(new Person(rs.getString("PersonId"),
                        rs.getString("AssociatedUsername"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Gender"),
                        rs.getString("FatherId"),
                        rs.getString("MotherId"),
                        rs.getString("SpouseId")));
            }

            if (persons.size() > 0) {
                return persons;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding multiple persons in the database");
        }
    }

    /**
     * Removes all the persons from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Person;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the person table");
        }
    }
}
