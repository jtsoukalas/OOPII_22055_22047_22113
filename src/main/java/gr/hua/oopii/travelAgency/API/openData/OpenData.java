package gr.hua.oopii.travelAgency.API.openData;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;


/**
 * City description and weather information using OpenData with Jackson JSON processor.
 *
 * @author John Violos
 * @version 1.0
 * @since 29-2-2020
 */
public class OpenData {

    /**
     * Retrieves weather information, geotag (lan, lon) and a Wikipedia article for a given city.
     * <p>
     * city    The Wikipedia article and OpenWeatherMap city.
     * country The country initials (i.e. gr, it, de).
     * appid   Your API key of the OpenWeatherMap.
     */


//    public static OpenWeatherMap retrieveWeatherData(String city, String country) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + APICredentials.getOpenWeatherID() + ""), OpenWeatherMap.class);
//    }

    /*public static MediaWiki retrieveWikiData(String city) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city + "&format=json&formatversion=2"), MediaWiki.class);
    }*/
}