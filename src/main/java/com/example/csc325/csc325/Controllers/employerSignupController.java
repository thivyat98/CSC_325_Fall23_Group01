package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.users.Employer;
import com.example.csc325.csc325.users.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Controller class for handling the logic of the employer signup view.
 */
public class employerSignupController {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^\\d{10}$"; // Assumes a 10-digit phone number
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    public TextField companyNameField;
    public TextField emailField;
    public TextField passwordField;
    public TextField ConfirmPasswordField;
    public TextField phoneNumberField;
    public Button signUp;
    public Hyperlink loginLink;
    public Label lblErrorMsg;

    /**
     * Navigates back to the login scene.
     *
     * @param actionEvent The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void backToLogin(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showLoginScene();
    }

    /**
     * Handles the signup process for an employer.
     *
     * @param actionEvent The ActionEvent triggering the method.
     * @throws ExecutionException   If the execution encounters an exception.
     * @throws InterruptedException If the execution is interrupted.
     * @throws IOException          If an I/O error occurs.
     */
    public void handleSignUp(ActionEvent actionEvent) throws ExecutionException, InterruptedException, IOException {
        Firestore db = FirestoreClient.getFirestore();

        // Validate email using Regex
        if (!emailField.getText().matches(EMAIL_REGEX)) {
            System.out.println("Invalid email format");
            lblErrorMsg.setText("Invalid email format");
            return;
        }

        // Validate phone number using Regex
        if (!phoneNumberField.getText().matches(PHONE_REGEX)) {
            System.out.println("Invalid phone number format");
            lblErrorMsg.setText("Invalid phone number format");
            return;
        }

        // Validate password using Regex
        if (!passwordField.getText().matches(PASSWORD_REGEX)) {
            System.out.println("Invalid password format");
            lblErrorMsg.setText("Invalid password format");
            return;
        }

        if (companyNameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                passwordField.getText().isEmpty() || ConfirmPasswordField.getText().isEmpty() ||
                phoneNumberField.getText().isEmpty()) {
            System.out.println("All fields are required");
            lblErrorMsg.setText("All fields are required");
        } else if (!passwordField.getText().equals(ConfirmPasswordField.getText())) {
            System.out.println("Passwords don't match");
            lblErrorMsg.setText("Passwords don't match");
        } else {
            ApiFuture<QuerySnapshot> query = db.collection("auth")
                    .whereEqualTo("username", emailField.getText().toLowerCase())
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();

            if (!documents.isEmpty()) {
                System.out.println("Account using email already exists");
                lblErrorMsg.setText("Account using email already exists");
                return;
            }

            User user = new Employer(companyNameField.getText(), emailField.getText(), phoneNumberField.getText());
            user.register(passwordField.getText());
            SceneManager.getInstance().showSuccessfulRegScene();
        }
    }

    /**
     * Navigates to the signup scene.
     *
     * @param actionEvent The ActionEvent triggering the method.
     * @throws IOException If an I/O error occurs.
     */
    public void toSignup(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showSignUpScene();
    }
}
