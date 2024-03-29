package com.alphadev.artemisjvfx.gui;

import com.alphadev.artemisjvfx.controllers.Admin.AdminController;
import com.alphadev.artemisjvfx.controllers.Client.ClientController;
import com.alphadev.artemisjvfx.controllers.ProfileController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    // Client View
    private final StringProperty clientSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane transactionView;
    private AnchorPane creditsView;
    private AnchorPane portefeuilleView;
    private AnchorPane projetView;
    private AnchorPane certifView;
    private AnchorPane profileView;
//Admin

    private final StringProperty adminSelectedMenuItem;
    private AnchorPane dashboardAdminView;
    private AnchorPane transactionAdminView;
    private AnchorPane creditsAdminView;
    private AnchorPane portefeuilleAdminView;
    private AnchorPane projetAdminView;
    private AnchorPane certifAdminView;

    public ViewFactory(){
        this.clientSelectedMenuItem = new SimpleStringProperty("");
        this.adminSelectedMenuItem = new SimpleStringProperty("");
    }
    public StringProperty getClientSelectedMenuItem(){
        return clientSelectedMenuItem;
    }
    public AnchorPane getDashboardView()
    {
        if( dashboardView == null)
        {
            try{
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Client/Dashboard.fxml")).load();
            }catch( Exception e)
            {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader);
    }


    public void showSignInWindow(){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/SignIn.fxml"));
        createStage(loader);
    }

    public void showClientWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Client.fxml"));
        ClientController clientController = new ClientController();
        loader.setController(clientController);
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try{
            scene = new Scene(loader.load());
        }catch(Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Artemis Bank");
        stage.show();
    }
    public void closeStage(Stage stage){
        stage.close();
    }

    public AnchorPane getTransactionView() {
        if(transactionView == null)
        {
            try{
                transactionView = new FXMLLoader(getClass().getResource("/Fxml/Client/Transaction.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return transactionView;
    }

    public AnchorPane getCreditsView(){
        if(creditsView == null){
            try{
                creditsView = new FXMLLoader(getClass().getResource("/Fxml/Client/Credits.fxml")).load();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
    }
        return creditsView;
    }

    public AnchorPane getPortefeuilleView()
    {
        if(portefeuilleView == null)
        try{
            portefeuilleView = new FXMLLoader(getClass().getResource("/Fxml/Client/Portefeuille.fxml")).load();
        }catch (Exception e )
        {
            e.printStackTrace();
        }
        return portefeuilleView;

    }

    public AnchorPane getProjetView()
    {
            if(projetView == null)
            {
                try{
                    projetView = new FXMLLoader(getClass().getResource("/Fxml/Client/Projets.fxml")).load();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
            return projetView;
    }

    public AnchorPane getCertifView()
    {
        if(certifView == null)
        {
            try{
                certifView = new FXMLLoader(getClass().getResource("/Fxml/Client/Certifs.fxml")).load();
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }
        return certifView;
    }
    public AnchorPane getProfileView() {
        if(profileView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Profile.fxml"));
                Parent root = loader.load();
                ProfileController profileController = loader.getController();
                // Now that the controller is fully initialized, you can call methods on it
                profileController.displayData(); // Pass user data to display on the profile page
                profileView = (AnchorPane) root;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return profileView;
    }
    //Admin
    public StringProperty getAdminSelectedMenuItem(){
        return adminSelectedMenuItem;
    }
    public void showAdminWindow()
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Admin.fxml"));
        AdminController adminController = new AdminController();
        loader.setController(adminController);
        createStage(loader);

    }
    public AnchorPane getDashboardAdminView()
    {
        if( dashboardAdminView == null)
        {
            try{
                dashboardAdminView = new FXMLLoader(getClass().getResource("/Fxml/Admin/DashboardAdmin.fxml")).load();
            }catch( Exception e)
            {
                e.printStackTrace();
            }
        }
        return dashboardAdminView;
    }

    public AnchorPane getTransactionAdminView() {
        if(transactionAdminView == null)
        {
            try{
                transactionAdminView = new FXMLLoader(getClass().getResource("/Fxml/Admin/TransactionAdmin.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return transactionAdminView;
    }

    public AnchorPane getCreditsAdminView(){
        if(creditsAdminView == null){
            try{
                creditsAdminView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreditsAdmin.fxml")).load();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        return creditsAdminView;
    }

    public AnchorPane getPortefeuilleAdminView()
    {
        if(portefeuilleAdminView == null)
            try{
                portefeuilleAdminView = new FXMLLoader(getClass().getResource("/Fxml/Admin/PortefeuilleAdmin.fxml")).load();
            }catch (Exception e )
            {
                e.printStackTrace();
            }
        return portefeuilleAdminView;

    }

    public AnchorPane getProjetAdminView()
    {
        if(projetAdminView == null)
        {
            try{
                projetAdminView = new FXMLLoader(getClass().getResource("/Fxml/Admin/ProjetsAdmin.fxml")).load();
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }
        return projetAdminView;
    }

    public AnchorPane getCertifAdminView()
    {
        if(certifAdminView == null)
        {
            try{
                certifAdminView = new FXMLLoader(getClass().getResource("/Fxml/Admin/CertifsAdmin.fxml")).load();
            }catch(Exception e)
            {
                e.printStackTrace();
            }

        }
        return certifAdminView;
    }
    /*
    public AnchorPane getProfileAdminView() {
        if(profileView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Profile.fxml"));
                Parent root = loader.load();
                ProfileController profileController = loader.getController();
                // Now that the controller is fully initialized, you can call methods on it
                profileController.displayData(); // Pass user data to display on the profile page
                profileView = (AnchorPane) root;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

}
