package com.alphadev.artemisjvfx.controllers.Admin;

import com.alphadev.artemisjvfx.gui.gui;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    public Button dashboard_Admin_btn;
    public Button transaction_Admin_btn;
    public Button portefeuille_Admin_btn;
    public Button credit_Admin_btn;
    public Button certif_Admin_btn;
    public Button projet_Admin_btn;
    public Button profile_Admin_btn;
    public Button logout_Admin_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }

    private void addListeners(){
        dashboard_Admin_btn.setOnAction(event->onDashboard());
        transaction_Admin_btn.setOnAction(event -> onTransactions());
        credit_Admin_btn.setOnAction(event -> onCredits());
        projet_Admin_btn.setOnAction(event -> onProjects());
        portefeuille_Admin_btn.setOnAction(event->onPortefeuille());
        profile_Admin_btn.setOnAction(event->onProfile());
        certif_Admin_btn.setOnAction(event-> onCertif());
        logout_Admin_btn.setOnAction(event -> onLogout() );

    }

    private void onDashboard(){
        gui.getInstance().getViewFactory().getAdminSelectedMenuItem().set("DashboardAdmin");
    }

    private void onTransactions()
    {
        gui.getInstance().getViewFactory().getAdminSelectedMenuItem().set("TransactionsAdmin");
    }

    private  void onCredits()
    {
        gui.getInstance().getViewFactory().getAdminSelectedMenuItem().set("CreditsAdmin");
    }
    private void onProjects(){
        gui.getInstance().getViewFactory().getAdminSelectedMenuItem().set("ProjetsAdmin");
    }
    private void onPortefeuille()
    {
        gui.getInstance().getViewFactory().getAdminSelectedMenuItem().set("PortefeuilleAdmin");
    }
    private void onCertif()
    {
        gui.getInstance().getViewFactory().getAdminSelectedMenuItem().set("CertifsAdmin");
    }

    private void onProfile()
    {
        gui.getInstance().getViewFactory().getAdminSelectedMenuItem().set("ProfileAdmin");
    }

    private void onLogout()
    {
        Stage stage = (Stage) logout_Admin_btn.getScene().getWindow();
        //ProfileController.user=null;
        gui.getInstance().getViewFactory().closeStage(stage);
        gui.getInstance().getViewFactory().showLoginWindow();
    }
}
