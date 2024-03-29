package com.alphadev.artemisjvfx.controllers.Client;

import com.alphadev.artemisjvfx.gui.gui;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane client_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gui.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldVal, newVal) ->{
            switch (newVal){
                case "Transactions" -> client_parent.setCenter(gui.getInstance().getViewFactory().getTransactionView());
                case "Credits" -> client_parent.setCenter(gui.getInstance().getViewFactory().getCreditsView());
                case "Portefeuille" -> client_parent.setCenter(gui.getInstance().getViewFactory().getPortefeuilleView());
                case "Projets" -> client_parent.setCenter(gui.getInstance().getViewFactory().getProjetView());
                case "Certifs" -> client_parent.setCenter(gui.getInstance().getViewFactory().getCertifView());
                case "Profile" -> client_parent.setCenter(gui.getInstance().getViewFactory().getProfileView());

                default ->client_parent.setCenter(gui.getInstance().getViewFactory().getDashboardView());
            }
        } );

    }
}
