module com.example.oopii_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;
    requires com.fasterxml.jackson.annotation;
    requires commons.lang;
    requires org.testng;
    requires junit;


    opens com.example.oopii_test to javafx.fxml;
    exports com.example.oopii_test;

    exports gr.hua.oopii.travelAgency.openData;
    exports gr.hua.oopii.travelAgency.openWeather;

}