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
            System.out.println("SUCCESSFULLY add book!");
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
                        rs.getString("orgTechType"),
                        rs.getString("orgTechModel"),
                        rs.getString("problemDescription"),
                        rs.getInt("masterID"),
                        rs.getInt("clientID")
                );
                request.setRequestStatus(rs.getString("requestStatus"));
                request.setCompletionDate(rs.getDate("completionDate"));
                request.setRepairParts(rs.getString("repairParts"));
                requests.add(request);
            }
        } catch (SQLException e) {
            throw new SQLException("!!!При получении списка заявок произошла ошибка:" + e.getMessage() + "!!!");
        }
        return requests;
    }



}
