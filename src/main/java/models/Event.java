package models;

import java.util.Objects;

/**
 * Represents an event in a person's life.
 */
public class Event {
    /**
     * A unique identifier for the event.
     */
    private String eventId;

    /**
     * The username of the user to whom this event is linked.
     */
    private String associatedUsername;

    /**
     * The personId of the person for whom this event occurred.
     */
    private String personId;

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
     * @param eventId a unique identifier for the event.
     * @param associatedUsername the username of the user to whom this event is linked.
     * @param personId the personId of the person for whom this event occurred.
     * @param latitude the latitude of the event's location.
     * @param longitude the longitude of the event's location.
     * @param country the country in which the event occurred.
     * @param city the city in which the event occurred.
     * @param eventType the type of event that occurred.
     * @param year the year in which the event occurred.
     */
    public Event(String eventId, String associatedUsername, String personId, float latitude,
                 float longitude, String country, String city, String eventType, int year) {
        this.eventId = eventId;
        this.associatedUsername = associatedUsername;
        this.personId = personId;
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
        return Objects.equals(eventId, event.eventId) &&
                Objects.equals(associatedUsername, event.associatedUsername) &&
                Objects.equals(personId, event.personId) &&
                latitude == event.latitude &&
                longitude == event.longitude &&
                Objects.equals(country, event.country) &&
                Objects.equals(city, event.city) &&
                Objects.equals(eventType, event.eventType) &&
                Objects.equals(year, event.year);
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
