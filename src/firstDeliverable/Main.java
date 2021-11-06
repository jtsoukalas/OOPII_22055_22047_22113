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
                    System.out.println("Recommendations: " + Control.recommendationToString(control.runPerceptron(age)));
                } catch (NoRecommendationException e) {
                    System.err.println(e.getMessage());
                }

                System.out.println("Next traveler? (true/false)");
            } while (Input.readBoolean());

            //Testing unifiedDistRec
            System.out.println("Closest City is: " + (City.unifiedDistRec(Control.getLastPerceptron(), control.getCitiesLibrary()).getName()));

        } catch (StopRunningException e) {
            System.err.println("The program stopped running. Please come back later.");
            throw new Exception(e);     //For debugging reasons only
            //System.exit(-1);
        }
    }
}
