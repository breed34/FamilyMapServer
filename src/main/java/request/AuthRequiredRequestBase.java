package request;

import models.Authtoken;

/**
 * The base request object for requests that require authentication.
 */
public abstract class AuthRequiredRequestBase {
    /**
     * The authtoken of the active user.
     */
    private Authtoken authtoken;

    /**
     * Creates a request object for requests that require authentication.
     *
     * @param authtoken the authtoken of the active user.
     */
    public AuthRequiredRequestBase(Authtoken authtoken) {
        this.authtoken = authtoken;
    }

    public Authtoken getAuthtoken() {
        return authtoken;
    }
}
