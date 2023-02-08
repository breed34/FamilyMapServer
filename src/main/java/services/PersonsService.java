package services;

import daos.Database;
import daos.PersonDao;
import models.Person;
import request.PersonsRequest;
import result.EventsResult;
import result.PersonsResult;
import utilities.Extensions;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * The service object for getting all persons associated
 * with the logged-in user from the database.
 */
public class PersonsService {
    /**
     * The PersonsService logger.
     */
    private static final Logger logger = Logger.getLogger("Persons Service");

    /**
     * Gets all persons associated with the logged-in user from the database.
     *
     * @param request the request object for getting all persons associated with the logged-in user from the database.
     * @return the result of the call all persons associated with the logged-in user from the database.
     */
    public PersonsResult getPersons(PersonsRequest request) {
        Database db = new Database();
        try {
            db.openConnection();

            if (!Extensions.isValidAuthtoken(db.getConnection(), request.getAuthtoken())) {
                logger.info("Error: The authtoken provided was not valid.");
                db.closeConnection(false);
                return new PersonsResult("Error: The authtoken provided was not valid.");
            }

            ArrayList<Person> persons = new PersonDao(db.getConnection())
                    .findByUser(request.getAuthtoken().getUsername());
            if (persons == null) {
                logger.info("Error: The desired persons were not found.");
                db.closeConnection(false);
                return new PersonsResult("Error: The desired persons were not found.");
            }

            db.closeConnection(true);
            return new PersonsResult(persons);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new PersonsResult("Error: An error occurred while trying to find persons by username.");
        }
    }
}
