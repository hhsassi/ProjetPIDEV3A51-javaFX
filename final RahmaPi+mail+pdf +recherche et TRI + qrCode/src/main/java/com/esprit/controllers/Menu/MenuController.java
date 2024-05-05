package com.esprit.controllers.Menu;

import com.esprit.controllers.Comptes.AfficherComptesController;
import com.esprit.controllers.Transactions.AfficherTransactionsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class MenuController {

    @FXML
    private StackPane contentPane;


    private void loadContent(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node view = loader.load();

            if (fxml.equals("/Comptes/AfficherComptes.fxml")) {
                AfficherComptesController controller = loader.getController();
                controller.setMainContent(contentPane);
            }
            if (fxml.equals("/Transactions/AfficherTransactions.fxml")) {
                AfficherTransactionsController controller = loader.getController();
                controller.setMainContent(contentPane);
            }
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onComptesClick(ActionEvent event) {
        loadContent("/Comptes/AfficherComptes.fxml");
    }

    public void onTransactionsClick(ActionEvent actionEvent) {
        loadContent("/Transactions/AfficherTransactions.fxml");
    }

}