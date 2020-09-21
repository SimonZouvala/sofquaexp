package fi.muni.sqe.exceptions;

/**
 * Exception that is used when is problem with read or write data to file
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class DatabaseException extends Exception {
    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
