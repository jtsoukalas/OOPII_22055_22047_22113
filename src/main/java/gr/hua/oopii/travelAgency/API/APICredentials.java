package gr.hua.oopii.travelAgency.API;

import com.amadeus.Amadeus;

public interface APICredentials {
     String amadeusAPIKey = "APIKey";
     String amadeusAPISecret = "APISecret";
     Amadeus amadeus = Amadeus.builder(amadeusAPIKey,amadeusAPISecret).build();
     String amadeusAPIToken = "AccessToken ";

     String openWeatherID = "OpenWeatherID";

     String airLabsID = "AirLabsID";

}
