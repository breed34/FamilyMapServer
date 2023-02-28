package daos;

import exceptions.DataAccessException;
import models.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "INSERT INTO event (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            addEventDataToStatement(event, stmt);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an event into the database");
        }
    }

    /**
     * Finds an event by its eventID and the username of the active user.
     *
     * @param eventID the eventID of the desired event.
     * @param associatedUsername the username of the active user.
     * @return the desired event or null if none is found.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public Event find(String eventID, String associatedUsername) throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM event WHERE eventID = ? AND associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            stmt.setString(2, associatedUsername);
            rs = stmt.executeQuery();

            return getEventFromResultSet(rs);
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
    public List<Event> findByUser(String associatedUsername) throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM event WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();

            return getEventsFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding events by user in the database");
        }
    }

    /**
     * Finds all events associated with an associated user.
     *
     * @return a list of all events or null if none are found.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public List<Event> findAll() throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM event";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();

            return getEventsFromResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all events in the database");
        }
    }

    /**
     * Removes all the events from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM event;";
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
        String sql = "DELETE FROM event WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing events by user.");
        }
    }

    private void addEventDataToStatement(Event event, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, event.getEventID());
        stmt.setString(2, event.getAssociatedUsername());
        stmt.setString(3, event.getPersonID());
        stmt.setFloat(4, event.getLatitude());
        stmt.setFloat(5, event.getLongitude());
        stmt.setString(6, event.getCountry());
        stmt.setString(7, event.getCity());
        stmt.setString(8, event.getEventType());
        stmt.setInt(9, event.getYear());
    }

    private Event getEventFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new Event(rs.getString("eventID"),
                    rs.getString("associatedUsername"),
                    rs.getString("personID"),
                    rs.getFloat("latitude"),
                    rs.getFloat("longitude"),
                    rs.getString("country"),
                    rs.getString("city"),
                    rs.getString("eventType"),
                    rs.getInt("year"));
        }
        else {
            return null;
        }
    }

    private List<Event> getEventsFromResultSet(ResultSet rs) throws SQLException {
        List<Event> events = new ArrayList<>();
        while (rs.next()) {
            events.add(new Event(rs.getString("eventID"),
                    rs.getString("associatedUsername"),
                    rs.getString("personID"),
                    rs.getFloat("latitude"),
                    rs.getFloat("longitude"),
                    rs.getString("country"),
                    rs.getString("city"),
                    rs.getString("eventType"),
                    rs.getInt("year")));
        }

        if (events.size() > 0) {
            return events;
        }
        else {
            return null;
        }
    }
}
