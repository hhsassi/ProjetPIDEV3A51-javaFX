package com.alphadev.artemisjvfx;

import com.alphadev.artemisjvfx.gui.gui;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage)   {
        gui.getInstance().getViewFactory().showLoginWindow();

    }
}
