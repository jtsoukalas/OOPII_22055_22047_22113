package gr.hua.oopii.travelAgency.GUI;

import gr.hua.oopii.travelAgency.City;
import gr.hua.oopii.travelAgency.Control;
import gr.hua.oopii.travelAgency.exception.*;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronTraveler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newCachedThreadPool;
//import static jdk.internal.org.jline.terminal.Terminal.MouseTracking.Button;

public class GUIController implements Initializable {

    public WebView personalRecPanel;
    public Accordion personalRecommendationAccordion;
    public Tab personalRecommendationTab;
    public Accordion recommendationsAccordion;
    ExecutorService executorService = newCachedThreadPool();

    public Spinner<Integer> ageSpinner;
    public TextArea recommendationsTextArea;
    public CheckBox uppercaseCheckBox;
    public ChoiceBox<String> sortChoiceBox;
    public TextArea citiesLibraryTextArea;
    public Tab citiesLibraryTab;
    public Tab recommendTab;
    public TabPane tabs;
    public TextField candidateCityName;
    public Button addCandidateCityButton;
    public TextField candidateCityISO;
    public Text addCandidateCityNotification;
    public Tab addCandidateCityTab;
    public TextArea citiesLibraryByDays;


    public Slider futureSlider0;
    public Slider futureSlider1;
    public Slider futureSlider2;
    public Slider futureSlider3;
    public Slider futureSlider4;
    public Slider futureSlider5;
    public Slider futureSlider6;

    public Spinner<Integer> futureSpinner0;
    public Spinner<Integer> futureSpinner1;
    public Spinner<Integer> futureSpinner2;
    public Spinner<Integer> futureSpinner3;
    public Spinner<Integer> futureSpinner4;
    public Spinner<Integer> futureSpinner5;
    public Spinner<Integer> futureSpinner6;

    private ArrayList<Spinner<Integer>> relatedSpinners;
    private ArrayList<Slider> relatedSliders;

    @FXML
    private TitledPane personalRecPaneTitle;

    enum ObjectChanged {SLIDER, SPINNER}

    enum RecommendationTab {AGE_RECOMMENDATION, PERSONAL_RECOMMENDATION}


    @FXML
    private LineChart<String, Integer> lineChartCitiesLibrary;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;


    @FXML
    protected void gainRecommendationsButtonAction(Event event) throws StopRunningException, IOException {
        Control.mainLogger.info("GUI selection");
        recommendationsAccordion.getPanes().clear();
        try {
            ArrayList<City> recommendations = Control.runPerceptron(ageSpinner.getValue(), false);

            for (City recommendation : recommendations) {
                WebView webView = new WebView();
                webView.setMaxSize(465, 280);

                String[] covidRestrictions = recommendation.presentRecommendation();
                webView.getEngine().loadContent(covidRestrictions[City.Recommendation_Present_Headers.BODY.getIndex()]);

                AnchorPane anchorPane = new AnchorPane(webView);
                TitledPane titledPane = new TitledPane(recommendation.getName(), anchorPane);
                titledPane.setMaxSize(465, 250);

                Paint paint = Paint.valueOf("#000000");
                System.out.println(recommendation.getCountryName()+" "+covidRestrictions[City.Recommendation_Present_Headers.RISK_LEVEL.getIndex()]);
//                if (covidRestrictions[City.Recommendation_Present_Headers.RISK_LEVEL.getIndex()].equalsIgnoreCase("low")) {
//                    paint = Paint.valueOf("#61d942");
//                } else {
//                    if (covidRestrictions[City.Recommendation_Present_Headers.RISK_LEVEL.getIndex()].equalsIgnoreCase("medium")) {
//                        paint = Paint.valueOf("#ecc00e");
//                    } else {
//                        if (covidRestrictions[City.Recommendation_Present_Headers.RISK_LEVEL.getIndex()].equalsIgnoreCase("high")) {
//                            paint = Paint.valueOf("#ff7830");
//                        } else if (covidRestrictions[City.Recommendation_Present_Headers.RISK_LEVEL.getIndex()].equalsIgnoreCase("extreme")) {
//                            paint = Paint.valueOf("#cb2727");
//                        }
//                    }
//                }

                titledPane.setTextFill(paint);

                recommendationsAccordion.getPanes().add(titledPane);
            }
            recommendationsAccordion.setVisible(true);

        } catch (NoRecommendationException e) {
            noRecommendationExceptionHandling(e, RecommendationTab.AGE_RECOMMENDATION);
        }
    }


//                recommendationsTextArea.setText(Control.presentRecommendations(Control.runPerceptron(ageSpinner.getValue(),uppercaseCheckBox.isSelected())));
//                recommendationsTextArea.setStyle("-fx-text-fill: black;");
//                sortChoiceBox.getSelectionModel().

