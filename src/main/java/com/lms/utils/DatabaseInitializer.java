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
                                        "deletedAt TIMESTAMP DEFAULT NULL," + 
                                        "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                        "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)";

            String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories (" +
                                        "id CHAR(36) PRIMARY KEY DEFAULT (UUID())," +
                                        "name VARCHAR(100) NOT NULL UNIQUE," +
                                        "deletedAt TIMESTAMP DEFAULT NULL," + 
                                        "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                        "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)";

            String createAuthorTable = "CREATE TABLE IF NOT EXISTS authors (" +
                                        "id char(36) PRIMARY KEY DEFAULT (UUID())," +
                                        "name VARCHAR(256) NOT NULL UNIQUE," +
                                        "deletedAt TIMESTAMP DEFAULT NULL," + 
                                        "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                        "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)";

            String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                                        "id CHAR(36) PRIMARY KEY DEFAULT (UUID())," +
                                        "name VARCHAR(100) NOT NULL UNIQUE," +
                                        "availableCopies INTEGER DEFAULT 0," +
                                        "totalCopies INTEGER DEFAULT 0," +
                                        "location VARCHAR(100)," +
                                        "category_id CHAR(36)," +
                                        "author_id CHAR(36)," +
                                        "deletedAt TIMESTAMP DEFAULT NULL," + 
                                        "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                        "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                                        "FOREIGN KEY (author_id) REFERENCES authors(id)," +
                                        "FOREIGN KEY (category_id) REFERENCES categories(id))";

            String createReservationTable = "CREATE TABLE IF NOT EXISTS reservations (" +
                                            "id CHAR(36) PRIMARY KEY DEFAULT (UUID())," +
                                            "user_id CHAR(36)," +
                                            "book_id CHAR(36)," +
                                            "reservation_date TIMESTAMP," +
                                            "status TINYINT(1) DEFAULT 0," +
                                            "deletedAt TIMESTAMP DEFAULT NULL," + 
                                            "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                            "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                                            "FOREIGN KEY (user_id) REFERENCES users(id)," +
                                            "FOREIGN KEY (book_id) REFERENCES books(id))";

            String createBorrowingTable = "CREATE TABLE IF NOT EXISTS borrowings (" +
                                            "id CHAR(36) PRIMARY KEY DEFAULT (UUID())," +
                                            "user_id CHAR(36)," +
                                            "book_id CHAR(36)," +
                                            "status TINYINT(1) DEFAULT 0," +
                                            "date_borrowed TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                            "due_date TIMESTAMP NULL," +
                                            "deletedAt TIMESTAMP DEFAULT NULL," + 
                                            "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                                            "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                                            "FOREIGN KEY (user_id) REFERENCES users(id)," +
                                            "FOREIGN KEY (book_id) REFERENCES books(id))" ;



            // String createSessionTable = "CREATE TABLE IF NOT EXISTS session (" +
            //                              "id CHAR(36) PRIMARY KEY DEFAULT (UUID())," +
            //                              "user_id CHAR(36) NOT NULL UNIQUE," +
            //                              "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            //                              "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            //                              "FOREIGN KEY (user_id) REFERENCES users(id)" ;


            statement.executeUpdate(createUserTable);
            statement.executeUpdate(createCategoriesTable);
            statement.executeUpdate(createAuthorTable);
            statement.executeUpdate(createBooksTable);
            statement.executeUpdate(createReservationTable);
            statement.executeUpdate(createBorrowingTable);
            // statement.executeUpdate(createSessionTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
