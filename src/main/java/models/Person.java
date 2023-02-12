package models;

import java.util.Objects;

/**
 * Represents a person linked in a user's family.
 */
public class Person {
    /**
     * A unique identifier for the person.
     */
    private String personID;

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
     * Creates a person without a fatherID, motherID, or spouseID.
     *
     * @param personID a unique identifier for the person.
     * @param associatedUsername the username of the user to whom this person is linked.
     * @param firstName the person's first name.
     * @param lastName the person's last name.
     * @param gender the person's gender. Must be "f" or "m".
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Creates a person with a fatherID, motherID, and spouseID.
     *
     * @param personID a unique identifier for the person.
     * @param associatedUsername the username of the user to whom this person is linked.
     * @param firstName the person's first name.
     * @param lastName the person's last name.
     * @param gender the person's gender. Must be "f" or "m".
     * @param fatherID the personID of the person's father.
     * @param motherID the personID of the person's mother.
     * @param spouseID the personID of the person's spouse.
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName,
                  String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    /**
     * Checks whether two persons are equal.
     *
     * @param obj the person compare with this.
     * @return whether the persons are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Person person = (Person)obj;
        return  personID.equals(person.personID) &&
                associatedUsername.equals(person.associatedUsername) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                gender.equals(person.gender) &&
                Objects.equals(fatherID, person.fatherID) &&
                Objects.equals(motherID, person.motherID) &&
                Objects.equals(spouseID, person.spouseID);
    }

    public String getPersonID() {
        return personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
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

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }
}
