package request;

import models.Authtoken;

/**
 * The request object for getting all persons
 * associated with the active user.
 */
public class PersonsRequest extends AuthRequiredRequestBase {
    /**
     *
     *
     * @param authtoken
     */
    public PersonsRequest(Authtoken authtoken) {
        super(authtoken);
    }
}
