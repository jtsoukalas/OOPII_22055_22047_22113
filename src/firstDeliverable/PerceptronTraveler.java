package firstDeliverable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class PerceptronTraveler {

    // 1.cafe,restaurant 2.sea, 3.museums, 4.wellness center, 5.stadium,
    // 6.bar/club, 7.parks,playgrounds, 8.temperature, 9.weather(how cloudy), 10.distance
    // Convention of weightBias: min = 0 max = 3

    private float weightsBias[];

    public PerceptronTraveler(float[] weightsBias) {
        this.weightsBias = weightsBias;
    }

    public float[] getWeightsBias() {
        return weightsBias;
    }

    public ArrayList recommend(boolean[] compatibleCities, City[] citiesLibrary) {
        ArrayList recommendations = new ArrayList();
        for (int cityCounter = 0; cityCounter < compatibleCities.length; cityCounter++) {
            if (compatibleCities[cityCounter]) {
                recommendations.add(citiesLibrary[cityCounter].getName());
            }
        }
        return recommendations;

    }

    public ArrayList recommend(boolean[] compatibleCities, City[] citiesLibrary, boolean uppercase) {
        ArrayList recommendation = recommend(compatibleCities, citiesLibrary);

        for (int cityCounter = 0; cityCounter < compatibleCities.length; cityCounter++) {

            String city = recommendation.get(cityCounter).toString().toUpperCase(Locale.ROOT);
            recommendation.set(cityCounter, city);
        }
        return recommendation;
    }



    public boolean[] retrieveCompatibleCities(City citiesLibrary[]) {
        boolean approvedCities[] = new boolean[citiesLibrary.length];

        for (int cityCounter = 0; cityCounter < approvedCities.length; cityCounter++) {
            float sum = 0;
            for (int featureCounter = 0; featureCounter < 10; featureCounter++) {
                sum += citiesLibrary[cityCounter].getFeatures()[featureCounter] * weightsBias[featureCounter];
            }
            approvedCities[cityCounter] = sum > 1000 ? true : false;                //Temp change to sum>0.
        }
        //!!!   BIAS missing   !!!!!
        return approvedCities;
    }
}


/* Για καθε πολη κανω: rec=features[] * weightBias[]
                       Η καταλληλη πολη (το recommendation ) ειναι η πολη με το μεγαλυτερο αποτελεσμα(rec)*/