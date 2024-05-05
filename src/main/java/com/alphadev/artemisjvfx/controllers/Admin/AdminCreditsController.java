package com.alphadev.artemisjvfx.controllers.Admin;

import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.models.pret;
import com.alphadev.artemisjvfx.services.ServicePret;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.alphadev.artemisjvfx.models.Remboursement;
import com.alphadev.artemisjvfx.services.ControleDeSaisie;
import com.alphadev.artemisjvfx.services.ServiceRemborsement;
import com.alphadev.artemisjvfx.utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
public class AdminCreditsController implements Initializable{
    public TableView <pret> table_pret;
    public TableColumn valeur;
    public TableColumn motif;
    public TableColumn salaire;
    public TableColumn garantie;
    public TableColumn valeur_garantie;
    public TableColumn user_id;
    public TableColumn user_id1;
    public TableView <Remboursement> table_remb;
    public TableColumn valeur1;
    public TableColumn duree;
    public TableColumn valeur_tranche;
    public TableColumn etat;
    public TableColumn pret_id;
    public TableColumn id1;
    public Button supprimer;
    public Button delete;
    public Button recherche;
    public TextField tfrecherche;
    public TextField trech;
    //public static  User user = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServicePret pr=new ServicePret();
        List<pret> li =pr.realAll();
        ObservableList<pret> data = FXCollections.observableArrayList(li);

        valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        motif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        salaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        garantie.setCellValueFactory(new PropertyValueFactory<>("garantie"));
        valeur_garantie.setCellValueFactory(new PropertyValueFactory<>("valeur_garantie"));
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        user_id1.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_pret.setItems(data);


        /*********************************************************************/
        ServiceRemborsement rb= new ServiceRemborsement();
        // rb.realAll1();

        List<Remboursement> li1 =rb.realAll1();
        ObservableList<Remboursement> data1= FXCollections.observableArrayList(li1);

        valeur1.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        valeur_tranche.setCellValueFactory(new PropertyValueFactory<>("valeur_tranche"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        pret_id.setCellValueFactory(new PropertyValueFactory<>("pret_id"));
        id1.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_remb.setItems(data1);
    }




    public void supprimer(ActionEvent actionEvent) {
        ServicePret pr=new ServicePret();
        int myIndex = table_pret.getSelectionModel().getSelectedIndex();
        System.out.println("index :"+myIndex);
        int id_sup = Integer.parseInt(String.valueOf(table_pret.getItems().get(myIndex).getId()));
        pr.delete(new pret(id_sup));
        /******/
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Supprimé avec succée !");
        alert.show();
        /**********************************************/


        List<pret> li =pr.realAll();
        ObservableList<pret> data = FXCollections.observableArrayList(li);

        valeur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        motif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        salaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        garantie.setCellValueFactory(new PropertyValueFactory<>("garantie"));
        valeur_garantie.setCellValueFactory(new PropertyValueFactory<>("valeur_garantie"));
        user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        user_id1.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_pret.setItems(data);
    }

    public void delete(ActionEvent actionEvent) throws SQLException {
        ServiceRemborsement rb= new ServiceRemborsement();
        int myIndex = table_remb.getSelectionModel().getSelectedIndex();
        System.out.println("index :"+myIndex);
        //int id_sup = Integer.parseInt(String.valueOf(table_pret.getItems().get(myIndex).getClass()));
        int id_sup = Integer.parseInt(String.valueOf(table_remb.getItems().get(myIndex).getId()));
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

        valeur1.setCellValueFactory(new PropertyValueFactory<>("valeur"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        valeur_tranche.setCellValueFactory(new PropertyValueFactory<>("valeur_tranche"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        pret_id.setCellValueFactory(new PropertyValueFactory<>("pret_id"));
        id1.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_remb.setItems(data);


    }

    public void Recherche(ActionEvent actionEvent) {
        ServiceRemborsement rb= new ServiceRemborsement();
        ObservableList<Remboursement> pretList = FXCollections.observableArrayList(rb.realAll1());
        trech.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                table_remb.setItems(pretList);
                return;
            }
            ObservableList<Remboursement> filteredList = FXCollections.observableArrayList();
            String lowerCaseFilter = newValue.toLowerCase();
            for (Remboursement event : pretList) {
                if (event.getEtat().toLowerCase().contains(lowerCaseFilter)) {
                    filteredList.add(event);
                }
            }
            table_remb.setItems(filteredList);
        });

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

    public void order(ActionEvent actionEvent) {
        ServicePret pr=new ServicePret();
        List<pret> li =pr.realAll();
        ObservableList<pret> data = FXCollections.observableArrayList(li);
        ObservableList<pret> sortedList = data.sorted(Comparator.comparing(pret::getValeur));
        table_pret.setItems(sortedList);
    }
}
