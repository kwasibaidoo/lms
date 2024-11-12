package com.lms.utils;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

import com.lms.config.DatabaseConfig;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();) {
            String createUserTable = "CREATE TABLE IF NOT EXISTS users (" +
                                        "id CHAR(36) PRIMARY KEY DEFAULT (UUID())," +
                                        "name VARCHAR(100)," +
                                        "email VARCHAR(256) NOT NULL UNIQUE," +
                                        "password VARCHAR(256)," +
                                        "accountType VARCHAR(256)," +
                                        "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                        "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";


            statement.executeUpdate(createUserTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
