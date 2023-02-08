package services;

import daos.Database;
import daos.PersonDao;
import models.Authtoken;
import models.Person;
import request.PersonRequest;
import result.PersonResult;
import utilities.Extensions;

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
     * Gets a person from the database.
     *
     * @param request the request object for getting a person from the database.
     * @return the result of the call to get a person from the database.
     */
    public PersonResult getPerson(PersonRequest request) {
        Database db = new Database();
        try {
            db.openConnection();

            if (!Extensions.isValidAuthtoken(db.getConnection(), request.getAuthtoken())) {
                logger.info("Error: The authtoken provided was not valid.");
                db.closeConnection(false);
                return new PersonResult("Error: The authtoken provided was not valid.");
            }

            Person person = new PersonDao(db.getConnection()).find(request.getPersonId());
            if (person == null || !request.getAuthtoken().getUsername().equals(person.getAssociatedUsername())) {
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