    //select(Control.retrieveDefaultSortingOption());
                /*} catch(NoRecommendationException e){
                    recommendationsTextArea.setText("There are no recommendations at the time for the age of " + ageSpinner.getValue() + ".");
                    recommendationsTextArea.setStyle("-fx-text-fill: red;");
                    Control.mainLogger.warning("No recommendations for traveller with age: " + ageSpinner.getValue());
                } catch(StopRunningException e){
                    stopRunningExceptionHandling(e);
                }
                tabs.getSelectionModel().select(recommendTab);*/


    @FXML
    protected void updateRelatedSpinnerAndSlider(ObjectChanged objectChanged, int id, int newValue) {
        if (objectChanged == ObjectChanged.SPINNER) {
            relatedSliders.get(id).setValue((double) Double.valueOf(newValue));
        } else {
            relatedSpinners.get(id).getValueFactory().setValue(newValue);
        }
    }

    protected void mapRelatedSpinnerAndSlider() {       //TODO Optimization
        relatedSpinners = new ArrayList<>(7);
        relatedSpinners.add(futureSpinner0);
        relatedSpinners.add(futureSpinner1);
        relatedSpinners.add(futureSpinner2);
        relatedSpinners.add(futureSpinner3);
        relatedSpinners.add(futureSpinner4);
        relatedSpinners.add(futureSpinner5);
        relatedSpinners.add(futureSpinner6);

        relatedSliders = new ArrayList<>(7);
        relatedSliders.add(futureSlider0);
        relatedSliders.add(futureSlider1);
        relatedSliders.add(futureSlider2);
        relatedSliders.add(futureSlider3);
        relatedSliders.add(futureSlider4);
        relatedSliders.add(futureSlider5);
        relatedSliders.add(futureSlider6);
    }

    @FXML
    protected void clearButtonAction() {
        ageSpinner.getValueFactory().setValue(16);
        recommendationsTextArea.clear();
        Control.mainLogger.info("GUI selection");
    }

    @FXML
    protected void updateDataButtonAction() throws Throwable {
        Control.updateData();
        Control.mainLogger.info("GUI selection");
    }

    @FXML
    protected void gainPersonalRecommendation() throws CitiesLibraryEmptyException {
        float[] customWeights = new float[7];

        for (int i = 0; i < relatedSliders.size(); i++) {
            customWeights[i] = (float) relatedSliders.get(i).getValue();
        }

        City rec = null;
        try {
            rec = PerceptronTraveler.personalizedRecommend(Control.getCitiesLibrary(), customWeights);
        } catch (NoRecommendationException e) {
            noRecommendationExceptionHandling(e, RecommendationTab.PERSONAL_RECOMMENDATION);
        }
        // Disable the context menu
//        webView.setContextMenuEnabled(false);

        personalRecPaneTitle.setText(rec.getName());

        personalRecPanel.getEngine().loadContent(rec.presentRecommendation()[0]);

        personalRecPaneTitle.setVisible(true);
        personalRecommendationAccordion.setExpandedPane(personalRecPaneTitle);
    }

    @FXML
    protected void saveButtonAction() {
        Control.mainLogger.info("GUI selection");
        Control.mainLogger.info("Saving data to Json: " + Control.saveCitiesLibraryJson());
    }

