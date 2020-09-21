package fi.muni.sqe.exceptions;

/**
 * Runtime exception that is used when is used illegal command in command line
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class CommandLineException extends RuntimeException {
    public CommandLineException() {
    }

    public CommandLineException(String message) {
        super(message);
    }

    public CommandLineException(String message, Throwable cause) {
        super(message, cause);
    }
}
