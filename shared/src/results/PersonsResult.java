package results;

import models.Person;

import java.util.List;

/**
 * The result object for getting all persons associated
 * with the logged-in user.
 */
public class PersonsResult extends ResultBase {
    /**
     * The list of persons retrieved from the database.
     */
    private List<Person> data;

    /**
     * Creates a successful result object for getting all persons associated
     * with the logged-in user.
     *
     * @param data the persons retrieved from the database.
     */
    public PersonsResult(List<Person> data) {
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

    public List<Person> getData() {
        return data;
    }
}
