package com.example.demoexam2024;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Create {
    @FXML
    private TextField orgTechTypeField;

    @FXML
    private TextField orgTechModelField;

    @FXML
    private TextField problemDescriptionField;

    @FXML
    private TextField masterIDField;

    @FXML
    private TextField clientIDField;

    private RequestsDAO requestsDAO = new RequestsDAO();
    private Stage stage;

    // Метод для создания новой заявки
    @FXML
    private void createRequest() {
        try {
            // Получаем данные из текстовых полей
            String orgTechType = orgTechTypeField.getText();
            String orgTechModel = orgTechModelField.getText();
            String problemDescription = problemDescriptionField.getText();
            int masterID = Integer.parseInt(masterIDField.getText());
            int clientID = Integer.parseInt(clientIDField.getText());

            // Создаем объект новой заявки
            Requests newRequest = new Requests(
                    orgTechType,
                    orgTechModel,
                    problemDescription,
                    masterID,
                    clientID
            );

            // Сохраняем заявку в базе данных
            requestsDAO.create(newRequest);

            // Показываем уведомление об успешном создании заявки
            showAlert("Успех", "Новая заявка успешно создана!", Alert.AlertType.INFORMATION);

            // Закрываем окно создания заявки
            stage.close();

        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Пожалуйста, убедитесь, что ID мастера и клиента являются числами!", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            showAlert("Ошибка", "Пожалуйста, убедитесь, что верно заполнили поля!", Alert.AlertType.ERROR);
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
}