package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.UserSessionManager;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Controller class for handling user login functionality.
 */
public class loginController {

    @FXML
    public Button loginBtn;

    @FXML
    public Label lblErrorMsg;
    public TextField usernameField;
    public TextField passwordField;

    /**
     * Switches to the sign-up scene.
     *
     * @param actionEvent The event triggered by the sign-up button.
     * @throws IOException If an I/O error occurs.
     */
    public void signup(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showSignUpScene();
    }

    /**
     * Attempts to sign in the user based on provided credentials.
     *
     * @param actionEvent The event triggered by the login button.
     * @throws IOException          If an I/O error occurs.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     */
    public void signin(ActionEvent actionEvent) throws IOException, ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        // Query Firestore for the user account with the entered email
        ApiFuture<QuerySnapshot> query = db.collection("auth")
                .whereEqualTo("username", usernameField.getText().toLowerCase())
                .get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        // Check if an account with the entered email exists
        if (documents.isEmpty()) {
            System.out.println("No Account under this email");
            lblErrorMsg.setText("No Account under this email");
            return;
        }

        // Retrieve the hashed password stored in Firestore for the user
        String storedHashedPassword = documents.get(0).getString("hashPassword");

        // Check if the entered password matches the stored hashed password
        assert storedHashedPassword != null;
        if (BCrypt.checkpw(passwordField.getText(), storedHashedPassword)) {
            System.out.println(documents.get(0).getString("ID"));
            // Log in the user and switch to the main scene
            UserSessionManager.loginUser(documents.get(0).getString("ID"), documents.get(0).getString("Type"));
            SceneManager.getInstance().showMainScene();
        } else {
            System.out.println("Incorrect Password");
            lblErrorMsg.setText("Incorrect Password");
            return;
        }
    }
}
