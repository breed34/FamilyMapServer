package services;

import daos.Database;
import daos.PersonDao;
import models.Person;
import request.AuthenticatedRequest;
import result.PersonResult;
import result.PersonsResult;

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
    public PersonsResult getAllPersons(AuthenticatedRequest request) {
        Database db = new Database();
        try {
            db.openConnection();

            ArrayList<Person> persons = new PersonDao(db.getConnection()).findByUser(request.getActiveUserName());

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
