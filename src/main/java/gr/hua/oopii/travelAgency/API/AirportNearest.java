package gr.hua.oopii.travelAgency.API;

// How to install the library at https://github.com/amadeus4dev/amadeus-java

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.Location;

public class AirportNearest implements APICredentials {

    public static void main(String[] args) throws ResponseException  {

        // Airport Nearest Relevant (for London)
        Location[] locations = amadeus.referenceData.locations.airports.get(Params
                .with("latitude", 41.9028)
                .and("longitude", 12.4964));

        if(locations[0].getResponse().getStatusCode() != 200) {
            System.out.println("Wrong status code: " + locations[0].getResponse().getStatusCode());
            System.exit(-1);
        }
        System.out.println(locations[0]);
    }
}
