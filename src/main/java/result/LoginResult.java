package result;

import models.Authtoken;
import models.User;

/**
 * The result object for logging in a user.
 */
public class LoginResult extends ResultBase {
    /**
     * The user's authtoken.
     */
    private String authtoken;

    /**
     * The user's username.
     */
    private String username;

    /**
     * The personId of the person associated with the user.
     */
    private String personId;

    /**
     * Creates a successful result object for logging in a user.
     *
     * @param authtoken the authtoken generated for the user logging in.
     * @param user the user logging in.
     */
    public LoginResult(Authtoken authtoken, User user) {
        super();
        this.authtoken = authtoken.getAuthtoken();
        this.username = authtoken.getUsername();
        this.personId = user.getPersonId();
    }

    /**
     * Creates an unsuccessful login result.
     *
     * @param message an error message describing what went wrong.
     */
    public LoginResult(String message) {
        super(message, false);
    }
}
