package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

public class NoCovidRestrictionsException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions = 0;
    private String city;

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public NoCovidRestrictionsException() {
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
    public NoCovidRestrictionsException(String city) {
        numExceptions++;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String getMessage() {
        return "No Covid restrictions received. Connecting to API wasn't productive (possible cause: API token expired)";
    }
}


