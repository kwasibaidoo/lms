package com.lms.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lms.config.DatabaseConfig;
import com.lms.models.Book;


@ExtendWith(MockitoExtension.class)
public class BookDAOTest {

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() throws SQLException {
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void testCreateBook() throws SQLException {
        Book book = new Book(
                "The last hope", 
                "1",
                "1",
                20,
                60,
                "Shelf 1"
            );
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = bookDAO.createBook(book);
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = bookDAO.createBook(book);
        assertFalse(failure);
    }

    @Test
    void testDeleteBook() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = bookDAO.deleteBook("34");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = bookDAO.deleteBook("34");
        assertFalse(failure);
    }

    @Test
    void testFindBook() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getString("id")).thenReturn("1"); 
        when(resultSet.getString("name")).thenReturn("The Last Hope");
        when(resultSet.getString("author_name")).thenReturn("Author name");
        when(resultSet.getString("category_name")).thenReturn("Category Name");
        when(resultSet.getString("location")).thenReturn("Shelf 1");
        when(resultSet.getInt("availableCopies")).thenReturn(10);
        when(resultSet.getInt("totalCopies")).thenReturn(40);
        when(resultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2020-01-01 10:00:00"));
        when(resultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2020-01-01 10:00:00"));

        LinkedList<Book> result = bookDAO.findBook("The last hope");
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Book> failureresult = bookDAO.findBook("The last hope");
        assertNotNull(failureresult);
    }

    @Test
    void testGetBookById() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getString("id")).thenReturn("1"); 
        when(resultSet.getString("name")).thenReturn("The Last Hope");
        when(resultSet.getString("author_name")).thenReturn("Author name");
        when(resultSet.getString("category_name")).thenReturn("Category Name");
        when(resultSet.getString("location")).thenReturn("Shelf 1");
        when(resultSet.getInt("availableCopies")).thenReturn(10);
        when(resultSet.getInt("totalCopies")).thenReturn(40);
        when(resultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2020-01-01 10:00:00"));
        when(resultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2020-01-01 10:00:00"));

        Book book = bookDAO.getBookById("1");
        assertNotNull(book);

        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        Book failure_result = bookDAO.getBookById("1");
        assertEquals(failure_result, new Book());

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        Book failureresult = bookDAO.getBookById("1");
        assertNotNull(failureresult);
        assertNull(failureresult.getName());
        // assertEquals(null, failureresult.getAuthor_id());

        
    }

    @Test
    void testGetBookID() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);

        when(resultSet.getString("id")).thenReturn("1"); 
        String id = bookDAO.getBookID("The last hope");
        assertEquals(id, "1");

        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        String id_booknotfound = bookDAO.getBookID("The last hope");
        assertEquals(id_booknotfound, "");

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        String id_exception = bookDAO.getBookID("The last hope");
        assertEquals(id_exception, "");
    }

    @Test
    void testGetBooks() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);

        when(resultSet.getString("id")).thenReturn("1"); 
        when(resultSet.getString("name")).thenReturn("The Last Hope");
        when(resultSet.getString("author_name")).thenReturn("Author name");
        when(resultSet.getString("category_name")).thenReturn("Category Name");
        when(resultSet.getString("location")).thenReturn("Shelf 1");
        when(resultSet.getInt("availableCopies")).thenReturn(10);
        when(resultSet.getInt("totalCopies")).thenReturn(40);
        when(resultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2020-01-01 10:00:00"));
        when(resultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2020-01-01 10:00:00"));

        LinkedList<Book> books = bookDAO.getBooks();
        assertNotNull(books);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Book> books_failed = bookDAO.getBooks();
        assertEquals(books_failed.size(), 0);
    }

    @Test
    void testUpdateBook() throws SQLException {
        Book book = new Book(
            "The last hope", 
            "1",
            "1",
            20,
            60,
            "Shelf 1"
        );

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = bookDAO.updateBook(book, "1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = bookDAO.updateBook(book, "1");
        assertFalse(failure);
    }

    @Test
    void testUpdateBookAvailableCopies() throws SQLException {
        Book book = new Book(
            "The last hope", 
            "1",
            "1",
            20,
            60,
            "Shelf 1"
        );

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = bookDAO.updateBookAvailableCopies(book, "1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = bookDAO.updateBookAvailableCopies(book, "1");
        assertFalse(failure);
    }
}
