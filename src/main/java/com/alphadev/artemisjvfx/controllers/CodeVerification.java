package com.alphadev.artemisjvfx.controllers;

import com.alphadev.artemisjvfx.gui.gui;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CodeVerification implements Initializable {

    public static int code;


    public TextField code_fld;
    public Button confirmCode_btn;
    public Text ErrorCode_txt;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmCode_btn.setOnAction(event-> onConfirm());

    }

    private void onConfirm() {
        Stage stage = (Stage) confirmCode_btn.getScene().getWindow();
        String codeVerificationString = code_fld.getText();

        if ( !codeVerificationString.matches("[0-9]+")){
            ErrorCode_txt.setText("Code is composed by Numbers Only");
        }else{
        int codeVerification = Integer.parseInt(code_fld.getText());


        if(codeVerification == CodeVerification.code) {
            gui.getInstance().getViewFactory().closeStage(stage);
            gui.getInstance().getViewFactory().showClientWindow();
        }
        else {
            ErrorCode_txt.setText("Wrong code");

        }
    }
    }





}

