package com.alphadev.artemisjvfx.controllers.Admin;

import com.alphadev.artemisjvfx.gui.gui;
import com.alphadev.artemisjvfx.models.Actions;
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
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

import javax.mail.MessagingException;
import javax.mail.Transport;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class AdminActionsController implements Initializable {
    public static User user = null;

    public static ObservableList<Actions> adminActions;

    @FXML
    private TableView<Actions> table_action;
    @FXML
    private TableColumn<Actions, Date> DateColumn;

    @FXML
    private TableColumn<Actions, Integer> IdPortColumn;

    @FXML
    private TableColumn<Actions, String> SymbolColumn;

    @FXML
    private TableColumn<Actions, Double> ValueColumn;

    @FXML
    private Button btnportfolios;

    @FXML
    private DatePicker datetxt;

    @FXML
    private ComboBox<String> potrtidtxt;

    @FXML
    private TableColumn<?, ?> qteColumn;

    @FXML
    private TextField qtetxt;

    @FXML
    private TextField symboltxt;

    @FXML
    private TextField valuetxt;


    private ActionsService actionsService;
    private PortfolioService portfolioService;
    private ControleDeSaisie controleDeSaisie = new ControleDeSaisie();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.actionsService = new ActionsService();
        this.portfolioService = new PortfolioService();

        SymbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
        ValueColumn.setCellValueFactory(new PropertyValueFactory<>("valeurAchat"));
        qteColumn.setCellValueFactory(new PropertyValueFactory<>("qte"));
        DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        IdPortColumn.setCellValueFactory(new PropertyValueFactory<>("idPortfolio"));

        loadActions();
        try {
            List<Integer> portfolioIds = portfolioService.getAllPortfolioIds();
            List<String> portfolioIdStrings = portfolioIds.stream().map(String::valueOf).collect(Collectors.toList());

            // Convert list of strings to ObservableList
            ObservableList<String> observablePortfolioIds = FXCollections.observableArrayList(portfolioIdStrings);

            // Set items in ComboBox
            potrtidtxt.setItems(observablePortfolioIds);
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
        }
        ObservableList<Actions> adminActions = FXCollections.observableArrayList();

    }
    public ObservableList<Actions> getAdminActions() {
        return adminActions;
    }

    public void Add(ActionEvent actionEvent) {
        String symbol = symboltxt.getText();
        String valueStr = valuetxt.getText();
        String quantityStr = qtetxt.getText();
        Object portfolioIdObj = potrtidtxt.getValue();

        if (!controleDeSaisie.checkValeur(valueStr) || !controleDeSaisie.checkValeur(quantityStr) || portfolioIdObj == null) {
            showAlert("Problem", null, "Please enter valid values.", Alert.AlertType.ERROR);
            return;
        }

        double value = Double.parseDouble(valueStr);
        int quantity = Integer.parseInt(quantityStr);
        int portfolioId = Integer.parseInt(portfolioIdObj.toString());
        try {
            actionsService.add(new Actions(0, symbol, value, quantity, new Date(), portfolioId));

            // Send an email to notify the client
            sendEmailToClient(symbol, value, quantity);

            showAlert("Success", null, "Action added successfully!", Alert.AlertType.INFORMATION);
            loadActions();
        } catch (SQLException e) {
            showAlert("Error", "Failed to add action", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void Delete(ActionEvent actionEvent) {
        Actions selectedAction = table_action.getSelectionModel().getSelectedItem();
        if (selectedAction != null) {
            try {
                actionsService.delete(selectedAction);
                loadActions();
                showAlert("Success", null, "Action deleted successfully.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                showAlert("Error", "Failed to delete action", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "No action selected", "Please select an action to delete.", Alert.AlertType.ERROR);
        }
    }


    public void Update(ActionEvent actionEvent) {
        Actions selectedAction = table_action.getSelectionModel().getSelectedItem();
        if (selectedAction != null) {
            String symbol = symboltxt.getText();
            String valueStr = valuetxt.getText();
            String quantityStr = qtetxt.getText();
            Object portfolioIdObj = potrtidtxt.getValue();

            if (!controleDeSaisie.checkValeur(valueStr) || !controleDeSaisie.checkValeur(quantityStr) || portfolioIdObj == null) {
                showAlert("Error", null, "Please enter valid values.", Alert.AlertType.ERROR);
                return;
            }

            double value = Double.parseDouble(valueStr);
            int quantity = Integer.parseInt(quantityStr);
            int portfolioId = Integer.parseInt(portfolioIdObj.toString());

            selectedAction.setSymbol(symbol);
            selectedAction.setValeurAchat(value);
            selectedAction.setQte(quantity);
            selectedAction.setDate(new Date());
            selectedAction.setIdPortfolio(portfolioId);

            try {
                actionsService.update(selectedAction);
                loadActions();
                showAlert("Success", null, "Action updated successfully.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                showAlert("Error", "Failed to update action", e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "No action selected", "Please select an action to update.", Alert.AlertType.ERROR);
        }
    }


    private void loadActions() {
        table_action.getItems().clear();
        try {
            ObservableList<Actions> actions = FXCollections.observableArrayList(actionsService.getAll());
            table_action.setItems(actions);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load actions", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String headerText, String contentText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText != null ? headerText : ""); // Set empty string if headerText is null
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public void Portfolios(ActionEvent event) {
        try {
            gui.getInstance().getViewFactory().getAdminSelectedMenuItem().set("PortefeuilleAdmin");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/PortefeuilleAdmin.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
        }


    }

    private void sendEmailToClient(String symbol, double value, int quantity) {
        String username = "zeinebdaghfous2@gmail.com";
        String password = "ljmafzsawnpjecoe";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("userdagh@gmail.com"));
            message.setSubject("New action is added ! \n Start investment now!");
            message.setText("Cher client,\n\nUne nouvelle action a été ajoutée :\n" +
                    "Symbol : " + symbol + "\n" +
                    "Value : " + value + "\n" +
                    "Quantity : " + quantity + "\n\n" +
                    "Cordialement,\nArtemis bank");


            Transport.send(message);
            System.out.println("E-mail envoyé avec succès au client.");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
}