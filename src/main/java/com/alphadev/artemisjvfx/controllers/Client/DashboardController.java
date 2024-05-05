package com.alphadev.artemisjvfx.controllers.Client;

import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Button send_r_btn;
    public TextField amnt_fld;
    public TextField rib_r_fld;
    public LineChart income_chart;
    public Label balance_lbl;
    public Label rib_lbl;
    public ListView transaction_listV;
    public ListView stocks_listV;
    public Label user_name_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
