package gr.hua.oopii.travelAgency;

import gr.hua.oopii.travelAgency.API.APICallers;
import gr.hua.oopii.travelAgency.API.covidRestrictions.CovidRestrictions;
import gr.hua.oopii.travelAgency.exception.*;
import gr.hua.oopii.travelAgency.API.openData.CountWords;
import gr.hua.oopii.travelAgency.API.openData.MediaWiki;
import gr.hua.oopii.travelAgency.API.openWeather.OpenWeatherMap;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronTraveler;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newCachedThreadPool;

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
    private Double lon, lat;

    /**
     * Timestamp is the point of time when a city is created
     */
    private Date timestamp;
    private float[] features;
    private Date weatherDownloadTimestamp;

    public static int WikiProcessCount = 0;        //4 DEBUGGING reasons
    public static int WeatherProcessCount = 0;        //4 DEBUGGING reasons

    public enum Recommendation_Present_Headers {
        RISK_LEVEL(0), TRANSPORTATION_TEXT(1),
        TRANSPORTATION_BAN(2), ENTRY_TEXT(3),
        ENTRY_BAN(4), ENTRY_LINK(5),
        QUARANTINE_TEXT(6), QUARANTINE_DURATION(7),
        QUARANTINE_LINK(8), MASK_TEXT(9), MASK_REQUIRED(10), BODY(11), INFECTION_MAP_LINK(12);

        private int index;

        Recommendation_Present_Headers(int index) {
            this.index = index;
        }

        public int getIndex() {
            return this.index;
        }
    }

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
    public City(float[] features, String name, String countryName, Double lon, Double lat) {
        this.features = features;
        this.name = name;
        this.countryName = countryName;
        this.timestamp = new Date();
        this.weatherDownloadTimestamp = new Date();
        this.lon = lon;
        this.lat = lat;
    }

    /**
     * <h1> Constructor only with geodesic distance </h1>
     * The other attributes will be set at default values  The other attributes will be set at default values
     *
     * @param geodesicDist float
     */
    public City(float geodesicDist) {
        this(new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, geodesicDist}, null, null, null, null);
    }

    /**
     * <h1> Consructor with city name and country name</h1>
     *
     * @param name
     * @param countryName
     */
    public City(String name, String countryName) {
        this(new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F}, name, countryName, null, null);
    }

    public City(String name ) {
        this(new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F}, name, null, null, null);
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
     *             <u>0==Wiki, 1==Weather, 2==Clouds, 3==GeodesicDistance, 4==Custom Weights </u>
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
                if (mode == 2 || mode == 4) {                    //clouds normalisation or weight normalisation from gui
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
     * //todo needs update
     * <h1>Creates a string to represent the recommendations<h1/>
     * Takes compatible cities names from each city Object and appends them to a string
     *
     * @return a string with all the recommendations
     * @throws NoRecommendationException
     */
    public String[] presentRecommendation() throws NoCovidRestrictionsException {

        String[] res = new String[Recommendation_Present_Headers.values().length];
        CovidRestrictions covidRestrictions = null;
        try {
            covidRestrictions = APICallers.retrieveCovidRestrictions(this);


            res[Recommendation_Present_Headers.RISK_LEVEL.index] = covidRestrictions.getData().getDiseaseRiskLevel();

            res[Recommendation_Present_Headers.TRANSPORTATION_BAN.index] = covidRestrictions.getData().getAreaAccessRestriction().getTransportation().getIsBanned();
            res[Recommendation_Present_Headers.TRANSPORTATION_TEXT.index] = covidRestrictions.getData().getAreaAccessRestriction().getTransportation().getText();
            try {
                res[Recommendation_Present_Headers.QUARANTINE_DURATION.index] = covidRestrictions.getData().getAreaAccessRestriction().getQuarantineModality().getAdditionalProperties().get("duration").toString();
            } catch (NullPointerException e) {
                res[Recommendation_Present_Headers.QUARANTINE_DURATION.index] = "";
            }

            res[Recommendation_Present_Headers.MASK_REQUIRED.index] = covidRestrictions.getData().getAreaAccessRestriction().getMask().getIsRequired();
            try {
                res[Recommendation_Present_Headers.MASK_TEXT.index] = covidRestrictions.getData().getAreaAccessRestriction().getMask().getText();
            } catch (NullPointerException e) {
                res[Recommendation_Present_Headers.MASK_TEXT.index] = "";
            }
            res[Recommendation_Present_Headers.QUARANTINE_LINK.index] = covidRestrictions.getData().getAreaAccessRestriction().getQuarantineModality().getRules();
            res[Recommendation_Present_Headers.QUARANTINE_TEXT.index] = covidRestrictions.getData().getAreaAccessRestriction().getQuarantineModality().getText();
            res[Recommendation_Present_Headers.ENTRY_TEXT.index] = covidRestrictions.getData().getAreaAccessRestriction().getEntry().getText();
            res[Recommendation_Present_Headers.ENTRY_BAN.index] = covidRestrictions.getData().getAreaAccessRestriction().getEntry().getBan();
            res[Recommendation_Present_Headers.ENTRY_LINK.index] = covidRestrictions.getData().getAreaAccessRestriction().getEntry().getRules();
            res[Recommendation_Present_Headers.INFECTION_MAP_LINK.index] = covidRestrictions.getData().getDiseaseInfection().getInfectionMapLink();

            res[Recommendation_Present_Headers.BODY.index] = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\" />\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\" />\n" +
                    "    <link rel=\"stylesheet\" href=\"index.css\" />\n" +
                    "    <title>OOPI Java II</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            background-color: lightgray;\n" +
                    "            font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif;\n" +
                    "            font-size: 12px; "+
                    "        }\n" +
                    "        \n" +
                    "        p {\n" +
                    "            margin-top: 0px;\n" +
                    "            color: rgb(48, 57, 59);\n" +
                    "        }\n" +
                    "        \n" +
                    "        table {\n" +
                    "            border: 1px solid rgb(48, 57, 59);\n" +
                    "            border-color: 1px solid rgb(121, 48, 48);\n" +
                    "            width: 50%;\n" +
                    "            float: right;\n" +
                    "            margin-left: 1%;\n" +
                    "            margin-right: 9px;\n" +
                    "            margin-bottom: 1%;\n" +
                    "            margin-top: 10%;\n" +
                    "            color: rgb(48, 57, 59);\n" +
                    "        }\n" +
                    "        \n" +
                    "        .first_column {\n" +
                    "            margin: 0px;\n" +
                    "            border: solid black;\n" +
                    "        }\n" +
                    "        \n" +
                    "        .second_column {\n" +
                    "            margin: 0px;\n" +
                    "            border: solid black;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        \n" +
                    "        .subtitle {\n" +
                    "            color: black;\n" +
                    "            margin-bottom: 5px"+
                    "        }\n" +
                    "        \n" +
                    "        #alert_emoji {\n" +
                    "            position: absolute;\n" +
                    "            float: left;\n" +
                    "            top: -25px;\n" +
                    "            font-size: 30px"+
                    "        }\n" +
                    "        \n" +
                    "        #covid_restriction_title {\n" +
                    "            position: absolute;\n" +
                    "            top: -3px;\n" +
                    "            text-align: left;\n" +
                    "            margin-left: 16px;\n" +
                    "        }\n" +
                    "       #transportation{\n" +
                    "            margin-top: 5px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "    <h1 id=\"alert_emoji\">&#128680;</h1>\n" +
                    "    <h3 id=\"covid_restriction_title\">Covid Restrictions</h3>\n" +
                    "    <br /><br />\n" +
                    "    <table>\n" +
                    "        <tr>\n" +
                    "            <td class=\"first_column\">Covid Risk Level</td>\n" +
                    "            <td class=\"second_column\">"+res[Recommendation_Present_Headers.RISK_LEVEL.index]+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td class=\"first_column\">Entry Ban</td>\n" +
                    "            <td class=\"second_column\">"+res[Recommendation_Present_Headers.ENTRY_BAN.index]+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td class=\"first_column\">Quarantine Duration</td>\n" +
                    "            <td class=\"second_column\">"+res[Recommendation_Present_Headers.QUARANTINE_DURATION.index]+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td class=\"first_column\">Mask Requirement</td>\n" +
                    "            <td class=\"second_column\">"+res[Recommendation_Present_Headers.MASK_REQUIRED.index]+"</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td class=\"first_column\">Infection Map</td>\n" +
                    "            <td class=\"second_column\"><a href=\""+res[Recommendation_Present_Headers.INFECTION_MAP_LINK.index]+"\">View</a></td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "    <h4 class=\"subtitle\" id=\"transportation\" >\n" +
                    "         Transportation\n" +
                    "    </h4>\n" +
                    "    <p style=\"margin-top: 0px\">\n" +
                    "        "+res[Recommendation_Present_Headers.TRANSPORTATION_TEXT.index]+
                    "    </p>\n" +
                    "    <h4 class=\"subtitle\">\n" +
                    "        <a class=\"subtitle\" href=\""+res[Recommendation_Present_Headers.ENTRY_LINK.index]+"\">Entry</a>\n" +
                    "    </h4>\n" +
                    "    <p>\n" +
                    "        "+res[Recommendation_Present_Headers.ENTRY_TEXT.index]+
                    "    </p>\n" +
                    "    <h4 class=\"subtitle\">\n" +
                    "        <a class=\"subtitle\""+res[Recommendation_Present_Headers.QUARANTINE_LINK.index]+"\">Quarantine</a>\n" +
                    "    </h4>\n" +
                    "    <p style=\"margin-top: 0px\">\n" +
                    "        "+res[Recommendation_Present_Headers.QUARANTINE_TEXT.index]+
                    "    </p>\n" +
                    "    <h4 class=\"subtitle\">\n" +
                    "        Mask Restrictions\n" +
                    "    </h4>\n" +
                    "    <p>\n" +
                    "        "+res[Recommendation_Present_Headers.MASK_TEXT.index]+
                    "    </p>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";

        } catch (NoCovidRestrictionsException e) {
            Control.mainLogger.warning("Retrieving covid restrictions failed: " + e.getCity());
            throw e;
        } catch (IOException e) {
            Control.mainLogger.warning("Retrieving covid restrictions failed: " + e.getMessage());
            throw new NoCovidRestrictionsException();
        }

        return res;
    }

    /**
     * <h1> Collects weather data for a city</h1>
     * Calls open weather map API for a city, sets the appropriate weather features after normalising them with {@link #normaliseFeature}
     *
     * @throws NoSuchCityException if city wasn't found at API(s)
     * @throws NoInternetException if there is no connection with API(s)
     */
    public void setWeatherData() throws NoSuchCityException, NoInternetException {
        try {
            //Gathering weather data
            OpenWeatherMap tempWeatherObj = APICallers.retrieveWeatherData(this.name, this.countryName);

            //Name formatting if needed
            if (!this.name.equals(tempWeatherObj.getName())) {
                this.name = tempWeatherObj.getName();
            }
            if (!this.countryName.equals(tempWeatherObj.getSys().getCountry())) {
                this.countryName = tempWeatherObj.getSys().getCountry();
            }

            float[] tempFeatures = this.features;
            this.lon = tempWeatherObj.getCoord().getLon();
            this.lat = tempWeatherObj.getCoord().getLat();

            //Setting normalised data at feature[] of city object
            tempFeatures[7] = normaliseFeature((float) tempWeatherObj.getMain().getTemp(), 1);
            tempFeatures[8] = normaliseFeature((float) tempWeatherObj.getClouds().getAll(), 2);
            tempFeatures[9] = normaliseFeature((float) geodesicDistance(Control.getUserLat(), Control.getUserLon(), lat, lon), 3);
            this.features = tempFeatures;
            this.weatherDownloadTimestamp = new Date();

        } catch (FileNotFoundException e) {
            throw new NoSuchCityException(this.name, "OpenWeather");
        } catch (IOException e) {
            throw new NoInternetException("OpenWeather");
        }
    }

    /**
     * <h1>Creates a {@link Runnable} object for downloading weather data</h1>
     * {@link Runnable#run()} method
     * @deprecated
     *
     * @return
     */
    private Runnable createRunnableObjForWeatherDownload() {
        City city = this;
        int pid = WeatherProcessCount++;
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello from process: " + pid);      //4 dubbing reasons
                try {
                    city.setWeatherData();
                } catch (NoSuchCityException e) {
                    e.printStackTrace();
                } catch (NoInternetException e) {
                    e.printStackTrace();
                }
                /*try {
                    //Gathering weather data
                    OpenWeatherMap tempWeatherObj = OpenData.retrieveWeatherData(city.name, city.countryName);


                    //Name formatting if needed
                    if (!city.name.equals(tempWeatherObj.getName())) {
                        city.name = tempWeatherObj.getName();
                    }
                    if (!city.countryName.equals(tempWeatherObj.getSys().getCountry())) {
                        city.countryName = tempWeatherObj.getSys().getCountry();
                    }

                    float[] tempFeatures = city.features;

                    //Setting normalised data at feature[] of city object
                    tempFeatures[7] = normaliseFeature((float) tempWeatherObj.getMain().getTemp(), 1);
                    tempFeatures[8] = normaliseFeature((float) tempWeatherObj.getClouds().getAll(), 2);
                    tempFeatures[9] = normaliseFeature((float) geodesicDistance(Control.getUserLat(), Control.getUserLon(), tempWeatherObj.getCoord().getLat(), tempWeatherObj.getCoord().getLon()), 3);
                    city.features = tempFeatures;
                    city.weatherDownloadTimestamp = new Date();

                } catch (FileNotFoundException e) {
                    try {
                        throw new NoSuchCityException(city.name, "OpenWeather");
                    } catch (NoSuchCityException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException e) {
                    try {
                        throw new NoInternetException("OpenWeather");
                    } catch (NoInternetException ex) {
                        ex.printStackTrace();
                    }
                }*/
            }
        };
    }

    /**
     * <h1>Creates a callable object for downloading weather data</h1>
     * @return a callable object for downloading weather data
     */
    private Callable<Void> createCallableObjForWeatherDownload() {
        City city = this;
        int pid = WeatherProcessCount++;

        return () -> {
            city.setWeatherData();
            return null;
        };
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
    public static void setWeatherData(@NotNull ArrayList<City> citiesLibrary) throws CitiesLibraryEmptyException, NoSuchCityException, NoInternetException {

        if (citiesLibrary.isEmpty()) {
            throw new CitiesLibraryEmptyException();
        }

        ExecutorService executorService = newCachedThreadPool();
        ArrayList<Future<Void>> futures = new ArrayList<>();

        //For each city, make a process and submit (add & execute) to the thread pool
        for (City city : citiesLibrary) {
            futures.add(executorService.submit(city.createCallableObjForWeatherDownload()));
        }

        //For every process that submitted before, check the outcome (if any exception was thrown)
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause.getClass().isAssignableFrom(NoInternetException.class)) {
                    throw (NoInternetException) cause;
                } else if (cause.getClass().isAssignableFrom(NoSuchCityException.class)) {
                    throw (NoInternetException) cause;
                }
            }
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

    private Callable<Void> createCallableObjForWikiDownload() {
        City city = this;
        int pid = WikiProcessCount++;

        return () -> {
            city.setWikiData();
            return null;
        };
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

        ExecutorService executorService = newCachedThreadPool();
        ArrayList<Future<Void>> futures = new ArrayList<>();

        //For each city, make a process and submit (add & execute) to the thread pool
        for (City city : citiesLibrary) {
            //city.setWikiData();
            futures.add(executorService.submit(city.createCallableObjForWikiDownload()));
        }

        //For every process that submitted before, check the outcome (if any exception was thrown)
        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause.getClass().isAssignableFrom(NoInternetException.class)) {
                    throw (NoInternetException) cause;
                } else if (cause.getClass().isAssignableFrom(NoSuchCityException.class)) {
                    throw (NoInternetException) cause;
                }
            }
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
            MediaWiki wikiObj = APICallers.retrieveWikiData(city);
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

    public Double getLon() {
        return lon;
    }

    public Double getLat() {
        return lat;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.getName().equalsIgnoreCase(((City) obj).getName());
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
            clone.timestamp = (Date) timestamp.clone();
            clone.features = features;
            clone.weatherDownloadTimestamp = (Date) weatherDownloadTimestamp.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}