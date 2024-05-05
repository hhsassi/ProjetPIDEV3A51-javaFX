package com.alphadev.artemisjvfx.controllers.Client;

import com.alphadev.artemisjvfx.models.Remboursement;
import com.alphadev.artemisjvfx.services.ControleDeSaisie;
import com.alphadev.artemisjvfx.services.ServiceRemborsement;
import com.alphadev.artemisjvfx.utils.DBConnection;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
//qr


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import tray.notification.TrayNotification;

public class RemboursementController implements Initializable {
    public TableView<Remboursement> table_pret;
    public TableColumn <Remboursement,Double>valeur;
    public TableColumn<Remboursement,Integer> duree;
    public TableColumn<Remboursement,Double>valeur_tranche;
    public TableColumn<Remboursement,String> etat;
    public TableColumn <Remboursement,Integer>pret_id;
    public TableColumn <Remboursement,Integer>id1;
    public ComboBox pret_id1;
    public TextField valeur1;
    public TextField duree1;
    public TextField valeur_tranche1;

    public TextField etat1;
    public ImageView codeqrr;
    public Button codeQr;
    ControleDeSaisie controleDeSaisie = new ControleDeSaisie();

    private ResultSet rs = null ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceRemborsement rb= new ServiceRemborsement();
       // rb.realAll1();

        List<Remboursement> li =rb.realAll1();
        ObservableList<Remboursement> data = FXCollections.observableArrayList(li);

        valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        valeur_tranche.setCellValueFactory(new PropertyValueFactory<>("valeur_tranche"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        pret_id.setCellValueFactory(new PropertyValueFactory<>("pret_id"));
        id1.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_pret.setItems(data);
        /***************************************************************/

        try {
            // TODO
            Connection cnx = DBConnection.getInstance().getCnx();
            rs = cnx.createStatement().executeQuery("SELECT id FROM pret ");
            // Now add the comboBox addAll statement
            while (rs.next()){
                pret_id1.getItems().addAll(rs.getString("id"));

            }
        } catch (SQLException ex) {
        }
    }

    public void ajouter(ActionEvent actionEvent) {
        String valeur_=valeur1.getText();
        String valeur_tranche_=valeur_tranche1.getText();
        String etat_=etat1.getText();
        String duree_=duree1.getText();
        if (!controleDeSaisie.chekNumeroP(valeur_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Valeur doit etre  numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }else if  (duree_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Duree doit etre saisi");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (!controleDeSaisie.chekNumeroP(duree_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Duree doit etre saisi Numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (valeur_tranche_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("valeur_tranche doit etre saisi");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (!controleDeSaisie.chekNumeroP(valeur_tranche_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("valeur_tranche  doit etre saisi Numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        else if (etat_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etat doit etre saisi ");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (controleDeSaisie.chekNumeroP(etat_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etat  doit etre saisi String");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }



else{
            ServiceRemborsement rb= new ServiceRemborsement();
            Connection cnx = DBConnection.getInstance().getCnx();
            try {
                ResultSet rs6 = null;
                String v= String.valueOf(pret_id1.getValue());
                rs6 = cnx.createStatement().executeQuery("SELECT id FROM pret where id='"+v+"'");

                rs6.next();
                int id1 = rs6.getInt("id");
                rb.add(new Remboursement(Double.parseDouble(valeur_),Integer.parseInt(duree_),Double.parseDouble(valeur_tranche_),etat_,id1));


            } catch (SQLException e) {

            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Remboursement  Ajoutee  avec succès!");
            alert.showAndWait();
            List<Remboursement> li =rb.realAll1();
            ObservableList<Remboursement> data = FXCollections.observableArrayList(li);

            valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
            duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
            valeur_tranche.setCellValueFactory(new PropertyValueFactory<>("valeur_tranche"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            pret_id.setCellValueFactory(new PropertyValueFactory<>("pret_id"));
            id1.setCellValueFactory(new PropertyValueFactory<>("id"));
            table_pret.setItems(data);

        }

    }

    public void supprimer(ActionEvent actionEvent) throws SQLException {
        ServiceRemborsement rb= new ServiceRemborsement();
        int myIndex = table_pret.getSelectionModel().getSelectedIndex();
        System.out.println("index :"+myIndex);
        //int id_sup = Integer.parseInt(String.valueOf(table_pret.getItems().get(myIndex).getClass()));
        int id_sup = Integer.parseInt(String.valueOf(table_pret.getItems().get(myIndex).getId()));
        System.out.println("id_sup:"+id_sup);
        rb.delete(new Remboursement(id_sup));
        /******/
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Supprimé avec succée !");
        alert.show();

        List<Remboursement> li =rb.realAll1();
        ObservableList<Remboursement> data = FXCollections.observableArrayList(li);

        valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        valeur_tranche.setCellValueFactory(new PropertyValueFactory<>("valeur_tranche"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        pret_id.setCellValueFactory(new PropertyValueFactory<>("pret_id"));
        id1.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_pret.setItems(data);
        notiff();
    }

    public void Update(ActionEvent actionEvent) {
        String valeur_=valeur1.getText();
        String valeur_tranche_=valeur_tranche1.getText();
        String etat_=etat1.getText();
        String duree_=duree1.getText();
        if (!controleDeSaisie.chekNumeroP(valeur_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Valeur doit etre  numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if  (duree_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Duree doit etre saisi");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (!controleDeSaisie.chekNumeroP(duree_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Duree doit etre saisi Numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (valeur_tranche_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("valeur_tranche doit etre saisi");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        else if (!controleDeSaisie.chekNumeroP(valeur_tranche_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("valeur_tranche  doit etre saisi Numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }else if (etat_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etat doit etre saisi ");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }else if (controleDeSaisie.chekNumeroP(etat_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Etat  doit etre saisi String");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
       

else{
            ServiceRemborsement rb= new ServiceRemborsement();
            Connection cnx = DBConnection.getInstance().getCnx();
            try {
                ResultSet rs6 = null;
                String v= String.valueOf(pret_id1.getValue());
                rs6 = cnx.createStatement().executeQuery("SELECT id FROM pret where id='"+v+"'");

                rs6.next();
                int id1 = rs6.getInt("id");
                int myIndex = table_pret.getSelectionModel().getSelectedIndex();
                System.out.println("index :"+myIndex);
                int id_up = Integer.parseInt(String.valueOf(table_pret.getItems().get(myIndex).getId()));
                rb.update(new Remboursement(id_up,Double.parseDouble(valeur_),Integer.parseInt(duree_),Double.parseDouble(valeur_tranche_),etat_,id1));


            } catch (SQLException e) {

            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Remboursement  Updated  avec succès!");
            alert.showAndWait();

            List<Remboursement> li =rb.realAll1();
            ObservableList<Remboursement> data = FXCollections.observableArrayList(li);

            valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
            duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
            valeur_tranche.setCellValueFactory(new PropertyValueFactory<>("valeur_tranche"));
            etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
            pret_id.setCellValueFactory(new PropertyValueFactory<>("pret_id"));
            id1.setCellValueFactory(new PropertyValueFactory<>("id"));
            table_pret.setItems(data);

        }


    }

    public void remb(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Credits.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML content
            Scene scene = new Scene(root);

            // Get the stage from the event source (assuming the button is on the current stage)
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene on the stage and display it
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            // Handle the IOException if the FXML file cannot be loaded
            System.err.println("Error loading FXML file: " + e.getMessage());
            // You can also consider showing an error dialog to the user
        }
    }




    private void notiff() {

        tray.notification.TrayNotification notification = new tray.notification.TrayNotification();
        AnimationType type = AnimationType.POPUP;
        notification.setAnimationType(type);
        notification.setTitle("Remboursement ");
        notification.setMessage("Remboursement:  a été supprimé  .");
        notification.setNotificationType(NotificationType.INFORMATION);
        notification.showAndDismiss(Duration.millis(2000));

    }

    public void codeQr(ActionEvent actionEvent) {



    }

    public void credit_Admin(ActionEvent actionEvent) {

        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/CreditsAdmin.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML content
            Scene scene = new Scene(root);

            // Get the stage from the event source (assuming the button is on the current stage)
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene on the stage and display it
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            // Handle the IOException if the FXML file cannot be loaded
            System.err.println("Error loading FXML file: " + e.getMessage());
            // You can also consider showing an error dialog to the user
        }
    }

    public void credit_btn(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Credits.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML content
            Scene scene = new Scene(root);

            // Get the stage from the event source (assuming the button is on the current stage)
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene on the stage and display it
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            // Handle the IOException if the FXML file cannot be loaded
            System.err.println("Error loading FXML file: " + e.getMessage());
            // You can also consider showing an error dialog to the user
        }
    }
}
