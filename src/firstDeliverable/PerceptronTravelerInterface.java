package firstDeliverable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public  interface PerceptronTravelerInterface {

    public String recommend(boolean[] compatibleCities, City[] citiesLibrary);

    public String recommend(boolean[] compatibleCities, City[] citiesLibrary, boolean uppercase);

    public boolean[] retrieveCompatibleCities(City citiesLibrary[]);

    public float[] getWeightsBias();
}
