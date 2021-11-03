package firstDeliverable.exception;

import java.io.Serial;

public class StopRunningException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions = 0;
    private final Exception cause;

    public StopRunningException(Exception cause) {
        numExceptions++;
        this.cause = cause;
    }
}
