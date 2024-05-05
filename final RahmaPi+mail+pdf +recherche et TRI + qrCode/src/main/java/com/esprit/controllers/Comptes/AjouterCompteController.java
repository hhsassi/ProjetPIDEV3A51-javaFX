package com.esprit.controllers.Comptes;

import com.esprit.models.Compte;
import com.esprit.services.CompteService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import com.esprit.utils.EmailSender;

import java.io.IOException;

public class AjouterCompteController {

    @FXML
    private TextField tfRib;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfSolde;
    @FXML
    private StackPane mainContentPane;

    private CompteService compteService;

    @FXML
    public void initialize() {
        compteService = new CompteService();
    }

    @FXML
    public void handleAjouterCompte(ActionEvent event) {
        String rib = tfRib.getText().trim();
        String type = tfType.getText().trim();
        double solde;

        if (rib.isEmpty() || type.isEmpty() || tfSolde.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            solde = Double.parseDouble(tfSolde.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le solde doit être un nombre valide.");
            return;
        }

        Compte newCompte = new Compte(rib, type, solde);
        compteService.ajouter(newCompte);
        EmailSender.sendConfirmationEmail("khaledcheour555@gmail.com","","");
        showAlert(Alert.AlertType.INFORMATION, "Compte Ajouté", "Le compte a été ajouté avec succès!");
        clearForm();
        loadAfficherComptesView();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearForm() {
        tfRib.clear();
        tfType.clear();
        tfSolde.clear();
    }

    private void loadAfficherComptesView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Comptes/AfficherComptes.fxml"));
            Parent afficherCompteView = loader.load();

            AfficherComptesController afficherComptesController = loader.getController();
            afficherComptesController.setMainContent(mainContentPane);

            mainContentPane.getChildren().setAll(afficherCompteView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
