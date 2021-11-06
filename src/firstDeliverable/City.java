package firstDeliverable;

import firstDeliverable.exception.NoRecommendationException;
import firstDeliverable.openData.CountWords;
import firstDeliverable.openData.MediaWiki;
import firstDeliverable.openWeather.OpenWeatherMap;
import firstDeliverable.perceptrons.PerceptronTraveler;
import firstDeliverable.openData.OpenData;

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

    //Retrieving data from the OpenWeatherMap API features[10]
    public City(float geodesicDist) {
        this.features = new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, geodesicDist};
    }

    public City(String name, String countryName) {
        this.name = name;
        this.countryName = countryName;
        this.features = new float[]{0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F, 0F};
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

    public static City unifiedDistRec(PerceptronTraveler perceptron, ArrayList<City> citiesLibrary) throws Exception {
        ArrayList <City>citiesToCompare = perceptron.getLastRecommendation();

        if (citiesToCompare.isEmpty()){
            throw new NoRecommendationException();
        }
        City min = new City(MAX_DISTANCE);
        for (City city : citiesToCompare) {
            if (min.getFeatures()[9] > city.getFeatures()[9] && city.getFeatures()[9] != 0) {
                min = city;
            }
        }
        return min;
    }

    public static float normaliseFeature(float term, int mode) {
        //mode defines the type of normalisation 0==Wiki, 1==Weather, 2==Clouds, 3==GeodesicDistance
        double min, max;

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
        return (float) ((term - min) / (max - min));
    }

    public void normaliseFeature() {
        for (int featureCounter = 0; featureCounter < 10; featureCounter++) {
            if (featureCounter < 7) {                   //wiki normalisation
                features[featureCounter] = normaliseFeature(features[featureCounter], 0);
            } else {
                if (featureCounter == 7) {              //weather normalisation
                    features[featureCounter] = normaliseFeature(features[featureCounter], 1);
                } else {
                    if (featureCounter == 8) {          //clouds normalisation
                        features[featureCounter] = normaliseFeature(features[featureCounter], 2);
                    } else {                            //distance normalisation
                        features[featureCounter] = normaliseFeature(features[featureCounter], 3);
                    }
                }
            }
        }
    }

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

    public static void setWeatherData(ArrayList<City> citiesLibrary) throws IOException {
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

    public static void setWikiData(ArrayList<City> citiesLibrary) throws IOException {
        for (City city : citiesLibrary) {
            float[] tempFeatures = city.getFeatures();
            int[] tempWikiFeatures = calcWikiFeatures(city.getName(), city.getCountryName());
            //Normalise and copy wiki features to city's features
            for (int featureCounter = 0; featureCounter < wikiFeatures.length; featureCounter++) {
                tempFeatures[featureCounter] = normaliseFeature((float) tempWikiFeatures[featureCounter], 0);
            }
            city.setFeatures(tempFeatures);
        }
    }

    public static int[] calcWikiFeatures(String city, String country) throws IOException {
        MediaWiki wikiObj = OpenData.retrieveWikiData(city, country);
        int[] tempFeature = new int[wikiFeatures.length];
        //Count criteria for city's article
        for (int featureCounter = 0; featureCounter < wikiFeatures.length; featureCounter++) {
            tempFeature[featureCounter] = CountWords.countCriterionfCity(wikiObj.toString(), wikiFeatures[featureCounter]);
        }
        return tempFeature;
    }

}