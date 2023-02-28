package requests;

/**
 * The request object for getting a person by their personID.
 */
public class PersonRequest extends AuthRequiredRequestBase {
    /**
     * The personID of the person to get from the database.
     */
    private String personID;

    /**
     * Creates a request object for getting a person by their personID.
     *
     * @param activeUserName the username of the active user.
     * @param personID the personID of the person to get from the database.
     */
    public PersonRequest(String personID, String activeUserName) {
        super(activeUserName);
        this.personID = personID;
    }

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return super.isValid() && personID != null && !personID.trim().isEmpty();
    }

    public String getPersonID() {
        return personID;
    }
}
