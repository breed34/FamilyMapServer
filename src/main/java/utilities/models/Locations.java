package utilities.models;

import java.util.List;
import java.util.Random;

/**
 * The object use to deserialize a list of locations
 * found in the locations.json file.
 */
public class Locations {
    /**
     * The list of locations.
     */
    private List<Location> data;

    /**
     * Gets a random location from the list of locations.
     *
     * @return the random location.
     */
    public Location getRandomLocation() {
        Random rand = new Random();
        return data.get(rand.nextInt(data.size() - 1));
    }
}
