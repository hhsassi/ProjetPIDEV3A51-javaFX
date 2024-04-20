package com.alphadev.artemisjvfx.controllers.Admin;

import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.models.Course;
import com.alphadev.artemisjvfx.services.ServiceCourse;
import com.alphadev.artemisjvfx.services.ControleDeSaisie;
import com.alphadev.artemisjvfx.models.Certif;
import com.alphadev.artemisjvfx.services.ServiceCertif;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AdminCoursesController implements Initializable {
    @FXML public Text title_error_txt;
    @FXML public Text description_error_txt;
    @FXML public Text level_error_txt;
    @FXML public Text file_error_txt;
    @FXML public TextField titleFld;
    @FXML public TextField descriptionFld;
    @FXML public TextField levelFld;
    @FXML public TextField fileFld;
    @FXML public ComboBox<String> certificationComboBox;
    @FXML public Button addBtn,updateBtn,deleteBtn;
    @FXML public TableView<Course> tableCourses;
    @FXML public TableColumn<Course, String> colTitle, colDescription, colLevel, colFile, colCertificationName;
    @FXML public TableColumn<Course, Integer> colId;

    private String filePath;
    private HashMap<String, Integer> certificationMap = new HashMap<>();
    private ServiceCertif serviceCertif = new ServiceCertif();
    private ServiceCourse serviceCourse = new ServiceCourse();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        loadCertifications();
        loadCourses();
        clearForm();
        tableCourses.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
                addBtn.setDisable(true);
                updateBtn.setDisable(false);
                deleteBtn.setDisable(false);
            }
        });
    }

    private void clearForm() {titleFld.clear();
        descriptionFld.clear();
        levelFld.clear();
        fileFld.clear();
        certificationComboBox.setValue(null);
        addBtn.setDisable(false);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        filePath = null;
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        colFile.setCellValueFactory(new PropertyValueFactory<>("file"));
        colCertificationName.setCellValueFactory(new PropertyValueFactory<>("certificationName"));
        colFile.setCellFactory(tc -> new TableCell<Course, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    Hyperlink link = new Hyperlink(item);
                    link.setOnAction(e -> openFileWithDefaultApplication(item));
                    setGraphic(link);
                }
            }
        });
    }

    private void loadCertifications() {
        certificationComboBox.getItems().clear();
        certificationComboBox.setPromptText("Select Certification");
        List<Certif> certifications = serviceCertif.getAllCertifs();
        for (Certif cert : certifications) {
            certificationComboBox.getItems().add(cert.getNom_certif());
            certificationMap.put(cert.getNom_certif(), cert.getId());
        }
    }

    private void loadCourses() {
        List<Course> courses = serviceCourse.getAllCoursesWithCertificationName();
        tableCourses.setItems(FXCollections.observableArrayList(courses));
    }

    private void openFileWithDefaultApplication(String filePath) {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", filePath).start();
            } else {
                new ProcessBuilder("open", filePath).start();
            }
        } catch (IOException ex) {
            System.out.println("Error opening file: " + ex.getMessage());
        }
    }

    @FXML
    public void addCourse() {
        if (!validateForm()) {
            showAlert("Error", "Please fill in all fields .", Alert.AlertType.ERROR);
            return;
        }
        if (!validateCourseForm()) {
            return; // Exit if validation fails
        }
        String title = titleFld.getText();
        String description = descriptionFld.getText();
        String level = levelFld.getText();
        String selectedCertName = certificationComboBox.getValue();
        Integer certId = certificationMap.get(selectedCertName);
        if (certId == null) {
            showAlert("Error", "No certification selected or certification not found.", Alert.AlertType.ERROR);
            return;
        }
        serviceCourse.addCourse(new Course(title, description, level, filePath, certId));
        showConfirmation("Course added successfully");
        loadCourses();
        clearForm();
    }

    private boolean validateCourseForm() {

        boolean isValid = true;

        // Clear previous errors
        title_error_txt.setText("");
        description_error_txt.setText("");
        level_error_txt.setText("");
        file_error_txt.setText("");

        if (!ControleDeSaisie.isValidTitle(titleFld.getText())) {
            title_error_txt.setText("Invalid title. Must be at least 2 characters.");
            isValid = false;
        }
        if (!ControleDeSaisie.isValidDescription(descriptionFld.getText())) {
            description_error_txt.setText("Invalid description. Must be at least 10 characters.");
            isValid = false;
        }
        if (!ControleDeSaisie.isValidLevel(levelFld.getText())) {
            level_error_txt.setText("Invalid level. Must be between 1 and 10.");
            isValid = false;
        }
        if (!ControleDeSaisie.isValidFilePath(filePath)) {
            file_error_txt.setText("Invalid file path or format. Accept only .pdf or .txt.");
            isValid = false;
        }

        return isValid;
    }

    private boolean validateForm() {
        return !titleFld.getText().isEmpty() && !descriptionFld.getText().isEmpty() && !levelFld.getText().isEmpty() && certificationComboBox.getValue() != null;

    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Path destination = Paths.get("src/main/resources/files/" + selectedFile.getName());
                Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                filePath = destination.toString();
                fileFld.setText(selectedFile.getAbsolutePath()); // Ensure this line is not throwing the NPE
            } catch (IOException e) {
                System.out.println("Error while uploading file: " + e.getMessage());
            }
        }

    }
    private void populateFields(Course course) {
        if (course != null) {
            titleFld.setText(course.getTitle());
            descriptionFld.setText(course.getDescription());
            levelFld.setText(course.getLevel());
            fileFld.setText(course.getFile());
            certificationComboBox.setValue(getCertificationNameById(course.getCertificationId()));
            filePath = course.getFile();  // Assuming you store the path in filePath
        }
    }
    private String getCertificationNameById(int id) {
        for (String key : certificationMap.keySet()) {
            if (certificationMap.get(key).equals(id)) {
                return key;
            }
        }
        return null;
    }

    public void updateCourse() {
        Course selectedCourse = tableCourses.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            selectedCourse.setTitle(titleFld.getText());
            selectedCourse.setDescription(descriptionFld.getText());
            selectedCourse.setLevel(levelFld.getText());
            selectedCourse.setFile(filePath); // Assuming you want to update the file path as well
            selectedCourse.setCertificationId(certificationMap.get(certificationComboBox.getValue()));

            serviceCourse.updateCourse(selectedCourse);  // You need to implement this method in your service
            showConfirmation("Course updated successfully");
            loadCourses();
            clearForm();// Refresh the table
        } else {
            showAlert("Error", "No course selected for update.", Alert.AlertType.ERROR);
        }
    }

    public void deleteCourse() {

        Course selectedCourse = tableCourses.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            serviceCourse.deleteCourse(selectedCourse.getId());
            loadCourses(); // Reload the courses to update the table view
            showConfirmation("Course deleted successfully");
            clearForm();
        } else {
            showAlert("Error", "No course selected.", Alert.AlertType.ERROR);
        }
    }

    public void gotoCertifs(ActionEvent actionEvent) {
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
