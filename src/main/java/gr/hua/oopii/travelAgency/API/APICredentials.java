package gr.hua.oopii.travelAgency.API;

import com.amadeus.Amadeus;
import gr.hua.oopii.travelAgency.API.openWeather.OpenWeatherMap;

public interface APICredentials {
     Amadeus amadeus = Amadeus.builder("AxfOt4SwYsfHvBKoZgCTbc7PLtTyucVm","jMV9i4ZxaKUuaAvO").build();
     String openWeatherID = "a506d7877c77a7c49a31382e27104746";
     String airLabsID = "6af5994d-091b-4231-b3e3-087fcc8745a1";
}