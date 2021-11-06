package firstDeliverable;

import firstDeliverable.exception.NoRecommendationException;
import firstDeliverable.exception.StopRunningException;

public class Main {

    public static void main(String[] args) throws Exception {
        try {
            Control control = new Control("Athens", "GR");
            control.initNameCitiesLibrary();


            do {
                System.out.println("Please enter traveler's age:");
                int age = Input.readAge();
                try {
                    System.out.println(Control.recommendationToString(control.runPerceptron(age)));
                } catch (NoRecommendationException e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Do you want to run for another traveler? (true/false)");
            } while (Input.readBoolean());

            //Testing unifiedDistRec
            System.out.println("-Start printing the closest city-\n"
                    + control.retrieveName(City.unifiedDistRec(Control.getLastPerceptron(), control.getCitiesLibrary()))
                    + "\n-End printing the closest city-");

        } catch (StopRunningException e) {
            System.err.println("The program stopped running. Please come back later.");
            throw new Exception(e);     //For debugging reasons only
            //System.exit(-1);
        }
    }
}
