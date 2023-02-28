package requests;

/**
 * The request object for getting all persons associated with
 * the active user from the database.
 */
public class PersonsRequest extends AuthRequiredRequestBase {
    /**
     * Creates a request object for getting all persons associated with
     * the active user from the database.
     *
     * @param activeUserName the username of the active user.
     */
    public PersonsRequest(String activeUserName) {
        super(activeUserName);
    }
}
