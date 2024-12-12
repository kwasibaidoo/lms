package com.lms.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
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
import com.lms.models.Author;


@ExtendWith(MockitoExtension.class)
public class AuthorDAOTest {
    
    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private AuthorDAO authorDAO;

    @BeforeEach
    void setUp() throws SQLException {
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void testCreateAuthor() throws SQLException {
        Author author = new Author("Kwasi Baidoo");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = authorDAO.createAuthor(author);
        assertTrue(success);
        
        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);  // Mocking SQLException
        boolean failure = authorDAO.createAuthor(author);
        assertFalse(failure);
    }

    @Test
    void testDeleteAuthor() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = authorDAO.deleteAuthor("34");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = authorDAO.deleteAuthor("34");
        assertFalse(failure);

    }

    @Test
    void testFindAuthor() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("id")).thenReturn("1");
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2020-01-01 10:00:00"));
        when(resultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2021-01-01 10:00:00"));

        LinkedList<Author> result = authorDAO.findAuthor("John");
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Author> result_failure = authorDAO.findAuthor("John");
        assertEquals(result_failure, new LinkedList<Author>());
    }

    @Test
    void testGetAuthorById() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("id")).thenReturn("1");
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2020-01-01 10:00:00"));
        when(resultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2021-01-01 10:00:00"));

        Author author = authorDAO.getAuthorById("1");
        assertNotNull(author);
        assertEquals(author.getId(), "1");

        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Author author_failure = authorDAO.getAuthorById("1");
        assertNotNull(author_failure);
        assertEquals(null, author_failure.getId());
        assertEquals(null, author_failure.getName());
    }

    @Test
    void testGetAuthorById_Exception() throws SQLException {
        // Arrange
        String id = "1";
        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        Author author = authorDAO.getAuthorById(id);
        assertNotNull(author);
        assertEquals(null, author.getId());
        assertEquals(null, author.getName());
    }

    @Test
    void testGetAuthorID() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("id")).thenReturn("1");
        String id = authorDAO.getAuthorID("John");

        verify(mockConnection).close();

        assertNotNull(id);
        assertEquals(id, "1");

        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);
        String id_failure = authorDAO.getAuthorID("John");
        assertEquals(id_failure, "");

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        String id_failure_exception = authorDAO.getAuthorID("John");
        assertEquals(id_failure_exception, "");
    }

    @Test
    void testGetAuthors() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getString("id")).thenReturn("1");
        when(resultSet.getString("name")).thenReturn("John Doe");
        when(resultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2020-01-01 10:00:00"));
        when(resultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2021-01-01 10:00:00"));

        LinkedList<Author> result = authorDAO.getAuthors();
        assertNotNull(result);
        assertEquals(result.size(), 2);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Author> result_failure = authorDAO.getAuthors();
        assertEquals(result_failure, new LinkedList<Author>());
    }

    @Test
    void testGetAuthorsName() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);

        when(resultSet.getString("name")).thenReturn("John Doe");

        LinkedList<Author> result = authorDAO.getAuthorsName();
        assertNotNull(result);
        assertEquals(result.size(), 2);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Author> result_failure = authorDAO.getAuthorsName();
        assertEquals(result_failure, new LinkedList<Author>());
    }

    @Test
    void testUpdateAuthor() throws SQLException {
        Author author = new Author("Kwasi Baidoo");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = authorDAO.updateAuthor(author, "1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = authorDAO.updateAuthor(author, "1");
        assertFalse(failure);
    }
}
