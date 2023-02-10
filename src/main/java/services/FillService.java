package services;

import daos.Database;
import daos.EventDao;
import daos.PersonDao;
import daos.UserDao;
import models.Person;
import models.User;
import request.FillRequest;
import result.FillResult;
import utilities.FamilyGenerator;

import java.util.logging.Logger;

/**
 * The service object for populating the database with data for a given user.
 */
public class FillService {
    /**
     * The FillService logger.
     */
    private static final Logger logger = Logger.getLogger("Fill Service");

    /**
     * Populates the database with data for a given user.
     *
     * @param request the request object for populating the database with data for a given user.
     * @return the result of the call to populate the database with data for a given user.
     */
    public FillResult fill(FillRequest request) {
        if (request == null || !request.isValid()) {
            logger.info("Error: Invalid fill request object.");
            return new FillResult("Error: Invalid request.", false);
        }

        Database db = new Database();
        try {
            db.openConnection();

            User user = new UserDao(db.getConnection()).find(request.getUsername());
            if (user == null) {
                String message = String.format("User with username \"%s\" does not exist.", request.getUsername());
                logger.info(message);
                db.closeConnection(false);
                return new FillResult("Error: No user with that username could be found.", false);
            }

            Person person = new PersonDao(db.getConnection()).find(user.getPersonId());
            new PersonDao(db.getConnection()).clearByUser(user.getUsername());
            new EventDao(db.getConnection()).clearByUser(user.getUsername());

            int birthYear = FamilyGenerator.generateUserBirthYear();
            FamilyGenerator.generateGenerations(db.getConnection(), person, request.getUsername(),
                    birthYear, request.getGenerations());

            int numberOfPersons = new PersonDao(db.getConnection()).findByUser(user.getUsername()).size();
            int numberOfEvents = new EventDao(db.getConnection()).findByUser(user.getUsername()).size();

            db.closeConnection(true);

            String message = String.format("Successfully added %d persons and %d events to the database.",
                    numberOfPersons, numberOfEvents);
            return new FillResult(message, true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new FillResult("Error: An error occurred while trying to fill the database" +
                    "with data for the given user.", false);
        }
    }
}
