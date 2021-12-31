package gr.hua.oopii.travelAgency.GUI;

import gr.hua.oopii.travelAgency.Control;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class GUIApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GUIApplication.class.getResource("GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Travel Agency");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            /**
             * Invoked when a specific event of the type for which this handler is
             * registered happens.
             *
             * @param event the event which occurred
             */
            @Override
            public void handle(WindowEvent event) {
                System.out.println("Saving Json when closing app: "+ Control.saveCitiesLibraryJson());
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }
}