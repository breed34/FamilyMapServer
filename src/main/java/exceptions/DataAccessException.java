package exceptions;

/**
 * An exception to be thrown when an error happens during a database transaction.
 */
public class DataAccessException extends Exception {
    /**
     * Creates a DataAccessException with no message.
     */
    public DataAccessException() {
        super();
    }

    /**
     * Creates a DataAccessException with a message.
     *
     * @param message the message to send with the exception.
     */
    public DataAccessException(String message) {
        super(message);
    }
}
