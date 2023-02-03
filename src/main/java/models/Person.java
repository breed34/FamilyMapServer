package models;

import java.util.Objects;

/**
 * Represents a person linked in a user's family.
 */
public class Person {
    /**
     * A unique identifier for the person.
     */
    private String personId;

    /**
     * The username of the user to whom this person is linked.
     */
    private String associatedUsername;

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
     * Creates a person without a fatherId, motherId, or spouseId.
     *
     * @param personId a unique identifier for the person.
     * @param associatedUsername the username of the user to whom this person is linked.
     * @param firstName the person's first name.
     * @param lastName the person's last name.
     * @param gender the person's gender. Must be "f" or "m".
     */
    public Person(String personId, String associatedUsername, String firstName, String lastName, String gender) {
        this.personId = personId;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Creates a person with a fatherId, motherId, and spouseId.
     *
     * @param personId a unique identifier for the person.
     * @param associatedUsername the username of the user to whom this person is linked.
     * @param firstName the person's first name.
     * @param lastName the person's last name.
     * @param gender the person's gender. Must be "f" or "m".
     * @param fatherId the personId of the person's father.
     * @param motherId the personId of the person's mother.
     * @param spouseId the personId of the person's spouse.
     */
    public Person(String personId, String associatedUsername, String firstName, String lastName,
                  String gender, String fatherId, String motherId, String spouseId) {
        this.personId = personId;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherId = fatherId;
        this.motherId = motherId;
        this.spouseId = spouseId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Person person = (Person)obj;
        return Objects.equals(personId, person.personId) &&
                Objects.equals(associatedUsername, person.associatedUsername) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(gender, person.gender) &&
                Objects.equals(fatherId, person.fatherId) &&
                Objects.equals(motherId, person.motherId) &&
                Objects.equals(spouseId, person.spouseId);
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getMotherId() {
        return motherId;
    }

    public void setMotherId(String motherId) {
        this.motherId = motherId;
    }

    public String getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(String spouseId) {
        this.spouseId = spouseId;
    }
}
