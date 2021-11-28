package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

/**
 * These exceptions used when the program can not connect to the Internet.
 * @since firstDeliverable
 * @version 0
 * @author
 */
public class NoInternetException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions =0;
    private final String causeAPI;

    /**
     * Main constructor for {@link NoInternetException}
     * @param causeAPI source API that caused the exception
     * @version 0
     * @since firstDeliverable
     * @author
     */
    public NoInternetException(String causeAPI) {
        numExceptions++;
        this.causeAPI = causeAPI;
    }

    /**
     *
     * @return String message with the API that has cause the exception and further instructions
     * @version 0
     * @since firstDeliverable
     * @author
     */
    public String getMessage() {
        return "Connection to "+causeAPI+" failed. Check internet connection and try again.";
    }
}
