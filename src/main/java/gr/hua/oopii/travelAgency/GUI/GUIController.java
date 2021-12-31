package gr.hua.oopii.travelAgency.GUI;

import gr.hua.oopii.travelAgency.Control;
import gr.hua.oopii.travelAgency.exception.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class GUIController implements Initializable {

    public Spinner<Integer> age;
    public TextArea recommendationsTextArea;
    public CheckBox uppercaseCheckBox;  //FIXME: Change related method so it doesnt effect cities library (source data)
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
    @FXML
    private LineChart<String, Integer> lineChartCitiesLibrary;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;


    @FXML
    protected void gainRecommendationsButtonAction() throws NoRecommendationException, StopRunningException {
        recommendationsTextArea.setText(Control.recommendationToString(Control.runPerceptron(age.getValue(), uppercaseCheckBox.isSelected())));
        sortChoiceBox.getSelectionModel().select(Control.retrieveDefaultSortingOption());
        tabs.getSelectionModel().select(recommendTab);
    }

    @FXML
    protected void saveButtonAction() {
        System.out.println("Saving data to Json: " + Control.saveCitiesLibraryJson());                 //4 DEBUGGING reasons
    }

    @FXML
    protected void loadDataButtonAction() {
        System.out.println("Retrieving data from Json: " + Control.retrieveCitiesLibraryJson());    //4 DEBUGGING reasons
    }

    @FXML
    protected void updateCitiesLibraryTextArea() throws CitiesLibraryEmptyException {
        citiesLibraryTextArea.setText(Control.cityLibraryToString());
    }

    @FXML
    protected void addCandidateCityButtonAction() {
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
            return;
        } catch (NoInternetException e) {
            addCandidateCityNotification.setText("Please check your internet connection and try again.");
            addCandidateCityNotification.setFill(Color.RED);
            addCandidateCityNotification.setVisible(true);
            return;
        } catch (IllegalArgumentException e) {
            addCandidateCityNotification.setText("Please insert city name and country ISO");
            addCandidateCityNotification.setFill(Color.RED);
            addCandidateCityNotification.setVisible(true);
            return;
        }

        if (res == null) {
            addCandidateCityNotification.setText(candidateCityName.getText() + " added successfully to Cities Library");
            addCandidateCityNotification.setFill(Color.GREEN);
        } else {
            addCandidateCityNotification.setText(candidateCityName.getText() + " already exists in Cities Library since " + res + ".");
            addCandidateCityNotification.setFill(Color.GRAY);
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
        if (!Control.retrieveCitiesLibraryJson()) {
            System.out.println("Retrieving Json data on init: false "); //4 DEBUGGING reasons
            try {
                Control.initCitiesLibrary();    //FIXME: Delaying GUI start
            } catch (StopRunningException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Retrieving Json data on init: true "); //4 DEBUGGING reasons
        }

        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(16, 115);
        spinnerValueFactory.setValue(16);
        age.setValueFactory(spinnerValueFactory);


        sortChoiceBox.getItems().addAll(Control.retrieveSortingOptions());
        sortChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    recommendationsTextArea.setText(Control.sortRecommendation((int) newValue));
                } catch (NoRecommendationException e) {
                    e.printStackTrace();
                }
            }
        }); //TODO: For optimization reasons, we can modify it so it want listen the auto value change (when new age added)

        addCandidateCityTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                candidateCityName.clear();
                candidateCityISO.clear();
                addCandidateCityNotification.setVisible(false);
                try {
                    updateCitiesLibraryTextArea();
                } catch (CitiesLibraryEmptyException e) {
                    try {
                        Control.initCitiesLibrary();
                    } catch (StopRunningException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        XYChart.Series series = new XYChart.Series();


        citiesLibraryTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                TreeMap<String, String> weekCityCatalogue = Control.makeWeekCityCatalogue();
                citiesLibraryByDays.setText(Control.presentWeekCityCatalogue(weekCityCatalogue));

                series.getData().clear();
                TreeMap<String, Integer> weekCityCatalogueStatistics = Control.statisticsWeekCityCatalogue(weekCityCatalogue);
                Iterator<Map.Entry<String, Integer>> it = weekCityCatalogueStatistics.entrySet().iterator();

                for (Iterator<Map.Entry<String, Integer>> iter = it; iter.hasNext(); ) {
                    Map.Entry<String, Integer> tmp = it.next();
                    series.getData().add(new XYChart.Data(tmp.getKey().substring(0,3), tmp.getValue()));
                }
                System.out.println(Control.statisticsWeekCityCatalogue(weekCityCatalogue).toString());
                lineChartCitiesLibrary.getData().add(series);
            }
        }); //FIXME: Problem at x bar's text
    }
}