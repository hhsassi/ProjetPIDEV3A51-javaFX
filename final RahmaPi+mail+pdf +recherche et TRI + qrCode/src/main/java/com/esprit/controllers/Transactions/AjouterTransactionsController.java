package com.esprit.controllers.Transactions;

import com.esprit.models.Transaction;
import com.esprit.models.Compte;
import com.esprit.services.TransactionService;
import com.esprit.services.CompteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

public class AjouterTransactionsController {

    @FXML
    private ComboBox<Compte> cbCompteId;
    @FXML
    private TextField tfMontant;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextField tfRibDest;
    @FXML
    private StackPane mainContentPane;

    private TransactionService transactionService;
    private CompteService compteService;

    @FXML
    public void initialize() {
        transactionService = new TransactionService();
        compteService = new CompteService();

        // Populate the ComboBox with Compte IDs
        List<Compte> comptes = compteService.getAll();
        ObservableList<Compte> observableComptes = FXCollections.observableArrayList(comptes);
        cbCompteId.setItems(observableComptes);
    }

    @FXML
    public void handleAjouterTransactions(ActionEvent event) {
        double montant;
        String ribDest = tfRibDest.getText().trim();

        if (cbCompteId.getValue() == null || tfMontant.getText().isEmpty() || dpDate.getValue() == null || ribDest.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            montant = Double.parseDouble(tfMontant.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le montant doit être un nombre valide.");
            return;
        }

        Transaction newTransaction = new Transaction(montant, dpDate.getValue(), ribDest, cbCompteId.getValue().getId());
        transactionService.ajouter(newTransaction);
        showAlert(Alert.AlertType.INFORMATION, "Transaction Ajoutée", "La transaction a été ajoutée avec succès!");
        clearForm();
        loadAfficherTransactionsView();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearForm() {
        cbCompteId.setValue(null);
        tfMontant.clear();
        dpDate.getEditor().clear();
        tfRibDest.clear();
    }

    private void loadAfficherTransactionsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Transactions/AfficherTransactions.fxml"));
            Parent afficherTransactionsView = loader.load();

            AfficherTransactionsController afficherTransactionsController = loader.getController();
            afficherTransactionsController.setMainContent(mainContentPane);

            mainContentPane.getChildren().setAll(afficherTransactionsView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



