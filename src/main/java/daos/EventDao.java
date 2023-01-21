package daos;

import exceptions.DataAccessException;
import models.Event;

import java.util.ArrayList;

/**
 * The object for performing database operations with events.
 */
public class EventDao {
    /**
     * Inserts an event into the database.
     *
     * @param event the event to insert into the database.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void insert(Event event) throws DataAccessException {

    }

    /**
     * Finds an event by its eventId.
     *
     * @param eventId the eventId of the desired event.
     * @return the desired event.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public Event find(String eventId) throws DataAccessException {
        return null;
    }

    /**
     * Finds all events associated with an associated user.
     *
     * @param associatedUsername the username of the associated user.
     * @return a list of all the events associated with an associated user.
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public ArrayList<Event> findAll(String associatedUsername) throws DataAccessException {
        return null;
    }

    /**
     * Removes all the events from the database.
     *
     * @throws DataAccessException if an error happens during the database transaction.
     */
    public void clear() throws DataAccessException {

    }
}
