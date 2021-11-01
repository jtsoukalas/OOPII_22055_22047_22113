package firstDeliverable.openData;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;
import firstDeliverable.City;
import firstDeliverable.openWeather.OpenWeatherMap;


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
     * @param city    The Wikipedia article and OpenWeatherMap city.
     * @param country The country initials (i.e. gr, it, de).
     * @param appid   Your API key of the OpenWeatherMap.
     */
    private static final String appid = "a506d7877c77a7c49a31382e27104746";

    public static void RetrieveData(String city, String country) throws IOException {


        float geodesicDist = (float) City.normaliseFeature(City.geodesicDistance(37.983810,23.727539,  weather_obj.getCoord().getLat(), weather_obj.getCoord().getLon()), 3);
        double temp = weather_obj.getMain().getTemp();
        City cityObj = new City(city, (float)temp, geodesicDist );

    }

    public OpenWeatherMap retrieveWeatherData(String city, String country) throws IOException{

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("http://api.openweathermap.org/data/2.5/weather?q=" + city + "," + country + "&APPID=" + appid + ""), OpenWeatherMap.class);
        //System.out.println(city + " temperature: " + (weather_obj.getMain()).getTemp());
        //System.out.println(city + " lat: " + weather_obj.getCoord().getLat() + " lon: " + weather_obj.getCoord().getLon());

    }

    public MediaWiki retrieveWikiData(String city, String country)throws IOException{

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new URL("https://en.wikipedia.org/w/api.php?action=query&prop=extracts&titles=" + city + "&format=json&formatversion=2"), MediaWiki.class);
        //System.out.println(city + " Wikipedia article: " + mediaWiki_obj.getQuery().getPages().get(0).getExtract());

    }


    public static void main(String[] args) throws IOException {

        RetrieveData("Rome", "it");
        RetrieveData("Athens", "gr");
        RetrieveData("Corfu", "gr");
        RetrieveData("Berlin", "de");
    }

}