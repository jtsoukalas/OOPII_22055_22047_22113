package gr.hua.oopii.travelAgency;

//import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.reflect.TypeToken;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
import gr.hua.oopii.travelAgency.exception.CitiesLibraryEmptyException;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;
import gr.hua.oopii.travelAgency.exception.StopRunningException;
import gr.hua.oopii.travelAgency.openData.OpenData;
import gr.hua.oopii.travelAgency.openWeather.OpenWeatherMap;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronElderTraveler;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronMiddleTraveler;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronTraveler;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronYoungTraveler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;


public class Control {

    private ArrayList<City> citiesLibrary;
    private final PerceptronYoungTraveler youngPerceptron = new PerceptronYoungTraveler();
    private final PerceptronMiddleTraveler middlePerceptron = new PerceptronMiddleTraveler();
    private final PerceptronElderTraveler elderPerceptron = new PerceptronElderTraveler();
    private static PerceptronTraveler lastPerceptronUsed;

    private boolean wikiDataDownloaded = false;
    private LocalDateTime weatherDataDownloadTime;

    private float officeLon, officeLat;

    private final String citiesLibraryJsonFileName = "citiesLibrary.json";


    public Control(String officeCity, String officeCountry) throws IOException, StopRunningException {
        System.out.println("Retrieve cities library from Json file res= " + this.retrieveCitiesLibraryJson());  //Debugging reasons

        try {
            OpenWeatherMap tempWeatherObj = OpenData.retrieveWeatherData(officeCity, officeCountry);
            this.officeLat = (float) tempWeatherObj.getCoord().getLat();
            this.officeLon = (float) tempWeatherObj.getCoord().getLon();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            System.err.println("Error! Please check your internet connection and try again.");
            throw new StopRunningException(e);
        }
        this.wikiDataDownloaded = false;
        this.weatherDataDownloadTime = null;
    }

