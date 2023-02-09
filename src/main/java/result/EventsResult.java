package result;

import models.Event;

import java.util.ArrayList;

/**
 * The result object for getting all events associated
 * with the logged-in user.
 */
public class EventsResult extends ResultBase {
    /**
     * The list of events retrieved from the database.
     */
    private ArrayList<Event> data;

    /**
     * Creates a successful result object for getting all events associated
     * with the logged-in user.
     *
     * @param data the events retrieved from the database.
     */
    public EventsResult(ArrayList<Event> data) {
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

    public ArrayList<Event> getData() {
        return data;
    }
}
