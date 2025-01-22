package com.example.demoexam2024;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RequestsDAO {
    private DBConnection dbConnection = new DBConnection();

//  Создание заявки
    public void create (Requests request) throws SQLException {
        String sql = "INSERT INTO Requests (startDate, orgTechType, orgTechModel, problemDescryption, requestStatus, completionDate, repairParts, masterID, clientID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, request.getStartDate());
            stmt.setString(2, request.getOrgTechType());
            stmt.setString(3, request.getOrgTechModel());
            stmt.setString(4, request.getProblemDescription());
            stmt.setString(5, request.getRequestStatus());
            stmt.setDate(6, request.getCompletionDate() != null ? request.getCompletionDate() : null);
            stmt.setString(7, request.getRepairParts());
            stmt.setInt(8, request.getMasterID());
            stmt.setInt(9, request.getClientID());
            stmt.executeUpdate();
            System.out.println("Заявка успешно создана!");
        }catch (SQLException e) {
            throw new SQLException("!!!При создании заявки произошла ошибка!!!");
        }
    }

//    Получение списка всех заявок
    public List<Requests> getAllRequests() throws SQLException {
        List<Requests> requests = new ArrayList<>();
        String sql = "SELECT * FROM Requests";

        try (Connection conn = dbConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Requests request = new Requests(
                        rs.getInt("requestID"),
                        rs.getDate("startDate"),
                        rs.getString("orgTechType"),
                        rs.getString("orgTechModel"),
                        rs.getString("problemDescription"),
                        rs.getString("requestStatus"),
                        rs.getDate("completionDate"),
                        rs.getString("repairParts"),
                        rs.getInt("masterID"),
                        rs.getInt("clientID")
                );
                requests.add(request);
            }
        } catch (SQLException e) {
            throw new SQLException("!!!При получении списка заявок произошла ошибка:" + e.getMessage() + "!!!");
        }
        return requests;
    }

//    Изменение этапа выполнения, описания проблемы и ответственного за работу
    public void updateRequestDetails(int requestID, String newStage, String newDescription, Integer newMasterID) throws SQLException {
        String sql = "UPDATE Requests SET requestStatus = ?, problemDescryption = ?, masterID = ? WHERE requestID = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStage != null ? newStage : "Новая заявка");
            stmt.setString(2, newDescription != null ? newDescription : "");
            stmt.setInt(3, newMasterID != null ? newMasterID : 0); // Если null, не обновляем мастера
            stmt.setInt(4, requestID);
            stmt.executeUpdate();
            System.out.println("Изменения полей заявок было успешным");
        } catch (SQLException e) {
            throw new SQLException("!!!При изменении полей заявки возникла ошибка:" + e.getMessage() + "!!!");
        }
    }

//     Поиск заявки по номеру или по параметрам
    public List<Requests> searchRequests (String searchTerm) throws SQLException {
        List<Requests> requests = new ArrayList<>();
        String sql = "SELECT * FROM Requests WHERE requestID::TEXT LIKE ? " +
                "OR orgTechType ILIKE ? OR orgTechModel ILIKE ? OR clientID ILIKE ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            stmt.setString(4, searchPattern);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Requests request = new Requests(
                        rs.getInt("requestID"),
                        rs.getDate("startDate"),
                        rs.getString("orgTechType"),
                        rs.getString("orgTechModel"),
                        rs.getString("problemDescription"),
                        rs.getString("requestStatus"),
                        rs.getDate("completionDate"),
                        rs.getString("repairParts"),
                        rs.getInt("masterID"),
                        rs.getInt("clientID")
                );
                requests.add(request);
            }
        } catch (SQLException e) {
            throw new SQLException("!!!При поиске заявки возникла ошибка:" + e.getMessage() + "!!!");
        }
        return requests;
    }

//    Добавление мастера к заявке
    public void assignMasterToRequest(int requestID, int masterID) throws SQLException {
        String sql = "UPDATE Requests SET masterID = ? WHERE requestID = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, masterID);
            stmt.setInt(2, requestID);
            stmt.executeUpdate();
            System.out.println("Мастер успешно был добавлен к другой заявке");
        } catch (SQLException e) {
            throw new SQLException("!!!При добавлении мастера к другой заявке возникла ошибка:" + e.getMessage() + "!!!");
        }
    }


}
