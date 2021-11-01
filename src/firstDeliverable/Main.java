package firstDeliverable;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Control control = new Control();

        // Testing control.dummyData
        control.makeDummyData();
        City.setWeatherData(control.getCitiesLibrary());
        System.out.println("-Start printing dummy data-\n"
                +control.cityLibraryToString()
                +"-End printing dummy data-\n");

        //Testing runPerceptron + recommend + retrieveCompatibleCities
        System.out.println("-Start printing dummy recommendation-");
        ArrayList<City> compatibleCities;
        if ((compatibleCities = control.runPerceptron(20))!=null){
            for (City compatibleCity : compatibleCities) {
                System.out.print(compatibleCity.getName()+"\t");
            }
        } else {
            System.out.println("There are no recommendations at the time.");
        }
        System.out.println("\n-End printing dummy recommendation-\n");

        //Testing unifiedDistRec
        System.out.println("-Start printing the closest city (with dummy-random data)-\n"
                +control.retrieveName(City.unifiedDistRec(control.getYoungPerceptron(),control.getCitiesLibrary()))
                +"\n-End printing the closest city-");
    }
}
