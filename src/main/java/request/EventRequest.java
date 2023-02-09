package request;

/**
 * The request object for getting an event by its eventId.
 */
public class EventRequest extends AuthenticatedRequest {
    /**
     * The eventId of the event to get from the database.
     */
    private String eventId;

    /**
     * Creates a request object for getting an event by its eventId.
     *
     * @param eventId the eventId of the event to get from the database.
     */
    public EventRequest(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
}
