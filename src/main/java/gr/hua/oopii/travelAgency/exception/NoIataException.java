package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

public class NoIataException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions = 0;
    private String message = null;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public NoIataException() {
        numExceptions++;
    }

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NoIataException(String message) {
        numExceptions++;
        this.message = message;
    }
}
