package com.lms.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DatabaseConfigTest {
    @Test
    void testGetConnection() throws SQLException {
        Connection mockConnection = mock(Connection.class);
        try (MockedStatic<DriverManager> mockedDriverManager = mockStatic(DriverManager.class);) {
            mockedDriverManager.when(()-> DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);

            // Connection connection = DatabaseConfig.getConnection();
            // assertNotNull(connection);
            // assertEquals(mockConnection, connection);
            // mockedDriverManager.verify(() -> DriverManager.getConnection(anyString(), anyString(), anyString()), times(1));
        } 
    }
}
