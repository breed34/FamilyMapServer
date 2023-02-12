package services;

import daos.AuthtokenDao;
import daos.Database;
import models.Authtoken;
import requests.AuthenticationRequest;
import results.AuthenticationResult;

import java.util.logging.Logger;

/**
 * The service object for authenticating a user.
 */
public class AuthenticationService {
    /**
     * The EventService logger.
     */
    private static final Logger logger = Logger.getLogger("Event Service");

    /**
     * Authenticates a given authtoken.
     *
     * @param request the request object for authenticating an authtoken.
     * @return the result of the call to authenticate a given authtoken.
     */
    public AuthenticationResult authenticate(AuthenticationRequest request) {
        if (!request.isValid()) {
            logger.info("Error: Invalid authentication request object.");
            return new AuthenticationResult("Error: Request requires authentication.", false);
        }

        Database db = new Database();
        try {
            db.openConnection();

            Authtoken authtoken = new AuthtokenDao(db.getConnection()).find(request.getAuthtoken());

            // Handle if no authtoken could be found
            if (authtoken == null) {
                logger.info("Error: The provided authtoken was invalid.");
                db.closeConnection(false);
                return new AuthenticationResult("Error: The provided authtoken was invalid.", false);
            }

            db.closeConnection(true);
            return new AuthenticationResult(authtoken.getUsername());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            db.closeConnection(false);
            return new AuthenticationResult("Error: An error occurred while trying to authenticate" +
                    "the user.", false);
        }
    }
}
