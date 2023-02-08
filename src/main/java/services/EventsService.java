package services;

import daos.Database;
import daos.EventDao;
import models.Event;
import request.EventsRequest;
import result.EventResult;
import result.EventsResult;
import utilities.Extensions;

import java.util.ArrayList;
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
     * @param request the request object for getting all events associated with the logged-in user from the database.
     * @return the result of the call all events associated with the logged-in user from the database.
     */
    public EventsResult getEvents(EventsRequest request) {
        Database db = new Database();
        try {
            db.openConnection();

            if (!Extensions.isValidAuthtoken(db.getConnection(), request.getAuthtoken())) {
                logger.info("Error: The authtoken provided was not valid.");
                db.closeConnection(false);
                return new EventsResult("Error: The authtoken provided was not valid.");
            }

            ArrayList<Event> events = new EventDao(db.getConnection())
                    .findByUser(request.getAuthtoken().getUsername());
            if (events == null) {
                logger.info("Error: The desired events were not found.");
                db.closeConnection(false);
                return new EventsResult("Error: The desired events were not found.");
            }

            db.closeConnection(true);
            return new EventsResult(events);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new EventsResult("Error: An error occurred while trying to find events by username.");
        }
    }
}
