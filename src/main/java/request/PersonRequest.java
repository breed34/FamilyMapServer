package request;

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
     * @param personId the personId of the person to get from the database.
     */
    public PersonRequest(String personId) {
        this.personId = personId;
    }

    /**
     * Checks whether the request is valid.
     *
     * @return whether the request is valid.
     */
    public boolean isValid() {
        return super.isValid() && personId != null && !personId.isBlank();
    }

    public String getPersonId() {
        return personId;
    }
}
