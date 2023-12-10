package com.example.csc325.csc325.Controllers;

import com.example.csc325.csc325.SceneManager;
import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class msignup {
    @FXML
    public Button signUp;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField emailField;
    public TextField passwordFeild;
    public TextField ConfirmPasswordFeild;
    public TextField phoneNumberField;
    public Hyperlink loginLink;

    public void handleSignUp(ActionEvent actionEvent) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || emailField.getText().isEmpty() || passwordFeild.getText().isEmpty() || ConfirmPasswordFeild.getText().isEmpty() || phoneNumberField.getText().isEmpty()) {
            System.out.println("All fields are required");

        }
         else if (!passwordFeild.getText().equals(ConfirmPasswordFeild.getText())) {
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
            User user = new Employee(firstNameField.getText(), lastNameField.getText(), phoneNumberField.getText(), emailField.getText());
            user.register(passwordFeild.getText());
        }
    }

    public void backToLogin(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showLoginScene();
    }
}
