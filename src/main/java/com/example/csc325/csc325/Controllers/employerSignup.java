package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.users.Employee;
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
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class employerSignup {


    public TextField companyNameField;
    public TextField emailField;
    public TextField passwordField;
    public TextField ConfirmPasswordField;
    public TextField phoneNumberField;
    public Button signUp;
    public Hyperlink loginLink;

    public void backToLogin(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showLoginScene();
    }

    public void handleSignUp(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        if (companyNameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordField.getText().isEmpty() || ConfirmPasswordField.getText().isEmpty() || phoneNumberField.getText().isEmpty()) {
            System.out.println("All fields are required");

        }
        else if (!passwordField.getText().equals(ConfirmPasswordField.getText())) {
            System.out.println("Passwords dont match");

        } else {
            ApiFuture<QuerySnapshot> query = db.collection("auth")
                    .whereEqualTo("username", emailField.getText())
                    .get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();

            if (!documents.isEmpty()) {
                System.out.println("Account using email already exists");
                return;

            }
            User user = new Employer(companyNameField.getText(), emailField.getText(), phoneNumberField.getText());
            user.register(passwordField.getText());
        }
    }

    public void toSignup(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showSignUpScene();
    }
}
