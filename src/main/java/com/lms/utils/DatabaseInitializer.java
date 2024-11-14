package com.lms.utils;

import java.sql.Statement;
import java.util.LinkedList;
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

            String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                                        "id CHAR(36) PRIMARY KEY DEFAULT (UUID())," +
                                        "name VARCHAR(100) NOT NULL UNIQUE," +
                                        "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                        "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";

            String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                                        "id CHAR(36) PRIMARY KEY DEFAULT (UUID())," +
                                        "name VARCHAR(100)," +
                                        "availableCopies INTEGER DEFAULT 0" +
                                        "totalCopies INTEGER DEFAULT 0" +
                                        "location VARCHAR(100)" +
                                        "category_id CHAR(36)" +
                                        "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                        "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP)" +
                                        "FOREIGN KEY (category_id) REFERENCES categories(id)";


            statement.executeUpdate(createUserTable);
            statement.executeUpdate(createCategoriesTable);
            // statement.executeUpdate(createBooksTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
