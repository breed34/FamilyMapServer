package request;

import models.Authtoken;

/**
 * The request object for getting a person by their personId.
 */
public class PersonRequest extends AuthRequiredRequestBase {
    /**
     * The personId of the person to get from the database.
     */
    private String personId;

    /**
     * Creates a request object for getting a person by their personId.
     *
     * @param authtoken the authtoken of the active user.
     * @param personId the personId of the person to get from the database.
     */
    public PersonRequest(Authtoken authtoken, String personId) {
        super(authtoken);
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }
}
