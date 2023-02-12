package results;

/**
 * The base result class.
 */
public abstract class ResultBase {
    /**
     * An optional message describing the request outcome.
     */
    private String message;

    /**
     * Whether the request was successful.
     */
    private boolean success;

    /**
     * Creates a successful result with no message.
     */
    public ResultBase() {
        this.success = true;
    }

    /**
     * Creates a successful or unsuccessful result with a message.
     *
     * @param message an optional message describing the request outcome.
     * @param success whether the request was successful.
     */
    public ResultBase(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
