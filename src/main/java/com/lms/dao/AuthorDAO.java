package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.lms.config.DatabaseConfig;
import com.lms.models.Author;

public class AuthorDAO {

    public static boolean createAuthor(Author author) {
        String sql = "INSERT INTO authors (name) VALUES (?)";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, author.getName());
            int rowsAffected = preparedStatement.executeUpdate();
            connection.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static Author getAuthorById(String id) {
        String sql = "SELECT * FROM authors WHERE id=?";
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return new Author(resultSet.getString("id"), resultSet.getString("name"),resultSet.getTimestamp("createdAt"),resultSet.getTimestamp("updatedAt"));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return new Author();
        }
        return new Author();
    }


    public static LinkedList<Author> getAuthors() {
        String sql = "SELECT * FROM authors";
        LinkedList<Author> queryResult = new LinkedList<Author>();
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            

            while(resultSet.next()) {
                queryResult.add(new Author(resultSet.getString("id"), resultSet.getString("name"),resultSet.getTimestamp("createdAt"),resultSet.getTimestamp("updatedAt")));
            }
            connection.close();

            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queryResult;
    }
}