package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.Control;
import gr.hua.oopii.travelAgency.exception.CitiesLibraryEmptyException;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * PerceptronTraveler used to process {@link City} objects in order to produce recommendations.
 */
public interface PerceptronTravelerInterface {

    /**
     * Checks all the values from CompatibleCities and if it's {@code true} adds this city to the {@code recommendation}.
     * @param compatibleCities boolean array with true for output from {@link #retrieveCompatibleCities(ArrayList)}
     * @param citiesLibrary {@link Control} fixme
     * @return the recommendation Arraylist with the approved cities
     */
     ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary);

    /**
     * Checks all the values from CompatibleCities and if it's {@code true} adds this city to the {@code recommendation}.
     * @param compatibleCities boolean array with true for output from {@link #retrieveCompatibleCities(ArrayList)}
     * @param citiesLibrary {@link Control} fixme
     * @param uppercase -accepts a boolean value
     * @return the recommendation Arraylist with the approved cities
     */
    ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary, boolean uppercase);

    /**
     * Process the features for all {@link City} objects at {@code citiesLibrary},
     * the weights and the bias from this perceptron and produces a parallel boolean Array for {@code citiesLibrary}
     * (each array element represents a city from {@code citiesLibrary}).
     * <br>
     * In particular: Adds up each feature multiplied with the specific weight and at the end adds the bias,
     * for each city at {@code citiesLibrary}. After that process, implements the HeavySide step and
     * if the previous rate was grater than 0, adds {@code true} value at return Array.
     *
     * @param citiesLibrary fixme
     * @return parallel boolean[] with {@code citiesLibrary}, {@code true} if city passes the required rate else {@code false}.
     * @throws CitiesLibraryEmptyException if the parameter {@code citiesLibrary} is empty.
     */
    boolean[] retrieveCompatibleCities(@NotNull ArrayList<City> citiesLibrary) throws CitiesLibraryEmptyException;

    /**
     * it's an assistant method
     * @return last recommendation Arraylist
     */
    //ArrayList<City> getLastRecommendation();

    /**
     *it's an assistant method
     * @return weightBias
     */
    //float[] getWeightsBias();

    /**
     * Sorts the recommendations based one the {@code comparator}.
     *
     * @param comparator compares the desired fields
     * @see gr.hua.oopii.travelAgency.comparators.GeodesicCompare
     * @see gr.hua.oopii.travelAgency.comparators.TimestampCompare
     * @return the recommended cities sorted
     * @throws NoRecommendationException when {@link PerceptronTraveler#getLastRecommendation} returns null
     */
    ArrayList<City> sortRecommendation(Comparator<City> comparator) throws NoRecommendationException;

    /**
     * Sorts the recommendations with the default order.
     * Uses the {@link PerceptronTraveler#sortRecommendation(Comparator)} with the default comparator.
     * @return the recommended cities sorted
     * @throws NoRecommendationException when {@link PerceptronTraveler#getLastRecommendation} returns null
     */
    ArrayList<City> sortRecommendation() throws NoRecommendationException;
}
//TODO Talk about which methods should be at the interface