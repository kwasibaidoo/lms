package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.lms.config.DatabaseConfig;
import com.lms.models.Reservation;

public class ReservationDAO {

    public static boolean createReservation(Reservation reservation) {
        String sql = "INSERT INTO reservations (user_id,book_id,status,reservation_date) VALUES (? , ? , ? , ?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1,reservation.getUser_id());
            preparedStatement.setString(2,reservation.getBook_id());
            preparedStatement.setInt(3,reservation.getStatus());
            preparedStatement.setTimestamp(4,reservation.getReservation_date());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static LinkedList<Reservation> getAllReservations() {
        String sql = "SELECT * FROM reservations";
        LinkedList<Reservation> queryResult = new LinkedList<Reservation>();
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                queryResult.add(new Reservation(
                    resultSet.getString("id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("book_id"),
                    resultSet.getInt("status"),
                    resultSet.getTimestamp("reservation_date"),
                    resultSet.getTimestamp("createdAt"),
                    resultSet.getTimestamp("updatedAt")
                ));
            }
            return queryResult;
        } catch (Exception e) {
            e.printStackTrace();
            return queryResult;
        }
    }


    public static LinkedList<Reservation> getUserReservations(String user_id) {
        String sql = "SELECT * FROM reservations WHERE user_id=?";
        LinkedList<Reservation> queryResult = new LinkedList<Reservation>();
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                queryResult.add(new Reservation(
                    resultSet.getString("id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("book_id"),
                    resultSet.getInt("status"),
                    resultSet.getTimestamp("reservation_date"),
                    resultSet.getTimestamp("createdAt"),
                    resultSet.getTimestamp("updatedAt")
                ));
            }
            return queryResult;
        } catch (Exception e) {
            e.printStackTrace();
            return queryResult;
        }
    }

    public static Reservation getReservationById(String id) {
        String sql = "SELECT * FROM reservations WHERE id=?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return new Reservation(
                    resultSet.getString("id"),
                    resultSet.getString("user_id"),
                    resultSet.getString("book_id"),
                    resultSet.getInt("status"),
                    resultSet.getTimestamp("reservation_date"),
                    resultSet.getTimestamp("createdAt"),
                    resultSet.getTimestamp("updatedAt")
                );
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return new Reservation();
        }
        return new Reservation();
    }

    public static boolean deleteReservation(String id) {
        String sql = "UPDATE reservations SET deletedAt = CURRENT_TIMESTAMP WHERE id=?";
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
