module com.lms {
    requires java.sql;
    requires lombok;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.lms to javafx.fxml;
    exports com.lms;
}
