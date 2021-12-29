package gr.hua.oopii.travelAgency;

import gr.hua.oopii.travelAgency.exception.NoRecommendationException;
import gr.hua.oopii.travelAgency.exception.NoSuchCityException;
import gr.hua.oopii.travelAgency.exception.StopRunningException;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        try {
            //Control control = new Control();

            do {
                System.out.println("Please enter traveler's age:");
                int age = Input.readAge();
                try {
                    System.out.println("Recommendations: " + Control.recommendationToString(Control.runPerceptron(age,false)));
                } catch (NoRecommendationException e) {
                    System.err.println(e.getMessage());
                }
                {// Testing addCandidateCity
                    boolean retry;
                    System.out.println("Do you want to add a new candidate city? (true/false)");
                    if (Input.readBoolean()) {
                        do {
                            try {
                                retry = false;

                                System.out.println("Provide city name");
                                String name = Input.readString();
                                System.out.println("Provide country's ISO");
                                String countryName = Input.readString();
                                Date date = Control.addCandidateCity(name, countryName);
                                if (date == null) {
                                    System.out.println("City added successfully");
                                } else {
                                    System.out.println("City already exists since " + date);
                                }
                            } catch (NoSuchCityException e) {
                                System.err.println("City " + e.getCityName() + " wasn't found. Do you want to try another city? (true/false)");
                                retry = Input.readBoolean();
                            }
                        } while (retry);
                    }
                }
                {//Testing weekCityCatalogue methods
                    System.out.println("Do you want to print cities catalog? (true/false)");
                    if (Input.readBoolean()){
                        System.out.println("Cities catalog (ordered by day):\n"+Control.presentWeekCityCatalogue(Control.makeWeekCityCatalogue()));
                    }
                }
                System.out.println("Next traveler? (true/false)");
            } while (Input.readBoolean());

            //Testing findClosestRec
            System.out.println("Closest City is: " + (City.findClosestRec(Control.getLastPerceptronUsed()).getName()));

        } catch (StopRunningException e) {
            System.err.println("The program stopped running. Please come back later.");
            throw new Exception(e);     //For debugging reasons only
            //System.exit(-1);
        }
   }
}
