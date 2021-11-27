package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

public class NoInternetException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions =0;
    private final String causeAPI;

    public NoInternetException(String causeAPI) {
        numExceptions++;
        this.causeAPI = causeAPI;
    }

    public String getMessage() {
        return "Connection to "+causeAPI+" failed. Check internet connection and try again.";
    }
}
