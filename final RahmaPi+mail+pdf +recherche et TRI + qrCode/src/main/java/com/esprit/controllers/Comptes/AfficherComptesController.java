package com.esprit.controllers.Comptes;

import com.esprit.models.Compte;
import com.esprit.services.CompteService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AfficherComptesController {

    @FXML
    private ListView<Compte> comptesListView;
    private ObservableList<Compte> comptesObservableList;

    @FXML
    private TextField tfRib;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfSolde;

    @FXML
    private VBox detailsPane;

    private CompteService compteService;

    @FXML
    private StackPane contentPane;

    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    @FXML
    public void initialize() {
        compteService = new CompteService();

        comptesObservableList = FXCollections.observableArrayList();
        comptesListView.setItems(comptesObservableList);
        refreshComptesList();

        detailsPane.setVisible(false);

        comptesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Compte item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vBox = new VBox(
                            new Label("RIB: " + item.getRib()),
                            new Label("Type: " + item.getType()),
                            new Label("Solde: " + item.getSolde())
                    );
                    vBox.setSpacing(4);
                    setGraphic(vBox);
                }
            }
        });

        comptesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfRib.setText(newValue.getRib());
                tfType.setText(newValue.getType());
                tfSolde.setText(String.valueOf(newValue.getSolde()));
                detailsPane.setVisible(true);
            }
        });
    }

    private void refreshComptesList() {
        comptesObservableList.setAll(compteService.getAll());
    }

    @FXML
    public void addCompte(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Comptes/AjouterCompte.fxml"));
            Node ajouterCompteView = loader.load();
            contentPane.getChildren().setAll(ajouterCompteView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateCompte(ActionEvent event) {
        Compte selected = comptesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Do you really want to update this account?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    selected.setRib(tfRib.getText());
                    selected.setType(tfType.getText());
                    selected.setSolde(Double.parseDouble(tfSolde.getText()));
                    compteService.modifier(selected);
                    refreshComptesList();
                    detailsPane.setVisible(false);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Account updated successfully!");
                    successAlert.showAndWait();
                }
            });
        }
    }

    @FXML
    private void deleteCompte(ActionEvent event) {
        Compte selected = comptesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this account?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    compteService.supprimer(selected.getId());
                    refreshComptesList();
                    detailsPane.setVisible(false);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Account deleted successfully!");
                    successAlert.showAndWait();
                }
            });
        }
    }
}
