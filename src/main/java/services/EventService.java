package services;

import daos.Database;
import daos.EventDao;
import models.Event;
import requests.EventRequest;
import results.EventResult;

import java.util.logging.Logger;

/**
 * The service object for getting a single event from the database.
 */
public class EventService {
    /**
     * The EventService logger.
     */
    private static final Logger logger = Logger.getLogger("Event Service");

    /**
     * Gets an event from the database if it is associated with the active user.
     *
     * @param request the request object for getting an event from the database.
     * @return the result of the call to get an event from the database.
     */
    public EventResult getEvent(EventRequest request) {
        if (request == null || !request.isValid()) {
            logger.info("Error: Invalid get event request object.");
            return new EventResult("Error: Invalid request.");
        }

        Database db = new Database();
        try {
            db.openConnection();

            Event event = new EventDao(db.getConnection()).find(request.getEventId(),
                    request.getActiveUserName());

            // Handle if no event was found
            if (event == null) {
                logger.info("Error: The desired event was not found.");
                db.closeConnection(false);
                return new EventResult("Error: The desired event was not found.");
            }

            db.closeConnection(true);
            return new EventResult(event);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new EventResult("Error: An error occurred while trying to find the event by eventID.");
        }
    }
}
