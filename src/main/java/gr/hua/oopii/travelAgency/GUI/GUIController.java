package gr.hua.oopii.travelAgency.GUI;

import gr.hua.oopii.travelAgency.Control;
import gr.hua.oopii.travelAgency.exception.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;

public class GUIController implements Initializable {

    public Spinner<Integer> age;
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
    protected void saveAsButtonAction() {
        FileChooser fileChooser = new FileChooser();


        fileChooser.setTitle("Select file to save data");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json files", "*.json"));

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            System.out.println("Saving data to Json res: " + Control.saveCitiesLibraryJson(file));                 //4 DEBUGGING reasons
        } else {
            System.out.println("Saving data to Json res: false");                 //4 DEBUGGING reasons
        }
    }

    @FXML
    protected void loadAsButtonAction() {
        FileChooser fileChooser = new FileChooser();


        fileChooser.setTitle("Select file to read data");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Json files", "*.json"));

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            System.out.println("Loading data to Json res: " + Control.retrieveCitiesLibraryJson(file));                 //4 DEBUGGING reasons
        } else {
            System.out.println("Loading data to Json res: false");                 //4 DEBUGGING reasons
        }
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
            final Stage dialog = new Stage();

            Image icon = new Image(getClass().getResourceAsStream("warning-icon.png"));
            dialog.getIcons().add(icon);

            dialog.setTitle("Warning");
            dialog.initModality(Modality.WINDOW_MODAL);

            //dialog.initOwner(primaryStage);
            VBox dialogVbox = new VBox(20);
            Scene dialogScene = new Scene(dialogVbox);

            Button downloadDataButton = new Button("Download data from web");
            Button loadDataButton = new Button("Load data from Json file");

            downloadDataButton.setOnAction(e -> {
                dialogVbox.getChildren().add(new Text("Please wait wile downloading data...")); //FIXME: Message appears after complete download
                try {
                    Control.initCitiesLibrary();
                } catch (StopRunningException ex) {
                    ex.printStackTrace();
                }
            });

            loadDataButton.setOnAction(e -> loadAsButtonAction());

            Text text = new Text("We couldn't load data from default file. \nPlease select an option to proceed:");
            text.setFont(new Font("Arial", 15));
            text.setTextAlignment(TextAlignment.CENTER);

            dialogVbox.getChildren().addAll(text, downloadDataButton, loadDataButton);

            dialog.setScene(dialogScene);
            dialog.setAlwaysOnTop(true);
            dialog.setResizable(false);
            dialog.setX(10);
            dialog.alwaysOnTopProperty();
            dialog.show();
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


        citiesLibraryTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
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
            }
        });
    }
}