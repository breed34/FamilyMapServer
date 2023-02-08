package services;

import daos.AuthtokenDao;
import daos.Database;
import daos.UserDao;
import models.Authtoken;
import models.Person;
import models.User;
import request.RegisterRequest;
import result.RegisterResult;
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
        Database db = new Database();
        try {
            db.openConnection();

            if (new UserDao(db.getConnection()).find(request.getUsername()) != null) {
                String message = String.format("User with username \"%s\" already exists.", request.getUsername());
                logger.info(message);
                db.closeConnection(false);
                return new RegisterResult("Error: A user with that username already exists.");
            }

            String userPersonId = UUID.randomUUID().toString();
            User user = extractUser(request, userPersonId);
            new UserDao(db.getConnection()).insert(user);

            Person person = extractPerson(request, userPersonId);
            int birthYear = FamilyGenerator.generateUserBirthYear();
            FamilyGenerator.generateGenerations(db.getConnection(), person, request.getUsername(),
                    birthYear, 4);

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

    private User extractUser(RegisterRequest request, String userPersonId) {
        return new User(request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getGender(),
                userPersonId);
    }

    private Person extractPerson(RegisterRequest request, String userPersonId) {
        return new Person(userPersonId,
                request.getUsername(),
                request.getFirstName(),
                request.getLastName(),
                request.getGender());
    }
}
