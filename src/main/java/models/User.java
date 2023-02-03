package models;

import java.util.Objects;

/**
 * Represents a user.
 */
public class User {
    /**
     * The user's username.
     */
    private String username;

    /**
     * The user's password.
     */
    private String password;

    /**
     * The user's email address.
     */
    private String email;

    /**
     * The user's first name.
     */
    private String firstName;

    /**
     * The user's last name.
     */
    private String lastName;

    /**
     * The user's gender. Must be "f" or "m".
     */
    private String gender;

    /**
     * The personId assigned to this user's generated person.
     */
    private String personId;

    /**
     * Creates a user.
     *
     * @param username the user's username.
     * @param password the user's password.
     * @param email the user's email address.
     * @param firstName the user's first name.
     * @param lastName the user's last name.
     * @param gender the user's gender. Must be "f" or "m".
     * @param personId the personId assigned to this user's generated person.
     */
    public User(String username, String password, String email, String firstName,
                String lastName, String gender, String personId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personId = personId;
    }

    /**
     * Checks whether two users are equal.
     *
     * @param obj the user compare with this.
     * @return whether the users are equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        User user = (User)obj;
        return Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(gender, user.gender) &&
                Objects.equals(personId, user.personId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
