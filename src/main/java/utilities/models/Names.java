package utilities.models;

import java.util.ArrayList;
import java.util.Random;

/**
 * The object use to deserialize a list of names found
 * in the mnames.json, fnames.json, and snames.json files.
 */
public class Names {
    /**
     * The list of names.
     */
    private ArrayList<String> data;

    /**
     * Gets a random name from the list of names.
     *
     * @return the random name.
     */
    public String getRandomName() {
        Random rand = new Random();
        return data.get(rand.nextInt(data.size() - 1));
    }
}
