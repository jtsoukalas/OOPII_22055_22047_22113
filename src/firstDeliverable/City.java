package firstDeliverable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.ArrayList;

public class City {
    private float features[];
    private String name;

    public City(float[] features, String name) {

        this.features = features;
        this.name = name;
    }

    public float[] getFeatures() {
        return features;
    }

    public void setFeatures(float[] features) {
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name +
                "\t\t\t\t" + Arrays.toString(features);
    }

    public static City unifiedDistRec(PerceptronTraveler perceptron,City[] citiesLibrary){

        //Εχω τα recommendations απο το perceptron
        //Εχω τα citiesLibrary για να καλεσω τη recommend απο το perceptron

        //Απο τον perceptron παιρνω την recommend η οποια εχει τις πολεις που ειναι κοντα στα κρητηρια
        //απο καθε πολη παιρνω το distance και βρισκω το μικροτερο

        try {
            ArrayList<City> citiesToCompare = perceptron.recommend(perceptron.retrieveCompatibleCities(citiesLibrary), citiesLibrary);
            City min = citiesToCompare.get(0);
            for (int citiesCounter = 1; citiesCounter < citiesToCompare.size(); citiesCounter++) {
                if (min.getFeatures()[9] > citiesToCompare.get(citiesCounter).getFeatures()[9]) {
                    min = citiesToCompare.get(citiesCounter);
                }
            }
            return min;
        }catch(Exception e) {
            return null;
        }
    }
}
