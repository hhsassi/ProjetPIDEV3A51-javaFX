package com.esprit.controllers.Transactions;

import com.esprit.models.Compte;
import com.esprit.models.Transaction;
import com.esprit.services.CompteService;
import com.esprit.services.TransactionService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AfficherTransactionsController {

    @FXML
    private ListView<Transaction> transactionsListView;
    private ObservableList<Transaction> transactionsObservableList;

    @FXML
    private ComboBox<Compte> cbCompteId;

    @FXML
    private TextField tfMontant;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfRibDest;

    @FXML
    private VBox detailsPane;


    private TransactionService transactionService;
    private CompteService compteService;
    @FXML
    private StackPane contentPane;
    @FXML
    private TextField searchByIdTextField;
    @FXML
    private PdfGeneratorController pdfGeneratorController;
    @FXML
    public ImageView qrCodeImageView;

    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    @FXML
    public void initialize() {
        transactionService = new TransactionService();
        compteService = new CompteService();
        pdfGeneratorController = new PdfGeneratorController();

        transactionsObservableList = FXCollections.observableArrayList();
        transactionsListView.setItems(transactionsObservableList);
        refreshTransactionsList();

        cbCompteId.setItems(FXCollections.observableArrayList(compteService.getAll()));

        detailsPane.setVisible(false);

        transactionsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Transaction item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vBox = new VBox(
                            new Label("Compte ID: " + item.getCompteId()),
                            new Label("Montant: " + item.getMontant()),
                            new Label("Date: " + item.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                            new Label("RIB Destination: " + item.getRibDest())
                    );
                    vBox.setSpacing(4);
                    setGraphic(vBox);
                }
            }
        });

        transactionsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfMontant.setText(String.valueOf(newValue.getMontant()));
                dpDate.setValue(newValue.getDate());
                tfRibDest.setText(newValue.getRibDest());
                cbCompteId.getSelectionModel().select(findCompte(newValue.getCompteId()));
                detailsPane.setVisible(true);
            }
        });
    }

    private void refreshTransactionsList() {
        transactionsObservableList.setAll(transactionService.getAll());
    }

    @FXML
    public void addTransaction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Transactions/AjouterTransactions.fxml"));
            Node ajouterTransactionView = loader.load();
            contentPane.getChildren().setAll(ajouterTransactionView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateTransaction(ActionEvent event) {
        Transaction selected = transactionsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to modify this transaction?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    selected.setMontant(Double.parseDouble(tfMontant.getText()));
                    selected.setDate(dpDate.getValue());
                    selected.setRibDest(tfRibDest.getText());
                    selected.setCompteId(cbCompteId.getValue().getId());



                    // Générer le code QR à partir des données de la transaction
                    try {
                        byte[] qrCodeData = QRCodeGenerator.generateQRCode(selected.toString(), 200, 200);
                        Image qrCodeImage = new Image(new ByteArrayInputStream(qrCodeData));

                        // Afficher le code QR dans ImageView ou dans une autre partie de votre interface utilisateur
                        qrCodeImageView.setImage(qrCodeImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Gérer les exceptions liées à la génération du code QR
                    }



                    transactionService.modifier(selected);
                    refreshTransactionsList();
                    detailsPane.setVisible(false);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Transaction modified successfully!");
                    successAlert.showAndWait();
                }
            });
        }
    }

    @FXML
    private void deleteTransaction(ActionEvent event) {
        Transaction selected = transactionsListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this transaction?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    transactionService.supprimer(selected.getId());
                    refreshTransactionsList();
                    detailsPane.setVisible(false);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Transaction deleted successfully!");
                    successAlert.showAndWait();
                }
            });
        }
    }

    private Compte findCompte(int compteId) {
        for (Compte compte : cbCompteId.getItems()) {
            if (compte.getId() == compteId) {
                return compte;
            }
        }
        return null; // in case there is no match
    }
    @FXML
    private void generatePdf() {
        pdfGeneratorController.setTransactions(transactionsObservableList);
        pdfGeneratorController.generatePdf();
    }


    @FXML
    void searchById() {
        String id = searchByIdTextField.getText(); // Obtenez l'ID à partir du champ de texte de recherche

        // Parcours de la liste des transactions pour trouver celle avec l'ID correspondant
        for (Transaction transaction : transactionsListView.getItems()) {
            if (transaction.getId() == Integer.parseInt(id)) { // Utilisez == pour comparer les valeurs
                // Sélectionnez la transaction dans la liste
                transactionsListView.getSelectionModel().select(transaction);
                // Faites défiler la liste pour la transaction sélectionnée
                transactionsListView.scrollTo(transaction);
                return; // Sortez de la méthode une fois que la transaction est trouvée
            }
        }

        // Si aucune transaction correspondante n'est trouvée, effacez la sélection
        transactionsListView.getSelectionModel().clearSelection();
    }
    @FXML
    void sortByDate() {
        Collections.sort(transactionsListView.getItems(), Comparator.comparing(Transaction::getDate));
    }

    @FXML
    void sortById() {
        Collections.sort(transactionsListView.getItems(), Comparator.comparingInt(Transaction::getId));
    }

}

