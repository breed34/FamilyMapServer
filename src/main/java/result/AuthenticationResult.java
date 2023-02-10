package result;

/**
 * The result object for authenticating a given authtoken.
 */
public class AuthenticationResult extends ResultBase {
    /**
     * The username of the authenticated user.
     */
    private String username;

    /**
     * Creates a successful result object for authenticating a given authtoken.
     *
     * @param username the username of the authenticated user.
     */
    public AuthenticationResult(String username) {
        this.username = username;
    }

    /**
     * Creates an unsuccessful result object for authenticating a given authtoken.
     *
     * @param message an error message describing what went wrong.
     * @param success whether the request was successful.
     */
    public AuthenticationResult(String message, boolean success) {
        super(message, success);
    }

    public String getUsername() {
        return username;
    }
}
