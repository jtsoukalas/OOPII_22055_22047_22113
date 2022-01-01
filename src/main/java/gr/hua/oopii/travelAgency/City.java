package gr.hua.oopii.travelAgency;

import gr.hua.oopii.travelAgency.exception.*;
import gr.hua.oopii.travelAgency.openData.CountWords;
import gr.hua.oopii.travelAgency.openData.MediaWiki;
import gr.hua.oopii.travelAgency.openData.OpenData;
import gr.hua.oopii.travelAgency.openWeather.OpenWeatherMap;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronTraveler;
import org.controlsfx.tools.Duplicatable;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * <h1> Represents a real city and its data</h1>
 * Contains methods for: normalising features, downloading data, calculating distances.
 *
 * @author Orestis Kritsotakis
 * @author Konstantinos Kokkalis
 * @author Iasonas Tsoukalas
 * @version 0
 * @since firstDeliverable
 */
public class City implements Comparable<City>, Cloneable {
    private static final String[] wikiFeatures = new String[]{"cafe", "sea", "museum", "temple", "stadium", "bar", "park"};
    private static final float MAX_DISTANCE = 9517;             //Athens - Sydney distance


    private String name;
    private String countryName;

    /**
     * Timestamp is the point of time when a city is created
     */
    private Date timestamp;
    private float[] features;
    private Date weatherDownloadTimestamp;

    /**
     * <h1> Empty constructor</h1>
     */
    public City() {
    }

    /**
     * <h1> Main constructor</h1>
     *
     * @param features
     * @param name
     * @param countryName
     */
    public City(float[] features, String name, String countryName) {
        this.features = features;
        this.name = name;
        this.countryName = countryName;
        this.timestamp = new Date();
        this.weatherDownloadTimestamp = new Date();
    }

