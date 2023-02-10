package services;

import daos.Database;
import daos.PersonDao;
import models.Person;
import request.PersonRequest;
import result.PersonResult;

import java.util.logging.Logger;

/**
 * The service object for getting a single person from the database.
 */
public class PersonService {
    /**
     * The PersonService logger.
     */
    private static final Logger logger = Logger.getLogger("Person Service");

    /**
     * Gets a person from the database if it is associated with the active user.
     *
     * @param request the request object for getting a person from the database.
     * @return the result of the call to get a person from the database.
     */
    public PersonResult getPerson(PersonRequest request) {
        if (request == null || !request.isValid()) {
            logger.info("Error: Invalid get person request object.");
            return new PersonResult("Error: Invalid request.");
        }

        Database db = new Database();
        try {
            db.openConnection();

            Person person = new PersonDao(db.getConnection()).find(request.getPersonId());
            if (person == null || !person.getAssociatedUsername().equals(request.getActiveUserName())) {
                logger.info("Error: The desired person was not found.");
                db.closeConnection(false);
                return new PersonResult("Error: The desired person was not found.");
            }

            db.closeConnection(true);
            return new PersonResult(person);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new PersonResult("Error: An error occurred while trying to find the person by personId.");
        }
    }
}
