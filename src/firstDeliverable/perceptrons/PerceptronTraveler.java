package firstDeliverable.perceptrons;

import firstDeliverable.City;

import java.util.ArrayList;

public abstract class PerceptronTraveler implements PerceptronTravelerInterface {

    /* Perceptron's weightBias fields:
     * 0.cafe 1.sea, 2.museums, 3.wellness center, 4.stadium,
     * 5.bar/club, 6.parks,playgrounds, 7.temperature, 8.weather(how cloudy), 9.distance
     * Range = [-1,1] -> [Less significant, Most significant]
     */
    //TODO Review weights and biases
    private final float[] weightsBias;
    private final float bias;

    public PerceptronTraveler(float[] weightsBias, float bias) {
        this.weightsBias = weightsBias;
        this.bias = bias;
    }

    public PerceptronTraveler(float cafeRestaurant, float sea, float museum, float wellnessCenter,
                              float stadium, float barClub, float parksPlaygrounds, float temperature,
                              float weather, float distance, float bias) {
        this(new float[]{cafeRestaurant, sea, museum, wellnessCenter, stadium,
                barClub, parksPlaygrounds, temperature, weather, distance}, bias);
    }

    public float[] getWeightsBias() {
        return weightsBias;
    }

    public ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City>citiesLibrary) {
        ArrayList<City> recommendations = new ArrayList<>();
        for (int cityCounter = 0; cityCounter < compatibleCities.length; cityCounter++) {
            if (compatibleCities[cityCounter] && citiesLibrary.get(cityCounter).getFeatures()[9]!=0) {
                recommendations.add(citiesLibrary.get(cityCounter));
            }
        }
        return recommendations;
    }

    public ArrayList<City> recommend(boolean[] compatibleCities, ArrayList<City> citiesLibrary, boolean uppercase) {
        ArrayList<City> recommendation = recommend(compatibleCities, citiesLibrary);

        if (uppercase) {
            for (int cityCounter = 0; cityCounter < compatibleCities.length; cityCounter++) {
                City tempCity = citiesLibrary.get(cityCounter);
                tempCity.setName(tempCity.getName().toUpperCase());
                recommendation.set(cityCounter, tempCity);
            }
        }
        //recommendation.replaceAll(String::toUpperCase);
        return recommendation;
    }


    public boolean[] retrieveCompatibleCities(ArrayList<City> citiesLibrary) {
        boolean[] approvedCities = new boolean[citiesLibrary.size()];

        for (int cityCounter = 0; cityCounter < approvedCities.length; cityCounter++) {
            float sum = 0;
            for (int featureCounter = 0; featureCounter < 10; featureCounter++) {
                sum += citiesLibrary.get(cityCounter).getFeatures()[featureCounter] * weightsBias[featureCounter];
            }
            sum += bias;
            approvedCities[cityCounter] = sum > 0;
        }
        return approvedCities;
    }
}