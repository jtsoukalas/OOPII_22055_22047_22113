package gr.hua.oopii.travelAgency.GUI;

import gr.hua.oopii.travelAgency.Control;
import gr.hua.oopii.travelAgency.exception.*;
import gr.hua.oopii.travelAgency.perceptrons.PerceptronTraveler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import static java.util.concurrent.Executors.newCachedThreadPool;
//import static jdk.internal.org.jline.terminal.Terminal.MouseTracking.Button;

public class GUIController implements Initializable {

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


    public Slider testSlider;
    public Slider futureSlider0;
    public Slider futureSlider1;
    public Slider futureSlider2;
    public Slider futureSlider3;
    public Slider futureSlider4;
    public Slider futureSlider5;
    public Slider futureSlider6;

    public Spinner<Integer> testSpinnner;
    public Spinner<Integer> futureSpinner0;
    public Spinner<Integer> futureSpinner1;
    public Spinner<Integer> futureSpinner2;
    public Spinner<Integer> futureSpinner3;
    public Spinner<Integer> futureSpinner4;
    public Spinner<Integer> futureSpinner5;
    public Spinner<Integer> futureSpinner6;

    private ArrayList<Spinner<Integer>> relatedSpinners;
    private ArrayList<Slider> relatedSliders;

    private TitledPane personalRecPane;

    enum ObjectChanged {SLIDER, SPINNER}


    @FXML
    private LineChart<String, Integer> lineChartCitiesLibrary;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;


    @FXML
    protected void gainRecommendationsButtonAction() {
        Control.mainLogger.info("GUI selection");
        try {
            recommendationsTextArea.setText(Control.recommendationToString(Control.runPerceptron(ageSpinner.getValue(), uppercaseCheckBox.isSelected())));
            recommendationsTextArea.setStyle("-fx-text-fill: black;");
            sortChoiceBox.getSelectionModel().select(Control.retrieveDefaultSortingOption());
        } catch (NoRecommendationException e) {
            recommendationsTextArea.setText("There are no recommendations at the time for the age of " + ageSpinner.getValue() + ".");
            recommendationsTextArea.setStyle("-fx-text-fill: red;");
            Control.mainLogger.warning("No recommendations for traveller with age: " + ageSpinner.getValue());
        } catch (StopRunningException e) {
            stopRunningExceptionHandling(e);
        }
        tabs.getSelectionModel().select(recommendTab);
    }

    @FXML
    protected void updateRelatedSpinnerAndSlider() {
        System.out.println("hi");

//        if (event.getSource() instanceof Slider){
//            System.out.println("Its slider's event");
//        } else {
//            System.out.println("It's spinner's event");
//        }

//        System.out.println(testSlider.getValue());
//        testSpinnner.getValueFactory().setValue((int) testSlider.getValue());
//        System.out.println(event.toString());
//        if (event.getSource().equals(testSlider)) {
//
//        }
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
        {
            try {
                Control.initLogger();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Control obj init
        {
            try {
                Control.init("Athens", "GR");
            } catch (StopRunningException e) {
                stopRunningExceptionHandling(e);
            } catch (NoSuchCityException e) {
                Control.mainLogger.severe("GUI:" + e);
            }

        }

        //Loading data from JSON. If get trouble, inform the user to choose another file or download data
        {
            if (!Control.retrieveCitiesLibraryJson()) {
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

        //Personal recommendation tab inits
        {
            //TODO Work needed
            SpinnerValueFactory<Integer> testspinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100);
            testspinnerValueFactory.setValue(50);
            futureSpinner0.setValueFactory(testspinnerValueFactory);

            futureSlider0.valueProperty().addListener((obs, oldValue, newValue) -> {

                updateRelatedSpinnerAndSlider();
//
            });

        }

        //Recommend destination tab inits
        {
            SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(16, 115);
            spinnerValueFactory.setValue(16);
            ageSpinner.setValueFactory(spinnerValueFactory);

            sortChoiceBox.getItems().addAll(Control.retrieveSortingOptions());
            sortChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    recommendationsTextArea.setText(Control.sortRecommendation((int) newValue));
                } catch (NoRecommendationException e) {
                    e.printStackTrace();
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
        }

        //Cities library tab inits
        {
            citiesLibraryTab.setOnSelectionChanged(event -> {
                XYChart.Series series = new XYChart.Series();
                TreeMap<String, String> weekCityCatalogue = Control.makeWeekCityCatalogue();
                citiesLibraryByDays.setText(Control.presentWeekCityCatalogue(weekCityCatalogue));

                TreeMap<String, Integer> weekCityCatalogueStatistics;
                weekCityCatalogueStatistics = Control.statisticsWeekCityCatalogue(weekCityCatalogue);
                Iterator<Map.Entry<String, Integer>> it = weekCityCatalogueStatistics.entrySet().iterator();

                for (Iterator<Map.Entry<String, Integer>> iter = it; iter.hasNext(); ) {
                    Map.Entry<String, Integer> tmp = it.next();
                    series.getData().add(new XYChart.Data(tmp.getKey().substring(0, 3), tmp.getValue()));
                }
                System.out.println(Control.statisticsWeekCityCatalogue(weekCityCatalogue).toString());
                lineChartCitiesLibrary.setLegendVisible(false);
                lineChartCitiesLibrary.setAnimated(false);
                lineChartCitiesLibrary.getData().clear();
                lineChartCitiesLibrary.getData().add(series);
            });

        }
        float[] customWeights = {0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F};
        try {
            System.out.println(PerceptronTraveler.personalizedRecommend(Control.getCitiesLibrary(), customWeights));
        } catch (CitiesLibraryEmptyException e) {
            e.printStackTrace();
        } catch (NoRecommendationException e) {
            e.printStackTrace();
        }
    }

    private void stopRunningExceptionHandling(StopRunningException e) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText("Unexpected error occurred. Please restart the app");
        error.setContentText("Error info: " + e.getMessage());
        error.getButtonTypes().add(ButtonType.CLOSE);
        error.showAndWait();

        if (error.getResult() == ButtonType.CLOSE) {
            exit(1);
        }
    }
}