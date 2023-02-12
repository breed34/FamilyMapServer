package services;

import daos.Database;
import daos.PersonDao;
import models.Person;
import requests.PersonsRequest;
import results.PersonsResult;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The service object for getting all persons associated
 * with the active user from the database.
 */
public class PersonsService {
    /**
     * The PersonsService logger.
     */
    private static final Logger logger = Logger.getLogger("Persons Service");

    /**
     * Gets all persons associated with the active user from the database.
     *
     * @param request the request object for getting all persons associated with the active user from the database.
     * @return the result of the call all persons associated with the active user from the database.
     */
    public PersonsResult getAllPersons(PersonsRequest request) {
        if (!request.isValid()) {
            logger.info("Error: Invalid get persons request object.");
            return new PersonsResult("Error: Invalid request.");
        }

        Database db = new Database();
        try {
            db.openConnection();

            ArrayList<Person> persons = new PersonDao(db.getConnection()).findByUser(request.getActiveUserName());

            // Handle if no persons were found
            if (persons == null) {
                logger.info("Error: No persons were found.");
                db.closeConnection(false);
                return new PersonsResult("Error: No persons were found.");
            }

            db.closeConnection(true);
            return new PersonsResult(persons);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new PersonsResult("Error: An error occurred while trying to find persons for the active user.");
        }
    }
}
