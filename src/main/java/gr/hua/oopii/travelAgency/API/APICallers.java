package gr.hua.oopii.travelAgency.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.hua.oopii.travelAgency.API.covidRestrictions.CovidRestrictions;
import gr.hua.oopii.travelAgency.API.openData.MediaWiki;
import gr.hua.oopii.travelAgency.API.openWeather.OpenWeatherMap;
import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.exception.NoAirportException;
import gr.hua.oopii.travelAgency.exception.NoCovidRestrictionsException;
import gr.hua.oopii.travelAgency.exception.NoFlightException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class APICallers implements APICredentials {

    public static OpenWeatherMap retrieveWeatherData(String city, String country) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + openWeatherID + ""), OpenWeatherMap.class);
    }

    public static MediaWiki retrieveWikiData(String city) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city + "&format=json&formatversion=2"), MediaWiki.class);
    }

    public static CovidRestrictions retrieveCovidRestrictions(City city) throws IOException, NoCovidRestrictionsException {
        boolean secondTry = false;
        URL url = new URL("https://test.api.amadeus.com/v1/duty-of-care/diseases/covid19-area-report?countryCode=" + city.getCountryName());
        HttpURLConnection http;
        byte[] response;
        while (true) {
            http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", "Bearer " + amadeusAPIToken);

            if (http.getResponseCode() == 200) {
                response = http.getInputStream().readAllBytes();
                break;
            } else {
                if (secondTry) {
                    throw new NoCovidRestrictionsException(city.getName());
                }
                secondTry = true;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        CovidRestrictions covidRestrictions = mapper.readValue(response, CovidRestrictions.class);
        http.disconnect();
        return covidRestrictions;
    }

    //Not finished methods for flight cost
    {
        /*public static String retrieveIata (City city) throws IOException, NoAirportException {
        ObjectMapper mapper = new ObjectMapper();

        AirportsRadar test = mapper.readValue(new URL("https://airlabs.co/api/v9/nearby?lat=" + city.getLat() + "&lng=" + city.getLon() + "&distance=100" + "&api_key=" + airLabsID), AirportsRadar.class);
        ;

        String src = ((Map<String, Object>) test.getAdditionalProperties().get("response")).get("airports").toString();
        System.out.println(src);
        String[] subStrings = src.split(",");
        for (int i = 0; i < subStrings.length; i++) {
            if (subStrings[i].contains("iata_code")) {
                return subStrings[i].split("=")[1];
            }
        }
        throw new NoAirportException("No IATA code found for " + city.getName());
    }

        *//**
         * Retrieves every nearby airport's IATA code for given city
         *
         * @param city to search aiports
         * @return IATA codes for airports
         * @throws IOException
         * @throws NoAirportException if there is no airport found
         *//*
        public static ArrayList<String> retrieveAllAirportsIataCode (City city) throws IOException, NoAirportException {
        ObjectMapper mapper = new ObjectMapper();

        AirportsRadar airports = mapper.readValue(new URL("https://airlabs.co/api/v9/nearby?lat=" + city.getLat() + "&lng=" + city.getLon() + "&distance=100" + "&api_key=" + airLabsID), AirportsRadar.class);


        String response = ((Map<String, Object>) airports.getAdditionalProperties().get("response")).get("airports").toString();

        ArrayList<String> res = new ArrayList<>();

        String[] subStrings = response.split(",");
        for (int i = 0; i < subStrings.length; i++) {
            if (subStrings[i].contains("iata_code")) {
                res.add(subStrings[i].split("=")[1]);       //add iata code to res
            }
        }
        if (res.isEmpty()) {
            throw new NoAirportException("No IATA code found for " + city.getName());
        }
        return res;
    }
        public static String retrieveAllAirportsIataCode (City city) throws IOException, NoAirportException {
        ObjectMapper mapper = new ObjectMapper();

        AirportsRadar airports = mapper.readValue(new URL("https://airlabs.co/api/v9/nearby?lat=" + city.getLat() + "&lng=" + city.getLon() + "&distance=100" + "&api_key=" + airLabsID), AirportsRadar.class);


        String response = ((Map<String, Object>) airports.getAdditionalProperties().get("response")).get("airports").toString();

        String[] subStrings = response.split(",");
        for (int i = 0; i < subStrings.length; i++) {
            if (subStrings[i].contains("iata_code")) {
                return subStrings[i].split("=")[1];       //add iata code to res
            }
        }
        throw new NoAirportException();
    }

        *//**
         * Retrieves a flight offer for given cities.
         * Tries different combinations of origin and destination airports until a flight is found
         *
         * @param originLocation      t
         * @param destinationLocation
         * @return a flight offer
         * @throws IOException
         * @throws NoAirportException
         * @throws NoFlightException
         *//*
        public static FlightOfferSearch retrieveFlightData (@NotNull City originLocation, @NotNull City
        destinationLocation) throws IOException, NoAirportException, NoFlightException {
        ArrayList<String> originAirportIata = retrieveAllAirportsIataCode(originLocation);
        ArrayList<String> destinationAirportIata = retrieveAllAirportsIataCode(destinationLocation);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


        Iterator<String> destinationIterator = destinationAirportIata.iterator();
        Iterator<String> originIterator = originAirportIata.iterator();
        String currentOriginAirport = originIterator.hasNext() ? originIterator.next() : null;
        String currentDestinationAirport = destinationIterator.hasNext() ? destinationIterator.next() : null;

        while (true) {
            try {
                FlightOfferSearch[] flightOffersSearches = amadeus.shopping.flightOffersSearch.get(
                        Params.with("originLocationCode", currentOriginAirport)
                                .and("destinationLocationCode", currentDestinationAirport)
                                .and("departureDate", date)
                                .and("adults", 1)
                                .and("max", 1));

                if (flightOffersSearches[0].getResponse().getStatusCode() != 200) {
                    throw new ResponseException(flightOffersSearches[0].getResponse());
                }
                return flightOffersSearches[0];

            } catch (Exception e) {
                if (destinationIterator.hasNext()) {
                    currentDestinationAirport = destinationIterator.next();
                } else {
                    throw new NoFlightException(originLocation.getName(), destinationLocation.getName());
                }
            }
        }
    }

        public static FlightDate retrieveFlightData (@NotNull City originLocation, @NotNull City destinationLocation) throws
        IOException, NoAirportException, NoFlightException, ResponseException {
        String originAirportIata = retrieveAllAirportsIataCode(originLocation);
        String destinationAirportIata = retrieveAllAirportsIataCode(destinationLocation);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        FlightDate[] flightDates = amadeus.shopping.flightDates.get(Params
                .with("origin", "DCW")
                .and("destination", "NYC"));

        if (flightDates[0].getResponse().getStatusCode() != 200) {
            System.out.println("Wrong status code: " + flightDates[0].getResponse().getStatusCode());
            System.exit(-1);
        }


        return flightDates[0];

        //throw new NoFlightException(originLocation.getName(), destinationLocation.getName());
    }*/
    }

}
