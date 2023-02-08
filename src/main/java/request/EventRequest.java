package request;

import models.Authtoken;

/**
 * The request object for getting an event by its eventId.
 */
public class EventRequest extends AuthRequiredRequestBase {
    /**
     * The eventId of the event to get from the database.
     */
    private String eventId;

    /**
     * Creates a request object for getting an event by its eventId.
     *
     * @param authtoken the authtoken of the active user.
     * @param eventId the eventId of the event to get from the database.
     */
    public EventRequest(Authtoken authtoken, String eventId) {
        super(authtoken);
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
}
