package com.alphadev.artemisjvfx.controllers.Admin;

import com.alphadev.artemisjvfx.models.InscriptionCertif;
import com.alphadev.artemisjvfx.services.ServiceInscriptionCertif;
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

import java.net.URL;
import java.util.ResourceBundle;

public class AdminInscriptionCertifController implements Initializable {
    @FXML
    public ComboBox<String> certificationComboBox;
    @FXML
    public TextField userAddressField;
    @FXML
    public TableView<InscriptionCertif> enrollmentsTable;
    @FXML
    public TableColumn<InscriptionCertif, String> colUserEmail, colCertificationName;



    @FXML
    public Button updateButton,deleteButton;

    public ServiceInscriptionCertif inscriptionService;
    private InscriptionCertif selectedEnrollment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inscriptionService = new ServiceInscriptionCertif();
        setupTableColumns();
        populateComboBox();
        loadEnrollments();
        enrollmentsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            selectedEnrollment = newSelection;
            if (selectedEnrollment != null) {
                userAddressField.setText(selectedEnrollment.getUserName());
                certificationComboBox.setValue(selectedEnrollment.getCertificationName());
            }
        });
    }

    private void setupTableColumns() {
        colUserEmail.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colCertificationName.setCellValueFactory(new PropertyValueFactory<>("certificationName"));
    }

    private void populateComboBox() {
        certificationComboBox.setItems(inscriptionService.getAllCertificationNames());
    }

    private void loadEnrollments() {
        ObservableList<InscriptionCertif> enrollments = inscriptionService.getAllEnrollments();
        enrollmentsTable.setItems(enrollments);
    }
    @FXML
    public void handleAddEnrollment() {
        String userAddress = userAddressField.getText();
        String certName = certificationComboBox.getValue();

        if (userAddress.isEmpty() || certName == null) {
            showAlert("Error", "Missing Information", "Please fill in all fields.", Alert.AlertType.ERROR);
            return;
        }

        String result = inscriptionService.addEnrollmentByAddress(userAddress, certName);
        loadEnrollments(); // Refresh the table
        clearForm(); // Clear form fields
        showAlert(result.contains("successfully") ? "Success" : "Error", result, "", result.contains("successfully") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
    }

    @FXML
    public void handleUpdateEnrollment() {
        if (selectedEnrollment == null) {
            showAlert("Error", "No Enrollment Selected", "Please select an enrollment to update.", Alert.AlertType.ERROR);
            return;
        }
        String newAddress = userAddressField.getText();
        String newCertName = certificationComboBox.getValue();
        String result = inscriptionService.updateEnrollment(selectedEnrollment.getId(), newAddress, newCertName);
        loadEnrollments();
        clearForm();
        showAlert(result.contains("successfully") ? "Success" : "Error", result, "", result.contains("successfully") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
    }

    @FXML
    public void handleDeleteEnrollment() {
        if (selectedEnrollment == null) {
            showAlert("Error", "No Enrollment Selected", "Please select an enrollment to delete.", Alert.AlertType.ERROR);
            return;
        }
        String result = inscriptionService.deleteEnrollment(selectedEnrollment.getId());
        loadEnrollments();
        clearForm();
        showAlert(result.contains("successfully") ? "Success" : "Error", result, "", result.contains("successfully") ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
    }

    public void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    private void clearForm() {
        userAddressField.setText("");  // Clear the text field for the user address
        certificationComboBox.getSelectionModel().clearSelection();  // Clear selection in the combo box
        enrollmentsTable.getSelectionModel().clearSelection();  // Clear any selection in the table
    }


    public void gotoCertification(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            Parent coursPage = FXMLLoader.load(getClass().getResource("/Fxml/Admin/CertifsAdmin.fxml"));

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

