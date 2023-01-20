package request;

/**
 * The request object for logging in a user.
 */
public class LoginRequest {
    /**
     * The user's username.
     */
    private String username;

    /**
     * The user's password.
     */
    private String password;

    /**
     * Creates a request object for logging in a user.
     *
     * @param username the user's username.
     * @param password the user's password.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
