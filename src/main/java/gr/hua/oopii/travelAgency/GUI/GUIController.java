package gr.hua.oopii.travelAgency.GUI;

import gr.hua.oopii.travelAgency.Control;
import gr.hua.oopii.travelAgency.exception.NoRecommendationException;
import gr.hua.oopii.travelAgency.exception.StopRunningException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    public Spinner<Integer> age;
    public TextArea recommendationsTextArea;
    public CheckBox uppercaseCheckBox;
    @FXML
    private Label welcomeText;



    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void gainRecommendationsButton() throws NoRecommendationException, StopRunningException {
        recommendationsTextArea.setText(Control.recommendationToString(Control.runPerceptron(age.getValue(),uppercaseCheckBox.isSelected())));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(16, 125);
        spinnerValueFactory.setValue(16);
        age.setValueFactory(spinnerValueFactory);

//        try {
//            control = new Control("Athens", "GR");
//        } catch (StopRunningException e) {
//            e.printStackTrace();
//        } catch (NoSuchCityException e) {
//            e.printStackTrace();
//        }
    }
}