package firstDeliverable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class PerceptronTraveler {
    private float weightsBias[] = new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};

    public ArrayList recommend(City data) {
        //add code
        return null;            //temporarily return
    }

    public ArrayList recommend(City data, boolean uppercase) {
        ArrayList recommendation = recommend(data);

        if (uppercase) {
            for (int i = 0; i < recommendation.size(); i++) {
                recommendation.get(i).toString().toUpperCase(); //Needs testing
            }
        }
        return recommendation;
    }

    public boolean[] retrieveCompatibleCities(City citiesLibrary[]) {
        boolean approvedCities[] = new boolean[citiesLibrary.length];

        for (int cityCounter = 0; cityCounter < approvedCities.length; cityCounter++) {
            float sum=0;
            for (int featureCounter = 0; featureCounter < 10; featureCounter++) {
                sum+=citiesLibrary[cityCounter].getFeatures()[featureCounter]*weightsBias[featureCounter];
            }
            //bias
            approvedCities[cityCounter]=sum>0?true:false;
        }
        //!!!   BIAS   !!!!!

        return approvedCities;
    }

    public float[] getWeightsBias() {
        return weightsBias;
    }
}


/* Για καθε πολη κανω: rec=features[] * weightBias[]
                       Η καταλληλη πολη (το recommendation ) ειναι η πολη με το μεγαλυτερο αποτελεσμα(rec)*/