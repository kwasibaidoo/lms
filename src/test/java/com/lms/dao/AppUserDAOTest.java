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
import java.util.LinkedList;

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

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = appUserDAO.createUser(appUser);
        assertFalse(failure);
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

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        AppUser appUser_failure = appUserDAO.findUserByEmail(email);
        assertEquals(appUser_failure, new AppUser());

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        AppUser appUser_failure_exception = appUserDAO.findUserByEmail(email);
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

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        AppUser appUser_failure = appUserDAO.findUserById(id);
        assertEquals(appUser_failure, new AppUser());

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        AppUser appUser_failure_exception = appUserDAO.findUserById(id);
    }

    @Test
    void testGetUserID() throws SQLException {
        String name = "John";
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("id")).thenReturn("1");

        String id = appUserDAO.getUserID(name);
        assertEquals("1", id);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        String id_failure = appUserDAO.getUserID(name);
        assertEquals("", id_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        String id_failure_exception = appUserDAO.getUserID(name);
        assertEquals("", id_failure_exception);
    }

    @Test
    void testGetUserRole() throws SQLException {
        AppUser appUser = new AppUser("John Doe", "john@example.com", "password123", "USER");

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("accountType")).thenReturn("USER");

        String role = appUserDAO.getUserRole(appUser);
        assertEquals("USER", role);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        String role_failure = appUserDAO.getUserRole(appUser);
        assertEquals("", role_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        String role_failure_exception = appUserDAO.getUserRole(appUser);
        assertEquals("", role_failure_exception);
    }

    @Test
    void testGetUsers() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);

        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("name")).thenReturn("John Doe");
        when(mockResultSet.getString("email")).thenReturn("john@gmail.com");
        when(mockResultSet.getString("accountType")).thenReturn("USER");
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(null); // Mocking other fields as needed
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(null);
        when(mockResultSet.getTimestamp("deletedAt")).thenReturn(null);

        LinkedList<AppUser> appUsers = appUserDAO.getUsers();
        assertNotNull(appUsers);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<AppUser> appUsers_failure = appUserDAO.getUsers();
        assertEquals(appUsers_failure, new LinkedList<AppUser>());
    }

    @Test
    void testUpdateUser() throws SQLException {
        AppUser appUser = new AppUser("John Doe", "john@example.com", "password123", "USER");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean success = appUserDAO.updateUser(appUser, "1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = appUserDAO.updateUser(appUser, "1");
        assertFalse(failure);
    }

    @Test
    void testUpdateUserPassword() throws SQLException {
        AppUser appUser = new AppUser("John Doe", "john@example.com", "password123", "USER");
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean success = appUserDAO.updateUserPassword(appUser, "1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = appUserDAO.updateUserPassword(appUser, "1");
        assertFalse(failure);
    }
}
