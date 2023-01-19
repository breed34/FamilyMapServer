package models;

/**
 * An authorization token for the active user.
 */
public class Authtoken {
    /**
     * The token for the active user.
     */
    private String authtoken;

    /**
     * The active user's username.
     */
    private String username;

    /**
     * Creates an authorization token.
     * 
     * @param authtoken the token for the active user.
     * @param username the active user's username.
     */
    public Authtoken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
