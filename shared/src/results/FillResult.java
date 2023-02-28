package results;

/**
 * The result object for populating the database with
 * generated data for a specified user.
 */
public class FillResult extends ResultBase {
    /**
     * Creates a successful or unsuccessful fill result object with a message.
     *
     * @param message a message describing the request outcome.
     * @param success whether the request was successful.
     */
    public FillResult(String message, boolean success) {
        super(message, success);
    }
}
