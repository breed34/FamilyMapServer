package request;

import models.Authtoken;

/**
 * The request object for getting all events
 * associated with the active user.
 */
public class EventsRequest extends AuthRequiredRequestBase {
    /**
     * Creates a request object for getting all events
     * associated with the active user.
     *
     * @param authtoken the authtoken of the active user.
     */
    public EventsRequest(Authtoken authtoken) {
        super(authtoken);
    }
}
