package com.lms.dao;



import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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


    

    @Test
    void testCreateUser() throws SQLException {

        // AppUser appUser = new AppUser("John Doe", "john@example.com", "password123", "patron");

        // // Mock the connection from DatabaseConfig.getConnection
        // try (MockedStatic<DatabaseConfig> mockDatabaseConfig = mockStatic(DatabaseConfig.class)) {
        //     // Mock DatabaseConfig.getConnection to return a mock connection
        //     // Connection mockConnection = mock(Connection.class);
        //     mockDatabaseConfig.when(DatabaseConfig::getConnection).thenReturn(mockConnection);

        //     // Mock the behavior of the connection and preparedStatement
        //     // PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        //     when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        //     when(mockPreparedStatement.executeUpdate()).thenReturn(1);  // Simulate successful insertion

        //     // Act: Call the createUser method
        //     boolean result = AppUserDAO.createUser(appUser);

        //     // Assert: Verify that the method returns true indicating success
        //     assertTrue(result, "User creation should be successful.");
            
        //     // Verify the PreparedStatement set methods were called with the correct parameters
        //     verify(mockPreparedStatement, times(1)).setString(1, "John Doe");
        //     verify(mockPreparedStatement, times(1)).setString(2, "john@example.com");
        //     verify(mockPreparedStatement, times(1)).setString(3, "password123");
        //     verify(mockPreparedStatement, times(1)).setString(4, "patron");
            
        //     // Verify that executeUpdate was called exactly once
        //     verify(mockPreparedStatement, times(1)).executeUpdate();
        // }
    }

    @Test
    void testFindUserByEmail() {

    }

    @Test
    void testFindUserById() {

    }

    @Test
    void testGetUserID() {

    }

    @Test
    void testGetUserRole() {

    }

    @Test
    void testGetUsers() {

    }

    @Test
    void testUpdateUser() {

    }

    @Test
    void testUpdateUserPassword() {

    }
}
