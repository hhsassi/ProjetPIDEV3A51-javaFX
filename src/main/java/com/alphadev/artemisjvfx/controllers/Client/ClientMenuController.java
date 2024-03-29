package com.alphadev.artemisjvfx.controllers.Client;

import com.alphadev.artemisjvfx.controllers.ProfileController;
import com.alphadev.artemisjvfx.gui.gui;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientMenuController implements Initializable {
    public Button profile_btn;
    public Button logout_btn;
    public Button credit_btn;
    public Button certif_btn;

    public Button projet_btn;
    public Button portefeuille_btn;
    public Button transaction_btn;
    public Button dashboard_btn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners();
    }
    private void addListeners(){
        dashboard_btn.setOnAction(event->onDashboard());
        transaction_btn.setOnAction(event -> onTransactions());
        credit_btn.setOnAction(event -> onCredits());
        projet_btn.setOnAction(event -> onProjects());
        portefeuille_btn.setOnAction(event->onPortefeuille());
        profile_btn.setOnAction(event->onProfile());
        certif_btn.setOnAction(event-> onCertif());
        logout_btn.setOnAction(event -> onLogout() );

    }

    private void onDashboard(){
        gui.getInstance().getViewFactory().getClientSelectedMenuItem().set("Dashboard");
    }

    private void onTransactions()
    {
        gui.getInstance().getViewFactory().getClientSelectedMenuItem().set("Transactions");
    }

    private  void onCredits()
    {
        gui.getInstance().getViewFactory().getClientSelectedMenuItem().set("Credits");
    }
    private void onProjects(){
        gui.getInstance().getViewFactory().getClientSelectedMenuItem().set("Projets");
    }
    private void onPortefeuille()
    {
        gui.getInstance().getViewFactory().getClientSelectedMenuItem().set("Portefeuille");
    }
    private void onCertif()
    {
        gui.getInstance().getViewFactory().getClientSelectedMenuItem().set("Certifs");
    }

    private void onProfile()
    {
        gui.getInstance().getViewFactory().getClientSelectedMenuItem().set("Profile");
    }

    private void onLogout()
    {
        Stage stage = (Stage) logout_btn.getScene().getWindow();
        //ProfileController.user=null;
        gui.getInstance().getViewFactory().closeStage(stage);
        gui.getInstance().getViewFactory().showLoginWindow();
    }
}