    @FXML
    protected void saveAsButtonAction() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select file to save data");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json files", "*.json"));

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            Control.mainLogger.info("Saving data to " + file.getAbsolutePath() + ": " + Control.saveCitiesLibraryJson(file));                 //4 DEBUGGING reasons
        } else {
            Control.mainLogger.info("Saving data to custom JSON: false ");
        }
    }

    @FXML
    protected void loadAsButtonAction() {
        FileChooser fileChooser = new FileChooser();


        fileChooser.setTitle("Select file to read data");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json files", "*.json"));

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            Control.mainLogger.info("Loading data from " + file.getAbsolutePath() + ": " + Control.retrieveCitiesLibraryJson(file));                 //4 DEBUGGING reasons
        } else {
            Control.mainLogger.info("Loading data from custom JSON: false ");
        }
    }


    @FXML
    protected void loadDataButtonAction() {
        Control.mainLogger.info("Loading data from default JSON: " + Control.retrieveCitiesLibraryJson());    //4 DEBUGGING reasons
    }

    @FXML
    protected void updateCitiesLibraryTextArea() throws CitiesLibraryEmptyException {
        citiesLibraryTextArea.setText(Control.cityLibraryToString());
        Control.mainLogger.info("GUI selection");
    }

    @FXML
    protected void addCandidateCityButtonAction() {
        Control.mainLogger.info("GUI selection");
        addCandidateCityNotification.setText("Please wait ...");
        addCandidateCityNotification.setFill(Color.GRAY);
        addCandidateCityNotification.setVisible(true);

        Date res = null;
        try {
            res = Control.addCandidateCity(candidateCityName.getText(), candidateCityISO.getText());
        } catch (NoSuchCityException e) {
            addCandidateCityNotification.setText("We couldn't found " + candidateCityName.getText() + " at " + candidateCityISO.getText());
            addCandidateCityNotification.setFill(Color.RED);
            addCandidateCityNotification.setVisible(true);
            Control.mainLogger.info("City: " + candidateCityName.getText() + " at " + candidateCityISO.getText() + " wasn't found");
            return;
        } catch (NoInternetException e) {
            addCandidateCityNotification.setText("Please check your internet connection and try again.");
            addCandidateCityNotification.setFill(Color.RED);
            addCandidateCityNotification.setVisible(true);
            Control.mainLogger.warning(e.getMessage());
            return;
        } catch (IllegalArgumentException e) {
            addCandidateCityNotification.setText("Please insert city name and country ISO");
            addCandidateCityNotification.setFill(Color.RED);
            addCandidateCityNotification.setVisible(true);
            Control.mainLogger.warning(e.getMessage());
            return;
        }

        if (res == null) {
            addCandidateCityNotification.setText(candidateCityName.getText() + " added successfully to Cities Library");
            addCandidateCityNotification.setFill(Color.GREEN);
            Control.mainLogger.info(candidateCityName.getText() + " added successfully to Cities Library");
        } else {
            addCandidateCityNotification.setText(candidateCityName.getText() + " already exists in Cities Library since " + res + ".");
            addCandidateCityNotification.setFill(Color.GRAY);
            Control.mainLogger.info(candidateCityName.getText() + " already exists in Cities Library since " + res);
        }
        addCandidateCityNotification.setVisible(true);
        try {
            updateCitiesLibraryTextArea();
        } catch (CitiesLibraryEmptyException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Logger init
        try {
            Control.initLogger();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Control obj init
        try {
            Control.init("Athens", "GR");
        } catch (StopRunningException e) {
            stopRunningExceptionHandling(e);
        } catch (NoSuchCityException e) {
            Control.mainLogger.severe("GUI:" + e);
        }

        //Loading data from JSON. If get trouble, inform the user to choose another file or download data
        Future<Boolean> jsonLoadRes = executorService.submit(new Callable<Boolean>() {
            /**
             * Computes a result, or throws an exception if unable to do so.
             *
             * @return computed result
             * @throws Exception if unable to compute a result
             */
            @Override
            public Boolean call() {
                return Control.retrieveCitiesLibraryJson();
            }
        });

        //Personal recommendation tab inits
        executorService.submit(new Runnable() {
            /**
             * When an object implementing interface {@code Runnable} is used
             * to create a thread, starting the thread causes the object's
             * {@code run} method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method {@code run} is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {
                personalRecPaneTitle.setVisible(false);
                mapRelatedSpinnerAndSlider();
                //FIXME all objects are connected - ORESTIS: "Spaghetti code"!


                for (Spinner<Integer> spinner : relatedSpinners) {
                    SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
                    spinnerValueFactory.setWrapAround(true);
                    spinnerValueFactory.setValue(50);
                    spinner.setValueFactory(spinnerValueFactory);
                    spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                        updateRelatedSpinnerAndSlider(ObjectChanged.SPINNER, relatedSpinners.indexOf(spinner), newValue);
                    });
                }

                for (int i = 0; i < relatedSliders.size(); i++) {
                    int finalI = i;
                    relatedSliders.get(i).valueProperty().addListener((observable, oldValue, newValue) -> updateRelatedSpinnerAndSlider(ObjectChanged.SLIDER, finalI, newValue.intValue()));
                }
            }
        });

        //Recommend destination tab inits
        executorService.submit(new Callable<Void>() {
            /**
             * Computes a result, or throws an exception if unable to do so.
             *
             * @return computed result
             * @throws Exception if unable to compute a result
             */
            @Override
            public Void call() {
                SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(16, 115);
                spinnerValueFactory.setValue(16);
                ageSpinner.setValueFactory(spinnerValueFactory);

                sortChoiceBox.getItems().addAll(Control.retrieveSortingOptions());
                sortChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        recommendationsTextArea.setText(Control.sortRecommendation((int) newValue));
                    } catch (NoRecommendationException e) {
                        noRecommendationExceptionHandling(e, RecommendationTab.AGE_RECOMMENDATION);
                    }

                }); //TODO: For optimization reasons, we can modify it so it want listen the auto value change (when new ageSpinner added)

                addCandidateCityTab.setOnSelectionChanged(event -> {
                    candidateCityName.clear();
                    candidateCityISO.clear();
                    addCandidateCityNotification.setVisible(false);
                    try {
                        updateCitiesLibraryTextArea();
                    } catch (CitiesLibraryEmptyException e) {
                        try {
                            Control.initCitiesLibrary();
                        } catch (StopRunningException e2) {
                            stopRunningExceptionHandling(e2);
                        }
                    }
                });

                return null;
            }
        });

        //Cities library tab inits
        executorService.submit(new Runnable() {
            /**
             * When an object implementing interface {@code Runnable} is used
             * to create a thread, starting the thread causes the object's
             * {@code run} method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method {@code run} is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {
                citiesLibraryTab.setOnSelectionChanged(event -> {
                    XYChart.Series series = new XYChart.Series();
                    TreeMap<String, String> weekCityCatalogue = Control.makeWeekCityCatalogue();
                    citiesLibraryByDays.setText(Control.presentWeekCityCatalogue(weekCityCatalogue));

                    TreeMap<String, Integer> weekCityCatalogueStatistics;
                    weekCityCatalogueStatistics = Control.statisticsWeekCityCatalogue(weekCityCatalogue);

                    for (Map.Entry<String, Integer> entry : weekCityCatalogueStatistics.entrySet()) {
                        series.getData().add(new XYChart.Data(entry.getKey().substring(0, 3), entry.getValue()));
                    }
                    lineChartCitiesLibrary.setLegendVisible(false);
                    lineChartCitiesLibrary.setAnimated(false);
                    lineChartCitiesLibrary.getData().clear();
                    lineChartCitiesLibrary.getData().add(series);
                });
            }
        });

        try {
            if (!jsonLoadRes.get()) {
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);           //Code reference: https://code.makery.ch/blog/javafx-dialogs-official/
                    alert.setTitle("Data warning");
                    alert.setHeaderText("Error retrieving data from default JSON file");

                    ButtonType downloadButton = new ButtonType("Download data from web");
                    ButtonType loadDataButton = new ButtonType("Load data from JSON file");
                    alert.getButtonTypes().setAll(downloadButton, loadDataButton);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == downloadButton) {
                        Alert downloadingDataInfo = new Alert(Alert.AlertType.INFORMATION, "Please press OK and wait for the data to get downloaded");
                        downloadingDataInfo.setHeaderText("Data download");
                        downloadingDataInfo.showAndWait();

                        try {
                            Control.initCitiesLibrary();
                        } catch (StopRunningException e) {
                            stopRunningExceptionHandling(e);
                        }
                        Control.mainLogger.finest("GUI election: Download data from web");
                    } else {
                        loadAsButtonAction();
                        Control.mainLogger.finest("GUI election: Load data from Json file");
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void noRecommendationExceptionHandling(NoRecommendationException e, RecommendationTab
            recommendationTab) {
        Control.mainLogger.warning("No recommendation exception. INFO:" + e);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);           //Code reference: https://code.makery.ch/blog/javafx-dialogs-official/
        alert.setTitle("No recommendations");

        alert.setHeaderText("There is no recommendations for you at the time, please try again later.");
        ButtonType personalRecommendation = new ButtonType("Personal recommendation");

        if (recommendationTab == RecommendationTab.AGE_RECOMMENDATION) {
            alert.setContentText("Feel free to try the personal recommendation engine!");
            alert.getButtonTypes().add(personalRecommendation);
        }

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == personalRecommendation) {
            tabs.getSelectionModel().select(personalRecommendationTab);
            Control.mainLogger.info("GUI Selection: Personal recommendation tab");
        }
    }

    private void stopRunningExceptionHandling(StopRunningException e) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText("Unexpected error occurred. Please restart the app");
        error.setContentText("Error info: " + e.getMessage());
        error.getButtonTypes().add(ButtonType.CLOSE);
        error.showAndWait();
        Control.mainLogger.severe("StopRunningException info: " + e);
        if (error.getResult() == ButtonType.CLOSE) {
            exit(1);
        }
    }
}