package firstDeliverable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface PerceptronTravelerInterface {

    String recommend(boolean[] compatibleCities, City[] citiesLibrary);

    String recommend(boolean[] compatibleCities, City[] citiesLibrary, boolean uppercase);

    boolean[] retrieveCompatibleCities(City[] citiesLibrary);

    float[] getWeightsBias();
}
