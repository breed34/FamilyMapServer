package result;

import models.Person;

/**
 * The result object for getting a person by their personID.
 */
public class PersonResult extends ResultBase {
    /**
     * The username of the user to whom this person is linked.
     */
    private String associatedUsername;

    /**
     * A unique identifier for the person.
     */
    private String personID;

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
     * The personID of the person's father.
     */
    private String fatherID;

    /**
     * The personID of the person's mother.
     */
    private String motherID;

    /**
     * The personID of the person's spouse.
     */
    private String spouseID;

    /**
     * Creates a successful result object for getting a person by their personID.
     *
     * @param person the person retrieved from the database.
     */
    public PersonResult(Person person) {
        super();
        this.associatedUsername = person.getAssociatedUsername();
        this.personID = person.getPersonID();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getFatherID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getSpouseID();
    }

    /**
     * Creates an unsuccessful result object for getting a person by their personID.
     *
     * @param message an error message describing what went wrong.
     */
    public PersonResult(String message) {
        super(message, false);
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }
}
