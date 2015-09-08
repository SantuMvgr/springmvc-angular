package tutorial.core.services.exceptions;

/**
 * Created by Sandy on 8/29/2015.
 */
public class AccountDoesNotExistException extends RuntimeException {
    public AccountDoesNotExistException(Throwable cause) {
            super(cause);
            }

    public AccountDoesNotExistException(String message, Throwable cause) {
            super(message, cause);
            }

    public AccountDoesNotExistException(String message) {
            super(message);
            }

    public AccountDoesNotExistException() {
    }
}
