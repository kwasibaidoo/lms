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
import com.lms.models.Reservation;

@ExtendWith(MockitoExtension.class)
public class ReservationDAOTest {

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private ReservationDAO reservationDAO;

    @BeforeEach
    void setUp() throws SQLException {
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void testCreateReservation() throws SQLException {
        Reservation reservation =  new Reservation("1", "1", 0, Timestamp.valueOf("2024-12-12 12:12:12"));
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = reservationDAO.createReservation(reservation);
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        boolean failure_count = reservationDAO.createReservation(reservation);
        assertFalse(failure_count);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = reservationDAO.createReservation(reservation);
        assertFalse(failure);
    }

    @Test
    void testDeleteReservation() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = reservationDAO.deleteReservation("1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenReturn(0);
        boolean failure_count = reservationDAO.deleteReservation("1");
        assertFalse(failure_count);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = reservationDAO.deleteReservation("1");
        assertFalse(failure);
    }

    @Test
    void testGetAllReservations() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("user_name")).thenReturn("Kwasi Baidoo");
        when(mockResultSet.getString("book_name")).thenReturn("The Last hope");
        when(mockResultSet.getInt("status")).thenReturn(1);
        when(mockResultSet.getTimestamp("reservation_date")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));

        LinkedList<Reservation> result = reservationDAO.getAllReservations();
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        LinkedList<Reservation> result_failure = reservationDAO.getAllReservations();
        assertEquals(new LinkedList<Reservation>(), result_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Reservation> result_failure_exception = reservationDAO.getAllReservations();
        assertEquals(new LinkedList<Reservation>(), result_failure_exception);
    }

    @Test
    void testGetReservationById() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("user_name")).thenReturn("Kwasi Baidoo");
        when(mockResultSet.getString("book_name")).thenReturn("The Last hope");
        when(mockResultSet.getInt("status")).thenReturn(1);
        when(mockResultSet.getTimestamp("reservation_date")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));

        Reservation result = reservationDAO.getReservationById("1");
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        Reservation result_failure = reservationDAO.getReservationById("1");
        assertEquals(new Reservation(), result_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        Reservation result_failure_exception = reservationDAO.getReservationById("1");
        assertEquals(new Reservation(), result_failure_exception);
    }

    @Test
    void testGetUserReservations() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("user_name")).thenReturn("Kwasi Baidoo");
        when(mockResultSet.getString("book_name")).thenReturn("The Last hope");
        when(mockResultSet.getInt("status")).thenReturn(1);
        when(mockResultSet.getTimestamp("reservation_date")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));

        LinkedList<Reservation> result = reservationDAO.getUserReservations("1");
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        LinkedList<Reservation> result_failure = reservationDAO.getUserReservations("1");
        assertEquals(new LinkedList<Reservation>(), result_failure);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Reservation> result_failure_exception = reservationDAO.getUserReservations("1");
        assertEquals(new LinkedList<Reservation>(), result_failure_exception);
    }
}
