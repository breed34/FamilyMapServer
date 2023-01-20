package result;

/**
 * The result object for deleting all data from the database.
 */
public class ClearResult extends ResultBase {
    /**
     * Creates a successful or unsuccessful clear result object with a message.
     *
     * @param message a message describing the request outcome.
     * @param success whether the request was successful.
     */
    public ClearResult(String message, boolean success) {
        super(message, success);
    }
}
