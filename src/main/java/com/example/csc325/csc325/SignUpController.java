package com.example.csc325.csc325;

import com.example.csc325.csc325.users.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpController {
    @FXML
    public TextField nameField;
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;

    @FXML
    public void signUp(ActionEvent actionEvent) {
        Employee user = new Employee(emailField.getText(), nameField.getText(), passwordField.getText());
        user.save();
    }

    public void login(ActionEvent actionEvent) throws IOException {
        SceneManager.getInstance().showLoginScene();
    }
}
