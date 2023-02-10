package request;

/**
 * The request object for getting all events associated with
 * the active user from the database.
 */
public class EventsRequest extends AuthRequiredRequestBase {
    /**
     * Creates a request object for getting all events associated with
     * the active user from the database.
     *
     * @param activeUserName the username of the active user.
     */
    public EventsRequest(String activeUserName) {
        super(activeUserName);
    }
}
