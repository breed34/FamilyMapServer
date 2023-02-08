package exceptions;

/**
 * An exception to be thrown when a user with a given username does not exist.
 */
public class UserDoesNotExistException extends Exception {
    /**
     * Creates a UserDoesNotExistException with no message.
     */
    public UserDoesNotExistException() {
    }

    /**
     * Creates a UserDoesNotExistException with a message.
     *
     * @param message the message to send with the exception.
     */
    public UserDoesNotExistException(String message) {
        super(message);
    }
}
