package com.lms.dao;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lms.config.DatabaseConfig;
import com.lms.models.AppUser;


@ExtendWith(MockitoExtension.class)
public class AppUserDAOTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private AppUserDAO appUserDAO;

    @BeforeEach
    void setUp() throws SQLException {
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }


    

    @Test
    void testCreateUser() throws SQLException {
        AppUser appUser = new AppUser("John Doe", "john@example.com", "password123", "USER");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean success = appUserDAO.createUser(appUser);
        assertTrue(success);
    }

    @Test
    void testFindUserByEmail() throws SQLException {
        String email = "email@example.com";
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn(email);
        when(mockResultSet.getString("password")).thenReturn("password123");
        when(mockResultSet.getString("accountType")).thenReturn("USER");
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(null); // Mocking other fields as needed
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(null);
        when(mockResultSet.getTimestamp("deletedAt")).thenReturn(null);

        AppUser appUser = appUserDAO.findUserByEmail(email);
        assertEquals(appUser.getEmail(),email);
    }

    @Test
    void testFindUserById() throws SQLException {
        String id = "gg";
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("id")).thenReturn(id);
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn("john@gmail.com");
        when(mockResultSet.getString("password")).thenReturn("password123");
        when(mockResultSet.getString("accountType")).thenReturn("USER");
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(null); // Mocking other fields as needed
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(null);
        when(mockResultSet.getTimestamp("deletedAt")).thenReturn(null);

        AppUser appUser = appUserDAO.findUserById(id);
        assertEquals(appUser.getId(),id);
    }

    @Test
    void testGetUserID() throws SQLException {
        String name = "John";
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("id")).thenReturn("1");

        String id = appUserDAO.getUserID(name);
        assertEquals("1", id);
    }

    // @Test
    // void testGetUserRole() {

    // }

    // @Test
    // void testGetUsers() {

    // }

    // @Test
    // void testUpdateUser() {

    // }

    // @Test
    // void testUpdateUserPassword() {

    // }
}
