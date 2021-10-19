package FirstDeliverable.src;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class PerceptronYoungTraveler implements PerceptronTraveler {


    public ArrayList recommend (City data) {
        //add code
        return null;        //temporarily return
    }

    public ArrayList recommend (City data, boolean uppercase) {

        ArrayList recommendation = recommend(data);

        if (uppercase) {
            for (int i = 0; i < recommendation.size(); i++) {
                recommendation.get(i).toString().toUpperCase();     //Needs testing
            }
        }
        return recommendation;
    }
}

