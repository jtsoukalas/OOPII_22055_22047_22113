module gr.hua.oopii.travelAgency.GUI.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;
    requires junit;
    requires com.fasterxml.jackson.annotation;
    requires commons.lang;


    opens gr.hua.oopii.travelAgency.GUI.main to javafx.fxml;
    exports gr.hua.oopii.travelAgency.GUI.main;
}