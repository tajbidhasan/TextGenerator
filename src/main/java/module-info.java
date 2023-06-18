module com.example.finalproject218 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.finalproject218 to javafx.fxml;
    exports com.example.finalproject218;
}