package services;

import daos.*;
import models.Event;
import models.Person;
import models.User;
import requests.LoadRequest;
import results.LoadResult;

import java.util.List;
import java.util.logging.Logger;

/**
 * The service for loading data directly into the database.
 */
public class LoadService {
    /**
     * The LoadService logger.
     */
    private static final Logger logger = Logger.getLogger("Load Service");

    /**
     * Loads data directly into the database.
     *
     * @param request the request for loading data directly into the database.
     * @return the result of the call for loading data directly into the database.
     */
    public LoadResult load(LoadRequest request) {
        if (!request.isValid()) {
            logger.info("Error: Invalid load request object.");
            return new LoadResult("Error: Invalid request.", false);
        }

        Database db = new Database();
        try {
            db.openConnection();

            // Clear the database of all information
            new UserDao(db.getConnection()).clear();
            new PersonDao(db.getConnection()).clear();
            new AuthtokenDao(db.getConnection()).clear();
            new EventDao(db.getConnection()).clear();

            // Add all users, persons, and events to the database
            for (User user : request.getUsers()) {
                new UserDao(db.getConnection()).insert(user);
            }

            for (Person person : request.getPersons()) {
                new PersonDao(db.getConnection()).insert(person);
            }

            for (Event event : request.getEvents()) {
                new EventDao(db.getConnection()).insert(event);
            }

            // Check how many user, persons,  and events were added to the database
            List<User> newUsers = new UserDao(db.getConnection()).findAll();
            List<Person> newPersons = new PersonDao(db.getConnection()).findAll();
            List<Event> newEvents = new EventDao(db.getConnection()).findAll();

            int numberOfUsers = newUsers != null ? newUsers.size() : 0;
            int numberOfPersons = newPersons != null ? newPersons.size() : 0;
            int numberOfEvents = newEvents != null ? newEvents.size() : 0;

            db.closeConnection(true);

            String message = String.format("Successfully added %d users, %d persons, and %d events to the database.",
                    numberOfUsers, numberOfPersons, numberOfEvents);
            return new LoadResult(message, true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new LoadResult("Error: An error occurred while trying to load the database.", false);
        }
    }
}
