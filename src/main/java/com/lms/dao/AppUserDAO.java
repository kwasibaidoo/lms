package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.lms.config.DatabaseConfig;
import com.lms.models.AppUser;

public class AppUserDAO {
    public static boolean createUser(AppUser appUser) {
        String sql = "INSERT INTO users (name,email,password,accountType) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, appUser.getName());
            preparedStatement.setString(2, appUser.getEmail());
            preparedStatement.setString(3, appUser.getPassword());
            preparedStatement.setString(4, appUser.getAccountType());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
                
    }
}