    /**
     * <h1> Constructor only with geodesic distance </h1>
     * The other attributes will be set at default values  The other attributes will be set at default values
     *
     * @param geodesicDist float
     */
    public City(float geodesicDist) {
        this(new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, geodesicDist}, null, null);
    }

    /**
     * <h1> Consructor with city name and country name</h1>
     *
     * @param name
     * @param countryName
     */
    public City(String name, String countryName) {
        this(new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F}, name, countryName);
    }

    /**
     * <h1> Finds the closest recommendation</h1>
     *
     * @param perceptron passed to getLastRecommendation
     * @return the closest City object
     * @throws NoRecommendationException if there is no recommendations
     */
    public static City findClosestRec(@NotNull PerceptronTraveler perceptron) throws NoRecommendationException {
        ArrayList<City> citiesToCompare = perceptron.getLastRecommendation();

        if (citiesToCompare.isEmpty()) {
            throw new NoRecommendationException();
        }
        City min = new City(normaliseFeature(MAX_DISTANCE, 3));
        for (City city : citiesToCompare) {
            if (min.getFeatures()[9] > city.getFeatures()[9] && city.getFeatures()[9] != 0) {
                min = city;
            }
        }
        return min;
    }

    /**
     * <h1> Normalise one term </h1>
     *
     * @param mode defines the type of normalisation:
     *             <u>0==Wiki, 1==Weather, 2==Clouds, 3==GeodesicDistance </u>
     * @param term float that will be normalised
     */
    public static float normaliseFeature(float term, int mode) {
        float min, max;

        if (mode == 0 && term > 10) {
            term = 10;
        }

        if (mode == 0) {                            //wiki normalisation
            min = 0;
            max = 10;
        } else {
            if (mode == 1) {                        //weather normalisation
                min = 184;
                max = 331;
            } else {
                if (mode == 2) {                    //clouds normalisation
                    min = 0;
                    max = 100;
                } else {
                    if (mode == 3) {                //geodesicDistance
                        min = 0;
                        max = MAX_DISTANCE;         //geodesicDistance athens-sydney
                    } else {
                       throw new IllegalArgumentException();
                    }
                }
            }
        }
        return (term - min) / (max - min);
    }

    /**
     * <h1>Normalise all features for this City object</h1>
     * Uses: {@link City#normaliseFeature(float, int)}
     */
    public void normaliseFeature() {
        for (int featureIndex = 0; featureIndex < 10; featureIndex++) {
            if (featureIndex < 7) {                   //wiki normalisation
                features[featureIndex] = normaliseFeature(features[featureIndex], 0);
            } else {
                if (featureIndex == 7) {              //weather normalisation
                    features[featureIndex] = normaliseFeature(features[featureIndex], 1);
                } else {
                    if (featureIndex == 8) {          //clouds normalisation
                        features[featureIndex] = normaliseFeature(features[featureIndex], 2);
                    } else {                            //distance normalisation
                        features[featureIndex] = normaliseFeature(features[featureIndex], 3);
                    }
                }
            }
        }
    }

    /**
     * <h1> Calculates geodesic distance between two cities</h1>
     *
     * @param lat1 first city latitude
     * @param lon1 first city longitude
     * @param lat2 second city latitude
     * @param lon2 second city longitude
     * @return geodesic distance between two cities in Miles
     * @author Code cource: https://www.geodatasource.com/developers/java
     */

    public static double geodesicDistance(double lat1, double lon1, double lat2, double lon2) {        //Code form: https://www.geodatasource.com/developers/java
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist);
        }
    }

    /**
     * <h1> Collects weather data for a city</h1>
     * Calls open weather map API for a city, sets the appropriate weather features after normalising them with {@link #normaliseFeature}
     *
     *
     * @throws NoSuchCityException if city wasn't found at API(s)
     * @throws NoInternetException if there is no connection with API(s)
     */
    public void setWeatherData() throws NoSuchCityException, NoInternetException {
        try {
            //Gathering weather data
            OpenWeatherMap tempWeatherObj = OpenData.retrieveWeatherData(this.name, this.countryName);

            //Name formatting if needed
            if (!this.name.equals(tempWeatherObj.getName())) {
                this.name = tempWeatherObj.getName();
            }
            if (!this.countryName.equals(tempWeatherObj.getSys().getCountry())) {
                this.countryName = tempWeatherObj.getSys().getCountry();
            }

            float[] tempFeatures = this.features;

            //Setting normalised data at feature[] of city object
            tempFeatures[7] = normaliseFeature((float) tempWeatherObj.getMain().getTemp(), 1);
            tempFeatures[8] = normaliseFeature((float) tempWeatherObj.getClouds().getAll(), 2);
            tempFeatures[9] = normaliseFeature((float) geodesicDistance(Control.getUserLat(), Control.getUserLon(), tempWeatherObj.getCoord().getLat(), tempWeatherObj.getCoord().getLon()), 3);
            this.features = tempFeatures;
            this.weatherDownloadTimestamp = new Date();

        } catch (FileNotFoundException e) {
            throw new NoSuchCityException(this.name, "OpenWeather");
        } catch (IOException e) {
            throw new NoInternetException("OpenWeather");
        }
    }

    /**
     * <h1> Downloads weather data for each city at citiesLibrary</h1>
     * Uses: {@link #setWeatherData()}
     *
     * @param citiesLibrary ArrayList<City>
     * @throws CitiesLibraryEmptyException if there is no cities at citiesLibrary
     * @throws NoSuchCityException         if city wasn't found at API(s)
     * @throws NoInternetException         if there is no connection with API(s)
     */
    public static void setWeatherData(@NotNull ArrayList<City> citiesLibrary) throws
            CitiesLibraryEmptyException, NoSuchCityException, NoInternetException {

        if (citiesLibrary.isEmpty()) {
            throw new CitiesLibraryEmptyException();
        }
        for (City city : citiesLibrary) {
            city.setWeatherData();
        }
    }

    /**
     * <h1>Downloads wiki data for a city </h1>
     * Sets on tempFeatures all the Features of the certain City
     * Sets on tempWikiFeatures the amount of each keyword from wikiFeatures by calling the countWikiKeywords(the name of the certain city)
     * Sets the new data on tempFeatures and normalise them depending on the mode and the amount of each keyword
     * Sets the new tempFeatures to features
     *
     * @throws NoSuchCityException
     * @throws NoInternetException
     */
    public void setWikiData() throws NoSuchCityException, NoInternetException {
        float[] tempFeatures = this.features;
        int[] tempWikiFeatures = countWikiKeywords(this.name);

        //Normalise and copy wiki features to city's features
        for (int featureIndex = 0; featureIndex < wikiFeatures.length; featureIndex++) {
            tempFeatures[featureIndex] = normaliseFeature((float) tempWikiFeatures[featureIndex], 0);
        }
        this.features = tempFeatures;
    }

    /**
     * <h1> Downloads wiki data for each city at citiesLibrary</h1>
     * Uses: {@link City#setWeatherData()}
     *
     * @param citiesLibrary ArrayList<City>
     * @throws CitiesLibraryEmptyException if there is no cities at citiesLibrary
     * @throws NoSuchCityException         if city wasn't found at API(s)
     * @throws NoInternetException         if there is no connection with API(s)
     */
    //Downloads wiki data and place it to citiesLibrary without changing other data at the library
    public static void setWikiData(@NotNull ArrayList<City> citiesLibrary) throws
            CitiesLibraryEmptyException, NoSuchCityException, NoInternetException {

        if (citiesLibrary.isEmpty()) {
            throw new CitiesLibraryEmptyException();
        }

        for (City city : citiesLibrary) {
            city.setWikiData();
        }
    }


    /**
     * <h1>Counts keywords shown to city's Wiki article for every feature.</h1>
     * Creates an object to retrieve wiki data of the City object that took as parameter
     * Creates a table(int[] tempFeatures) that contains the amount of each keyword and it's parallel to the wikiFeatures
     * and saves the amount of the words that have been counted on each shell of the tempFeatures table
     *
     * @param city
     * @return a table[] with the amount of the keywords of the City that took as parameter
     * @throws NoSuchCityException
     * @throws NoInternetException
     */
    public static int[] countWikiKeywords(String city) throws NoSuchCityException, NoInternetException {
        try {
            MediaWiki wikiObj = OpenData.retrieveWikiData(city);
            int[] tempFeature = new int[wikiFeatures.length];
            for (int featureIndex = 0; featureIndex < wikiFeatures.length; featureIndex++) {
                tempFeature[featureIndex] = CountWords.countCriterionfCity(wikiObj.toString(), wikiFeatures[featureIndex]);
            }
            return tempFeature;
        } catch (FileNotFoundException e) {
            throw new NoSuchCityException(city, "Wikipedia");
        } catch (IOException e) {
            throw new NoInternetException("Wikipedia");
        }
    }

    //TODO review setters & getters
    public float[] getFeatures() {
        return features;
    }

    public void setFeatures(float[] features) {
        this.features = features;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Date getWeatherDownloadTimestamp() {
        return weatherDownloadTimestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setWeatherDownloadTimestamp(Date weatherDownloadTimestamp) {
        this.weatherDownloadTimestamp = weatherDownloadTimestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.getName().equalsIgnoreCase(((City) obj).getName()) && this.getCountryName().equalsIgnoreCase(((City) obj).getCountryName());
    }

    @Override
    public String toString() {
        return name +
                "\t\t\t\t" + Arrays.toString(features);
    }

    @Override
    public int compareTo(@NotNull City o) {
        if (this.name.equalsIgnoreCase(o.name) && this.countryName.equalsIgnoreCase(o.countryName)) {
            return 1;
        }
        return 0;
    }

    @Override
    public City clone() {
        try {
            City clone = (City) super.clone();
            clone.name = name;
            clone.countryName = countryName;
            clone.timestamp= (Date) timestamp.clone();
            clone.features=features;
            clone.weatherDownloadTimestamp = (Date) weatherDownloadTimestamp.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}