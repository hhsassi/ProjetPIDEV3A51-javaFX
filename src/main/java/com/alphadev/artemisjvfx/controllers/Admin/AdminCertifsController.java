package com.alphadev.artemisjvfx.controllers.Admin;
import com.alphadev.artemisjvfx.models.User;
import com.alphadev.artemisjvfx.services.ControleDeSaisie;
import com.alphadev.artemisjvfx.services.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.alphadev.artemisjvfx.models.Certif;
import com.alphadev.artemisjvfx.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCertifsController implements Initializable {
    public static  User user = null;
    @FXML
    public TableView<Certif> table_view_admin_fld;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}

