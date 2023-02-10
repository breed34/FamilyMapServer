package request;

/**
 * The request object for requests that require authentication.
 */
public abstract class AuthRequiredRequestBase {
    /**
     * The username of the active user.
     */
    private String activeUserName;

    /**
     * Creates a request object for requests that require authentication without an active user's username.
     */
    public AuthRequiredRequestBase() {
    }

    /**
     * Creates a request object for requests that require authentication with an active user's username.
     *
     * @param activeUserName the username of the active user.
     */
    public AuthRequiredRequestBase(String activeUserName) {
        this.activeUserName = activeUserName;
    }

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return activeUserName != null && !activeUserName.isBlank();
    }

    public String getActiveUserName() {
        return activeUserName;
    }

    public void setActiveUserName(String activeUserName) {
        this.activeUserName = activeUserName;
    }
}