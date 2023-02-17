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

    /**
     * Checks whether two authtokens are equal.
     *
     * @param obj the authtoken to compare with this.
     * @return whether the authtokens are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Authtoken token = (Authtoken)obj;
        return  authtoken.equals(token.authtoken) &&
                username.equals(token.username);
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public String getUsername() {
        return username;
    }
}
