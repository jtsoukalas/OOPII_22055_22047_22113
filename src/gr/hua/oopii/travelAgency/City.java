package gr.hua.oopii.travelAgency;

import gr.hua.oopii.travelAgency.openWeather.OpenWeatherMap;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronTraveler;
import gr.hua.oopii.travelAgency.exception.CitiesLibraryEmptyException;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;
import gr.hua.oopii.travelAgency.openData.CountWords;
import gr.hua.oopii.travelAgency.openData.MediaWiki;
import gr.hua.oopii.travelAgency.openData.OpenData;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class City {
    private static final String[] wikiFeatures = new String[]{"cafe", "sea", "museum", "temple","stadium", "bar", "park"};
    private static final float MAX_DISTANCE = 9517;             //Athens - Sydney distance

    private float[] features;
    private String name;
    private String countryName;


    public City(float[] features, String name, String countryName) {
        this.features = features;
        this.name = name;
        this.countryName = countryName;
    }

    public City(float geodesicDist) {
        this.features = new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, geodesicDist};
    }

    public City(String name, String countryName) {
        this.name = name;
        this.countryName = countryName;
        this.features = new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
    }

    public static City unifiedDistRec(PerceptronTraveler perceptron) throws Exception {
        ArrayList <City>citiesToCompare = perceptron.getLastRecommendation();

        if (citiesToCompare.isEmpty()){
            throw new NoRecommendationException();
        }
        City min = new City(normaliseFeature(MAX_DISTANCE,3));
        for (City city : citiesToCompare) {
            if (min.getFeatures()[9] > city.getFeatures()[9] && city.getFeatures()[9] != 0) {
                min = city;
            }
        }
        return min;
    }

    //Normalise specific feature (term). Mode defines the type of normalisation:
    // 0==Wiki, 1==Weather, 2==Clouds, 3==GeodesicDistance
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
    public static void setWeatherData(ArrayList<City> citiesLibrary) throws IOException, CitiesLibraryEmptyException {
        if (citiesLibrary==null){
            throw new CitiesLibraryEmptyException();
        }
        for (City city : citiesLibrary) {
            //Gathering weather data
            OpenWeatherMap tempWeatherObj = OpenData.retrieveWeatherData(city.name, city.countryName);
            float[] tempFeatures = city.getFeatures();

            //Setting normalised data at feature[] of city object
            tempFeatures[7] = normaliseFeature((float) tempWeatherObj.getMain().getTemp(), 1);
            tempFeatures[8] = normaliseFeature((float) tempWeatherObj.getClouds().getAll(), 2);
            tempFeatures[9] = normaliseFeature((float) geodesicDistance(Control.getOfficeLat(), Control.getOfficeLon(), tempWeatherObj.getCoord().getLat(), tempWeatherObj.getCoord().getLon()), 3);
            city.setFeatures(tempFeatures);
        }
    }

    //Downloads wiki data and place it to citiesLibrary without changing other data at the library
    public static void setWikiData(ArrayList<City> citiesLibrary) throws IOException, CitiesLibraryEmptyException {
        if (citiesLibrary==null){
            throw new CitiesLibraryEmptyException();
        }
        for (City city : citiesLibrary) {
            float[] tempFeatures = city.getFeatures();
            int[] tempWikiFeatures = countWikiKeywords(city.getName(), city.getCountryName());
            //Normalise and copy wiki features to city's features
            for (int featureIndex = 0; featureIndex < wikiFeatures.length; featureIndex++) {
                tempFeatures[featureIndex] = normaliseFeature((float)tempWikiFeatures[featureIndex], 0);
            }
            city.setFeatures(tempFeatures);
        }
    }

    //Counts keywords shown to city's Wiki article for every feature
    public static int[] countWikiKeywords(String city, String country) throws IOException {
        MediaWiki wikiObj = OpenData.retrieveWikiData(city, country);
        int[] tempFeature = new int[wikiFeatures.length];
        for (int featureIndex = 0; featureIndex < wikiFeatures.length; featureIndex++) {
            tempFeature[featureIndex] = CountWords.countCriterionfCity(wikiObj.toString(), wikiFeatures[featureIndex]);
        }
        return tempFeature;
    }

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

    @Override
    public String toString() {
        return name +
                "\t\t\t\t" + Arrays.toString(features);
    }
}