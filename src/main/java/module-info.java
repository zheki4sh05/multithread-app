module com.example.threads {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.threads to javafx.fxml;
    exports com.example.threads;
}