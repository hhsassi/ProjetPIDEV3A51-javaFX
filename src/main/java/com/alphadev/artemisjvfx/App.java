package com.alphadev.artemisjvfx;

import com.alphadev.artemisjvfx.gui.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        gui.getInstance().getViewFactory().getCreditsView();


    }
}
