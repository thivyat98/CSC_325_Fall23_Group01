package com.example.csc325.csc325;

import com.example.csc325.csc325.users.Employee;
import com.example.csc325.csc325.users.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    public TextField user;
    @FXML
    public TextField password;
    @FXML
    public Button loginButton;
    @FXML
    public void handleForgotPassword(ActionEvent actionEvent) {
    }
//    @FXML
//    public void handleLogin(ActionEvent actionEvent) {
//        if(user.getText().isEmpty()){
//            System.out.println("Please enter a valid email");
//            return;}
//        try {
//            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(user.getText());
//            System.out.println("Successfully fetched user data: " + userRecord.getUid());
//
//            User user = new Employee(userRecord.getUid(), userRecord.getEmail(),"Default");
//            System.out.println("Successfully fetched user data: " + user.getId());
//            SceneManager.getInstance().showProfileScene(user);
//
//
//        } catch (FirebaseAuthException e) {
//            System.err.println("Failed: " + e.getMessage());
//        } catch (IOException e) {
//            System.out.println("Failed: " + e.getMessage());
//        }
//    }
//    @FXML
//    public void handleSignup(ActionEvent actionEvent) throws IOException {
//        SceneManager.getInstance().showSignUpScene();
//    }
}
