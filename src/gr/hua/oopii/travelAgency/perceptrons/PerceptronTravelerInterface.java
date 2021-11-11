package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.exception.CitiesLibraryEmptyException;

import java.util.ArrayList;

public interface PerceptronTravelerInterface {

    ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary);

    ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary, boolean uppercase);

    boolean[] retrieveCompatibleCities(ArrayList<City> citiesLibrary) throws CitiesLibraryEmptyException;

    ArrayList<City> getLastRecommendation();

    float[] getWeightsBias();
}