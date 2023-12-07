module com.example.csc325.csc325 {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.cloud.firestore;
    requires com.google.auth.oauth2;
    requires firebase.admin;
    requires com.google.auth;
    requires google.cloud.core;
    requires com.google.api.apicommon;
    requires java.sql;


    opens com.example.csc325.csc325 to javafx.fxml;
    exports com.example.csc325.csc325;
}