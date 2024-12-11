package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.lms.config.DatabaseConfig;
import com.lms.models.Author;

public class AuthorDAO {

    private DatabaseConfig databaseConfig = new DatabaseConfig();

    public boolean createAuthor(Author author) {
        String sql = "INSERT INTO authors (name) VALUES (?)";
        try (Connection connection = databaseConfig.getConnection();
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


    public Author getAuthorById(String id) {
        String sql = "SELECT * FROM authors WHERE id=? AND deletedAt IS NULL";
        try (Connection connection = databaseConfig.getConnection();
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


    public String getAuthorID(String name) {
        String sql = "SELECT id FROM authors WHERE name=? AND deletedAt IS NULL";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("id");
            }
            else{
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }


    public LinkedList<Author> getAuthors() {
        String sql = "SELECT * FROM authors WHERE deletedAt IS NULL";
        LinkedList<Author> queryResult = new LinkedList<Author>();
        try (Connection connection = databaseConfig.getConnection();
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

    public LinkedList<Author> getAuthorsName() {
        String sql = "SELECT name FROM authors";
        LinkedList<Author> queryResult = new LinkedList<Author>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            

            while(resultSet.next()) {
                queryResult.add(new Author(resultSet.getString("name")));
            }
            connection.close();

            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queryResult;
    }

    public boolean deleteAuthor(String id) {
        String sql = "UPDATE authors SET deletedAt = CURRENT_TIMESTAMP WHERE id=?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAuthor(Author author, String id) {
        String sql = "UPDATE authors SET name=? WHERE id=?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, author.getName());
            statement.setString(2, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LinkedList<Author> findAuthor(String query) {
        String sql = "SELECT * FROM authors " +
                     "WHERE authors.deletedAt IS NULL AND name LIKE ?";

        LinkedList<Author> queryResult = new LinkedList<Author>();
        try (Connection connection = databaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + query + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            

            while(resultSet.next()) {
                queryResult.add(new Author(
                    resultSet.getString("id"), 
                    resultSet.getString("name"),
                    resultSet.getTimestamp("createdAt"),
                    resultSet.getTimestamp("updatedAt")
                ));
            }
            connection.close();

            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queryResult;
    }
}
