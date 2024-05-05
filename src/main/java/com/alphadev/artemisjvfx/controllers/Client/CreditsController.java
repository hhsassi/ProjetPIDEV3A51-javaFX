package com.alphadev.artemisjvfx.controllers.Client;

import com.alphadev.artemisjvfx.gui.gui;
import com.alphadev.artemisjvfx.models.Remboursement;
import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.models.pret;
import com.alphadev.artemisjvfx.services.ControleDeSaisie;
import com.alphadev.artemisjvfx.services.ServicePret;
import com.alphadev.artemisjvfx.services.ServiceRemborsement;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CreditsController implements Initializable {
    public static User user = null;
    public TableView<pret> table_pret;
    public TableColumn<pret, Integer> id;
    public TableColumn<pret,Double> valeur;
    public TableColumn<pret,String> motif;
    public TableColumn<pret,Integer> salaire;
    public TableColumn<pret,Integer> garantie;
    public TableColumn<pret,Double> valeur_garantie;
    public TableColumn<pret,Integer> user_id;
    public TableColumn <pret,Integer>user_id1;
    public TextField valeur1;
    public TextField motif1;
    public TextField garantie1;
    public TextField salaire1;
    public TextField valeur_garantie1;
    public Button load;

    ControleDeSaisie controleDeSaisie = new ControleDeSaisie();
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
    }
    public void ajouter(ActionEvent actionEvent) {

        String valeur_= valeur1.getText();
     String salaire_= salaire1.getText();
      String motif_=motif1.getText();
      String garantie_=garantie1.getText();
     String valeur_garantie_= valeur_garantie1.getText();
        if (!controleDeSaisie.chekNumeroP(valeur_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Valeur doit etre  numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (motif_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Motif doit etre saisi");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (!(motif_.substring(0, 1).equals(motif_.substring(0, 1).toUpperCase()))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Motif doit etre commence  par lettre Majuscule ");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }


        else if (salaire_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Salaire doit etre saisi ");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (!controleDeSaisie.chekNumeroP(salaire_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Salaire doit etre saisi Numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (!controleDeSaisie.chekNumeroP(garantie_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Garantie doit etre saisi Numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if  (garantie_.length()<4) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(" garantie doit etre de lengeur superieur a 4 ");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if  (valeur_garantie_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("valeur_garantie doit etre saisi");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        else {

            ServicePret pr=new ServicePret();
            pr.add(new pret(Integer.parseInt(valeur_),motif_,Integer.parseInt(salaire_),Integer.parseInt(garantie_),valeur_garantie_,7));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Pret  inséré avec succès!");
            alert.showAndWait();
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
        /*'..........................................................................''*/

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

    public void Update(ActionEvent actionEvent) {
        ServicePret pret=new ServicePret();

        String valeur_= valeur1.getText();
        String salaire_= salaire1.getText();
        String motif_=motif1.getText();
        String garantie_=garantie1.getText();
        String valeur_garantie_= valeur_garantie1.getText();
        if (!controleDeSaisie.chekNumeroP(valeur_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Valeur doit etre  numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        } else if (motif_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Motif doit etre saisi");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        else if (!(motif_.substring(0, 1).equals(motif_.substring(0, 1).toUpperCase()))) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Motif doit etre commence  par lettre Majuscule ");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }

        else if (salaire_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Salaire doit etre saisi ");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (!controleDeSaisie.chekNumeroP(salaire_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Salaire doit etre saisi Numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (garantie_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Garantie doit etre saisi ");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if (!controleDeSaisie.chekNumeroP(garantie_)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("garantie doit etre saisi Numbre");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if  (garantie_.length()<4) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(" garantie doit etre de longeur superieur a 4 ");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else if  (valeur_garantie_.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("valeur_garantie doit etre saisi");
            alert.setTitle("Probleme");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        else
        {
            int myIndex = table_pret.getSelectionModel().getSelectedIndex();
            System.out.println("index :"+myIndex);
            int id = Integer.parseInt(String.valueOf(table_pret.getItems().get(myIndex).getId()));
            ServicePret pr=new ServicePret();
            pr.update(new pret(id,Integer.parseInt(valeur_),motif_,Integer.parseInt(salaire_),Integer.parseInt(garantie_),valeur_garantie_,2));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Pret updated avec succès!");
            alert.showAndWait();

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


    }


    public void load(ActionEvent actionEvent) {
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
    }

    public void remb(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Remboursement.fxml"));
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
    public void topdf(ActionEvent actionEvent) throws IOException, DocumentException {


        com.itextpdf.text.Document document = new com.itextpdf.text.Document();
        PdfWriter.getInstance(document, new FileOutputStream("table_pret.pdf"));
        document.open();

        PdfPTable pdfTable = new PdfPTable(  table_pret.getColumns().size());

        // Add table headers
        for (int i = 0; i <   table_pret.getColumns().size(); i++) {
            pdfTable.addCell(new Phrase(  table_pret.getColumns().get(i).getText()));
        }

        // Add table rows
        for (int i = 0; i <   table_pret.getItems().size(); i++) {
            for (int j = 0; j <   table_pret.getColumns().size(); j++) {
                Object cellValue =   table_pret.getColumns().get(j).getCellData(i);

                if (cellValue instanceof String) {
                    pdfTable.addCell(new Phrase((String) cellValue));
                } else if (cellValue instanceof Float) {
                    pdfTable.addCell(new Phrase(Float.toString((Float) cellValue)));
                }
                else if (cellValue instanceof Double) {
                    pdfTable.addCell(new Phrase(Double.toString((Double) cellValue)));
                }
                else if (cellValue instanceof Integer) {
                    pdfTable.addCell(new Phrase(Integer.toString((Integer) cellValue)));
                }


                else {
                    // Check if id_user column is empty
                    if (j ==   table_pret.getColumns().indexOf(valeur)) {
                        if (cellValue != null && !cellValue.toString().isEmpty()) {
                            pdfTable.addCell(new Phrase(cellValue.toString()));
                        }
                    } else {
                        pdfTable.addCell(new Phrase(""));
                    }
                }
            }
        }

        document.add(pdfTable);
        document.close();
        Process pro = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler table_pret.pdf");





    }


}

