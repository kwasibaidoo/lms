package com.lms.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lms.config.DatabaseConfig;


@ExtendWith(MockitoExtension.class)
public class ValidatorTest {
    @InjectMocks
    private Validator validator;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private ResultSet resultSet;


    

    
    @ParameterizedTest
    @CsvSource({
        "'password', 'password', true", // valid 
        "'password', 'passwor', false", // non matching passwords
        "'pas', 'pas', false", // short password
    })
    void testPasswordValidation(String password, String confirmation, boolean expectedValidity) {
        if(expectedValidity) {
            ValidationResult validationResult = validator.passwordValidation(password, confirmation);
            assertTrue(validationResult.isSuccess());
        }
        else {
            ValidationResult validationResult2 = validator.passwordValidation(password, confirmation);
            assertFalse(validationResult2.isSuccess());
        }
    }


    @ParameterizedTest
    @CsvSource({
        "'email', 'email@example.com', true",
        "'email', 'invalid-email.com', false", 
        "'not_null', '', false",    
        "'not_null', 'some value', true", 
        "'unique|users,email', 'email@example.com', true", 
        "'unique|users,email', 'duplicate@example.com', false", 
        "'', 'someemail', true"
    })
    void testValidateWrongOrNoValidationArgument(String validationType, String value, boolean expectedValidity) throws SQLException {
        if(validationType.equals("unique|users,email")) {
            when(databaseConfig.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getInt(1)).thenReturn(expectedValidity ? 0 : 1);
            ValidationResult result = validator.validate(value, validationType);
            if (expectedValidity) {
                assertTrue(result.isSuccess());
            } else {
                assertFalse(result.isSuccess());
            }

            when(databaseConfig.getConnection()).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
            ValidationResult result_sql_exception = validator.validate(value, validationType);
            assertFalse(result_sql_exception.isSuccess());
        }
        ValidationResult result = validator.validate(value, validationType);

        if(validationType.equals("")){
            assertTrue(result.isSuccess());
        }
    }

    @ParameterizedTest
    @CsvSource({
        "'not_null', '', false",
        "'not_null', '2024-10-10', true",
        "'valid_date', '2024-10-10', true", 
        "'valid_date', 'invalid-date', false", 
        "'before_today', '2025-01-01', false",
        "'before_today', '2023-12-01', true",
        "'after_today', '2023-12-01', false",
        "'after_today', '2025-01-01', true"
    })
    void testValidateDate(String validationType, String value, boolean expectedValidity) {
        // Pass the parameters dynamically to the validateDate method
        ValidationResult validationResult = validator.validateDate(value, validationType);

        if (expectedValidity) {
            assertTrue(validationResult.isSuccess(), 
                "Expected validation to pass but it failed for value: " + value + ", rule: " + validationType);
        } else {
            assertFalse(validationResult.isSuccess(),
                "Expected validation to fail but it passed for value: " + value + ", rule: " + validationType);
        }
    }
}
