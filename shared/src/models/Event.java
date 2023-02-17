package models;

/**
 * Represents an event in a person's life.
 */
public class Event {
    /**
     * A unique identifier for the event.
     */
    private String eventID;

    /**
     * The username of the user to whom this event is linked.
     */
    private String associatedUsername;

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
     * Creates an event.
     *
     * @param eventID a unique identifier for the event.
     * @param associatedUsername the username of the user to whom this event is linked.
     * @param personID the personID of the person for whom this event occurred.
     * @param latitude the latitude of the event's location.
     * @param longitude the longitude of the event's location.
     * @param country the country in which the event occurred.
     * @param city the city in which the event occurred.
     * @param eventType the type of event that occurred.
     * @param year the year in which the event occurred.
     */
    public Event(String eventID, String associatedUsername, String personID, float latitude,
                 float longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    /**
     * Checks whether two events are equal.
     *
     * @param obj the event compare with this.
     * @return whether the events are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Event event = (Event)obj;
        return  eventID.equals(event.eventID) &&
                associatedUsername.equals(event.associatedUsername) &&
                personID.equals(event.personID) &&
                latitude == event.latitude &&
                longitude == event.longitude &&
                country.equals(event.country) &&
                city.equals(event.city) &&
                eventType.equals(event.eventType) &&
                year == event.year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
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
