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
import com.lms.models.Borrowing;


@ExtendWith(MockitoExtension.class)
public class BorrowDAOTest {

    @Mock
    private DatabaseConfig databaseConfig;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private BorrowDAO borrowDAO;

    @BeforeEach
    void setUp() throws SQLException {
        when(databaseConfig.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void testCreateBorrowingRecord() throws SQLException {
        Borrowing borrowing = new Borrowing("1", "1", Timestamp.valueOf("2024-12-12 12:12:12"), Timestamp.valueOf("2024-12-12 12:12:12"));
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = borrowDAO.createBorrowingRecord(borrowing);
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = borrowDAO.createBorrowingRecord(borrowing);
        assertFalse(failure);
    }

    @Test
    void testDeleteBorrowing() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = borrowDAO.deleteBorrowing("1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = borrowDAO.deleteBorrowing("1");
        assertFalse(failure);
    }

    @Test
    void testGetAllRecords() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("user_name")).thenReturn("Kwasi Baidoo");
        when(mockResultSet.getString("book_name")).thenReturn("The Last hope");
        when(mockResultSet.getInt("status")).thenReturn(1);
        when(mockResultSet.getTimestamp("date_borrowed")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("due_date")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("deletedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));

        LinkedList<Borrowing> result = borrowDAO.getAllRecords();
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Borrowing> result_failure = borrowDAO.getAllRecords();
        assertEquals(new LinkedList<Borrowing>(), result_failure);
    }

    @Test
    void testGetRecordByID() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("user_name")).thenReturn("Kwasi Baidoo");
        when(mockResultSet.getString("book_name")).thenReturn("The Last hope");
        when(mockResultSet.getInt("status")).thenReturn(1);
        when(mockResultSet.getTimestamp("date_borrowed")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("due_date")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("deletedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));

        Borrowing borrowing = borrowDAO.getRecordByID("1");
        assertNotNull(borrowing);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);
        Borrowing borrowing_failure = borrowDAO.getRecordByID("1");
        assertEquals(new Borrowing(), borrowing_failure);


        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        Borrowing borrowing_failure_exception = borrowDAO.getRecordByID("1");
        assertEquals(new Borrowing(), borrowing_failure_exception);
    }

    @Test
    void testGetUserRecords() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        when(mockResultSet.getString("id")).thenReturn("1");
        when(mockResultSet.getString("user_name")).thenReturn("Kwasi Baidoo");
        when(mockResultSet.getString("book_name")).thenReturn("The Last hope");
        when(mockResultSet.getInt("status")).thenReturn(1);
        when(mockResultSet.getTimestamp("date_borrowed")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("due_date")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("createdAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("updatedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));
        when(mockResultSet.getTimestamp("deletedAt")).thenReturn(Timestamp.valueOf("2024-10-10 10:10:10"));

        LinkedList<Borrowing> result = borrowDAO.getUserRecords("id");
        assertNotNull(result);

        when(mockPreparedStatement.executeQuery()).thenThrow(SQLException.class);
        LinkedList<Borrowing> result_failure = borrowDAO.getUserRecords("id");
        assertEquals(new LinkedList<Borrowing>(), result_failure);
    }

    @Test
    void testUpdateBorrowRecord() throws SQLException {
        Borrowing borrowing = new Borrowing(1);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        boolean success = borrowDAO.updateBorrowRecord(borrowing, "1");
        assertTrue(success);

        when(mockPreparedStatement.executeUpdate()).thenThrow(SQLException.class);
        boolean failure = borrowDAO.updateBorrowRecord(borrowing, "1");
        assertFalse(failure);
    }
}
