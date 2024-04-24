package com.alphadev.artemisjvfx.controllers.Admin;

import com.alphadev.artemisjvfx.models.InscriptionCertif;
import com.alphadev.artemisjvfx.services.ServiceInscriptionCertif;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.net.URL;
import java.util.Properties;
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
    public Button updateButton, deleteButton;

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
                userAddressField.setText(selectedEnrollment.getUser().getEmail()); // Assuming User has getEmail()
                certificationComboBox.setValue(selectedEnrollment.getCertificationName());
            }
        });
    }

    private void setupTableColumns() {
        colUserEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getEmail()));
        colCertificationName.setCellValueFactory(cellData -> cellData.getValue().certificationNameProperty());
    }

    private void populateComboBox() {
        certificationComboBox.setItems(inscriptionService.getAllCertificationNames());
    }

    private void loadEnrollments() {
        ObservableList<InscriptionCertif> enrollments = inscriptionService.getAllEnrollments();
        enrollmentsTable.setItems(enrollments);
    }

    public void sendSms(String to, String message) {
        Message sms = Message.creator(
                new PhoneNumber(to),  // To number
                new PhoneNumber(FROM_NUMBER),  // From Twilio number
                message
        ).create();
        System.out.println("Sent message SID: " + sms.getSid());
    }
    public void handleAddEnrollment() {
        String userAddress = userAddressField.getText();
        String certName = certificationComboBox.getValue();

        if (userAddress.isEmpty() || certName == null) {
            showAlert("Error", "Missing Information", "Please fill in all fields.", Alert.AlertType.ERROR);
            return;
        }

        String result = inscriptionService.addEnrollmentByAddress(userAddress, certName);
        if (result.contains("successfully")) {
            loadEnrollments(); // Refresh the table
            clearForm(); // Clear form fields

            // Load the HTML template
            String htmlTemplate = loadHtmlTemplate();

            // Replace the placeholder with the certification name
            String htmlBody = htmlTemplate.replace("{{certificationName}}", certName);

            // Send the email with HTML content
            sendEmail(userAddress, "Inscription Confirmation", htmlBody);


            String phoneNumber="+216 29868128";
            String message = "Hello, you've been subscribed to " + certName + "!";

            sendSms(phoneNumber, message);

            showAlert("Success", "Enrollment Added", result, Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", result, "", Alert.AlertType.ERROR);
        }
    }

    private String loadHtmlTemplate() {
        // Load the HTML template from a file or resource
        // For simplicity, you can hardcode the HTML template here
        // Read the HTML template content from a file or resource and return it
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Certification Details</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #fff;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            color: #333;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            color: #666;\n" +
                "            line-height: 1.6;\n" +
                "        }\n" +
                "\n" +
                "        strong {\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "\n" +
                "        .signature {\n" +
                "            text-align: center;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <h1>Congratulations on Your Inscription!</h1>\n" +
                "    <p>Dear User,</p>\n" +
                "    <p>You have been successfully subscribed in the following certification:</p>\n" +
                "    <p><strong>Certification Name:</strong> {{certificationName}}</p>\n" +
                "    <p>Thank you for choosing our platform.</p>\n" +
                "    <p class=\"signature\">Best regards,<br>The Admin Team</p>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>\n";
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
    public void sendEmail(String toEmail, String subject, String htmlBody) {
        final String username = "hhsassi6@gmail.com"; // your email
        final String password = "nxqvkkpqvunwmgsd"; // your password

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            javax.mail.internet.MimeMessage message = new javax.mail.internet.MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            // Create a MimeBodyPart to hold the HTML content
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html");

            // Create a Multipart object to hold the HTML content and attachments (if any)
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            // Set the content of the message to the Multipart object
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
            showAlert("Email Error", "Failed to Send Email", "Could not send the email to: " + toEmail, Alert.AlertType.ERROR);
        }
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



