package gr.hua.oopii.travelAgency.perceptrons;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.exception.CitiesLibraryEmptyException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public abstract class PerceptronTraveler implements PerceptronTravelerInterface {

    /* Perceptron's weightBias fields:
     * 0.cafe 1.sea, 2.museum, 3.temple, 4.stadium,
     * 5.bar, 6.park 7.temperature, 8.weather(how cloudy), 9.distance
     * Range = [-1,1] -> [Less significant, Most significant]
     */

    private final float[] weightsBias;
    private final float bias;
    private ArrayList<City> lastRecommendation;

    public PerceptronTraveler(float[] weightsBias, float bias) {
        this.weightsBias = weightsBias;
        this.bias = bias;
    }

    public PerceptronTraveler(float cafe, float sea, float museum, float temple,
                              float stadium, float bar, float park, float temperature,
                              float weather, float distance, float bias) {
        this(new float[]{cafe, sea, museum, temple, stadium, bar, park, temperature, weather, distance}, bias);
    }

    //Returns an ArrayList with the cities that have a positive rate from the retrieveCompatibleCities method
    public ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary) {
        ArrayList<City> recommendation = new ArrayList<>();
        for (int cityIndex = 0; cityIndex < compatibleCities.length; cityIndex++) {
            if (compatibleCities[cityIndex] && citiesLibrary.get(cityIndex).getFeatures()[9] != 0) {
                recommendation.add(citiesLibrary.get(cityIndex));
            }
        }
        lastRecommendation = recommendation;
        return recommendation;
    }

    public ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary, boolean uppercase) {
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

    //Calculates city's rate and returns the result of Heaviside step
    public boolean[] retrieveCompatibleCities(ArrayList<City> citiesLibrary) throws CitiesLibraryEmptyException {
        if (citiesLibrary.isEmpty()){
            throw new CitiesLibraryEmptyException();
        }

        boolean[] approvedCities = new boolean[citiesLibrary.size()];
        for (int cityIndex = 0; cityIndex < approvedCities.length; cityIndex++) {
            float sum = 0;
            for (int featureIndex = 0; featureIndex < 10; featureIndex++) {
                sum += citiesLibrary.get(cityIndex).getFeatures()[featureIndex] * weightsBias[featureIndex];
            }
            sum += bias;
            approvedCities[cityIndex] = sum > 0;
        }
        return approvedCities;
    }

    public ArrayList<City> sortRecommendation(Comparator<City> comparator){
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