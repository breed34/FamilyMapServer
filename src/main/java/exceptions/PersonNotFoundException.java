package exceptions;

/**
 * An exception to be thrown when a person is not found in the database.
 */
public class PersonNotFoundException extends Exception {
    /**
     * Creates a PersonNotFoundException with no message.
     */
    public PersonNotFoundException() {
    }

    /**
     * Creates a PersonNotFoundException with a message.
     *
     * @param message the message to send with the exception.
     */
    public PersonNotFoundException(String message) {
        super(message);
    }
}
