package com.lms.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.lms.App;
import com.lms.dao.ReservationDAO;
import com.lms.models.Reservation;
import com.lms.utils.AuthUtil;
import com.lms.utils.NotificationToast;
import com.lms.utils.Router;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReservationsController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private NavigationController navigationController = new NavigationController();
    private ReservationDAO reservationDAO = new ReservationDAO();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Reservation, String> column_book;

    @FXML
    private TableColumn<Reservation, Timestamp> column_date;

    @FXML
    private TableColumn<Reservation, Integer> column_status;

    @FXML
    private Button delete_reservation;

    @FXML
    private TableView<Reservation> reservations_table;

    @FXML
    private Button view_reservation;

     private ObservableList<Reservation> reservations = FXCollections.observableArrayList();

    @FXML
    void deleteReservation(MouseEvent event) {
        boolean success = reservationDAO.deleteReservation(reservations_table.getSelectionModel().getSelectedItem().getId());
        if(!success) {
            notificationToast.showNotification(AlertType.ERROR,"Process Failed", "There was a problem while deleting the reservation");
        }
        else {
            notificationToast.showNotification(AlertType.CONFIRMATION,"Reservation Deleted", "Reservation deleted successfully.");
            navigationController.navCategories();
        }
    }

    @FXML
    void viewReservation(MouseEvent event) {
        try {
            Reservation selectedReservation = reservations_table.getSelectionModel().getSelectedItem();
            String reservationID = selectedReservation.getId();
            
            FXMLLoader loader = new FXMLLoader(App.class.getResource("viewreservations.fxml"));
            Parent root = loader.load();

            // Get the controller of the new window
            ViewReservationsController controller = loader.getController();
            controller.setReservationID(reservationID); // Pass the book ID to the controller

            // Open the new window
            Stage stage = new Stage();
            stage.setTitle("View Reservation Details");
            stage.setScene(new Scene(root, 640, 480));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        column_book.setCellValueFactory(new PropertyValueFactory<Reservation,String>("book_id")); 
        column_date.setCellValueFactory(new PropertyValueFactory<Reservation,Timestamp>("reservation_date")); 
        column_status.setCellValueFactory(new PropertyValueFactory<Reservation,Integer>("status")); 

        if(AuthUtil.getInstance().getUserRole() == "patron") {
            LinkedList<Reservation> reservationList = reservationDAO.getUserReservations(AuthUtil.getInstance().getUserID());
            reservations.addAll(reservationList);
            reservations_table.setItems(reservations);
        }
        else {
            LinkedList<Reservation> reservationList = reservationDAO.getAllReservations();
            reservations.addAll(reservationList);
            reservations_table.setItems(reservations);
        }

    }

    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }

}
