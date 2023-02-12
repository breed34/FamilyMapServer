package services;

import daos.AuthtokenDao;
import daos.Database;
import daos.UserDao;
import models.Authtoken;
import models.User;
import request.LoginRequest;
import result.LoginResult;

import java.util.UUID;
import java.util.logging.Logger;

/**
 * The service object for logging a user into the app.
 */
public class LoginService {
    /**
     * The LoginService logger.
     */
    private static final Logger logger = Logger.getLogger("Login Service");

    /**
     * Logs in a user.
     *
     * @param request a request object for logging in a user.
     * @return the result of the call to log in a user.
     */
    public LoginResult login(LoginRequest request) {
        if (!request.isValid()) {
            logger.info("Error: Invalid login request object.");
            return new LoginResult("Error: Invalid request.");
        }

        Database db = new Database();
        try {
            db.openConnection();


            User user = new UserDao(db.getConnection()).find(request.getUsername());

            // Handle if user does not exist or password does not match user found in database
            if (user == null || !request.getPassword().equals(user.getPassword())) {
                logger.info("Error: The username or password for the user was incorrect.");
                db.closeConnection(false);
                return new LoginResult("Error: The username or password for the user was incorrect.");
            }

            // Add a new authtoken for the user
            String tokenString = UUID.randomUUID().toString();
            Authtoken authtoken = new Authtoken(tokenString, request.getUsername());
            new AuthtokenDao(db.getConnection()).insert(authtoken);

            db.closeConnection(true);
            return new LoginResult(authtoken, user);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new LoginResult("Error: An error occurred while trying to login the user");
        }
    }
}
