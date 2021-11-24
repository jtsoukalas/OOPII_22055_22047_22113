package gr.hua.oopii.travelAgency;

//import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.reflect.TypeToken;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
import gr.hua.oopii.travelAgency.exception.*;
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
import java.time.ZoneId;
import java.util.*;


public class Control {

    private ArrayList<City> citiesLibrary;
    private final PerceptronYoungTraveler youngPerceptron = new PerceptronYoungTraveler();
    private final PerceptronMiddleTraveler middlePerceptron = new PerceptronMiddleTraveler();
    private final PerceptronElderTraveler elderPerceptron = new PerceptronElderTraveler();
    private static PerceptronTraveler lastPerceptronUsed;       //todo unnecessary?

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
        if (citiesLibrary != null && !citiesLibrary.isEmpty()) {
            return;
        }

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

    // Returns timestamp if city already exists, or null if it's a new city
    public Date addCandidateCity(String cityName, String countryName) throws NoSuchCityException, NoInternetException {
        City userCityRecommendation = new City(cityName, countryName);
        try {
            return citiesLibrary.get(citiesLibrary.indexOf(userCityRecommendation)).getTimestamp();
        } catch (IndexOutOfBoundsException e) {
            //Download data and append to ArrayList
            userCityRecommendation.setWeatherData(this);
            userCityRecommendation.setWikiData();
            citiesLibrary.add(userCityRecommendation);
            //Update Json
            this.saveCitiesLibraryJson();
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


    public ArrayList<City> runPerceptron(int age) throws StopRunningException, IllegalArgumentException, NoRecommendationException {
        this.updateData();

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

    private void updateData() throws StopRunningException {
        retrieveCitiesLibraryJson();

        boolean newData = false;
        try {
            if (!this.wikiDataDownloaded) {                              //Downloads wiki data once
                System.out.println("-Downloading data from the web, please wait-");
                initNameCitiesLibrary();
                City.setWikiData(getCitiesLibrary());
                wikiDataDownloaded = true;
                newData = true;
            }

            boolean downloadWeatherData = false;                    //Downloads weather data if 1 hour has elapsed since the last download
            try {
                if (weatherDataDownloadTime.plusHours(1).isAfter(LocalDateTime.now())) {
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
        } catch (CitiesLibraryEmptyException | NoSuchCityException | NoInternetException e) {
            System.err.println(e.getMessage());
            throw new StopRunningException(e);
        }

        //Update cities library Json file       //TODO Talk about where should we update the Json file
        if (newData) {
            System.out.println("-Cities library Json file update res = " + this.saveCitiesLibraryJson() + "-");
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
            this.citiesLibrary = mapper.readValue(new File("citiesLibrary.json"), new TypeReference<ArrayList<City>>() {
            });

            //Find last weather download time
            Date lastUpdateTime = citiesLibrary.get(0).getTimestamp();
            for (int citiesCounter = 1; citiesCounter < citiesLibrary.size(); citiesCounter++) {
                if (citiesLibrary.get(citiesCounter).getTimestamp().compareTo(lastUpdateTime) < 0) {
                    lastUpdateTime = citiesLibrary.get(citiesCounter).getTimestamp();
                }
            }
            this.weatherDataDownloadTime = lastUpdateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            this.wikiDataDownloaded = true;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TreeMap<Integer, String> makeWeekCityCatalogue(){

        TreeMap<Integer, String> map = new TreeMap<>();

        for (City city : citiesLibrary) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(city.getTimestamp());
            Integer dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            map.merge(dayOfWeek, city.getName(), (a, b) -> a + "," + b);
        }
        return map;
    }

    public String cityLibraryToString() throws CitiesLibraryEmptyException {
        if (citiesLibrary.isEmpty()) {
            throw new CitiesLibraryEmptyException();
        }

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
        return recommendation.deleteCharAt(recommendation.lastIndexOf("|")).toString();
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
