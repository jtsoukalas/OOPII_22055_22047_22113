package gr.hua.oopii.travelAgency.API;


import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.referenceData.Locations;
import com.amadeus.resources.Location;

public class AirportCitySearch implements APICredentials{

    public static void main(String[] args) throws ResponseException {
        // Airport & City Search (autocomplete)
        // Find all the cities and airports starting by the keyword 'LON'
        Location[] locations = amadeus.referenceData.locations.get(Params
                .with("keyword", "BRUSSELS")
                .and("subType", Locations.CITY));

        if(locations[0].getResponse().getStatusCode() != 200) {
            System.out.println("Wrong status code: " + locations[0].getResponse().getStatusCode());
            System.exit(-1);
        }
        System.out.println(locations[0]);
    }
}
