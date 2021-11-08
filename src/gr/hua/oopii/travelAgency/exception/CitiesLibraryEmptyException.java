package gr.hua.oopii.travelAgency.exception;

import java.io.Serial;

public class CitiesLibraryEmptyException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;
    static int numExceptions = 0;

    public CitiesLibraryEmptyException() {
        numExceptions++;
    }

    public String getMessage(){
        return "Error! Cities library hasn't been initialized. Please restart the program to fix the problem.";
    }
}
