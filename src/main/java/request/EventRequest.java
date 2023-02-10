package request;

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
     * @param eventId the eventId of the event to get from the database.
     */
    public EventRequest(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return super.isValid() && eventId != null && !eventId.isBlank();
    }

    public String getEventId() {
        return eventId;
    }
}
