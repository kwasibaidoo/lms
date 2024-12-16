package com.lms.utils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lms.config.DatabaseConfig;

@ExtendWith(MockitoExtension.class)
public class DatabaseInitializerTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private Statement mockStatement;

    @InjectMocks
    private DatabaseInitializer databaseInitializer;

    @Test
    void testInitializeDatabase() throws SQLException {
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        databaseInitializer.initializeDatabase();
        verify(mockStatement, times(6)).executeUpdate(anyString());

        when(mockStatement.executeUpdate(anyString())).thenThrow(SQLException.class);
        databaseInitializer.initializeDatabase();
    }
}
