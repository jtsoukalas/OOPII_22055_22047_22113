package gr.hua.oopii.travelAgency.GUI;

import gr.hua.oopii.travelAgency.Control;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;
import gr.hua.oopii.travelAgency.exception.StopRunningException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    public Spinner<Integer> age;
    public TextArea recommendationsTextArea;
    public CheckBox uppercaseCheckBox;  //FIXME: Change related method so it doesnt effect cities library (source data)
    public ChoiceBox<String> sortChoiceBox;
    public TextArea citiesLibraryTextArea;
    public Tab citiesLibraryTab;
    public Tab recommendTab;
    public TabPane tabs;


    @FXML
    protected void gainRecommendationsButtonAction() throws NoRecommendationException, StopRunningException {
        recommendationsTextArea.setText(Control.recommendationToString(Control.runPerceptron(age.getValue(), uppercaseCheckBox.isSelected())));
        sortChoiceBox.getSelectionModel().select(Control.retrieveDefaultSortingOption());
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
    protected void updateCitiesLibraryTextArea() {
        citiesLibraryTextArea.setText(Control.presentCitiesLibrary());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Control.retrieveCitiesLibraryJson();

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
    }
}