package requests;

/**
 * The request object for getting an event by its eventID.
 */
public class EventRequest extends AuthRequiredRequestBase {
    /**
     * The eventID of the event to get from the database.
     */
    private String eventID;

    /**
     * Creates a request object for getting an event by its eventID.
     *
     * @param activeUserName the username of the active user.
     * @param eventID the eventID of the event to get from the database.
     */
    public EventRequest(String eventID, String activeUserName) {
        super(activeUserName);
        this.eventID = eventID;
    }

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return super.isValid() && eventID != null && !eventID.isBlank();
    }

    public String getEventId() {
        return eventID;
    }
}
