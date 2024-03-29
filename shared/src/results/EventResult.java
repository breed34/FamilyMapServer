package results;

import models.Event;

/**
 * The result object for getting an event by its eventID.
 */
public class EventResult extends ResultBase {
    /**
     * The username of the user to whom this event is linked.
     */
    private String associatedUsername;

    /**
     * A unique identifier for the event.
     */
    private String eventID;

    /**
     * The personID of the person for whom this event occurred.
     */
    private String personID;

    /**
     * The latitude of the event's location.
     */
    private float latitude;

    /**
     * The longitude of the event's location.
     */
    private float longitude;

    /**
     * The country in which the event occurred.
     */
    private String country;

    /**
     * The city in which the event occurred.
     */
    private String city;

    /**
     * The type of event that occurred.
     */
    private String eventType;

    /**
     * The year in which the event occurred.
     */
    private int year;

    /**
     * Creates a successful result object for getting an event by its eventID.
     *
     * @param event the event retrieved from the database.
     */
    public EventResult(Event event) {
        super();
        this.associatedUsername = event.getAssociatedUsername();
        this.eventID = event.getEventID();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
    }

    /**
     * Creates an unsuccessful result object for getting an event by its eventID.
     *
     * @param message an error message describing what went wrong.
     */
    public EventResult(String message) {
        super(message, false);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }
}
