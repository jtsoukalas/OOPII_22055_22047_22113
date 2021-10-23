package firstDeliverable;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        // Test control.dummyData
        Control control = new Control();
        control.makeDummyData();

        System.out.println("Printing dummy data:");
        System.out.println(control.cityLibraryToString());

        //Testing runPerceptron + recomend + retrieveCompatibleCities
        System.out.println(control.runPerceptron(16));
    }
}
