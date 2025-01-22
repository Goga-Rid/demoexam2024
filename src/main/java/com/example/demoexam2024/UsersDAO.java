package com.example.demoexam2024;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {
    private DBConnection dbConnection = new DBConnection();

//  Добавление нового пользователя
    public void addUser(Users user) throws SQLException {
        String sql = "INSERT INTO Users (fio, phone, login, password, type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFio());
            stmt.setString(2, user.getPhone());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getType());

            ResultSet rs = stmt.executeQuery();
            System.out.println("Пользователь успешно создан!");
        } catch (SQLException e) {
            throw new SQLException("!!!При создании нового пользователя произошла ошибка:" +  e.getMessage() + "!!!");
        }
    }

//  Получение пользователя по id
    public Users getUserById(int userID) throws SQLException {
        String sql = "SELECT * FROM Users WHERE userID = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Users(
                        rs.getInt("userID"),
                        rs.getString("fio"),
                        rs.getString("phone"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            throw new SQLException("!!!При получении пользователя по id произошла ошибка:" +  e.getMessage() + "!!!");
        }
        return null; // Если пользователь не был найден
    }

//  Получения списка всех пользователей
    public List<Users> getAllUsers() throws SQLException {
        List<Users> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        try (Connection conn = dbConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Users user = new Users(
                        rs.getInt("userID"),
                        rs.getString("fio"),
                        rs.getString("phone"),
                        rs.getString("type")
                );
                users.add(user);
            }
        }catch(SQLException e){
            throw new SQLException("!!!При списка всех пользователей произошла ошибка:" + e.getMessage() + "!!!");
        }
        return users;
    }

//  Обновление полей пользователя
    public void updateUser(Users user) throws SQLException {
        String sql = "UPDATE Users SET fio = ?, phone = ?, login = ?, password = ?, type = ? WHERE userID = ?";

        try (Connection conn = dbConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getFio());
            stmt.setString(2, user.getPhone());
            stmt.setString(5, user.getType());
            stmt.executeUpdate();
            System.out.println("Данные пользователя были успешно изменены!");
        } catch (SQLException e) {
            throw new SQLException("!!!При обновлении полей пользователя произошла ошибка:" + e.getMessage() + "!!!");
        }
    }
}
