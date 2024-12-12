package com.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.lms.config.DatabaseConfig;
import com.lms.models.Book;

public class BookDAO {

    private DatabaseConfig databaseConfig = new DatabaseConfig();
    public boolean createBook(Book book) {
        String sql = "INSERT INTO books (name,author_id,category_id,location,availableCopies,totalCopies) VALUES (?,?,?,?,?,?)";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getName());
            preparedStatement.setString(2, book.getAuthor_id());
            preparedStatement.setString(3, book.getCategory_id());
            preparedStatement.setString(4, book.getLocation());
            preparedStatement.setInt(5, book.getAvailableCopies());
            preparedStatement.setInt(6, book.getTotalCopies());
            int rowsAffected = preparedStatement.executeUpdate();
            connection.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Book getBookById(String id) {
        String sql = "SELECT books.*,authors.name AS author_name,categories.name AS category_name FROM books " +
                      "INNER JOIN authors ON authors.id=books.author_id " +
                      "INNER JOIN categories ON categories.id=books.category_id " +
                      "WHERE books.deletedAt IS NULL AND books.id=?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                return new Book(
                                resultSet.getString("id"), 
                                resultSet.getString("name"),
                                resultSet.getString("author_name"),
                                resultSet.getString("category_name"),
                                resultSet.getString("location"),
                                resultSet.getInt("availableCopies"),
                                resultSet.getInt("totalCopies"),
                                resultSet.getTimestamp("createdAt"),
                                resultSet.getTimestamp("updatedAt")
                            );
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return new Book();
        }
        return new Book();
    }


    public String getBookID(String name) {
        String sql = "SELECT id FROM books WHERE name=?";
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
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }


    public LinkedList<Book> getBooks() {
        String sql = "SELECT books.*,authors.name AS author_name,categories.name AS category_name FROM books " +
                     "INNER JOIN authors ON authors.id=books.author_id " +
                     "INNER JOIN categories ON categories.id=books.category_id " +
                     "WHERE books.deletedAt IS NULL";
        LinkedList<Book> queryResult = new LinkedList<Book>();
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            

            while(resultSet.next()) {
                queryResult.add(new Book(
                    resultSet.getString("id"), 
                    resultSet.getString("name"),
                    resultSet.getString("author_name"),
                    resultSet.getString("category_name"),
                    resultSet.getString("location"),
                    resultSet.getInt("availableCopies"),
                    resultSet.getInt("totalCopies"),
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


    public boolean deleteBook(String id) {
        String sql = "UPDATE books SET deletedAt = CURRENT_TIMESTAMP WHERE id=?";
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

    public boolean updateBook(Book book, String id) {
        String sql = "UPDATE books SET name=?,author_id=?,category_id=?,location=?,availableCopies=?,totalCopies=? WHERE id=?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor_id());
            statement.setString(3, book.getCategory_id());
            statement.setString(4, book.getLocation());
            statement.setInt(5, book.getAvailableCopies());
            statement.setInt(6, book.getTotalCopies());

            statement.setString(7, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookAvailableCopies(Book book, String id) {
        String sql = "UPDATE books SET availableCopies=? WHERE id=?";
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, book.getAvailableCopies());

            statement.setString(2, id);
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public LinkedList<Book> findBook(String query) {
        String sql = "SELECT books.*,authors.name AS author_name,categories.name AS category_name FROM books " +
                     "INNER JOIN authors ON authors.id=books.author_id " +
                     "INNER JOIN categories ON categories.id=books.category_id " +
                     "WHERE books.deletedAt IS NULL AND (books.name LIKE ? OR authors.name LIKE ? OR categories.name LIKE ?)";

        LinkedList<Book> queryResult = new LinkedList<Book>();
        try (Connection connection = databaseConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "%" + query + "%");
            preparedStatement.setString(2, "%" + query + "%");
            preparedStatement.setString(3, "%" + query + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            

            while(resultSet.next()) {
                queryResult.add(new Book(
                    resultSet.getString("id"), 
                    resultSet.getString("name"),
                    resultSet.getString("author_name"),
                    resultSet.getString("category_name"),
                    resultSet.getString("location"),
                    resultSet.getInt("availableCopies"),
                    resultSet.getInt("totalCopies"),
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
