package request;

/**
 * The request object for requests that are authenticated.
 */
public class AuthenticatedRequest {
    /**
     * The username of the active user.
     */
    private String activeUserName;

    /**
     * Creates a request object for requests that are authenticated without an active user's username.
     */
    public AuthenticatedRequest() {
    }

    /**
     * Creates a request object for requests that are authenticated with an active user's username.
     *
     * @param activeUserName the username of the active user.
     */
    public AuthenticatedRequest(String activeUserName) {
        this.activeUserName = activeUserName;
    }

    public String getActiveUserName() {
        return activeUserName;
    }

    public void setActiveUserName(String activeUserName) {
        this.activeUserName = activeUserName;
    }
}