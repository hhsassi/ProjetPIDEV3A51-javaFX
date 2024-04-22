package com.alphadev.artemisjvfx.controllers;

import com.alphadev.artemisjvfx.gui.gui;
import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.services.ControleDeSaisie;
import com.alphadev.artemisjvfx.services.ServiceUser;
import com.alphadev.artemisjvfx.utils.EmailSender;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController implements Initializable {
    public TextField name_signIn_field;
    public TextField lastName_signIn_field;
    public TextField email_signIn_field;
    public TextField password_signIn_field;
    public TextField cin_signIn_field;
    public TextField adress_signIn_field;
    public TextField phoneNumber_signIn_field;
    public DatePicker dob_singIn_field;
    public Button register_signIn_btn;
    public Text email_error_txt;
    public Text phoneNumber_error_txt;
    public Text name_error_txt;
    public Text lastName_error_txt;
    public Text password_error_txt;
    public Text cin_error_txt;
    public Button picture_btn;

    User user = new User();
    ControleDeSaisie controleDeSaisie = new ControleDeSaisie();
    private final ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        register_signIn_btn.setOnAction(event -> {
            try {
                onSignIn();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        picture_btn.setOnAction(event -> onPicture());
    }

    private void onPicture() {
        // Ajouter le code pour prendre une photo ici
    }

    public void onSignIn() throws SQLException {
        Stage stage = (Stage) register_signIn_btn.getScene().getWindow();

        // Effacer le texte des erreurs
        email_error_txt.setText("");
        phoneNumber_error_txt.setText("");
        name_error_txt.setText("");
        lastName_error_txt.setText("");
        password_error_txt.setText("");
        cin_error_txt.setText("");

        if (controleDeSaisie.isValidEmail(email_signIn_field.getText()) && controleDeSaisie.chekNumero(phoneNumber_signIn_field.getText()) &&
                controleDeSaisie.checkPasswordStrength(password_signIn_field.getText()) && controleDeSaisie.checkText(name_signIn_field.getText())
                && controleDeSaisie.checkText(lastName_signIn_field.getText()) && controleDeSaisie.chekNumero(phoneNumber_signIn_field.getText()) &&
                controleDeSaisie.chekNumero(cin_signIn_field.getText()) && !serviceUser.emailExists(email_signIn_field.getText()) &&
                !serviceUser.cinExists(cin_signIn_field.getText())) {
            user.setPrenom(lastName_signIn_field.getText());
            user.setNom(name_signIn_field.getText());
            user.setCin(cin_signIn_field.getText());
            user.setNum_tel(phoneNumber_signIn_field.getText());
            user.setDob(Date.valueOf(dob_singIn_field.getValue()));
            user.setAdress(name_signIn_field.getText());
            user.setEmail(email_signIn_field.getText());
            user.setPassword(password_signIn_field.getText());

            EmailSender.sendConfirmationEmail(user.getEmail(), "", user.getNom());
            serviceUser.add(user);

            gui.getInstance().getViewFactory().closeStage(stage);
            gui.getInstance().getViewFactory().showLoginWindow();
        } else if (!controleDeSaisie.isValidEmail(email_signIn_field.getText())) {
            email_error_txt.setText("Invalid e-mail ");
        }
        else if (serviceUser.emailExists(email_signIn_field.getText())) {
            email_error_txt.setText("This email already exists");}
        else if (serviceUser.cinExists(cin_signIn_field.getText()))
        {
            cin_error_txt.setText("There is already an account associated to this Identity Card Number");
        } else if (!controleDeSaisie.checkPasswordStrength(password_signIn_field.getText())) {
            password_error_txt.setText("password is not strong enough");
        } else if (!controleDeSaisie.checkText(name_signIn_field.getText())) {
            name_error_txt.setText("Name Must be at least 2 letters");
        } else if (!controleDeSaisie.checkText(lastName_signIn_field.getText())) {
            lastName_error_txt.setText("Last Name Must be at least 2 letters");
        } else if (!controleDeSaisie.chekNumero(cin_signIn_field.getText())) {
            cin_error_txt.setText("cin must be 8 numbers");
        } else if (!controleDeSaisie.chekNumero(phoneNumber_signIn_field.getText())) {
            phoneNumber_error_txt.setText("Phone number must be 8 numbers");
        }
    }
}
