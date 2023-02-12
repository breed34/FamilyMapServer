package requests;

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

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return  username != null && !username.isBlank() &&
                password != null && !password.isBlank();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
