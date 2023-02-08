package exceptions;

/**
 * An exception to be thrown when an event is not found in the database.
 */
public class EventNotFoundException extends Exception {
    /**
     * Creates an EventNotFoundException with no message.
     */
    public EventNotFoundException() {
    }

    /**
     * Creates an EventNotFoundException with a message.
     *
     * @param message the message to send with the exception.
     */
    public EventNotFoundException(String message) {
        super(message);
    }
}
