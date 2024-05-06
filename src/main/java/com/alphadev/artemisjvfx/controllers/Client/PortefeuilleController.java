package com.alphadev.artemisjvfx.controllers.Client;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PortefeuilleController {
    public static  User user = null;


    @FXML
    private TableView<Actions> table_action;
    @FXML
    private TableColumn<Actions, Date> DateColumn;
    @FXML
    private TableColumn<?, ?> qteColumn;
    @FXML
    private TableColumn<Actions, Integer> IDColumn;

    @FXML
    private TableColumn<Actions, Integer> IdPortColumn;
    @FXML
    private TableColumn<Actions, String> SymbolColumn;

    @FXML
    private TableColumn<Actions, Double> ValueColumn;

    @FXML
    private Button btnDelete;
    @FXML
    private Label totalValueLabel;


    private ActionsService actionsService;

    private PortfolioService portfolioService;
    public void initialize(URL url, ResourceBundle resourceBundle) {
        portfolioService = new PortfolioService();

        this.actionsService = new ActionsService();
        this.actionsService = new ActionsService();
        this.portfolioService = new PortfolioService();

        //IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        SymbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        ValueColumn.setCellValueFactory(new PropertyValueFactory<>("valeurAchat"));
        qteColumn.setCellValueFactory(new PropertyValueFactory<>("qte"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        // IdPortColumn.setCellValueFactory(new PropertyValueFactory<>("idPortfolio"));

        loadActions();

    }
    private void loadActions() {
        table_action.getItems().clear();
        try {
            ObservableList<Actions> actions = FXCollections.observableArrayList(actionsService.getAll());
            table_action.setItems(actions);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load actions", e.getMessage());
        }
    }
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void Delete(ActionEvent actionEvent) {
        Actions selectedAction = table_action.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) btnDelete.getScene().getWindow();

        if (selectedAction != null) {
            try {
                actionsService.delete(selectedAction);
                loadActions();
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete action", e.getMessage());
            }
        } else {
            showAlert("Error", "No action selected", "Please select an action to delete");
        }
    }
    public void investments(ActionEvent actionEvent) {
        try {
            gui.getInstance().getViewFactory().getClientSelectedMenuItem().set("Investments");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }



