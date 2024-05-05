module com.alphadev.artemisjvfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
    requires itextpdf;
    requires java.desktop;
    //requires core;
    requires TrayTester;
    requires zxing.core;

    opens com.alphadev.artemisjvfx to javafx.fxml;
    opens com.alphadev.artemisjvfx.models to javafx.base;

    exports com.alphadev.artemisjvfx;
    exports com.alphadev.artemisjvfx.controllers;
    exports com.alphadev.artemisjvfx.controllers.Client;
    exports com.alphadev.artemisjvfx.controllers.Admin;

}