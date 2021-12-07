package gr.hua.oopii.travelAgency.openData;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.hua.oopii.travelAgency.openWeather.OpenWeatherMap;


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
     *
     * city    The Wikipedia article and OpenWeatherMap city.
     * country The country initials (i.e. gr, it, de).
     * appid   Your API key of the OpenWeatherMap.
     */
    private static final String appid = "a506d7877c77a7c49a31382e27104746";

    /*public static void RetrieveData(String city, String country) throws IOException {
        float geodesicDist = (float) City.normaliseFeature(
        double temp = weather_obj.getMain().getTemp();
        City cityObj = new City(city, (float)temp, geodesicDist );

    }*/

    public static OpenWeatherMap retrieveWeatherData(String city, String country) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appid + ""), OpenWeatherMap.class);
    }

    public static MediaWiki retrieveWikiData(String city)throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city + "&format=json&formatversion=2"), MediaWiki.class);
    }
}