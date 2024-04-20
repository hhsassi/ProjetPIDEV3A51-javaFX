package com.alphadev.artemisjvfx.controllers.Client;

import com.alphadev.artemisjvfx.services.ServiceCertif;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.alphadev.artemisjvfx.models.Certif;
import com.alphadev.artemisjvfx.models.User;


import java.net.URL;
import java.util.ResourceBundle;

public class CertifsController  implements Initializable{
    @FXML
    public TableView<Certif> tableCertifications;
    @FXML
    public TableColumn<Certif, Integer> colId;
    @FXML
    public TableColumn<Certif, String> colName, colDescription;
    @FXML
    public TableColumn<Certif, Integer> colDuration, colLevel;
    @FXML
    public TableColumn<Certif, Image> colBadgeImage;

    public static User user = null;
    ServiceCertif serviceCertif = new ServiceCertif();

    private void setupColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nom_certif"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description_certif"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duree_certif"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("niveau_certif"));
        colBadgeImage.setCellValueFactory(new PropertyValueFactory<>("badgeImage"));
        ServiceCertif serviceCertif = new ServiceCertif();

        colBadgeImage.setCellFactory(column -> new TableCell<Certif, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    imageView.setFitHeight(50);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
            }
        });


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        loadCertifications();
    }

    private void loadCertifications() {
        ObservableList<Certif> certifList = FXCollections.observableArrayList(serviceCertif.getAllCertifs());
        tableCertifications.setItems(certifList);
    }
}
