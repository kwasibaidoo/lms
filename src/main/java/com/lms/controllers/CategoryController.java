package com.lms.controllers;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;

import com.lms.App;
import com.lms.dao.AuthorDAO;
import com.lms.dao.CategoryDAO;
import com.lms.models.Author;
import com.lms.models.Category;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CategoryController implements Router {

    private NotificationToast notificationToast = new NotificationToast();
    private NavigationController navigationController = new NavigationController();

    @FXML
    private VBox content;

    @FXML
    private TableView<Category> category_table;
    
    @FXML
    private Button category_button;

    @FXML
    private TableColumn<Category,String> column_name;

    @FXML
    private TableColumn<Category,Timestamp> column_date;

    @FXML
    private TextField search_category;

    private ObservableList<Category> categoryList = FXCollections.observableArrayList();
    private ObservableList<Category> filteredCategoryList = FXCollections.observableArrayList();


    // public void addCategory() {
    //     try {
    //         // Load the Add Category page dynamically
    //         FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/lms/addCategory.fxml"));
    //         Parent addCategoryPage = loader.load();
            
    //         // You can either load the page into the current content VBox
    //         content.getChildren().clear();  // Clear any existing content
    //         content.getChildren().add(addCategoryPage);  // Add the new page content
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }


   


    @FXML
    public void initialize() {
        // Set up the columns
        column_name.setCellValueFactory(new PropertyValueFactory<Category,String>("name")); 
        column_date.setCellValueFactory(new PropertyValueFactory<Category,Timestamp>("createdAt")); 

        // Fetch data from the database
        LinkedList<Category> categories = CategoryDAO.getCategories();

        // Add data to the ObservableList
        categoryList.addAll(categories);

        // Bind the data to the TableView
        category_table.setItems(categoryList);
        filteredCategoryList.addAll(categoryList);
        // search stuff
        search_category.textProperty().addListener((observable, oldValue, newValue) -> {
            search(newValue);  // Trigger the search every time the text changes
        });
    }

    @FXML
    public void deleteCategory() {
        if(AuthUtil.getInstance().getUserRole().equals("patron")){
            notificationToast.showNotification(AlertType.ERROR,"You're not authorised", "You do not have permission to access this page");
        }
        else{
            boolean success = AuthorDAO.deleteAuthor(category_table.getSelectionModel().getSelectedItem().getId());
            if(!success) {
                notificationToast.showNotification(AlertType.ERROR,"Process Failed", "There was a problem while deleting the category");
            }
            else {
                notificationToast.showNotification(AlertType.CONFIRMATION,"Category Deleted", "Category deleted successfully.");
                navigationController.navCategories();
            }
        }
        
    }

    @FXML
    public void updateCategory(){
        if(AuthUtil.getInstance().getUserRole().equals("patron")){
            notificationToast.showNotification(AlertType.ERROR,"You're not authorised", "You do not have permission to access this page");
        }
        else{
            try {
                Category selectedCategory = category_table.getSelectionModel().getSelectedItem();
                String categoryId = selectedCategory.getId();
                
                FXMLLoader loader = new FXMLLoader(App.class.getResource("updatecategory.fxml"));
                Parent root = loader.load();

                // Get the controller of the new window
                UpdateCategoryController controller = loader.getController();
                controller.setCategoryId(categoryId); // Pass the book ID to the controller

                // Open the new window
                Stage stage = new Stage();
                stage.setTitle("Update Author Details");
                stage.setScene(new Scene(root, 640, 480));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void search(String query) {
        String lowerCaseSearchText = query.toLowerCase();
        filteredCategoryList.clear();
        if(lowerCaseSearchText.isEmpty()){
            filteredCategoryList.addAll(categoryList);
        }
        else{
            LinkedList<Category> searchResults = CategoryDAO.findCategory(lowerCaseSearchText);
            filteredCategoryList.addAll(searchResults);
        }
        

        
        category_table.setItems(filteredCategoryList);
    }





    @Override
    public void setNavigationController(NavigationController navigationController) {
        this.navigationController = navigationController;
    }
}
