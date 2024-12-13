package com.lms.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.lms.models.Category;

@ExtendWith(MockitoExtension.class)
public class CategoryDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private CategoryDAO categoryDAO;

    @BeforeEach
    void setUp() throws SQLException {
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void testCreateCategory() throws SQLException {
        Category category = new Category("Comedy");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = categoryDAO.createCategory(category);
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = categoryDAO.createCategory(category);
        assertFalse(failure);
    }

    @Test
    void testDeleteCategory() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = categoryDAO.deleteCategory("1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = categoryDAO.deleteCategory("1");
        assertFalse(failure);
    }

    @Test
    void testFindCategory() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("name")).thenReturn("Comedy");
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        LinkedList<Category> result = categoryDAO.findCategory("Comed");
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        LinkedList<Category> result_failure = categoryDAO.findCategory("Comed");
        assertEquals(new LinkedList<Category>(), result_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Category> result_failure_exception = categoryDAO.findCategory("Comed");
    }

    @Test
    void testGetCategories() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("name")).thenReturn("Comedy");
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        LinkedList<Category> result = categoryDAO.getCategories();
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        LinkedList<Category> result_failure = categoryDAO.getCategories();
        assertEquals(new LinkedList<Category>(), result_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Category> result_failure_exception = categoryDAO.getCategories();
        assertEquals(new LinkedList<Category>(), result_failure_exception);
    }

    @Test
    void testGetCategoryById() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("name")).thenReturn("Comedy");
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        Category result = categoryDAO.getCategoryById("1");
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        Category result_failure = categoryDAO.getCategoryById("1");
        assertEquals(new Category(), result_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        Category result_failure_exception = categoryDAO.getCategoryById("1");
        assertEquals(new Category(), result_failure_exception);
    }

    @Test
    void testGetCategoryID() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("id")).thenReturn("1");
        String result = categoryDAO.getCategoryID("Comedy");
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        String result_failure = categoryDAO.getCategoryID("Comedy");
        assertEquals("", result_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        String result_failure_exception = categoryDAO.getCategoryID("Comedy");
        assertEquals("", result_failure_exception);
    }

    @Test
    void testGetCategoryNames() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("name")).thenReturn("Comedy");
        LinkedList<Category> result = categoryDAO.getCategoryNames();
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        LinkedList<Category> result_failure = categoryDAO.getCategoryNames();
        assertEquals(new LinkedList<Category>(), result_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Category> result_failure_exception = categoryDAO.getCategoryNames();
        assertEquals(new LinkedList<Category>(), result_failure_exception);
    }

    @Test
    void testUpdateCategory() throws SQLException {
        Category category = new Category("Comedy");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = categoryDAO.updateCategory(category, "1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = categoryDAO.updateCategory(category, "1");
        assertFalse(failure);
    }
}
