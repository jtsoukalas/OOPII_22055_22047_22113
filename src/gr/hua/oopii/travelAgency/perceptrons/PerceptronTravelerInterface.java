package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.cityComparator.geodesicCompare;
import gr.hua.oopii.travelAgency.exception.CitiesLibraryEmptyException;

import java.util.ArrayList;
import java.util.Comparator;

public interface PerceptronTravelerInterface {

    ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary);

    ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary, boolean uppercase);

    boolean[] retrieveCompatibleCities(ArrayList<City> citiesLibrary) throws CitiesLibraryEmptyException;

    ArrayList<City> getLastRecommendation();

    float[] getWeightsBias();

    ArrayList<City> sortRecommendation(Comparator<City> comparator);
    ArrayList<City> sortRecommendation();
}
