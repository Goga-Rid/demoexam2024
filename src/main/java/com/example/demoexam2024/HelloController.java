package com.example.demoexam2024;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class HelloController {

    @FXML
    private TableView<Requests> table;
    @FXML
    private TableColumn<Requests, Integer> requestID;
    @FXML
    private TableColumn<Requests, Date> startDate;
    @FXML
    private TableColumn<Requests, String> orgTechType;
    @FXML
    private TableColumn<Requests, String> orgTechModel;
    @FXML
    private TableColumn<Requests, String> problemDescription;
    @FXML
    private TableColumn<Requests, String> requestStatus;
    @FXML
    private TableColumn<Requests, Date> completionDate;
    @FXML
    private TableColumn<Requests, String> repairParts;
    @FXML
    private TableColumn<Requests, Integer> masterID;
    @FXML
    private TableColumn<Requests, Integer> clientID;


    private DBConnection dbConnection = new DBConnection();

    @FXML
    public void initialize(){
        try {
//          Получение подлючения
            dbConnection.getConnection();
//          Инициализация колонок таблицы со значениями модели "Заявки"
            initTableViewColumns();
//          Загрузка данных из БД в таблицу
            loadDataIntoTable();
            welcomeText.setText("Connect to DB!");
        } catch (SQLException e) {
            welcomeText.setText("Error Date Base connection!");
        }
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void initTableViewColumns() {
        requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        orgTechType.setCellValueFactory(new PropertyValueFactory<>("orgTechType"));
        orgTechModel.setCellValueFactory(new PropertyValueFactory<>("orgTechModel"));
        problemDescription.setCellValueFactory(new PropertyValueFactory<>("problemDescription"));
        requestStatus.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
        completionDate.setCellValueFactory(new PropertyValueFactory<>("completionDate"));
        repairParts.setCellValueFactory(new PropertyValueFactory<>("repairParts"));
        masterID.setCellValueFactory(new PropertyValueFactory<>("masterID"));
        clientID.setCellValueFactory(new PropertyValueFactory<>("clientID"));
    }

    private void loadDataIntoTable() {
        try {
            RequestsDAO requestsDAO = new RequestsDAO();

            // Получаем список всех заявок из базы данных
            List<Requests> requestsList = requestsDAO.getAllRequests();

            // Преобразуем список в ObservableList, который может быть использован в TableView
            ObservableList<Requests> observableRequestsList = FXCollections.observableArrayList(requestsList);

            // Загружаем данные в TableView
            table.setItems(observableRequestsList);

        } catch (Exception e) {
            e.getMessage();
        }
    }
}