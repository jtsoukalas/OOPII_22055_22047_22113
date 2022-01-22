package gr.hua.oopii.travelAgency.API;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.SafePlace;


public class SafePlaces implements APICredentials{
    public static void main(String[] args) throws ResponseException {
        SafePlace[] safetyScore = amadeus.safety.safetyRatedLocations.get(Params
                .with("latitude", "41.39715")
                .and("longitude", "2.160873"));

        if(safetyScore[0].getResponse().getStatusCode() != 200) {
            System.out.println("Wrong status code: " + safetyScore[0].getResponse().getStatusCode());
            System.exit(-1);
        }
        System.out.println(safetyScore[0]);
    }
}
