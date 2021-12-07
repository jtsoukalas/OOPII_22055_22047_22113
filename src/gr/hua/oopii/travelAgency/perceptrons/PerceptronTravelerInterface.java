package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.Control;
import gr.hua.oopii.travelAgency.exception.CitiesLibraryEmptyException;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * <h1>PerceptronTraveler used to process {@link City} objects in order to produce recommendations</h1>
 */
public interface PerceptronTravelerInterface {

    /**
     * <h1>Produces recommendation</h1>
     * Checks CompatibleCities and for each {@code true} value adds this city to the {@code recommendation}.
     * @param compatibleCities boolean array with true for output from {@link #retrieveCompatibleCities(ArrayList)}
     * @param citiesLibrary {@link Control} fixme
     * @return the recommendation Arraylist with the approved cities
     */
     ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary);

    /**
     * <h1> Produces recommendation</h1>
     * <h4> with uppercase output option</h4>
     * Checks CompatibleCities and for each {@code true} value adds this city to the {@code recommendation}.
     * @param compatibleCities boolean array with true for output from {@link #retrieveCompatibleCities(ArrayList)}
     * @param citiesLibrary {@link Control} fixme
     * @param uppercase -accepts a boolean value
     * @return the recommendation Arraylist with the approved cities
     */
    ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary, boolean uppercase);

    /**
     * <h1>Summation and heaviside step in order to retrieve compatible cities</h1>
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
     * <h1>Sorts recommendations based one the {@code comparator}</h1>
     *
     * @param comparator compares the desired fields
     * @see gr.hua.oopii.travelAgency.comparators.GeodesicCompare
     * @see gr.hua.oopii.travelAgency.comparators.TimestampCompare
     * @return the recommended cities sorted
     * @throws NoRecommendationException when {@link PerceptronTraveler#getLastRecommendation} returns null
     */
    ArrayList<City> sortRecommendation(Comparator<City> comparator) throws NoRecommendationException;

    /**
     * <h1>Sorts recommendations using implementation's comparator </h1>
     * Uses the {@link PerceptronTraveler#sortRecommendation(Comparator)} with the comparator defined at implementations.
     * @return the recommended cities sorted
     * @throws NoRecommendationException when {@link PerceptronTraveler#getLastRecommendation} returns null
     */
    ArrayList<City> sortRecommendation() throws NoRecommendationException;
}
//TODO Talk about which methods should be at the interface