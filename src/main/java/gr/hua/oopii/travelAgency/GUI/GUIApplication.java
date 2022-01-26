package gr.hua.oopii.travelAgency.GUI;

import gr.hua.oopii.travelAgency.Control;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Travel Agency");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            boolean success = Control.saveCitiesLibraryJson();
            if (success){
                Control.mainLogger.info("Saving Json when closing app successful");
            } else {
                Control.mainLogger.warning("Saving Json when closing app: unsuccessful");
            }
        });
        stage.setResizable(false);

        Image icon = new Image(getClass().getResourceAsStream("travel-agency-logo.png"));
        stage.getIcons().add(icon);
    }

    public static void main(String[] args) {
        launch();
    }
}