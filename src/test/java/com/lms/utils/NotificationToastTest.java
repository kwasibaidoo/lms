package com.lms.utils;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


@ExtendWith(MockitoExtension.class)
public class NotificationToastTest {
    @Mock
    private Alert mockAlert;

    @InjectMocks
    private NotificationToast notificationToast;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        notificationToast = new NotificationToast() {
            @Override
            public void showNotification(AlertType type, String title, String message) {
                // Use the mock Alert instead of creating a real one
                mockAlert.setContentText(message);
                mockAlert.setTitle(title);
                mockAlert.setAlertType(type);
                mockAlert.showAndWait();
            }
        };
    }

    @Test
    void testShowNotification() {
        AlertType expectedType = AlertType.INFORMATION;
        String expectedTitle = "Test Title";
        String expectedMessage = "This is a test message";

        // Act
        notificationToast.showNotification(expectedType, expectedTitle, expectedMessage);
        verify(mockAlert).setContentText(expectedMessage);  // Check if setContentText was called
        verify(mockAlert).setTitle(expectedTitle);  // Check if setTitle was called
        verify(mockAlert).setAlertType(expectedType);
        verify(mockAlert).showAndWait();
        
    }
}
