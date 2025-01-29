package com.example.demoexam2024;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void initialize() {

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

        table.setRowFactory(tv -> {
            TableRow<Requests> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                    Requests rowData = row.getItem(); // Получаем данные из строки таблицы
                    if (rowData != null) { // Проверяем, есть ли данные в строке
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("edit.fxml"));
                        try {
                            Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
                            Edit secondController = fxmlLoader.getController();
                            secondController.setData(rowData, stage); // Передаем данные и Stage в контроллер
                            stage.setTitle("Изменение заявки");
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        showAlert("Внимание!", "Вы выбрали пустую строку в таблице заявок", Alert.AlertType.WARNING);
                        showConfirmation("Добавление новой заявки", "Желаете ли вы создать новую заявку?");
                    }
                }
            });
            return row;
        });
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

    // Метод для показа уведомлений
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Добавляем кнопки "Да" и "Нет"
        ButtonType buttonTypeYes = new ButtonType("Да", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Нет", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Обрабатываем выбор пользователя
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeYes) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
                    Create thirdController = fxmlLoader.getController();
                    stage.setTitle("Создание заявки");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (buttonType == buttonTypeNo) {
                System.out.println("Пользователь выбрал 'Нет'");
            }
        });
    }
}