package services;

import daos.Database;
import daos.EventDao;
import models.Event;
import request.EventsRequest;
import result.EventsResult;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The service object for getting all events associated
 * with the active user from the database.
 */
public class EventsService {
    /**
     * The EventsService logger.
     */
    private static final Logger logger = Logger.getLogger("Events Service");

    /**
     * Gets all events associated with the active user from the database.
     *
     * @param request the request object for getting all events associated with the active user from the database.
     * @return the result of the call all events associated with the active user from the database.
     */
    public EventsResult getAllEvents(EventsRequest request) {
        if (!request.isValid()) {
            logger.info("Error: Invalid get events request object.");
            return new EventsResult("Error: Invalid request.");
        }

        Database db = new Database();
        try {
            db.openConnection();

            ArrayList<Event> events = new EventDao(db.getConnection()).findByUser(request.getActiveUserName());

            // Handle if no events were found
            if (events == null) {
                logger.info("Error: No events were found.");
                db.closeConnection(false);
                return new EventsResult("Error: No events were found.");
            }

            db.closeConnection(true);
            return new EventsResult(events);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new EventsResult("Error: An error occurred while trying to find events for the active user.");
        }
    }
}
