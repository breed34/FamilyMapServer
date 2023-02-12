package services;

import daos.AuthtokenDao;
import daos.Database;
import daos.UserDao;
import models.Authtoken;
import models.Person;
import models.User;
import requests.RegisterRequest;
import results.RegisterResult;
import utilities.FamilyGenerator;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * The service object for registering a new user.
 */
public class RegisterService {
    /**
     * The RegisterService logger.
     */
    private static final Logger logger = Logger.getLogger("Register Service");

    /**
     * Registers a new user.
     *
     * @param request a request object for registering a new user.
     * @return the result of the call to register a new user.
     */
    public RegisterResult register(RegisterRequest request) {
        // Validate request object
        if (!request.isValid()) {
            logger.info("Error: Invalid register request object.");
            return new RegisterResult("Error: Invalid request.");
        }

        Database db = new Database();
        try {
            db.openConnection();

            // Handle if user already exists
            if (new UserDao(db.getConnection()).find(request.getUsername()) != null) {
                String message = String.format("User with username \"%s\" already exists.", request.getUsername());
                logger.info(message);
                db.closeConnection(false);
                return new RegisterResult("Error: A user with that username already exists.");
            }

            // Create and insert the new user
            String userPersonID = UUID.randomUUID().toString();
            User user = createUserFromRequest(request, userPersonID);
            new UserDao(db.getConnection()).insert(user);

            // Create the person representing the new user and generate all related family data
            Person person = createPersonFromRequest(request, userPersonID);
            int birthYear = FamilyGenerator.generateUserBirthYear();
            FamilyGenerator.generateGenerations(db.getConnection(), person, request.getUsername(),
                    birthYear, 4);

            // Add an authtoken for the new user
            String tokenString = UUID.randomUUID().toString();
            Authtoken authtoken = new Authtoken(tokenString, request.getUsername());
            new AuthtokenDao(db.getConnection()).insert(authtoken);

            db.closeConnection(true);
            return new RegisterResult(authtoken, user);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new RegisterResult("Error: An error occurred while trying to register the user.");
        }
    }

    private User createUserFromRequest(RegisterRequest request, String userPersonID) {
        return new User(request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getGender(),
                userPersonID);
    }

    private Person createPersonFromRequest(RegisterRequest request, String userPersonID) {
        return new Person(userPersonID,
                request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getGender());
    }
}
