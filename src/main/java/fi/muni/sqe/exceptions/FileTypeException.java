package fi.muni.sqe.exceptions;

/**
 * Exception that is used when is problem with file type like remote or local.
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class FileTypeException extends Exception {
    public FileTypeException() {
        super();
    }

    public FileTypeException(String message) {
        super(message);
    }

    public FileTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
