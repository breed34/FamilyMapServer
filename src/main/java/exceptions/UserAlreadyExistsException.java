package exceptions;

/**
 * An exception to be thrown when a user, with a given username, already exists.
 */
public class UserAlreadyExistsException extends Exception {
    /**
     * Creates a UserAlreadyExistsException with no message.
     */
    public UserAlreadyExistsException() {
        super();
    }

    /**
     * Creates a UserAlreadyExistsException with a message.
     *
     * @param message the message to send with the exception.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
