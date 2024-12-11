package com.lms.utils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lms.config.DatabaseConfig;


@ExtendWith(MockitoExtension.class)
public class ValidatorTest {
    private Validator validator;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private ResultSet resultSet;


    @BeforeEach
    void setUp() {
        validator = new Validator();
    }

    
    @Test
    void testPasswordValidation() {
        String value = "password";
        String confirmation = "password";


        ValidationResult validationResult = validator.passwordValidation(value, confirmation);
        assertTrue(validationResult.isSuccess());
    }

    @Test
    void testEmailValidate() {;
        ValidationResult validationResult = validator.validate("email@example.com", "email");
        assertTrue(validationResult.isSuccess());
    }

    @Test
    void testUniqueEmailValidate() throws SQLException {
        lenient().when(databaseConfig.getConnection()).thenReturn(mockConnection);
        lenient().when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        lenient().when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
        lenient().when(resultSet.next()).thenReturn(true);
        lenient().when(resultSet.getInt(1)).thenReturn(0); // No duplicate found

        ValidationResult validationResult = validator.validate("email@example.com", "unique|users,email");
        assertTrue(validationResult.isSuccess(), "Email should be unique");
    }

    @Test
    void testValidateDate() {
        ValidationResult validationResult = validator.validateDate("2024-12-20", "after_today");
        assertTrue(validationResult.isSuccess());
    }
}
