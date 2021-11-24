package gr.hua.oopii.travelAgency;

import gr.hua.oopii.travelAgency.exception.*;
import gr.hua.oopii.travelAgency.openWeather.OpenWeatherMap;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronTraveler;
import gr.hua.oopii.travelAgency.openData.CountWords;
import gr.hua.oopii.travelAgency.openData.MediaWiki;
import gr.hua.oopii.travelAgency.openData.OpenData;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;

public class City implements Comparable<City> {
    private static final String[] wikiFeatures = new String[]{"cafe", "sea", "museum", "temple", "stadium", "bar", "park"};
    private static final float MAX_DISTANCE = 9517;             //Athens - Sydney distance

    private Date timestamp;
    private float[] features;
    private String name;
    private String countryName;


    public City(float[] features, String name, String countryName) {
        this.features = features;
        this.name = name;
        this.countryName = countryName;
        this.timestamp = new Date();
    }

    public City() {}

    public City(float geodesicDist) {
        this.features = new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, geodesicDist};
    }

    public City(String name, String countryName) {
        this(new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F}, name, countryName);
    }

    public static City unifiedDistRec(@NotNull PerceptronTraveler perceptron) throws NoRecommendationException {
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

    /* Normalise specific feature (term). Mode defines the type of normalisation:
     * 0==Wiki, 1==Weather, 2==Clouds, 3==GeodesicDistance
     */
    public static float normaliseFeature(float term, int mode) {
        float min, max;

        if (mode == 0 && term > 10) {
            term = 10;
        }

        if (mode == 0) {       //wiki normalisation
            min = 0;
            max = 10;
        } else {
            if (mode == 1) {       //weather normalisation
                min = 184;
                max = 331;
            } else {
                if (mode == 2) {  //clouds normalisation
                    min = 0;
                    max = 100;
                } else {
                    if (mode == 3) { //geodesicDistance
                        min = 0;
                        max = MAX_DISTANCE;      //geodesicDistance athens-sydney
                    } else {
                        return 0;
                    }
                }
            }
        }
        return (term - min) / (max - min);
    }

    //Normalise all features form City object
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

    //Returns geodesic distance in Miles
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

    //Downloads weather data and place it to citiesLibrary without changing other data at the library
    public static void setWeatherData(@NotNull ArrayList<City> citiesLibrary, @NotNull Control control) throws
            CitiesLibraryEmptyException, NoSuchCityException, NoInternetException {
        if (citiesLibrary.isEmpty()) {
            throw new CitiesLibraryEmptyException();
        }
        for (City city : citiesLibrary) {
            city.setWeatherData(control);
        }
    }

    public void setWeatherData(@NotNull Control control) throws NoSuchCityException, NoInternetException {
        try {
            //Gathering weather data
            OpenWeatherMap tempWeatherObj = OpenData.retrieveWeatherData(this.name, this.countryName);

            //Name formatting if needed
            if (!this.getName().equals(tempWeatherObj.getName())) {
                this.setName(tempWeatherObj.getName());
            }
            if (this.getCountryName().equals(tempWeatherObj.getSys().getCountry())) {
                this.setCountryName(tempWeatherObj.getSys().getCountry());
            }

            float[] tempFeatures = this.getFeatures();

            //Setting normalised data at feature[] of city object
            tempFeatures[7] = normaliseFeature((float) tempWeatherObj.getMain().getTemp(), 1);
            tempFeatures[8] = normaliseFeature((float) tempWeatherObj.getClouds().getAll(), 2);
            tempFeatures[9] = normaliseFeature((float) geodesicDistance(control.getOfficeLat(), control.getOfficeLon(), tempWeatherObj.getCoord().getLat(), tempWeatherObj.getCoord().getLon()), 3);
            this.setFeatures(tempFeatures);

        } catch (FileNotFoundException e) {
            throw new NoSuchCityException(this.name, "OpenWeather");
        } catch (IOException e) {
            throw new NoInternetException("OpenWeather");
        }
    }


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

    public void setWikiData() throws NoSuchCityException, NoInternetException {
        float[] tempFeatures = this.getFeatures();
        int[] tempWikiFeatures = countWikiKeywords(this.getName());
        //Normalise and copy wiki features to city's features
        for (int featureIndex = 0; featureIndex < wikiFeatures.length; featureIndex++) {
            tempFeatures[featureIndex] = normaliseFeature((float) tempWikiFeatures[featureIndex], 0);
        }
        this.setFeatures(tempFeatures);
    }

    //Counts keywords shown to city's Wiki article for every feature
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
        return 0;
    }
}