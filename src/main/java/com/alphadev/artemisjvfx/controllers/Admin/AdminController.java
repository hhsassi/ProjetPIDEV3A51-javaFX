package com.alphadev.artemisjvfx.controllers.Admin;

import com.alphadev.artemisjvfx.gui.gui;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    public BorderPane admin_parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gui.getInstance().getViewFactory().getAdminSelectedMenuItem().addListener((observableValue, oldVal, newVal) ->{
            switch (newVal){
                case "TransactionsAdmin" -> admin_parent.setCenter(gui.getInstance().getViewFactory().getTransactionAdminView());
                case "CreditsAdmin" -> admin_parent.setCenter(gui.getInstance().getViewFactory().getCreditsAdminView());
                case "PortefeuilleAdmin" -> admin_parent.setCenter(gui.getInstance().getViewFactory().getPortefeuilleAdminView());
                case "Investments" -> admin_parent.setCenter(gui.getInstance().getViewFactory().getInvestmentsAdminView());

                case "ProjetsAdmin" -> admin_parent.setCenter(gui.getInstance().getViewFactory().getProjetAdminView());
                case "CertifsAdmin" -> admin_parent.setCenter(gui.getInstance().getViewFactory().getCertifAdminView());
                case "ProfileAdmin" -> admin_parent.setCenter(gui.getInstance().getViewFactory().getProfileView());

                default ->admin_parent.setCenter(gui.getInstance().getViewFactory().getDashboardAdminView());
            }
        } );

    }
}
