package result;

import models.Person;

import java.util.ArrayList;

/**
 * The result object for getting all persons associated
 * with the logged-in user.
 */
public class PersonsResult extends ResultBase {
    /**
     * The list of persons retrieved from the database.
     */
    private ArrayList<Person> data;

    /**
     * Creates a successful result object for getting all persons associated
     * with the logged-in user.
     *
     * @param data the persons retrieved from the database.
     */
    public PersonsResult(ArrayList<Person> data) {
        super();
        this.data = data;
    }

    /**
     * Creates an unsuccessful result object for getting all persons associated
     * with the logged-in user.
     *
     * @param message an error message describing what went wrong.
     */
    public PersonsResult(String message) {
        super(message, false);
    }
}
