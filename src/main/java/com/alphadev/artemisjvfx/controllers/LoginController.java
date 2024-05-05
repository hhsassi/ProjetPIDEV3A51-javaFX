package com.alphadev.artemisjvfx.controllers;

import com.alphadev.artemisjvfx.controllers.Admin.*;
import com.alphadev.artemisjvfx.controllers.Client.*;
import com.alphadev.artemisjvfx.gui.gui;
import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.services.ServiceUser;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    // Ui
    public Label email_fp_lbl;
    public Label email_login_lbl;
    public TextField email_login_field;
    public Text error_login_lbl;
    public Label password_login_lbl;
    public TextField password_login_field;
    public Button  login_btn;
    public Button signIn_btn;
    /* - */
    // Autre
    private User user = new User();
    // Ajout du su user connecte aux autre entites
    public ProfileController profileController = new ProfileController();

    private ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    login_btn.setOnAction(event-> onLogin());
    signIn_btn.setOnAction(event-> onSignIn());
    }

    private void onLogin(){
        Stage stage = (Stage) login_btn.getScene().getWindow();

            // Obtention du mail et du password
            String email = email_login_field.getText();
            String password = password_login_field.getText();
            // Verfication de l'existance de l'utilidateur
            user = serviceUser.Login(email,password);

        if(user != null )
            {
                if(user.getRoles().equals("[]")){
                    ProfileController.user = user;
                    TransactionController.user = user;
                    PortefeuilleController.user = user;
                    CreditsController.user = user;
                    CertifsController.user = user;
                    ProjetsController.user = user;


                gui.getInstance().getViewFactory().closeStage(stage);
                gui.getInstance().getViewFactory().getCreditsView();
                }
                else if (user.getRoles().equals("[\"ROLE_ADMIN\"]")) {

                    ProfileController.user = user;
                    AdminPortefeuilleController.user = user;
                    AdminTransactionsController.user = user;
                   // AdminCreditsController.user = user;
                    AdminCertifsController.user = user;
                    AdminProjetController.user = user;

                    gui.getInstance().getViewFactory().closeStage(stage);
                    gui.getInstance().getViewFactory().showAdminWindow();


                }

            }
            else {
                error_login_lbl.setText("No email password combination correspond to an account ");
            }

        }

    private void onSignIn(){
        Stage stage  = (Stage) signIn_btn.getScene().getWindow();

        gui.getInstance().getViewFactory().closeStage(stage);
        gui.getInstance().getViewFactory().showSignInWindow();

    }

    }



