package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.lms.config.DatabaseConfig;
import com.lms.models.Borrowing;

public class BorrowDAO {
    public static boolean createBorrowingRecord(Borrowing borrowing) {
        String sql = "INSERT INTO borrowings (user_id, book_id, due_date, date_borrowed) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, borrowing.getUser_id());
            preparedStatement.setString(2, borrowing.getBook_id());
            preparedStatement.setTimestamp(3, borrowing.getDue_date());
            preparedStatement.setTimestamp(4, borrowing.getDateBorrowed());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static LinkedList<Borrowing> getAllRecords() {
        String sql = "SELECT * FROM borrowings WHERE deletedAt IS NULL";
        LinkedList<Borrowing> queryResult = new LinkedList<Borrowing>();
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                queryResult.add(new Borrowing(
                    resultSet.getString("id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("book_id"),
                    resultSet.getInt("status"),
                    resultSet.getTimestamp("date_borrowed"),
                    resultSet.getTimestamp("due_date"),
                    resultSet.getTimestamp("deletedAt"),
                    resultSet.getTimestamp("createdAt"),
                    resultSet.getTimestamp("updatedAt")
                ));
            }

            return queryResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return queryResult;
        }
    }

    public static LinkedList<Borrowing> getUserRecords(String id) {
        String sql = "SELECT * FROM borrowings WHERE deletedAt IS NULL AND user_id = ?";
        LinkedList<Borrowing> queryResult = new LinkedList<Borrowing>();
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                queryResult.add(new Borrowing(
                    resultSet.getString("id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("book_id"),
                    resultSet.getInt("status"),
                    resultSet.getTimestamp("dateBorrowed"),
                    resultSet.getTimestamp("due_date"),
                    resultSet.getTimestamp("deletedAt"),
                    resultSet.getTimestamp("createdAt"),
                    resultSet.getTimestamp("updatedAt")
                ));
            }

            return queryResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return queryResult;
        }
    }


    public static Borrowing getRecordByID(String id) {
        String sql = "SELECT * FROM borrowings WHERE deletedAt IS NULL AND id = ?";
        // LinkedList<Borrowing> queryResult = new LinkedList<Borrowing>();
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Borrowing(
                    resultSet.getString("id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("book_id"),
                    resultSet.getInt("status"),
                    resultSet.getTimestamp("date_borrowed"),
                    resultSet.getTimestamp("due_date"),
                    resultSet.getTimestamp("deletedAt"),
                    resultSet.getTimestamp("createdAt"),
                    resultSet.getTimestamp("updatedAt")
                );
            }

            return new Borrowing();
            // return queryResult;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Borrowing();
        }
    }

    public static boolean deleteBorrowing(String id) {
        String sql = "UPDATE borrowings SET deletedAt = CURRENT_TIMESTAMP WHERE id=?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
