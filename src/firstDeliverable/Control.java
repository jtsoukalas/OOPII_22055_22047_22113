package firstDeliverable;

import firstDeliverable.openData.OpenData;
import firstDeliverable.openWeather.OpenWeatherMap;
import firstDeliverable.perceptrons.PerceptronElderTraveler;
import firstDeliverable.perceptrons.PerceptronMiddleTraveler;
import firstDeliverable.perceptrons.PerceptronTraveler;
import firstDeliverable.perceptrons.PerceptronYoungTraveler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Control {

    private ArrayList<City> citiesLibrary;
    private final PerceptronYoungTraveler youngPerceptron = new PerceptronYoungTraveler();
    private final PerceptronMiddleTraveler middlePerceptron = new PerceptronMiddleTraveler();
    private final PerceptronElderTraveler elderPerceptron = new PerceptronElderTraveler();

    private static boolean wikiDataDownloaded = false;
    public static float officeLon, officeLat;

    public void initNameCitiesLibrary(){
        int cityAmount = 15;
        String[] cityNames = new String[]{"Athens", "London", "Brussels", "Madrid", "Helsinki", "Paris", "Berlin",
                "Stockholm", "Tokyo", "Rio", "Denver", "Rome", "Naples", "Milan", "Moscow"};
        String[] countryNames = new String[]{"GR", "UK", "BE", "ES", "FI", "FR", "DE",
                "SE", "JP", "BR", "US", "IT", "IT", "IT", "RU"};

        citiesLibrary = new ArrayList<>();
        for (int citiesCounter = 0; citiesCounter < cityAmount; citiesCounter++){
            citiesLibrary.add(new City(cityNames[citiesCounter],countryNames[citiesCounter]));
        }
    }

    public void makeDummyData() {
        int cityAmount = 15;
        Random rand = new Random();

        initNameCitiesLibrary();
        City tempCity;
        for (int cityCounter = 0; cityCounter < cityAmount; cityCounter++) {
            tempCity = citiesLibrary.remove(cityCounter);
            tempCity.setFeatures(new float[]{(float) rand.nextInt(10), (float) rand.nextInt(10),
                                                    (float) rand.nextInt(10), (float) rand.nextInt(10),
                                                    (float) rand.nextInt(10), (float) rand.nextInt(10),
                                                    (float) rand.nextInt(10),
                                                    rand.nextFloat() + rand.nextInt(146) + 184,                                     //Temperature in Kelvin
                                                    rand.nextFloat() + rand.nextInt(99),                                            //Weather Condition
                                                    rand.nextFloat() + rand.nextInt(9522)});                                        //geodesic distance in miles
            tempCity.normaliseFeature();
            citiesLibrary.add(tempCity);
        }
    }

    public ArrayList<City> runPerceptron (int age) throws IOException {
        //Update Wiki and Weather data
        if (!wikiDataDownloaded){
            City.setWikiData(getCitiesLibrary());
            wikiDataDownloaded=true;
        }
        City.setWeatherData(getCitiesLibrary());

        //Choose suitable perceptron
        PerceptronTraveler casePerceptron;

        if (age >15 && age<25){             //Young traveller
            casePerceptron = youngPerceptron;
        } else {
            if(age>=25 && age <60){         //Middle traveller
                casePerceptron = middlePerceptron;
            } else {
                if(age>=60 && age<115){     //Elder traveller
                    casePerceptron = elderPerceptron;
              } else {
                    return null;
                }
            }
        }
        //Run perceptron
        return casePerceptron.recommend(youngPerceptron.retrieveCompatibleCities(citiesLibrary), citiesLibrary);
    }

    public String cityLibraryToString(){
        StringBuilder returnCityCatalogue= new StringBuilder();
        for (City city : citiesLibrary) {
            returnCityCatalogue.append(city.toString()).append("\n");
        }
        return returnCityCatalogue.toString();
    }


    public ArrayList<City> getCitiesLibrary() {
        return citiesLibrary;
    }


    public String retrieveName(City city){
        try{
            return city.getName();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public PerceptronYoungTraveler getYoungPerceptron() {
        return youngPerceptron;
    }

    public PerceptronMiddleTraveler getMiddlePerceptron() {
        return middlePerceptron;
    }

    public PerceptronElderTraveler getElderPerceptron() {
        return elderPerceptron;
    }

    public Control(float officeLon, float officeLat) {
        Control.officeLon = officeLon;
        Control.officeLat = officeLat;
    }

    public Control(){
        String officeCity, officeCountry;
        Scanner input = new Scanner(System.in);
        boolean retry;

        System.out.println("Welcome to the session!");
        do {
            retry = false;
            System.out.println("Please enter your current location (city):");
            officeCity = input.next();
            System.out.println("Please enter the country's ISO:");
            officeCountry = input.next();
            try{
                OpenWeatherMap tempWeatherObj = OpenData.retrieveWeatherData(officeCity, officeCountry);
                Control.officeLat=(float)tempWeatherObj.getCoord().getLat();
                Control.officeLon=(float)tempWeatherObj.getCoord().getLon();
            }catch (IOException e){         //TODO We have to add more exceptions (eg NoInternetConnection)
                System.err.println("Error! There is no such country. Please try again.");
                retry=true;
            }
        }while (retry);
    }
}
