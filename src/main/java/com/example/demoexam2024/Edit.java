package com.example.demoexam2024;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class Edit {

    @FXML
    private Label requestStatusLabel;

    @FXML
    private ComboBox<String> requestStatusComboBox;

    @FXML
    private Button saveButton;

    @FXML
    private TextField problemDescriptionField;


    @FXML
    private TextField masterIDField;


    private Requests selectedRequest; // Выбранная заявка для редактирования
    private RequestsDAO requestsDAO = new RequestsDAO();

    // Список возможных статусов заявки
    private ObservableList<String> statusOptions = FXCollections.observableArrayList(
            "Новая заявка",
            "В процессе ремонта",
            "Готова к выдаче",
            "Ожидание запчастей",
            "Завершена"
    );

    @FXML
    private void initialize() {
        requestStatusComboBox.setItems(statusOptions); // Устанавливаем список статусов в ComboBox
    }

    // Метод для установки данных из выбранной строки таблицы
    public void setData(Requests request) {
        this.selectedRequest = request;
        if (request != null) {
            requestStatusComboBox.setValue(request.getRequestStatus());
            problemDescriptionField.setText(request.getProblemDescription());
            masterIDField.setText(String.valueOf(request.getMasterID()));
        } else {
            requestStatusLabel.setText("Заявка не выбрана");
            /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ТУТ НАДО ДОДЕЛАТЬ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
        }
    }

    // Метод для сохранения изменений
    @FXML
    private void saveChanges() {
        if (selectedRequest == null) {
            showAlert("Ошибка", "Заявка не выбрана!", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Получаем новый статус из комбобокса
            String newStatus = requestStatusComboBox.getValue();

            // Обновляем статус заявки в базе данных
            requestsDAO.updateRequestDetails(
                    selectedRequest.getRequestID(),
                    newStatus,
                    selectedRequest.getProblemDescription(),
                    selectedRequest.getMasterID()
            );

            // Показываем уведомление об успешном обновлении
            showAlert("Успех", "Статус заявки успешно обновлен!", Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            // Обработка ошибки базы данных
            showAlert("Ошибка", "Не удалось обновить статус заявки: " + e.getMessage(), Alert.AlertType.ERROR);
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
