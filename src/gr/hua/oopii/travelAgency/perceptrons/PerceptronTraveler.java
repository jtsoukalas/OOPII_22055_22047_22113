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
 *
 * @see PerceptronTravelerInterface
 */
public abstract class PerceptronTraveler implements PerceptronTravelerInterface {

    /**
     * Perceptron's weightBias fields: <br>
     * 0.cafe, 1.sea, 2.museum, 3.temple, 4.stadium,
     * 5.bar, 6.park 7.temperature, 8.weather(how cloudy), 9.distance
     * <br> Range = [-1,1] -> [Less significant, Most significant]
     * <br> Used at {@link #retrieveCompatibleCities(ArrayList)}
     */
    private final float[] weightsBias;
    /**
     * Determines the abstraction of weightBias for recommendations.
     * <br> Used at {@link #retrieveCompatibleCities(ArrayList)}
     */
    private final float bias;
    /**
     * Contains the last recommendation produced by this perceptron.
     */
    private ArrayList<City> lastRecommendation;

    /**
     * <h1>Main constructor for {@link PerceptronTraveler}</h1>
     *
     * @author
     * @since firstDeliverable
     */
    public PerceptronTraveler(float[] weightsBias, float bias) {
        this.weightsBias = weightsBias;
        this.bias = bias;
    }

    /**
     * <h1>Constructor with bias and weights as independent floats </h1>
     * <br>Uses {@link #PerceptronTraveler(float[], float)} constructor.
     *
     * @author
     * @since
     */
    public PerceptronTraveler(float cafe, float sea, float museum, float temple,
                              float stadium, float bar, float park, float temperature,
                              float weather, float distance, float bias) {
        this(new float[]{cafe, sea, museum, temple, stadium, bar, park, temperature, weather, distance}, bias);
    }

    /**
     * {@inheritDoc}
     *
     * @param compatibleCities boolean[ ] - output from {@link #retrieveCompatibleCities(ArrayList)}
     * @param citiesLibrary    {@link Control} fixme
     * @return the recommendation Arraylist with the approved cities
     * @version
     * @author
     * @since
     */
    public ArrayList<City> recommend(boolean @NotNull [] compatibleCities, @NotNull ArrayList<City> citiesLibrary) {
        ArrayList<City> recommendation = new ArrayList<>();
        for (int cityIndex = 0; cityIndex < compatibleCities.length; cityIndex++) {
            if (compatibleCities[cityIndex] && citiesLibrary.get(cityIndex).getFeatures()[9] != 0) { //Avoids user's location to be a recommendation
                recommendation.add(citiesLibrary.get(cityIndex));
            }
        }
        lastRecommendation = recommendation;
        return recommendation;
    }

    /**
     * {@inheritDoc}
     *
     * @param compatibleCities boolean array with true for output from {@link #retrieveCompatibleCities(ArrayList)}
     * @param citiesLibrary    {@link Control} fixme
     * @param uppercase        -accepts a boolean value
     * @return the recommendation Arraylist with the approved cities
     * @version
     * @author
     * @since
     */
    public ArrayList<City> recommend(boolean @NotNull [] compatibleCities, @NotNull ArrayList<City> citiesLibrary, boolean uppercase) {
        ArrayList<City> recommendation = recommend(compatibleCities, citiesLibrary);

        if (uppercase) {
            for (int cityIndex = 0; cityIndex < compatibleCities.length; cityIndex++) {
                City tempCity = citiesLibrary.get(cityIndex);
                tempCity.setName(tempCity.getName().toUpperCase());
                recommendation.set(cityIndex, tempCity);
            }
        }
        return recommendation;
    }

    /**
     * {@inheritDoc}
     *
     * @param citiesLibrary fixme
     * @return parallel boolean[] with {@code citiesLibrary}, {@code true} if city passes the required rate else {@code false}.
     * @throws CitiesLibraryEmptyException if the parameter {@code citiesLibrary} is empty.
     * @version
     * @author
     * @since
     */
    public boolean[] retrieveCompatibleCities(@NotNull ArrayList<City> citiesLibrary) throws CitiesLibraryEmptyException {
        if (citiesLibrary.isEmpty()) {
            throw new CitiesLibraryEmptyException();
        }

        // Summation and Heaviside step
        boolean[] approvedCities = new boolean[citiesLibrary.size()];
        for (int cityIndex = 0; cityIndex < approvedCities.length; cityIndex++) {
            float sum = 0;
            for (int featureIndex = 0; featureIndex < 10; featureIndex++) {
                sum += citiesLibrary.get(cityIndex).getFeatures()[featureIndex] * weightsBias[featureIndex];
            }
            sum += bias;    // sum = Σ (feature * weight) + bias
            approvedCities[cityIndex] = sum > 0;
        }
        return approvedCities;
    }

    /**
     * {@inheritDoc}
     *
     * @param comparator compares the desired fields
     * @return the recommended cities sorted
     * @throws NoRecommendationException when {@link PerceptronTraveler#getLastRecommendation} returns null
     * @version
     * @author
     * @see gr.hua.oopii.travelAgency.comparators.GeodesicCompare
     * @see gr.hua.oopii.travelAgency.comparators.TimestampCompare
     * @since
     */
    public ArrayList<City> sortRecommendation(Comparator<City> comparator) throws NoRecommendationException {
        if (this.lastRecommendation == null || this.lastRecommendation.isEmpty()) {
            throw new NoRecommendationException();
        }
        ArrayList<City> tempLastRec = lastRecommendation;
        tempLastRec.sort(comparator);
        return tempLastRec;
    }

    public float[] getWeightsBias() {
        return weightsBias;
    }

    public ArrayList<City> getLastRecommendation() {
        return lastRecommendation;
    }
}