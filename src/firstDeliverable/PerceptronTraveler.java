package firstDeliverable;

import java.util.ArrayList;

public abstract class PerceptronTraveler {

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
}


/* Για καθε πολη κανω: rec=features[] * weightBias[]
                       Η καταλληλη πολη (το recommendation ) ειναι η πολη με το μεγαλυτερο αποτελεσμα(rec)*/