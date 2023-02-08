package request;

import models.Authtoken;

/**
 * The request object for getting all persons
 * associated with the active user.
 */
public class PersonsRequest extends AuthRequiredRequestBase {
    /**
     * Creates a request object for getting all persons
     * associated with the active user.
     *
     * @param authtoken the authtoken of the active user.
     */
    public PersonsRequest(Authtoken authtoken) {
        super(authtoken);
    }
}
