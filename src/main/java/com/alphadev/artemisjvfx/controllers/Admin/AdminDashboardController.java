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

public class AdminDashboardController implements Initializable {

    @FXML
    public TextField name_admin_fld;
    @FXML
    public TextField lastName_admin_fld;
    @FXML
    public TextField email_admin_fldd;
    @FXML
    public TextField password_admin_fld;
    @FXML
    public TextField cin_admin_fld;
    @FXML
    public TextField phoneNumber_admin_fld;
    @FXML
    public TextField adress_admin_fld;
    @FXML
    public DatePicker dob_admin_fld;
    @FXML
    public TextField tableView_admin_fld;
    @FXML
    public Button delete_admin_btn;
    @FXML
    public Button search_admin_btn;
    @FXML
    public TableView<User> user_admin_tableView;

    private final ServiceUser userService = new ServiceUser();
    public Button register_admin_btn;
    public Button refresh_admin_btn;
    public Text email_error_txt;
    public Text name_error_txt;
    public Text lastName_error_txt;
    public Text password_error_txt;
    public Text phoneNumber_error_txt;
    public Text Adress_error_txt;
    public Text cin_error_txt;

    ControleDeSaisie controleDeSaisie = new ControleDeSaisie();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        loadTableData();
        delete_admin_btn.setOnAction(event-> {
            try {
                handleDeleteAction();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        search_admin_btn.setOnAction(event->handleSearchAction());
        register_admin_btn.setOnAction(event-> {
            try {
                onSignIn();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        refresh_admin_btn.setOnAction(event->loadTableData());





    }

    private void initTable() {
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> rolesColumn = new TableColumn<>("Roles");
        rolesColumn.setCellValueFactory(new PropertyValueFactory<>("roles"));

        TableColumn<User, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<User, String> prenomColumn = new TableColumn<>("Prenom");
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<User, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<User, String> dobColumn = new TableColumn<>("Date of Birth");
        dobColumn.setCellValueFactory(new PropertyValueFactory<>("dob"));

        TableColumn<User, String> numTelColumn = new TableColumn<>("Phone Number");
        numTelColumn.setCellValueFactory(new PropertyValueFactory<>("num_tel"));

        TableColumn<User, String> cinColumn = new TableColumn<>("CIN");
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));

        TableColumn<User, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("adress"));

        TableColumn<User, String> verifiedColumn = new TableColumn<>("Verification Status");
        verifiedColumn.setCellValueFactory(new PropertyValueFactory<>("is_verified"));

        user_admin_tableView.getColumns().addAll(emailColumn, rolesColumn, nomColumn, prenomColumn,
                passwordColumn, dobColumn, numTelColumn, cinColumn, addressColumn, verifiedColumn);
    }

    private void loadTableData() {
        try {
            ResultSet resultSet = userService.selectAll();
            ObservableList<User> userList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setRoles(resultSet.getString("roles"));
                user.setNom(resultSet.getString("nom"));
                user.setPrenom(resultSet.getString("prenom"));
                user.setPassword(resultSet.getString("password"));
                user.setDob(Date.valueOf(resultSet.getDate("dob").toString()));
                user.setNum_tel(resultSet.getString("num_tel"));
                user.setCin(resultSet.getString("cin"));
                user.setAdress(resultSet.getString("adress"));
                user.setIs_verified(resultSet.getInt("is_verified"));
                userList.add(user);
            }

            user_admin_tableView.setItems(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteAction() throws SQLException {
        User selectedUser = user_admin_tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userService.delete(selectedUser);
            loadTableData();
        } else {
            showAlert("No user selected", Alert.AlertType.ERROR);
        };
    }

    @FXML
    private void handleSearchAction() {
        String email = tableView_admin_fld.getText();
        try {
            User user = userService.searchByEmail(email);
            if (user != null) {
                ObservableList<User> userList = FXCollections.observableArrayList();
                userList.add(user);
                user_admin_tableView.setItems(userList);
            } else {
                showAlert("User not found", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onSignIn() throws SQLException {


        if (controleDeSaisie.isValidEmail(email_admin_fldd.getText()) && controleDeSaisie.chekNumero(phoneNumber_admin_fld.getText()) &&
                controleDeSaisie.checkPasswordStrength(password_admin_fld.getText()) && controleDeSaisie.checkText(name_admin_fld.getText())
                && controleDeSaisie.checkText(lastName_admin_fld.getText()) && controleDeSaisie.chekNumero(phoneNumber_admin_fld.getText()) &&
                controleDeSaisie.chekNumero(cin_admin_fld.getText())) {
            User user = new User();
            user.setPrenom(lastName_admin_fld.getText());
            user.setNom(name_admin_fld.getText());
            user.setCin(cin_admin_fld.getText());
            user.setNum_tel(phoneNumber_admin_fld.getText());
            user.setDob(Date.valueOf(dob_admin_fld.getValue()));
            user.setAdress(adress_admin_fld.getText());
            user.setEmail(email_admin_fldd.getText());
            user.setPassword(password_admin_fld.getText());


            // Ajout dans la base de donnees
            ServiceUser serviceUser = new ServiceUser();
            serviceUser.add(user);
            // Mise a jour du table View apres  ajout
            loadTableData();


        } else if (!controleDeSaisie.isValidEmail(email_admin_fldd.getText())) {
            email_error_txt.setText("Invalid e-mail ");


        }
        else if(!controleDeSaisie.checkPasswordStrength(password_admin_fld.getText()))
        {
            password_error_txt.setText("password is not strong enough");
        }
        else if(!controleDeSaisie.checkText(name_admin_fld.getText()))
        {
            name_error_txt.setText("Name Must be at least 2 letters");
        }
        else if(!controleDeSaisie.checkText(lastName_admin_fld.getText()))
        {
            lastName_error_txt.setText("Last Name Must be at least 2 letters");
        } else if (!controleDeSaisie.chekNumero(cin_admin_fld.getText())) {
            cin_error_txt.setText("cin must be 8 numbers");
        }
        else if(!controleDeSaisie.chekNumero(phoneNumber_admin_fld.getText()))
        {
            phoneNumber_error_txt.setText("Phone number must be 8 numbers");
        }

    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
