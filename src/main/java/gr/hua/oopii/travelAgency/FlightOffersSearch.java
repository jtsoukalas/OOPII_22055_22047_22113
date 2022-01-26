package gr.hua.oopii.travelAgency;
// How to install the library at https://github.com/amadeus4dev/amadeus-java

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;

public class FlightOffersSearch {

    public static void main(String[] args) throws ResponseException {

        Amadeus amadeus = Amadeus
                .builder("AxfOt4SwYsfHvBKoZgCTbc7PLtTyucVm","jMV9i4ZxaKUuaAvO")
                .build();

        FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", "SYD")
                        .and("destinationLocationCode", "BKK")
                        .and("departureDate", "2022-11-01")
                        .and("returnDate", "2022-11-08")
                        .and("adults", 1)
                        .and("max", 10));

        if (flightOffersSearches[0].getResponse().getStatusCode() != 200) {
            System.out.println("Wrong status code: " + flightOffersSearches[0].getResponse().getStatusCode());
            System.exit(-1);
        }

        System.out.println(flightOffersSearches[0]);
    }
}