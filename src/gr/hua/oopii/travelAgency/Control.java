package gr.hua.oopii.travelAgency;

//import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.reflect.TypeToken;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
import gr.hua.oopii.travelAgency.comparators.WeekDayCompare;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


public class Control {
    /**
     * ArrayList<
     */
    private ArrayList<City> citiesLibrary;

    /**
     * {@link PerceptronTraveler} objects that will be used at processes
     */
    private final PerceptronYoungTraveler youngPerceptron = new PerceptronYoungTraveler();
    private final PerceptronMiddleTraveler middlePerceptron = new PerceptronMiddleTraveler();
    private final PerceptronElderTraveler elderPerceptron = new PerceptronElderTraveler();
    private static PerceptronTraveler lastPerceptronUsed;       //todo unnecessary?

    /**
     * Flag for wikipedia data download
     */
    private boolean wikiDataDownloaded;
    /**
     * Is the point of time when weather data downloaded(worse case senario)
     */
    private LocalDateTime weatherDownloadTimestamp;

    /**
     * Coordination in order to find distance between user and candidate city
     */
    private float userLon, userLat;

    /**
     * @param userCity
     * @param userCountry
     * @throws IOException
     * @throws StopRunningException
     */
    public Control(String userCity, String userCountry) throws IOException, StopRunningException {
        try {
            OpenWeatherMap tempWeatherObj = OpenData.retrieveWeatherData(userCity, userCountry);
            this.userLat = (float) tempWeatherObj.getCoord().getLat();
            this.userLon = (float) tempWeatherObj.getCoord().getLon();
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
                        this.userLat = (float) tempWeatherObj.getCoord().getLat();
                        this.userLon = (float) tempWeatherObj.getCoord().getLon();
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

    /**
     * <h1>Initialization of Cities names</h1>
     * Save the City names into a table of Strings
     * Save the ISO of each City in a parallel table of Strings
     * Adds each City object in an ArrayList
     */
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

    /**
     * <h1>Adds a Candidate City from the User</h1>
     * Creates new City object
     *
     * @param cityName
     * @param countryName
     * @return the (Date) timestamp if city already exists, or null if it's a new city
     * @throws NoSuchCityException if there is not a City with this name in earth
     * @throws NoInternetException if there is no Internet
     */
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


    /**
     * <h1>Create Hardcoded Data</h1>
     * For each city name that initialized:sets random value to each feature.
     * Normalizes all the features that added randomly.
     * Adds the temp City to citiesLibrary.
     */
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
     * <h1>Run Perceptron</h1>
     * Uses parameter age and creates the corresponding perceptron(young,middle or elder)
     * Asks for the recommendations to be sorted or not
     * @param age
     * @return last recommendations sorted or not
     * @throws StopRunningException      if city Library is Empty
     * @throws IllegalArgumentException  if wrong inputs has been given from the user
     * @throws NoRecommendationException if there are no recommendations
     */
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
            System.err.println(e.getMessage());     //Debugging reasons
            throw new StopRunningException(e);
        }

    }

    /**
     *
     * @throws StopRunningException
     */
    private void updateData() throws StopRunningException {
        System.out.println("Retrieve cities library from Json file res= " + this.retrieveCitiesLibraryJson());  //Debugging reasons

        boolean newData = false;
        try {
            if (weatherDownloadTimestamp == null) {
                initNameCitiesLibrary();
            }

            if (!this.wikiDataDownloaded) {                              //Downloads wiki data once
                System.out.println("-Downloading Wiki data, please wait-");
                City.setWikiData(citiesLibrary);
                wikiDataDownloaded = true;
                newData = true;
            }

            boolean downloadWeatherData = false;                    //Downloads weather data if 1 hour has elapsed since the last download
            try {
                if (weatherDownloadTimestamp.plusHours(1).isBefore(LocalDateTime.now())) {
                    downloadWeatherData = true;
                }
            } catch (NullPointerException e) {
                downloadWeatherData = true;
            } finally {
                if (downloadWeatherData) {
                    System.out.println("-Downloading Weather data, please wait-");
                    City.setWeatherData(getCitiesLibrary(), this);
                    weatherDownloadTimestamp = LocalDateTime.now();
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
        //mapper.en
        try {
            this.citiesLibrary = mapper.readValue(new File("citiesLibrary.json"), new TypeReference<ArrayList<City>>() {});

            //Find last weather download time
            Date firstWeatherDownloadTimestamp = citiesLibrary.get(0).getWeatherDownloadTimestamp();
            for (int citiesCounter = 1; citiesCounter < citiesLibrary.size(); citiesCounter++) {
                if (citiesLibrary.get(citiesCounter).getWeatherDownloadTimestamp().compareTo(firstWeatherDownloadTimestamp) < 0) {
                    firstWeatherDownloadTimestamp = citiesLibrary.get(citiesCounter).getWeatherDownloadTimestamp();
                }
            }
            this.weatherDownloadTimestamp = firstWeatherDownloadTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            this.wikiDataDownloaded = true;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public TreeMap<String, String> makeWeekCityCatalogue() {
        TreeMap<String, String> map = new TreeMap<>(new WeekDayCompare());

        SimpleDateFormat f = new SimpleDateFormat("EEEEEE");
        for (City city : citiesLibrary) {
            map.merge(f.format(city.getTimestamp()).toUpperCase(Locale.ROOT), city.getName(), (a, b) -> a + "," + b);
        }
        return map;
    }

    public String presentWeekCityCatalogue(TreeMap<String, String> tree) {
        StringBuilder sb = new StringBuilder();

        enum Days {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        }
        for (Days day : Days.values()) {
            sb.append(day.ordinal() + 1).append(". ").append(day).append(": ").append(tree.get(day.toString())).append("\n");
        }
        return sb.toString();
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

    public float getUserLon() {
        return this.userLon;
    }

    public float getUserLat() {
        return this.userLat;
    }
}
