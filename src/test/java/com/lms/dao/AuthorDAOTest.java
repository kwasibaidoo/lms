package com.lms.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    }

    // @Test
    // void testDeleteAuthor() {

    // }

    // @Test
    // void testFindAuthor() {

    // }

    // @Test
    // void testGetAuthorById() {

    // }

    // @Test
    // void testGetAuthorID() {

    // }

    // @Test
    // void testGetAuthors() {

    // }

    // @Test
    // void testGetAuthorsName() {

    // }

    // @Test
    // void testUpdateAuthor() {

    // }
}
