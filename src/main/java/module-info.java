module com.alphadev.artemisjvfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires stripe.java;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires java.mail;
    requires TrayTester;

    opens com.alphadev.artemisjvfx to javafx.fxml;
    opens com.alphadev.artemisjvfx.models to javafx.base;
    opens com.alphadev.artemisjvfx.controllers.Admin to javafx.fxml;
    opens com.alphadev.artemisjvfx.controllers.Client to javafx.fxml;


    exports com.alphadev.artemisjvfx;
    exports com.alphadev.artemisjvfx.controllers;
    exports com.alphadev.artemisjvfx.controllers.Client;
    exports com.alphadev.artemisjvfx.controllers.Admin;


}