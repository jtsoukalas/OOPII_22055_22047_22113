package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.Control;
import gr.hua.oopii.travelAgency.exception.CitiesLibraryEmptyException;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

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
                recommendation.add(citiesLibrary.get(cityIndex).clone());
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
            for (City city : recommendation) {
                city.setName(city.getName().toUpperCase());
            }
        }
        return recommendation;
    }

    /**
     * Personal Recommendation with custom weights
     * Note: It doesn't return user's city as recommendation
     * @param citiesLibrary
     * @param customWeights at range of [0,1]
     * @return the recommended city (with the maximum dot product)
     * @throws CitiesLibraryEmptyException if there is no cities to examine
     * @throws NoRecommendationException if there is no recommendation
     */
    public static City personalizedRecommend(ArrayList<City> citiesLibrary, float[] customWeights) throws CitiesLibraryEmptyException, NoRecommendationException {

        if (citiesLibrary == null || citiesLibrary.isEmpty()) {
            throw new CitiesLibraryEmptyException();
        }

        for (int i = 0; i < customWeights.length; i++) {
            customWeights[i] = City.normaliseFeature(customWeights[i], 4);
        }

        try {
            City userCity = new City(Control.getUserCity());
            return citiesLibrary.stream().filter(city -> ! city.equals(userCity)).max((city1, city2) -> Float.compare(dotProduct(city1.getFeatures(), customWeights), dotProduct(city2.getFeatures(), customWeights))).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NoRecommendationException();
        }
    }

    /**
     * <h1> Calculates a sum of products for given arrays</h1>
     * @param features array to take part at the calculations
     * @param weights array to take part at the calculations
     * @return the dotProduct (or rate) for given arrays
     */
    private static float dotProduct(float[] features, float[] weights) {
        float sum = 0;
        for (int i = 0; i < weights.length; i++)
            sum += features[i] * weights[i];
        return sum;
    }

    /**
     * {@inheritDoc}
     *
     * @param citiesLibrary
     * @return parallel boolean[] with {@code citiesLibrary}, {@code true} if city passes the required rate else {@code false}.
     * @throws CitiesLibraryEmptyException if the parameter {@code citiesLibrary} is empty.
     *
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

    /**
     * Returns generated last recommendation
     * @return generated last recommendation
     */
    public ArrayList<City> getLastRecommendation() {
        return lastRecommendation;
    }
}