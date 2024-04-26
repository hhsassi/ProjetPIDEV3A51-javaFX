package com.alphadev.artemisjvfx.controllers;

import com.alphadev.artemisjvfx.controllers.Admin.*;
import com.alphadev.artemisjvfx.controllers.Client.*;
import com.alphadev.artemisjvfx.gui.gui;
import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.services.ServiceUser;
import com.alphadev.artemisjvfx.utils.SmsSending;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.alphadev.artemisjvfx.utils.EmailSender;
import com.alphadev.artemisjvfx.utils.CodeGenerator;




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

    //SMS
    public static final String ACCOUNT_SID = "ACae5665c0b3278c7d2a21487542122dc3";
    public static final String AUTH_TOKEN = "9a6a3fa610b67f13d64af2f8a1935c55";
    private static final String FROM_NUMBER = "+18583455486";







    /* - */
    // Autre
    private User user = new User();

    // Ajout du su user connecte aux autre entites
    public ProfileController profileController = new ProfileController();

    private ServiceUser serviceUser = new ServiceUser();

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }
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
            // Generation du Code de verification



        if(user != null )
            {
                CodeVerification.code = Integer.parseInt(CodeGenerator.generateCode(user.getNom()));
                System.out.println(" Le code envoye par email est : " + CodeVerification.code);
                if(user.getRoles().equals("[]")){
                    ProfileController.user = user;
                    TransactionController.user = user;
                    PortefeuilleController.user = user;
                    CreditsController.user = user;
                    CertifsController.user = user;
                    ProjetsController.user = user;
                    //Envoie du SMS conetenant le code de verification

                    String VerifString = "Votre Code de verification est : "+CodeVerification.code;
                    this.sendSms(VerifString);
                    //Envoie du mail de Verification
                    EmailSender.sendCodeVerif(user.getEmail(),CodeVerification.code,user.getNom() );


                gui.getInstance().getViewFactory().closeStage(stage);
                gui.getInstance().getViewFactory().showVerificationCodeWindow();
                }
                else if (user.getRoles().equals("[\"ROLE_ADMIN\"]")) {

                    ProfileController.user = user;
                    AdminPortefeuilleController.user = user;
                    AdminTransactionsController.user = user;
                    AdminCreditsController.user = user;
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

    public  void sendSms( String message) {
        Message sms = Message.creator(
                new PhoneNumber("+216 27372431"),  // To number
                new PhoneNumber(FROM_NUMBER),  // From Twilio number
                message
        ).create();
        System.out.println("Sent message SID: " + sms.getSid());
    }

    }





