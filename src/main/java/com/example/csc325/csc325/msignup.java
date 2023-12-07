package com.example.csc325.csc325;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class msignup {
    @FXML
    public Button signUp;



    public void handleSignUp(ActionEvent actionEvent) {

    }


    private void storeDataToFirestore(String data) {
        Firestore firestore = FirestoreClient.getFirestore();
        try {
            // Add data to Firestore
            Map<String, Object> docData = new HashMap<>();
            docData.put("Mazen is dumb", data + " Andrew is Smart");

            firestore.collection("users")
                    .add(docData)
                    .get(); // Blocking call to wait for the result
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
