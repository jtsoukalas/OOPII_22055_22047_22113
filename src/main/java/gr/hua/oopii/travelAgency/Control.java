package gr.hua.oopii.travelAgency;

//import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.hua.oopii.travelAgency.API.APICallers;
import gr.hua.oopii.travelAgency.comparators.GeodesicCompare;
import gr.hua.oopii.travelAgency.comparators.TimestampCompare;
import gr.hua.oopii.travelAgency.comparators.WeekDayCompare;
import gr.hua.oopii.travelAgency.exception.*;
import gr.hua.oopii.travelAgency.API.openWeather.OpenWeatherMap;
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
import java.util.logging.*;

public class Control {
    /**
     * Source data for cities
     * @see City
     */
    private static ArrayList<City> citiesLibrary;

    /**
     * {@link PerceptronTraveler} objects that will be used at processes
     */
    private static final PerceptronYoungTraveler youngPerceptron = new PerceptronYoungTraveler();
    private static final PerceptronMiddleTraveler middlePerceptron = new PerceptronMiddleTraveler();
    private static final PerceptronElderTraveler elderPerceptron = new PerceptronElderTraveler();
    private static PerceptronTraveler lastPerceptronUsed;

    /**
     * Flag for wikipedia data download
     */
    private static boolean wikiDataDownloaded;
    /**
     * Is the point of time when weather data downloaded(worse case senario)
     */
    private static LocalDateTime weatherDownloadTimestamp;

    /**
     * Coordination in order to find distance between user and candidate city
     */
    private static float userLon, userLat;

    /**
     * Logger object
     */
    public static Logger mainLogger;

    /**
     * @param userCity    user's city name
     * @param userCountry user's country name (ISO)
     * @throws NoSuchCityException  if there is no city with given parameters
     * @throws StopRunningException if there is no connection to the internet. Then the program has to end.
     */
    public static void init(String userCity, String userCountry) throws StopRunningException, NoSuchCityException {
        try {
            OpenWeatherMap tempWeatherObj = APICallers.retrieveWeatherData(userCity, userCountry);
            userLat = (float) tempWeatherObj.getCoord().getLat();
            userLon = (float) tempWeatherObj.getCoord().getLon();
        } catch (FileNotFoundException e) {
            throw new NoSuchCityException(userCity, "OpenWeather");
        } catch (IOException e) {
            System.err.println("Error! Please check your internet connection and try again.");
            throw new StopRunningException(new NoInternetException("OpenWeather"));
        }
    }

//    public Control() throws StopRunningException {
//        String officeCity, officeCountry;
//        Scanner input = new Scanner(System.in);
//        boolean retry;
//
//        System.out.println("Welcome to the Travel Agency app!");
//        do {
//            retry = false;
//            System.out.println("Please enter your current location (city):");
//            officeCity = input.next();
//            System.out.println("Please enter the country's ISO:");
//            officeCountry = input.next();
//            try {
//                //this(officeCity,officeCountry);
//                { //Temp code block -> No internet exception handling
//                    try {
//                        OpenWeatherMap tempWeatherObj = OpenData.retrieveWeatherData(officeCity, officeCountry);
//                        this.userLat = (float) tempWeatherObj.getCoord().getLat();
//                        this.userLon = (float) tempWeatherObj.getCoord().getLon();
//                    } catch (FileNotFoundException e) {
//                        throw new FileNotFoundException();
//                    } catch (IOException e) {
//                        System.err.println("Error! Please check your internet connection and try again.");
//                        throw new StopRunningException(e);
//                    }
//                }
//            } catch (IOException e) {
//                System.err.println("Error! There is no such city at " + officeCountry + ". Please try again.");
//                retry = true;
//            }
//        } while (retry);
//    }

