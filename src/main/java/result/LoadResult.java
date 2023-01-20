package result;

/**
 * The result object for loading data directly into the database.
 */
public class LoadResult extends ResultBase {
    /**
     * Creates a successful or unsuccessful load result object with a message.
     *
     * @param message a message describing the request outcome.
     * @param success whether the request was successful.
     */
    public LoadResult(String message, boolean success) {
        super(message, success);
    }
}
