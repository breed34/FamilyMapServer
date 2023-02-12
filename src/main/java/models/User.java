package models;

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
     * The personID assigned to this user's generated person.
     */
    private String personID;

    /**
     * Creates a user.
     *
     * @param username the user's username.
     * @param password the user's password.
     * @param email the user's email address.
     * @param firstName the user's first name.
     * @param lastName the user's last name.
     * @param gender the user's gender. Must be "f" or "m".
     * @param personID the personID assigned to this user's generated person.
     */
    public User(String username, String password, String email, String firstName,
                String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
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
        return  username.equals(user.username) &&
                password.equals(user.password) &&
                email.equals(user.email) &&
                firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                gender.equals(user.gender) &&
                personID.equals(user.personID);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
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

    public String getPersonID() {
        return personID;
    }
}
