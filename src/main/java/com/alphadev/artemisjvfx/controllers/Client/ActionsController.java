package com.alphadev.artemisjvfx.controllers.Client;
import com.alphadev.artemisjvfx.services.PaymentService;
import com.alphadev.artemisjvfx.services.StripepaymentService;

import com.alphadev.artemisjvfx.gui.gui;
import com.alphadev.artemisjvfx.models.Actions;
import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.services.ActionsService;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ActionsController implements Initializable {
    public static User user = null;
    private static final int ITEMS_PER_PAGE = 10;
    private PortfolioService portfolioService;

    private PaymentService paymentService;

    @FXML
    private TableView<Actions> table_action;
    @FXML
    private TableColumn<Actions, Date> DateColumn;
    @FXML
    private Pagination pagination;
    @FXML
    private ComboBox<Actions> ActionsCombo;
    @FXML
    private TextField QTECOl;
    @FXML
    private TableColumn<Actions, String> SymbolColumn;

    @FXML
    private TableColumn<Actions, Double> ValueColumn;

    @FXML
    private Button btnbuy;

    @FXML
    private Button btnportfolio;
    @FXML
    private TextField searchBtn;
    @FXML
    private TableColumn<Actions, Integer> qteColumn;

    private ActionsService actionsService;

    @FXML


        public void initialize(URL url, ResourceBundle resourceBundle) {

            portfolioService = new PortfolioService();
            this.actionsService = new ActionsService();
            this.portfolioService = new PortfolioService();

           // IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            SymbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbol"));
            ValueColumn.setCellValueFactory(new PropertyValueFactory<>("valeurAchat"));
            qteColumn.setCellValueFactory(new PropertyValueFactory<>("qte"));
            DateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            //IdPortColumn.setCellValueFactory(new PropertyValueFactory<>("idPortfolio"));
            initializeActionsComboBox();

            loadActions();
            actionsService = new ActionsService();
            initializePagination();
            paymentService = new StripepaymentService();


    }
    private void initializePagination() {
        try {
            int totalItems = actionsService.getTotalActionsCount();
            int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
            pagination.setPageCount(totalPages);
            pagination.setCurrentPageIndex(0); // Start at the first page by default
            pagination.setPageFactory(this::createPage);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private Node createPage(int pageIndex) {
        try {
            List<Actions> actions = actionsService.getActionsForPage(pageIndex, ITEMS_PER_PAGE);
            ObservableList<Actions> actionsList = FXCollections.observableArrayList(actions);
            table_action.setItems(actionsList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
        return table_action;
    }
    private void initializeActionsComboBox() {
        try {
            ObservableList<Actions> allActions = FXCollections.observableArrayList(actionsService.getAll());

            ActionsCombo.getItems().clear();

            ActionsCombo.setCellFactory(param -> new ListCell<Actions>() {
                @Override
                protected void updateItem(Actions item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getSymbol());
                    }
                }
            });

            ActionsCombo.getItems().addAll(allActions);
        } catch (SQLException e) {
            showAlert("Error", "Failed to load actions", e.getMessage());
        }
    }


    @FXML
    void portfolio(ActionEvent event) {
        try {
            gui.getInstance().getViewFactory().getClientSelectedMenuItem().set("Portefeuille");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Client/Portefeuille.fxml"));
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
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
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

    @FXML
    void selectAction(ActionEvent event) {
        Actions selectedAction = ActionsCombo.getValue();
        if (selectedAction != null) {
        } else {
            showAlert("Error", "No action selected", "Please select an action.");
        }
    }

    @FXML
   void selectQte(ActionEvent event) {
        String qteStr = QTECOl.getText().trim();
        if (!qteStr.isEmpty()) {
            try {
                int qte = Integer.parseInt(qteStr);
            } catch (NumberFormatException e) {
                showAlert("Error", "Invalid quantity", "Please enter a valid quantity.");
            }
        } else {
            showAlert("Error", "No quantity entered", "Please enter a quantity.");
        }
    }


    @FXML
    void Buy(ActionEvent event) {
        Actions selectedAction = ActionsCombo.getValue();
        String qteStr = QTECOl.getText().trim();

        if (selectedAction == null) {
            showAlert("Error", "No action selected", "Please select an action.");
            return;
        }

        if (qteStr.isEmpty()) {
            showAlert("Error", "No quantity entered", "Please enter a quantity.");
            return;
        }

        try {
            int qteRequested = Integer.parseInt(qteStr);
            int qteAvailable = selectedAction.getQte();

            if (qteRequested > qteAvailable) {
                showAlert("Error", "Insufficient quantity", "There is not enough quantity available for this action.");
                return;
            }
            paymentService.processTransaction(selectedAction, qteRequested);
            // Add the selected action to the portfolio
            portfolioService.addActionToPortfolio(selectedAction, qteRequested);
            showAlert("Success", "Action added to portfolio", "The action has been successfully added to your portfolio.");

            // Update portfolio view
            updatePortfolio(selectedAction, qteRequested);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid quantity", "Please enter a valid quantity.");
        } catch (Exception e) {
            showAlert("Error", "Failed to add action to portfolio", e.getMessage());
        }
    }

    private void updatePortfolio(Actions selectedAction, int qteRequested) {
        try {
            List<Actions> purchasedActionsList = portfolioService.getPurchasedActions();

            ObservableList<Actions> purchasedActions = FXCollections.observableArrayList(purchasedActionsList);

            boolean alreadyExists = false;
            for (Actions action : purchasedActions) {
                if (action.getId() == selectedAction.getId()) {
                    // Update the existing action with the new quantity
                    action.setQte(action.getSelectedQuantity());
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                purchasedActions.add(new Actions(selectedAction.getSymbol(), qteRequested));
            }

            table_action.setItems(purchasedActions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void searchbysymbol(ActionEvent event) {
        String symbol = searchBtn.getText().trim();
        try {
            Actions action = actionsService.searchBySymbol(symbol);
            if (action != null) {
                // Show alert if action is found
                showAlert("Action Found", "Symbol: " + action.getSymbol(), action.toString());
                // Update the table view with the found action
                table_action.setItems(FXCollections.observableArrayList(action));
            } else {
                // Show alert if action is not found
                showAlert("Action Not Found", "Symbol: " + symbol, "Action with this symbol was not found.");
            }
        } catch (SQLException e) {
            showAlert("Error", "Search Failed", "An error occurred while searching for action: " + e.getMessage());
        }
    }


}







