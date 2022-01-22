package gr.hua.oopii.travelAgency.API;

import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.Location;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.hua.oopii.travelAgency.API.airportsRadar.AirportsRadar;
import gr.hua.oopii.travelAgency.API.airportsRadar.SummariseAirport;
import gr.hua.oopii.travelAgency.API.iata.Airport;
import gr.hua.oopii.travelAgency.API.iata.Welcome;
import gr.hua.oopii.travelAgency.API.openData.MediaWiki;
import gr.hua.oopii.travelAgency.API.openWeather.OpenWeatherMap;
import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.exception.NoIataException;
import org.jetbrains.annotations.NotNull;
import gr.hua.oopii.travelAgency.API.airportsRadar.SummariseAirport;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.logging.FileHandler;

public class APICallers implements APICredentials {

    public static void main(String[] args) throws Exception {
        City origin = new City("Athens","GR");
        origin.setWeatherData();
        City destination = new City("Rome","IT");
        destination.setWeatherData();
      //  AirportsRadar test = retrieveIata(origin);

//        System.out.println(test.toString());
        //System.out.println(((Map<String,Object>)test.getAdditionalProperties().get("response")));
//        System.out.println("2"+test.getCities());
//        System.out.println("3"+test.getAirports());
        System.out.println(retrieveFlightData(origin,destination).toString());
        //retrieveIata(origin);
    }

    public static OpenWeatherMap retrieveWeatherData(String city, String country) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + openWeatherID + ""), OpenWeatherMap.class);
    }

    public static MediaWiki retrieveWikiData(String city) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city + "&format=json&formatversion=2"), MediaWiki.class);
    }

    public static String retrieveIata(City city) throws IOException, NoIataException {
        ObjectMapper mapper = new ObjectMapper();

        AirportsRadar test = mapper.readValue(new URL("https://airlabs.co/api/v9/nearby?lat="+city.getLat()+"&lng="+city.getLon()+"&distance=100"+"&api_key="+airLabsID), AirportsRadar.class);;

        String src = ((Map<String,Object>)test.getAdditionalProperties().get("response")).get("airports").toString();
        System.out.println(src);
        String[] subStrings = src.split(",");
        for (int i = 0; i < subStrings.length; i++){
            if (subStrings[i].contains("iata_code")){
                return subStrings[i].split("=")[1];
            }
        }
        throw new NoIataException("No IATA code found for "+city.getName());
    }

    public static FlightOfferSearch retrieveFlightData(@NotNull City originLocation, @NotNull City destinationLocation) throws ResponseException, IOException, NoIataException {

        String originAirportIata = retrieveIata(originLocation);
        String destinationAirportIata = retrieveIata(destinationLocation);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        System.out.println("Origin "+originAirportIata+" dest "+destinationAirportIata);

        FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                Params.with("originLocationCode", originAirportIata)
                        .and("destinationLocationCode", destinationAirportIata)
                        .and("departureDate", "2022-01-24")
                        .and("adults", 1)
                        .and("max", 1));

        if (flightOffersSearches[0].getResponse().getStatusCode() != 200) {
            throw new ResponseException(flightOffersSearches[0].getResponse());
        }
        return flightOffersSearches[0];
    }
}
