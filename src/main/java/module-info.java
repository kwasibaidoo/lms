module com.lms {
    requires java.sql;
    requires lombok;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.lms to javafx.fxml;
    opens com.lms.controllers;
    exports com.lms;
    exports com.lms.controllers to javafx.fxml;
}