    public Control() throws StopRunningException {
        String officeCity, officeCountry;
        Scanner input = new Scanner(System.in);
        boolean retry;

        System.out.println("Welcome to the Travel Agency app!");
        do {
            retry = false;
            System.out.println("Please enter your current location (city):");
            officeCity = input.next();
            System.out.println("Please enter the country's ISO:");
            officeCountry = input.next();
            try {
                //this(officeCity,officeCountry);  //FIXME
                { //Temp code block -> No internet exception handling
                    System.out.println("-Retrieve cities library from Json file res = " + this.retrieveCitiesLibraryJson() + "-");  //Debugging reasons

                    try {
                        OpenWeatherMap tempWeatherObj = OpenData.retrieveWeatherData(officeCity, officeCountry);
                        this.officeLat = (float) tempWeatherObj.getCoord().getLat();
                        this.officeLon = (float) tempWeatherObj.getCoord().getLon();
                    } catch (FileNotFoundException e) {
                        throw new FileNotFoundException();
                    } catch (IOException e) {
                        System.err.println("Error! Please check your internet connection and try again.");
                        throw new StopRunningException(e);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error! There is no such city at " + officeCountry + ". Please try again.");
                retry = true;
            }
        } while (retry);
    }


    public void initNameCitiesLibrary() {
        int cityAmount = 15;
        String[] cityNames = new String[]{"Athens", "London", "Brussels", "Madrid", "Helsinki", "Paris", "Berlin",
                "Stockholm", "Tokyo", "Rio", "Denver", "Rome", "Naples", "Milan", "Moscow"};
        String[] countryNames = new String[]{"GR", "UK", "BE", "ES", "FI", "FR", "DE",
                "SE", "JP", "BR", "US", "IT", "IT", "IT", "RU"};

        citiesLibrary = new ArrayList<>();
        for (int cityIndex = 0; cityIndex < cityAmount; cityIndex++) {
            citiesLibrary.add(new City(cityNames[cityIndex], countryNames[cityIndex]));
        }
    }

    // Returns timestamp if user's city already exists, or null if it's a new city
    public Date newCity(String cityName, String countryName) throws IOException {
        City userCityRecommendation = new City(cityName, countryName);
        try {
            return citiesLibrary.get(citiesLibrary.indexOf(userCityRecommendation)).getTimestamp();
        } catch (IndexOutOfBoundsException e) {
            userCityRecommendation.setWeatherData(this);
            userCityRecommendation.setWikiData();
            citiesLibrary.add(userCityRecommendation);
            return null;
        }
    }

    public void makeDummyData() {
        int cityAmount = 15;
        Random rand = new Random();

        initNameCitiesLibrary();
        City tempCity;
        for (int cityIndex = 0; cityIndex < cityAmount; cityIndex++) {
            tempCity = citiesLibrary.remove(cityIndex);
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

    /**
     * @version 1.2
     */
    public ArrayList<City> runPerceptron(int age) throws StopRunningException, IllegalArgumentException {
        //Update Wiki and Weather data if needed
        boolean newData = false;
        try {
            if (!wikiDataDownloaded) {                              //Downloads wiki data once
                System.out.println("-Downloading data from the web, please wait-");
                initNameCitiesLibrary();
                City.setWikiData(getCitiesLibrary());
                wikiDataDownloaded = true;
                newData = true;
            }

            boolean downloadWeatherData = false;                    //Downloads weather data if 1 hour has elapsed since the last download
            try {
                if (weatherDataDownloadTime.plusHours(1).isBefore(LocalDateTime.now())) {
                    downloadWeatherData = true;
                }
            } catch (NullPointerException e) {
                downloadWeatherData = true;
            } finally {
                if (downloadWeatherData) {
                    City.setWeatherData(getCitiesLibrary(), this);
                    weatherDataDownloadTime = LocalDateTime.now();
                    newData = true;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error! Can not download data form the internet. Please try again later.");
            throw new StopRunningException(e);
        } catch (IOException e) {
            System.err.println("Error! Please check your internet connection and try again.");
            throw new StopRunningException(e);
        } catch (CitiesLibraryEmptyException e) {
            System.err.println(e.getMessage());
            throw new StopRunningException(e);
        }

        //Update cities library Json file
        if (newData) {
            System.out.println("-Cities library Json file update res = " + this.saveCitiesLibraryJson() + "-");
        }

        //Choose suitable perceptron
        PerceptronTraveler casePerceptron;
        if (age >= 15 && age < 25) {             //Young traveller
            casePerceptron = youngPerceptron;
        } else {
            if (age >= 25 && age < 60) {         //Middle traveller
                casePerceptron = middlePerceptron;
            } else {
                if (age >= 60 && age <= 115) {     //Elder traveller
                    casePerceptron = elderPerceptron;
                } else {
                    throw new IllegalArgumentException("Age should be from 15 to 115.");
                }
            }
        }
        lastPerceptronUsed = casePerceptron;
        System.out.println("Would you like your recommendation sorted? True/False");
        boolean sorted = Input.readBoolean();


        //Run perceptron
        try {
           casePerceptron.recommend(casePerceptron.retrieveCompatibleCities(citiesLibrary), citiesLibrary);

            if (sorted) {
                return casePerceptron.sortRecommendation();
            } else {
                return casePerceptron.getLastRecommendation();
            }
        } catch (CitiesLibraryEmptyException e) {
            System.err.println(e.getMessage());     //4 debugging
            throw new StopRunningException(e);
        }

    }

    public boolean saveCitiesLibraryJson() {
        /*{//ref: https://www.youtube.com/watch?v=ZZddxpxGQPE&list=PLpUMhvC6l7AOy4UEORSutzFus98n-Es_l&index=4
            String json = new Gson().toJson(this.citiesLibrary);
            return true;
        }*/
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("citiesLibrary.json"), this.citiesLibrary);     //FIXME Parametric file name
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public boolean retrieveCitiesLibraryJson() {
        ObjectMapper mapper = new ObjectMapper();
        //mapper.enableDefaultTyping();
        try {
            /*
            {//ref: https://www.youtube.com/watch?v=ZZddxpxGQPE&list=PLpUMhvC6l7AOy4UEORSutzFus98n-Es_l&index=4
                File file = new File("citiesLibrary.json");
                String tmpJson = "[{\"features\":[0.1,1.0,1.0,0.6,1.0,0.8,1.0,0.6857142,0.02,0.0],\"name\":\"Athens\",\"countryName\":\"GR\"},{\"features\":[0.1,1.0,1.0,0.4,0.4,1.0,1.0,0.68517005,0.9,0.15611756],\"name\":\"London\",\"countryName\":\"UK\"},{\"features\":[0.0,1.0,1.0,0.0,0.6,1.0,1.0,0.66680264,0.75,0.13639854],\"name\":\"Brussels\",\"countryName\":\"BE\"},{\"features\":[0.0,1.0,1.0,0.2,0.1,1.0,1.0,0.6740816,0.0,0.15464252],\"name\":\"Madrid\",\"countryName\":\"ES\"},{\"features\":[0.0,1.0,1.0,0.0,0.5,0.0,0.2,0.6329252,0.75,0.16118917],\"name\":\"Helsinki\",\"countryName\":\"FI\"},{\"features\":[0.0,1.0,1.0,0.1,0.0,1.0,1.0,0.6777551,0.75,0.13680944],\"name\":\"Paris\",\"countryName\":\"FR\"},{\"features\":[0.0,1.0,1.0,0.1,0.1,1.0,1.0,0.64142865,0.0,0.11772461],\"name\":\"Berlin\",\"countryName\":\"DE\"},{\"features\":[0.0,1.0,1.0,0.0,0.2,0.2,1.0,0.60503405,0.0,0.15723297],\"name\":\"Stockholm\",\"countryName\":\"SE\"},{\"features\":[0.0,1.0,1.0,0.0,0.6,0.2,1.0,0.67768705,0.2,0.62063575],\"name\":\"Tokyo\",\"countryName\":\"JP\"},{\"features\":[0.0,0.1,0.0,0.0,0.0,1.0,0.1,0.77231294,0.91,0.69603443],\"name\":\"Rio\",\"countryName\":\"BR\"},{\"features\":[0.0,1.0,1.0,0.0,0.1,0.5,1.0,0.69625854,0.34,0.64736575],\"name\":\"Denver\",\"countryName\":\"US\"},{\"features\":[0.0,1.0,1.0,0.3,0.4,1.0,1.0,0.70149654,0.2,0.06860799],\"name\":\"Rome\",\"countryName\":\"IT\"},{\"features\":[0.0,1.0,1.0,0.1,0.1,1.0,0.9,0.7121088,0.4,0.05695873],\"name\":\"Naples\",\"countryName\":\"IT\"},{\"features\":[0.0,1.0,1.0,0.2,0.3,1.0,1.0,0.6689796,0.75,0.095412716],\"name\":\"Milan\",\"countryName\":\"IT\"},{\"features\":[0.1,1.0,1.0,0.0,1.0,0.8,1.0,0.63476187,0.88,0.14568649],\"name\":\"Moscow\",\"countryName\":\"RU\"}]";

                Type myType = new TypeToken<ArrayList<City>>() {
                }.getClass();
                Gson gson = new Gson();
                ArrayList<City> tmp = gson.fromJson(tmpJson, myType);
                System.out.println(tmp.toString());
            }
            */
            //this.citiesLibrary = mapper.readValue(new File("citiesLibrary.json"), new TypeReference<ArrayList<City>>(){});//mapper.getTypeFactory().constructCollectionType(List.class, City.class));  //FIXME Class type problem
            this.weatherDataDownloadTime = LocalDateTime.now();     //FIXME Optimization needed
            this.wikiDataDownloaded = true;                         //FIXME Optimization needed
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String cityLibraryToString() {
        StringBuilder returnCityCatalogue = new StringBuilder();
        for (City city : citiesLibrary) {
            returnCityCatalogue.append(city.toString()).append("\n");
        }
        return returnCityCatalogue.toString();
    }

    public static String recommendationToString(ArrayList<City> compatibleCities) throws NoRecommendationException {
        if (compatibleCities.isEmpty()) {
            throw new NoRecommendationException();
        }

        StringBuilder recommendation = new StringBuilder();
        for (City compatibleCity : compatibleCities) {
            recommendation.append(compatibleCity.getName()).append(" | ");
        }
        return recommendation.toString();
    }

    public ArrayList<City> getCitiesLibrary() {
        return citiesLibrary;
    }

    public static PerceptronTraveler getLastPerceptronUsed() {
        return lastPerceptronUsed;
    }

    public float getOfficeLon() {
        return this.officeLon;
    }

    public float getOfficeLat() {
        return this.officeLat;
    }
}
