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
    //requires org.testng;
    requires junit;
    requires java.sql;
    requires amadeus.java;
    //requires GMapsFX;  //for json deserialization
    requires  java.base;
    requires json.simple;
    requires java.annotation;
//    requires javax.annotation;
    //requires javax.annotation;


    opens gr.hua.oopii.travelAgency.GUI to javafx.fxml;

    exports gr.hua.oopii.travelAgency.GUI;
    exports gr.hua.oopii.travelAgency.API.openData;
    exports gr.hua.oopii.travelAgency.API.openWeather;
    opens gr.hua.oopii.travelAgency;
    exports gr.hua.oopii.travelAgency.exception;
    exports gr.hua.oopii.travelAgency.API.iata;
    exports gr.hua.oopii.travelAgency.API.covidRestrictions;

    exports gr.hua.oopii.travelAgency.API.airportsRadar to com.fasterxml.jackson.databind;

    //opens java.time to com.fasterxml.jackson.databind;

//     java.base does not "opens java.time" com.fasterxml.jackson.databind

}