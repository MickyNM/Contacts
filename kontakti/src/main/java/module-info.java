module com.example.kontakti {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.kontakti to javafx.fxml;
    exports com.example.kontakti;
    exports com.example.kontakti.podaci;
    opens com.example.kontakti.podaci to javafx.fxml;
}