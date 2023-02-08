package services;

import daos.Database;
import daos.EventDao;
import models.Event;
import result.EventResult;
import result.EventsResult;
import utilities.Extensions;

import java.util.logging.Logger;

/**
 * The service object for getting all events associated
 * with the logged-in user from the database.
 */
public class EventsService {
    /**
     * The EventsService logger.
     */
    private static final Logger logger = Logger.getLogger("Events Service");

    /**
     * Gets all events associated with the logged-in user from the database.
     *
     * @return the result of the call all events associated with the logged-in user from the database.
     */
    public EventsResult getEvents() {
        return null;
    }
}
