package requests;

/**
 * The request object for authenticating a given authtoken.
 */
public class AuthenticationRequest {
    /**
     * The authtoken to authenticate.
     */
    private String authtoken;

    /**
     * Creates an object for authenticating a given authtoken.
     *
     * @param authtoken the authtoken to authenticate.
     */
    public AuthenticationRequest(String authtoken) {
        this.authtoken = authtoken;
    }

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return authtoken != null && !authtoken.isBlank();
    }

    public String getAuthtoken() {
        return authtoken;
    }
}
