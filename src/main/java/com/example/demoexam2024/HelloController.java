package com.example.demoexam2024;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.SQLException;

public class HelloController {
    private DBConnection dbConnection = new DBConnection();

    @FXML
    public void initialize(){
        try {
            Connection conn = dbConnection.getConnection();;
            welcomeText.setText("Connect to DB!");
        } catch (SQLException e) {
            welcomeText.setText("Error Date Base connection!");
        }
    }


    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws SQLException {
        RequestsDAO requestsDAO = new RequestsDAO();
        //requestsDAO.create();
    }
}