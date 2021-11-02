package firstDeliverable;

import com.fasterxml.jackson.databind.ObjectMapper;
import firstDeliverable.openData.OpenData;
import firstDeliverable.openWeather.OpenWeatherMap;
import firstDeliverable.openWeather.Sys;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Control control = new Control();

        // Testing control.dummyData
        control.makeDummyData();
        System.out.println("-Start printing dummy data-\n"
                +control.cityLibraryToString()
                +"-End printing dummy data-\n");

        // Testing setWeatherData
        City.setWeatherData(control.getCitiesLibrary());
        System.out.println("-Start printing data with OpenWeatherData-\n"
                +control.cityLibraryToString()
                +"-End printing data-\n");

        // Testing setWikiData
        City.setWikiData(control.getCitiesLibrary());
        System.out.println("-Start printing data with OpenWikiData-\n"
                +control.cityLibraryToString()
                +"-End printing data-\n");

        //Testing runPerceptron + recommend + retrieveCompatibleCities
        System.out.println("-Start printing recommendation-");
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
        System.out.println("-Start printing the closest city (with dummy-random data)-\n"
                +control.retrieveName(City.unifiedDistRec(control.getYoungPerceptron(),control.getCitiesLibrary()))
                +"\n-End printing the closest city-");
    }
}
