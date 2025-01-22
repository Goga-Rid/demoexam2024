package com.example.demoexam2024;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentsDAO {
    private DBConnection dbConnection = new DBConnection();

//  Добавление нового комментария
    public void addComment(Comments comment) throws SQLException {
        String sql = "INSERT INTO Comments (requestID, clientID, masterID, message) VALUES (?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, comment.getRequestID());
            stmt.setInt(2, comment.getClientID());
            stmt.setInt(3, comment.getMasterID());
            stmt.setString(4, comment.getMessage());
            ResultSet rs = stmt.executeQuery();
            System.out.println("Комментарий успешно создан!");
        } catch (SQLException e) {
            throw new SQLException("!!!При создании нового комментария произошла ошибка:" +  e.getMessage() + "!!!");
        }
    }

//  Получение всех комментариев по ID заявки
    public List<Comments> getCommentsByRequestId(int requestID) throws SQLException {
        List<Comments> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comments WHERE requestID = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, requestID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Comments comment = new Comments(
                        rs.getInt("requestID"),
                        rs.getInt("clientID"),
                        rs.getInt("masterID"),
                        rs.getString("message"),
                        rs.getInt("commentID")
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            throw new SQLException("!!!При получении списка комментариев по id произошла ошибка:" +  e.getMessage() + "!!!");
        }
        return comments;
    }

    // Удаление комментария по ID
    public void deleteComment(int commentID) throws SQLException {
        String sql = "DELETE FROM Comments WHERE commentID = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, commentID);
            stmt.executeUpdate();
            System.out.println("Комментарий успешно удален!");
        } catch (SQLException e) {
            throw new SQLException("!!!При удалении комментария по id произошла ошибка:" +  e.getMessage() + "!!!");
        }
    }
}
