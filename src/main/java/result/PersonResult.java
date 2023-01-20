package result;

import models.Person;

/**
 * The result object for getting a person by their personId.
 */
public class PersonResult extends ResultBase {
    /**
     * The username of the user to whom this person is linked.
     */
    private String associatedUsername;

    /**
     * A unique identifier for the person.
     */
    private String personId;

    /**
     * The person's first name.
     */
    private String firstName;

    /**
     * The person's last name.
     */
    private String lastName;

    /**
     * The person's gender. Must be "f" or "m".
     */
    private String gender;

    /**
     * The personId of the person's father.
     */
    private String fatherId;

    /**
     * The personId of the person's mother.
     */
    private String motherId;

    /**
     * The personId of the person's spouse.
     */
    private String spouseId;

    /**
     * Creates a successful result object for getting a person by their personId.
     *
     * @param person the person retrieved from the database.
     */
    public PersonResult(Person person) {
        super();
        this.associatedUsername = person.getAssociatedUsername();
        this.personId = person.getPersonId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherId = person.getFatherId();
        this.motherId = person.getMotherId();
        this.spouseId = person.getSpouseId();
    }

    /**
     * Creates an unsuccessful result object for getting a person by their personId.
     *
     * @param message an error message describing what went wrong.
     */
    public PersonResult(String message) {
        super(message, false);
    }
}
