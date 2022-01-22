package gr.hua.oopii.travelAgency.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.hua.oopii.travelAgency.API.cityIATA.CityIATum;
import gr.hua.oopii.travelAgency.API.openData.MediaWiki;
import gr.hua.oopii.travelAgency.API.openWeather.OpenWeatherMap;
import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.exception.NoIataException;

import java.io.IOException;
import java.net.URL;

public class APICallers implements APICredentials {

    public static void main(String[] args) throws Exception {
        City origin = new City("Athens", "GR");
        origin.setWeatherData();
        City destination = new City("Rome", "IT");
        destination.setWeatherData();
        retrieveCityIataCode(destination);

//        System.out.println(test.toString());
        //System.out.println(((Map<String,Object>)test.getAdditionalProperties().get("response")));
//        System.out.println("2"+test.getCities());
//        System.out.println("3"+test.getAirports());
        // System.out.println(retrieveFlightData(origin,destination).toString());
        //retrieveCityIataCode(origin);
    }

    public static OpenWeatherMap retrieveWeatherData(String city, String country) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + openWeatherID + ""), OpenWeatherMap.class);
    }

    public static MediaWiki retrieveWikiData(String city) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city + "&format=json&formatversion=2"), MediaWiki.class);
    }

    public static CityNode retrieveCityIataCode(City city) throws IOException, NoIataException {
        ObjectMapper mapper = new ObjectMapper();

        CityIATum test = mapper.readValue(new URL("https://airlabs.co/api/v9/cities?country_code=" + city.getCountryName() + "&api_key=" + airLabsID), CityIATum.class);

        String src = (test.getAdditionalProperties().get("response").toString());

        String[] subStrings = src.split(",");

        for (int i = 0; i < subStrings.length; i++) {
            CityNode node = new CityNode();
            if (subStrings[i].contains("name") && subStrings[i].contains(city.getName())) {
                node.setName(subStrings[i].split("=")[1]);
            }
            try {
                if (subStrings[i + 1].contains("city_code")) {
                    node.setIataCode(subStrings[i + 1].split("=")[1]);
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            }
            if (node.getName() != null && node.getIataCode() != null) {
                return node;
            }
        }
        throw new NoIataException("No IATA code found for " + city.getCountryName());
    }

    /*public static FlightOfferSearch retrieveFlightData(@NotNull City originLocation, @NotNull City destinationLocation) throws ResponseException, IOException, NoIataException {

        String originAirportIata = retrieveCityIataCode(originLocation);
        String destinationAirportIata = retrieveCityIataCode(destinationLocation);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        System.out.println("Origin " + originAirportIata + " dest " + destinationAirportIata);

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
    }*/

    private static class CityNode {
        private String name;
        private String iataCode;

        public CityNode() {
        }

        public CityNode(String name, String iataCode) {
            this.name = name;
            this.iataCode = iataCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIataCode() {
            return iataCode;
        }

        public void setIataCode(String iataCode) {
            this.iataCode = iataCode;
        }

        @Override
        public String toString() {
            return "CityNode{" +
                    "name='" + name + '\'' +
                    ", iataCode='" + iataCode + '\'' +
                    '}';
        }
    }
}
