package daos;

import exceptions.DataAccessException;
import models.Event;
import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The object for performing database operations with events.
 */
public class EventDao {
    /**
     * The connection to the database.
     */
    private final Connection conn;

    /**
     * Creates a new object for performing database operations with events.
     *
     * @param conn the connection to the database.
     */
    public EventDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts an event into the database.
     *
     * @param event the event to insert into the database.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void insert(Event event) throws DataAccessException {
        String sql = "INSERT INTO Event (EventId, AssociatedUsername, PersonId, Latitude, Longitude, " +
                "Country, City, EventType, Year) VALUES(?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getEventId());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonId());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Finds an event by its eventId.
     *
     * @param eventId the eventId of the desired event.
     * @return the desired event or null if none is found.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public Event find(String eventId) throws DataAccessException {
        Event event;
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE EventId = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("EventId"),
                        rs.getString("AssociatedUsername"),
                        rs.getString("PersonId"),
                        rs.getFloat("Latitude"),
                        rs.getFloat("Longitude"),
                        rs.getString("Country"),
                        rs.getString("City"),
                        rs.getString("EventType"),
                        rs.getInt("Year"));
                return event;
            }
            else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    /**
     * Finds all events associated with an associated user.
     *
     * @param associatedUsername the username of the associated user.
     * @return a list of all the events associated with an associated user or null if none are found.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public ArrayList<Event> findAll(String associatedUsername) throws DataAccessException {
        ArrayList<Event> events = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Event WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                events.add(new Event(rs.getString("EventId"),
                        rs.getString("AssociatedUsername"),
                        rs.getString("PersonId"),
                        rs.getFloat("Latitude"),
                        rs.getFloat("Longitude"),
                        rs.getString("Country"),
                        rs.getString("City"),
                        rs.getString("EventType"),
                        rs.getInt("Year")));
            }

            if (events.size() > 0) {
                return events;
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
     * Removes all the events from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Event;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the event table");
        }
    }

    /**
     * Removes all the events from the database associated with a given user.
     *
     * @param  associatedUsername the username associated with the events to be removed.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clearByUser(String associatedUsername) throws DataAccessException {
        String sql = "DELETE FROM Event WHERE AssociatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing events by user.");
        }
    }
}
