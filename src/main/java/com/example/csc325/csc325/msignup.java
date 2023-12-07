package com.example.csc325.csc325;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
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

    @FXML
    public Button signUpGoogle;

    private static final String SERVICE_ACCOUNT_KEY_PATH = "src/main/java/com/example/csc325/csc325/privatekey.json";
    private static final String DB_URL = "https://csc325-finalproject.firebaseio.com"; // Replace with your Firestore database URL

    private final Firestore firestore;

    public msignup() {
        // Initialize Firestore
        try (FileInputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_KEY_PATH)) {
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            this.firestore = FirestoreOptions.newBuilder().setCredentials(credentials).build().getService();
        } catch (IOException e) {
            throw new RuntimeException("Error loading Firestore credentials", e);
        }
    }

    public void handleSignUp(ActionEvent actionEvent) {
        //Store this data to a local SQL database
    }

    public void handleGoogleSignUp(ActionEvent actionEvent) {
        //Using a Google account, store this data to a SQL database
        String googleUserData = "google_data_to_store";
        storeDataToFirestore(googleUserData);
    }

    private void storeDataToFirestore(String data) {
        try {
            // Add data to Firestore
            Map<String, Object> docData = new HashMap<>();
            docData.put("column_name", data);

            firestore.collection(COLLECTION_NAME)
                    .add(docData)
                    .get(); // Blocking call to wait for the result
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
