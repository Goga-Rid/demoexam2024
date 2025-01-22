module com.example.demoexam2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.demoexam2024 to javafx.fxml;
    exports com.example.demoexam2024;
}