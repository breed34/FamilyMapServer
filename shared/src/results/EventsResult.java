package results;

import models.Event;

import java.util.List;

/**
 * The result object for getting all events associated
 * with the logged-in user.
 */
public class EventsResult extends ResultBase {
    /**
     * The list of events retrieved from the database.
     */
    private List<Event> data;

    /**
     * Creates a successful result object for getting all events associated
     * with the logged-in user.
     *
     * @param data the events retrieved from the database.
     */
    public EventsResult(List<Event> data) {
        super();
        this.data = data;
    }

    /**
     * Creates an unsuccessful result object for getting all events associated
     * with the logged-in user.
     *
     * @param message an error message describing what went wrong.
     */
    public EventsResult(String message) {
        super(message, false);
    }

    public List<Event> getData() {
        return data;
    }
}
