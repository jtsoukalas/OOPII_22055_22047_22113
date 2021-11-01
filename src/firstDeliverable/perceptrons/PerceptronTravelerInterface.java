package firstDeliverable.perceptrons;

import firstDeliverable.City;

import java.util.ArrayList;

public interface PerceptronTravelerInterface {

    ArrayList<City> recommend(boolean[] compatibleCities, City[] citiesLibrary);

    ArrayList<City> recommend(boolean[] compatibleCities, City[] citiesLibrary, boolean uppercase);

    boolean[] retrieveCompatibleCities(City[] citiesLibrary);

    float[] getWeightsBias();
}
