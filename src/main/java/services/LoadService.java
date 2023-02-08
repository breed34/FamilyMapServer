package services;

import daos.*;
import models.Event;
import models.Person;
import models.User;
import request.LoadRequest;
import result.LoadResult;

/**
 * The service for loading data directly into the database.
 */
public class LoadService {
    /**
     * Loads data directly into the database.
     *
     * @param request the request for loading data directly into the database.
     * @return the result of the call for loading data directly into the database.
     */
    public LoadResult load(LoadRequest request) {
        Database db = new Database();
        try {
            db.openConnection();

            new UserDao(db.getConnection()).clear();
            new PersonDao(db.getConnection()).clear();
            new AuthtokenDao(db.getConnection()).clear();
            new EventDao(db.getConnection()).clear();

            for (User user : request.getUsers()) {
                new UserDao(db.getConnection()).insert(user);
            }

            for (Person person : request.getPersons()) {
                new PersonDao(db.getConnection()).insert(person);
            }

            for (Event event : request.getEvents()) {
                new EventDao(db.getConnection()).insert(event);
            }

            int numberOfUsers = new UserDao(db.getConnection()).findAll().size();
            int numberOfPersons = new PersonDao(db.getConnection()).findAll().size();
            int numberOfEvents = new EventDao(db.getConnection()).findAll().size();

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
