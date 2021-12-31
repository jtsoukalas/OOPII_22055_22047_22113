module gr.hua.oopii.travelAgency {
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
    requires java.sql;  //for json deserialization


    opens gr.hua.oopii.travelAgency.GUI to javafx.fxml;

    exports gr.hua.oopii.travelAgency.GUI;
    exports gr.hua.oopii.travelAgency.openData;
    exports gr.hua.oopii.travelAgency.openWeather;
    opens gr.hua.oopii.travelAgency;
    exports gr.hua.oopii.travelAgency.exception;
}