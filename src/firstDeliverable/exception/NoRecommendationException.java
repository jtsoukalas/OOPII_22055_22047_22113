package firstDeliverable.exception;

import java.io.Serial;

public class NoRecommendationException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions = 0;
    private String cause;

    public NoRecommendationException() {
        numExceptions++;
    }

    public String getMessage() {
        return "There are no recommendations at the time.";
    }
}
