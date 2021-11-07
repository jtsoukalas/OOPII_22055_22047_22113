package travelAgency.perceptrons;

import travelAgency.City;
import java.util.ArrayList;

public interface PerceptronTravelerInterface {

    ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary);

    ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary, boolean uppercase);

    boolean[] retrieveCompatibleCities(ArrayList<City> citiesLibrary);

    float[] getWeightsBias();
}
