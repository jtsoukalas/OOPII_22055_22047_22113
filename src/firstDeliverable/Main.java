package firstDeliverable;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        Control control = new Control();

        // Testing control.dummyData
        control.makeDummyData();
        System.out.println("-Start printing dummy data-\n"+control.cityLibraryToString()+"-End printing dummy data-\n");

        //Testing runPerceptron + recommend + retrieveCompatibleCities
        System.out.println("-Start printing dummy recommendation-");
        ArrayList<City> compatibleCities;
        if ((compatibleCities = control.runPerceptron(100))!=null){
            for (City compatibleCity : compatibleCities) {
                System.out.print(compatibleCity.getName()+"\t");
            }
        } else {
            System.out.println("There are no recommendations at the time.");
        }
        System.out.println("\n-End printing dummy recommendation-\n");

        //Testing unifiedDistRec
        System.out.println("-Start printing the closest city (with dummy-random data)-\n"+control.retrieveName(City.unifiedDistRec(new PerceptronElderTraveler(),control.getCitiesLibrary()))+"\n-End printing the closest city-");

    }
}
