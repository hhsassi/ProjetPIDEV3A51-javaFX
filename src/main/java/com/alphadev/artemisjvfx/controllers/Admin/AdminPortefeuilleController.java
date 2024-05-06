package com.alphadev.artemisjvfx.controllers.Admin;
import com.alphadev.artemisjvfx.gui.gui;
import com.alphadev.artemisjvfx.models.Actions;
import com.alphadev.artemisjvfx.models.Portefeuille;
import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.services.ActionsService;
import com.alphadev.artemisjvfx.services.ControleDeSaisie;
import com.alphadev.artemisjvfx.services.PortfolioService;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminPortefeuilleController implements Initializable {
    public static  User user = null;


    @FXML
    private TableColumn<Portefeuille, String> NameColmn;

    @FXML
    private TableColumn<Portefeuille, Double> ValueColmn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;
    @FXML
    private Button btn_investment;
    @FXML
    private TableView<Portefeuille> table_portefeuille;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtValeur;

    private PortfolioService portfolioService;
    private ActionsService actionsService;

    ControleDeSaisie controleDeSaisie = new ControleDeSaisie();

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.portfolioService = new PortfolioService();

        NameColmn.setCellValueFactory(new PropertyValueFactory<>("nomPortefeuille"));
        ValueColmn.setCellValueFactory(new PropertyValueFactory<>("valeurTotale"));

        loadPortefeuilles();

    }


    public void Add(ActionEvent actionEvent) {
        String nomPortefeuille = txtName.getText();
        String valeurPortefeuille = txtValeur.getText();
        Stage stage = (Stage) btnAdd.getScene().getWindow();

        if (!controleDeSaisie.checkValeur(valeurPortefeuille)) {
            showAlert("Problem", null, "The portfolio value must be of type double", Alert.AlertType.ERROR);
        } else if (nomPortefeuille.isEmpty()) {
            showAlert("Problem", null, "The portfolio name must be entered", Alert.AlertType.ERROR);
        } else if (valeurPortefeuille.isEmpty()) {
            showAlert("Problem", null, "The portfolio value must be entered", Alert.AlertType.ERROR);
        } else {
            double valeur = Double.parseDouble(valeurPortefeuille);

            // Assuming you have a PortfolioService
            PortfolioService portfolioService = new PortfolioService();
            try {
                portfolioService.add(new Portefeuille(0, nomPortefeuille, valeur));
                showAlert("Information", null, "Portfolio added successfully!", Alert.AlertType.INFORMATION);
                loadPortefeuilles();
            } catch (SQLException e) {
                showAlert("Error", "Failed to add portfolio", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void Delete(ActionEvent actionEvent) {
        Stage stage = (Stage) btnDelete.getScene().getWindow();

        Portefeuille selectedPortefeuille = (Portefeuille) table_portefeuille.getSelectionModel().getSelectedItem();
        if (selectedPortefeuille != null) {
            try {
                portfolioService.delete(selectedPortefeuille);
                loadPortefeuilles();
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete portfolio", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "No portfolio selected", "Please select a portfolio to delete.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void Update(ActionEvent actionEvent) {
        String newName = txtName.getText();
        String newValue = txtValeur.getText();
        Stage stage = (Stage) btnUpdate.getScene().getWindow();

        if (newName.isEmpty() || newValue.isEmpty()) {
            showAlert("Problem", null, "Empty Field", Alert.AlertType.ERROR);
        } else {
            try {
                // Get the selected item from the table
                Portefeuille selectedPortefeuille = (Portefeuille) table_portefeuille.getSelectionModel().getSelectedItem();
                if (selectedPortefeuille != null) {
                    // Update the selected portfolio with new values
                    selectedPortefeuille.setNomPortefeuille(newName);
                    selectedPortefeuille.setValeurTotale(Double.parseDouble(newValue));

                    // Call the update method from PortfolioService
                    portfolioService.update(selectedPortefeuille);
                    loadPortefeuilles();

                    // Show success message
                    showAlert("Information Dialog", null, "Portfolio updated successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "No portfolio selected", "Please select a portfolio to update.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException e) {
                showAlert("Problem", "Invalid Value", "Please enter a valid numeric value for the amount.", Alert.AlertType.ERROR);
            } catch (SQLException e) {
                showAlert("Error", "Failed to update portfolio", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void loadPortefeuilles() {
        table_portefeuille.getItems().clear();
        try {
            ObservableList<Portefeuille> portefeuilles = portfolioService.getAll();
            table_portefeuille.setItems(portefeuilles);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load portfolio", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String headerText, String contentText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void investments(ActionEvent actionEvent) {
        try {
            gui.getInstance().getViewFactory().getAdminSelectedMenuItem().set("Investments");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/Actions.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
        }
    }
        public ObservableList<Actions> getAdminActions() {
            ObservableList<Actions> adminActions = FXCollections.observableArrayList();
            try {
                // Retrieve the list of admin actions from the service
                List<Actions> actionsList = actionsService.getAll();

                // Add the actions to the observable list
                adminActions.addAll(actionsList);
            } catch (SQLException e) {
                e.printStackTrace(); // Or handle the exception as appropriate
            }
            return adminActions;
        }
}
