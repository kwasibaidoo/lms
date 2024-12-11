package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.lms.config.DatabaseConfig;
import com.lms.models.AppUser;

public class AppUserDAO {
    private DatabaseConfig databaseConfig = new DatabaseConfig();

    
    public boolean createUser(AppUser appUser) {
        String sql = "INSERT INTO users (name,email,password,accountType) VALUES (?,?,?,?)";
        try (Connection connection = databaseConfig.getConnection();
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

    // public boolean setIsLoggedIn(AppUser appUser) {
    //     String sql = "UPDATE users SET isLoggedIn = true WHERE id=?";
    //     try (Connection connection = DatabaseConfig.getConnection();
    //          PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    //         preparedStatement.setString(1, appUser.getId());

    //         int rowsAffected = preparedStatement.executeUpdate();
    //         return rowsAffected > 0;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public String getUserRole(AppUser appUser) {
        String sql = "SELECT accountType from users WHERE accountType = ? AND deletedAt IS NULL";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, appUser.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getString("accountType");
            }
            else{
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    } 


    public String getUserID(String name) {
        String sql = "SELECT id FROM users WHERE name=? AND deletedAt IS NULL LIMIT 1";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getString("id");
            }
            else{
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public AppUser findUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email=? AND deletedAt IS NULL LIMIT 1";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                return new AppUser(
                                    resultSet.getString("id"), 
                                    resultSet.getString("name"), 
                                    resultSet.getString("email"), 
                                    resultSet.getString("password"), 
                                    resultSet.getString("accountType"), 
                                    resultSet.getTimestamp("deletedAt"), 
                                    resultSet.getTimestamp("createdAt"), 
                                    resultSet.getTimestamp("updatedAt")
                                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return new AppUser();
    } 


    public AppUser findUserById(String id) {
        String sql = "SELECT * FROM users WHERE id=? AND deletedAt IS NULL LIMIT 1";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new AppUser(
                                    resultSet.getString("id"), 
                                    resultSet.getString("name"), 
                                    resultSet.getString("email"), 
                                    resultSet.getString("password"), 
                                    resultSet.getString("accountType"), 
                                    resultSet.getTimestamp("deletedAt"), 
                                    resultSet.getTimestamp("createdAt"), 
                                    resultSet.getTimestamp("updatedAt")
                                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return new AppUser();
    } 


    public LinkedList<AppUser> getUsers() {
        String sql = "SELECT * FROM users WHERE deletedAt IS NULL AND accountType = 'patron'";
        LinkedList<AppUser> queryResult = new LinkedList<AppUser>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                queryResult.add(new AppUser(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("accountType"),
                    resultSet.getTimestamp("deletedAt"),
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

    public boolean updateUser(AppUser appUser, String id) {
        String sql = "UPDATE users SET name=?,email=? WHERE id=?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, appUser.getName());
            statement.setString(2, appUser.getEmail());
            statement.setString(3, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserPassword(AppUser appUser, String id) {
        String sql = "UPDATE users SET password=? WHERE id=?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, appUser.getPassword());
            statement.setString(2, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
