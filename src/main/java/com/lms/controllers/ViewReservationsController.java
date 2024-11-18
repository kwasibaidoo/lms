package com.lms.controllers;

import java.net.URL;
import java.util.ResourceBundle;


import com.lms.dao.ReservationDAO;
import com.lms.models.Reservation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ViewReservationsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label book_value;

    @FXML
    private Label createdAt_value;

    @FXML
    private Label patron_name_value;

    @FXML
    private Label reservation_date_value;

    @FXML
    private Label status_value;
    
    private String reservationID;
    
    @FXML
    void initialize() {
        

    }
    
       
    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
        System.out.println(reservationID);

        // Fetch the book details using the ID
        Reservation reservation = ReservationDAO.getReservationById(reservationID); 

        if (reservation != null) {
            book_value.setText(reservation.getBook_id());
            patron_name_value.setText(reservation.getUser_id());
            reservation_date_value.setText(reservation.getReservation_date().toString()); 
            createdAt_value.setText(reservation.getCreatedAt().toString());
            status_value.setText(String.valueOf(reservation.getStatus()));
        } else {
            System.out.println("Reservation not found.");
        }
    }

}
