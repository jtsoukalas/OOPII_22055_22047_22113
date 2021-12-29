package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

/**
 * These exceptions used when the program has to terminate due to earlier error(s).
 * @since firstDeliverable
 * @version 0
 * @author
 */
public class StopRunningException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions = 0;
    private final Exception cause;

    /**
     * Main constructor for {@link StopRunningException}
     * @param cause Exception that could not be handled
     * @version 0
     * @since firstDeliverable
     * @author
     */
    public StopRunningException(Exception cause) {
        numExceptions++;
        this.cause = cause;
    }

    /**
     *
     * @return the Exception that caused the {@link StopRunningException}.
     * @since firstDeliverable
     * @version 0
     * @author
     */
    @Override
    public Exception getCause() {
        return cause;
    }
}
