package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.lms.config.DatabaseConfig;
import com.lms.models.Category;

public class CategoryDAO {

    private DatabaseConfig databaseConfig = new DatabaseConfig();

    public boolean createCategory(Category category) {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, category.getName());
            int rowsAffected = preparedStatement.executeUpdate();
            connection.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getCategoryID(String name) {
        String sql = "SELECT id FROM categories WHERE name=? AND deletedAt IS NULL";
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

    public LinkedList<Category> getCategories() {
        String sql = "SELECT * FROM categories WHERE deletedAt IS NULL";
        LinkedList<Category> result = new LinkedList<Category>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(new Category(resultSet.getString("id"), resultSet.getString("name"), resultSet.getTimestamp("createdAt"), resultSet.getTimestamp("updatedAt")));
            }

            return result;
        } catch (SQLException e) {
            return result;
        }
    }

    public LinkedList<Category> getCategoryNames() {
        String sql = "SELECT name FROM categories";
        LinkedList<Category> queryResult = new LinkedList<Category>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            

            while(resultSet.next()) {
                queryResult.add(new Category(resultSet.getString("name")));
            }
            connection.close();

            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queryResult;
    }

    public boolean deleteCategory(String id) {
        String sql = "UPDATE categories SET deletedAt = CURRENT_TIMESTAMP WHERE id=?";
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

    public boolean updateCategory(Category category, String id) {
        String sql = "UPDATE categories SET name=? WHERE id=?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            statement.setString(2, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Category getCategoryById(String id) {
        String sql = "SELECT * FROM categories WHERE id=?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Category(resultSet.getString("id"), resultSet.getString("name"), resultSet.getTimestamp("createdAt"), resultSet.getTimestamp("updatedAt"));
            }

            return new Category();
        } catch (SQLException e) {
            return new Category();
        }
    }

    public LinkedList<Category> findCategory(String query) {
        String sql = "SELECT * FROM categories " +
                     "WHERE categories.deletedAt IS NULL AND name LIKE ?";

        LinkedList<Category> queryResult = new LinkedList<Category>();
        try (Connection connection = databaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + query + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            

            while(resultSet.next()) {
                queryResult.add(new Category(
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