    /**
     * <h1>Initialization of Cities Library</h1>
     * Save the City names into a table of Strings
     * Save the ISO of each City in a parallel table of Strings
     * Adds each City object in an ArrayList
     * Calls {@link #updateDataIfNeeded()}
     */
    public static void initCitiesLibrary() throws StopRunningException {
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
        updateDataIfNeeded();
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
    public static Date addCandidateCity(String cityName, String countryName) throws NoSuchCityException, NoInternetException, IllegalArgumentException {
        if (cityName == null /*|| countryName== null*/ || cityName.isEmpty() /*|| countryName.isEmpty()*/) { //TODO Talk about it
            throw new IllegalArgumentException("City name or ISO missing");
        }

        City userCityRecommendation = new City(cityName, countryName);
        try {
            return citiesLibrary.get(citiesLibrary.indexOf(userCityRecommendation)).getTimestamp();
        } catch (IndexOutOfBoundsException e) {
            //Download data and append to ArrayList
            userCityRecommendation.setWeatherData();
            userCityRecommendation.setWikiData();

            int tmpIndex = citiesLibrary.indexOf(userCityRecommendation);
            if (tmpIndex >= 0) {
                return citiesLibrary.get(tmpIndex).getTimestamp();
            }

            citiesLibrary.add(userCityRecommendation);
//            //Update Json
//            saveCitiesLibraryJson();  //TODO Talk about it
            return null;
        }
    }


    /**
     * <h1>Create Hardcoded Data</h1>
     * For each city name that initialized:sets random value to each feature.
     * Normalizes all the features that added randomly.
     * Adds the temp City to citiesLibrary.
     *//*
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
    }*/


    /**
     * <h1>Run Perceptron</h1>
     * Uses parameter age and creates the corresponding perceptron(young,middle or elder)
     * Asks for the recommendations to be sorted or not
     *
     * @param age
     * @return last recommendations sorted or not
     * @throws StopRunningException      if city Library is Empty
     * @throws IllegalArgumentException  if wrong inputs has been given from the user
     * @throws NoRecommendationException if there are no recommendations
     */
    public static ArrayList<City> runPerceptron(int age, boolean uppercase) throws StopRunningException, IllegalArgumentException, NoRecommendationException {
        updateDataIfNeeded();

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

        //Run perceptron
        try {
            casePerceptron.recommend(casePerceptron.retrieveCompatibleCities(citiesLibrary), citiesLibrary, uppercase);
            return casePerceptron.sortRecommendation();
        } catch (CitiesLibraryEmptyException e) {
            System.err.println(e.getMessage());     //Debugging reasons
            throw new StopRunningException(e);
        }

    }

    /**
     * <h1>Controls when data from wikipedia open weather map and Json files need an update</h1>
     * If weather data hasn't been downloaded once it initialises the citiesLibrary ArrayList to extract data for the contained cities
     * Downloads wikipedia data if needed and sets newData to true
     * Downloads weather data if needed and sets newData to true (with the help of a timestamp in order to update accordingly)
     * When newData is true the CitiesLibrary Json file updates in order to save the new data
     *
     * @throws StopRunningException
     */
    private static void updateDataIfNeeded() throws StopRunningException {
        //System.out.println("Retrieve cities library from Json file res= " + retrieveCitiesLibraryJson());  //TODO: Talk about it

        boolean newData = false;
        try {
            if (weatherDownloadTimestamp == null) {
                initCitiesLibrary();
            }

            if (!wikiDataDownloaded) {                              //Downloads wiki data once
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
                    City.setWeatherData(Control.getCitiesLibrary());
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
            //System.out.println("-Cities library Json file update res = " + saveCitiesLibraryJson() + "-");
        }
    }

    public static void updateData() throws StopRunningException {

        try {
            if (weatherDownloadTimestamp == null) {
                initCitiesLibrary();
            }

            System.out.println("-Downloading Wiki data, please wait-");
            City.setWikiData(citiesLibrary);
            wikiDataDownloaded = true;

            System.out.println("-Downloading Weather data, please wait-");
            City.setWeatherData(Control.getCitiesLibrary());
            weatherDownloadTimestamp = LocalDateTime.now();
        } catch (CitiesLibraryEmptyException | NoSuchCityException | NoInternetException | StopRunningException e) {
            System.err.println(e.getMessage());
            throw new StopRunningException(e);
        }
    }

    /**
     * <h1>Saves the citiesLibrary to specified Json file</h1>
     * Takes the city Library objects and saves them to the specified Json file
     *
     * @param file desired file to save data
     * @return true if the saving is successful otherwise false
     */
    public static boolean saveCitiesLibraryJson(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, citiesLibrary);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * <h1>Saves the citiesLibrary to default json file </h1>
     * Takes the city Library objects and saves them to default json file (citiesLibrary.json)
     *
     * @return true if the saving is successful otherwise false
     */
    public static boolean saveCitiesLibraryJson() {
        return saveCitiesLibraryJson(new File("citiesLibrary.json"));
    }


    /**
     * <h1>Retrieves data from the specified Json file and creates CitiesLibrary</h1>
     * Retrieve the data of each city that saved in the Json
     * Saves a timestamp from earliest weather download
     *
     * @param file desired file to retrieve data
     * @return true if the retrieving is successfully otherwise false
     */
    public static boolean retrieveCitiesLibraryJson(File file) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            citiesLibrary = mapper.readValue(file, new TypeReference<ArrayList<City>>() {
            });

            //Find last weather download time
            Date firstWeatherDownloadTimestamp = citiesLibrary.get(0).getWeatherDownloadTimestamp();
            for (int citiesCounter = 1; citiesCounter < citiesLibrary.size(); citiesCounter++) {
                if (citiesLibrary.get(citiesCounter).getWeatherDownloadTimestamp().compareTo(firstWeatherDownloadTimestamp) < 0) {
                    firstWeatherDownloadTimestamp = citiesLibrary.get(citiesCounter).getWeatherDownloadTimestamp();
                }
            }
            weatherDownloadTimestamp = firstWeatherDownloadTimestamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            wikiDataDownloaded = true;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * <h1>Retrieves data from the default Json file and creates CitiesLibrary</h1>
     * Retrieve the data of each city that saved in citiesLibrary.json
     * Saves a timestamp from earliest weather download
     *
     * @return true if the retrieving is successfully otherwise false
     */
    public static boolean retrieveCitiesLibraryJson() {
        return retrieveCitiesLibraryJson(new File("citiesLibrary.json"));
    }

    /**
     * <h1>Creates a city catalogue that sorts each city by the day of week that its been added</h1>
     *
     * @return map
     */
    public static TreeMap<String, String> makeWeekCityCatalogue() {
        TreeMap<String, String> map = new TreeMap<>(new WeekDayCompare());

        SimpleDateFormat f = new SimpleDateFormat("EEEEEE");
        for (City city : citiesLibrary) {
            map.merge(f.format(city.getTimestamp()).toUpperCase(Locale.ROOT), city.getName(), (a, b) -> a + ", " + b);
        }
        return map;
    }

    public static TreeMap<String, Integer> statisticsWeekCityCatalogue(TreeMap<String, String> weekCityCatalogue) {
        Iterator<Map.Entry<String, String>> it = weekCityCatalogue.entrySet().iterator();

        TreeMap<String, Integer> res = new TreeMap<>(new WeekDayCompare());

        for (Iterator<Map.Entry<String, String>> iter = it; iter.hasNext(); ) {
            Map.Entry<String, String> tmp = iter.next();

            //Count number of cities
            StringTokenizer st = new StringTokenizer(tmp.getValue(), ",");
            res.put(tmp.getKey(), st.countTokens());
        }
        return res;
    }

    /**
     * <h1>toString method that outputs the city catalogue and the day of week that each city is being added </h1>
     *
     * @param tree The treeMap that we want to present
     * @return City Catalogue sorted by day of week
     */
    public static String presentWeekCityCatalogue(TreeMap<String, String> tree) {
        StringBuilder sb = new StringBuilder();

        enum Days {
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
        }
        for (Days day : Days.values()) {
            sb.append(day.ordinal() + 1).append(". ").append(day).append(": ").append(tree.get(day.toString()) == null ? "" : tree.get(day.toString())).append("\n");
        }
        return sb.toString();
    }

    public static String cityLibraryToString() throws CitiesLibraryEmptyException {
        if (citiesLibrary.isEmpty()) {
            throw new CitiesLibraryEmptyException();
        }

        StringBuilder returnCityCatalogue = new StringBuilder();
        for (City city : citiesLibrary) {
            returnCityCatalogue.append(city.toString()).append("\n");
        }
        return returnCityCatalogue.toString();
    }

    /**
     * <h1>Creates a string to represent the recommendations<h1/>
     * Takes compatible cities names from each city Object and appends them to a string
     *
     * @param compatibleCities
     * @return a string with all the recommendations
     * @throws NoRecommendationException
     */
    public static String recommendationToString(ArrayList<City> compatibleCities) throws NoRecommendationException {
        if (compatibleCities.isEmpty()) {
            throw new NoRecommendationException();
        }

        StringBuilder recommendation = new StringBuilder();
        for (City compatibleCity : compatibleCities) {
            recommendation.append(compatibleCity.getName()).append("\n");
        }
        return recommendation.deleteCharAt(recommendation.lastIndexOf("\n")).toString();
    }

    public static void initLogger() throws IOException {        //Source code from: https://www.vogella.com/tutorials/Logging/article.html
        // get the global logger to configure it
        mainLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        // create a directory for logs if it doesn't exist
        new File("./.logs").mkdir();

        // create new log file
        String dateAndTime = new SimpleDateFormat("-yyyy-MM-dd--HH-mm-ss").format(new Date());
        FileHandler fileTxt = new FileHandler("./.logs/Logging" + dateAndTime + ".log");

        // create a TXT formatter
        SimpleFormatter formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        mainLogger.addHandler(fileTxt);
    }

    public static String sortRecommendation(int option) throws NoRecommendationException {
        if (lastPerceptronUsed == null || lastPerceptronUsed.getLastRecommendation() == null) {
            throw new NoRecommendationException();
        }

        ArrayList<String> options = retrieveSortingOptions();

        if (option < 0 || option > options.size() - 1) {
            throw new IllegalArgumentException("Sorting option: " + option + "isn't supported");
        }

        Comparator<City> caseComparator = switch (option) {
            case 0 -> new GeodesicCompare();
            case 1 -> new GeodesicCompare().reversed();
            case 2 -> new TimestampCompare();
            default -> null;
        };

        return recommendationToString(lastPerceptronUsed.sortRecommendation(caseComparator));
    }

    /**
     * Finds the default sorting option of last used perceptron
     *
     * @return a sorting option
     * @throws NullPointerException if there is no Perceptron used
     */
    public static String retrieveDefaultSortingOption() {
        if (lastPerceptronUsed == null) {
            throw new NullPointerException("There is no recommendations to sort");
        }

        if (lastPerceptronUsed instanceof PerceptronElderTraveler) {
            return "Distance descending";
        } else if (lastPerceptronUsed instanceof PerceptronMiddleTraveler) {
            return "Time city entered";
        } else {
            return "Distance";
        }
    }

    /**
     * Returns the supported sorting options
     *
     * @return the sorting options
     */
    public static ArrayList<String> retrieveSortingOptions() {
        ArrayList<String> res = new ArrayList<>();
        res.add("Distance");
        res.add("Distance descending");
        res.add("Time city entered");
        return res;
    }

    public static ArrayList<City> getCitiesLibrary() {
        return citiesLibrary;
    }

    public static PerceptronTraveler getLastPerceptronUsed() {
        return lastPerceptronUsed;
    }

    public static float getUserLon() {
        return userLon;
    }

    public static float getUserLat() {
        return userLat;
    }
}
