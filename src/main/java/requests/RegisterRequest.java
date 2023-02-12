package requests;

/**
 * The request object for registering a new user.
 */
public class RegisterRequest {
    /**
     * The new user's username.
     */
    private String username;

    /**
     * The new user's password.
     */
    private String password;

    /**
     * The new user's email address.
     */
    private String email;

    /**
     * The new user's first name.
     */
    private String firstName;

    /**
     * The new user's last name.
     */
    private String lastName;

    /**
     * The new user's gender. Must be "f" or "m".
     */
    private String gender;

    /**
     * Creates a request object for registering a new user.
     *
     * @param username the new user's username.
     * @param password the new user's password.
     * @param email the new user's email address.
     * @param firstName the new user's first name.
     * @param lastName the new user's last name.
     * @param gender the new user's gender. Must be "f" or "m".
     */
    public RegisterRequest(String username, String password, String email, String firstName,
                           String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return  username != null && !username.isBlank() &&
                password != null && !password.isBlank() &&
                email != null && !email.isBlank() &&
                firstName != null && !firstName.isBlank() &&
                lastName != null && !lastName.isBlank() &&
                (gender.toLowerCase().equals("m") || gender.toLowerCase().equals("f"));
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
}
