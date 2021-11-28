package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

/**
 * @since firstDeliverable
 * @version 0
 * @author
 */

public class NoRecommendationException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions = 0;

    /**
     * increases number of exceptions
     */
    public NoRecommendationException() {
        numExceptions++;
    }

    /**
     * @version 0
     * @return a message that there are no reccomendations yet
     */
    public String getMessage() {
        return "There are no recommendations at the time.";
    }
}
