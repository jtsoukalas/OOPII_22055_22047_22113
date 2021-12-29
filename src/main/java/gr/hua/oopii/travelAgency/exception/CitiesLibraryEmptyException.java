package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

/**
 * @since firstDeliverable
 * @version 0
 * @author
 */

public class CitiesLibraryEmptyException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions = 0;

    /**
     * increases number of exceptions
     */
    public CitiesLibraryEmptyException() {
        numExceptions++;
    }

    /**
     * @return  a message that tells the user what to do in case of the error
     * @since firstDeliverable
     * @version 0
     * @author
     */
    public String getMessage(){
        return "Error! Cities library hasn't been initialized. Please restart the program to fix the problem.";
    }
}
