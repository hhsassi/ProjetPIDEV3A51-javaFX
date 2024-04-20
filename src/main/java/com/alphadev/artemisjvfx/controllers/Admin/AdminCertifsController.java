package com.alphadev.artemisjvfx.controllers.Admin;

import com.alphadev.artemisjvfx.models.Certif;
import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.services.ServiceCertif;

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
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.alphadev.artemisjvfx.services.ControleDeSaisie;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminCertifsController implements Initializable  {
    public static User user = null;
   @FXML public Text name_error_txt ;
    @FXML public Text description_error_txt ;
    @FXML public Text lvl_error_txt ;
    @FXML public Text duree_error_txt ;
    @FXML public Text image_error_txt ;
    @FXML public Button coursBtn;

    @FXML public Button updateBtn;
    @FXML public TextField nameFld;
    @FXML public TextField descriptionFld;
    @FXML public TextField durationFld;
    @FXML public TextField levelFld;
    @FXML public TableView<Certif> tableCertifications;
    @FXML public TableColumn<Certif, Integer> colId;
    @FXML public TableColumn<Certif, String> colName, colDescription;
    @FXML public TableColumn<Certif, Integer> colDuration, colLevel;
    @FXML public TableColumn<Certif, Image> colBadgeImage;
    @FXML public ImageView badgePreview;
    @FXML public Button addBtn;

    public String badgePath;
    public final ServiceCertif serviceCertif = new ServiceCertif();
    ControleDeSaisie controleDeSaisie = new ControleDeSaisie();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        loadCertifications();
        setupSelectionModel();
        updateBtn.disableProperty().bind(tableCertifications.getSelectionModel().selectedItemProperty().isNull());
        tableCertifications.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection == null) {
                addBtn.setDisable(false);  // Enable the add button when no row is selected
            } else {
                clearErrorMessages();
                addBtn.setDisable(true);   // Disable the add button when a row is selected
            }
        });

    }

    private void setupColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nom_certif"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description_certif"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duree_certif"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("niveau_certif"));

        colBadgeImage.setCellValueFactory(new PropertyValueFactory<>("badgeImage"));
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

    private void setupSelectionModel() {
        tableCertifications.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Certif selectedCertif = tableCertifications.getSelectionModel().getSelectedItem();
                updateForm(selectedCertif);
            }
        });
    }

    private void updateForm(Certif certif) {
        nameFld.setText(certif.getNom_certif());
        descriptionFld.setText(certif.getDescription_certif());
        durationFld.setText(String.valueOf(certif.getDuree_certif()));
        levelFld.setText(String.valueOf(certif.getNiveau_certif()));
        badgePreview.setImage(certif.getBadgeImage());
        badgePath = certif.getBadge_certif();  // Store path for possible re-upload
    }

    @FXML
    public void uploadBadge() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Badge Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Files.copy(selectedFile.toPath(), Paths.get("src/main/resources/badges/" + selectedFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                badgePath = "badges/" + selectedFile.getName();
                Image image = new Image(new File("src/main/resources/" + badgePath).toURI().toString());
                badgePreview.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void addCertif() {
        boolean inputValid = true;
        clearErrorMessages(); // Clear previous error messages

        if (!controleDeSaisie.checkName(nameFld.getText())) {
            name_error_txt.setText("Invalid name format.");
            inputValid = false;
        }
        if (!controleDeSaisie.checkDescription(descriptionFld.getText())) {
            description_error_txt.setText("Description cannot be empty.");
            inputValid = false;
        }
        if (!controleDeSaisie.checkDuration(durationFld.getText())) {
            duree_error_txt.setText("Invalid duration. Enter a positive number.");
            inputValid = false;
        }
        if (!controleDeSaisie.checkLevel(levelFld.getText())) {
            lvl_error_txt.setText("Level must be between 1 and 10.");
            inputValid = false;
        }
        if (!controleDeSaisie.checkImage(badgePath)) {
            image_error_txt.setText("Invalid image file.");
            inputValid = false;
        }

        if (inputValid) {
            Certif newCertif = new Certif(nameFld.getText(), badgePath, descriptionFld.getText(), Integer.parseInt(durationFld.getText()), Integer.parseInt(levelFld.getText()));
            serviceCertif.addCertif(newCertif);
            clearForm();
            loadCertifications();
            clearErrorMessages();
            showConfirmation("Certification added successfully!");
        }
    }

    private void clearErrorMessages() {
        name_error_txt.setText("");
        description_error_txt.setText("");
        duree_error_txt.setText("");
        lvl_error_txt.setText("");
        image_error_txt.setText("");
    }


    @FXML
    public void updateCertif() {
        Certif selectedCertif = tableCertifications.getSelectionModel().getSelectedItem();
        if (selectedCertif == null) {
            showAlert("No certification selected to update.");
            return;
        }
        boolean inputValid = true;
        clearErrorMessages(); // Clear previous error messages

        if (!controleDeSaisie.checkName(nameFld.getText())) {
            name_error_txt.setText("Invalid name format.");
            inputValid = false;
        }
        if (!controleDeSaisie.checkDescription(descriptionFld.getText())) {
            description_error_txt.setText("Description cannot be empty.");
            inputValid = false;
        }
        if (!controleDeSaisie.checkDuration(durationFld.getText())) {
            duree_error_txt.setText("Invalid duration. Enter a positive number.");
            inputValid = false;
        }
        if (!controleDeSaisie.checkLevel(levelFld.getText())) {
            lvl_error_txt.setText("Level must be between 1 and 10.");
            inputValid = false;
        }
        if (!controleDeSaisie.checkImage(badgePath)) {
            image_error_txt.setText("Invalid image file.");
            inputValid = false;
        }

        if (inputValid) {

        Certif updatedCertif = new Certif(selectedCertif.getId(), nameFld.getText(), badgePath, descriptionFld.getText(), Integer.parseInt(durationFld.getText()), Integer.parseInt(levelFld.getText()));
        serviceCertif.updateCertif(updatedCertif);

        clearForm();
        loadCertifications();
        clearErrorMessages();
        showConfirmation("Certification updated successfully!");
        }
    }

    @FXML
    public void deleteCertif() {
        Certif selectedCertif = tableCertifications.getSelectionModel().getSelectedItem();
        if (selectedCertif == null) {
            showAlert("No certification selected to delete.");
            return;
        }

        // Confirmation dialog
        if (showConfirmationDialog("Are you sure you want to delete this certification?")) {
            serviceCertif.deleteCertif(selectedCertif.getId());
            loadCertifications(); // Reload the list to reflect the deletion
            showConfirmation("Certification deleted successfully!");
            clearForm();
            clearErrorMessages();// Clear the form fields
        }
    }
    private boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Deletion Confirmation");
        alert.setContentText(message);

        // Customize the buttons on the confirmation dialog
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        // Show the alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    private void clearForm() {
        nameFld.clear();
        descriptionFld.clear();
        durationFld.clear();
        levelFld.clear();
        badgePreview.setImage(null);
        tableCertifications.getSelectionModel().clearSelection();
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadCertifications() {
        List<Certif> certifs = serviceCertif.getAllCertifs();
        ObservableList<Certif> data = FXCollections.observableArrayList(certifs);
        tableCertifications.setItems(data);
    }


        public void goToCours(ActionEvent actionEvent) {
            try {
                // Load the FXML file
                Parent coursPage = FXMLLoader.load(getClass().getResource("/Fxml/Admin/CoursAdmin.fxml"));

                // Get the current stage (window)
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

                // Set the scene to your new layout
                stage.setScene(new Scene(coursPage));

                // Show the new stage
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
