package utilities.models;

/**
 * The object use to deserialize individual locations
 * found in the locations.json file.
 */
public class Location {
    /**
     * The country of the location.
     */
    private String country;

    /**
     * The city of the location.
     */
    private String city;

    /**
     * The latitude of the location.
     */
    private float latitude;

    /**
     * The longitude of the location.
     */
    private float longitude;

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
