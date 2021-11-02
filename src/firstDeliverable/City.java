package firstDeliverable;

import firstDeliverable.openData.CountWords;
import firstDeliverable.openData.MediaWiki;
import firstDeliverable.openWeather.OpenWeatherMap;
import firstDeliverable.perceptrons.PerceptronTraveler;
import firstDeliverable.openData.OpenData;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class City {
    private static final String[] wikiFeatures = new String[]{"cafe", "sea", "museums", "wellness center", "stadium", "bar", "park"};

    private float[] features;
    private String name;
    private String countryName;

    public City(float[] features, String name, String countryName) {

        this.features = features;
        this.name = name;
        this.countryName = countryName;
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

    public static City unifiedDistRec(PerceptronTraveler perceptron, City[] citiesLibrary) {

        //Εχω τα recommendations απο το perceptron
        //Εχω τα citiesLibrary για να καλεσω τη recommend απο το perceptron

        //Απο τον perceptron παιρνω την recommend η οποια εχει τις πολεις που ειναι κοντα στα κρητηρια
        //απο καθε πολη παιρνω το distance και βρισκω το μικροτερο

        try {
            ArrayList<City> citiesToCompare = perceptron.recommend(perceptron.retrieveCompatibleCities(citiesLibrary), citiesLibrary);
            City min = citiesToCompare.get(0);
            for (int citiesCounter = 1; citiesCounter < citiesToCompare.size(); citiesCounter++) {
                if (min.getFeatures()[9] > citiesToCompare.get(citiesCounter).getFeatures()[9]) {
                    min = citiesToCompare.get(citiesCounter);
                }
            }
            return min;
        } catch (Exception e) {
            return null;
        }
    }

    public static float normaliseFeature(float term, int mode) {    //term==API data
        //mode defines the type of normalisation 0==Wiki, 1==Weather, 2==Clouds, 3==GeodesicDistance
        double min, max;

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
                    if (mode == 3) { //geodesticDistance
                        min = 0;
                        max = 9523.1;      //geodesicDistance athens-sydney
                    } else {
                        return 0;
                    }
                }
            }
        }
        return (float) ((term - min) / (max - min));
    }

    public static double normaliseFeature(double term, int mode) {    //term==API data
        //mode defines the type of normalisation 0==Wiki, 1==Weather, 2==Clouds, 3==GeodesicDistance
        double min, max;

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
                    if (mode == 3) { //geodesticDistance
                        min = 0;
                        max = 9523.1;      //geodesicDistance athens-sydney
                    } else {
                        return 0;
                    }
                }
            }
        }
        return (term - min) / (max - min);
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

    public static double geodesicDistance(double lat1, double lon1, double lat2, double lon2) {        //lat==API coordinates  Code form: https://www.geodatasource.com/developers/java
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist);
        }
    }

    public static void setWeatherData(City[] citiesLibrary) throws IOException {
        OpenWeatherMap tempWeatherObj;
        for (City city : citiesLibrary) {
            tempWeatherObj = OpenData.retrieveWeatherData(city.name, city.countryName);
            float[] tempFeatures = city.getFeatures();
            tempFeatures[7] = (float) normaliseFeature(tempWeatherObj.getMain().getTemp(), 1);
            tempFeatures[9] = (float) normaliseFeature(geodesicDistance(37.9795, 23.7162, tempWeatherObj.getCoord().getLat(), tempWeatherObj.getCoord().getLon()), 3);
            city.setFeatures(tempFeatures);
            //System.out.println("Object City:"+citiesLibrary[citiesCounter].getName()+"\ttemp: "+tempWeatherObj.getMain().getTemp() + "\tgeod: "+geodesicDistance(37.983810,23.727539, tempWeatherObj.getCoord().getLat(), tempWeatherObj.getCoord().getLon()));
        }
    }

    public static void setWikiData(City[] citiesLibrary) throws IOException {
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



