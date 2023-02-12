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
     * The personID of the person associated with the user.
     */
    private String personID;

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
        this.personID = user.getPersonID();
    }

    /**
     * Creates an unsuccessful login result.
     *
     * @param message an error message describing what went wrong.
     */
    public LoginResult(String message) {
        super(message, false);
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }

    public String getPersonID() {
        return personID;
    }
}
