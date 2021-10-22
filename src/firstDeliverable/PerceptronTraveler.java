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

    /*public Array summation (City citiesLibrary[]){
        City calc[] = new City[15];
        float bias[] =getWeightsBias();

        for (int i = 0; i < citiesLibrary.length; i++) {
            calc[i]= citiesLibrary. * bias[i];

        }
    }*/

    public float[] getWeightsBias() {
        return weightsBias;
    }
}


/* Για καθε πολη κανω: rec=features[] * weightBias[]
                       Η καταλληλη πολη (το recommendation ) ειναι η πολη με το μεγαλυτερο αποτελεσμα(rec)*/