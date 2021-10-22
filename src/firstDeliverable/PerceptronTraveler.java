package firstDeliverable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class PerceptronTraveler {

    // 1.cafe,restaurant 2.sea, 3.museums, 4.wellness center, 5.stadium,
    // 6.bar/club, 7.parks,playgrounds, 8.temperature, 9.weather(how cloudy), 10.distance
    // Convention of weightBias: min = 0 max = 3

    private float weightsBias[] = new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};

    public PerceptronTraveler(float[] weightsBias) {
        this.weightsBias = weightsBias;
    }

    public float[] getWeightsBias() {
        return weightsBias;
    }

    public String recommend(Boolean[] compatibleCities, City[] citiesLibrary) {

        String recommend = "";
        for (int cityCounter = 0; cityCounter < compatibleCities.length; cityCounter++) {
            recommend += compatibleCities[cityCounter] ? citiesLibrary[cityCounter].getName() : ", ";     //!!!!
        }
        return recommend;
    }

    public String recommend(Boolean[] compatibleCities, City[] citiesLibrary, boolean uppercase) {

        String recommendation = recommend(compatibleCities, citiesLibrary);

        return  uppercase? recommendation.toUpperCase():recommendation;
    }

    public boolean[] retrieveCompatibleCities(City citiesLibrary[]) {
        boolean approvedCities[] = new boolean[citiesLibrary.length];

        for (int cityCounter = 0; cityCounter < approvedCities.length; cityCounter++) {
            float sum = 0;
            for (int featureCounter = 0; featureCounter < 10; featureCounter++) {
                sum += citiesLibrary[cityCounter].getFeatures()[featureCounter] * weightsBias[featureCounter];
            }
            //bias
            approvedCities[cityCounter] = sum > 0 ? true : false;
        }
        //!!!   BIAS   !!!!!

        return approvedCities;
    }


}


/* Για καθε πολη κανω: rec=features[] * weightBias[]
                       Η καταλληλη πολη (το recommendation ) ειναι η πολη με το μεγαλυτερο αποτελεσμα(rec)*/