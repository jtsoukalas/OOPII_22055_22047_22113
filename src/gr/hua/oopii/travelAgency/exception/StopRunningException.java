package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

/**
 * @since firstDeliverable
 * @version 1.1
 */

public class StopRunningException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions = 0;
    private final Exception cause;

    public StopRunningException(Exception cause) {
        numExceptions++;
        this.cause = cause;
    }

    @Override
    public Exception getCause() {
        return cause;
    }
}
